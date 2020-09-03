package com.cm.common.echarts.extract;

import com.cm.common.echarts.entity.EChartsGenericData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 利用mao缓存处理的数据
 */
public abstract class EChartsExtractWithMap extends EChartsExtract {
    /**
     * 缓存计算中的数据
     */
    private Map<Object, EChartsGenericData> dataMap = new HashMap<>();

    /**
     * 获取清洗后的数据
     * @return
     */
    public List<EChartsGenericData> getDataList(){
        ArrayList<EChartsGenericData> dataList = new ArrayList<>(dataMap.values());
        //清空数据
        dataMap = new HashMap<>();
        return dataList;
    }

    protected void put(Object key, EChartsGenericData data){
        dataMap.put(key, data);
    }

    protected EChartsGenericData get(Object key){
        return dataMap.get(key);
    }

    protected boolean containsKey(Object key){
        return dataMap.containsKey(key);
    }
}
