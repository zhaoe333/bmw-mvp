package com.cm.sys.mapper;

import com.cm.sys.entity.SysRole;
import com.cm.sys.query.SysRoleQuery;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysRoleMapper
 * @Description: 系统角色Mapper接口
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public interface SysRoleMapper  {
	/**
	 * 创建角色
	 * @param sysRole
	 * @return
	 * @throws Exception
	 */
	void createRole(SysRole sysRole)throws Exception;
	/**
	 * 更新角色
	 * @param sysRole
	 * @return
	 * @throws Exception
	 */

	void updateRole(SysRole sysRole)throws Exception;

	/**
	 * 根据id删除角色
	 * @param sysRole
	 * @return
	 * @throws Exception
	 */
	void deleteRoleById(SysRole sysRole)throws Exception;



	/**
	 * 查询数据
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<SysRole> selectRoleByQuery(SysRoleQuery query)throws Exception;





}