package com.cm.sys.web;

import com.cm.common.exception.FMException;
import com.cm.common.http.FMResponseCode;
import com.cm.common.utils.LogicUtil;
import com.cm.common.utils.MD5;
import com.cm.common.utils.TokenUtil;
import com.cm.common.web.BaseController;
import com.cm.sys.query.SysUserQuery;
import com.cm.sys.query.vo.SysUserVo;
import com.cm.sys.service.SysResourcesService;
import com.cm.sys.service.SysRoleService;
import com.cm.sys.service.SysUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Title: SysUserController
 * @Description: 系统用户登陆Controller类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Api(tags = {"系统用户登陆"})
@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysLoginController extends BaseController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysResourcesService sysResourcesService;
    @Resource
    private TokenUtil tokenUtil;

    /**
     * 系统用户登陆
     * @param sysUserQuery
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("系统用户登录")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "系统用户登陆token", response = String.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "$accountName,password", required = true) })
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String loginSystemUser(
            @ApiParam(value = "系统用户信息", required = true) @RequestBody SysUserQuery sysUserQuery,
            HttpServletRequest request) throws Exception {
        //参数验证
        if (!LogicUtil.checkBeanNullAndEmpty(sysUserQuery, "accountName", "password")) {
            paramsError();
        }
        //账号密码验证
        sysUserQuery.setPassword(MD5.md5String32(sysUserQuery.getPassword()));
        SysUserVo sysUser=sysUserService.getUserAndRole(sysUserQuery);

        if (sysUser!=null&& LogicUtil.isNotNullAndEmpty(sysUser.getId())){
            //定义返回结果
            Map<String, Object> responseMap = new HashMap<>();

            // 创建Token
            String token = tokenUtil.create(TokenUtil.TOKEN_PRE_PC, sysUser.getId(), sysUser.getAccountName());

            responseMap.put("token", token);
            responseMap.put("user", sysUser);
            sysUserQuery.setLoginUserId(sysUser.getId());
            sysUserQuery.setLoginName(sysUser.getAccountName());
            sysUserQuery.setLoginIP(request.getRemoteAddr());
            return response(responseMap);

        }else{
            FMException.throwEx(FMResponseCode.passworderror);
            return response(null);
        }
    }

    /**
     * 用户登出
     * @param sysUserVo
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("用户登出")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Void.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "$", required = false) })
    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    @ResponseBody
    public String logoutSystemUser(@ApiParam(value = "系统用户信息", required = false) @RequestBody SysUserVo sysUserVo,
                                   HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        if (LogicUtil.isNullOrEmpty(token)) {
            FMException.throwEx(FMResponseCode.tokenerror);
        } else {
            tokenUtil.checkToken(token);
            tokenUtil.remove(token);
        }
        return response(null);
    }

}