package com.cm.sys.query.vo;

import com.cm.common.query.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version V1.0
 * @Title: SysUserRoleVo
 * @Description: 系统用户角色关联Vo类
 * @author: cuijie
 * @date: 2018/12/69:48 AM
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRoleVo extends Query implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键")
	private String id;
	@ApiModelProperty("用户ID")
	private String userId;
	@ApiModelProperty("角色code")
	private String roleCode;

    public SysUserRoleVo() {

    }
}