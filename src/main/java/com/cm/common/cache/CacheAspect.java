package com.cm.common.cache;

import com.cm.common.json.JacksonUtil;
import com.cm.common.query.Query;
import com.cm.common.utils.BeanUtil;
import com.cm.common.utils.DateTimeUtils;
import com.cm.common.utils.MD5;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * aop缓存处理类
 * @author zyl
 *
 */
@Component
@Aspect
public class CacheAspect {

	private static final String PRE="CACHE-";

	private static final Logger log = LoggerFactory.getLogger(CacheAspect.class);

	@Resource
	private RedisTemplate<String,Object> redisTemplate;
	
	@Around(value = "execution(* com.cm..impl.*.*(..)) && @annotation(cacheAnn)")
	public Object aroundMethod(ProceedingJoinPoint pjd, CacheAnnotation cacheAnn) throws Throwable {
		//先关闭缓存
		if(!checkNeedCache(pjd.getArgs())){
			log.debug("no need cache ");
			return pjd.proceed();
		}
		Object result;
		String json = JacksonUtil.get().readAsString(pjd.getArgs());
		String key = PRE+pjd.getSignature().getName()+"-"+ MD5.md5String32(json);
		if(redisTemplate.hasKey(key)) {
			result = redisTemplate.opsForValue().get(key);
			log.info("get cache "+key);
		}else {
			result = pjd.proceed();
			redisTemplate.opsForValue().set(key, result);
			if(cacheAnn.expire()>0) {
				redisTemplate.expire(key, (int)cacheAnn.expire(), TimeUnit.MINUTES);
			}
			log.info("cached "+key);
		}
        return result;
	}

	/**
	 * true 可以缓存
	 * false 不可以缓存
	 * @param args
	 * @return
	 */
	private boolean checkNeedCache(Object[] args) throws Exception {
		//TODO 后续添加通知机制 数据处理完之后进行通知
		Date yesterday = DateTimeUtils.getYesterday(new Date());
		if(args.length==0){
			return true;
		}
		for(Object arg:args){
			if(arg instanceof Query){
				//判断时间段查询结束时间
				Date endTime = (Date) BeanUtil.getNoException(arg, "endTime");
				if(null!=endTime&&endTime.compareTo(yesterday)<1){
					return true;
				}
				//判断时间点查询
				Date queryTime = (Date) BeanUtil.getNoException(arg, "queryTime");
				if(null!=queryTime&&queryTime.compareTo(yesterday)<1){
					return true;
				}
				//时间段有问题 不要缓存
				if(null!=endTime||null!=queryTime){
					return false;
				}
			}
		}
		return true;
	}
}
