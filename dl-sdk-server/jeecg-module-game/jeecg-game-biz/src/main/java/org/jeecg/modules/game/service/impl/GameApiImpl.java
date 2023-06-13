package org.jeecg.modules.game.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.advert.vo.OpChannelModel;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.RuleTypeConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.function.bo.GetNameByIdDto;
import org.jeecg.common.function.vo.GetNameByIdVo;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.bo.PkgChannelConfJrtt;
import org.jeecg.common.game.vo.OpCommodityModel;
import org.jeecg.common.game.vo.OpGameModel;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpPrivacyPolicyModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.modules.game.bo.OpCommodityBo;
import org.jeecg.modules.game.entity.OpCommodity;
import org.jeecg.modules.game.entity.OpGame;
import org.jeecg.modules.game.entity.OpPkg;
import org.jeecg.modules.game.entity.OpPrivacyPolicy;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.service.IOpCommodityService;
import org.jeecg.modules.game.service.IOpGameService;
import org.jeecg.modules.game.service.IOpPkgService;
import org.jeecg.modules.game.service.IOpPrivacyPolicyService;
import org.jeecg.modules.game.service.IOpSubGameService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameApiImpl implements IGameApi {

    @Resource
    private IOpSubGameService opSubGameService;
    @Resource
    private IOpGameService opGameService;
    @Resource
    private IOpCommodityService opCommodityService;
    @Resource
    private IOpPkgService opPkgService;
    @Autowired
    private IOpPrivacyPolicyService opPrivacyPolicyService;

    public OpSubGameModel getOpSubGame(Integer subGameId) {
        OpSubGame opSubGame = opSubGameService.getSubGameById(subGameId);
        OpSubGameModel opSubGameModel = new OpSubGameModel();
        if (null == opSubGame) {
            return opSubGameModel;
        }
        BeanUtils.copyProperties(opSubGame, opSubGameModel);
        return opSubGameModel;
    }

    @Override
    public OpGameModel getOpGame(Integer id) {
        OpGame opGame = opGameService.getById(id);
        OpGameModel opGameModel = new OpGameModel();
        if(null == opGame){
            return opGameModel;
        }
        BeanUtils.copyProperties(opGame, opGameModel);
        return opGameModel;
    }

    @Override
    public OpCommodityModel getOpCommdityByGoodId(Integer subGameId, Integer pkgId, String goodsId) {
        List<OpCommodityBo> opCommodityBos =  opCommodityService.getOpCommodityByPkgIdAndSubGameId(pkgId, subGameId);
        OpCommodityModel result = null;
        if(CollectionUtil.isEmpty(opCommodityBos)){
            return result;
        }
        for(OpCommodityBo opCommodityBo : opCommodityBos){
            if(Objects.equals(opCommodityBo.getGoodsId(), goodsId)){
                result = new OpCommodityModel();
                BeanUtils.copyProperties(opCommodityBo, result);
                break;
            }
        }
        return result;
    }

    @Override
    public OpCommodityModel getOpCommdityByMoney(Integer subGameId, Integer pkgId, BigDecimal money) {
        List<OpCommodityBo> opCommodityBos =  opCommodityService.getOpCommodityByPkgIdAndSubGameId(pkgId, subGameId);
        OpCommodityModel result = null;
        if(CollectionUtil.isEmpty(opCommodityBos)){
            return result;
        }
        for(OpCommodityBo opCommodityBo : opCommodityBos){
            if(Objects.equals(opCommodityBo.getMoney(), money)){
                result = new OpCommodityModel();
                BeanUtils.copyProperties(opCommodityBo, result);
                break;
            }
        }
        return result;
    }

    @Override
    public OpPkgModel getOpPkgById(Integer id) {
        OpPkg opPkg = opPkgService.getById(id);
        OpPkgModel opPkgModel = new OpPkgModel();
        if(opPkg == null){
            return opPkgModel;
        }
        BeanUtils.copyProperties(opPkg, opPkgModel);
        if (ChannelConstant.JRTT.equals(opPkg.getChannelId()) || ChannelConstant.XING_TU.equals(opPkg.getChannelId())) {
            PkgChannelConfJrtt jrttConf = JSONObject.parseObject(opPkgModel.getChannelConf(),
                PkgChannelConfJrtt.class);
            opPkgModel.setJrttConf(jrttConf);
        }
        return opPkgModel;
    }

    @Override
    public OpPkgModel getPkgByPackageName(String packageName) {
        OpPkg opPkg = opPkgService.getPkgByPackageName(packageName);
        OpPkgModel opPkgModel = new OpPkgModel();
        if (opPkg != null) {
            BeanUtils.copyProperties(opPkg, opPkgModel);
            return opPkgModel;
        }
        return null;
    }

    @Override
    public boolean checkVendorIsBind(List<String> list) {
        LambdaQueryWrapper<OpSubGame> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OpSubGame::getVendorId, list);
        OpSubGame subGame = opSubGameService.getOne(queryWrapper, false);
        return subGame != null;
    }

    @Override
    public OpPrivacyPolicyModel getOpPrivacyId(Integer id){
        LambdaQueryWrapper<OpPrivacyPolicy> opPrivacyPolicyModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        opPrivacyPolicyModelLambdaQueryWrapper.eq(OpPrivacyPolicy::getId,id);
        OpPrivacyPolicy opPrivacyPolicy = opPrivacyPolicyService
            .getOne(opPrivacyPolicyModelLambdaQueryWrapper);
        OpPrivacyPolicyModel opPrivacyPolicyModel = new OpPrivacyPolicyModel();

        BeanUtils.copyProperties(opPrivacyPolicy,opPrivacyPolicyModel);
        return opPrivacyPolicyModel;
    }
}
