package com.cm.common.echarts.extract;

import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.utils.BeanUtil;

import java.util.List;
import java.util.function.Function;

/**
 * 数据抽取 抽象类
 */
public abstract class EChartsExtract {

    /**
     * 对应EChartsCommonExtract 负责列数据的处理
     * [
     *  {name:1,type:0,value:10},
     *  {name:1,type:1,value:11},
     *  {name:1,type:2,value:12},
     *  {name:2,type:0,value:1},
     *  {name:2,type:1,value:2},
     *  {name:3,type:2,value:3}
     * ]
     */
    public static final int COMMON=0;

    /**
     * 对应EChartsFlatExtract 负责扁平化数据的处理
     * [
     *  {name:2018-01-01,userTotalNum:0,userNewNum:10,loyaltyNum:15},
     *  {name:2018-01-02,userTotalNum:0,userNewNum:10,loyaltyNum:15},
     *  {name:2018-01-03,userTotalNum:0,userNewNum:10,loyaltyNum:15},
     * ]
     */
    public static final int FLAT=1;

    /**
     * 对应EChartsCountSumExtract 负责需要计算的数据的处理
     */
    public static final int SUM=2;

    /**
     * 对应EChartsCountDistinctExtract 负责需要计算的数据的处理
     */
    public static final int DISTINCT=3;

    /**
     * x轴描述信息变量名
     */
    protected EChartsExtractKey xAxisNameKey;

    /**
     * id  key
     */
    protected String idKey;

    /**
     * 设置namekey
     * @param nameKey
     * @param name
     */
    public abstract void addNameKey(String nameKey, String name);

    /**
     * 设置namekey
     * @param nameKey
     * @param nameFunc
     */
    public abstract void addNameKey(String nameKey, Function<Object,String> nameFunc);

    /**
     * 设置valueKey
     * @param key
     * @param name
     * @param type
     */
    public abstract void addValueKey(String key, String name, String type);

    /**
     * 抽取数据
     * @return
     */
    public <T> void extract(T item){
        extract(item,null);
    }

    /**
     * 抽取数据
     * @param item
     * @param expandFunc
     * @return
     */
    public abstract <T> void extract(T item, Function<T,Object> expandFunc);

    /**
     * 设置x轴信息及转换规则
     * @param xNameKey
     * @param func
     * @return
     */
    public void setXAxisNameKey(String xNameKey, Function<Object, String> func) {
        this.xAxisNameKey = new EChartsExtractKey(xNameKey,func);
    }

    /**
     * 设置x轴信息
     * @param xNameKey
     * @return
     */
    public void setXAxisNameKey(String xNameKey) {
        setXAxisNameKey(xNameKey,null);
    }

    /**
     * 设置idkey 需要扩展数据中有该属性
     * @param idKey
     */
    public void setIdKey(String idKey){
        this.idKey=idKey;
    }

    /**
     * 获取数据抽取类
     * @param extractType
     * @returnT
     */
    public static EChartsExtract getInstance(int extractType){
        switch (extractType){
            case EChartsExtract.COMMON:
                return new EChartsCommonExtract();
            case EChartsExtract.FLAT:
                return new EChartsFlatExtract();
            case EChartsExtract.SUM:
                return new EChartsCountSumExtract();
            case EChartsExtract.DISTINCT:
                return new EChartsCountDistinctExtract();
            default:
                throw new RuntimeException("no this extract type:"+extractType);
        }
    }

    /**
     * 获取name(包括xname和name字段,value字段需单独计算)
     * @param item
     * @param key
     * @return
     * @throws Exception
     */
    public String getNameByKey(Object item, EChartsExtractKey key) throws Exception {
        if(null==key||null==key.getKey()){
            return null;
        }
        Object name = BeanUtil.get(item,key.getKey());
        if(null!=key.getNameFunc()){
            return key.getNameFunc().apply(name);
        }
        if(null!=name){
            return name.toString();
        }
        if(null!=key.getName()){
            return key.getName();
        }
        return null;
    }

    /**
     * 获取name(包括xname和name字段,value字段需单独计算)
     * @param item
     * @param idKey
     * @return
     * @throws Exception
     */
    public Object getIdByKey(Object item, String idKey) throws Exception {
        if(null==idKey||"".equals(idKey)){
            return null;
        }
        Object name = BeanUtil.get(item,idKey);
        if(null!=name){
            return name;
        }else{
            return idKey;
        }
    }

    /**
     * 返回设定的idKey
     * @return
     */
    public String getIdKey(){
        return this.idKey;
    }

    /**
     * 获取抽取之后的数据list
     * 无排序
     * @return
     */
    public abstract List<EChartsGenericData> getDataList();

}
