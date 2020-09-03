package com.cm.common.aop;


import com.cm.common.exception.FMException;
import com.cm.common.http.FMResponseCode;
import com.cm.common.utils.RedisLock;
import com.cm.common.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class RepeatSubmitAspect {

    @Resource
    RedisLock redisLock;

    @Resource
    HttpServletRequest request;

    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
    }

    @Around("pointCut(noRepeatSubmit)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        int lockSeconds = noRepeatSubmit.lockTime();
        String token = request.getHeader("token");
        String path = request.getServletPath();
        String key = getKey(token, path);
        String clientId = UUIDUtils.generateUUID();

        log.info("tryLock redis, key = [{}]", key);
        boolean isSuccess = redisLock.tryLock(key, clientId, lockSeconds);

        if (!isSuccess) {
            log.info("lock redis fail, key = [{}]", key);
            FMException.throwEx(FMResponseCode.repeatsubmit);
        }
        log.info("lock redis success, key = [{}]", key);
        try {
            return pjp.proceed();
        } finally {
            log.info("release redis lock, key = [{}]", key);
            redisLock.release(key, clientId);
        }
    }

    private String getKey(String token, String path) {
        return token + path;
    }

}
