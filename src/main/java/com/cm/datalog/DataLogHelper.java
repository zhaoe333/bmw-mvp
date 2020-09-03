package com.cm.datalog;

import com.cm.common.json.JacksonUtil;
import com.cm.common.spring.SpringUtil;
import com.cm.common.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.core.KafkaTemplate;

/**
 * TODO 调整为kafka记录 暂未优化
 */
@Slf4j
public class DataLogHelper {

    private static final String VER = "2";

    private static final String topicName = "bmw-logs";

    /**
     * 用于判断是否构建索引
     */
    private static String lastDateStr = null;

//    private static KafkaTemplate<String,String> getKafkaTemplate(){
//        if(null!=kafkaTemplate){
//            return kafkaTemplate;
//        }
//        kafkaTemplate = SpringUtil.getBean("kafkaTemplate");
//        return kafkaTemplate;
//    }

    public static void send(BaseLog baseLog){
        String message = JacksonUtil.get().readAsString(baseLog);
        //TODO 目前业务场景不需要处理回调
//        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
//        getKafkaTemplate().send(topicName, message);
        //数据量大 调试的话可以开启本地记录
        log.info(message);
    }

    /**
     * General http tracing log
     * @param tracingId
     * @return
     */
    public static TracingLog.Builder createTracingLog(String tracingId) {
        return new TracingLog.Builder()
                .ver(VER)
                .tracingId(tracingId);
    }

    /**
     * Internel http request/response log
     * @param url
     * @param header
     * @param body
     * @param statusCode
     * @param response
     * @param duration
     */
    public static void sendInternalTracingLog(String url,
                                              Object header,
                                              Object body,
                                              int statusCode,
                                              String response,
                                              int duration)
    {
        TracingLog.Builder b = new TracingLog.Builder()
                .ver(VER)
                .tracingId(String.valueOf(Thread.currentThread().getId()))
                .timestamp(DateTimeUtils.formatDate())
                .reqURL(url)
                .reqHeader(header != null ? (header instanceof String?header.toString(): JacksonUtil.get().readAsString(header)): null)
                .reqBody(body != null ? (body instanceof String?body.toString(): JacksonUtil.get().readAsString(body)) : null)
                .respStatusCode(statusCode)
                .respContent(response)
                .duration(duration)
                .invokeBy("internal");
        send(b.build());
    }

    /**
     * 定制业务日志 当有新用户访问时
     * @param userId
     */
    public static void sendNewUserLog(String userId){
        send(new TracingLog.Builder().ver(VER).tracingId(String.valueOf(Thread.currentThread().getId()))
                .timestamp(DateTimeUtils.formatDate()).userId(userId).reqURL("service/newUser").invokeBy("service").build());
    }
}
