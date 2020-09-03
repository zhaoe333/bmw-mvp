package com.cm.common.constant;

/**
 * 各种维度的常量类
 */
public class DimConstants {

    /**
     * 不指定维度
     */
    public static final int NO_DIM=0;
    /**
     * 时间类型  按天统计
     */
    public static final String DATE_DAY="day";
    /**
     * 时间类型  按周统计
     */
    public static final String DATE_WEEK="week";
    /**
     * 时间类型  按月统计
     */
    public static final String DATE_MONTH="month";
    /**
     * 时间类型  按季度统计
     */
    public static final String DATE_QUARTER="quarter";
    /**
     * 时间类型  按年统计
     */
    public static final String DATE_YEAR="year";
    /**
     * 手机类型  品牌
     */
    public static final int MOBILE_BRAND=1;
    /**
     * 手机类型  型号
     */
    public static final int MOBILE_MODEL=2;
    /**
     * 地域类型  按省统计
     */
    public static final int AREA_PROVINCE=1;
    /**
     * 地域类型  按市统计
     */
    public static final int AREA_CITY=2;
    /**
     * 地域类型  按区统计
     */
    public static final int AREA_DISTRICT=3;
    /**
     * 服务类型  按方法统计
     */
    public static final int SERVER_FUNCTION=3;
    /**
     * 服务类型  按服务统计
     */
    public static final int SERVER_SERVICE=2;
    /**
     * 服务类型  按供应商统计
     */
    public static final int SERVER_SUPPLIER=1;
    /**
     * 车辆类型  按车型统计
     */
    public static final int VEHICLE_MODEL=2;
    /**
     * 车辆类型  按车系统计
     */
    public static final int VEHICLE_SERIES=1;
    /**
     * 统计结果 用户数
     */
    public static final int DIM_USER_NUM =1;
    /**
     * 统计结果 服务次数
     */
    public static final int DIM_ACCESS_NUM =2;
    /**
     * 统计结果 频次 服务次数/用户数
     */
    public static final int DIM_FREQUENCY =3;
    /**
     * 统计结果 新用户数
     */
    public static final int DIM_USER_NEW_NUM =4;
    /**
     * 统计结果 用户数
     */
    public static final int DIM_USER_TOTAL_NUM =5;
    /**
     * 统计结果 服务次数
     */
    public static final int DIM_ACCESS_TOTAL_NUM =6;
    /**
     * 统计结果 频次 服务次数/用户数
     */
    public static final int DIM_TOTAL_FREQUENCY =7;
    /**
     * 统计结果 新用户数
     */
    public static final int USER_NEW_NUM =1;
    /**
     * 统计结果 总用户数
     */
    public static final int USER_TOTAL_NUM=2;
    /**
     * 统计结果 累积用户数
     */
    public static final int USER_ACCUMULATE_NUM=3;
    /**
     * 统计结果 日活跃
     */
    public static final int DAU=1;
    /**
     * 统计结果 周活跃
     */
    public static final int WAU=2;
    /**
     * 统计结果 月活跃
     */
    public static final int MAU=3;
    /**
     * 普通用户
     */
    public static final int DIM_USER_COMMON = 0;
    /**
     * 忠诚用户
     */
    public static final int DIM_USER_LOYALTY = 1;
    /**
     * 新用户
     */
    public static final int DIM_USER_NEW = 3;
    /**
     * 回流用户
     */
    public static final int DIM_USER_REFLOW = 4;
    /**
     * 沉默用户
     */
    public static final int DIM_USER_SILENCE = 2;
    /**
     * 流失用户
     */
    public static final int DIM_USER_CHURN = 5;
}
