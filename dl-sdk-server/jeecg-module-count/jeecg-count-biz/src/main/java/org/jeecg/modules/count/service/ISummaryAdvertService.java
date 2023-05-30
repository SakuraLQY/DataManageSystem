package org.jeecg.modules.count.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.jeecg.modules.count.dto.SummaryAdvertDto;
import org.jeecg.modules.count.vo.SummaryAdvertBarVo;
import org.jeecg.modules.count.vo.SummaryAdvertVo;

/**
 * @Description: 数据总表
 * @Author: jeecg-boot
 * @Date:   2023-04-19
 * @Version: V1.0
 */
public interface ISummaryAdvertService {

    /**
     * @param summaryAdvertDto
     * @return java.util.List<org.jeecg.modules.count.vo.SummaryAdvertVo>
     * @author chenyw
     * @description 获取广告数据明细
     * @date 19:54 2023/5/11/011
     **/
    List<SummaryAdvertVo> getSummaryAdvert(SummaryAdvertDto summaryAdvertDto);

    /**
     * @param summaryAdvertDto
     * @return org.jeecg.modules.count.vo.SummaryAdvertBarVo
     * @author chenyw
     * @description 获取广告数据 饼图
     * @date 18:59 2023/5/12/012
     **/
    SummaryAdvertBarVo getSummaryAdvertEChart(SummaryAdvertDto summaryAdvertDto);

    /**
     * @param summaryAdvertDto
     * @return java.util.Map<java.lang.String,java.util.Map<java.lang.String,java.math.BigDecimal>>
     * @author chenyw
     * @description 获取广告数据 折线图
     * @date 17:25 2023/5/13/013
     **/
    Map<String, Map<String, BigDecimal>> getSummaryAdvertLine(SummaryAdvertDto summaryAdvertDto);

}
