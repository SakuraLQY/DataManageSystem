package org.jeecg.common.advert.api;

import org.jeecg.common.advert.vo.OpChannelModel;
import org.jeecg.common.advert.vo.OpDealModel;
/**
 * @author lili
 * @version V1.0
 * @description:
 * @date: 2023/2/14 11:22
 **/
public interface AdvertApi {

   /**
    * @param id
    * @return org.jeecg.common.advert.vo.OpDealModel
    * @Author lili
    * @Description 根据ID得到广告
    * @Date 14:29 2023/5/9
    **/
   OpDealModel getOpDeal(Integer id);

   /**
    * @return org.jeecg.common.advert.vo.OpChannelModel
    * @Author lili
    * @Description 根据id得到渠道对象
    * @Date 15:00 2023/5/9
    **/
   OpChannelModel getOpChannel(Integer id);

}
