package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.modules.advert.dto.OpDealDto;
import org.jeecg.modules.advert.entity.OpDeal;

/**
 * @Description: ad_deal
 * @Author: jeecg-boot
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public interface IOpDealService extends IService<OpDeal> {

    /**
     * @param opDeal:    广告
     * @param startDate: 开始日期
     * @param endDate:   结束日期
     * @return QueryWrapper<OpDeal>
     * @author xmh
     * @description 构建基础查询
     * @date 2023/3/6 16:07
     */
    QueryWrapper<OpDeal> baseQuery(OpDeal opDeal, String startDate, String endDate);

    /**
     * @param opDealDto:       广告数据
     * @param channelId:       渠道
     * @param iosTrackUrl:     IOS监测链接前缀
     * @param androidTrackUrl: Android监测链接前缀
     * @return List<OpDeal>
     * @author xmh
     * @description 添加一条广告到广告表中
     * @date 2023/3/6 16:07
     */
    List<OpDeal> addDeal(OpDealDto opDealDto, Integer channelId, String iosTrackUrl,
        String androidTrackUrl);

    /**
     * @param opDealDto: 广告信息
     * @author xmh
     * @description 更新广告基础信息
     * @date 2023/3/6 16:08
     */
    void updateDeal(OpDealDto opDealDto);
}
