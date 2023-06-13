package org.jeecg.modules.game.service.impl;

import static org.jeecg.common.constant.KafkaKeyConstant.GROUP_SDK_START;

import com.alibaba.fastjson.JSON;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.KafkaTopicConstant;
import org.jeecg.common.constant.PkgParentConstant;
import org.jeecg.common.constant.SwitchConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.bo.PkgChannelConfJrtt;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.game.bo.OpCommodityBo;
import org.jeecg.modules.game.dto.SdkConfDto;
import org.jeecg.modules.game.dto.SdkIosConfDto;
import org.jeecg.modules.game.dto.SdkStartDataDto;
import org.jeecg.modules.game.entity.OpPkg;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.service.IOpCommodityService;
import org.jeecg.modules.game.service.IOpPkgService;
import org.jeecg.modules.game.service.IOpSubGameService;
import org.jeecg.modules.game.service.SdkGameService;
import org.jeecg.modules.game.vo.PrivacyVo;
import org.jeecg.modules.game.vo.SdkConfRes;
import org.jeecg.modules.game.vo.SdkIosConfRes;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author lili
 * @Description sdk游戏
 * @Date 2023-03-29
 **/
@Service
@Slf4j
public class SdkGameServiceImpl implements SdkGameService {

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
        if (!PkgParentConstant.DEFAULT_PKG_ID.equals(sdkConfDto.getPkgId()) && oConvertUtils.isNotEmpty(opPkg)) {
            //是否开启实名认证窗口
            sdkConfRes.setIdAuth(opPkg.getIdAuthSwitch());
            if (Objects.equals(opPkg.getChannelId(), ChannelConstant.JRTT)
                || Objects.equals(opPkg.getChannelId(), ChannelConstant.XING_TU)) {
                sdkConfRes.setToutiaoOpen(SwitchConstant.OPEN);
                sdkConfRes.setToutiaoChannel(String.valueOf(opPkg.getChannelId()));
                PkgChannelConfJrtt pkgChannelConfJrtt = JSON.parseObject(opPkg.getChannelConf(),
                    PkgChannelConfJrtt.class);
                sdkConfRes.setToutiaoAid(pkgChannelConfJrtt.getAppId());
            }
            //隐私政策、用户协议、客服
            sdkConfRes.setUserProtocolUrl(opPkg.getUserAgreementUrl());
            sdkConfRes.setPrivacyPolicyUrl(opPkg.getPrivacyPolicyUrl());
            sdkConfRes.setPrivacyPolicyOpen(opPkg.getPrivacySwitch());
            sdkConfRes.setCustomerServiceUrl(opPkg.getCustomerServiceUrl());
        } else if (oConvertUtils.isNotEmpty(opSubGame)) {
            //是否开启实名认证窗口
            sdkConfRes.setIdAuth(opSubGame.getIdAuthSwitch());
            //隐私政策、用户协议、客服
            sdkConfRes.setUserProtocolUrl(opSubGame.getUserAgreementUrl());
            sdkConfRes.setPrivacyPolicyUrl(opSubGame.getPrivacyPolicyUrl());
            sdkConfRes.setPrivacyPolicyOpen(opSubGame.getPrivacySwitch());
            sdkConfRes.setCustomerServiceUrl(opSubGame.getCustomerServiceUrl());
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
        OpPkg opPkg = opPkgService.getById(sdkIosConfDto.getPkgId());
        // ios必定有渠道游戏包id
        sdkIosConfRes.setUserProtocolUrl(opPkg.getUserAgreementUrl());
        sdkIosConfRes.setPrivacyPolicyUrl(opPkg.getPrivacyPolicyUrl());
        sdkIosConfRes.setPrivacyPolicyOpen(opPkg.getPrivacySwitch());
        sdkIosConfRes.setCustomerServiceUrl(opPkg.getCustomerServiceUrl());
        return sdkIosConfRes;
    }

    @Override
    public void start(SdkStartDataDto sdkEventDto) {
        if (oConvertUtils.isEmpty(sdkEventDto.getSubGameId())) {
            throw new IdeaRunTimeException(ErrorCode.SUBGAMEID_IS_EMPTY);
        }
        OpDealModel opDeal = advertApi.getOpDeal(sdkEventDto.getDealId());
        OpSubGame opSubGame = opSubGameService.getSubGameById(sdkEventDto.getSubGameId());
        if(null == opSubGame){
            throw new IdeaRunTimeException(ErrorCode.NO_FOUND_SUB_GAME);
        }
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
        parseStartDto.setChannelId(opDeal.getChannelId() == null ? 0 : opDeal.getChannelId());
        parseStartDto.setChannelSubAccountId(
            opDeal.getChannelSubAccountId() == null ? 0 : opDeal.getChannelSubAccountId());
        parseStartDto.setChannelTypeId(
            opDeal.getChannelTypeId() == null ? 0 : opDeal.getChannelTypeId());
        parseStartDto.setGameId(opSubGame.getGameId());
        parseStartDto.setClientIp(sdkEventDto.getClientIp());
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_EVENT_PARSE, GROUP_SDK_START, parseStartDto);
    }

    @Override
    public PrivacyVo getPrivacy(Integer subGameId, Integer pkgId) {
        PrivacyVo result = new PrivacyVo();
        if (PkgParentConstant.DEFAULT_PKG_ID.equals(pkgId)) {
            OpSubGame opSubGame = opSubGameService.getSubGameById(subGameId);
            if(null == opSubGame){
                throw new IdeaRunTimeException(ErrorCode.SUB_GAME_IS_EMPTY);
            }
            result.setPrivacySwitch(opSubGame.getPrivacySwitch());
        }else{
            OpPkg opPkg = opPkgService.getPkgById(pkgId);
            if(null == opPkg){
                throw new IdeaRunTimeException(ErrorCode.PKGID_IS_EMPTY);
            }
            result.setPrivacySwitch(opPkg.getPrivacySwitch());
        }
        return result;

    }

}
