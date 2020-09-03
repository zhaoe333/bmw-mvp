package com.cm.common.spring;

import com.cm.common.exception.FMException;
import com.cm.common.exception.FMMultiException;
import com.cm.common.http.FMResponse;
import com.cm.common.http.FMResponseCode;
import com.cm.common.json.JacksonUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义 spring全局异常处理器
 */
@ControllerAdvice
public class SpringBootGlobalExceptionResolver {
    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String errorHandler(Exception ex) {
        ex.printStackTrace();
        return JacksonUtil.get().readAsString(new FMResponse(FMResponseCode.syserror));
    }

    /**
     * 拦截捕捉自定义异常 FMException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = FMException.class)
    public String myErrorHandler(FMException ex) {
        ex.printStackTrace();
        return JacksonUtil.get().readAsString(new FMResponse(ex.getCode(),ex.getMessage()));
    }


    /**
     * 拦截捕捉自定义异常 FMException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = FMMultiException.class)
    public String myErrorHandler(FMMultiException ex) {
        ex.printStackTrace();
        return JacksonUtil.get().readAsString(new FMResponse(ex.getCode(),ex.getMessage(),ex.getBody()));
    }
}
