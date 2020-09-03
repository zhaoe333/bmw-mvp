package com.cm.sys.web;

import com.cm.common.constant.CommonConstants;
import com.cm.common.query.PageFinder;
import com.cm.common.utils.ExcelUtils;
import com.cm.common.utils.LogicUtil;
import com.cm.common.utils.MD5;
import com.cm.common.web.BaseController;
import com.cm.sys.entity.SysUser;
import com.cm.sys.query.SysUserQuery;
import com.cm.sys.query.vo.SysUserRoleVo;
import com.cm.sys.query.vo.SysUserVo;
import com.cm.sys.service.SysResourcesService;
import com.cm.sys.service.SysUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserController
 * @Description: 系统用户Controller类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Api(tags = {"系统用户管理"})
@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController extends BaseController {


    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysResourcesService sysResourcesService;

    /**
     * 新增系统用户
     *
     * @param sysUserVo
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("新增系统用户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "系统用户id", response = String.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "$id,userName,accountName,phone,email,roleCodes,department,positions", required = true),
            @ApiImplicitParam(name = "$description,address,headUrl,wechat,mobilePhone", required = false)})
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String addSystemUser(@ApiParam(value = "系统用户信息", required = true) @RequestBody SysUserVo sysUserVo,
                                HttpServletRequest request) throws Exception {
        SysUser sysUser = sysUserService.saveUser(sysUserVo);
        return response(sysUser.getId());
    }

    /**
     * 修改系统用户
     *
     * @param sysUserVo
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("修改系统用户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "系统用户id", response = String.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "$id", required = true),
            @ApiImplicitParam(name = "$userName,accountName,password,phone,email,personage,loginLanguage", required = false)})
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateSystemUser(@ApiParam(value = "系统用户信息", required = true) @RequestBody SysUserVo sysUserVo,
                                   HttpServletRequest request) throws Exception {
        if (LogicUtil.isNullOrEmpty(sysUserVo.getId())) {
            paramsError();
        }
        sysUserService.updateUser(sysUserVo);
        return response(sysUserVo.getId());
    }


    /**
     * 删除系统用户
     *
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("删除系统用户")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success", response = Void.class)})
    @ApiImplicitParams({@ApiImplicitParam(name = "$id", required = true)})
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String removeUsers(@ApiParam(value = "系统用户信息", required = true) @RequestBody SysUserQuery query,
                              HttpServletRequest request) throws Exception {
        sysUserService.deleteUserById(query.getId());
        return response(null);
    }
    /**
     * 系统用户详情
     *
     * @param query
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("系统用户详情")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success", response = SysUserRoleVo.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "$id", required = true) })
    @RequestMapping(value = "/info", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String info(@ApiParam(value = "系统用户信息", required = true) @RequestBody SysUserQuery query,
                       HttpServletRequest request) throws Exception {
        if (LogicUtil.isNullOrEmpty(query.getId())) {
            paramsError();
        }
        SysUserVo user=sysUserService.getUserAndRole(query);
        return response(user);
    }
    /**
     * 分页查询系统用户列表
     *
     * @param query
     * @param
     * @return
     * @throws Exception
     */
    @ApiOperation("分页查询系统用户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "系统用户列表", response = SysUser.class, responseContainer = "list")})
    @ApiImplicitParams({@ApiImplicitParam(name = "$pageNo,pageSize,keywords", required = false)})
    @RequestMapping(value = "/pageQuery", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String pageQuerySystemUser(@ApiParam(value = "系统用户信息", required = true) @RequestBody SysUserQuery query,
                                      HttpServletRequest request) throws Exception {
        PageFinder<SysUser> pageFinder = sysUserService.getUserPageByQuery(query);
        return response(pageFinder);
    }

    /**
     * 获取用户菜单
     * @param query
     * @param
     * @return
     * @throws Exception
     */
    @ApiOperation("获取用户菜单")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "系统用户列表", response = SysUser.class, responseContainer = "list")})
    @ApiImplicitParams({@ApiImplicitParam(name = "$id", required = true)})
    @RequestMapping(value = "/resources", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String systemUserResources(@ApiParam(value = "系统用户", required = true) @RequestBody SysUserQuery query,
                                      HttpServletRequest request) throws Exception {
        if (LogicUtil.isNullOrEmpty(query.getId())) {
            paramsError();
        }
        SysUserVo sysUserVo = sysUserService.getUserAndRole(query);
        // 获取用户角色
        List sysResources=null;
        List<String> systemRoleIds = sysUserVo.getRoleIds();
        if (systemRoleIds!=null&&systemRoleIds.size()>0){
            String roleId=systemRoleIds.get(0);
            //查询角色菜单
            sysResources=sysResourcesService.getRoleRes(roleId);
        }
        return response(sysResources);
    }
    /**
     * 导出用户Execl
     *
     * @param keywords
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("导出用户Execl")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "下载地址", response = String.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "$keywords", required = false) })
    @RequestMapping(value = "/createExcel", method = RequestMethod.GET)
    @ResponseBody
    public void createExecl(HttpServletResponse response, HttpServletRequest request, String keywords) throws Exception {
        SysUserQuery query=new SysUserQuery();
        query.setKeywords(keywords);
        List<SysUser> sysmUsers = sysUserService.getUserByQuery(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), SysUser.class,sysmUsers);
        ExcelUtils.writeExcel(response,"用户.xls",workbook);

    }
    /**
     * 重置密码
     * @param sysUserVo
     * @throws Exception
     */
    @ApiOperation("重置密码")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回结果", response = String.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "$id", required = true) })
    @RequestMapping(value = "/rePassword", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String rePassword(
            @ApiParam(value = "重置密码", required = true) @RequestBody SysUserVo sysUserVo,
            HttpServletRequest request) throws Exception {
        if (LogicUtil.isNullOrEmpty(sysUserVo.getId())) {
            paramsError();
        }
        sysUserVo.setPassword(CommonConstants.USER_DEFAULT_PASSWORD);
        sysUserService.updatePassword(sysUserVo);
        return response("重置密码成功");
    }
    /**
     * 修改密码
     * @param sysUserVo
     * @throws Exception
     */
    @ApiOperation("修改密码")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回结果", response = String.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "$id,oldPassword,password", required = true) })
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String updatePassword(
            @ApiParam(value = "修改密码", required = true) @RequestBody SysUserVo sysUserVo,
            HttpServletRequest request) throws Exception {
        if (LogicUtil.isNullOrEmpty(sysUserVo.getId(), sysUserVo.getPassword(),sysUserVo.getOldPassword())) {
            paramsError();
        }
        //旧密码验证
        SysUserQuery sysUserQuery=new SysUserQuery();
        sysUserQuery.setPassword(MD5.md5String32(sysUserVo.getOldPassword()));
        sysUserQuery.setId(sysUserVo.getId());
        List<SysUser> sysUserList=sysUserService.getUserByQuery(sysUserQuery);
        if (sysUserList!=null&&sysUserList.size()>0){
            sysUserService.updatePassword(sysUserVo);
        }else{
            return response("旧的密码输入有误");
        }
        return response("修改密码成功");
    }

}