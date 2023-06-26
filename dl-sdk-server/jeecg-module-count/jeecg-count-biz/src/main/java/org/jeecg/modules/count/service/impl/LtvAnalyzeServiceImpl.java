package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang.time.DateFormatUtils;

import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.dto.LtvDto;
import org.jeecg.modules.count.entity.LtvAnalyze;
import org.jeecg.modules.count.mapper.LtvAnalyzeMapper;
import org.jeecg.modules.count.service.ILtvAnalyzeService;
import org.jeecg.modules.count.vo.LtvPaybackVo;
import org.jeecg.modules.count.vo.LtvVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: LTV分析
 * @Author: jeecg-boot
 * @Date:   2023-05-13
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class LtvAnalyzeServiceImpl extends ServiceImpl<LtvAnalyzeMapper, LtvAnalyze> implements ILtvAnalyzeService {

    @Resource
    private LtvAnalyzeMapper ltvAnalyzeMapper;
    @Override
    public List<LtvVo> qeuryLtvList(LtvDto ltvDto) {
        QueryWrapper<LtvDto>where = new QueryWrapper<>();
        //查询对应的ct_daily_payback
        String gameName = "全部游戏";
        String channelName = "全部渠道";
        String dealName = "全部广告";
        if (null != ltvDto.getGameId() && !ltvDto.getGameId().isEmpty()) {
            where.in("a.game_id", ltvDto.getGameId());
            gameName = "多款游戏";
        }
        if (null != ltvDto.getSubGameId() && !ltvDto.getSubGameId().isEmpty()) {
            where.in("a.sub_game_id", ltvDto.getSubGameId());
            gameName += "(多款子游戏)";
        } else {
            if (null == ltvDto.getGameId() || ltvDto.getGameId().isEmpty()) {
                gameName += "(全部子游戏)";
            }
        }
        if (null != ltvDto.getPkgId() && !ltvDto.getPkgId().isEmpty()) {
            where.in("a.pkg_id", ltvDto.getPkgId());
            gameName += "(多款渠道游戏包)";
        } else {
            if (null == ltvDto.getSubGameId() || ltvDto.getSubGameId().isEmpty()) {
                gameName += "(全部渠道游戏包)";
            }
        }
        if (null != ltvDto.getChannelTypeId() && !ltvDto.getChannelTypeId().isEmpty()) {
            where.in("a.channel_type_id", ltvDto.getChannelTypeId());
        }
        if (null != ltvDto.getChannelId() && !ltvDto.getChannelId().isEmpty()) {
            where.in("a.channel_id", ltvDto.getChannelId());
            channelName = "多款渠道";
        }
        if (null != ltvDto.getChannelSubAccountId() && !ltvDto.getChannelSubAccountId().isEmpty()) {
            where.in("a.channel_sub_account_id", ltvDto.getChannelSubAccountId());
            channelName += "(多款子渠道)";
        } else {
            if (null == ltvDto.getChannelId() || ltvDto.getChannelId().isEmpty()) {
                channelName += "(全部子渠道)";
            }
        }
        if (null != ltvDto.getDealId() && !ltvDto.getDealId().isEmpty()) {
            where.in("a.deal_id", ltvDto.getDealId());
            dealName = "多款广告";
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != ltvDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ltvDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
            }
            if (null != ltvDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ltvDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (null != ltvDto.getCreateUser()) {
            where.eq("f.create_by", ltvDto.getCreateUser());
        }
        //开始查询对应的LTV数据中的日期、游戏名、渠道名、广告名
        //1.拼接150个的sql语句
        String ltvSql = "";
        for (int i = 1; i <= 150; i++) {
            ltvSql +="IFNULL(SUM(b.day"+i+"),0) as ltv" + i+",";
        }
        //拿到按日期分类的对应的ltv1-150的数据
        List<LtvPaybackVo>list = ltvAnalyzeMapper.getPaybackLtvData(where,ltvSql);
        List<LtvVo>resList = new ArrayList<>();
        //合计的注册数
        Integer allRegCount = 0;
        Map<String, BigDecimal>allRemainLtv = new HashMap<>();
        BigDecimal count_ltv = BigDecimal.ZERO;
        //存储查询合計出来的ltv
        Map<String,BigDecimal>countLtvSummary = new HashMap<>();
        Map<String,BigDecimal>countUserLtv = new HashMap<>();
        for (int i = 1; i <= 150; i++) {
            countLtvSummary.put("ltv"+i,BigDecimal.ZERO);
            countUserLtv.put("count_user"+i,BigDecimal.ZERO);
        }
        if(null!=list && !list.isEmpty()){
            for (LtvPaybackVo ltvPaybackVo : list) {
                LtvVo ltvVo = new LtvVo();
                //日期
                ltvVo.setLtvDate(String.valueOf(ltvPaybackVo.getLtvDate()));
                //游戏名
                ltvVo.setGameName(gameName);
                //渠道名
                ltvVo.setDealName(dealName);
                //广告名
                ltvVo.setChannelName(channelName);
                //使用反射直接获取到我想要的150个值
                Map<String,BigDecimal>remainLtv = new HashMap<>();
                Map<String,BigDecimal>tempLtv = new HashMap<>();
                BigDecimal ltv1 = ltvPaybackVo.getLtv1();
                count_ltv.add(ltv1);
                BigDecimal value = null;
                if(null!=ltv1 && ltvPaybackVo.getRegCount().compareTo(0)>0){
                   value = ltv1
                        .divide(BigDecimal.valueOf(ltvPaybackVo.getRegCount()))
                        .setScale(2);
                   remainLtv.put("ltv1",value);
                   tempLtv.put("ltv1",value);
                }else{
                    value = BigDecimal.ZERO;
                    remainLtv.put("ltv1",value);
                    tempLtv.put("ltv1",value);
                }
                Class<LtvPaybackVo>ltvPaybackVoClass = LtvPaybackVo.class;
                for (int i = 2; i <=150; i++) {
                    int j = i-1;
                    Field declaredField = null;
                    //拿到當前的值
                    try {
                        declaredField = ltvPaybackVoClass.getDeclaredField("ltv" + i);

                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    declaredField.setAccessible(true);
                    BigDecimal values = null;
                    try {
                        values = (BigDecimal) declaredField.get(ltvPaybackVo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    BigDecimal tempData = null;
                    //进行累加计算
                    if(values!=null && null!=tempLtv.get("ltv"+j)){
                        tempData = values.add(tempLtv.get("ltv"+j));
                        tempLtv.put("ltv"+i,tempData);
                    }else{
                        tempLtv.put("ltv"+i,BigDecimal.ZERO);
                    }
                    if(null!=tempData && tempData.compareTo(new BigDecimal(0))>0 && ltvPaybackVo.getRegCount()>0 ){
                        BigDecimal temp1 = tempLtv.get("ltv"+i);
                        BigDecimal temp2 = BigDecimal.valueOf(ltvPaybackVo.getRegCount());
                        remainLtv.put("ltv"+i,temp1
                            .divide(temp2,2,RoundingMode.HALF_UP));
                    }else{
                        remainLtv.put("ltv"+i,BigDecimal.ZERO);
                    }
                }
                //将查询的ltv封装进去
                ltvVo.setRemainLtv(remainLtv);
                ltvVo.setRegCount(ltvPaybackVo.getRegCount());
                //合计注册人数的计算
                allRegCount+=ltvVo.getRegCount();
                //定义随时间的不同每一列的计算
                int day = DateUtils.dateToDiff('d',new Date(),
                DateUtils.str2Date(ltvPaybackVo.getLtvDate(), DateUtils.date_sdf.get()));
                for (int i = 1; i <=149 ; i++) {
                    if(day>=i){
                        countLtvSummary.put("ltv"+(i+1),countLtvSummary.get("ltv"+(i+1)).add(remainLtv.get("ltv"+(i+1))));
                        countUserLtv.put("count_user"+(i+1),countUserLtv.get("count_user"+(i+1)).add(BigDecimal.valueOf(ltvPaybackVo.getRegCount())));
                        }
                    }
                resList.add(ltvVo);
            }
            }
            LtvVo allLtvVo = new LtvVo();
            allLtvVo.setLtvDate("合计");
            allLtvVo.setChannelName("--");
            allLtvVo.setGameName("--");
            allLtvVo.setDealName("--");
            allLtvVo.setRegCount(allRegCount);
            //计算合计的LTV1
        if(count_ltv!=null && allRegCount>0 ){
            BigDecimal LTV1 = count_ltv.divide(BigDecimal.valueOf(allRegCount),2,RoundingMode.HALF_UP);
            allRemainLtv.put("ltv1",LTV1);
        }else{
            allRemainLtv.put("ltv1",BigDecimal.ZERO);
        }
        //计算剩下的合计LTV
        for(int i = 2;i<=150;i++){
            if(null!=countLtvSummary.get("ltv"+i) && null!=countUserLtv.get("count_user"+i)&&countUserLtv.get("count_user"+i).compareTo(BigDecimal.ZERO)>0){
                BigDecimal tempCountLtv = countLtvSummary.get("ltv"+i);
                BigDecimal tempCountUser = countUserLtv.get("count_user"+i);
                allRemainLtv.put("ltv"+i,tempCountLtv.divide(tempCountUser,2,RoundingMode.HALF_UP));
            }else{
                allRemainLtv.put("ltv"+i,BigDecimal.ZERO);
            }
        }
        allLtvVo.setRemainLtv(allRemainLtv);
        resList.add(allLtvVo);
        return resList;
    }
}
