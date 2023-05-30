package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: JrttDealActionVo
 * @author: Eric
 * @description: 查询行为类目请求对象
 * @date: 2023/2/23 11:32
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JrttDealActionCategoryReq {

    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    @JSONField(name = "action_scene")
    private List<String> actionScene;

    @JSONField(name = "action_days")
    private Integer actionDays;

    public JrttDealActionCategoryReq(Long advertiserId) {
        this.advertiserId = advertiserId;
    }
}
