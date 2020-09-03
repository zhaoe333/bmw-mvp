package com.cm.common.echarts.filter;

/**
 * echarts的数据过滤类
 * TODO 可以过滤两个地方 一个是extract之前 一个是extract之后 需要考虑一下?
 * TODO filter的实现机制采用抽象类还是
 */
@FunctionalInterface
public interface EChartsFilter<T> {

    boolean filter(T t);

}
