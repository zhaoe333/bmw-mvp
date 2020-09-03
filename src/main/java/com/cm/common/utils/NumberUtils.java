package com.cm.common.utils;

import java.math.BigDecimal;

/**
 * 数字计算相关工具类
 */
public class NumberUtils {

    /**
     * 计算 a-b/b的结果 结果保留小数点后两位
     * @param a
     * @param b
     * @return
     */
    public static Double countGrowthScale(Number a,Number b){
        return countGrowthScale(a,b,2);
    }


    public static Double countGrowthScale(Number a,Number b,int length){
        if(null==a||null==b){
            return null;
        }
        return countScale((a.doubleValue()-b.doubleValue())*100,b.doubleValue(),2);
    }

    /**
     * 计算 a/b的结果 结果保留小数点后两位
     * @param a
     * @param b
     * @return
     */
    public static Double countScale(Number a,Number b){
        return countScale(a,b,2);
    }

    /**
     * 计算 a/b的结果 结果保留小数点后length位
     * @param a
     * @param b
     * @param length
     * @return
     */
    public static Double countScale(Number a,Number b,int length){
        if(null==a||null==b){
            return null;
        }
        return countScale(a.doubleValue()*100,b.doubleValue(),2);
//        return new BigDecimal(a.doubleValue()*100/b.doubleValue()).setScale(length,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double countScale(Double a,Double b,int length){
        if(null==a||null==b){
            return null;
        }
        return new BigDecimal(a/b).setScale(length,BigDecimal.ROUND_HALF_UP).doubleValue();

    }
}
