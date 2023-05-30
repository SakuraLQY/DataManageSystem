package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jeecg.modules.count.bo.PayAnalysisBo;
import org.jeecg.modules.count.bo.PayAnalysisTempBo;
import org.jeecg.modules.count.dto.PayAnalysisDto;
import org.jeecg.modules.count.entity.PayAnalysis;
import org.jeecg.modules.count.mapper.PayAnalysisMapper;
import org.jeecg.modules.count.service.IPayAnalysisService;
import org.jeecg.modules.count.vo.PayAnalysisVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: pay_analysis
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class PayAnalysisServiceImpl extends ServiceImpl<PayAnalysisMapper, PayAnalysis> implements IPayAnalysisService {

    @Resource
    private PayAnalysisMapper payAnalysisMapper;
    @Override
    public List<PayAnalysisVo> queryList(PayAnalysisDto payAnalysisDto) {
        QueryWrapper where2 = new QueryWrapper();
        QueryWrapper where3 = new QueryWrapper();
        if (ObjectUtils.isNotEmpty(payAnalysisDto.getGameId())) {
            where2.in("game_id", payAnalysisDto.getGameId());
            where3.in("game_id", payAnalysisDto.getGameId());
        }
        if (ObjectUtils.isNotEmpty(payAnalysisDto.getSubGameId())) {
            where2.in("sub_game_id", payAnalysisDto.getSubGameId());
            where3.in("sub_game_id", payAnalysisDto.getSubGameId());
        }
        if (ObjectUtils.isNotEmpty(payAnalysisDto.getPkgId())) {
            where2.in("pkg_id", payAnalysisDto.getPkgId());
            where3.in("pkg_id", payAnalysisDto.getPkgId());
        }

        Date startDate = null;
        Date endDate = null;
        try {
            if (null != payAnalysisDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payAnalysisDto.getStartTime(),
                        "yyyy-MM-dd 00:00:00"));
                where2.ge("time_daily", startDate);
                where3.ge("create_time",startDate);
            }
            if (null != payAnalysisDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payAnalysisDto.getEndTime(),
                        "yyyy-MM-dd 23:59:59"));
                where2.le("time_daily", endDate);
                where3.le("create_time", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //根据判断条件进行选择
        switch (payAnalysisDto.getType()) {
            case " ":
                where3.groupBy("create_time");
                where2.groupBy("time_daily");
                break;
            case "deal_id":
                where3.groupBy("deal_id");
                where2.groupBy("deal_id");
                break;
            case "channel_id":
                where3.groupBy("channel_id");
                where2.groupBy("channel_id");
                break;
            case "game_id":
                where3.groupBy("game_id");
                where2.groupBy("game_id");
                break;
            case "sub_game_id":
                where3.groupBy("sub_game_id");
                where2.groupBy("sub_game_id");
                break;
            default:
                where3.groupBy("create_time");
                where2.groupBy("time_daily");
        }
        //进行查询
        List<PayAnalysisBo>payAnalysisBoList =null;
        if(payAnalysisDto.getType().equals(" ")){
            payAnalysisBoList = payAnalysisMapper.selectByDaily(where2);
        }else{
            payAnalysisBoList = payAnalysisMapper.selectByTypeDaily(payAnalysisDto.getType(),where2);
        }
        List<PayAnalysisTempBo>payAnalysisTempBoList = null;
        if(payAnalysisDto.getType().equals(" ")){
            payAnalysisTempBoList = payAnalysisMapper.selectByOrder(where3);
        }else{
            payAnalysisTempBoList = payAnalysisMapper.selectByTypeOrder(payAnalysisDto.getType(),where3);
        }
        if(payAnalysisBoList==null || payAnalysisTempBoList==null){
            throw new RuntimeException("数据库数据为NULL");
        }
        List<PayAnalysisVo>resList = new ArrayList<>();
        for (PayAnalysisBo payAnalysisBo : payAnalysisBoList) {
            PayAnalysisVo analysisVo = new PayAnalysisVo();
            if(ObjectUtils.isEmpty(payAnalysisBo)){
                continue;
            }
            if (payAnalysisDto.getType().equals(" ")) {
                //拿到对应的日期
                String dateTime = String.valueOf(
                    DateUtil.format(payAnalysisBo.getDateTime(),"yyyy-MM-dd"));
                analysisVo.setTypeId(dateTime);
                analysisVo.setName(dateTime);

            } else {
                //根据对应的进行查询不同的名字
                switch (payAnalysisDto.getType()) {
                    case "deal_id":
                        String dealName =  payAnalysisMapper.selectByDealId(payAnalysisBo.getId());
                        analysisVo.setName(dealName);
                        analysisVo.setTypeId(String.valueOf(payAnalysisBo.getId()));
                        break;
                    case "channel_id":
                        String channelName = payAnalysisMapper.selectByChannelId(payAnalysisBo.getId());
                        analysisVo.setName(channelName);
                        analysisVo.setTypeId(String.valueOf(payAnalysisBo.getId()));
                        break;
                    case "game_id":
                        String gameName = payAnalysisMapper.selectByGameId(payAnalysisBo.getId());
                        analysisVo.setName(gameName);
                        analysisVo.setTypeId(String.valueOf(payAnalysisBo.getId()));
                        break;
                    case "sub_game_id":
                        String subGameName = payAnalysisMapper.selectBySubGameId(payAnalysisBo.getId());
                        analysisVo.setName(subGameName);
                        analysisVo.setTypeId(String.valueOf(payAnalysisBo.getId()));
                        break;
                    default:
                        analysisVo.setName(String.valueOf(DateUtil.format(payAnalysisBo.getDateTime(),"yyyy-MM-dd")));
                        analysisVo.setTypeId(String.valueOf(payAnalysisBo.getId()));
                }
            }
            //计算金额和费率
            Integer alivePayUserNum = 0;
            Integer totalPayUserNum = 0;
            BigDecimal alivePayMoney = BigDecimal.ZERO;
            BigDecimal totalPayMoney = BigDecimal.ZERO;
            for (PayAnalysisTempBo payAnalysisTempBo : payAnalysisTempBoList) {
                if(payAnalysisTempBo==null){
                    break;
                }
                if(payAnalysisDto.getType().equals(" ")){
                    if(payAnalysisBo.getDateTime()==payAnalysisTempBo.getDateTime()){
                        alivePayUserNum = payAnalysisTempBo.getPayUserNum()-payAnalysisBo.getFirstPayUser();
                        totalPayUserNum = payAnalysisTempBo.getPayUserNum();
                        alivePayMoney = payAnalysisTempBo.getPayMoney().subtract(payAnalysisBo.getFirstMoney());
                        totalPayMoney = payAnalysisTempBo.getPayMoney();
                        break;
                    }
                }else{
                    if(payAnalysisBo.getId().equals(payAnalysisTempBo.getId())){
                        alivePayUserNum = payAnalysisTempBo.getPayUserNum()-payAnalysisBo.getFirstPayUser();
                        totalPayUserNum = payAnalysisTempBo.getPayUserNum();
                        alivePayMoney = payAnalysisTempBo.getPayMoney().subtract(payAnalysisBo.getFirstMoney());
                        totalPayMoney = payAnalysisTempBo.getPayMoney();
                        break;
                    }
                }
            }
            //新增付费人数
            analysisVo.setFirstPayUser(payAnalysisBo.getFirstPayUser());
            //老用户付费人数
            analysisVo.setOldPayUser(alivePayUserNum);
            //总付费人数
            analysisVo.setTotalPayUser(totalPayUserNum);
            //新增付费金额
            analysisVo.setFirstPayMoney(payAnalysisBo.getFirstMoney());
            //老用户付费金额
            analysisVo.setOldPayMoney(alivePayMoney);
            //总金额
            analysisVo.setTotalPayMoney(totalPayMoney);
            //老用户ARPU
            if(ObjectUtils.isNotEmpty(payAnalysisBo.getCountUser()) && alivePayMoney.compareTo(BigDecimal.ZERO)>0 && payAnalysisBo.getCountUser()>0){
                BigDecimal oldArpu = alivePayMoney.
                    divide(BigDecimal.valueOf(payAnalysisBo.getCountUser()),
                    0,RoundingMode.HALF_UP);
                analysisVo.setOldArpu(oldArpu);
            }else{
                analysisVo.setOldArpu(BigDecimal.ZERO);
            }
            //老用户ARPPU
            if(alivePayUserNum>0 && alivePayMoney.compareTo(BigDecimal.ZERO)>0){
                BigDecimal oldArppu = alivePayMoney
                    .divide(BigDecimal.valueOf(alivePayUserNum),0,RoundingMode.HALF_UP);
                analysisVo.setOldArppu(oldArppu);
            }else{
                analysisVo.setOldArppu(BigDecimal.ZERO);
            }
            //ARPU
            if(ObjectUtils.isNotEmpty(payAnalysisBo.getCountUser()) && totalPayMoney.compareTo(BigDecimal.ZERO)>0 && payAnalysisBo.getCountUser()>0){
                BigDecimal arpu = totalPayMoney.
                    divide(BigDecimal.valueOf(payAnalysisBo.getCountUser()),
                        0,RoundingMode.HALF_UP);
                analysisVo.setArpu(arpu);
            }else{
                analysisVo.setArpu(BigDecimal.ZERO);
            }
            //ARPPU
            if(totalPayUserNum>0 && totalPayMoney.compareTo(BigDecimal.ZERO)>0){
                BigDecimal arppu = totalPayMoney
                    .divide(BigDecimal.valueOf(totalPayUserNum),0,RoundingMode.HALF_UP);
                analysisVo.setArppu(arppu);
            }else{
                analysisVo.setArppu(BigDecimal.ZERO);
            }
            //新增付费率
            if(payAnalysisBo.getFirstPayUser()!=null && payAnalysisBo.getCountUser()>0){
                BigDecimal firstPayRate = BigDecimal.valueOf(payAnalysisBo.getFirstPayUser())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(payAnalysisBo.getCountUser()),2,RoundingMode.HALF_UP);
                analysisVo.setFirstPayRate(firstPayRate+"%");
            }else {
                analysisVo.setFirstPayRate("0");
            }
            //总付费率
            if(payAnalysisBo.getCountUser()>0 && totalPayUserNum!=null){
                BigDecimal totalRate = BigDecimal.valueOf(totalPayUserNum)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(payAnalysisBo.getCountUser()),2,RoundingMode.HALF_UP);
                analysisVo.setTotalRate(totalRate+"%");
            }else{
                analysisVo.setTotalRate("0");
            }
            resList.add(analysisVo);
        }
        return resList;
    }
}
