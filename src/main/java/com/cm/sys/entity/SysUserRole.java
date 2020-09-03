package com.cm.sys.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version V1.0
 * @Title: SysUserRole
 * @Description: 系统用户角色关联Entity类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public class SysUserRole implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private String id;
	@ApiModelProperty("用户id")
	private String userId;
	@ApiModelProperty("角色id")
	private String roleId;

    public SysUserRole() {

    }
	public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
	public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}