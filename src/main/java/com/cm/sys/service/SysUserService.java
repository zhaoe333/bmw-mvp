package com.cm.sys.service;

import com.cm.common.query.PageFinder;
import com.cm.sys.entity.SysUser;
import com.cm.sys.query.SysUserQuery;
import com.cm.sys.query.vo.SysUserVo;

import java.util.List;


/**
 * @version V1.0
 * @Title: SysUserService
 * @Description: 用户SysUserService接口
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public interface SysUserService {

    /**
     * 增加系统用户
     * @param sysUser
     * @return SysUser
     * @throws Exception
     */
    SysUser saveUser(SysUserVo sysUser) throws Exception;

    /**
     * 修改系统用户
     * @param sysUser
     * @return id
     * @throws Exception
     */
    String updateUser(SysUserVo sysUser) throws Exception;

    /**
     * 修改密码
     * @param sysUser
     * @return id
     * @throws Exception
     */
    String updatePassword(SysUserVo sysUser) throws Exception;

    /**
     * 删除系统用户
     * @param id
     * @throws Exception
     */
    void deleteUserById(String id) throws Exception;

    /**
     * 根据id获取用户
     * @param query
     * @return SysUser
     * @throws Exception
     */
    SysUser getUserById(SysUserQuery query) throws Exception;

    /**
     * 根据查询条件分页查询用户
     * @param query
     * @return pageFinder
     * @throws Exception
     */
    PageFinder<SysUser> getUserPageByQuery(SysUserQuery query) throws Exception;

    /**
     * 根据查询条件查询用户
     * @param query
     * @return pageFinder
     * @throws Exception
     */
    List<SysUser> getUserByQuery(SysUserQuery query) throws Exception;

    /**
     * 获取用户详情和角色id
     * @param query
     * @return SysUser
     * @throws Exception
     */
    SysUserVo getUserAndRole(SysUserQuery query) throws Exception;

}