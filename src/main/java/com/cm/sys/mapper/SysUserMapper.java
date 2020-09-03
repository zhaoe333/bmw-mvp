package com.cm.sys.mapper;

import com.cm.sys.entity.SysUser;
import com.cm.sys.query.SysUserQuery;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserMapper
 * @Description: 用户Mapper接口
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public interface SysUserMapper {
	/**
	 * 创建用户
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	void createUser(SysUser sysUser)throws Exception;
	/**
	 * 更新用户
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */

	void updateUser(SysUser sysUser)throws Exception;

	/**
	 * 根据id删除用户
	 * @param SysUser
	 * @return
	 * @throws Exception
	 */
	void deleteUserById(SysUser SysUser)throws Exception;

	/**
	 * 根据查询条件查询用户
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<SysUser> selectUserByQuery(SysUserQuery query)throws Exception;

	/**
	 * 查询用户总数
	 * @param query
	 * @return
	 * @throws Exception
	 */
	Integer selectUserCountQuery(SysUserQuery query) throws Exception;






}