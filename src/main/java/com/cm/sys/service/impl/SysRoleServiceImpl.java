package com.cm.sys.service.impl;

import com.cm.common.constant.CommonConstants;
import com.cm.common.exception.FMException;
import com.cm.common.http.FMResponseCode;
import com.cm.common.tree.BaseTree;
import com.cm.common.tree.Node;
import com.cm.common.utils.BeanUtil;
import com.cm.common.utils.UUIDUtils;
import com.cm.sys.entity.*;
import com.cm.sys.mapper.SysResourcesMapper;
import com.cm.sys.mapper.SysRoleMapper;
import com.cm.sys.mapper.SysRoleResourcesMapper;
import com.cm.sys.mapper.SysUserRoleMapper;
import com.cm.sys.query.SysRoleQuery;
import com.cm.sys.query.SysRoleResourcesQuery;
import com.cm.sys.query.SysUserRoleQuery;
import com.cm.sys.query.vo.SysRoleVo;
import com.cm.sys.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: SysRoleServiceImpl
 * @Description: 角色SysRoleServiceImpl实现类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleMapper sysRoleMapper;
	@Resource
	private SysRoleResourcesMapper sysRoleResourcesMapper;

	@Resource
	private SysResourcesMapper sysResourcesMapper;

	@Resource
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	@Transactional
	public SysRole saveRole(SysRoleVo sysRoleVo) throws Exception {
		//验证角色名称是否存在
		SysRoleQuery query = new SysRoleQuery();
		query.setName(sysRoleVo.getName());
		query.setCode(sysRoleVo.getCode());
		List<SysRole> list=sysRoleMapper.selectRoleByQuery(query);
		if(list!=null&&list.size()>0){
			FMException.throwEx(FMResponseCode.propertyUsed, "角色名称已存在");
		}

		SysRole sysRole = new SysRole();
		BeanUtil.copyPropertiesNotNull(sysRoleVo, sysRole);
		String id = UUIDUtils.generateUUID();
		sysRole.setId(id);
		sysRole.setDeleteFlag(CommonConstants.NO_DELETED);
		sysRole.setCreateTime(new Date());
		sysRole.setUpdateTime(new Date());
		//插入角色
		sysRoleMapper.createRole(sysRole);
		return sysRole;
	}

	@Override
	@Transactional
	public String updateRole(SysRoleVo sysRoleVo) throws Exception {
		SysRole sysRole = new SysRole();
		BeanUtil.copyPropertiesNotNull(sysRoleVo, sysRole);
		sysRole.setUpdateTime(new Date());
		sysRoleMapper.updateRole(sysRole);
		return sysRole.getId();
	}

	@Override
	@Transactional
	public String deleteRoleById(String id) throws Exception {
		SysUserRoleQuery sysUserRoleQuery=new SysUserRoleQuery();
		sysUserRoleQuery.setRoleId(id);
		List<SysUserRole> SysUserRoles=sysUserRoleMapper.selectUserRoleByQuery(sysUserRoleQuery);
		if(SysUserRoles.size()>0){
			return "删除失败,有用户正在使用角色";
		}
		SysRole sysRole = new SysRole();
		sysRole.setId(id);
		// 删除角色
		sysRoleMapper.deleteRoleById(sysRole);
		return "删除成功";
	}

	@Override
	public SysRoleVo getByRoleById(String id) throws Exception {
		SysRoleQuery sysRole = new SysRoleQuery();
		sysRole.setId(id);
		List<SysRole> sysRoles = sysRoleMapper.selectRoleByQuery(sysRole);
		if(null!=sysRoles&&sysRoles.size()==1){
			SysRoleVo sysRoleVo=new SysRoleVo();
			BeanUtil.copyPropertiesNotNull(sysRoles.get(0), sysRoleVo);

			List<SysResources> sysResourcessList=sysResourcessList = sysResourcesMapper.getRoleRes(id);
			List SysNodes = new ArrayList();
			if (sysResourcessList != null && sysResourcessList.size() > 0) {
				for (SysResources sysResources : sysResourcessList) {
					SysNode node = new SysNode();
					node.setId(sysResources.getId());
					node.setTitle(sysResources.getCnName());
					node.setParent(sysResources.getParentId());
					node.setValue(sysResources.getId());
					node.setUrl(sysResources.getResUrl());
					node.setIcon(sysResources.getIcon());
					node.setEnName(sysResources.getName());
					node.setSort(sysResources.getSort());
					node.setCnName(sysResources.getCnName());
					SysNodes.add(node);
				}
			}
			BaseTree tree = new BaseTree();
			tree.addNodes(SysNodes);
			List<Node> list=tree.getLeafs();
			List<String> resourcesIds=new ArrayList<String>();
			if(list!=null&&list.size()>0){
				for(Node node:list){
					resourcesIds.add(node.getId());
				}
			}
			sysRoleVo.setResouresIds(resourcesIds);
			return sysRoleVo;
		}else{
			return null;
		}
	}

	@Override
	public List getRoleTreeByQuery(SysRoleQuery query) throws Exception {
		List<SysRole> sysRoles = sysRoleMapper.selectRoleByQuery(query);
		List SysNodes=new ArrayList();
		for (SysRole sysRole:sysRoles){
			SysNode node=new SysNode();
			node.setId(sysRole.getId());
			node.setTitle(sysRole.getCnName());
			node.setParent(sysRole.getParentId());
			node.setValue(sysRole.getId());
			node.setCode(sysRole.getCode());
			node.setDescription(sysRole.getDescription());
			node.setCnName(sysRole.getCnName());
			node.setEnName(sysRole.getName());
			SysNodes.add(node);
		}
		BaseTree tree=new BaseTree();
		tree.addNodes(SysNodes);
		return tree.getRoot().getChildren();
	}

	@Override
	@Transactional
	public void addRoleRes(SysRoleQuery query) throws Exception {
		//删除之前菜单
		SysRoleResourcesQuery sysRoleResourcesQuery=new SysRoleResourcesQuery();
		sysRoleResourcesQuery.setRoleId(query.getId());
		sysRoleResourcesMapper.deleteRoleResourcesByRoleId(sysRoleResourcesQuery);
		//添加角色菜单
		List<String> resourcesList=query.getResouresIds();
		if(resourcesList!=null&&resourcesList.size()>0){
			for (String resId:resourcesList){
				SysRoleResources sysRoleResources=new SysRoleResources();
				sysRoleResources.setRoleId(query.getId());
				sysRoleResources.setResId(resId);
				sysRoleResources.setId(UUIDUtils.generateUUID());
				sysRoleResourcesMapper.createRoleResources(sysRoleResources);
			}
		}

	}



	@Override
	@Transactional(readOnly = true)
	public List<SysRole> getRoleByQuery(SysRoleQuery query) throws Exception {
		List<SysRole> sysRoles = sysRoleMapper.selectRoleByQuery(query);
		return sysRoles;
	}



}