package com.cm.common.echarts.convert;

import com.cm.common.echarts.entity.EChartsBaseEntry;
import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.echarts.entity.EChartsVo;

import java.util.List;

/**
 *  数据转化
 */
public abstract class EChartsDataConvert {
    /**
     * 折线或者柱图
     */
    public static final int LINE=0;
    /**
     * 饼图或者其他name/value(value为单一值)类型
     */
    public static final int PIE=1;
    /**
     * 散点图或者其他name/value(value为数据)的类型
     */
    public static final int SCATTER=2;
    /**
     * 父子图或者name/value/children的数据类型 需要指定parentId和id
     */
    public static final int TREE=3;
    /**
     * 桑基图 source/target/value的数据类型
     */
    public static final int LINK=4;
    /**
     * 雷达图 source/target/value的数据类型
     */
    public static final int RADAR=5;

    /**
     * 默认为false
     */
    protected boolean enableExpand=false;


    /**
     * 数据转化
     * @param dataList
     * @return
     */
    public abstract EChartsVo convertVo(List<EChartsGenericData> dataList);

    /**
     * 设置idkey和parentIdkey 仅TreeConvert有效
     * @param parentIdKey 父id字段名称
     */
    public abstract void enableChildren(String parentIdKey);

    /**
     * 设置sourceKey,targetKey 仅LinkConvert有效
     * @param sourceKey
     * @param targetKey
     */
    public abstract void enableLink(String sourceKey,String targetKey);

    /**
     * 设置是否返回expand 考虑部分扩展数据仅方便convert类处理 因此添加此参数作为是否展示的控制
     * @param enableExpand
     */
    public void enableExpand(boolean enableExpand){
        this.enableExpand=enableExpand;
    }

    /**
     * 获取数据抽取类
     * @param convertType
     * @returnT
     */
    public static EChartsDataConvert getInstance(int convertType){
        switch (convertType){
            case EChartsDataConvert.LINE:
                return new EChartsLineConvert();
            case EChartsDataConvert.PIE:
                return new EChartsPieConvert();
            case EChartsDataConvert.SCATTER:
                return new EChartsScatterConvert();
            case EChartsDataConvert.TREE:
                return new EChartsTreeConvert();
            case EChartsDataConvert.LINK:
                //TODO 待定
                break;
            case EChartsDataConvert.RADAR:
                return new EChartsRadarConvert();
            default:
                throw new RuntimeException("no this extract type:"+convertType);
        }
        return null;
    }

    private EChartsBaseEntry dataToEntry(EChartsBaseEntry entry, EChartsGenericData data) throws Exception {
        entry.setId(data.getId());
        entry.setName(data.getName());
        entry.addValue(data.getValue());
        if(enableExpand){
            entry.setExpandData(data.getExpandData());
        }
        return entry;
    }

    protected EChartsBaseEntry dataToEntry(int entryType, EChartsGenericData data) throws Exception {
        return dataToEntry(EChartsBaseEntry.getEntry(entryType),data);
    }

}
