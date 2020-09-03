package com.cm.sys.service;

import com.cm.sys.entity.SysResources;
import com.cm.sys.query.SysResourcesQuery;
import com.cm.sys.query.vo.SysResourcesVo;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysResourcesService
 * @Description: 系统菜单Service接口类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public interface SysResourcesService {

	/**
	 * 增加系统菜单
	 * @param sysResourcesVo
	 * @return SystemResources
	 * @throws Exception
	 */
	SysResources saveResources(SysResourcesVo sysResourcesVo)throws Exception ;
	
	/**
	 * 修改系统菜单
	 * @param sysResourcesVo
	 * @return id 
	 * @throws Exception
	 */
	String updateResources(SysResourcesVo sysResourcesVo)throws Exception ;
	
	/**
	 * 删除系统菜单
	 * @param id
	 * @throws Exception
	 */
	void delteteResourcesById(String id)throws Exception ;
	
	/**
	 * 获取系统菜单
	 * @param id
	 * @return SystemResources
	 * @throws Exception
	 */
	SysResources getResourcesById(String id) throws Exception;

	
	/**
	 * 非分页查询系统菜单
	 * @param query
	 * @return list
	 * @throws Exception
	 */
	List<SysResources> getResourcesByQuery(SysResourcesQuery query) throws Exception;

	/**
	 * 查询系统菜单tree
	 * @param query
	 * @return list
	 * @throws Exception
	 */
	List getResourcesTreeByQuery(SysResourcesQuery query) throws Exception;

	/**
	 * 查询角色菜单
	 * @param roleId
	 * @return list
	 * @throws Exception
	 */

	List getRoleRes(String roleId) throws Exception;

}