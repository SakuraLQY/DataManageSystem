package org.jeecg.common.game.api;

import java.util.List;
import org.jeecg.common.advert.dto.OpCostDto;
import org.jeecg.common.advert.vo.OpChannelSubAccountModel;
import org.jeecg.common.advert.vo.OpChannelModel;
import org.jeecg.common.advert.vo.OpChannelTypeModel;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.advert.vo.OpPutAccountModel;
import org.jeecg.common.count.vo.CostModel;
import org.jeecg.common.count.vo.CostMoneyModel;

public interface IAdvertApi{

    /**
     * @param id
     * @return org.jeecg.common.advert.vo.OpDealModel
     * @Author lili
     * @Description 根据id得到广告对象
     * @Date 16:46 2023/3/15
     **/
    OpDealModel getOpDeal(Integer id);

    /**
     * @return org.jeecg.common.advert.vo.OpChannelModel
     * @Author lili
     * @Description 根据id得到渠道对象
     * @Date 15:00 2023/5/9
     **/
    OpChannelModel getOpChannel(Integer id);

    /**
     * @param id
     * @return org.jeecg.common.advert.vo.OpChannelTypeModel
     * @Author lili
     * @Description 根据id得到渠道类型对象
     * @Date 15:48 2023/5/16
     **/
    OpChannelTypeModel getOpChannelType(Integer id);

    /**
     * @param dealId
     * @param uniqueId
     * @param deviceId
     * @param serialId
     * @param androidId
     * @param clientIp
     * @return java.lang.String 回调参数
     * @author chenyw
     * @description 获取唯一归因
     * @date 15:17 2023/4/24/024
     **/
    String getUnique(Integer dealId, String uniqueId, String deviceId,
        String serialId, String androidId, String clientIp);

    /**
     * @param pkgId
     * @param idfa
     * @param ip
     * @return java.lang.Integer
     * @author chenyw
     * @description 通过归因获取广告id
     * @date 16:21 2023/4/24/024
     **/
    Integer getDealIdByVisit(Integer pkgId, String idfa, String ip);

    /**
     * @param opCostDto
     * @return java.util.List<org.jeecg.common.count.vo.CostMoneyModel>
     * @author chenyw
     * @description 汇总表获取成本
     * @date 11:50 2023/5/5/005
     **/
    List<CostMoneyModel> getCostMoney(OpCostDto opCostDto);

    /**
     * @param accountId
     * @return org.jeecg.common.advert.vo.OpPutAccountModel
     * @author chenyw
     * @description 获取投放账号
     * @date 20:32 2023/5/8/008
     **/
    OpPutAccountModel getOpPutAccount(Integer accountId);

    /**
     * @param channelSubAccountId
     * @return org.jeecg.common.advert.vo.OpChannelSubAccountModel
     * @author chenyw
     * @description 获取一级游戏包
     * @date 20:46 2023/5/8/008
     **/
    OpChannelSubAccountModel getOpChannelSubAccount(Integer channelSubAccountId);

    /**
     * @param opCostDto
     * @return java.util.List<org.jeecg.common.count.vo.CostModel>
     * @author chenyw
     * @description 获取成本
     * @date 17:17 2023/5/22/022
     **/
    List<CostModel> getCostModel(OpCostDto opCostDto);

}
