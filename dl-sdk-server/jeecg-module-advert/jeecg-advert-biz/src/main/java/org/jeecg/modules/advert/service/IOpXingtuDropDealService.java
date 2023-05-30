package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.jeecg.modules.advert.dto.OpXingtuDropDealDto;
import org.jeecg.modules.advert.entity.OpXingtuDropDeal;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.vo.OpXingtuDropDealVo;
import org.jeecg.modules.advert.vo.MessageVo;

/**
 * @Description: op_xingtu_drop_deal
 * @Author: jeecg-boot
 * @Date: 2023-03-07
 * @Version: V1.0
 */
public interface IOpXingtuDropDealService extends IService<OpXingtuDropDeal> {

    /**
     * @param page:             分页信息
     * @param opXingtuDropDeal: 查询信息
     * @param startDate:        开始日期
     * @param endDate:          结束日期
     * @return IPage<OpXingtuDropDealVo>
     * @author xmh
     * @description 分页获取星图投放广告信息
     * @date 2023/3/7 16:09
     */
    IPage<OpXingtuDropDealVo> getByPage(Page<OpXingtuDropDealVo> page,
        OpXingtuDropDeal opXingtuDropDeal, String startDate, String endDate);

    /**
     * @param opXingtuDropDealDto: 广告信息
     * @return XingtuCampaignVo
     * @author xmh
     * @description 批量创建投放广告
     * @date 2023/3/7 15:06
     */
    List<MessageVo> addDropDeal(OpXingtuDropDealDto opXingtuDropDealDto);

    /**
     * @param opXingtuDropDealDto: 广告信息
     * @author xmh
     * @description 更新投放广告
     * @date 2023/3/7 15:58
     */
    void updateDropDeal(OpXingtuDropDealDto opXingtuDropDealDto);

    /**
     * @param opXingtuDropDealDto: 补建信息
     * @author xmh
     * @description 补建投放广告
     * @date 2023/3/7 17:03
     */
    void makeUpDropDeal(OpXingtuDropDealDto opXingtuDropDealDto);
}
