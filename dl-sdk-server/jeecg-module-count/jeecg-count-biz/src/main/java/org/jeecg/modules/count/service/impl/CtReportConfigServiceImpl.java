package org.jeecg.modules.count.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.beanutils.ConvertUtils;
import org.jeecg.common.advert.vo.OpChannelModel;
import org.jeecg.common.advert.vo.OpChannelSubAccountModel;
import org.jeecg.common.advert.vo.OpChannelTypeModel;
import org.jeecg.common.api.GameApi;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpGameModel;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.modules.count.bo.ReportConfigWeek;
import org.jeecg.modules.count.bo.ReportConfigWeekData;
import org.jeecg.modules.count.constant.ReportConfigName;
import org.jeecg.modules.count.entity.CtReportConfig;
import org.jeecg.modules.count.mapper.CtReportConfigMapper;
import org.jeecg.modules.count.service.ICtReportConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.ObjectUtils;

/**
 * @Description: ct_report_config
 * @Author: jeecg-boot
 * @Date: 2023-05-19
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class CtReportConfigServiceImpl extends
    ServiceImpl<CtReportConfigMapper, CtReportConfig> implements ICtReportConfigService {

    @Autowired
    private IGameApi gameApi;
    @Autowired
    private IAdvertApi advertApi;

    @Override
    public List<JSONObject> getByConfigName(String configName) {
        CtReportConfig ctReportConfig = getOne(
            new LambdaQueryWrapper<CtReportConfig>().eq(CtReportConfig::getConfigName, configName));
        if (null == ctReportConfig) {
            return null;
        }
        List<JSONObject> result = new ArrayList<>();
        if (ReportConfigName.WEEK_REPORT.equals(configName)) {
            List<ReportConfigWeek> configList = JSONObject.parseArray(ctReportConfig.getConfig(),
                ReportConfigWeek.class);
            for (ReportConfigWeek reportConfigWeek : configList) {
                for (ReportConfigWeekData reportConfigWeekData : reportConfigWeek.getData()) {
                    JSONObject json = new JSONObject();
                    json.put("firstGroup", reportConfigWeek.getFirstGroup());
                    json.put("secondGroup", reportConfigWeekData.getSecondGroup());
                    json.put("gameNickName", reportConfigWeekData.getGameNickName());
                    json.put("channelNickName", reportConfigWeekData.getChannelNickName());
                    List gameNameList = new ArrayList();
                    if (CollectionUtil.isNotEmpty(reportConfigWeekData.getGameId())) {
                        for (Integer gameId : reportConfigWeekData.getGameId()) {
                            OpGameModel opGameModel = gameApi.getOpGame(gameId);
                            if (opGameModel != null) {
                                gameNameList.add(opGameModel.getGameName());
                            }
                        }
                    }
                    json.put("gameName", String.join(",", gameNameList));
                    json.put("gameId", reportConfigWeekData.getGameId());
                    List subGameNameList = new ArrayList();
                    if (CollectionUtil.isNotEmpty(reportConfigWeekData.getSubGameId())) {
                        for (Integer subGameId : reportConfigWeekData.getSubGameId()) {
                            OpSubGameModel opSubGameModel = gameApi.getOpSubGame(subGameId);
                            if (opSubGameModel != null) {
                                subGameNameList.add(opSubGameModel.getGameName());
                            }
                        }
                    }
                    json.put("subGameName", String.join(",", subGameNameList));
                    json.put("subGameId", reportConfigWeekData.getSubGameId());
                    List pkgNameList = new ArrayList();
                    if (CollectionUtil.isNotEmpty(reportConfigWeekData.getPkgId())) {
                        for (Integer pkgId : reportConfigWeekData.getPkgId()) {
                            OpPkgModel opPkgModel = gameApi.getOpPkgById(pkgId);
                            if (opPkgModel != null) {
                                pkgNameList.add(opPkgModel.getPkgName());
                            }
                        }
                    }
                    json.put("pkgName", String.join(",", pkgNameList));
                    json.put("pkgId", reportConfigWeekData.getPkgId());
                    if (CollectionUtil.isNotEmpty(reportConfigWeekData.getChannelTypeId())) {
                        List channelTypeNameList = new ArrayList();
                        for (Integer channelTypeId : reportConfigWeekData.getChannelTypeId()) {
                            OpChannelTypeModel opChannelTypeModel = advertApi.getOpChannelType(
                                channelTypeId);
                            if (opChannelTypeModel != null) {
                                channelTypeNameList.add(opChannelTypeModel.getTypeName());
                            }
                        }
                        json.put("channelTypeName", String.join(",", channelTypeNameList));
                        json.put("channelTypeId", reportConfigWeekData.getChannelTypeId());
                    }
                    List channelNameList = new ArrayList();
                    if (CollectionUtil.isNotEmpty(reportConfigWeekData.getChannelId())) {
                        for (Integer channelId : reportConfigWeekData.getChannelId()) {
                            OpChannelModel opChannelModel = advertApi.getOpChannel(channelId);
                            if (opChannelModel != null) {
                                channelNameList.add(opChannelModel.getChannelName());
                            }
                        }
                    }
                    json.put("channelName", String.join(",", channelNameList));
                    json.put("channelId", reportConfigWeekData.getChannelId());
                    List subAccountNameList = new ArrayList();
                    if (CollectionUtil.isNotEmpty(reportConfigWeekData.getChannelSubAccountId())) {
                        for (Integer channelSubAccountId : reportConfigWeekData.getChannelSubAccountId()) {
                            OpChannelSubAccountModel opChannelSubAccountModel = advertApi.getOpChannelSubAccount(
                                channelSubAccountId);
                            if (opChannelSubAccountModel != null) {
                                subAccountNameList.add(
                                    opChannelSubAccountModel.getSubAccountName());
                            }
                        }
                    }
                    json.put("channelSubAccountName", String.join(",", subAccountNameList));
                    json.put("channelSubAccountId", reportConfigWeekData.getChannelSubAccountId());
                    result.add(json);
                }
            }
        }
        return result;
    }
}
