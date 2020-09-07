package com.cm.sys.query;

import com.cm.common.query.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Title: SysResourcesQuery
 * @Description: 系统菜单QueryVo类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Data
public class SysResourcesQuery extends Query {
	@ApiModelProperty("系统菜单id")
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
	@ApiModelProperty("中文名称")
	private String cnName;
	@ApiModelProperty("0返回同级结构 1返回树结构")
	private int noChild;
	@ApiModelProperty("角色Id")
	private String roleId;

}