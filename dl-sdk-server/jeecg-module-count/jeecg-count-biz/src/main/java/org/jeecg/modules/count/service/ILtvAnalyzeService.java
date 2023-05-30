package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.LtvDto;
import org.jeecg.modules.count.entity.LtvAnalyze;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.vo.LtvPaybackVo;
import org.jeecg.modules.count.vo.LtvVo;

/**
 * @Description: LTV分析
 * @Author: jeecg-boot
 * @Date:   2023-05-13
 * @Version: V1.0
 */
public interface ILtvAnalyzeService extends IService<LtvAnalyze> {

    /**@param ltvDto
     * @author chenglin
     * @description Ltv数据分析的接口开发
     * @date  17:12 2023/5/15
     **/
    List<LtvVo> qeuryLtvList(LtvDto ltvDto);
}
