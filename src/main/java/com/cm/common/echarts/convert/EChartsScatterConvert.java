package com.cm.common.echarts.convert;

import com.cm.common.echarts.entity.EChartsBaseEntry;
import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.echarts.entity.EChartsSeries;
import com.cm.common.echarts.entity.EChartsVo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 散点图或者其他name/value(value为数据)的类型
 */
@Slf4j
public class EChartsScatterConvert  extends EChartsDataConvert {

    @Override
    public EChartsVo convertVo(List<EChartsGenericData> dataList) {
        EChartsSeries series = new EChartsSeries();
        Map<String, EChartsBaseEntry> tempMap = new HashMap<>();
        dataList.forEach(data->{
            try{
                EChartsBaseEntry entry = tempMap.get(data.getName());
                if(null==entry){
                    entry = dataToEntry(EChartsBaseEntry.MULTI,data);
                    tempMap.put(data.getName(),entry);
                    series.addData(entry);
                }else{
                    entry.addValue(data.getValue());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        return new EChartsVo((series));
    }

    @Override
    public void enableChildren(String parentIdKey) {
        log.warn("this method enableChildren is useless in EChartsScatterConvert.class!");

    }

    @Override
    public void enableLink(String sourceKey, String targetKey) {
        log.warn("this method enableChildren is useless in EChartsScatterConvert.class!");
    }
}
