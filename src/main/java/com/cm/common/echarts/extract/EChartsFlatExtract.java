package com.cm.common.echarts.extract;

import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 负责扁平化数据的处理
 * [
 *  {name:2018-01-01,userTotalNum:0,userNewNum:10,loyaltyNum:15},
 *  {name:2018-01-02,userTotalNum:0,userNewNum:10,loyaltyNum:15},
 *  {name:2018-01-03,userTotalNum:0,userNewNum:10,loyaltyNum:15},
 * ]
 */
@Slf4j
public class EChartsFlatExtract extends EChartsExtractWithList {

    private List<EChartsExtractKey> valueKeyList = new ArrayList<>();

    @Override
    public void addNameKey(String nameKey, String name) {
        log.warn("this method addNameKey is useless in EChartsFlatExtract.class!");
    }

    @Override
    public void addNameKey(String nameKey, Function<Object, String> nameFunc) {
        log.warn("this method addNameKey is useless in EChartsFlatExtract.class!");
    }

    @Override
    public void addValueKey(String key, String name, String type) {
        this.valueKeyList.add(new EChartsExtractKey(key,name,type));
    }

    @Override
    public <T> void extract(T item, Function<T, Object> expandFunc) {
        valueKeyList.forEach(valueKey-> {
            EChartsGenericData entry = new EChartsGenericData();
            try {
                entry.setXName(getNameByKey(item, xAxisNameKey));
                entry.setName(valueKey.getName());
                entry.setValue((Number) BeanUtil.get(item, valueKey.getKey()));
                entry.setType(valueKey.getType());
                entry.setId(getIdByKey(item, idKey));
                if (null != expandFunc) {
                    entry.setExpandData(expandFunc.apply(item));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            add(entry);
        });
    }
}
