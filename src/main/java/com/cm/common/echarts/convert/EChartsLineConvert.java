package com.cm.common.echarts.convert;

import com.cm.common.echarts.entity.EChartsBaseEntry;
import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.echarts.entity.EChartsSeries;
import com.cm.common.echarts.entity.EChartsVo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转化为折线数据或者圆柱数据
 */
@Slf4j
public class EChartsLineConvert extends EChartsDataConvert {

    @Override
    public EChartsVo convertVo(List<EChartsGenericData> datas) {
        EChartsVo vo =new EChartsVo();
        Map<String, EChartsSeries> tempMap= new HashMap<>();
        List<EChartsSeries> entitys = new ArrayList<>();
        datas.forEach(data->{
            try {
                EChartsSeries series = tempMap.get(data.getName());
                if(null==series){
                    series = new EChartsSeries();
                    series.setName(data.getName());
                    series.setType(data.getType());
                    entitys.add(series);
                    tempMap.put(data.getName(),series);
                }
                series.addData(dataToEntry(EChartsBaseEntry.NORMAL,data));
                vo.addXname(data.getXName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        vo.setEntitys(entitys);
        return vo;
    }

    @Override
    public void enableChildren(String parentIdKey) {
        log.warn("this method enableChildren is useless in EChartsLineConvert.class!");
    }

    @Override
    public void enableLink(String sourceKey, String targetKey) {
        log.warn("this method enableLink is useless in EChartsLineConvert.class!");
    }

}
