package com.cm.sys.service.impl;

import com.cm.common.tree.BaseTree;
import com.cm.common.utils.BeanUtil;
import com.cm.common.utils.UUIDUtils;
import com.cm.sys.entity.SysNode;
import com.cm.sys.entity.SysResources;
import com.cm.sys.mapper.SysResourcesMapper;
import com.cm.sys.query.SysResourcesQuery;
import com.cm.sys.query.vo.SysResourcesVo;
import com.cm.sys.service.SysResourcesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: SysResourcesServiceImpl
 * @Description: 系统菜单Service实现类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Service
public class SysResourcesServiceImpl implements SysResourcesService {

    @Resource
    private SysResourcesMapper sysResourcesMapper;


    @Override
    @Transactional
    public SysResources saveResources(SysResourcesVo sysResourcesVo) throws Exception {
        SysResources sysResources = new SysResources();
        BeanUtil.copyPropertiesNotNull(sysResourcesVo, sysResources);
        sysResources.setCreateTime(new Date());
        sysResources.setUpdateTime(new Date());
        String id = UUIDUtils.generateUUID();
        sysResources.setId(id);
        sysResourcesMapper.createResources(sysResources);
        return sysResources;
    }

    @Override
    @Transactional
    public String updateResources(SysResourcesVo sysResourcesVo) throws Exception {
        SysResources sysResources = new SysResources();
        BeanUtil.copyPropertiesNotNull(sysResourcesVo, sysResources);
        sysResources.setUpdateTime(new Date());
        sysResourcesMapper.updateResources(sysResources);
        return sysResourcesVo.getId();
    }

    @Override
    @Transactional
    public void delteteResourcesById(String id) throws Exception {
        // 删除菜单
        SysResources sysResources = new SysResources();
        sysResources.setId(id);
        sysResourcesMapper.deleteResourcesById(sysResources);
    }

    @Override
    @Transactional(readOnly = true)
    public SysResources getResourcesById(String id) throws Exception {
        SysResourcesQuery sysResources = new SysResourcesQuery();
        sysResources.setId(id);
        List<SysResources> list = sysResourcesMapper.selectResourcesByQuery(sysResources);
        if (null != list && list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysResources> getResourcesByQuery(SysResourcesQuery query) throws Exception {
        List<SysResources> sysResourcess = sysResourcesMapper.selectResourcesByQuery(query);
        return sysResourcess;
    }

    @Override
    public List getResourcesTreeByQuery(SysResourcesQuery query) throws Exception {
        List<SysResources> sysResourcessList = sysResourcesMapper.selectResourcesByQuery(query);
        List SysNodes = new ArrayList();
        if (sysResourcessList != null && sysResourcessList.size() > 0) {
            for (SysResources sysResources : sysResourcessList) {
                SysNode node = new SysNode();
                node.setId(sysResources.getId());
                node.setTitle(sysResources.getCnName());
                node.setParent(sysResources.getParentId());
                node.setValue(sysResources.getId());
                node.setIcon(sysResources.getIcon());
                node.setEnName(sysResources.getName());
                node.setUrl(sysResources.getResUrl());
                node.setSort(sysResources.getSort());
                SysNodes.add(node);
            }
        }
        BaseTree tree = new BaseTree();
        tree.addNodes(SysNodes);
        return tree.getRoot().getChildren();
    }

    @Override
    public List getRoleRes(String roleId) throws Exception {
        List<SysResources> sysResourcessList=null;
        if("admin".equals(roleId)){
            sysResourcessList=sysResourcesMapper.selectResourcesByQuery(new SysResourcesQuery());
        }else{
            sysResourcessList = sysResourcesMapper.getRoleRes(roleId);
        }
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
                SysNodes.add(node);
            }
        }
        BaseTree tree = new BaseTree();
        tree.addNodes(SysNodes);
        return tree.getRoot().getChildren();
    }
}