package com.cm.common.echarts.convert;

import com.cm.common.echarts.entity.EChartsBaseEntry;
import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.echarts.entity.EChartsSeries;
import com.cm.common.echarts.entity.EChartsVo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 饼图或者其他name/value(value为单一值)类型
 */
@Slf4j
public class EChartsPieConvert extends EChartsDataConvert {

    @Override
    public EChartsVo convertVo(List<EChartsGenericData> dataList) {
        EChartsSeries series = new EChartsSeries();
        series.setType(dataList.get(0).getType());
        dataList.forEach(data->{
            try {
                series.addData(dataToEntry(EChartsBaseEntry.NORMAL,data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return new EChartsVo((series));
    }

    @Override
    public void enableChildren(String parentIdKey) {
        log.warn("this method enableChildren is useless in EChartsPieConvert.class!");
    }

    @Override
    public void enableLink(String sourceKey, String targetKey) {
        log.warn("this method enableChildren is useless in EChartsPieConvert.class!");
    }
}
