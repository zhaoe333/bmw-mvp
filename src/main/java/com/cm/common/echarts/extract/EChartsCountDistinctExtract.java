package com.cm.common.echarts.extract;

import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.utils.BeanUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * 去重数据统计 例如用户数统计
 */
public class EChartsCountDistinctExtract extends EChartsExtractWithMap {

    private EChartsExtractKey nameKey;

    private EChartsExtractKey valueKey;
    /**
     * 缓存已计算过的key,用于去重数据统计
     */
    private Map<Object,Set<Object>> cacheSetMap = new HashMap<>();

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
            //去重数据统计方法
            Object value = BeanUtil.get(item,valueKey.getKey());
            if(containsKey(id)){
                if(!cacheSetMap.get(id).contains(value)){
                    //注意 此处确定没有double类型的数据
                    get(id).setValue(get(id).getValue().intValue()+1);
                    cacheSetMap.get(id).add(value);
                }
            }else{
                EChartsGenericData entry = new EChartsGenericData();
                entry.setXName(getNameByKey(item,xAxisNameKey));
                entry.setName(getNameByKey(item,nameKey));
                entry.setId(getIdByKey(item,idKey));
                entry.setValue(1);
                entry.setType(valueKey.getType());
                if(null!=expandFunc){
                    entry.setExpandData(expandFunc.apply(item));
                }
                put(id,entry);
                cacheSetMap.put(id,new HashSet());
            }
        }catch (Exception e) {
            //TODO 此处异常处理 需要优化
            e.printStackTrace();
        }
    }
}
