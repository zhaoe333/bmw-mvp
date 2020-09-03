package com.cm.sys.entity;

import io.swagger.annotations.ApiModelProperty;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysUser
 * @Description: 用户Entity类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public class SysUser implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private String id;

	@Excel(name = "用户昵称")
	@ApiModelProperty("用户昵称")
	private String userName;

	@Excel(name = "账号")
	@ApiModelProperty("账号")
	private String accountName;


	@ApiModelProperty("密码")
	private String password;

	@Excel(name = "描述")
	@ApiModelProperty("描述")
	private String description;

	@Excel(name = "创建时间")
	@ApiModelProperty("创建时间")
	private java.util.Date createTime;

	@Excel(name = "移动电话")
	@ApiModelProperty("移动电话")
	private String phone;


	@ApiModelProperty("逻辑删除状态0:存在1:停用")
	private Integer deleteFlag;

	@Excel(name = "邮箱")
	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("地址")
	private String address;
	@ApiModelProperty("最后更新时间")
	private java.util.Date updateTime;
	@ApiModelProperty("用户角色列表")
	private List<SysRole> roles;

	@ApiModelProperty("头像地址")
	private String headUrl;
	@ApiModelProperty("用户状态 0可用 1冻结")
	private Integer status;
	@ApiModelProperty("微信号")
	private String wechat;
	@ApiModelProperty("移动电话")
	private String mobilePhone;

	@ApiModelProperty("登陆语言：Chinese、English")
	private String loginLanguage;


	public SysUser() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}


	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getLoginLanguage() {
		return loginLanguage;
	}

	public void setLoginLanguage(String loginLanguage) {
		this.loginLanguage = loginLanguage;
	}
}