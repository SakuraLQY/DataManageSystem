package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.constant.xingtu.XingtuUrlConstant;
import org.jeecg.modules.advert.dto.OpXingtuDealDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpXingtuDeal;
import org.jeecg.modules.advert.mapper.OpXingtuDealMapper;
import org.jeecg.modules.advert.service.IJrttDealService;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.modules.advert.service.IOpXingtuDealService;
import org.jeecg.modules.advert.vo.OpXingtuDealVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @Description: op_xingtu_deal
 * @Author: jeecg-boot
 * @Date: 2023-03-02
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_gateway")
public class OpXingtuDealServiceImpl extends
    ServiceImpl<OpXingtuDealMapper, OpXingtuDeal> implements IOpXingtuDealService {

    @Resource
    private IOpDealService opDealService;
    @Resource
    private IJrttDealService jrttDealService;

    @Override
    public IPage<OpXingtuDealVo> getByPage(Page<OpXingtuDealVo> page, OpDeal deal,
        Integer anchorId, String startDate, String endDate) {
        QueryWrapper<OpDeal> wrapper = opDealService.baseQuery(deal, startDate, endDate);
        wrapper.eq(oConvertUtils.isNotEmpty(anchorId), "oxd.anchor_plan_id", anchorId);
        wrapper.eq(oConvertUtils.isNotEmpty(deal.getId()), "d.id", deal.getId());
        return baseMapper.dealInfoPage(page, wrapper);
    }

    @Override
    @Transactional
    public void addDeal(OpXingtuDealDto opXingtuDealDto) {
        List<OpDeal> dealList = opDealService.addDeal(opXingtuDealDto, ChannelConstant.XING_TU,
            XingtuUrlConstant.IOS_TRACK_URL, XingtuUrlConstant.ANDROID_TRACK_URL);
        for (OpDeal deal : dealList) {
            Integer dealId = deal.getId();
            OpXingtuDeal xingtuDeal = new OpXingtuDeal();
            xingtuDeal.setDealId(dealId);
            xingtuDeal.setAnchorPlanId(opXingtuDealDto.getAnchorPlanId());
            xingtuDeal.setIosUrl(opXingtuDealDto.getIosUrl());
            xingtuDeal.setAndroidUrl(opXingtuDealDto.getAndroidUrl());
            // 插入星图广告
            if (!save(xingtuDeal)) {
                // 手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        // TODO 捕捉异常
        try {
            jrttDealService.extendPackage(dealList);
        } catch (Exception e) {
            log.error("打包出错");
        }
    }

    @Override
    public void deleteDeal(List<String> list) {
        opDealService.removeByIds(list);
        LambdaQueryWrapper<OpXingtuDeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(OpXingtuDeal::getDealId, list);
        remove(wrapper);
    }

    @Override
    public void deleteDeal(String id) {
        opDealService.removeById(id);
        LambdaQueryWrapper<OpXingtuDeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpXingtuDeal::getDealId, id);
        remove(wrapper);
    }

    @Override
    public void updateDeal(OpXingtuDealDto opXingtuDealDto) {
        opDealService.updateDeal(opXingtuDealDto);
        UpdateWrapper<OpXingtuDeal> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("anchor_plan_id", opXingtuDealDto.getAnchorPlanId());
        updateWrapper.set("ios_url", opXingtuDealDto.getIosUrl());
        updateWrapper.set("android_url", opXingtuDealDto.getAndroidUrl());
        updateWrapper.eq("deal_id", opXingtuDealDto.getId());
        update(updateWrapper);
    }
}
