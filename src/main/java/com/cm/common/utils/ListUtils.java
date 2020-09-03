package com.cm.common.utils;

import java.util.List;

/**
 * 处理集合操作的相关工具类
 */
public class ListUtils {
    /**
     * 数据向左移动1位 右边补null
     * @param list
     */
    public static void leftMove(List list){
        leftMove(list,null);
    }

    /**
     * 数据向左移动1位 右边补obj
     * @param list
     * @param obj
     */
    public static void leftMove(List list,Object obj){
        list.remove(0);
        list.add(obj);
    }

    /**
     *数据向右移动1位 左边补null
     * @param list
     */
    public static void rightMove(List list){
        rightMove(list,null);
    }

    /**
     * 数据向右移动1位 左边补obj
     * @param list
     * @param obj
     */
    public static void rightMove(List list,Object obj){
        list.add(0,obj);
        list.remove(list.size()-1);
    }
}
