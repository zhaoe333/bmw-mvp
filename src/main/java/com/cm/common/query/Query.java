package com.cm.common.query;

import com.cm.common.constant.DimConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 查询封装父类
 * @author yunlu
 *
 */
@Data
public class Query {
	
	@ApiModelProperty("页码 默认1")
	private int pageNo=1;

	@ApiModelProperty("每页数量 默认10")
	private int pageSize=10;

	@ApiModelProperty("删除标示 0未删除 1已删除")
	private Integer deleteFlag;

	@ApiModelProperty("查询关键字")
	private String keywords;
	@ApiModelProperty("查询时间 开始时间 yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@ApiModelProperty("查询时间 结束时间 yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	@ApiModelProperty("查询时间 某一天 yyyy-MM-dd 00:00:00")
	private Date queryTime;
	@ApiModelProperty("查询时间类型 支持day，week，month 默认day")
	private String timeType= DimConstants.DATE_DAY;
	@ApiModelProperty("只取limitNum的数据")
	private int limitNum;
	/**
	 * 用于mybatis拦截器处理分页
	 */
	private int total;

	/**
	 * 统一计算刷新后更新的总数据。
	 * 当从前端传来的此字段的值大于0时
	 * 返回的数据updateCount将等于总条数-此字段的值
	 */
	@ApiModelProperty("上次统计total的数量")
	private Integer lastTotal;
	
	@ApiModelProperty("0 asc 1 desc 默认1")
	private int orderType =1;
	/**
	 * 用户id
	 */
	private String loginUserId;
	
	private String loginName;
	
	private String loginIP;
	
	private String loginRoleCode;
	
	private List<String> loginRoleCodes;
    /**
     * 微信小程序专用
     */
    private String openId;

	private String sessionKey;
	
	@ApiModelProperty("中英文 1.中文 2.英文")
	private Integer language;

}
