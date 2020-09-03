package com.cm.sys.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version V1.0
 * @Title: SysRoleResources
 * @Description: 系统角色菜单关联Entity类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public class SysRoleResources implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private String id;
	@ApiModelProperty("菜单id")
	private String resId;
	@ApiModelProperty("角色id")
	private String roleId;

    public SysRoleResources() {

    }
	public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
	public String getResId() {
        return this.resId;
    }
    public void setResId(String resId) {
        this.resId = resId;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}