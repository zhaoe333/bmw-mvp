package com.cm.common.spring;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TSPStringHttpMessageConverter extends StringHttpMessageConverter {
	private String contentType="application/json;charset=UTF-8";

	@Override
	protected void writeInternal(String str, HttpOutputMessage outputMessage) throws IOException {
		// TODO Auto-generated method stub
		outputMessage.getHeaders().set("Content-Type",contentType);
		super.writeInternal(str, outputMessage);
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	protected void addDefaultHeaders(HttpHeaders headers, String t, MediaType contentType) throws IOException {
		// TODO Auto-generated method stub
		super.addDefaultHeaders(headers, t, contentType);
	}
	
	
}
