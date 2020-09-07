package com.cm.sys.query;

import com.cm.common.query.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Title: SysUserRoleQuery
 * @Description: 系统用户角色关联QueryVo类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRoleQuery extends Query {
	@ApiModelProperty("系统用户角色关联id")
	private String id;
	@ApiModelProperty("用户id")
	private String userId;
	@ApiModelProperty("角色id")
	private String roleId;

}