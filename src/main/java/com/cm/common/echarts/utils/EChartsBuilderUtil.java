package com.cm.common.echarts.utils;

import com.cm.common.constant.DimConstants;
import com.cm.common.echarts.entity.EChartsBaseEntry;
import com.cm.common.echarts.entity.EChartsEntry;
import com.cm.common.echarts.entity.EChartsSeries;
import com.cm.common.echarts.entity.EChartsVo;
import com.cm.common.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于辅助建echarts数据结构的工具类
 */
public class EChartsBuilderUtil {

    /**
     * 排序 从大到小
     */
    public static final int SORT_DESC=1;
    /**
     * 排序 从小到大
     */
    public static final int SORT_ASC=0;
    /**
     * 按照名称排序
     */
    public static final String SORT_FIELD_NAME="name";
    /**
     * 按照值排序
     */
    public static final String SORT_FIELD_VALUE="value";
    /**
     * 按照id排序
     */
    public static final String SORT_FIELD_ID="id";

    /**
     * 比率计算方式 增长率 a-b/b
     */
    public static final int SCALE_GROWTH=1;
    /**
     * 比率计算方式 占比率 a/b
     */
    public static final int SCALE_NORMAL=2;

    /**
     * 清除第一组数据
     */
    public static final Integer DELETE_FIRST=0;
    /**
     * 清除第二组数据
     */
    public static final Integer DELETE_SECOND=1;

    /**
     * value的类型 num
     */
    public static final String TYPE_NUM="num";
    /**
     * value的类型 rate
     */
    public static final String TYPE_RATE="rate";
    /**
     * value的类型 times
     */
    public static final String TYPE_TIMES="times";

    /**
     * 通过服务维度类型获取对应的属性名称字段
     * @param serviceType
     * @return
     */
    public static String getServiceNameKey(int serviceType){
        //TODO 后续应该从配置文件 或者数据库直接读取
        String nameKey=null;
        switch(serviceType){
            case DimConstants.SERVER_SUPPLIER:
                nameKey= "supplierName";
                break;
            case DimConstants.SERVER_SERVICE:
                nameKey = "serviceName";
                break;
            case DimConstants.SERVER_FUNCTION:
                nameKey="functionName";
                break;
        }
        return nameKey;
    }

    /**
     * 通过地域维度类型获取对应的属性名称字段
     * @param areaType
     * @return
     */
    public static String getAreaNameKey(int areaType){
        //TODO 后续应该从配置文件 或者数据库直接读取
        String nameKey=null;
        switch(areaType){
            case DimConstants.AREA_PROVINCE:
                nameKey= "provinceName";
                break;
            case DimConstants.AREA_CITY:
                nameKey = "cityName";
                break;
            case DimConstants.AREA_DISTRICT:
                nameKey="districtName";
                break;
        }
        return nameKey;
    }

    /**
     * 痛殴车辆维度类型获取对应的属性名称字段
     * @param carType
     * @return
     */
    public static String getCarNameKey(int carType){
        //TODO 后续应该从配置文件 或者数据库直接读取
        String nameKey=null;
        switch(carType){
            case DimConstants.VEHICLE_SERIES:
                nameKey= "carSeries";
                break;
            case DimConstants.VEHICLE_MODEL:
                nameKey = "carModel";
                break;
        }
        return nameKey;
    }

    /**
     * 获取基础统计结果的valueKey
     * @return
     */
    public static String getBaseValueKey(int valueType){
        //TODO 后续应该从配置文件 或者数据库直接读取
        String valueKey=null;
        switch(valueType){
            case DimConstants.DIM_USER_NUM:
                valueKey= "userNum";
                break;
            case DimConstants.DIM_ACCESS_NUM:
                valueKey = "accessNum";
                break;
            case DimConstants.DIM_FREQUENCY:
                valueKey = "frequency";
                break;
            case DimConstants.DIM_USER_NEW_NUM:
                valueKey = "userNewNum";
                break;
            case DimConstants.DIM_USER_TOTAL_NUM:
                valueKey = "userTotalNum";
                break;
            case DimConstants.DIM_ACCESS_TOTAL_NUM:
                valueKey = "accessTotalNum";
                break;
            case DimConstants.DIM_TOTAL_FREQUENCY:
                valueKey = "userTotalFrequency";
                break;
        }
        return valueKey;
    }

    /**
     * 获取用户统计结果的valueKey
     * @return
     */
    public static String getTrendCountValueKey(int valueType){
        //TODO 后续应该从配置文件 或者数据库直接读取
        String valueKey=null;
        switch(valueType){
            case DimConstants.USER_NEW_NUM:
                valueKey= "userNewNum";
                break;
            case DimConstants.USER_TOTAL_NUM:
                valueKey = "userTotalNum";
                break;
            case DimConstants.USER_ACCUMULATE_NUM:
                valueKey = "userAccumulateNum";
                break;
        }
        return valueKey;
    }

    /**
     * 获取用户统计结果的valueKey
     * @return
     */
    public static String getTrendRateCountValueKey(int valueType){
        //TODO 后续应该从配置文件 或者数据库直接读取
        String valueKey=null;
        switch(valueType){
            case DimConstants.USER_NEW_NUM:
                valueKey= "userNewGrowth";
                break;
            case DimConstants.USER_TOTAL_NUM:
                valueKey = "userGrowth";
                break;
            case DimConstants.USER_ACCUMULATE_NUM:
                valueKey = "userAccumulateGrowth";
                break;
        }
        return valueKey;
    }

    /**
     * 计算各种环比，同比，增长率
     * @param vo
     */
    public static void countRate(EChartsVo vo){
        countRate(vo,SCALE_GROWTH,null);
    }

    /**
     * 计算各种环比，同比，增长率
     * @param vo
     * @param scaleType
     */
    @Deprecated
    public static void countRate(EChartsVo vo, Integer scaleType){
        countRate(vo,scaleType,null);
    }

    /**
     * 计算各种环比，同比，增长率
     * @param vo
     * @param scaleType
     * @param deleteIndex
     */
    @Deprecated
    public static void countRate(EChartsVo vo, Integer scaleType, Integer deleteIndex){
        List<EChartsSeries> entitys = vo.getEntitys();
        //如果数据不为2组 则抛出异常
        if(null==entitys||entitys.size()!=2){
            throw new RuntimeException("目前支持两组数据进行计算比率");
        }
        if(vo.getXnames().size()==0){
            throw new RuntimeException("计算比率的时候必须有xname");
        }
        //需要展示的数据
        List<EChartsBaseEntry> dataA = entitys.get(0).getData();
        //用来计算比例的数据 后续根据参数dropFlag来判断是否删除
        List<EChartsBaseEntry> dataB = entitys.get(1).getData();
        List<EChartsBaseEntry> resultData = new ArrayList<>();
        for(int i =0;i<vo.getXnames().size();i++){
            if(scaleType==SCALE_GROWTH){
                resultData.add(new EChartsEntry(NumberUtils.countGrowthScale(((EChartsEntry)dataA.get(i)).getValue(),((EChartsEntry)dataB.get(i)).getValue())));
            }else if(scaleType==SCALE_NORMAL){
                resultData.add(new EChartsEntry(NumberUtils.countScale(((EChartsEntry)dataA.get(i)).getValue(),((EChartsEntry)dataB.get(i)).getValue())));
            }else{
                throw new RuntimeException("不支持的scaleType");
            }
        }
        if(null!=deleteIndex){
            entitys.remove(deleteIndex.intValue());
        }
        EChartsSeries entity = new EChartsSeries();
        entity.setName("rate");
        entity.setType(EChartsBuilderUtil.TYPE_RATE);
        entity.setData(resultData);
        vo.addEntitys(entity);
    }

}
