package org.jeecg.modules.advert.service;

import java.util.List;
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListListResponse;
import org.jeecg.modules.advert.dto.OpJrttPromotionDto;
import org.jeecg.modules.advert.entity.OpJrttPromotion;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: op_jrtt_promotion
 * @Author: jeecg-boot
 * @Date:   2023-02-22s
 * @Version: V1.0
 */
public interface IOpJrttPromotionService extends IService<OpJrttPromotion> {

    /**
     * @param opJrttPromotionDto
     * @author chenyw
     * @description 保存今日头条广告信息
     * @date 9:27 2023/2/25/025
     **/
    void saveOpJrttPromotion(OpJrttPromotionDto opJrttPromotionDto);

    /**
     * @param opJrttPromotionDto
     * @author chenyw
     * @description 更新今日头条广告信息
     * @date 17:16 2023/3/14/014
     **/
    void updateOpJrttPromotion(OpJrttPromotionDto opJrttPromotionDto);
}
