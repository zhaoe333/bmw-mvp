package com.cm.common.echarts.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 报表数据series实体类 一般用于折线图或者圆柱图
 */
@Data
public class EChartsSeries implements Serializable {

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("数据类型，支持num和rate")
    private String type;
    @ApiModelProperty("数据")
    private List<EChartsBaseEntry> data;
    @ApiModelProperty("专用于桑基图等关联图")
    private List<EChartsLink> links;

    public void addData(EChartsBaseEntry entry){
        if(null==data){
            data = new ArrayList<>();
        }
        data.add(entry);
    }

}

