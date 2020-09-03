package com.cm.datalog.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户访问页面的记录
 */
@Data
public class PageLog {

    @ApiModelProperty("小程序对应的页面路径")
    private String page;

    @ApiModelProperty("记录从哪个用户分享的连接进入")
    private String shareUserId;

    @ApiModelProperty("场景值 对应微信场景值")
    private Integer scene;

    @ApiModelProperty("行为类型 0进入页 1退出页 2正常访问页面 3正常访问二级页面 4访问图片或者视频")
    private int activeType;

    @ApiModelProperty("用户在页面的停留时长 单位毫秒 仅在activeType>1的情况下有值")
    private int period;

    @ApiModelProperty("二级页面的标记 index 推荐1，最热2，最新3。activity  活动列表list 我的活动mime")
    private String subPageKey;

    @ApiModelProperty("访问图片和视频的所属文章id 仅在activeType=4的情况下有值")
    private String articleId;
}
