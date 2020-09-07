package com.cm.sys.query.vo;

import com.cm.common.query.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @version V1.0
 * @Title: SysRoleVo
 * @Description: 角色SysRoleVo类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Data
public class SysRoleVo extends Query implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private String id;
	@ApiModelProperty("角色名称")
	private String name;
	@ApiModelProperty("角色描述")
	private String description;
	@ApiModelProperty("角色代码")
	private String code;
	@ApiModelProperty("父类id")
	private String parentId;
    @ApiModelProperty("角色菜单idd")
    private List<String> resouresIds;
    @ApiModelProperty("中文名称")
    private String cnName;

    public SysRoleVo() {

    }
}