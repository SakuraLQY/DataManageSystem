package org.jeecg.modules.count.service;

import javax.servlet.http.HttpServletRequest;
import org.jeecg.modules.count.modal.SummaryLaunchModal;
import org.jeecg.modules.count.vo.SummaryLaunchVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import org.jeecg.modules.count.dto.SummaryLaunchDto;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 数据投放
 * @Author: jeecg-boot
 * @Date: 2023-05-10
 * @Version: V1.0
 */
public interface ISummaryLaunchService extends IService<SummaryLaunchDto> {

    /**
     * @param summaryLaunch
     * @author chenglin
     * @description 数据投放数据的查询
     * @date 17:01 2023/05/11
     **/
    List<SummaryLaunchVo> queryList(SummaryLaunchDto summaryLaunch);

    /**@param summaryLaunchDto
     * @author chenglin
     * @description 数据投放数据的导出操作
     * @date  13:53 2023/6/13
     **/
    ModelAndView exportExcel(HttpServletRequest request, SummaryLaunchDto summaryLaunchDto, Class<SummaryLaunchModal> summaryLaunchModalClass, String title);
}
