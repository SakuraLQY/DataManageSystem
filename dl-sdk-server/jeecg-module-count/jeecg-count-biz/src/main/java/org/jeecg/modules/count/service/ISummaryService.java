package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.modules.count.dto.SummaryDealInfoDataDto;
import org.jeecg.modules.count.vo.SummaryDealInfoDataVo;
import org.jeecg.modules.count.vo.SummaryVo;

/**
 * @Description: 数据总表
 * @Author: jeecg-boot
 * @Date:   2023-04-19
 * @Version: V1.0
 */
public interface ISummaryService {

    /**
     * @param summaryDto
     * @return java.util.List<org.jeecg.modules.count.vo.SummaryVo>
     * @author chenyw
     * @description 获取数据总表数据
     * @date 18:00 2023/5/6/006
     **/
    List<SummaryVo> getSummary(SummaryDto summaryDto);

    /**
     * @param summaryDealInfoDataDto
     * @return org.jeecg.modules.count.vo.SummaryDealInfoDataVo
     * @author chenyw
     * @description 获取数据总表的 广告数据
     * @date 17:45 2023/5/8/008
     **/
    SummaryDealInfoDataVo getDealInfoData(SummaryDealInfoDataDto summaryDealInfoDataDto);
}
