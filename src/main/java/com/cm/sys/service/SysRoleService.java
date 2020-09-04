package com.cm.sys.service;

import com.cm.common.query.PageFinder;
import com.cm.sys.entity.SysRole;
import com.cm.sys.entity.SysUser;
import com.cm.sys.query.SysRoleQuery;
import com.cm.sys.query.SysUserQuery;
import com.cm.sys.query.vo.SysRoleVo;

import java.util.List;

/**
 * @version V1.0
 * @Title: SystRoleService
 * @Description: 角色SystRoleService接口
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public interface SysRoleService {

	/**
	 * 增加系统角色
	 * @param systemRoleVo
	 * @return SystemRole
	 * @throws Exception
	 */
	SysRole saveRole(SysRoleVo systemRoleVo)throws Exception ;
	
	/**
	 * 修改系统角色
	 * @param systemRoleVo
	 * @return id 
	 * @throws Exception
	 */
	String updateRole(SysRoleVo systemRoleVo)throws Exception ;
	
	/**
	 * 删除系统角色
	 * @param id
	 * @throws Exception
	 */
	String deleteRoleById(String id)throws Exception ;
	
	/**
	 * 获取系统角色和菜单id
	 * @param id
	 * @return SystemRole
	 * @throws Exception
	 */
	SysRoleVo getByRoleById(String id) throws Exception;
	
	/**
	 * 非分页查询系统角色
	 * @param query
	 * @return list
	 * @throws Exception
	 */
	List<SysRole> getRoleByQuery(SysRoleQuery query) throws Exception;


	/**
	 * 查询系统角色tree
	 * @param query
	 * @return list
	 * @throws Exception
	 */
	List getRoleTreeByQuery(SysRoleQuery query) throws Exception;


	/**
	 * 角色添加菜单
	 * @param query
	 * @throws Exception
	 */
	void addRoleRes(SysRoleQuery query) throws Exception;


	/**
	 * 根据查询条件分页查询用户
	 * @param query
	 * @return pageFinder
	 * @throws Exception
	 */
	PageFinder<SysRole> getRolePageByQuery(SysRoleQuery query) throws Exception;




}