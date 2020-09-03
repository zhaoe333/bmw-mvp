package com.cm.sys.entity;

import com.cm.common.tree.Node;

/**
 * @version V1.0
 * @Title: SysNode
 * @Description: 树节点模型
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
public class SysNode extends Node {
	/**
	 * value
	 */
	private String value;
	/**
	 * 菜单地址
	 */
	private String url;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 菜单中文名
	 */
	private String cnName;
	/**
	 * 菜单英文名
	 */
	private  String enName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * Code
	 */
	private String code;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
