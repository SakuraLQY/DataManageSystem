package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.RetainAnalyzeDto;
import org.jeecg.modules.count.entity.RetainAnalyze;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.vo.RetainLoyalVo;
import org.jeecg.modules.count.vo.RetainNewLoyalVo;

/**
 * @Description: 留存分析
 * @Author: jeecg-boot
 * @Date:   2023-05-17
 * @Version: V1.0
 */
public interface IRetainAnalyzeService extends IService<RetainAnalyze> {

    /**@param retainAnalyzeDto
     * @author chenglin
     * @description 用来查询留存方面的数据信息
     * @date  15:47 2023/5/17
     **/
    List<RetainLoyalVo> queryRetainList(RetainAnalyzeDto retainAnalyzeDto);

    /**@param retainAnalyzeDto
     * @author chenglin
     * @description
     * @date 15:48 2023/5/17
     **/
    List<RetainNewLoyalVo> queryRetainNewLoyalList(RetainAnalyzeDto retainAnalyzeDto);
}
