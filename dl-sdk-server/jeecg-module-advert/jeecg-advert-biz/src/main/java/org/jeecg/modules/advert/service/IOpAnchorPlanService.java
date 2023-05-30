package org.jeecg.modules.advert.service;

import java.util.List;
import org.jeecg.modules.advert.entity.OpAnchorPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.vo.CampaignAnchorVo;

/**
 * @Description: op_anchor_plan
 * @Author: jeecg-boot
 * @Date: 2023-02-28
 * @Version: V1.0
 */
public interface IOpAnchorPlanService extends IService<OpAnchorPlan> {

    /**
     * @param dealId:   广告ID
     * @param anchorId: 主播ID
     * @author xmh
     * @description 主播绑定广告
     * @date 2023/3/3 15:22
     */
    void bindDeal(Integer dealId, Integer anchorId);

    /**
     * @return List<CampaignAnchorVo>
     * @author xmh
     * @description 返回主播广告信息
     * @date 2023/3/8 13:48
     */
    List<CampaignAnchorVo> anchorDealInfo();
}
