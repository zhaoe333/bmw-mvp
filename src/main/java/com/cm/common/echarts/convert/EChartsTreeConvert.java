package com.cm.common.echarts.convert;

import com.cm.common.echarts.entity.EChartsBaseEntry;
import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.echarts.entity.EChartsSeries;
import com.cm.common.echarts.entity.EChartsVo;
import com.cm.common.utils.BeanUtil;
import com.cm.common.utils.TreeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 父子图或者name/value/children的数据类型 需要指定parentId和id
 */
@Slf4j
public class EChartsTreeConvert extends EChartsDataConvert {

    /**父idkey*/
    private String parentIdKey;

    @Override
    public EChartsVo convertVo(List<EChartsGenericData> dataList) {
        if(null==parentIdKey){
            throw new RuntimeException("need call enableChildren method first");
        }
        EChartsSeries series = new EChartsSeries();
        series.setType(dataList.get(0).getType());
        dataList.forEach(data->{
            try {
                EChartsBaseEntry entry = dataToEntry(EChartsBaseEntry.NORMAL, data);
                entry.setParentId(BeanUtil.get(data.getExpandData(),parentIdKey));
                series.addData(entry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        try {
            //生成树结构
            series.setData(TreeUtil.sort(series.getData()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EChartsVo((series));
    }

    @Override
    public void enableChildren(String parentIdKey) {
        this.parentIdKey=parentIdKey;
    }

    @Override
    public void enableLink(String sourceKey, String targetKey) {
        log.warn("this method enableChildren is useless in EChartsTreeConvert.class!");
    }

}
