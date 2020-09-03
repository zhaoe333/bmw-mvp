package com.cm.sys.mapper;

import com.cm.sys.entity.SysUserRole;
import com.cm.sys.query.SysUserRoleQuery;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserRoleMapper
 * @Description: 系统用户角色关联mapper接口类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public interface SysUserRoleMapper{
	/**
	 * 添加用户角色
	 * @param sysUserRole
	 * @return
	 * @throws Exception
	 */
	void createRoleUser(SysUserRole sysUserRole)throws Exception;

	/**
	 * 根据用户id删除角色
	 * @param query
	 * @return
	 * @throws Exception
	 */
	void deleteUserRoleByUserId(SysUserRoleQuery query)throws Exception;
	/**
	 * 查询用户角色数据
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<SysUserRole> selectUserRoleByQuery(SysUserRoleQuery query)throws Exception;

	
}