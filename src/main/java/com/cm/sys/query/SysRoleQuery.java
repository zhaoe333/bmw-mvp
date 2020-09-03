package com.cm.sys.query;

import com.cm.common.query.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysRoleQuery
 * @Description: 角色SysRoleQuery类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Data
public class SysRoleQuery extends Query {
	@ApiModelProperty("系统角色id")
	private String id;
	@ApiModelProperty("角色名称")
	private String name;
	@ApiModelProperty("角色描述")
	private String description;
	@ApiModelProperty("角色code")
	private String code;
	@ApiModelProperty("角色父code")
	private String parentId;
	@ApiModelProperty("0不返回根节点 1返回根节点")
	private Integer hasParent;
	@ApiModelProperty("角色菜单idd")
	private List<String> resouresIds;
	@ApiModelProperty("中文名称")
	private String cnName;

}