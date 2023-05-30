package org.jeecg.modules.advert.api.xingtu.plan.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.xingtu.plan.bo
 * @className: DeliverySetting
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/10 19:30
 * @version: 1.0
 */
@Data
public class DeliverySetting {

    @JSONField(name = "smart_bid_type")
    private String smartBidType;

    @JSONField(name = "flow_control_mode")
    private String flowControlMode;

    @JSONField(name = "budget_mode")
    private String budgetMode;

    private Float budget;

    @JSONField(name = "schedule_type")
    private String scheduleType;

    @JSONField(name = "start_time")
    private String startTime;

    @JSONField(name = "end_time")
    private String endTime;

    @JSONField(name = "schedule_time")
    private String scheduleTime;

    private String pricing;

    @JSONField(name = "cpa_bid")
    private Float cpaBid;

    @JSONField(name = "deep_bid_type")
    private String deepBidType;
}
