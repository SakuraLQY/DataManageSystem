package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.modules.advert.dto.OpDealDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpJrttAction;

/**
 * @Description: op_jrtt_action
 * @Author: jeecg-boot
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public interface IOpJrttActionService extends IService<OpJrttAction> {

    /**
     * @return java.util.List<org.jeecg.modules.advert.entity.OpJrttAction>
     * @author chenyw
     * @description 根据类型查询
     * @date 19:09 2023/3/8/008
     **/
    List<OpJrttAction> queryByActionType();

    /**
     * @param opJrttAction
     * @author chenyw
     * @description 保存行为
     * @date 20:41 2023/3/8/008
     **/
    void saveAction(OpJrttAction opJrttAction);
}
