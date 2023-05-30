package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.PayAnalysisDto;
import org.jeecg.modules.count.entity.PayAnalysis;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.vo.PayAnalysisVo;

/**
 * @Description: pay_analysis
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
public interface IPayAnalysisService extends IService<PayAnalysis> {

    /**@param payAnalysisDto
     * @author chenglin
     * @description 查询付费数据接口
     * @date 15:26 2023/5/24
     **/
    List<PayAnalysisVo> queryList(PayAnalysisDto payAnalysisDto);
}
