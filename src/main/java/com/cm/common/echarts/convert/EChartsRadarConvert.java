package com.cm.common.echarts.convert;

import com.cm.common.echarts.entity.EChartsBaseEntry;
import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.echarts.entity.EChartsSeries;
import com.cm.common.echarts.entity.EChartsVo;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理雷达图的数据转化
 */
@Slf4j
public class EChartsRadarConvert extends EChartsDataConvert {

    @Override
    public EChartsVo convertVo(List<EChartsGenericData> dataList) {
        EChartsVo vo = new EChartsVo();
        EChartsSeries series = new EChartsSeries();
        Map<String, EChartsBaseEntry> tempMap = new HashMap<>();
        dataList.forEach(data->{
            try{
                EChartsBaseEntry entry = tempMap.get(data.getXName());
                if(null==entry){
                    entry = dataToEntry(EChartsBaseEntry.MULTI,data);
                    //这里特殊处理一下 需要放xname 因为是雷达图.....
                    entry.setName(data.getXName());
                    tempMap.put(data.getXName(),entry);
                    vo.addXname(data.getXName());
                    series.addData(entry);
                }else{
                    entry.addValue(data.getValue());
                }
                vo.addDataName(data.getName());
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        vo.setEntitys(Arrays.asList(series));
        return vo;
    }

    @Override
    public void enableChildren(String parentIdKey) {
        log.warn("this method enableChildren is useless in EChartsRadarConvert.class!");
    }

    @Override
    public void enableLink(String sourceKey, String targetKey) {
        log.warn("this method enableChildren is useless in EChartsScatterConvert.class!");
    }
}
