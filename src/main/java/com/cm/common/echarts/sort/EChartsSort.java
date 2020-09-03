package com.cm.common.echarts.sort;

import com.cm.common.echarts.utils.EChartsBuilderUtil;
import com.cm.common.utils.BeanUtil;

import java.util.Collections;
import java.util.List;

/**
 * 负责抽取后的数据排序和limit
 */
public class EChartsSort {

    /**
     * 排序的字段 为null时无需排序
     */
    protected String sortField;
    /**
     * 排序的顺序 0 从小到大 1 从大到小 默认0
     */
    protected int sortType = 0;
    /**
     * 设置取多少条数据 默认0为取所有
     */
    protected int limit =0;

    /**
     * 设置排序 排序目前仅支持string和int类型
     * @param sortField
     * @param sortType
     */
    public void setSort(String sortField,int sortType){
        this.sortField=sortField;
        this.sortType=sortType;
    }

    /**
     * 设置取多少条数据 和sort方法结合使用
     * @param limitNum
     */
    public void setLimit(int limitNum){
        this.limit=limitNum;
    }

    /**
     * 设置排序 排序目前仅支持string和int类型 默认为ASC排序
     * @param sortField
     */
    public void setSort(String sortField){
        setSort(sortField, EChartsBuilderUtil.SORT_ASC);
    }

    /**
     * 处理数据排序和limit
     * @param dataList
     * @param <T>
     * @return
     */
    public <T> List<T> sort(List<T> dataList){
        doSort(dataList);
        if(limit>0&&dataList.size()>0){
            return dataList.subList(0,limit<dataList.size()?limit:dataList.size());
        }
        return dataList;
    }

    /**
     * 排序
     * @param dataList
     * @param <T>
     */
    private <T> void doSort(List<T> dataList){
        if(null!=sortField && 0!=dataList.size()){
            try{
                Object obj = BeanUtil.get(dataList.get(0), sortField);
                if(EChartsBuilderUtil.SORT_DESC == sortType){
                    //判断排序子弹的类型
                    if(obj instanceof String){
                        Collections.sort(dataList, (o1, o2)->{
                            try{
                                String field_1 = (String) BeanUtil.get(o1,sortField);
                                String field_2 = (String) BeanUtil.get(o2,sortField);
                                return -field_1.compareTo(field_2);
                            }catch(Exception e){
                                e.printStackTrace();
                                return 0;
                            }
                        });
                    }else if(obj instanceof Integer || obj instanceof Double){
                        Collections.sort(dataList, (o1,o2)->{
                            try{
                                int field_1 = (int) BeanUtil.get(o1,sortField);
                                int field_2 = (int) BeanUtil.get(o2,sortField);
                                return -(field_1-field_2);
                            }catch(Exception e){
                                e.printStackTrace();
                                return 0;
                            }
                        });
                    }
                }else if(EChartsBuilderUtil.SORT_ASC == sortType){
                    //判断排序子弹的类型
                    if(obj instanceof String){
                        Collections.sort(dataList, (o1,o2)->{
                            try{
                                String field_1 = (String) BeanUtil.get(o1,sortField);
                                String field_2 = (String) BeanUtil.get(o2,sortField);
                                return field_1.compareTo(field_2);
                            }catch(Exception e){
                                e.printStackTrace();
                                return 0;
                            }
                        });
                    }else if(obj instanceof Integer || obj instanceof Double){
                        Collections.sort(dataList, (o1,o2)->{
                            try{
                                int field_1 = (int) BeanUtil.get(o1,sortField);
                                int field_2 = (int) BeanUtil.get(o2,sortField);
                                return field_1-field_2;
                            }catch(Exception e){
                                e.printStackTrace();
                                return 0;
                            }
                        });
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
