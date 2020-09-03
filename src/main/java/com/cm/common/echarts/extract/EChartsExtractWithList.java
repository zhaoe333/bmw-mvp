package com.cm.common.echarts.extract;

import com.cm.common.echarts.entity.EChartsGenericData;

import java.util.ArrayList;
import java.util.List;

/**
 * 利用list缓存处理的数据
 */
public abstract class EChartsExtractWithList extends EChartsExtract {
    /**
     * 用来缓存数据
     */
    private List<EChartsGenericData> dataList = new ArrayList<>();

    /**
     * 获取清洗后的数据
     * @return
     */
    public List<EChartsGenericData> getDataList(){
        List<EChartsGenericData> result = new ArrayList<>(dataList);
        //读取一次之后 清空缓存数据
        dataList=null;
        return result;
    }

    protected void add(EChartsGenericData data){
        dataList.add(data);
    }
}
