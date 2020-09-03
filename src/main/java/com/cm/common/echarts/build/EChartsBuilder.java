package com.cm.common.echarts.build;

import com.cm.common.echarts.convert.EChartsDataConvert;
import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.echarts.entity.EChartsVo;
import com.cm.common.echarts.extract.EChartsExtract;
import com.cm.common.echarts.filter.EChartsFilter;
import com.cm.common.echarts.sort.EChartsSort;
import com.cm.common.echarts.utils.EChartsBuilderUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;

/**
 * 新版的echarts构建类
 * 讲数据抽取和数据转化分离
 * 将不同的原始数据抽取为统一的数据结构
 * 将统一的数据结构转化为不同的echarts数据
 */
@Slf4j
public class EChartsBuilder<T> {

    /**
     * 负责数据抽取
     */
    private EChartsExtract extract;
    /**
     * 负责数据转化
     */
    private EChartsDataConvert convert;
    /**
     * 数据接口扩展参数转化函数
     */
    private Function<T,Object> expandFunc;
    /**
     * 数据过滤器
     */
    private EChartsFilter<T> filter;
    /**
     * 负责清洗后的数据排序和limit
     */
    private EChartsSort sort;

    /**
     * 设置nameKey
     * @param nameKey
     * @return
     */
    public EChartsBuilder<T> addNameKey(String nameKey){
        return addNameKey(nameKey,null);
    }

    /**
     * 设置nameKey
     * @param nameKey
     * @param name
     * @return
     */
    public EChartsBuilder<T> addNameKey(String nameKey, String name){
        extract.addNameKey(nameKey,name);
        return this;
    }

    /**
     * 设置valueKey 设置显示名称
     * @param key
     * @param name
     * @return
     */
    public EChartsBuilder<T> addValueKey(String key, String name){
        return addValueKey(key,name,null);
    }

    /**
     * 设置valueKey 设置显示名称 和数据类型
     * @param key
     * @param name
     * @param type
     * @return
     */
    public EChartsBuilder<T> addValueKey(String key, String name, String type){
        extract.addValueKey(key,name,type);
        return this;
    }

    /**
     * 设置valueKey
     * @param key
     * @return
     */
    public EChartsBuilder<T> addValueKey(String key){
        return addValueKey(key,null);
    }

    /**
     * 设置x轴信息及转换规则
     * @param xNameKey
     * @param func
     * @return
     */
    public EChartsBuilder<T> setXAxisNameKey(String xNameKey, Function<Object, String> func) {
        extract.setXAxisNameKey(xNameKey,func);
        return this;
    }

    /**
     * 设置x轴信息
     * @param xNameKey
     * @return
     */
    public EChartsBuilder<T> setXAxisNameKey(String xNameKey) {
        return setXAxisNameKey(xNameKey,null);
    }

    /**
     * 调用此方法后返回数据会包含id 默认取id
     * @return
     */
    public EChartsBuilder<T> enableId() {
        return enableId("id");
    }

    /**
     * 设置id字段 调用此方法后返回数据会包含id
     * @param idKey 对象中id的字段名
     * @return
     */
    public EChartsBuilder<T> enableId(String idKey) {
        this.extract.setIdKey(idKey);
        return this;
    }

    /**
     * 设置idkey和parentIdkey 仅TreeConvert有效
     * 必须设置expandFunc
     * @param idKey 主键id名称
     * @param parentIdKey 父id字段名称
     */
    public EChartsBuilder<T> enableChildren(String idKey, String parentIdKey){
        if(null==this.expandFunc){
            throw new RuntimeException("this method enableChildren need expandFunc");
        }
        this.extract.setIdKey(idKey);
        this.convert.enableChildren(parentIdKey);
        return this;
    }

    /**
     * 设置sourceKey,targetKey 仅LinkConvert有效 目前无效 不要使用
     * @param sourceKey
     * @param targetKey
     */
    @Deprecated
    public EChartsBuilder<T> enableLink(String sourceKey, String targetKey){
        if(null==this.expandFunc){
            throw new RuntimeException("this method enableLink need expandFunc");
        }
        this.convert.enableChildren(targetKey);
        return this;
    }

    private EChartsBuilder(EChartsExtract extract, EChartsDataConvert convert) {
        super();
        this.extract=extract;
        this.convert=convert;
        this.sort = new EChartsSort();
    }

    /**
     * 获取builder实例
     * @param extractType
     * @param convertType
     * @param <T>
     * @return
     */
    public static <T> EChartsBuilder<T> getInstance(int extractType, int convertType){
        // 构建extract
        EChartsExtract extract = EChartsExtract.getInstance(extractType);
        // 构建convert
        EChartsDataConvert convert = EChartsDataConvert.getInstance(convertType);
        return new EChartsBuilder<>(extract,convert);
    }

    /**
     * 设置数据接口扩展参数转化函数
     * 用于convert内的处理
     * 返回数据不包含expand
     * @param func
     * @return
     */
    public EChartsBuilder<T> expandData(Function<T,Object> func){
        return this.expandData(func,false);
    }

    /**
     * 设置数据接口扩展参数转化函数
     * @param func
     * @param enableExpand 默认false 为true的情况下返回数据会携带expand
     * @return
     */
    public EChartsBuilder<T> expandData(Function<T,Object> func, boolean enableExpand){
        this.expandFunc=func;
        this.convert.enableExpand(enableExpand);
        return this;
    }

    /**
     * 抽取数据(单个数据抽取 暂不返回builder对象)
     * @param item
     */
    public void extract(T item){
        if(null==filter||filter.filter(item)){
            this.extract.extract(item, expandFunc);
        }
    }

    /**
     * 抽取数据 调用此方法之后再调用buildvo方法
     * TODO 这个方法后续需要调整吗?
     * @param dataList
     */
    public EChartsBuilder<T> extract(List<T> dataList){
        if(null!=dataList&&dataList.size()>0){
            dataList.forEach(item->extract(item));
        }
        return this;
    }


    /**
     * build数据 返回echart需要的vo数据
     * @return
     */
    public EChartsVo buildVo(){
        List<EChartsGenericData> dataList = this.extract.getDataList();
        if(null!=dataList&&0<dataList.size()){
            return this.convert.convertVo(this.sort.sort(dataList));
        }
        return new EChartsVo();
    }

    /**
     * 设置数据过滤器
     * @param filter
     */
    public EChartsBuilder<T> setFilter(EChartsFilter<T> filter){
        this.filter=filter;
        return this;
    }

    /**
     * 设置排序 排序目前仅支持string和int类型
     * @param sortField
     * @param sortType
     */
    public EChartsBuilder<T> setSort(String sortField, int sortType){
        this.sort.setSort(sortField,sortType);
        return this;
    }

    /**
     * 设置取多少条数据 和sort方法结合使用
     * @param limitNum
     */
    public EChartsBuilder<T> setLimit(int limitNum){
        this.sort.setLimit(limitNum);
        return this;
    }

    /**
     * 设置排序 排序目前仅支持string和int类型 默认为ASC排序
     * @param sortField
     */
    public EChartsBuilder<T> setSort(String sortField){
        return setSort(sortField, EChartsBuilderUtil.SORT_ASC);
    }

}
