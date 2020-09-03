package com.cm.common.echarts.extract;

import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * 处理以下类型数据结构
 *  xnamekey,namekey,valuekey
 * [
 *  {name:1,type:0,value:10},
 *  {name:1,type:1,value:11},
 *  {name:1,type:2,value:12},
 *  {name:2,type:0,value:1},
 *  {name:2,type:1,value:2},
 *  {name:3,type:2,value:3}
 * ]
 */
@Slf4j
public class EChartsCommonExtract extends EChartsExtractWithList {

    private EChartsExtractKey nameKey;

    private EChartsExtractKey valueKey;

    @Override
    public void addNameKey(String nameKey, String name) {
        this.nameKey = new EChartsExtractKey(nameKey,name);
    }

    @Override
    public void addNameKey(String nameKey, Function<Object, String> nameFunc) {
        this.nameKey = new EChartsExtractKey(nameKey,nameFunc);
    }

    @Override
    public void addValueKey(String key, String name, String type) {
        this.valueKey = new EChartsExtractKey(key,name,type);
    }

    @Override
    public <T> void extract(T item, Function<T,Object> expandFunc) {
        EChartsGenericData entry = new EChartsGenericData();
        try {
            entry.setXName(getNameByKey(item,xAxisNameKey));
            entry.setName(getNameByKey(item,nameKey));
            entry.setValue((Number) BeanUtil.get(item,valueKey.getKey()));
            entry.setType(valueKey.getType());
            entry.setId(getIdByKey(item,idKey));
            if(null!=expandFunc){
                entry.setExpandData(expandFunc.apply(item));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        add(entry);
    }
}
