package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.ComputePayDto;
import org.jeecg.modules.count.entity.ComputeMoney;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.vo.ComputePayVo;

/**
 * @Description: data_tool
 * @Author: jeecg-boot
 * @Date:   2023-05-23
 * @Version: V1.0
 */
public interface IComputeMoneyService extends IService<ComputeMoney> {

    /**@param computePayDto
     * @author chenglin
     * @description
     * @date 14:58 2023/5/23
     **/
    List<ComputePayVo> queryList(ComputePayDto computePayDto);
}
