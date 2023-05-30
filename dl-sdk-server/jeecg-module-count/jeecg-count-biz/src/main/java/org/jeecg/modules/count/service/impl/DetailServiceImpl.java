package org.jeecg.modules.count.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.advert.dto.OpCostDto;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.count.vo.CostMoneyModel;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpGameModel;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.modules.count.bo.DetailDailyBo;
import org.jeecg.modules.count.dto.DetailDto;
import org.jeecg.modules.count.service.ICtDailyService;
import org.jeecg.modules.count.service.ICtOrderService;
import org.jeecg.modules.count.service.IDetailService;
import org.jeecg.modules.count.vo.DetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl implements IDetailService {

    @Autowired
    private IAdvertApi advertApi;
    @Autowired
    private ICtDailyService ctDailyService;
    @Autowired
    private ICtOrderService ctOrderService;
    @Autowired
    private IGameApi gameApi;


    @Override
    public List<DetailVo> getDaily(DetailDto detailDto) {
        OpCostDto opCostDto = new OpCostDto();
        // 初始化时间 + 00:00:00
        if (StringUtils.isNotEmpty(detailDto.getRegStartTime())) {
            detailDto.setRegStartTime(detailDto.getRegStartTime() + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(detailDto.getRegEndTime())) {
            detailDto.setRegEndTime(detailDto.getRegEndTime() + " 23:59:59");
        }
        BeanUtils.copyProperties(detailDto, opCostDto);
        // 获取成本
        List<CostMoneyModel> costMoneyModelList = advertApi.getCostMoney(opCostDto);
        // 获取每日数据
        List<DetailDailyBo> detailDailyBoList = ctDailyService.getDetailDaily(detailDto);
        List<DetailVo> detailVoList = calcDaily(costMoneyModelList, detailDailyBoList, detailDto);
        return detailVoList;
    }

    /**
     * @param costMoneyModelList
     * @param detailDailyBoList
     * @return java.util.List<org.jeecg.modules.count.vo.DetailVo>
     * @author chenyw
     * @description 计算每日数据
     * @date 13:40 2023/5/10/010
     **/
    private List<DetailVo> calcDaily(List<CostMoneyModel> costMoneyModelList,
        List<DetailDailyBo> detailDailyBoList, DetailDto detailDto) {
        // 固定数据列
        String gameName = "全部游戏";
        String channelName = "全部渠道";
        String dealName = "全部广告";
        if (CollectionUtil.isNotEmpty(detailDto.getGameId())) {
            if (detailDto.getGameId().size() == 1) {
                OpGameModel opGameModel = gameApi.getOpGame(detailDto.getGameId().get(0));
                gameName = opGameModel.getGameName();
            } else {
                gameName = "多款游戏";
            }
            if (CollectionUtil.isNotEmpty(detailDto.getSubGameId())) {
                if (detailDto.getSubGameId().size() == 1) {
                    OpSubGameModel opSubGameModel = gameApi.getOpSubGame(
                        detailDto.getSubGameId().get(0));
                    gameName += opSubGameModel.getGameName();
                } else {
                    gameName += "-多款子游戏";
                }
            } else {
                gameName += "-全部子游戏";
            }
            if (CollectionUtil.isNotEmpty(detailDto.getPkgId())) {
                if (detailDto.getPkgId().size() == 1) {
                    OpPkgModel opPkgModel = gameApi.getOpPkgById(detailDto.getPkgId().get(0));
                    gameName += opPkgModel.getPkgName();
                } else {
                    gameName += "多款渠道游戏包";
                }
            } else {
                gameName += "-全部渠道游戏包";
            }
        }
        // TODO 渠道
        // 广告
        if (CollectionUtil.isNotEmpty(detailDto.getDealId())) {
            if (detailDto.getDealId().size() == 1) {
                OpDealModel opDealModel = advertApi.getOpDeal(detailDto.getDealId().get(0));
                dealName = opDealModel.getDealName();
            } else {
                dealName = "多个广告";
            }
        }
        List<DetailVo> result = new ArrayList<>();
        // 成本map
        String totalId = "合计";
        DetailDailyBo totalDetailDailyBo = new DetailDailyBo();
        totalDetailDailyBo.setDay(totalId);
        Map<String, CostMoneyModel> detailCostMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(costMoneyModelList)) {
            CostMoneyModel totalCostMoneyModel = new CostMoneyModel();
            totalCostMoneyModel.setId(totalId);
            for (CostMoneyModel costMoneyModel : costMoneyModelList) {
                detailCostMap.put(costMoneyModel.getId(), costMoneyModel);
                totalCostMoneyModel.setCostMoney(
                    totalCostMoneyModel.getCostMoney().add(costMoneyModel.getCostMoney()));
            }
            // 设置合计
            detailCostMap.put(totalId, totalCostMoneyModel);
        }
        for (DetailDailyBo detailDailyBo : detailDailyBoList) {
            DetailVo detailVo = new DetailVo();
            detailVo.setGameName(gameName);
            detailVo.setDealName(dealName);
            detailVo.setChannelName(channelName);
            CostMoneyModel costMoneyModel = detailCostMap.get(detailDailyBo.getDay());
            calcDetailBo(costMoneyModel, detailDailyBo, detailVo);
            // 累加bo的数据
            calcTotalDetailBo(detailDailyBo, totalDetailDailyBo);
            result.add(detailVo);
        }
        DetailVo totalDetailVo = new DetailVo();
        totalDetailVo.setGameName(gameName);
        totalDetailVo.setDealName(dealName);
        totalDetailVo.setChannelName(channelName);
        totalDetailVo.setDay(totalId);
        CostMoneyModel costMoneyModel = detailCostMap.get(totalId);
        // 计算汇总数据
        calcDetailBo(costMoneyModel, totalDetailDailyBo, totalDetailVo);
        result.add(totalDetailVo);
        return result;
    }

    /**
     * @param costMoneyModel
     * @param detailDailyBo
     * @param detailVo
     * @author chenyw
     * @description 计算详情表
     * @date 14:00 2023/5/10/010
     **/
    private void calcDetailBo(CostMoneyModel costMoneyModel, DetailDailyBo detailDailyBo,
        DetailVo detailVo) {
        BeanUtils.copyProperties(detailDailyBo, detailVo);
        if (null != costMoneyModel) {
            detailVo.setCostMoney(costMoneyModel.getCostMoney());
        }
        // 新增付费率
        if (detailDailyBo.getCountUser() != 0) {
            detailVo.setFirstMoneyRate(detailDailyBo.getFirstMoney().multiply(new BigDecimal(100))
                .divide(BigDecimal.valueOf(detailDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
        }
        // 新增ARPU
        if (detailDailyBo.getCountUser() != 0) {
            detailVo.setFirstArpu(detailDailyBo.getFirstMoney()
                .divide(BigDecimal.valueOf(detailDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
        }
        // 新增ARRPU
        if (detailDailyBo.getFirstPayuser() != 0) {
            detailVo.setFirstArppu(detailVo.getFirstMoney()
                .divide(BigDecimal.valueOf(detailDailyBo.getFirstPayuser()), 2,
                    RoundingMode.HALF_UP));
        }
        // 老用户
        // 老用户付费人数
        BigDecimal oldPayuser = BigDecimal.valueOf(
            detailDailyBo.getAlivePayuser() - detailDailyBo.getFirstPayuser());
        // 老用户活跃数
        BigDecimal oldDau = BigDecimal.valueOf(
            detailDailyBo.getCountDau() - detailDailyBo.getCountUser());
        // 老用户支付金额
        BigDecimal oldMoney = detailDailyBo.getAliveMoney()
            .subtract(detailDailyBo.getFirstMoney());
        // 老用户付费率
        if (!BigDecimal.ZERO.equals(oldDau)) {
            detailVo.setOldUserPayRate(oldPayuser.multiply(BigDecimal.valueOf(100))
                .divide(oldDau, 2, RoundingMode.HALF_UP));
        }
        // 老用户ARPU
        if (!BigDecimal.ZERO.equals(oldDau)) {
            detailVo.setOldUserArpu(oldMoney.divide(oldDau, 2, RoundingMode.HALF_UP));
        }
        // 老用户ARPPU
        if (!BigDecimal.ZERO.equals(oldPayuser)) {
            detailVo.setOldUserArppu(oldMoney.divide(oldPayuser, 2, RoundingMode.HALF_UP));
        }
        if (detailDailyBo.getCountDau() != 0) {
            // 活跃付费率
            detailVo.setAliveMoneyRate(
                BigDecimal.valueOf(detailDailyBo.getAlivePayuser())
                    .divide(BigDecimal.valueOf(detailDailyBo.getCountDau()), 2,
                        RoundingMode.HALF_UP));
            // ARPU
            detailVo.setArpu(detailDailyBo.getAliveMoney()
                .divide(BigDecimal.valueOf(detailDailyBo.getCountDau()), 2,
                    RoundingMode.HALF_UP));

        }
        if (detailDailyBo.getAlivePayuser() != 0) {
            // ARPPU
            detailVo.setArppu(detailDailyBo.getAliveMoney()
                .divide(BigDecimal.valueOf(detailDailyBo.getAlivePayuser()), 2,
                    RoundingMode.HALF_UP));
        }

    }

    /**
     * @param detailDailyBo
     * @param totalDetailDailyBo
     * @author chenyw
     * @description 计算汇总合计
     * @date 14:55 2023/5/10/010
     **/
    private void calcTotalDetailBo(DetailDailyBo detailDailyBo, DetailDailyBo totalDetailDailyBo) {
        totalDetailDailyBo.setCountActive(
            totalDetailDailyBo.getCountActive() + detailDailyBo.getCountActive());
        totalDetailDailyBo.setCountUser(
            totalDetailDailyBo.getCountUser() + detailDailyBo.getCountUser());
        totalDetailDailyBo.setCountValidUserDev(
            totalDetailDailyBo.getCountValidUserDev() + detailDailyBo.getCountValidUserDev());
        totalDetailDailyBo.setFirstPayuser(
            totalDetailDailyBo.getFirstPayuser() + detailDailyBo.getFirstPayuser());
        totalDetailDailyBo.setFirstMoney(
            totalDetailDailyBo.getFirstMoney().add(detailDailyBo.getFirstMoney()));
        totalDetailDailyBo.setTotalMoney(
            totalDetailDailyBo.getTotalMoney().add(detailDailyBo.getTotalMoney()));
        totalDetailDailyBo.setTotalPayuser(
            totalDetailDailyBo.getTotalPayuser() + detailDailyBo.getTotalPayuser());
        totalDetailDailyBo.setCountDau(
            totalDetailDailyBo.getCountDau() + detailDailyBo.getCountDau());
        totalDetailDailyBo.setAlivePayuser(
            totalDetailDailyBo.getAlivePayuser() + detailDailyBo.getAlivePayuser());
        totalDetailDailyBo.setAliveMoney(
            totalDetailDailyBo.getAliveMoney().add(detailDailyBo.getAliveMoney()));
    }
}
