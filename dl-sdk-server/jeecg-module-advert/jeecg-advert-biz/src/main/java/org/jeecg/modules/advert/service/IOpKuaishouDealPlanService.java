package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.modules.advert.dto.OpKuaishouDealPlanDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpKuaishouDealPlan;
import org.jeecg.modules.advert.vo.MessageVo;
import org.jeecg.modules.advert.vo.OpKuaishouDealPlanVo;

/** @Description: op_kuaishou_deal @Author: jeecg-boot @Date: 2023-03-09 @Version: V1.0 */
public interface IOpKuaishouDealPlanService extends IService<OpKuaishouDealPlan> {

  /**
   * @param page: 分页信息
   * @param deal: 查询信息
   * @param campaignId: 快手计划ID
   * @param startDate: 开始日期
   * @param endDate: 结束日期
   * @return IPage<OpKuaishouDealPlanVo>
   * @author xmh
   * @description 分页获取快手广告计划信息
   * @date 2023/3/14 16:11
   */
  IPage<OpKuaishouDealPlanVo> getByPage(
      Page<OpKuaishouDealPlan> page,
      OpDeal deal,
      Integer campaignId,
      String startDate,
      String endDate);

  /**
   * @param opKuaishouDealDto: 快手广告计划信息
   * @return List<MessageVo>
   * @author xmh
   * @description 添加快手广告计划
   * @date 2023/3/9 19:06
   */
  List<MessageVo> addDeal(OpKuaishouDealPlanDto opKuaishouDealDto);

  /**
   * @param opKuaishouDealDto: 广告信息
   * @author xmh
   * @description 更新快手广告计划信息
   * @date 2023/3/15 10:12
   */
  void updateDeal(OpKuaishouDealPlanDto opKuaishouDealDto);

  /**
   * @param opKuaishouDealPlanDto: 补建信息
   * @author xmh
   * @description 补建广告计划
   * @date 2023/3/16 10:31
   */
  void makeUpDealPlan(OpKuaishouDealPlanDto opKuaishouDealPlanDto);

  /**
   * @param opKuaishouDealPlanDto: 预算
   * @author xmh
   * @description 修改计划预算
   * @date 2023/3/16 13:43
   */
  void modifyBudget(OpKuaishouDealPlanDto opKuaishouDealPlanDto);

  /**
   * @param list: 广告ID集合
   * @author xmh
   * @description 删除多条广告计划
   * @date 2023/3/3 15:42
   */
  void deleteDeal(List<String> list);

  /**
   * @param id: 广告ID
   * @author xmh
   * @description 删除广告计划
   * @date 2023/3/3 15:42
   */
  void deleteDeal(String id);

  /**
   * @param id 广告id
   * @descript 添加广告id进apk
   * @author fkh
   * @date 2023/3/22
   */
  void packDeal(Integer id) throws Exception;
}
