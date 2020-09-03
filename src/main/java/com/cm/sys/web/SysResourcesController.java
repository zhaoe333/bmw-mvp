package com.cm.sys.web;

import com.cm.common.tree.Node;
import com.cm.common.utils.LogicUtil;
import com.cm.common.web.BaseController;
import com.cm.sys.entity.SysResources;
import com.cm.sys.query.SysResourcesQuery;
import com.cm.sys.query.vo.SysResourcesVo;
import com.cm.sys.service.SysResourcesService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @version V1.0
 * @Title: SysUserController
 * @Description: 系统菜单Controller类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Api(tags={"系统菜单管理"})
@Controller
@RequestMapping("/sys/resources")
@Slf4j
public class SysResourcesController extends BaseController {

	@Resource
	private SysResourcesService sysResourcesService;

	/**
	 * 新增系统菜单
	 * @param sysResourcesVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("新增系统菜单")
	@ApiResponses(value ={@ApiResponse(code = 200, message = "系统菜单id", response = String.class)})
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "$name,cnName,parentId,resUrl,checkResUrl", required = true),
			@ApiImplicitParam(name = "$type,icon,sort,description,module", required = false) })
	@RequestMapping(value="/add",method= RequestMethod.POST,consumes="application/json;charset=UTF-8")
	@ResponseBody
	public String addSystemResources(@ApiParam(value = "系统菜单信息", required = true)@RequestBody SysResourcesVo sysResourcesVo,
			HttpServletRequest request) throws Exception{
		if(!LogicUtil.checkBeanNullAndEmpty(sysResourcesVo,"name","resUrl")){
			paramsError();
		}
		SysResources sysResources=sysResourcesService.saveResources(sysResourcesVo);
		return response(sysResources.getId());
	}
	
	/**
	 * 修改系统菜单
	 * 
	 * @param sysResourcesVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("修改系统菜单")
	@ApiResponses(value ={@ApiResponse(code = 200, message = "系统菜单id", response = String.class)})
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "$id", required = true),
			@ApiImplicitParam(name = "$name,cnName,parentId,resUrl,checkResUrl,type,icon,sort,description,module", required = false) })
	@RequestMapping(value="/update",method= RequestMethod.POST,consumes="application/json;charset=UTF-8")
	@ResponseBody
	public String updateSystemResources(@ApiParam(value = "系统菜单信息", required = true)@RequestBody SysResourcesVo sysResourcesVo,
			HttpServletRequest request) throws Exception {
		if(LogicUtil.isNullOrEmpty(sysResourcesVo.getId())){
			paramsError();
		}
		sysResourcesService.updateResources(sysResourcesVo);
		return response(sysResourcesVo.getId());
	}
	
	/**
	 * 删除系统菜单
	 * 
	 * @param query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("删除系统菜单")
	@ApiResponses(value ={@ApiResponse(code = 200, message = "success", response = Void.class)})
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "$id", required = true)})
	@RequestMapping(value="/delete",method= RequestMethod.POST,consumes="application/json;charset=UTF-8")
	@ResponseBody
	public String remove(@ApiParam(value = "系统菜单信息", required = true)@RequestBody SysResourcesQuery query,
			HttpServletRequest request) throws Exception {
		if(LogicUtil.isNullOrEmpty(query.getId())){
			paramsError();
		}
		sysResourcesService.delteteResourcesById(query.getId());
		return response(null);
	}

	/**
	 * 查询系统菜单列表树形
	 * @param query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("查询系统菜单树形")
	@ApiResponses(value ={@ApiResponse(code = 200, message = "系统菜单列表", response = Node.class,responseContainer = "list")})
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "$", required = false)})
	@RequestMapping(value="/query",method= RequestMethod.POST,consumes="application/json;charset=UTF-8")
	@ResponseBody
	public String querySystemResources(@ApiParam(value = "系统菜单信息", required = true)@RequestBody SysResourcesQuery query,
			HttpServletRequest request)throws Exception {
		return response(sysResourcesService.getResourcesTreeByQuery(query));
	}
}