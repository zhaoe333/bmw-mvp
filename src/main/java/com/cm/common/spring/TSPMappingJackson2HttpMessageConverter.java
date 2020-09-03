package com.cm.common.spring;

import com.cm.common.constant.CommonConstants;
import com.cm.common.exception.FMException;
import com.cm.common.query.Query;
import com.cm.common.utils.DESUtils;
import com.cm.common.utils.DateTimeUtils;
import com.cm.common.utils.LogicUtil;
import com.cm.common.utils.TokenUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 自定义spring-jackson-converter 可以方便订制objectmapper和处理一些特殊情况
 * 
 * @author yunlu
 *
 */
@Component
public class TSPMappingJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

	private String jsonPrefix;
	@Resource
	private TokenUtil tokenUtil;

	/**
	 * Construct a new
	 * configuration provided by {@link Jackson2ObjectMapperBuilder}.
	 */
	public TSPMappingJackson2HttpMessageConverter() {
		this(Jackson2ObjectMapperBuilder.json().build());
	}

	/**
	 * Construct a new
	 * {@link ObjectMapper}. You can use {@link Jackson2ObjectMapperBuilder} to
	 * build it easily.
	 * 
	 * @see Jackson2ObjectMapperBuilder#json()
	 */
	public TSPMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		// objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
		super(objectMapper,  MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
		super.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		super.objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		super.objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		super.objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		super.objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * Specify a custom prefix to use for this view's JSON output. Default is
	 * none.
	 * 
	 * @see #setPrefixJson
	 */
	public void setJsonPrefix(String jsonPrefix) {
		this.jsonPrefix = jsonPrefix;
	}

	/**
	 * Indicate whether the JSON output by this view should be prefixed with
	 * ")]}', ". Default is false.
	 * <p>
	 * Prefixing the JSON string in this manner is used to help prevent JSON
	 * Hijacking. The prefix renders the string syntactically invalid as a
	 * script so that it cannot be hijacked. This prefix should be stripped
	 * before parsing the string as JSON.
	 * 
	 * @see #setJsonPrefix
	 */
	public void setPrefixJson(boolean prefixJson) {
		this.jsonPrefix = (prefixJson ? ")]}', " : null);
	}

	@Override
	protected void writePrefix(JsonGenerator generator, Object object) throws IOException {
		if (this.jsonPrefix != null) {
			generator.writeRaw(this.jsonPrefix);
		}
//		String jsonpFunction = (object instanceof MappingJacksonValue
//				? ((MappingJacksonValue) object).getJsonpFunction() : null);
//		if (jsonpFunction != null) {
//			generator.writeRaw("/**/");
//			generator.writeRaw(jsonpFunction + "(");
//		}
	}

	@Override
	protected void writeSuffix(JsonGenerator generator, Object object) throws IOException {
//		String jsonpFunction = (object instanceof MappingJacksonValue
//				? ((MappingJacksonValue) object).getJsonpFunction() : null);
//		if (jsonpFunction != null) {
//			generator.writeRaw(");");
//		}s
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		JavaType javaType = getJavaType(clazz, null);
		if (inputMessage.getHeaders().getContentLength() == 0) {
			return this.objectMapper.readValue("{}", javaType);
		}
		return readJavaType(javaType, inputMessage);
	}

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		JavaType javaType = getJavaType(type, contextClass);
		if (inputMessage.getHeaders().getContentLength() == 0) {
			return this.objectMapper.readValue("{}", javaType);
		}
		return readJavaType(javaType, inputMessage);
	}

	@SuppressWarnings("deprecation")
	private Object readJavaType(JavaType javaType, HttpInputMessage inputMessage) {
		try {
			Object obj = null;
			if (inputMessage instanceof MappingJacksonInputMessage) {
				Class<?> deserializationView = ((MappingJacksonInputMessage) inputMessage).getDeserializationView();
				if (deserializationView != null) {
					obj = this.objectMapper.readerWithView(deserializationView).withType(javaType)
							.readValue(inputMessage.getBody());
				}
			}
			if (null == obj) {
				obj = this.objectMapper.readValue(inputMessage.getBody(), javaType);
			}
			if (obj instanceof Query) {
				setQuery((Query) obj, inputMessage.getHeaders().getFirst("token"));
				setQueryTime((Query) obj);
			}
			return obj;
		} catch (FMException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new HttpMessageNotReadableException("Could not read document: " + ex.getMessage(), ex);
		}
	}

	/**
	 * 设置任意时间为需要的查询时间
	 * @param query
	 * @throws Exception
	 */
	private void setQueryTime(Query query)throws Exception {
		if(null!=query.getQueryTime() && null!=query.getTimeType()){
			query.setQueryTime(DateTimeUtils.convertByTimeType(query.getQueryTime(),query.getTimeType()));
		}
	}

	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		outputMessage.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		if (object instanceof String) {
			Charset charset = Charset.forName("UTF-8");
			StreamUtils.copy((String) object, charset, outputMessage.getBody());
		} else {
			super.writeInternal(object, type, outputMessage);
		}
	}

	/**
	 * 设置用户的相关信息
	 * @param query
	 * @param token
	 * @throws Exception
	 */
	private void setQuery(Query query, String token) throws Exception {
		//TODO 这里需要处理区分手机用户和后端用户
		if (LogicUtil.isNullOrEmpty(token)) {
			return;
		}
		try {
			token = DESUtils.decrypt(token);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Object map = tokenUtil.get(token);
		if (map != null) {
			Map tokenMap = (Map) map;
			if (LogicUtil.isNotNullAndEmpty(tokenMap)) {
				query.setLoginUserId((String) tokenMap.get(CommonConstants.TOKEN_USER_KEY));
				query.setLoginIP((String) tokenMap.get(CommonConstants.TOKEN_LOGINIP_KEY));
				query.setLoginName((String) tokenMap.get(CommonConstants.TOKEN_LOGINNAME_KEY));
				query.setLoginRoleCode((String) tokenMap.get(CommonConstants.TOKEN_ROLECODE_KEY));
				query.setLoginRoleCodes((List<String>) tokenMap.get(CommonConstants.TOKEN_ROLECODES_KEY));
				query.setSessionKey((String)tokenMap.get(CommonConstants.TOKEN_SESSION_KEY));
				query.setOpenId((String)tokenMap.get(CommonConstants.TOKEN_OPENID_KEY));
				if(LogicUtil.isNullOrEmpty(query.getLoginRoleCodes())){
					query.setLoginRoleCodes(null);
				}
			}
		}
	}
}
