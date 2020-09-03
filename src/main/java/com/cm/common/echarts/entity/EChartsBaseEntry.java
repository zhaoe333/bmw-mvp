package com.cm.common.echarts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * echarts 基础封装类 通用属性 id,名称,类型和扩展参数
 */
@Data
public abstract class EChartsBaseEntry implements Serializable {

    /**对应EChartsEntry*/
    public static final int NORMAL=0;
    /**对应EChartsMultiEntry*/
    public static final int MULTI=1;

    @ApiModelProperty("id")
    private Object id;
    @ApiModelProperty("名称")
    private String name;
    @JsonIgnore
    private Object parentId;
    @ApiModelProperty("子数据")
    private List<EChartsEntry> children;
    @ApiModelProperty("扩展参数,用于浮窗展示,各个接口扩展数据格式可能不一致")
    private Object expandData;

    public abstract void addValue(Number value);

    /**
     * 工厂方法
     * @param entryType
     * @return
     */
    public static EChartsBaseEntry getEntry(int entryType){
        switch (entryType){
            case NORMAL:
                return new EChartsEntry();
            case MULTI:
                return new EChartsMultiEntry();
            default:
                return new EChartsEntry();
        }
    }
}
