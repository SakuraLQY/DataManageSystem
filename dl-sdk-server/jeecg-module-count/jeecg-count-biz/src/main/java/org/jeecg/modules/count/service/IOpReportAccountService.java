package org.jeecg.modules.count.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.modules.count.dto.ReportAccountDto;
import org.jeecg.modules.count.entity.OpReport;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.modal.ReportAccountModal;
import org.jeecg.modules.count.vo.ReportAccountBillVo;
import org.jeecg.modules.count.vo.ReportAccountVo;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 数据报表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
public interface IOpReportAccountService extends IService<OpReport> {

    /**@param reportAccountDto
     * @author chenglin
     * @description 查询账号报表的信息
     * @date 16:50 2023/5/22
     **/
    List<ReportAccountVo> queryAccountList(ReportAccountDto reportAccountDto,String username);

    /**@param id
     * @author chenglin
     * @description 点击查询显示账单信息
     * @date 11:50 2023/5/25
     **/
    List<ReportAccountBillVo> queryBillByAccountId(Integer id);

    /**@param reportAccountDto
     * @author chenglin
     * @description 补充导出excel
     * @date 16:45 2023/5/30
     **/
    ModelAndView exportExcel(HttpServletRequest request, ReportAccountDto reportAccountDto, Class<ReportAccountModal> reportAccountClass, String
        title);
}
