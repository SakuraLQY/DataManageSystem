package org.jeecg.modules.advert.service;

import java.util.List;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.advert.dto.OpJrttAssetsDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpJrttEvents;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.vo.EventExternalActionVo;

/**
 * @Description: op_jrtt_events
 * @Author: jeecg-boot
 * @Date:   2023-02-16
 * @Version: V1.0
 */
public interface IOpJrttEventsService extends IService<OpJrttEvents> {

    /**
     * @param opJrttEvents
     * @Author lili
     * @Description 创建事件- 今日头条
     * @Date 15:48 2023/2/14
     **/
    void add(OpJrttEvents opJrttEvents);

    /**
     * @param opDeal
     * @return java.util.List<org.jeecg.modules.advert.vo.LabelAndValVo>
     * @Author lili
     * @Description 查询资产下所有的事件
     * @Date 17:13 2023/3/6
     **/
    List<EventExternalActionVo> getExternalAction(OpDeal opDeal);

    /**
     * @param opJrttAssets
     * @Author lili
     * @Description 同步事件- 今日头条
     * @Date 15:48 2023/2/14
     **/
    void syncEvents(OpJrttAssetsDto opJrttAssets);

}
