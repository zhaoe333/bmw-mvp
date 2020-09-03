package com.cm.common.utils;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * execl工具类
 * 
 * @author liujie
 * @date 2017-09-14
 */
public class ExcelUtils {


	/**
	 * 将Excel对象发送给客户端
	 * @param response
	 * @param fileName 文件名
	 * @param wb excel
	 * @throws Exception
	 */
	public static void writeExcel(HttpServletResponse response, String fileName, Workbook wb) throws Exception{
		String contentType = "application/vnd.ms-excel;charset=GBK";
		response.setContentType(contentType);
		byte[] fileNameBytes = fileName.getBytes("GBK");
		String name = new String(fileNameBytes, "ISO8859_1");
		response.setHeader("Content-disposition", "inline;filename=\""+name+"\"");
		OutputStream outputStream = response.getOutputStream();
		wb.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

}
