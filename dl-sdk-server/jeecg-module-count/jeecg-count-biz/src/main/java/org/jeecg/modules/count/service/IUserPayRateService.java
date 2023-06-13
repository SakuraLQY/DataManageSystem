package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.SummaryDealInfoDataDto;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.modules.count.dto.UserPayRateDto;
import org.jeecg.modules.count.vo.SummaryDealInfoDataVo;
import org.jeecg.modules.count.vo.SummaryVo;
import org.jeecg.modules.count.vo.UserPayRateVo;

/**
 * @Description: 用户付费率
 * @Author: jeecg-boot
 * @Date:   2023-04-19
 * @Version: V1.0
 */
public interface IUserPayRateService {

    /**
     * @param userPayRateDto
     * @return java.util.List<org.jeecg.modules.count.vo.UserPayRateVo>
     * @author chenyw
     * @description 获取用户付费率
     * @date 17:32 2023/5/17/017
     **/
    List<UserPayRateVo> getPayRate(UserPayRateDto userPayRateDto);

}
