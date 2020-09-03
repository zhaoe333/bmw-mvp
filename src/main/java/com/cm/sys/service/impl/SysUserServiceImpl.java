package com.cm.sys.service.impl;

import com.cm.common.constant.CommonConstants;
import com.cm.common.exception.FMException;
import com.cm.common.http.FMResponseCode;
import com.cm.common.query.PageFinder;
import com.cm.common.utils.BeanUtil;
import com.cm.common.utils.LogicUtil;
import com.cm.common.utils.MD5;
import com.cm.common.utils.UUIDUtils;
import com.cm.sys.entity.SysUser;
import com.cm.sys.entity.SysUserRole;
import com.cm.sys.mapper.SysRoleResourcesMapper;
import com.cm.sys.mapper.SysUserMapper;
import com.cm.sys.mapper.SysUserRoleMapper;
import com.cm.sys.query.SysRoleResourcesQuery;
import com.cm.sys.query.SysUserQuery;
import com.cm.sys.query.SysUserRoleQuery;
import com.cm.sys.query.vo.SysUserVo;
import com.cm.sys.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserServiceImpl
 * @Description: 用户SysUserServiceImpl实现类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
 @Service
public class SysUserServiceImpl implements SysUserService {
	
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;
	@Resource
	private SysRoleResourcesMapper sysRoleResourcesMapper;

	@Override
	@Transactional
	public SysUser saveUser(SysUserVo sysUserVo)throws Exception {

        //判断账号是否存在
		SysUserQuery query = new SysUserQuery();
		query.setAccountName(sysUserVo.getAccountName());
		List<SysUser> list=sysUserMapper.selectUserByQuery(query);
        if(list!=null&&list.size()>0){
			FMException.throwEx(FMResponseCode.propertyUsed,"用户账号已存在");
		}

		SysUser sysUser = new SysUser();
		BeanUtil.copyPropertiesNotNull(sysUserVo,sysUser);
		//添加属性值
		sysUser.setCreateTime(new Date());
		sysUser.setUpdateTime(new Date());
		sysUser.setDeleteFlag(CommonConstants.NO_DELETED);
		sysUser.setStatus(CommonConstants.NO_FREEZE);
		//默认密码
		sysUser.setPassword(MD5.md5String32(CommonConstants.USER_DEFAULT_PASSWORD));
		String id = UUIDUtils.generateUUID();
		sysUser.setId(id);
		//保存用户
		sysUserMapper.createUser(sysUser);
		//用户角色id
		List<String> roleIds=sysUserVo.getRoleIds();
		if(roleIds!=null&&roleIds.size()>0){
			for (String roleId:roleIds){
				SysUserRole userRole=new SysUserRole();
				String userRoleId = UUIDUtils.generateUUID();
				userRole.setId(userRoleId);
				userRole.setRoleId(roleId);
				userRole.setUserId(id);
				sysUserRoleMapper.createRoleUser(userRole);
			}
		}
		return sysUser;

	}
	
	@Override
	@Transactional
	public String updateUser(SysUserVo sysUserVo)throws Exception {
		SysUser sysUser = new SysUser();
		BeanUtil.copyPropertiesNotNull(sysUserVo,sysUser);
		sysUser.setUpdateTime(new Date());
		if(LogicUtil.isNotNullAndEmpty(sysUserVo.getPassword())){
			sysUser.setPassword(MD5.md5String32(sysUserVo.getPassword()));
		}
		if(!"1".equals(sysUserVo.getPersonage())){
			//先删除已有角色
			SysUserRoleQuery sysUserRoleQuery=new SysUserRoleQuery();
			sysUserRoleQuery.setUserId(sysUserVo.getId());
			sysUserRoleMapper.deleteUserRoleByUserId(sysUserRoleQuery);
			//用户角色id
			List<String> roleIds=sysUserVo.getRoleIds();
			if(roleIds!=null&&roleIds.size()>0){
				for (String roleId:roleIds){
					SysUserRole userRole=new SysUserRole();
					String userRoleId = UUIDUtils.generateUUID();
					userRole.setId(userRoleId);
					userRole.setRoleId(roleId);
					userRole.setUserId(sysUserVo.getId());
					sysUserRoleMapper.createRoleUser(userRole);
				}
			}
		}
		sysUserMapper.updateUser(sysUser);
		return sysUser.getId();
	}
	@Override
	@Transactional
	public String updatePassword(SysUserVo sysUserVo)throws Exception {
		SysUser sysUser = new SysUser();
		BeanUtil.copyPropertiesNotNull(sysUserVo,sysUser);
		sysUser.setUpdateTime(new Date());
		if(LogicUtil.isNotNullAndEmpty(sysUserVo.getPassword())){
			sysUser.setPassword(MD5.md5String32(sysUserVo.getPassword()));
		}
		sysUserMapper.updateUser(sysUser);
		return sysUser.getId();
	}
	@Override
	@Transactional
	public void deleteUserById(String id)throws Exception {
		SysUser SysUser = new SysUser();
		SysUser.setId(id);
		//删除用户
		sysUserMapper.deleteUserById(SysUser);

        //查询用户角色
		SysUserRoleQuery userRoleQuery=new SysUserRoleQuery();
		userRoleQuery.setUserId(id);
		List<SysUserRole> listUserRoles=sysUserRoleMapper.selectUserRoleByQuery(userRoleQuery);

        //删除用户角色
		sysUserRoleMapper.deleteUserRoleByUserId(userRoleQuery);
		//删除角色菜单
		SysRoleResourcesQuery sysRoleResourcesQuery=new SysRoleResourcesQuery();
		for (SysUserRole sysUserRole:listUserRoles){
			sysRoleResourcesQuery.setRoleId(sysUserRole.getRoleId());
			sysRoleResourcesMapper.deleteRoleResourcesByRoleId(sysRoleResourcesQuery);
		}
	}

	@Override
	public SysUser getUserById(SysUserQuery query) throws Exception {
		List<SysUser> sysUsers = sysUserMapper.selectUserByQuery(query);
		if(null!=sysUsers&&sysUsers.size()==1){
			return sysUsers.get(0);
		}else{
			return null;
		}
	}


	@Override
	@Transactional(readOnly=true)
	public PageFinder<SysUser> getUserPageByQuery(SysUserQuery query) throws Exception{
		query.setFirstNo((query.getPageNo()-1)*query.getPageSize());
		Integer count = sysUserMapper.selectUserCountQuery(query);
		List<SysUser> SysUsers = sysUserMapper.selectUserByQuery(query);
		PageFinder<SysUser> pageFinder = new PageFinder<>(query.getPageNo(), query.getPageSize(), count, SysUsers);
		return pageFinder;
	}

	@Override
	@Transactional(readOnly=true)
	public List<SysUser> getUserByQuery(SysUserQuery query) throws Exception{
		List<SysUser> SysUsers = sysUserMapper.selectUserByQuery(query);
		return SysUsers;
	}

	public SysUserVo getUserAndRole(SysUserQuery query) throws Exception{
		List<SysUser> sysUsers = sysUserMapper.selectUserByQuery(query);
		if(null!=sysUsers&&sysUsers.size()==1){
			SysUser user=sysUsers.get(0);
			SysUserVo sysUserVo=new SysUserVo();
			BeanUtil.copyPropertiesNotNull(user,sysUserVo);
			//查询角色
			SysUserRoleQuery sysUserRoleQuery=new SysUserRoleQuery();
			sysUserRoleQuery.setUserId(user.getId());
			List<SysUserRole> SysUserRoles=sysUserRoleMapper.selectUserRoleByQuery(sysUserRoleQuery);
			List userRoleIds=new ArrayList();
			if(SysUserRoles!=null&&SysUserRoles.size()>0){
				for (SysUserRole sysUserRole: SysUserRoles){
					userRoleIds.add(sysUserRole.getRoleId());
				}
			}
			sysUserVo.setRoleIds(userRoleIds);
			return sysUserVo;
		}else{
			return null;
		}
	}
	
}