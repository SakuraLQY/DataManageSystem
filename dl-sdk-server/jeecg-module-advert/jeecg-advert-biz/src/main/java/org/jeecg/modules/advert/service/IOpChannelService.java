package org.jeecg.modules.advert.service;

import java.util.List;
import org.jeecg.modules.advert.entity.OpChannel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: op_channel
 * @Author: jeecg-boot
 * @Date:   2023-01-05
 * @Version: V1.0
 */
public interface IOpChannelService extends IService<OpChannel> {

    /**
     * @param typeId 渠道类型-id
     * @return java.util.List<org.jeecg.modules.advert.entity.OpChannel>
     * @Author lili
     * @Description 根据typeId查询列表
     * @Date 13:43 2023/1/6
     **/
    List<OpChannel> selectByTypeId(Integer typeId);

}
