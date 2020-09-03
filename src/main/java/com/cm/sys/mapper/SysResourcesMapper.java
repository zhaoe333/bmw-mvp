package com.cm.sys.mapper;

import com.cm.sys.entity.SysResources;
import com.cm.sys.query.SysResourcesQuery;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysResourcesMapper
 * @Description: 系统菜单Mapper接口
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public interface SysResourcesMapper{
	/**
	 * 创建菜单
	 * @param sysResources
	 * @return
	 * @throws Exception
	 */
	void createResources(SysResources sysResources)throws Exception;
	/**
	 * 更新菜单
	 * @param sysResources
	 * @return
	 * @throws Exception
	 */

	void updateResources(SysResources sysResources)throws Exception;

	/**
	 * 根据id删除菜单
	 * @param sysResources
	 * @return
	 * @throws Exception
	 */
	void deleteResourcesById(SysResources sysResources)throws Exception;

	/**
	 * 查询条件菜单数据
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<SysResources> selectResourcesByQuery(SysResourcesQuery query)throws Exception;

	/**
	 * 获取角色菜单
	 * @param roleId
	 * @throws Exception
	 */
	List<SysResources> getRoleRes(String roleId) throws Exception;

}