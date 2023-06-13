package org.jeecg.modules.count.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.count.dto.StatDealDto;
import org.jeecg.modules.count.entity.StatDeal;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.vo.StatDealVo;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: stat_deal
 * @Author: jeecg-boot
 * @Date:   2023-05-30
 * @Version: V1.0
 */
public interface IStatDealService extends IService<StatDeal> {

    /**@param statDealDto
     * @author chenglin
     * @description 查询单位合作数据接口
     * @date 10:33 2023/5/30
     **/
    List<StatDealVo> queryList(StatDealDto statDealDto,String username);

    ModelAndView exportExcel(HttpServletRequest request, StatDealDto statDealDto, Class<StatDealVo>clazz, String title);
}
