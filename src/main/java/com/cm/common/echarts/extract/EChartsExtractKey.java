package com.cm.common.echarts.extract;

import lombok.Data;

import java.util.function.Function;

/**
 * 用户数据抽取中设置xnameKey和namekey和valuekey
 */
@Data
public class EChartsExtractKey {

    /**用于获取对象中的统计结果数据 字段名称*/
    private String key;
    /**用于描述统计结果数据*/
    private String name;
    /**统计结果数据的类型 目前支持num和rate和times @EChartsBuilderUtil.TYPE*/
    private String type;
    /**
     * 描述信息转化函数 和name冲突 当nameFunc存在时 name无效
     */
    private Function<Object, String> nameFunc;

    public EChartsExtractKey(){

    }

    /**
     * key
     * @param key
     */
    public EChartsExtractKey(String key) {
        this.key = key;
        this.name= key;
    }

    /**
     * key,name
     * @param key
     * @param name
     */
    public EChartsExtractKey(String key, String name) {
        this.key = key;
        if(null==name){
            this.name = key;
        }else{
            this.name=name;
        }
    }

    /**
     * key,name,type
     * @param key
     * @param name
     * @param type
     */
    public EChartsExtractKey(String key, String name, String type) {
        this.key = key;
        if(null==name){
            this.name = key;
        }else{
            this.name=name;
        }
        this.type = type;
    }

    /**
     * key,name
     * @param key
     * @param nameFunc
     */
    public EChartsExtractKey(String key, Function<Object,String> nameFunc) {
        this.key = key;
        this.name=key;
        this.nameFunc = nameFunc;
    }

    /**
     * key,name
     * @param key
     * @param nameFunc
     */
    public EChartsExtractKey(String key, String type, Function<Object,String> nameFunc) {
        this.key = key;
        this.type=type;
        this.name=key;
        this.nameFunc = nameFunc;
    }

}
