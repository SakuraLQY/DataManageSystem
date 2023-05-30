package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.StatHourDto;
import org.jeecg.modules.count.entity.StatHour;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.vo.StatHourVo;

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
}
