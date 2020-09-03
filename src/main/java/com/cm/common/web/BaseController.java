package com.cm.common.web;

import com.cm.common.exception.FMException;
import com.cm.common.http.FMResponse;
import com.cm.common.http.FMResponseCode;
import com.cm.common.json.JacksonUtil;
import com.cm.common.utils.LogicUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class BaseController {

	
	//日期类型
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	protected String response(Object obj)throws Exception{
		return JacksonUtil.get().readAsString(FMResponse.success(obj));
	}
	
	protected String responseError(String code,String msg)throws Exception{
		return JacksonUtil.get().readAsString(new FMResponse(code,msg));
	}

	protected String responseError(FMResponseCode responseCode, Object body)throws Exception{
		return JacksonUtil.get().readAsString(new FMResponse<>(responseCode, body));
	}

	protected void  paramsError() throws Exception{
		FMException.throwEx(FMResponseCode.paramserror);
	}
	
	protected ResponseEntity<byte[]> imageResponse(byte[] bytes, String filename)throws Exception{
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    List<Charset> charsets = new ArrayList<Charset>();
	    charsets.add(Charset.forName("UTF-8"));
	    headers.setAcceptCharset(charsets);
	    if(LogicUtil.isNotNullAndEmpty(filename)){
			headers.setContentDispositionFormData("attachment", filename);
		}
	    return new ResponseEntity<byte[]>(bytes,headers, HttpStatus.OK);
	}
	
}
