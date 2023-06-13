package org.jeecg.modules.count.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.modules.count.dto.StatHourDto;
import org.jeecg.modules.count.entity.StatHour;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.modal.StatHourModal;
import org.jeecg.modules.count.vo.StatHourVo;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: cooperator_stat
 * @Author: jeecg-boot
 * @Date:   2023-05-29
 * @Version: V1.0
 */
public interface IStatHourService extends IService<StatHour> {

    /**@param statHourDto
     * @author chenglin
     * @description 查询合作商渠道接口
     * @date 11:15 2023/5/29
     **/
    List<StatHourVo> queryList(StatHourDto statHourDto,String username);

    /**@param statHourDto
     * @author chenglin
     * @description 合作商渠道导出表
     * @date 15:38 2023/6/13
     **/
    ModelAndView exportExcel(HttpServletRequest request, StatHourDto statHourDto, Class<StatHourModal> statHourModalClass, String title);
}
