package com.cm.common.utils;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLock {

    private static final String DEFAULT_LOCK_PREFIX = "LOCK:";

    @Resource
    RedisTemplate redisTemplate;

    public boolean tryLock(String key, String clientId, long expire) {
        return tryLock(key, clientId,
                new StringRedisSerializer(),
                new StringRedisSerializer(),
                expire, TimeUnit.SECONDS);
    }

    private boolean tryLock(String key, String clientId, RedisSerializer argsSerializer, RedisSerializer resultSerializer, long expire, TimeUnit unit) {
        String redisKey = DEFAULT_LOCK_PREFIX + key;
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return 'OK' " +
                "else return redis.call('set', KEYS[1], ARGV[1],'EX',ARGV[2],'NX') end";
        String result = (String) redisTemplate.execute(RedisScript.of(script, String.class),
                argsSerializer,
                resultSerializer,
                Collections.singletonList(redisKey),
                clientId,
                String.valueOf(unit.toSeconds(expire)));
        return "OK".equals(result);
    }

    public boolean release(String key, String clientId) {
        String redisKey = DEFAULT_LOCK_PREFIX + key;
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then redis.call('del', KEYS[1]) return 1 else return 0 end";
        Long result = (Long) redisTemplate.execute(RedisScript.of(script, Long.class),
                Collections.singletonList(redisKey), clientId);
        return result != null && result > 0;
    }

}
