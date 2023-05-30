package org.jeecg.modules.count.service;

import org.jeecg.modules.count.dto.GameChargeDto;
import org.jeecg.modules.count.vo.CtGameChargeTotal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: ct_gameChargeTotal
 * @Author: jeecg-boot
 * @Date: 2023-05-09
 * @Version: V1.0
 */
public interface ICtGameChargeTotalService extends IService<CtGameChargeTotal> {

    /**
     * @author chenglin
     * @description 自定义查询游戏投放数据
     * @date 14:25 2023/5/10
     **/
    List<GameChargeDto> queryList();

}
