package com.cm.common.echarts.extract;

import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.utils.BeanUtil;

import java.util.function.Function;

/**
 * 单个数据抽取处理 最后输出汇总数据 该抽取主要涉及了计算方面,后续可以继续扩展
 * 负责累计数据的计算
 */
public class EChartsCountSumExtract extends EChartsExtractWithMap {

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


    /**
     * 抽取数据
     * @param item
     * @param expandFunc
     * @param <T>
     * @return
     */
    @Override
    public <T> void extract(T item, Function<T, Object> expandFunc) {
        try{
            Object id = BeanUtil.get(item,idKey);
            //累加数据统计方法
            Number value = (Number) BeanUtil.get(item,valueKey.getKey());
            if(containsKey(id)){
                //TODO 注意 此处暂时没有处理double类型的数据
                get(id).setValue(get(id).getValue().intValue()+value.intValue());
            }else{
                EChartsGenericData entry = new EChartsGenericData();
                entry.setXName(getNameByKey(item,xAxisNameKey));
                entry.setName(getNameByKey(item,nameKey));
                entry.setId(getIdByKey(item,idKey));
                entry.setValue(value);
                entry.setType(valueKey.getType());
                if(null!=expandFunc){
                    entry.setExpandData(expandFunc.apply(item));
                }
                put(id,entry);
            }
        }catch (Exception e) {
            //TODO 此处异常处理 需要优化
            e.printStackTrace();
        }
    }
}
