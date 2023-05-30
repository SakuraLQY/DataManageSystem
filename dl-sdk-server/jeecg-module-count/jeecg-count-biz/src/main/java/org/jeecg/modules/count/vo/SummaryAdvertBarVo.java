package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @Description: SummaryAdvertBarVo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value = "广告数据明细图表", description = "广告数据明细图表")
public class SummaryAdvertBarVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充值用户注册日期分布图
     **/
    private List<OrderDateGroupRateVo> dateRateGroup;

    /**
     * 新增充值面额占比图
     **/
    private List<OrderMoneyGroupRateVo> aliveMoneyRateGroup;

    /**
     * 活跃充值面额占比图
     **/
    private List<OrderMoneyGroupRateVo> firstMoneyRateGroup;

}
