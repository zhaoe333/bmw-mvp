package com.cm.datalog;

import com.cm.common.json.JacksonUtil;
import com.cm.common.utils.DESUtils;
import com.cm.common.utils.DateTimeUtils;
import com.cm.common.utils.LogicUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 负责整体项目的数据采集
 * @author zyl
 * @createTime 2019-12-02
 */
@Component
@Slf4j
public class DataLogFilter implements Filter {

    private static String hostname = "";
    private static final String LOCAL_IP = "127.0.0.1";
    private static final String LOCAL_GATEWAY = "0:0:0:0:0:0:0:1";
    private static final String UNKNOWN = "unknown";



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取本机hostname
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("无法获取hostname.");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            try{
                String uri = ((HttpServletRequest) request).getRequestURI();
                // 忽略心跳检测(url="/")及静态文件请求及swagger
                if (uri.equals("/")||uri.contains("static")||uri.contains("swagger")||uri.contains("api-docs")) {
                    chain.doFilter(request, response);
                } else {
                    ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
                    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

                    //TODO invoke这块需要坐下判断区分小程序端 和 pc端
                    TracingLog.Builder logBuilder = DataLogHelper.createTracingLog(String.valueOf(Thread.currentThread().getId()))
                            .invokeBy("web").hostname(hostname)
                            .extra("page:" + requestWrapper.getHeader("pageCode"));


                    long start = System.currentTimeMillis();
                    preRequestHandler(requestWrapper, logBuilder);

                    // Do normal servlet dispatch
                    chain.doFilter(requestWrapper, responseWrapper);

                    postResponseHandler(requestWrapper, responseWrapper, start, logBuilder);


                    // Call this otherwise front-end won't receive the body content
                    responseWrapper.copyBodyToResponse();

                    DataLogHelper.send(logBuilder.build());
                }
            }catch(Exception e){
                e.printStackTrace();
                log.error("埋点数据出现异常， 请及时排查。");
            }
        }
    }

    @Override
    public void destroy() {

    }

    private void handleUserDeviceData(HttpServletRequest request, TracingLog.Builder builder) {
        String phoneModel = request.getHeader("model");
        String phoneBrand = request.getHeader("brand");
        String osInfo = request.getHeader("system");

        phoneModel = phoneModel != null ? phoneModel : "unknown";
        phoneBrand = phoneBrand != null ? phoneBrand : "unknown";
        osInfo = osInfo != null ? osInfo : "unknown";

        String deviceId = genDeviceId(phoneBrand, phoneModel, osInfo.split(" ")[0]);
        String encodedDeviceId = Base64Utils.encodeToString(deviceId.getBytes());

        builder.deviceId(encodedDeviceId);
        builder.deviceSystem(osInfo);
    }

    private String genDeviceId(String brand, String model, String os) {
        StringBuilder sb = new StringBuilder();
        sb.append(brand);
        sb.append("|");
        sb.append(model);
        sb.append("|");
        sb.append(os);
        return sb.toString();
    }

    private void preRequestHandler(ContentCachingRequestWrapper request, TracingLog.Builder builder) throws Exception{
        String uri = request.getRequestURI();
        String method = request.getMethod();
        // Request headers
        Map<String, String> headers = new HashMap<>();
        //TODO 后续判断是否需要header里面的所有信息
        Collections.list(request.getHeaderNames()).forEach(headerName ->
                Collections.list(request.getHeaders(headerName)).forEach(headerValue ->
                        headers.put(headerName, headerValue)));

        // 记录访问日志
        builder.reqMethod(method)
                .reqURL(uri)
                .reqHeader(headers.toString())
                .reqHost(getIpAddr(request))
                .reqTimestamp(DateTimeUtils.formatDate());


    }

    private String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(LOCAL_IP.equals(ipAddress) || LOCAL_GATEWAY.equals(ipAddress)){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.warn("Unknown Host: ",e);
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割 ipv6不支持
        //"***.***.***.***".length() = 15
        if(ipAddress != null && ipAddress.length() > 15){
            if(ipAddress.indexOf(",") > 0){
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    private void postResponseHandler(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long startTime, TracingLog.Builder builder) throws Exception{
        String uri = request.getRequestURI();
        // Response
        String content = new String(response.getContentAsByteArray());
        // Request
        String reqContent = new String(request.getContentAsByteArray());
        int code = response.getStatus();
        // 记录访问日志
        builder.respStatusCode(code)
                .respContent(content.isEmpty() ? null : content)
                .timestamp(DateTimeUtils.formatDate())
                .reqBody(reqContent)
                .duration( (int) (System.currentTimeMillis() - startTime) );
        // 通过token获取用户id
        String token = request.getHeader("token");

        // 使用contains 避免项目名称影响判断
        if (uri.contains("/wx/user/login")) {
            // 如果是小程序登录 获取手机
            handleUserDeviceData(request, builder);
            JsonNode bodyNode = JacksonUtil.get().json2Node(content);
            token = bodyNode.get("body").asText();
        }else if(uri.contains("/sys/user/login")){
            JsonNode bodyNode = JacksonUtil.get().json2Node(content);
            token = bodyNode.get("body").get("token").asText();
        }
        handleToken(token,builder);
    }

    private void handleToken(String token, TracingLog.Builder builder) throws Exception{
        if(LogicUtil.isNullOrEmpty(token)){
            return;
        }
        String[] userInfos = DESUtils.decrypt(token).split("-");
        builder.invokeBy(userInfos[0]);
        builder.userId(userInfos[2]);
    }
}
