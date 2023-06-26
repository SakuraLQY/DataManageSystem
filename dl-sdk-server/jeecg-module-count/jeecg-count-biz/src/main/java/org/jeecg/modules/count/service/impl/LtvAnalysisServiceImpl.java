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
import org.jeecg.modules.count.bo.LtvAnalysisBo;
import org.jeecg.modules.count.dto.LtvAnalysisDto;
import org.jeecg.modules.count.entity.LtvAnalysis;
import org.jeecg.modules.count.mapper.LtvAnalysisMapper;
import org.jeecg.modules.count.service.ILtvAnalysisService;
import org.jeecg.modules.count.vo.LtvAnalysisVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: ltv_analysis
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class LtvAnalysisServiceImpl extends ServiceImpl<LtvAnalysisMapper, LtvAnalysis> implements ILtvAnalysisService {

    @Resource
    private LtvAnalysisMapper ltvAnalysisMapper;
    @Override
    public List<LtvAnalysisVo> queryList(LtvAnalysisDto ltvAnalysisDto) {
        List<LtvAnalysisVo>resList = new ArrayList<>();
        QueryWrapper where = new QueryWrapper();
        if (ObjectUtils.isNotEmpty(ltvAnalysisDto.getGameId())) {
            where.in("a.game_id", ltvAnalysisDto.getGameId());
        }
        if (ObjectUtils.isNotEmpty(ltvAnalysisDto.getSubGameId())) {
            where.in("a.sub_game_id", ltvAnalysisDto.getSubGameId());
        }
        if (ObjectUtils.isNotEmpty(ltvAnalysisDto.getPkgId())) {
            where.in("a.pkg_id", ltvAnalysisDto.getPkgId());
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != ltvAnalysisDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ltvAnalysisDto.getStartTime(),
                        "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
            }
            if (null != ltvAnalysisDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ltvAnalysisDto.getEndTime(),
                        "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //根据判断条件进行选择
        switch (ltvAnalysisDto.getType()) {
            case " ":
                where.groupBy("a.time_daily");
                break;
            case "deal_id":
                where.groupBy("a.deal_id");
                break;
            case "channel_id":
                where.groupBy("a.channel_id");
                break;
            case "game_id":
                where.groupBy("a.game_id");
                break;
            case "sub_game_id":
                where.groupBy("a.sub_game_id");
                break;
            case "pkg_id":
                where.groupBy("a.pkg_id");
                break;
            default:
                where.groupBy("a.time_daily");
        }
        //进行查询
        List<LtvAnalysisBo>ltvAnalysisBoList  = null;
        if(ltvAnalysisDto.getType().equals(" ")){
            ltvAnalysisBoList = ltvAnalysisMapper.queryLtvAllList(where);
        }else{
            ltvAnalysisBoList = ltvAnalysisMapper.queryLtvByTypeList(ltvAnalysisDto.getType(),where);
        }
//        if(ltvAnalysisBoList.isEmpty()){
//            throw new RuntimeException("数据库没有数据!!");
//        }
        for (LtvAnalysisBo ltvAnalysisBo : ltvAnalysisBoList) {
            LtvAnalysisVo analysisVo = new LtvAnalysisVo();
            if(ltvAnalysisBo==null){
                continue;
            }
            //设置对应的ID和名称
            if(ltvAnalysisDto.getType().equals(" ")){
                String dateTime = DateUtil.format(ltvAnalysisBo.getDateTime(), "yyyy-MM-dd");
                analysisVo.setTypeId(dateTime);
                analysisVo.setName(dateTime);
            }else{
                analysisVo.setTypeId(String.valueOf(ltvAnalysisBo.getId()));
                switch (ltvAnalysisDto.getType()) {
                    case "deal_id":
                        String dealName =  ltvAnalysisMapper.selectByDealId(ltvAnalysisBo.getId());
                        analysisVo.setName(dealName);
                        break;
                    case "channel_id":
                        String channelName = ltvAnalysisMapper.selectByChannelId(ltvAnalysisBo.getId());
                        analysisVo.setName(channelName);
                        break;
                    case "game_id":
                        String gameName = ltvAnalysisMapper.selectByGameId(ltvAnalysisBo.getId());
                        analysisVo.setName(gameName);
                        break;
                    case "sub_game_id":
                        String subGameName = ltvAnalysisMapper.selectBySubGameId(ltvAnalysisBo.getId());
                        analysisVo.setName(subGameName);
                        break;
                    case "pkg_id":
                        String pkgName=ltvAnalysisMapper.selectByPkgId(ltvAnalysisBo.getId());
                        analysisVo.setName(pkgName);
                        break;
                    default:
                        analysisVo.setName(String.valueOf(DateUtil.format(ltvAnalysisBo.getDateTime(),"yyyy-MM-dd")));
                }
            }
            //查询对应的count_user
            Integer countUser = ltvAnalysisBo.getCountUser();
            if(countUser<=0){
                countUser = 1;
            }
            BigDecimal ltv1 = ltvAnalysisBo.getLtv1();
            BigDecimal ltv2 = ltv1.add(ltvAnalysisBo.getLtv2());
            BigDecimal ltv3 = ltv2.add(ltvAnalysisBo.getLtv3());
            BigDecimal ltv4 = ltv3.add(ltvAnalysisBo.getLtv4());
            BigDecimal ltv5 = ltv4.add(ltvAnalysisBo.getLtv5());
            BigDecimal ltv6 = ltv5.add(ltvAnalysisBo.getLtv6());
            BigDecimal ltv7 = ltv6.add(ltvAnalysisBo.getLtv7());
            BigDecimal ltv15 = ltv7.add(ltvAnalysisBo.getLtv15());
            BigDecimal ltv30 = ltv15.add(ltvAnalysisBo.getLtv30());
            BigDecimal ltv45 = ltv30.add(ltvAnalysisBo.getLtv45());
            BigDecimal ltv60 = ltv45.add(ltvAnalysisBo.getLtv60());
            BigDecimal ltv90 = ltv60.add(ltvAnalysisBo.getLtv90());
            BigDecimal ltv120 = ltv90.add(ltvAnalysisBo.getLtv120());
            BigDecimal ltv150 = ltv120.add(ltvAnalysisBo.getLtv150());
            //计算其ltv
            analysisVo.setLtv1(ltv1.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv2(ltv2.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv3(ltv3.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv4(ltv4.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv5(ltv5.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv6(ltv6.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv7(ltv7.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv15(ltv15.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv30(ltv30.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv45( ltv45.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv60(ltv60.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv90(ltv90.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv120(ltv120.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            analysisVo.setLtv150(ltv150.divide(BigDecimal.valueOf(countUser),2,RoundingMode.HALF_UP));
            resList.add(analysisVo);
        }
        return resList;
    }
}
