package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.bo.jrtt.project
 * @className: DeliverySetting
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/17 14:25
 * @version: 1.0
 */
@Data
public class DeliverySetting {

    @JSONField(name = "schedule_type")
    private String scheduleType;
    @JSONField(name = "start_time")
    private String startTime;
    @JSONField(name = "end_time")
    private String endTime;
    @JSONField(name = "schedule_time")
    private String scheduleTime;
    @JSONField(name = "deep_bid_type")
    private String deepBidType;
    @JSONField(name = "bid_type")
    private String bidType;
    @JSONField(name = "bid_speed")
    private String bidSpeed;
    @JSONField(name = "budget_mode")
    private String budgetMode;
    @JSONField(name = "budget")
    private String budget;
    @JSONField(name = "pricing")
    private String pricing;
    @JSONField(name = "budget_optimize_switch")
    private String budgetOptimizeSwitch;
}
