package org.jeecg.modules.advert.api.jrtt.report.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttPageInfoResponse;

/**
 * @Description: 指标数据
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class Metrics {

    /**
     * 点击数
     **/
    @JSONField(name = "click_cnt")
    private Integer clickCnt;

    /**
     * 下载数
     **/
    @JSONField(name = "download_finish_cnt")
    private Integer downloadFinishCnt;

    /**
     * 展示数
     **/
    @JSONField(name = "show_cnt")
    private Integer showCnt;

    /**
     * 成本
     **/
    @JSONField(name = "stat_cost")
    private BigDecimal statCost;

}
