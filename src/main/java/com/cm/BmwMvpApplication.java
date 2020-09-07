package com.cm;

import com.cm.common.interceptor.PrivilegeInterceptor;
import com.cm.common.spring.TSPMappingJackson2HttpMessageConverter;
import com.cm.common.spring.TSPStringHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication(scanBasePackages={"com.cm"})
@EnableScheduling
@MapperScan("com.cm.**.mapper")
@EnableConfigurationProperties
public class BmwMvpApplication extends WebMvcConfigurationSupport {

	@Autowired
	private PrivilegeInterceptor privilegeInterceptor;
	@Autowired
	private TSPMappingJackson2HttpMessageConverter jsonMessageConvert;
	@Autowired
	private TSPStringHttpMessageConverter stringMessageConvert;


	@Override
	/**
	 * 添加拦截器
	 */
	protected void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(privilegeInterceptor).addPathPatterns("/**");
	}

	/**
	 * 继承了WebMvcConfigurationSupport后 会导致WebMvcAutoConfiguration失效
	 * 从而导致spring.mvc.static-path-pattern和spring.resources.static-locations失效
	 * 因此需要手动配置静态文件访问路径
	 * 此项目暂时无用
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}

	@Override
	/**
	 * 添加自定义message convert
	 */
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.extendMessageConverters(converters);
		jsonMessageConvert.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
		jsonMessageConvert.setDefaultCharset(StandardCharsets.UTF_8);
		stringMessageConvert.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN,MediaType.TEXT_HTML));
		stringMessageConvert.setWriteAcceptCharset(false);
		stringMessageConvert.setDefaultCharset(StandardCharsets.UTF_8);
		converters.add(0,jsonMessageConvert);
		converters.add(1,stringMessageConvert);
	}

	/**
	 * 添加全局cors跨域设置
	 * @param registry
	 */
	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		super.addCorsMappings(registry);
		registry.addMapping("/**").allowedOrigins("*")
				.allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(false).maxAge(3600);
	}

	/**
	 * 设置redis序列化
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean(name="redisTemplate")
	public RedisTemplate<Object, Object> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory){
		RedisTemplate<Object,Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}


	public static void main(String[] args) {
		SpringApplication.run(BmwMvpApplication.class, args);
	}

}
