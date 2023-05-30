package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jeecg.modules.count.bo.RetainLoyalBo;
import org.jeecg.modules.count.bo.RetainPayTempBo;
import org.jeecg.modules.count.bo.PayResBo;
import org.jeecg.modules.count.bo.PayUserBo;
import org.jeecg.modules.count.bo.RetainAnalyzeBo;
import org.jeecg.modules.count.bo.RetainCostBo;
import org.jeecg.modules.count.dto.RetainAnalyzeDto;
import org.jeecg.modules.count.entity.RetainAnalyze;
import org.jeecg.modules.count.mapper.RetainAnalyzeMapper;
import org.jeecg.modules.count.service.IRetainAnalyzeService;
import org.jeecg.modules.count.vo.RetainLoyalVo;
import org.jeecg.modules.count.vo.RetainNewLoyalVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 留存分析
 * @Author: jeecg-boot
 * @Date: 2023-05-17
 * @Version: V1.0
 */
@Service
public class RetainAnalyzeServiceImpl extends
    ServiceImpl<RetainAnalyzeMapper, RetainAnalyze> implements IRetainAnalyzeService {

    @Resource
    private RetainAnalyzeMapper retainAnalyzeMapper;

    @Override
    public List<RetainLoyalVo> queryRetainList(RetainAnalyzeDto retainAnalyzeDto) {
        QueryWrapper<RetainAnalyzeDto> where = new QueryWrapper<>();
        QueryWrapper cost_where = new QueryWrapper();
        String gameName = "全部游戏";
        String channelName = "全部渠道";
        String dealName = "全部广告";
        if (null != retainAnalyzeDto.getGameId() && !retainAnalyzeDto.getGameId().isEmpty()) {
            where.in("a.game_id", retainAnalyzeDto.getGameId());
            gameName = "多款游戏";
        }
        if (null != retainAnalyzeDto.getSubGameId() && !retainAnalyzeDto.getSubGameId().isEmpty()) {
            where.in("a.sub_game_id", retainAnalyzeDto.getSubGameId());
            gameName += "(多款子游戏)";
        } else {
            if (null == retainAnalyzeDto.getGameId() || retainAnalyzeDto.getGameId().isEmpty()) {
                gameName += "(全部子游戏)";
            }
        }
        if (null != retainAnalyzeDto.getPkgId() && !retainAnalyzeDto.getPkgId().isEmpty()) {
            where.in("a.pkg_id", retainAnalyzeDto.getPkgId());
            gameName += "(多款渠道游戏包)";
        } else {
            if (null == retainAnalyzeDto.getSubGameId() || retainAnalyzeDto.getSubGameId()
                .isEmpty()) {
                gameName += "(全部渠道游戏包)";
            }
        }
        if (null != retainAnalyzeDto.getChannelTypeId() && !retainAnalyzeDto.getChannelTypeId()
            .isEmpty()) {
            where.in("a.channel_type_id", retainAnalyzeDto.getChannelTypeId());
        }
        if (null != retainAnalyzeDto.getChannelId() && !retainAnalyzeDto.getChannelId().isEmpty()) {
            where.in("a.channel_id", retainAnalyzeDto.getChannelId());
            channelName = "多款渠道";
        }
        if (null != retainAnalyzeDto.getChannelSubAccountId()
            && !retainAnalyzeDto.getChannelSubAccountId().isEmpty()) {
            where.in("a.channel_sub_account_id", retainAnalyzeDto.getChannelSubAccountId());
            channelName += "(多款子渠道)";
        } else {
            if (null == retainAnalyzeDto.getChannelId() || retainAnalyzeDto.getChannelId()
                .isEmpty()) {
                channelName += "(全部子渠道)";
            }
        }
        if (null != retainAnalyzeDto.getDealId() && !retainAnalyzeDto.getDealId().isEmpty()) {
            where.in("a.deal_id", retainAnalyzeDto.getDealId());
            dealName = "多款广告";
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != retainAnalyzeDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(retainAnalyzeDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
                cost_where.ge("a.cost_day", startDate);
            }
            if (null != retainAnalyzeDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(retainAnalyzeDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
                cost_where.le("a.cost_day", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //创建人账号
        if (null != retainAnalyzeDto.getCreateUser()) {
            where.eq("f.create_by", retainAnalyzeDto.getCreateUser());
            cost_where.eq("f.create_by", retainAnalyzeDto.getCreateUser());
        }
        //业务逻辑的编写
        //1.查询投入成本
        List<RetainCostBo> retainCost = retainAnalyzeMapper.queryCostList(cost_where);
        Map<String, BigDecimal> costMap = new HashMap<>();
        //key:时间 value:成本
        for (RetainCostBo retainCostBo : retainCost) {
            costMap.put(String.valueOf(retainCostBo.getDateTime()), retainCostBo.getCostMoney());
        }
        //拼接一个动态的sql
        String loyalSql = "";
        for (int i = 2; i <=150 ; i++) {
            loyalSql += "IFNULL(SUM(b.day"+i+"),0) as loyal"+i +",";
        }
        loyalSql = loyalSql.substring(0,loyalSql.length()-1);
        //此处对应php中的$res
        List<RetainLoyalBo>loyalList = retainAnalyzeMapper.queryLoyalList(where,loyalSql);
        //统计合计留存的用户
        Map<String,BigDecimal>loyalMap = new HashMap<>();
        for (int i = 2; i <=150 ; i++) {
            loyalMap.put("loyal"+i,BigDecimal.ZERO);
            loyalMap.put("loyal_num_count"+i,BigDecimal.ZERO);
            loyalMap.put("loyal_cost_count"+i,BigDecimal.ZERO);
        }
        //初始化注册数和首次付费人数
        Integer countUserNum = 0;
        Integer firstUserNum = 0;
        List<RetainLoyalVo>resList = new ArrayList<>();
        if(loyalList!=null){
            for (RetainLoyalBo retainLoyal : loyalList) {
                RetainLoyalVo result = new RetainLoyalVo();
                result.setDateTime(DateUtil.format(retainLoyal.getDateTime(),"yyyy-MM-dd"));
                result.setGameName(gameName);
                result.setChannelName(channelName);
                result.setDealName(dealName);
                result.setRegCount(retainLoyal.getRegCount());
                //取对应的成本
                if(costMap.containsKey(retainLoyal.getDateTime())){
                    result.setCostPay(costMap.get(retainLoyal.getDateTime()));
                }else{
                    result.setCostPay(BigDecimal.ZERO);
                }
                //计算对应的留存，同时将150个存储到我的Map集合中去
                Map<String,String>remainLoyalMap = new HashMap<>();
                Map<String,BigDecimal>remainPriceMap = new HashMap<>();
                Class< RetainLoyalBo> loyalClass = RetainLoyalBo.class;
                for (int i = 2; i <=150 ; i++) {
                    Field declaredField = null;
                    //通过反射拿到具体的值
                    try {
                        declaredField = loyalClass.getDeclaredField("loyal" + i);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    declaredField.setAccessible(true);
                    BigDecimal value = null;
                    try {
                        value = (BigDecimal) declaredField.get(retainLoyal);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if(value!=null && retainLoyal.getRegCount()!=null && retainLoyal.getRegCount()!=0){
                        BigDecimal tempValue = value
                            .divide(BigDecimal.valueOf(retainLoyal.getRegCount()),2,RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                        remainLoyalMap.put("loyal"+i,tempValue+"%");
                    }else{
                        remainLoyalMap.put("loyal"+i,"0%");
                    }
                    if(value.compareTo(BigDecimal.ZERO)>0 && result.getCostPay()!=null && value!=null){
                        BigDecimal priceLoyal = result.getCostPay().divide(value).setScale(2,RoundingMode.HALF_UP);
                        remainPriceMap.put("price"+i,priceLoyal);
                    }else{
                        remainPriceMap.put("price"+i,BigDecimal.ZERO);
                    }
                }
                result.setRemainLoyal(remainLoyalMap);
                result.setPriceLoyal(remainPriceMap);
                resList.add(result);
                //计算其他的
                for (int i = 2; i <= 150 ; i++) {
                    long day = DateUtil.between(retainLoyal.getDateTime(),new Date(),DateUnit.DAY);
                    if(day>=i){
                        loyalMap.put("loyal"+i,loyalMap.get("loyal"+i).add(BigDecimal.valueOf(Double.parseDouble((remainLoyalMap.get("loyal"+i).replace("%",""))))));
                        loyalMap.put("loyal_num_count"+i,loyalMap.get("loyal_num_count"+i).add(BigDecimal.valueOf(retainLoyal.getRegCount())));
                        loyalMap.put("loyal_cost_count"+i,loyalMap.get("loyal_cost_count"+i).add(result.getCostPay()));
                    }
                }
                countUserNum+=retainLoyal.getRegCount();
                firstUserNum+=retainLoyal.getFirstPayUser();
            }
        }
        //计算其合计数据
        RetainLoyalVo allRetainLoyal = new RetainLoyalVo();
        allRetainLoyal.setDateTime("合计值");
        allRetainLoyal.setGameName("--");
        allRetainLoyal.setDealName("--");
        allRetainLoyal.setChannelName("--");
        allRetainLoyal.setRegCount(countUserNum);
        allRetainLoyal.setFirstPayUser(firstUserNum);
        if(!costMap.isEmpty()){
            BigDecimal sum = costMap.values().stream()
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
            allRetainLoyal.setCostPay(sum);
        }else{
            allRetainLoyal.setCostPay(BigDecimal.ZERO);
        }
        Map<String,String>loyalTempMap = new HashMap<>();
        Map<String,BigDecimal>priceTempMap = new HashMap<>();
        for (int i = 2; i <=150 ; i++) {
            if(loyalMap.get("loyal"+i)!=null && loyalMap.get("loyal_num_count"+i)!=null && loyalMap.get("loyal_num_count"+i).compareTo(BigDecimal.ZERO)>0){
                BigDecimal loyal = loyalMap.get("loyal"+i).multiply(BigDecimal.valueOf(100))
                    .divide(loyalMap.get("loyal_num_count"+i),2,RoundingMode.HALF_UP);
                loyalTempMap.put("loyal"+i,loyal+"%");
            }else{
                loyalTempMap.put("loyal"+i,"0%");
            }
            if(loyalMap.get("loyal"+i)!=null && loyalMap.get("loyal"+i).compareTo(BigDecimal.ZERO)>0 && loyalMap.get("loyal_cost_count"+i)!=null){
                BigDecimal price = loyalMap.get("loyal_cost_count"+i)
                    .divide(loyalMap.get("loyal"+i),2,RoundingMode.HALF_UP);
                priceTempMap.put("price"+i,price);
            }else{
                priceTempMap.put("price"+i,BigDecimal.ZERO);
            }
        }
        allRetainLoyal.setRemainLoyal(loyalTempMap);
        allRetainLoyal.setPriceLoyal(priceTempMap);
        resList.add(allRetainLoyal);
        return resList;
    }

    @Override
    public List<RetainNewLoyalVo> queryRetainNewLoyalList(RetainAnalyzeDto retainAnalyzeDto) {
        QueryWrapper<RetainAnalyzeDto> where = new QueryWrapper<>();
        QueryWrapper cost_where = new QueryWrapper();
        QueryWrapper section_where = new QueryWrapper<>();
        QueryWrapper pay_where = new QueryWrapper();
        //查询对应的ct_daily_payback
        String gameName = "全部游戏";
        String channelName = "全部渠道";
        String dealName = "全部广告";
        if (null != retainAnalyzeDto.getGameId() && !retainAnalyzeDto.getGameId().isEmpty()) {
            where.in("a.game_id", retainAnalyzeDto.getGameId());
            gameName = "多款游戏";
        }
        if (null != retainAnalyzeDto.getSubGameId() && !retainAnalyzeDto.getSubGameId().isEmpty()) {
            where.in("a.sub_game_id", retainAnalyzeDto.getSubGameId());
            gameName += "(多款子游戏)";
        } else {
            if (null == retainAnalyzeDto.getGameId() || retainAnalyzeDto.getGameId().isEmpty()) {
                gameName += "(全部子游戏)";
            }
        }
        if (null != retainAnalyzeDto.getPkgId() && !retainAnalyzeDto.getPkgId().isEmpty()) {
            where.in("a.pkg_id", retainAnalyzeDto.getPkgId());
            gameName += "(多款渠道游戏包)";
        } else {
            if (null == retainAnalyzeDto.getSubGameId() || retainAnalyzeDto.getSubGameId()
                .isEmpty()) {
                gameName += "(全部渠道游戏包)";
            }
        }
        if (null != retainAnalyzeDto.getChannelTypeId() && !retainAnalyzeDto.getChannelTypeId()
            .isEmpty()) {
            where.in("a.channel_type_id", retainAnalyzeDto.getChannelTypeId());
        }
        if (null != retainAnalyzeDto.getChannelId() && !retainAnalyzeDto.getChannelId().isEmpty()) {
            where.in("a.channel_id", retainAnalyzeDto.getChannelId());
            channelName = "多款渠道";
        }
        if (null != retainAnalyzeDto.getChannelSubAccountId()
            && !retainAnalyzeDto.getChannelSubAccountId().isEmpty()) {
            where.in("a.channel_sub_account_id", retainAnalyzeDto.getChannelSubAccountId());
            channelName += "(多款子渠道)";
        } else {
            if (null == retainAnalyzeDto.getChannelId() || retainAnalyzeDto.getChannelId()
                .isEmpty()) {
                channelName += "(全部子渠道)";
            }
        }
        if (null != retainAnalyzeDto.getDealId() && !retainAnalyzeDto.getDealId().isEmpty()) {
            where.in("a.deal_id", retainAnalyzeDto.getDealId());
            dealName = "多款广告";
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != retainAnalyzeDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(retainAnalyzeDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
                cost_where.ge("a.cost_day", startDate);
                section_where.ge("a.user_create_time", startDate);
                pay_where.ge("a.pay_time", startDate);
            }
            if (null != retainAnalyzeDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(retainAnalyzeDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
                cost_where.le("a.cost_day", endDate);
                section_where.le("a.user_create_time", endDate);
                pay_where.le("a.pay_time", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //创建人账号
        if (null != retainAnalyzeDto.getCreateUser()) {
            where.eq("f.create_by", retainAnalyzeDto.getCreateUser());
            cost_where.eq("f.create_by", retainAnalyzeDto.getCreateUser());
        }
        //查询投入成本
        List<RetainCostBo> retainCost = retainAnalyzeMapper.queryCostList(cost_where);
        Map<String, BigDecimal> costMap = new HashMap<>();
        for (RetainCostBo retainCostBo : retainCost) {
            costMap.put(String.valueOf(retainCostBo.getDateTime()), retainCostBo.getCostMoney());
        }
        //查询注册数以及首日付费用户数->$res
        List<RetainAnalyzeBo> retainAnalyzeBoList = retainAnalyzeMapper.queryRetainList(where);
        //新增付费用户数据
        List<PayResBo> payRes;
        if (null != retainAnalyzeDto.getEndSection() && null != retainAnalyzeDto.getBegSection()) {
            List<PayUserBo> payOrderRes = retainAnalyzeMapper.queryOrderUser(section_where);
            List<Integer> userIds = new ArrayList<>();
            for (PayUserBo orderUser : payOrderRes) {
                Integer userId = orderUser.getUserId();
                if ((retainAnalyzeDto.getBegSection() == null
                    || orderUser.getPayMoney().compareTo(retainAnalyzeDto.getBegSection()) >= 0
                ) && (retainAnalyzeDto.getEndSection() == null
                    || orderUser.getPayMoney().compareTo(retainAnalyzeDto.getEndSection()) <= 0)) {
                    userIds.add(userId);
                }
            }
            if (userIds.isEmpty()) {
                payRes = new ArrayList<>();
            } else {
                pay_where.in("a.user_id", userIds);
                payRes = retainAnalyzeMapper.queryPayRes(pay_where);
            }
        } else {
            payRes = retainAnalyzeMapper.queryPayRes(pay_where);
        }
        //开始计算统计合计留存用户
        RetainPayTempBo tempCalculate = new RetainPayTempBo();
        //返回给前端的数据
        List<RetainNewLoyalVo> resList = new ArrayList<>();
        for (RetainAnalyzeBo retainAnalyzeBo : retainAnalyzeBoList) {
            RetainNewLoyalVo result = new RetainNewLoyalVo();
            //添加时间
            String dateTime = DateUtil.format(retainAnalyzeBo.getDateTime(), "yyyy-MM-dd");
            result.setDateTime(dateTime);
            //添加游戏名等
            result.setGameName(gameName);
            result.setChannelName(channelName);
            result.setDealName(dealName);
            //添加注册人数
            result.setRegCount(retainAnalyzeBo.getRegCount());
            //记录付费人数
            Integer payUserNumber = 0;
            //
            Integer[] pay_loyal = new Integer[9];
            Arrays.fill(pay_loyal, 1, 9, 0);
            for (PayResBo payUser : payRes) {
                if (payRes.isEmpty()) {
                    continue;
                }
                if (payUser.getDateTime().equals(retainAnalyzeBo.getDateTime())) {
                    payUserNumber++;
                }
                for (int i = 1; i <= 8; i++) {
                    if (0 != payUser.getPayUserLoginMask() & (1 << i) != 0) {
                        if (pay_loyal[i] == null) {
                            pay_loyal[i] = 1;
                        } else {
                            pay_loyal[i]++;
                        }
                    }
                }
            }
            //如果查询出来有成本的话
            if (costMap.containsKey(result.getDateTime())) {
                result.setCostPay(costMap.get(result.getDateTime()));
            } else {
                result.setCostPay(BigDecimal.ZERO);
            }
            //计算新增付费留存
            result.setFirstPayUser(payUserNumber);
            //计算新增付费次留
            if (payUserNumber > 0) {
                BigDecimal firstPayLoyal = BigDecimal.valueOf(pay_loyal[1])
                    .divide(BigDecimal.valueOf(payUserNumber))
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayLoyal(firstPayLoyal + "%");
            } else {
                result.setFirstPayLoyal("0%");
            }
            //计算新增付费3留
            if (payUserNumber > 0) {
                BigDecimal firstPayLoyal3 = BigDecimal.valueOf(pay_loyal[2])
                    .divide(BigDecimal.valueOf(payUserNumber))
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayLoyal3(firstPayLoyal3 + "%");
            } else {
                result.setFirstPayLoyal3("0%");
            }
            //计算新增付费7留
            if (payUserNumber > 0) {
                BigDecimal firstPayLoyal7 = BigDecimal.valueOf(pay_loyal[3])
                    .divide(BigDecimal.valueOf(payUserNumber))
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayLoyal7(firstPayLoyal7 + "%");
            } else {
                result.setFirstPayLoyal7("0%");
            }
            //计算新增付费15留
            if (payUserNumber > 0) {
                BigDecimal firstPayLoyal15 = BigDecimal.valueOf(pay_loyal[4])
                    .divide(BigDecimal.valueOf(payUserNumber))
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayLoyal15(firstPayLoyal15 + "%");
            } else {
                result.setFirstPayLoyal15("0%");
            }
            //计算新增付费30留
            if (payUserNumber > 0) {
                BigDecimal firstPayLoyal30 = BigDecimal.valueOf(pay_loyal[5])
                    .divide(BigDecimal.valueOf(payUserNumber))
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayLoyal30(firstPayLoyal30 + "%");
            } else {
                result.setFirstPayLoyal30("0%");
            }
            //计算新增付费45留
            if (payUserNumber > 0) {
                BigDecimal firstPayLoyal45 = BigDecimal.valueOf(pay_loyal[6])
                    .divide(BigDecimal.valueOf(payUserNumber))
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayLoyal45(firstPayLoyal45 + "%");
            } else {
                result.setFirstPayLoyal45("0%");
            }
            //计算新增付费60留
            if (payUserNumber > 0) {
                BigDecimal firstPayLoyal60 = BigDecimal.valueOf(pay_loyal[7])
                    .divide(BigDecimal.valueOf(payUserNumber))
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayLoyal60(firstPayLoyal60 + "%");
            } else {
                result.setFirstPayLoyal60("0%");
            }
            //计算新增付费90留
            if (payUserNumber > 0) {
                BigDecimal firstPayLoyal90 = BigDecimal.valueOf(pay_loyal[8])
                    .divide(BigDecimal.valueOf(payUserNumber))
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayLoyal90(firstPayLoyal90 + "%");
            } else {
                result.setFirstPayLoyal90("0%");
            }
            //计算新增付费留存单价
            if (pay_loyal[1] > 0) {
                BigDecimal firstPayPrice = result.getCostPay()
                    .divide(BigDecimal.valueOf(pay_loyal[1]))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayPrice(firstPayPrice);
            } else {
                result.setFirstPayPrice(BigDecimal.ZERO);
            }
            //计算新增付费留存3单价
            if (pay_loyal[2] > 0) {
                BigDecimal firstPayPrice3 = result.getCostPay()
                    .divide(BigDecimal.valueOf(pay_loyal[2]))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayPrice3(firstPayPrice3);
            } else {
                result.setFirstPayPrice3(BigDecimal.ZERO);
            }
            //计算新增付费留存7单价
            if (pay_loyal[3] > 0) {
                BigDecimal firstPayPrice7 = result.getCostPay()
                    .divide(BigDecimal.valueOf(pay_loyal[3]))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayPrice7(firstPayPrice7);
            } else {
                result.setFirstPayPrice7(BigDecimal.ZERO);
            }
            //计算新增付费留存15单价
            if (pay_loyal[4] > 0) {
                BigDecimal firstPayPrice15 = result.getCostPay()
                    .divide(BigDecimal.valueOf(pay_loyal[4]))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayPrice15(firstPayPrice15);
            } else {
                result.setFirstPayPrice15(BigDecimal.ZERO);
            }
            //计算新增付费留存30单价
            if (pay_loyal[5] > 0) {
                BigDecimal firstPayPrice30 = result.getCostPay()
                    .divide(BigDecimal.valueOf(pay_loyal[5]))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayPrice30(firstPayPrice30);
            } else {
                result.setFirstPayPrice30(BigDecimal.ZERO);
            }
            //计算新增付费留存45单价
            if (pay_loyal[6] > 0) {
                BigDecimal firstPayPrice45 = result.getCostPay()
                    .divide(BigDecimal.valueOf(pay_loyal[6]))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayPrice45(firstPayPrice45);
            } else {
                result.setFirstPayPrice45(BigDecimal.ZERO);
            }
            //计算新增付费留存60单价
            if (pay_loyal[7] > 0) {
                BigDecimal firstPayPrice60 = result.getCostPay()
                    .divide(BigDecimal.valueOf(pay_loyal[7]))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayPrice60(firstPayPrice60);
            } else {
                result.setFirstPayPrice60(BigDecimal.ZERO);
            }
            //计算新增付费留存90单价
            if (pay_loyal[8] > 0) {
                BigDecimal firstPayPrice90 = result.getCostPay()
                    .divide(BigDecimal.valueOf(pay_loyal[8]))
                    .setScale(2, RoundingMode.HALF_UP);
                result.setFirstPayPrice90(firstPayPrice90);
            } else {
                result.setFirstPayPrice90(BigDecimal.ZERO);
            }
            resList.add(result);
            //判断留存是否参与了统计
            //计算当前日期与拿到的日期相差几天
            long day = DateUtil.between(retainAnalyzeBo.getDateTime(), new Date(), DateUnit.DAY);
            if (day >= 1) {
                Integer payLoyal2 = tempCalculate.getPayLoyal2();
                payLoyal2 += pay_loyal[1];
                Integer payUserNumCount2 = tempCalculate.getPayUserNumCount2();
                payUserNumCount2 += payUserNumber;
                BigDecimal payUserCostCount2 = tempCalculate.getPayUserCostCount2()
                    .add(result.getCostPay());
                tempCalculate.setPayLoyal2(payLoyal2);
                tempCalculate.setPayUserCostCount2(payUserCostCount2);
                tempCalculate.setPayUserNumCount2(payUserNumCount2);
            }
            if (day >= 2) {
                Integer payLoyal3 = tempCalculate.getPayLoyal3();
                payLoyal3 += pay_loyal[2];
                Integer payUserNumCount3 = tempCalculate.getPayUserNumCount3();
                payUserNumCount3 += payUserNumber;
                BigDecimal payUserCostCount3 = tempCalculate.getPayUserCostCount3()
                    .add(result.getCostPay());
                tempCalculate.setPayLoyal3(payLoyal3);
                tempCalculate.setPayUserCostCount3(payUserCostCount3);
                tempCalculate.setPayUserNumCount3(payUserNumCount3);
            }
            if (day >= 6) {
                Integer payLoyal7 = tempCalculate.getPayLoyal7();
                payLoyal7 += pay_loyal[3];
                Integer payUserNumCount7 = tempCalculate.getPayUserNumCount7();
                payUserNumCount7 += payUserNumber;
                BigDecimal payUserCostCount7 = tempCalculate.getPayUserCostCount7()
                    .add(result.getCostPay());
                tempCalculate.setPayLoyal7(payLoyal7);
                tempCalculate.setPayUserCostCount7(payUserCostCount7);
                tempCalculate.setPayUserNumCount7(payUserNumCount7);
            }
            if (day >= 14) {
                Integer payLoyal15 = tempCalculate.getPayLoyal15();
                payLoyal15 += pay_loyal[4];
                Integer payUserNumCount15 = tempCalculate.getPayUserNumCount15();
                payUserNumCount15 += payUserNumber;
                BigDecimal payUserCostCount15 = tempCalculate.getPayUserCostCount15()
                    .add(result.getCostPay());
                tempCalculate.setPayLoyal15(payLoyal15);
                tempCalculate.setPayUserCostCount15(payUserCostCount15);
                tempCalculate.setPayUserNumCount15(payUserNumCount15);
            }
            if (day >= 29) {
                Integer payLoyal30 = tempCalculate.getPayLoyal30();
                payLoyal30 += pay_loyal[5];
                Integer payUserNumCount30 = tempCalculate.getPayUserNumCount30();
                payUserNumCount30 += payUserNumber;
                BigDecimal payUserCostCount30 = tempCalculate.getPayUserCostCount30()
                    .add(result.getCostPay());
                tempCalculate.setPayLoyal30(payLoyal30);
                tempCalculate.setPayUserCostCount30(payUserCostCount30);
                tempCalculate.setPayUserNumCount30(payUserNumCount30);
            }
            if (day >= 44) {
                Integer payLoyal45 = tempCalculate.getPayLoyal45();
                payLoyal45 += pay_loyal[6];
                Integer payUserNumCount45 = tempCalculate.getPayUserNumCount45();
                payUserNumCount45 += payUserNumber;
                BigDecimal payUserCostCount45 = tempCalculate.getPayUserCostCount45()
                    .add(result.getCostPay());
                tempCalculate.setPayLoyal45(payLoyal45);
                tempCalculate.setPayUserCostCount45(payUserCostCount45);
                tempCalculate.setPayUserNumCount45(payUserNumCount45);
            }
            if (day >= 59) {
                Integer payLoyal60 = tempCalculate.getPayLoyal60();
                payLoyal60 += pay_loyal[7];
                Integer payUserNumCount60 = tempCalculate.getPayUserNumCount60();
                payUserNumCount60 += payUserNumber;
                BigDecimal payUserCostCount60 = tempCalculate.getPayUserCostCount60()
                    .add(result.getCostPay());
                tempCalculate.setPayLoyal60(payLoyal60);
                tempCalculate.setPayUserCostCount60(payUserCostCount60);
                tempCalculate.setPayUserNumCount60(payUserNumCount60);
            }
            if (day >= 89) {
                Integer payLoyal90 = tempCalculate.getPayLoyal90();
                payLoyal90 += pay_loyal[8];
                Integer payUserNumCount90 = tempCalculate.getPayUserNumCount90();
                payUserNumCount90 += payUserNumber;
                BigDecimal payUserCostCount90 = tempCalculate.getPayUserCostCount90()
                    .add(result.getCostPay());
                tempCalculate.setPayLoyal90(payLoyal90);
                tempCalculate.setPayUserCostCount90(payUserCostCount90);
                tempCalculate.setPayUserNumCount90(payUserNumCount90);
            }
            Integer countUserNum = tempCalculate.getCountUserNum();
            Integer firstUserNum = tempCalculate.getFirstUserNum();
            countUserNum += result.getRegCount();
            firstUserNum += result.getFirstPayUser();
            tempCalculate.setCountUserNum(countUserNum);
            tempCalculate.setFirstUserNum(firstUserNum);
        }
        //计算合计的数据
        RetainNewLoyalVo allRetainNewLoyal = new RetainNewLoyalVo();
        allRetainNewLoyal.setDateTime("合计");
        allRetainNewLoyal.setGameName("--");
        allRetainNewLoyal.setChannelName("--");
        allRetainNewLoyal.setDealName("--");
        allRetainNewLoyal.setRegCount(tempCalculate.getCountUserNum());
        allRetainNewLoyal.setFirstPayUser(tempCalculate.getFirstUserNum());
        if (!costMap.isEmpty()) {
            BigDecimal sum = costMap.values().stream()
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
            allRetainNewLoyal.setCostPay(sum);
        }else{
            allRetainNewLoyal.setCostPay(BigDecimal.ZERO);
        }
        //计算合计的留存等信息
        if (null != tempCalculate.getPayLoyal2() && null != tempCalculate.getPayUserNumCount2()
            && tempCalculate.getPayUserNumCount2() > 0) {
            BigDecimal firstPayLoyal2 = BigDecimal.valueOf(tempCalculate.getPayLoyal2())
                .divide(BigDecimal.valueOf(tempCalculate.getPayUserNumCount2()))
                .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayLoyal(firstPayLoyal2 + "%");
        } else {
            allRetainNewLoyal.setFirstPayLoyal("0%");
        }
        if (null != tempCalculate.getPayLoyal3() && null != tempCalculate.getPayUserNumCount3()
            && tempCalculate.getPayUserNumCount3() > 0) {
            BigDecimal firstPayLoyal3 = BigDecimal.valueOf(tempCalculate.getPayLoyal3())
                .divide(BigDecimal.valueOf(tempCalculate.getPayUserNumCount3()))
                .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayLoyal3(firstPayLoyal3 + "%");
        } else {
            allRetainNewLoyal.setFirstPayLoyal3("0%");
        }
        if (null != tempCalculate.getPayLoyal7() && null != tempCalculate.getPayUserNumCount7()
            && tempCalculate.getPayUserNumCount7() > 0) {
            BigDecimal firstPayLoyal7 = BigDecimal.valueOf(tempCalculate.getPayLoyal7())
                .divide(BigDecimal.valueOf(tempCalculate.getPayUserNumCount7()))
                .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayLoyal7(firstPayLoyal7 + "%");
        } else {
            allRetainNewLoyal.setFirstPayLoyal7("0%");
        }
        if (null != tempCalculate.getPayLoyal15() && null != tempCalculate.getPayUserNumCount15()
            && tempCalculate.getPayUserNumCount15() > 0) {
            BigDecimal firstPayLoyal15 = BigDecimal.valueOf(tempCalculate.getPayLoyal15())
                .divide(BigDecimal.valueOf(tempCalculate.getPayUserNumCount15()))
                .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayLoyal(firstPayLoyal15 + "%");
        } else {
            allRetainNewLoyal.setFirstPayLoyal15("0%");
        }
        if (null != tempCalculate.getPayLoyal30() && null != tempCalculate.getPayUserNumCount30()
            && tempCalculate.getPayUserNumCount30() > 0) {
            BigDecimal firstPayLoyal30 = BigDecimal.valueOf(tempCalculate.getPayLoyal30())
                .divide(BigDecimal.valueOf(tempCalculate.getPayUserNumCount30()))
                .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayLoyal30(firstPayLoyal30 + "%");
        } else {
            allRetainNewLoyal.setFirstPayLoyal30("0%");
        }
        if (null != tempCalculate.getPayLoyal45() && null != tempCalculate.getPayUserNumCount45()
            && tempCalculate.getPayUserNumCount45() > 0) {
            BigDecimal firstPayLoyal45 = BigDecimal.valueOf(tempCalculate.getPayLoyal45())
                .divide(BigDecimal.valueOf(tempCalculate.getPayUserNumCount45()))
                .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayLoyal45(firstPayLoyal45 + "%");
        } else {
            allRetainNewLoyal.setFirstPayLoyal45("0%");
        }
        if (null != tempCalculate.getPayLoyal60() && null != tempCalculate.getPayUserNumCount60()
            && tempCalculate.getPayUserNumCount60() > 0) {
            BigDecimal firstPayLoyal60 = BigDecimal.valueOf(tempCalculate.getPayLoyal60())
                .divide(BigDecimal.valueOf(tempCalculate.getPayUserNumCount60()))
                .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayLoyal60(firstPayLoyal60 + "%");
        } else {
            allRetainNewLoyal.setFirstPayLoyal60("0%");
        }
        if (null != tempCalculate.getPayLoyal90() && null != tempCalculate.getPayUserNumCount90()
            && tempCalculate.getPayUserNumCount90() > 0) {
            BigDecimal firstPayLoyal90 = BigDecimal.valueOf(tempCalculate.getPayLoyal90())
                .divide(BigDecimal.valueOf(tempCalculate.getPayUserNumCount90()))
                .multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayLoyal90(firstPayLoyal90 + "%");
        } else {
            allRetainNewLoyal.setFirstPayLoyal90("0%");
        }
        //计算新增付费合计单价
        if (null != tempCalculate.getPayLoyal2() && null != tempCalculate.getPayUserCostCount2()
            && !tempCalculate.getPayUserCostCount2().equals(BigDecimal.ZERO)) {
            BigDecimal firstPayPrice =
                tempCalculate.getPayUserCostCount2()
                    .divide(BigDecimal.valueOf(tempCalculate.getPayLoyal2()))
                    .setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayPrice(firstPayPrice);
        } else {
            allRetainNewLoyal.setFirstPayPrice(BigDecimal.ZERO);
        }
        if (null != tempCalculate.getPayLoyal3() && null != tempCalculate.getPayUserCostCount3()
            && !tempCalculate.getPayUserCostCount3().equals(BigDecimal.ZERO)) {
            BigDecimal firstPayPrice3 =
                (tempCalculate.getPayUserCostCount3()).divide(
                        BigDecimal.valueOf(tempCalculate.getPayLoyal3()))
                    .setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayPrice3(firstPayPrice3);
        } else {
            allRetainNewLoyal.setFirstPayPrice3(BigDecimal.ZERO);
        }
        if (null != tempCalculate.getPayLoyal7() && null != tempCalculate.getPayUserCostCount7()
            && !tempCalculate.getPayUserCostCount7().equals(BigDecimal.ZERO)) {
            BigDecimal firstPayPrice7 =
                (tempCalculate.getPayUserCostCount7()).divide(
                        BigDecimal.valueOf(tempCalculate.getPayLoyal7()))
                    .setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayPrice7(firstPayPrice7);
        } else {
            allRetainNewLoyal.setFirstPayPrice7(BigDecimal.ZERO);
        }
        if (null != tempCalculate.getPayLoyal15() && null != tempCalculate.getPayUserCostCount15()
            && !tempCalculate.getPayUserCostCount15().equals(BigDecimal.ZERO)) {
            BigDecimal firstPayPrice15 =
                tempCalculate.getPayUserCostCount15()
                    .divide(BigDecimal.valueOf(tempCalculate.getPayLoyal15()))
                    .setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayPrice15(firstPayPrice15);
        } else {
            allRetainNewLoyal.setFirstPayPrice15(BigDecimal.ZERO);
        }
        if (null != tempCalculate.getPayLoyal30() && null != tempCalculate.getPayUserCostCount30()
            && !tempCalculate.getPayUserCostCount30().equals(BigDecimal.ZERO)) {
            BigDecimal firstPayPrice30 =
                (tempCalculate.getPayUserCostCount30()).divide(
                        BigDecimal.valueOf(tempCalculate.getPayLoyal30()))
                    .setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayPrice30(firstPayPrice30);
        } else {
            allRetainNewLoyal.setFirstPayPrice30(BigDecimal.ZERO);
        }
        if (null != tempCalculate.getPayLoyal45() && null != tempCalculate.getPayUserCostCount45()
            && !tempCalculate.getPayUserCostCount45().equals(BigDecimal.ZERO)) {
            BigDecimal firstPayPrice45 =
                tempCalculate.getPayUserCostCount45()
                    .divide(BigDecimal.valueOf(tempCalculate.getPayLoyal45()))
                    .setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayPrice45(firstPayPrice45);
        } else {
            allRetainNewLoyal.setFirstPayPrice45(BigDecimal.ZERO);
        }
        if (null != tempCalculate.getPayLoyal60() && null != tempCalculate.getPayUserCostCount60()
            && !tempCalculate.getPayUserCostCount60().equals(BigDecimal.ZERO)) {
            BigDecimal firstPayPrice60 =
                tempCalculate.getPayUserCostCount60()
                    .divide(BigDecimal.valueOf(tempCalculate.getPayLoyal60()))
                    .setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayPrice60(firstPayPrice60);
        } else {
            allRetainNewLoyal.setFirstPayPrice60(BigDecimal.ZERO);
        }
        if (null != tempCalculate.getPayLoyal90() && null != tempCalculate.getPayUserCostCount90()
            && !tempCalculate.getPayUserCostCount90().equals(BigDecimal.ZERO)) {
            BigDecimal firstPayPrice90 =
                tempCalculate.getPayUserCostCount90()
                    .divide(BigDecimal.valueOf(tempCalculate.getPayLoyal90()))
                    .setScale(2, RoundingMode.HALF_UP);
            allRetainNewLoyal.setFirstPayPrice90(firstPayPrice90);
        } else {
            allRetainNewLoyal.setFirstPayPrice90(BigDecimal.ZERO);
        }
        resList.add(allRetainNewLoyal);
        return resList;
    }

    /**
     * @param date1str
     * @author chenglin
     * @description 计算两者时间差方法
     * @date 14:45 2023/5/18
     **/
    public static int daysBetween(String date1str, String date2str) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(date1str);
        Date date2 = format.parse(date2str);
        int a = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
        return a;
    }

}
