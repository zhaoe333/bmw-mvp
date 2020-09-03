package com.cm.common.echarts.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 重构一下这堆东西
 */
public class EChartsVo implements Serializable {

    @ApiModelProperty("echarts相关数据 对应echarts的series")
    private List<EChartsSeries> entitys;
    @ApiModelProperty("x轴描述信息 一般用于折线图 柱状图 其他情况下改值为null")
    private List<String> xnames;
    @ApiModelProperty("描述数据的名称 目前用于雷达图")
    private List<String> dataNames;


    public List<EChartsSeries> getEntitys() {
        return entitys;
    }

    public void setEntitys(List<EChartsSeries> entitys) {
        this.entitys = entitys;
    }

    public List getXnames() {
        return xnames;
    }

    public List<String> getDataNames() {
        return dataNames;
    }

    public EChartsVo() {

    }

    public EChartsVo(EChartsSeries series){
        this.entitys= Arrays.asList(series);
    }

    /**
     * 添加x轴描述信息
     * @param name
     */
    public void addXname(String name){
        if(null==name){
            return;
        }
        if(null==xnames){
            xnames=new ArrayList<>();
        }
        if(!xnames.contains(name)){
            xnames.add(name);
        }
    }

    /**
     * 添加x轴描述信息
     * @param name
     */
    public void addDataName(String name){
        if(null==name){
            return;
        }
        if(null==dataNames){
            dataNames=new ArrayList<>();
        }
        if(!dataNames.contains(name)){
            dataNames.add(name);
        }
    }

    /**
     * 将中文妆化为英文
     */
    public void convertEN(){
        //TODO
    }

    /**
     * 整合数据
     * @param vo
     */
    public void addEntitys(EChartsVo vo){
        entitys.addAll(vo.getEntitys());
    }
    /**
     * 整合数据
     * @param entity
     */
    public void addEntitys(EChartsSeries entity){
        entitys.add(entity);
    }
    /**
     * 整合数据
     * @param entityList
     */
    public void addEntitys(List<EChartsSeries> entityList){
        entitys.addAll(entityList);
    }

    /**
     * 填充xnames
     * 仅限于pie类型转line类型
     * 其他数据结构慎用
     */
    public EChartsVo fillXNames(){
        if(entitys==null){
            return this;
        }
        entitys.forEach(series->series.getData().forEach(data->addXname(data.getName())));
        return this;
    }

}
