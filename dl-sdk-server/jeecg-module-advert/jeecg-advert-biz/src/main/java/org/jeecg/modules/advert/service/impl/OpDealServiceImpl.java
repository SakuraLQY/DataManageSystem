package org.jeecg.modules.advert.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.SubGameTypeConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.dto.OpDealDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.mapper.OpDealMapper;
import org.jeecg.modules.advert.service.IOpDealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: ad_deal
 * @Author: jeecg-boot
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_gateway")
public class OpDealServiceImpl extends ServiceImpl<OpDealMapper, OpDeal> implements
    IOpDealService {

    @Resource
    private IGameApi gameApi;

    @Override
    public QueryWrapper<OpDeal> baseQuery(OpDeal deal, String startDate, String endDate) {
        QueryWrapper<OpDeal> wrapper = new QueryWrapper<>();
        wrapper.eq(oConvertUtils.isNotEmpty(deal.getGameId()), "d.game_id", deal.getGameId());
        wrapper.eq(oConvertUtils.isNotEmpty(deal.getSubGameId()), "d.sub_game_id",
            deal.getSubGameId());
        wrapper.eq(oConvertUtils.isNotEmpty(deal.getSubGameType()), "d.sub_game_type",
            deal.getSubGameType());
        wrapper.eq(oConvertUtils.isNotEmpty(deal.getPkgId()), "d.pkg_id", deal.getPkgId());
        wrapper.eq(oConvertUtils.isNotEmpty(deal.getAccountId()), "d.account_id",
            deal.getAccountId());
        wrapper.eq(oConvertUtils.isNotEmpty(deal.getPackState()), "d.pack_state",
            deal.getPackState());
        wrapper.eq(oConvertUtils.isNotEmpty(deal.getCreateBy()), "d.create_by", deal.getCreateBy());
        wrapper.eq(oConvertUtils.isNotEmpty(deal.getId()), "d.id", deal.getId());
        wrapper.eq(oConvertUtils.isNotEmpty(deal.getChannelSubAccountId()), "d.channel_sub_account_id", deal.getChannelSubAccountId());
        wrapper.like(oConvertUtils.isNotEmpty(deal.getDealName()), "d.deal_name",
            deal.getDealName());
        wrapper.ge(oConvertUtils.isNotEmpty(startDate), "d.create_time",
            startDate);
        wrapper.le(oConvertUtils.isNotEmpty(endDate), "d.create_time",
            endDate);
        wrapper.orderByDesc("id");
        return wrapper;
    }

    @Override
    @Transactional
    public List<OpDeal> addDeal(OpDealDto opDealDto, Integer channelId, String iosTrackUrl,
        String androidTrackUrl) {
        int dealNumbers = opDealDto.getDealNumbers();
        // 添加的广告列表
        List<OpDeal> dealList = new ArrayList<>();
        for (int i = 0; i < dealNumbers; i++) {
            String accounts = opDealDto.getAccountIds();
            String[] accountArr = accounts.split(",");
            OpDeal baseDeal = new OpDeal(opDealDto, channelId);
            // 子游戏类型
            OpSubGameModel subGameModel = gameApi.getOpSubGame(opDealDto.getSubGameId());
            int gameType = subGameModel.getGameType();
            baseDeal.setSubGameType(gameType);
            for (String account : accountArr) {
                OpDeal deal = new OpDeal();
                BeanUtils.copyProperties(baseDeal, deal);
                // 投放账号
                deal.setAccountId(Integer.valueOf(account));
                save(deal);
                // 更新部分字段
                int dealId = deal.getId();
                UpdateWrapper<OpDeal> wrapper = new UpdateWrapper<>();
                wrapper.eq("id", dealId);
                // 拼接广告名
                String newDealName = account + "-" + opDealDto.getDealName() + "-" + dealId;
                // 检查是否重复
                OpDeal isRepeat = getOne(new LambdaQueryWrapper<OpDeal>().eq(OpDeal::getDealName, newDealName));
                if(oConvertUtils.isNotEmpty(isRepeat)){
                    newDealName += "-" + DateUtil.format(new Date(), "MMdd_HH:mm:ss.SSS");
                }
                deal.setDealName(newDealName);
                wrapper.set("deal_name", newDealName);
                // 监测链接为空
                if (oConvertUtils.isEmpty(deal.getDealArgs())) {
                    String trackUrl;
                    if (gameType == SubGameTypeConstant.ANDROID) {
                        trackUrl = androidTrackUrl + dealId;
                    } else {
                        trackUrl = iosTrackUrl + dealId;
                    }
                    wrapper.set("deal_args", trackUrl);
                }
                // 下载链接
                if (oConvertUtils.isEmpty(deal.getPkgUrl())) {
                    OpPkgModel pkgModel = gameApi.getOpPkgById(deal.getPkgId());
                    String downloadUrl = pkgModel.getDownloadUrl();
                    if (oConvertUtils.isEmpty(downloadUrl)) {
                        throw new JeecgBootException("游戏包下载链接为空！");
                    }
                    if (gameType == SubGameTypeConstant.ANDROID) {
                        downloadUrl += "/" + dealId;
                    }
                    deal.setPkgUrl(downloadUrl);
                    wrapper.set("pkg_url", downloadUrl);
                }
                update(wrapper);
                dealList.add(deal);
            }
        }
        return dealList;
    }

    @Override
    public void updateDeal(OpDealDto opDealDto) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 子游戏类型
        OpSubGameModel subGameModel = gameApi.getOpSubGame(opDealDto.getSubGameId());
        UpdateWrapper<OpDeal> wrapper = new UpdateWrapper<>();
        wrapper.set("deal_name", opDealDto.getDealName());
        wrapper.set("game_id", opDealDto.getGameId());
        wrapper.set("sub_game_id", opDealDto.getSubGameId());
        wrapper.set("sub_game_type", subGameModel.getGameType());
        wrapper.set("pkg_id", opDealDto.getPkgId());
        wrapper.set("account_id", Integer.valueOf(opDealDto.getAccountIds()));
        wrapper.set("channel_sub_account_id", opDealDto.getChannelSubAccountId());
        wrapper.set("deal_args", opDealDto.getDealArgs());
        wrapper.set("pkg_url", opDealDto.getPkgUrl());
        wrapper.set("deal_desc", opDealDto.getDealDesc());
        wrapper.set("update_by", sysUser.getUsername());
        wrapper.set("update_time", new Date());
        wrapper.eq("id", opDealDto.getId());
        update(wrapper);
    }
}
