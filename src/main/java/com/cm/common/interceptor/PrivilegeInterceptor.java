package com.cm.common.interceptor;

import com.cm.common.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * url过滤  权限过滤 token验证
 * @author yunlu
 */
@Component
public class PrivilegeInterceptor implements HandlerInterceptor {


    @Resource
	private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String method=request.getMethod();
        if("OPTIONS".equals(method)){
            response.setStatus(200);
            return true;
        }
        String contextPath = request.getRequestURI();
        String referer = request.getHeader("referer");

        if (!checkUrlForMain(contextPath,referer)){
			//验证登陆 token
            String token = request.getHeader("token");
			tokenUtil.checkToken(token);
		}
        return true;
    }
    /**
     * 验证url是否为公用 开发阶段使用
     * @param url 请求地址
     * @return
     */
    private boolean checkUrlForMain(String url,String referer) {
        //验证是否为借口文档
        if ((null!=referer&&referer.contains("swagger-ui"))||url.contains("/api-docs") || url.contains("/yuqing") || url.contains("/v2/api-docs") || url.contains("swagger-ui") || url.contains("/swagger-resources/configuration/ui")
                || url.contains("/swagger-resources")) {
            return true;
        }
        //系统登陆,登出
        if (url.contains("/sys/user/login") || url.contains("/sys/user/logout") || url.contains("/wx/user/login")) {
            return true;
        }
       //下载
        if (url.contains("createExcel")) {
            return true;
        }

        //视频回调
        if (url.contains("/video/callback")) {
            return true;
        }

        //图片资源
        if (url.contains("/static/img")) {
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
