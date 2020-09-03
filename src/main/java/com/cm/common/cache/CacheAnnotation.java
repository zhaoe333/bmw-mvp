package com.cm.common.cache;

import java.lang.annotation.*;

/**
 * 
 * @author zyl
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheAnnotation {
	/**
	 * mytest
	 * @return
	 */
	String value() default "";
	
	String key() default "";
	/**
	 * 过期时间 0不过期 默认一天
	 * @return
	 */
	long expire() default 24*60;
}
