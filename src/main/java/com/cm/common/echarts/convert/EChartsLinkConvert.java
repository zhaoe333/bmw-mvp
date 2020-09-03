package com.cm.common.echarts.convert;

import com.cm.common.echarts.entity.EChartsGenericData;
import com.cm.common.echarts.entity.EChartsVo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * TODO 未完成
 * 桑基图 source/target/value的数据类型
 */
@Slf4j
public class EChartsLinkConvert extends EChartsDataConvert {
    @Override
    public EChartsVo convertVo(List<EChartsGenericData> dataList) {
        return null;
    }

    @Override
    public void enableChildren(String parentIdKey) {

    }

    @Override
    public void enableLink(String sourceKey, String targetKey) {

    }
}
