package com.cm.common.echarts.entity;

import lombok.Data;

/**
 * echarts通用数据 将所有数据结构抽取为该数据结构之后再进行转化
 */
@Data
public class EChartsGenericData{

    /**
     * 数据的唯一id
     */
    private Object id;
    /**
     * 用于折线图或者柱状图 xAsis对应的data
     */
    private String xName;
    /**
     * 基础数据的name
     */
    private String name;
    /**
     * 基础数据的value
     */
    private Number value;
    /**
     * 指定的数据类型 和name绑定关系
     */
    private String type;
    /**
     * 扩展数据 其他数据均放置于扩展数据中
     */
    private Object ExpandData;
}
