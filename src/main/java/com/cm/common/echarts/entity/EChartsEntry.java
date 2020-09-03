package com.cm.common.echarts.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 最基础的echart数据 name&value value为单个值
 */
@Data
public class EChartsEntry extends EChartsBaseEntry {

    @ApiModelProperty("单个值")
    private Number value;


    public EChartsEntry(Number value){
        this.value=value;
    }

    /**
     * 默认构造器
     */
    public EChartsEntry(){

    }


    @Override
    public void addValue(Number value) {
        this.value=value;
    }
}
