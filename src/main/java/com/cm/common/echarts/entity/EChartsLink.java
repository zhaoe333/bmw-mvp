package com.cm.common.echarts.entity;

import lombok.Data;

/**
 * 适用于桑基图
 */
@Data
public class EChartsLink {
    /**
     * 源name 对应entry中的name
     */
    private String source;
    /**
     * 目标name 对应entry中的name
     */
    private String target;
    /**
     * 值
     */
    private Number value;
}
