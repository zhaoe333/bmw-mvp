package com.cm.sys.query.vo;

import com.cm.common.query.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version V1.0
 * @Title: SysRoleVo
 * @Description: 系统角色菜单关联Vo类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Data
public class SysRoleResourcesVo extends Query implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private String id;
	@ApiModelProperty("菜单id")
	private String resId;
	@ApiModelProperty("角色id")
	private String roleId;

    public SysRoleResourcesVo() {

    }
}