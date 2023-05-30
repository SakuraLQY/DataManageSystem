package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jeecg.modules.count.bo.ComputePayBo;
import org.jeecg.modules.count.dto.ComputePayDto;
import org.jeecg.modules.count.entity.ComputeMoney;
import org.jeecg.modules.count.mapper.ComputeMoneyMapper;
import org.jeecg.modules.count.service.IComputeMoneyService;
import org.jeecg.modules.count.vo.ComputePayVo;
import org.jeecg.modules.count.vo.ReportAccountVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: data_tool
 * @Author: jeecg-boot
 * @Date:   2023-05-23
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class ComputeMoneyServiceImpl extends ServiceImpl<ComputeMoneyMapper, ComputeMoney> implements IComputeMoneyService {
    @Resource
    private ComputeMoneyMapper computeMoneyMapper;
    @Override
    public List<ComputePayVo> queryList(ComputePayDto computePayDto) {
        QueryWrapper where = new QueryWrapper<>();
        QueryWrapper where2 = new QueryWrapper<>();
        QueryWrapper where3 = new QueryWrapper<>();
        List<ComputePayVo>resList = new ArrayList<>();
        if(computePayDto.getGameId()!=null){
            where.in("a.game_id",computePayDto.getGameId());
            where2.in("a.game_id",computePayDto.getGameId());
            where3.in("game_id",computePayDto.getGameId());
        }
        if(computePayDto.getSubGameId()!=null){
            where.in("a.sub_game_id",computePayDto.getSubGameId());
            where2.in("a.sub_game_id",computePayDto.getSubGameId());
            where3.in("sub_game_id",computePayDto.getSubGameId());
        }
        if(computePayDto.getPkgId()!=null){
            where.in("a.pkg_id",computePayDto.getPkgId());
            where2.in("a.pkg_id",computePayDto.getPkgId());
            where3.in("pkg_id",computePayDto.getPkgId());
        }
        if(computePayDto.getChannelId()!=null){
            where.in("a.channel_id",computePayDto.getChannelId());
            where2.in("a.channel_id",computePayDto.getChannelId());
            where3.in("channel_id",computePayDto.getChannelId());
        }
        if(computePayDto.getChannelSubAccountId()!=null){
            where.in("a.channel_sub_account_id",computePayDto.getChannelSubAccountId());
            where2.in("a.channel_sub_account_id",computePayDto.getChannelSubAccountId());
            where3.in("channel_sub_account_id",computePayDto.getChannelSubAccountId());
        }
        if(computePayDto.getChannelTypeId()!=null){
            where.in("a.channel_type_id",computePayDto.getChannelTypeId());
            where2.in("a.channel_type_id",computePayDto.getChannelTypeId());
            where3.in("channel_type_id",computePayDto.getChannelTypeId());
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != computePayDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(computePayDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
            }
            if (null != computePayDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(computePayDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date payStart = null;
        Date payEnd = null;
        try {
            if (null != computePayDto.getPayStart()) {
                payStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(computePayDto.getPayStart(), "yyyy-MM-dd 00:00:00"));
                where3.ge("create_time", payStart);
            }
            if (null != computePayDto.getEndTime()) {
                payEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(computePayDto.getPayEnd(), "yyyy-MM-dd 23:59:59"));
                where3.le("create_time", payEnd);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<ComputePayBo>payList = computeMoneyMapper.queryPayList(where);
        //计算合计的数据
        Integer allRegCount = 0;
        Integer allFirstPayUser = 0;
        BigDecimal allFirstPay = BigDecimal.ZERO;
        Integer allPeriodUser = 0;
        BigDecimal allPeriodMoney = BigDecimal.ZERO;
        if(payList!=null){
        for (ComputePayBo computePayBo : payList) {
            ComputePayVo computePayVo = new ComputePayVo();
            computePayVo.setRegTime(DateUtil.format(DateUtil.date(computePayBo.getRegTime()),"yyyy-MM-dd"));
            computePayVo.setRegCount(computePayBo.getRegCount());
            computePayVo.setFirstPayUser(computePayBo.getFirstPayUser());
            computePayVo.setFirstPay(computePayBo.getFirstPay());
            Date regTime = computePayBo.getRegTime();
            Integer periodUser = computeMoneyMapper.selectPeriodUser(where3,DateUtil.date(regTime));
            BigDecimal periodMoney = computeMoneyMapper.selectPeriodMoney(where3,DateUtil.date(regTime));
            if(periodUser==null){
                computePayVo.setPeriodUser(0);
            }else{
                computePayVo.setPeriodUser(periodUser);
            }
            //期间付费
            if(periodMoney==null){
                computePayVo.setPeriodMoney(BigDecimal.ZERO);
            }else{
                computePayVo.setPeriodMoney(periodMoney);
            }
            allRegCount+=computePayBo.getRegCount();
            allFirstPayUser+= computePayBo.getFirstPayUser();
            allFirstPay.add(computePayBo.getFirstPay());
            allPeriodMoney.add(computePayVo.getPeriodMoney());
            allPeriodUser+= computePayVo.getPeriodUser();
            resList.add(computePayVo);
        }
        ComputePayVo allPayVo = new ComputePayVo();
        allPayVo.setRegTime("合计");
        allPayVo.setFirstPay(allFirstPay);
        allPayVo.setRegCount(allRegCount);
        allPayVo.setFirstPayUser(allFirstPayUser);
        allPayVo.setPeriodMoney(allPeriodMoney);
        allPayVo.setPeriodUser(allPeriodUser);
        resList.add(allPayVo);
        }else{
        return null;
        }
        return resList;
    }
}
