package com.cm.sys.mapper;

import com.cm.sys.entity.SysRoleResources;
import com.cm.sys.query.SysRoleResourcesQuery;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysRoleResourcesMapper
 * @Description: 系统角色菜单mapper接口类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public interface SysRoleResourcesMapper  {
	/**
	 * 添加角色菜单
	 * @param sysRoleResources
	 * @return
	 * @throws Exception
	 */
	void createRoleResources(SysRoleResources sysRoleResources)throws Exception;

	/**
	 * 根据角色id删除菜单
	 * @param query
	 * @return
	 * @throws Exception
	 */
	void deleteRoleResourcesByRoleId(SysRoleResourcesQuery query)throws Exception;
	/**
	 * 查询角色菜单数据
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<SysRoleResources> selectRoleResourcesByQuery(SysRoleResourcesQuery query)throws Exception;

}