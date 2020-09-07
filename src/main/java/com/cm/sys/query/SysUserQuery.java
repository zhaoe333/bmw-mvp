package com.cm.sys.query;

import com.cm.common.query.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Title: SysUserQuery
 * @Description: 用户SysUserQuery类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Data
public class SysUserQuery extends Query {
	@ApiModelProperty("系统用户id")
	private String id;
	@ApiModelProperty("用户昵称")
	private String userName;
	@ApiModelProperty("账号")
	private String accountName;
	@ApiModelProperty("密码")
	private String password;
	@ApiModelProperty("描述")
	private String description;
	@ApiModelProperty("移动电话")
	private String phone;
	@ApiModelProperty("邮箱")
	private String email;
	@ApiModelProperty("地址")
	private String address;
	@ApiModelProperty("头像地址")
	private String headUrl;
	@ApiModelProperty("状态 0 可用 1 冻结")
	private Integer status;
	@ApiModelProperty("文件名")
	private String fileName;
	@ApiModelProperty("微信号")
	private String wechat;
	@ApiModelProperty("移动电话")
	private String mobilePhone;
	@ApiModelProperty("开始记录数")
	private Integer firstNo;
	@ApiModelProperty("模糊搜索key")
	private String keywords;

}