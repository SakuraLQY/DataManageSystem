package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.LtvAnalysisDto;
import org.jeecg.modules.count.entity.LtvAnalysis;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.vo.LtvAnalysisVo;

/**
 * @Description: ltv_analysis
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
public interface ILtvAnalysisService extends IService<LtvAnalysis> {

    /**@param ltvAnalysisDto
     * @author chenglin
     * @description
     * @date 9:38 2023/5/25
     **/
    List<LtvAnalysisVo> queryList(LtvAnalysisDto ltvAnalysisDto);
}
