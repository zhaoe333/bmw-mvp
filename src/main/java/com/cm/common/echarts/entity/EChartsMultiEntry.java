package com.cm.common.echarts.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EChartsMultiEntry extends EChartsBaseEntry {

    @ApiModelProperty("多个值,一般用于散点图,地图之类")
    private List<Number> value = new ArrayList<>();


    @Override
    public void addValue(Number value) {
        this.value.add(value);
    }
}
