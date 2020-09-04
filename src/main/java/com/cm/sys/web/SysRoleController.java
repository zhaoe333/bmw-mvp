package com.cm.sys.web;

import com.cm.common.tree.Node;
import com.cm.common.utils.LogicUtil;
import com.cm.common.web.BaseController;
import com.cm.sys.entity.SysRole;
import com.cm.sys.query.SysRoleQuery;
import com.cm.sys.query.vo.SysRoleVo;
import com.cm.sys.service.SysRoleService;
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
 * @Title: SysRoleController
 * @Description: 系统角色Controller类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Api(tags = {"系统角色管理"})
@Controller
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;


    /**
     * 新增系统角色
     *
     * @param sysRoleVo
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("新增系统角色")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "系统角色code", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "$name,parentId,cnName", required = true),
            @ApiImplicitParam(name = "$description,code", required = false)})
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String addSystemRole(@ApiParam(value = "系统角色信息", required = true) @RequestBody SysRoleVo sysRoleVo,
                                HttpServletRequest request) throws Exception {
        if (!LogicUtil.checkBeanNullAndEmpty(sysRoleVo, "name", "parentId")) {
            paramsError();
        }
        SysRole systemRole = sysRoleService.saveRole(sysRoleVo);
        return response(systemRole.getId());
    }

    /**
     * 修改系统角色
     *
     * @param sysRoleVo
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("修改系统角色")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "系统角色code", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "$id,code", required = true),
            @ApiImplicitParam(name = "$description,name", required = false)})
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateSystemRole(@ApiParam(value = "系统角色信息", required = true) @RequestBody SysRoleVo sysRoleVo,
                                   HttpServletRequest request) throws Exception {
        if (LogicUtil.isNullOrEmpty(sysRoleVo.getId())) {
            paramsError();
        }
        sysRoleService.updateRole(sysRoleVo);
        return response(sysRoleVo.getCode());
    }

    /**
     * 删除系统角色
     *
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("删除系统角色")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success", response = Void.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "$id", required = true)})
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String remove(@ApiParam(value = "系统角色信息", required = true) @RequestBody SysRoleQuery query,
                         HttpServletRequest request) throws Exception {
        if (LogicUtil.isNullOrEmpty(query.getId())) {
            paramsError();
        }
        String res=sysRoleService.deleteRoleById(query.getId());
        /* 保存日志 */
        return response(res);
    }

    /**
     * 系统角色详情
     *
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("系统角色详情")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success", response = SysRoleVo.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "$id", required = true)})
    @RequestMapping(value = "/info", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String info(@ApiParam(value = "系统角色信息", required = true) @RequestBody SysRoleQuery query,
                       HttpServletRequest request) throws Exception {
        if (LogicUtil.isNullOrEmpty(query.getId())) {
            paramsError();
        }
        SysRoleVo sysRoleVo = sysRoleService.getByRoleById(query.getId());
        return response(sysRoleVo);
    }

    /**
     * 查询角色树形
     *
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("查询角色树形")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "系统角色列表", response = Node.class, responseContainer = "list")})
    @ApiImplicitParams({@ApiImplicitParam(name = "$", required = false)})
    @RequestMapping(value = "/query", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String querySystemRole(@ApiParam(value = "系统角色信息", required = true) @RequestBody SysRoleQuery query,
                                  HttpServletRequest request) throws Exception {
        return response(sysRoleService.getRoleTreeByQuery(query));
    }

    /**
     * 分页查询角色
     *
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("查询角色分页")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "系统角色列表", response = SysRole.class, responseContainer = "list")})
    @ApiImplicitParams({@ApiImplicitParam(name = "$pageNo,pageSize", required = false)})
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String pageQuerySystemRole(@ApiParam(value = "系统角色信息", required = true) @RequestBody SysRoleQuery query,
                                  HttpServletRequest request) throws Exception {
        return response(sysRoleService.getRolePageByQuery(query));
    }

    /**
     * 分配角色菜单
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("增加角色菜单")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "结果", response = Void.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "$id", required = true),
            @ApiImplicitParam(name = "$resouresIds", required = false)})
    @RequestMapping(value = "/addMenu", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String addMenu(@ApiParam(value = "查询参数", required = true) @RequestBody SysRoleQuery query,
                          HttpServletRequest request) throws Exception {
        if (!LogicUtil.checkParamsNullAndEmpty(query.getId())||!LogicUtil.checkParamsNullAndEmpty(query.getResouresIds())) {
            paramsError();
        }
        sysRoleService.addRoleRes(query);
        return response(null);
    }
}