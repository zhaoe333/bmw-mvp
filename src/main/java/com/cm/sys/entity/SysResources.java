package com.cm.sys.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version V1.0
 * @Title: SysResources
 * @Description: 系统菜单Entity类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public class SysResources implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private String id;
	@ApiModelProperty("菜单名称")
	private String name;
	@ApiModelProperty("上级菜单id")
	private String parentId;
	@ApiModelProperty("类型 ")
	private String type;
	@ApiModelProperty("访问路径")
	private String resUrl;
	@ApiModelProperty("")
	private String icon;
	@ApiModelProperty("排序")
	private Integer sort;
	@ApiModelProperty("描述")
	private String description;
	@ApiModelProperty("模块名")
	private String module;
	@ApiModelProperty("验证路径")
	private String checkResUrl;
	@ApiModelProperty("创建时间")
	private java.util.Date createTime;
	@ApiModelProperty("最后更新时间")
	private java.util.Date updateTime;
	@ApiModelProperty("中文名称")
	private String cnName;


    public SysResources() {

    }
	public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
	public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
	public String getParentId() {
        return this.parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
	public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
	public String getResUrl() {
        return this.resUrl;
    }
    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }
	public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
	public Integer getSort() {
        return this.sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
	public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
	public String getModule() {
        return this.module;
    }
    public void setModule(String module) {
        this.module = module;
    }
	public String getCheckResUrl() {
		return checkResUrl;
	}
	public void setCheckResUrl(String checkResUrl) {
		this.checkResUrl = checkResUrl;
	}
	public java.util.Date getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
	public java.util.Date getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
    
}