package com.cm.sys.query;
import com.cm.common.query.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Title: SysRoleResourcesQuery
 * @Description: 系统角色菜单关联QueryVo类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleResourcesQuery extends Query {
	@ApiModelProperty("系统角色权限关联id")
	private String id;
	@ApiModelProperty("角色Id")
	private String roleId;
	@ApiModelProperty("开始记录数")
	private Integer firstNo;
	@ApiModelProperty("菜单id")
	private String resId;

}