package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.DetailDto;
import org.jeecg.modules.count.dto.SummaryDealInfoDataDto;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.modules.count.vo.DetailVo;
import org.jeecg.modules.count.vo.SummaryDealInfoDataVo;
import org.jeecg.modules.count.vo.SummaryVo;

/**
 * @Description: 数据总表
 * @Author: jeecg-boot
 * @Date:   2023-04-19
 * @Version: V1.0
 */
public interface IDetailService {

    /**
     * @param detailDto
     * @return java.util.List<org.jeecg.modules.count.vo.DetailVo>
     * @author chenyw
     * @description 获取详细分析数据
     * @date 18:00 2023/5/6/006
     **/
    List<DetailVo> getDaily(DetailDto detailDto);

}
