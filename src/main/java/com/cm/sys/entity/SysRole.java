package com.cm.sys.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version V1.0
 * @Title: SysRole
 * @Description: 角色Entity类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public class SysRole implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private String id;
	@ApiModelProperty("角色名称")
	private String name;
	@ApiModelProperty("角色描述")
	private String description;
	@ApiModelProperty("角色code")
	private String code;
	@ApiModelProperty("角色上级code")
	private String parentId;
	@ApiModelProperty("0未删除 1已删除")
	private Integer deleteFlag;
	@ApiModelProperty("创建时间")
	private java.util.Date createTime;
	@ApiModelProperty("最后更新时间")
	private java.util.Date updateTime;
    @ApiModelProperty("中文名称")
    private String cnName;


    public SysRole() {

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
	public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
	public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
	public Integer getDeleteFlag() {
        return this.deleteFlag;
    }
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
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
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public String getCnName() {
        return cnName;
    }
    public void setCnName(String cnName) {
        this.cnName = cnName;
    }
}