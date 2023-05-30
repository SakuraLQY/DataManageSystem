package org.jeecg.modules.game.service.impl;

import static org.jeecg.common.constant.KafkaKeyConstant.GROUP_SDK_START;

import com.alibaba.fastjson.JSON;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.KafkaTopicConstant;
import org.jeecg.common.constant.SwitchConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.bo.PkgChannelConfJrtt;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.sign.context.SdkContext;
import org.jeecg.config.sign.context.SdkInfo;
import org.jeecg.modules.game.bo.OpCommodityBo;
import org.jeecg.modules.game.dto.SdkConfDto;
import org.jeecg.modules.game.dto.SdkIosConfDto;
import org.jeecg.modules.game.dto.SdkStartDataDto;
import org.jeecg.modules.game.entity.OpPkg;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.service.IOpCommodityService;
import org.jeecg.modules.game.service.IOpPkgService;
import org.jeecg.modules.game.service.IOpPrivacyPolicyService;
import org.jeecg.modules.game.service.IOpSubGameService;
import org.jeecg.modules.game.service.SdkGameService;
import org.jeecg.modules.game.vo.PrivacyAndUserProtocolVo;
import org.jeecg.modules.game.vo.SdkConfRes;
import org.jeecg.modules.game.vo.SdkIosConfRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author lili
 * @Description sdk游戏
 * @Date 2023-03-29
 **/
@Service
@Slf4j
public class SdkGameServiceImpl  implements SdkGameService {

    @Resource
    private IOpSubGameService opSubGameService;
    @Resource
    private IOpPkgService opPkgService;
    @Resource
    private IOpCommodityService opCommodityService;
    @Resource
    private KafkaTemplate kafkaTemplate;
    @Resource
    private IAdvertApi advertApi;

    @Override
    public SdkConfRes conf(SdkConfDto sdkConfDto) {
        OpSubGame opSubGame = opSubGameService.getSubGameById(sdkConfDto.getSubGameId());
        OpPkg opPkg = opPkgService.getById(sdkConfDto.getPkgId());
        if (oConvertUtils.isEmpty(opSubGame) && oConvertUtils.isEmpty(opPkg)) {
            throw new IdeaRunTimeException(ErrorCode.SUB_GAME_AND_PKG_EMPTY);
        }
        SdkConfRes sdkConfRes = new SdkConfRes();
        sdkConfRes.setToutiaoOpen(SwitchConstant.CLOSE);
        //先读取一级游戏包，不存在则取子游戏
        if (oConvertUtils.isNotEmpty(opPkg)) {
            //是否开启实名认证窗口
            sdkConfRes.setIdAuth(opPkg.getIdAuthSwitch());
            if (Objects.equals(opPkg.getChannelId(), ChannelConstant.JRTT)
                || Objects.equals(opPkg.getChannelId(), ChannelConstant.XING_TU)) {
                sdkConfRes.setToutiaoOpen(SwitchConstant.OPEN);
                sdkConfRes.setToutiaoChannel(String.valueOf(opPkg.getChannelId()));
                PkgChannelConfJrtt pkgChannelConfJrtt = JSON.parseObject(opPkg.getChannelConf(),PkgChannelConfJrtt.class);
                sdkConfRes.setToutiaoAid(pkgChannelConfJrtt.getAppId());
            }
        }else {
            if (oConvertUtils.isNotEmpty(opSubGame)) {
                //是否开启实名认证窗口
                sdkConfRes.setIdAuth(opSubGame.getIdAuthSwitch());
            }
        }
        return sdkConfRes;
    }

    @Override
    public SdkIosConfRes iosConf(SdkIosConfDto sdkIosConfDto) {
        SdkIosConfRes sdkIosConfRes = new SdkIosConfRes();
        // 通过归因查询广告id 查不到返回0
        Integer dealId = advertApi.getDealIdByVisit(sdkIosConfDto.getPkgId(),
            sdkIosConfDto.getIdfa(), sdkIosConfDto.getClientIp());
        sdkIosConfRes.setDealId(dealId);
        List<OpCommodityBo> opCommodityBos = opCommodityService.getOpCommodityByPkgIdAndSubGameId(
            sdkIosConfDto.getPkgId(), sdkIosConfDto.getSubGameId());
        sdkIosConfRes.setIosProductList(opCommodityBos);
        sdkIosConfRes.setFirstActive(sdkIosConfDto.getFirstActive());
        return sdkIosConfRes;
    }

    @Override
    public void start(SdkStartDataDto sdkEventDto) {
        if (oConvertUtils.isEmpty(sdkEventDto.getSubGameId())) {
            throw new IdeaRunTimeException(ErrorCode.SUBGAMEID_IS_EMPTY);
        }
        OpDealModel opDeal = advertApi.getOpDeal(sdkEventDto.getDealId());
        ParseStartDto parseStartDto = new ParseStartDto();
        parseStartDto.setDealId(sdkEventDto.getDealId());
        parseStartDto.setDeviceId(sdkEventDto.getDeviceId());
        parseStartDto.setPkgId(sdkEventDto.getPkgId());
        parseStartDto.setSubGameId(sdkEventDto.getSubGameId());
        //转为毫秒为单位的时间戳
        long time = System.currentTimeMillis();
        parseStartDto.setTime(time);
        parseStartDto.setFirstActive(sdkEventDto.getFirstActive());
        parseStartDto.setUniqueId(sdkEventDto.getUniqueId());
        parseStartDto.setAndroidId(sdkEventDto.getAndroidId());
        parseStartDto.setDevModel(sdkEventDto.getDevModel());
        parseStartDto.setDevOs(sdkEventDto.getDevOs());
        parseStartDto.setDevOsVer(sdkEventDto.getDevOsVer());
        parseStartDto.setPkgName(sdkEventDto.getPkgName());
        parseStartDto.setPkgVersionCode(sdkEventDto.getPkgVersionCode());
        parseStartDto.setPkgVersionName(sdkEventDto.getPkgVersionName());
        parseStartDto.setSdkVersion(sdkEventDto.getSdkVer());
        parseStartDto.setSerialId(sdkEventDto.getSerialId());
        parseStartDto.setChannelId(opDeal.getChannelId());
        parseStartDto.setChannelSubAccountId(opDeal.getChannelSubAccountId());
        parseStartDto.setChannelTypeId(opDeal.getChannelTypeId());
        parseStartDto.setGameId(opDeal.getGameId());
        parseStartDto.setClientIp(sdkEventDto.getClientIp());
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_EVENT_PARSE,
            GROUP_SDK_START, parseStartDto);
    }

    @Override
    public PrivacyAndUserProtocolVo getPrivacyAndUserProtocol(Integer pkgId) {
        if (pkgId == 0) {
            throw new IdeaRunTimeException(ErrorCode.PKGID_IS_EMPTY);
        }
        OpPkg opPkg = opPkgService.getById(pkgId);
        if (oConvertUtils.isEmpty(opPkg)) {
            throw new IdeaRunTimeException(ErrorCode.PKGID_IS_EMPTY);
        }
        if (opPkg.getPrivacySwitch() == 0) {
            return null;
        }
        if (oConvertUtils.isEmpty(opPkg.getPrivacyPolicyUrl())) {
            throw new IdeaRunTimeException(ErrorCode.PRIVACY_POLICY_UNBOUND);
        }
        if (oConvertUtils.isEmpty(opPkg.getUserAgreementUrl())) {
            throw new IdeaRunTimeException(ErrorCode.USER_PROTOCOL_UNBOUND);
        }
        PrivacyAndUserProtocolVo privacyAndUserProtocolVo = new PrivacyAndUserProtocolVo();
        privacyAndUserProtocolVo.setUserProtocolUrl(opPkg.getUserAgreementUrl());
        privacyAndUserProtocolVo.setPrivacyPolicyUrl(opPkg.getPrivacyPolicyUrl());
        return privacyAndUserProtocolVo;
    }

}
