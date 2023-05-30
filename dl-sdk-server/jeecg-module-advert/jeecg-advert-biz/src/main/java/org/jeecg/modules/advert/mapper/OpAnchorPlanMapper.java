package org.jeecg.modules.advert.mapper;

import java.util.List;
import org.jeecg.modules.advert.entity.OpAnchorPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.vo.CampaignAnchorVo;

/**
 * @Description: op_anchor_plan
 * @Author: jeecg-boot
 * @Date: 2023-02-28
 * @Version: V1.0
 */
public interface OpAnchorPlanMapper extends BaseMapper<OpAnchorPlan> {

    /**
     * @return List<CampaignAnchorVo>
     * @author xmh
     * @description 主播广告信息
     * @date 2023/3/8 13:48
     */
    List<CampaignAnchorVo> anchorDealInfo();
}
