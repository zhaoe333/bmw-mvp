package com.cm.common.web;

import com.cm.common.exception.FMException;
import com.cm.common.http.FMResponseCode;
import com.cm.common.utils.LogicUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Api(tags = { "文件处理" })
@Controller
@RequestMapping("/file")
public class ExportCommonController extends BaseController {

	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 导出上报数据
	 */
	@ApiOperation("下载舆情PDF")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "通过id下载文件", response = String.class) })
	@ApiImplicitParams({ @ApiImplicitParam(name = "$fileId", required = true) })
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public String downloadHistory(@RequestParam String fileId, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		if (LogicUtil.isNullOrEmpty(fileId)) {
			paramsError();
		}
		String path = redisTemplate.opsForValue().get(fileId);
		if (LogicUtil.isNullOrEmpty(fileId)) {
			FMException.throwEx(FMResponseCode.filetimeout);
		}
		OutputStream toClient = null;
		try {
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			// response.reset();
			// 设置response的Header
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			toClient.close();
		}
		return response(null);
	}

	/**
	 * 导出上报数据 Excel
	 */
	@ApiOperation("导出用户Execl")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = String.class) })
	@ApiImplicitParams({ @ApiImplicitParam(name = "$fileName", required = true) })
	@RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	@ResponseBody
	public String downloadUserInfo(@RequestParam String fileName, HttpServletResponse response) throws Exception {
		if (LogicUtil.isNullOrEmpty(fileName)) {
			paramsError();
		}
		OutputStream toClient = null;
		try {
			File file = new File(fileName);
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(
					new FileInputStream(fileName));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			// response.reset();
			// 设置response的Header
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (Exception e) {
			System.out.println("ExportCommonController类downloadUserInfo方法:" + e.getMessage());
			FMException.throwEx(FMResponseCode.filetimeout);
		} finally {
			toClient.close();
		}
		return response("");
	}
}
