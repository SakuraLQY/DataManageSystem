package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.bo.ChannelAllTempBo;
import org.jeecg.modules.count.bo.ChannelDetailAllBo;
import org.jeecg.modules.count.bo.ChannelDetailBo;
import org.jeecg.modules.count.bo.ChannelDetailTempBo;
import org.jeecg.modules.count.bo.ChannelTotalBo;
import org.jeecg.modules.count.dto.ChannelDetailDto;
import org.jeecg.modules.count.entity.ChannelDetails;
import org.jeecg.modules.count.mapper.ChannelDetailsMapper;
import org.jeecg.modules.count.modal.ChannelDetailModal;
import org.jeecg.modules.count.service.IChannelDetailsService;
import org.jeecg.modules.count.vo.ChannelDetailVo;

import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 渠道明细表数据
 * @Author: jeecg-boot
 * @Date: 2023-05-11
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_countly")
public class ChannelDetailsServiceImpl extends
    ServiceImpl<ChannelDetailsMapper, ChannelDetails> implements IChannelDetailsService {

    private static final String DETAIL_DATA = "detail";

    private static final String ALL_DETAIL = "total";
    @Value("${jeecg.path.upload}")
    private String upLoadPath;
    @Resource
    private ChannelDetailsMapper channelDetailsMapper;

    @Override
    public List<ChannelDetailVo> queryList(ChannelDetailDto channelDetailDto) {
        QueryWrapper q = new QueryWrapper();
        List<ChannelDetailVo>list = new ArrayList<>();
            if (oConvertUtils.isNotEmpty(channelDetailDto.getGameId())&&channelDetailDto.getGameId().length>=1) {
                q.in("game_id", channelDetailDto.getGameId());
            }
            if (oConvertUtils.isNotEmpty(channelDetailDto.getSubGameId())&& channelDetailDto.getSubGameId().length>=1) {
                q.in("sub_game_id", channelDetailDto.getSubGameId());
            }
            if (oConvertUtils.isNotEmpty(channelDetailDto.getChannelId()) && channelDetailDto.getChannelId().length>=1) {
                q.in("channel_id", channelDetailDto.getChannelId());
            }
            if (oConvertUtils.isNotEmpty(channelDetailDto.getChannelTypeId()) &&channelDetailDto.getChannelTypeId().length>=1) {
                q.in("channel_type_id", channelDetailDto.getChannelTypeId());
            }
            if (oConvertUtils.isNotEmpty(channelDetailDto.getStartTime()) && oConvertUtils.isNotEmpty(
                channelDetailDto.getEndTime())) {
                q.ge("time_daily", DateUtil.beginOfMinute(channelDetailDto.getStartTime()));
                q.le("time_daily", DateUtil.endOfDay(channelDetailDto.getEndTime()));
            }
        //查询数据信息
        List<ChannelDetailBo> tempData = new ArrayList<>();
        if (channelDetailDto.getShowType().equals(DETAIL_DATA)) {
            tempData = channelDetailsMapper.queryListByDetail(q, "a.game_id", "a.channel_id",
                "a.sub_game_id");
        }
        if (channelDetailDto.getShowType().equals(ALL_DETAIL)) {
            tempData = channelDetailsMapper.queryListByTotal(q);
        }
        for (ChannelDetailBo tempDatum : tempData) {
            //用来接收返回给前端的实体类
            ChannelDetailVo channelDetailVo = new ChannelDetailVo();
            //依据选项不同，将共有对象先进行拷贝过去
            if (channelDetailDto.getShowType().equals(ALL_DETAIL)) {
                ChannelTotalBo channelDetailTempBo = new ChannelTotalBo();
                BeanUtils.copyProperties(tempDatum, channelDetailTempBo);
                BeanUtils.copyProperties(channelDetailTempBo, channelDetailVo);
                String dateTime = DateUtil.format(tempDatum.getTimeDaily(), "yyyy-MM-dd");
                channelDetailVo.setTimeDaily(dateTime);
                channelDetailVo.setGameName("全部游戏");
                channelDetailVo.setSubGameName("全部子游戏");
                channelDetailVo.setChannel("全部渠道");
            }
            if (channelDetailDto.getShowType().equals(DETAIL_DATA)) {
                ChannelDetailTempBo channelDetailTempBo = new ChannelDetailTempBo();
                BeanUtils.copyProperties(tempDatum, channelDetailTempBo);
                //将除了id字段都拷贝过去
                BeanUtils.copyProperties(channelDetailTempBo, channelDetailVo);
                String gameName = channelDetailsMapper.getGameNameById((int)tempDatum.getGameName());
                String subGameName = channelDetailsMapper.getSubGameNameByid(
                    (int)tempDatum.getSubGameName());
                String channelName = channelDetailsMapper.getChannelNameById(
                    (int)tempDatum.getChannel());
                String dateTime = DateUtil.format(tempDatum.getTimeDaily(), "yyyy-MM-dd");
                channelDetailVo.setGameName(gameName);
                channelDetailVo.setSubGameName(subGameName);
                channelDetailVo.setChannel(channelName);
                channelDetailVo.setTimeDaily(dateTime);
            }
            //计算激活注册率
            if (tempDatum.getCountActiveDev() != 0) {
                BigDecimal activeRate = BigDecimal.valueOf(tempDatum.getCountUserDev())
                    .divide(BigDecimal.valueOf(tempDatum.getCountActiveDev()),2,RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setActiveRate(activeRate + "%");
            }else{
                channelDetailVo.setActiveRate(String.valueOf(BigDecimal.ZERO));
            }
            //计算新增付费率
            if (tempDatum.getCountUser() != 0) {
                BigDecimal firstPayRate = BigDecimal.valueOf(tempDatum.getFirstPayUser())
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setFirstPayRate(firstPayRate + "%");
            }else{
                channelDetailVo.setFirstPayRate(String.valueOf(BigDecimal.ZERO));
            }
            //计算新增arpu
            if (tempDatum.getCountUser() != 0) {
                BigDecimal firstArpu = BigDecimal.valueOf(tempDatum.getFirstMoney())
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2, RoundingMode.HALF_UP);
                channelDetailVo.setFirstArpu(firstArpu);
            }else{
                channelDetailVo.setFirstArpu(BigDecimal.ZERO);
            }
            //计算新增arppu
            if (tempDatum.getFirstPayUser() != 0) {
                BigDecimal firstArppu = BigDecimal.valueOf(tempDatum.getFirstMoney())
                    .divide(BigDecimal.valueOf(tempDatum.getFirstPayUser()),2, RoundingMode.HALF_UP);
                channelDetailVo.setFirstArppu(firstArppu);
            }else{
                channelDetailVo.setFirstArppu(BigDecimal.ZERO);
            }
            //计算老用户付费数
            Integer oldPayUser = tempDatum.getAlivePayUser() - tempDatum.getFirstPayUser();
            channelDetailVo.setOldPayUser(oldPayUser);
            //计算老用户付费金额
            BigDecimal oldMoney = BigDecimal.valueOf(tempDatum.getAliveMoney())
                .subtract(BigDecimal.valueOf(tempDatum.getFirstMoney()))
                .setScale(2, RoundingMode.HALF_UP);
            channelDetailVo.setOldMoney(oldMoney);
            //计算老用户的付费率
            BigDecimal oldDau = BigDecimal.valueOf(tempDatum.getDau())
                .subtract(BigDecimal.valueOf(tempDatum.getCountUser()));
            if (!oldDau.equals(BigDecimal.ZERO) ) {
                BigDecimal oldPayRate = BigDecimal.valueOf(oldPayUser).divide(oldDau,1, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setOldPayRate(oldPayRate+"%");
            }else{
                channelDetailVo.setOldPayRate(String.valueOf(BigDecimal.ZERO));
            }
            //计算老用户的arpu
            if(!oldDau.equals(BigDecimal.ZERO)){
                BigDecimal oldArpu = oldMoney.divide(oldDau,2, RoundingMode.HALF_UP);
                channelDetailVo.setOldArpu(oldArpu);
            }else{
                channelDetailVo.setOldArpu(BigDecimal.ZERO);
            }
            //计算老用户的arppu
            if(oldPayUser!=0){
                BigDecimal oldArppu = oldMoney.divide(BigDecimal.valueOf(oldPayUser),2, RoundingMode.HALF_UP);
                channelDetailVo.setOldArppu(oldArppu);
            }else{
                channelDetailVo.setOldArppu(BigDecimal.ZERO);
            }
            //计算arpu
            if(tempDatum.getDau()!=0){
                BigDecimal arpu = BigDecimal.valueOf(tempDatum.getAlivePayUser())
                    .divide(BigDecimal.valueOf(tempDatum.getDau()),2, RoundingMode.HALF_UP);
                channelDetailVo.setArpu(arpu);
            }else{
                channelDetailVo.setArpu(BigDecimal.ZERO);
            }
            //计算arppu
            if(tempDatum.getAlivePayUser()!=0){
                BigDecimal arppu = BigDecimal.valueOf(tempDatum.getAliveMoney())
                    .divide(BigDecimal.valueOf(tempDatum.getAlivePayUser()),2, RoundingMode.HALF_UP);
                channelDetailVo.setArppu(arppu);
            }else{
                channelDetailVo.setArppu(BigDecimal.ZERO);
            }
            //计算总费率
            if(tempDatum.getDau()!=0){
                BigDecimal totalPayRate = BigDecimal.valueOf(tempDatum.getTotalMoney())
                    .divide(BigDecimal.valueOf(tempDatum.getDau()),2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setTotalPayRate(totalPayRate+"");
            }else{
                channelDetailVo.setTotalPayRate(String.valueOf(BigDecimal.ZERO));
            }
            //计算ltv
            if(tempDatum.getCountUser()!=0){
               BigDecimal ltv1 =  tempDatum.getLtv1()
                   .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP);
               channelDetailVo.setLtv1(ltv1);
            }else{
                channelDetailVo.setLtv1(BigDecimal.ZERO);
            }
            if(tempDatum.getCountUser()!=0){
                BigDecimal ltv2 =  tempDatum.getLtv2()
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP);
                channelDetailVo.setLtv2(ltv2);
            }else{
                channelDetailVo.setLtv2(BigDecimal.ZERO);
            }
            if(tempDatum.getCountUser()!=0){
                BigDecimal ltv2 =  tempDatum.getLtv2()
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP);
                channelDetailVo.setLtv1(ltv2);
            }else{
                channelDetailVo.setLtv2(BigDecimal.ZERO);
            }
            if(tempDatum.getCountUser()!=0){
                BigDecimal ltv3 =  tempDatum.getLtv3()
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP);
                channelDetailVo.setLtv3(ltv3);
            }else{
                channelDetailVo.setLtv3(BigDecimal.ZERO);
            }
            if(tempDatum.getCountUser()!=0){
                BigDecimal ltv4 =  tempDatum.getLtv4()
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP);
                channelDetailVo.setLtv4(ltv4);
            }else{
                channelDetailVo.setLtv4(BigDecimal.ZERO);
            }
            if(tempDatum.getCountUser()!=0){
                BigDecimal ltv5 =  tempDatum.getLtv5()
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP);
                channelDetailVo.setLtv5(ltv5);
            }else{
                channelDetailVo.setLtv5(BigDecimal.ZERO);
            }
            if(tempDatum.getCountUser()!=0){
                BigDecimal ltv6 =  tempDatum.getLtv6()
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP);
                channelDetailVo.setLtv6(ltv6);
            }else{
                channelDetailVo.setLtv6(BigDecimal.ZERO);
            }
            if(tempDatum.getCountUser()!=0){
                BigDecimal ltv7 =  tempDatum.getLtv7()
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP);
                channelDetailVo.setLtv7(ltv7);
            }else{
                channelDetailVo.setLtv7(BigDecimal.ZERO);
            }
            //计算留存
            if(tempDatum.getLoyal()!=null && tempDatum.getCountUser()!=0){
                BigDecimal loyal = BigDecimal.valueOf(tempDatum.getLoyal())
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setLoyal(loyal+"%");
            }else{
                channelDetailVo.setLoyal("0");
            }
            if(tempDatum.getLoyal3()!=null && tempDatum.getCountUser()!=0){
                BigDecimal loyal3 = BigDecimal.valueOf(tempDatum.getLoyal3())
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setLoyal3(loyal3+"%");
            }else{
                channelDetailVo.setLoyal3("0");
            }
            if(tempDatum.getLoyal4()!=null && tempDatum.getCountUser()!=0){
                BigDecimal loyal4 = BigDecimal.valueOf(tempDatum.getLoyal4())
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setLoyal4(loyal4+"%");
            }else{
                channelDetailVo.setLoyal4("0");
            }
            if(tempDatum.getLoyal5()!=null && tempDatum.getCountUser()!=0){
                BigDecimal loyal5 = BigDecimal.valueOf(tempDatum.getLoyal5())
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setLoyal5(loyal5+"%");
            }else{
                channelDetailVo.setLoyal5("0");
            }
            if(tempDatum.getLoyal6()!=null && tempDatum.getCountUser()!=0){
                BigDecimal loyal6 = BigDecimal.valueOf(tempDatum.getLoyal6())
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setLoyal6(loyal6+"%");
            }else{
                channelDetailVo.setLoyal6("0");
            }
            if(tempDatum.getLoyal7()!=null && tempDatum.getCountUser()!=0){
                BigDecimal loyal7 = BigDecimal.valueOf(tempDatum.getLoyal7())
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2,RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setLoyal7(loyal7+"%");
            }else{
                channelDetailVo.setLoyal7("0");
            }
            list.add(channelDetailVo);
        }
        //todo 计算合计栏
        ChannelDetailVo allChannelVo = new ChannelDetailVo();
        allChannelVo.setTimeDaily("合计");
        allChannelVo.setGameName("---");
        allChannelVo.setChannel("---");
        allChannelVo.setSubGameName("---");
        ChannelDetailAllBo channelDetailAllBo = channelDetailsMapper.getSumChannel(q);
        ChannelAllTempBo temp = new ChannelAllTempBo();
        BeanUtils.copyProperties(channelDetailAllBo,temp);
        BeanUtils.copyProperties(temp,allChannelVo);
        //计算激活注册率
        if(channelDetailAllBo.getCountUser()!=null && channelDetailAllBo.getCountActiveDev()!=0){
           BigDecimal activeRate =  BigDecimal.valueOf(channelDetailAllBo.getCountUser())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountActiveDev()),1,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            allChannelVo.setActiveRate(activeRate+"%");
        }else{
            allChannelVo.setActiveRate("0");
        }
        //计算老用户付费数
        Integer alivePayUser = channelDetailAllBo.getAlivePayUser();
        Integer firstPayUser = channelDetailAllBo.getFirstPayUser();
        Integer oldPayUser = alivePayUser-firstPayUser;
        allChannelVo.setOldPayUser(oldPayUser);
        //计算老用户的dau
        Integer oldDau = channelDetailAllBo.getDau()- channelDetailAllBo.getCountUser();
        //计算老用户的付费金额
        Integer oldPayMoney = channelDetailAllBo.getAliveMoney()- channelDetailAllBo.getFirstMoney();
        allChannelVo.setOldMoney(BigDecimal.valueOf(oldPayMoney));
        //计算老用户的付费率
        if(oldPayUser!=null && oldDau!=0){
            BigDecimal oldPayRate = BigDecimal.valueOf(oldPayUser)
                .divide(BigDecimal.valueOf(oldDau),2,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            allChannelVo.setOldPayRate(oldPayRate+"%");
        }else{
            allChannelVo.setOldPayRate("0");
        }
        //计算老用户的arpu
        if(oldPayMoney!=null && oldDau!=0){
            BigDecimal oldArpu = BigDecimal.valueOf(oldPayMoney)
                .divide(BigDecimal.valueOf(oldDau),2,RoundingMode.HALF_UP);
            allChannelVo.setOldArpu(oldArpu);
        }else{
            allChannelVo.setOldArpu(BigDecimal.ZERO);
        }
        //计算老用户的arppu
        if(oldPayMoney!=null && oldPayUser!=0){
            BigDecimal oldArppu = BigDecimal.valueOf(oldPayMoney)
                .divide(BigDecimal.valueOf(oldPayUser),2,RoundingMode.HALF_UP);
            allChannelVo.setOldArppu(oldArppu);
        }else{
            allChannelVo.setOldArppu(BigDecimal.ZERO);
        }
        //计算新增Arpu
        if(channelDetailAllBo.getFirstMoney()!=null && channelDetailAllBo.getCountUser()!=0){
            BigDecimal firstArpu = BigDecimal.valueOf(channelDetailAllBo.getFirstMoney())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP);
            allChannelVo.setFirstArpu(firstArpu);
        }else{
            allChannelVo.setFirstArpu(BigDecimal.ZERO);
        }
        //计算新增Arpu
        if(channelDetailAllBo.getFirstMoney()!=null && channelDetailAllBo.getFirstPayUser()!=0){
            BigDecimal firstArppu = BigDecimal.valueOf(channelDetailAllBo.getFirstMoney())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getFirstPayUser()),2,RoundingMode.HALF_UP);
            allChannelVo.setFirstArppu(firstArppu);
        }else{
            allChannelVo.setFirstArppu(BigDecimal.ZERO);
        }
        //计算ARPU
        if(channelDetailAllBo.getAliveMoney()!=null && channelDetailAllBo.getDau()!=0){
            BigDecimal arpu = BigDecimal.valueOf(channelDetailAllBo.getAliveMoney())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getDau()),2,RoundingMode.HALF_UP);
            allChannelVo.setArpu(arpu);
        }else{
            allChannelVo.setArpu(BigDecimal.ZERO);
        }
        //計算ARPPU
        if(channelDetailAllBo.getFirstMoney()!=null && channelDetailAllBo.getAlivePayUser()!=0){
            BigDecimal arppu = BigDecimal.valueOf(channelDetailAllBo.getFirstMoney())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getAlivePayUser()),2,RoundingMode.HALF_UP);
            allChannelVo.setArppu(arppu);
        }else{
            allChannelVo.setArppu(BigDecimal.ZERO);
        }
        //计算新增付费率
        if(channelDetailAllBo.getFirstPayUser()!=null && channelDetailAllBo.getCountUser()!=0){
            BigDecimal firstPayRate = BigDecimal.valueOf(channelDetailAllBo.getFirstPayUser())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            allChannelVo.setFirstPayRate(firstPayRate+"%");
        }else{
            allChannelVo.setFirstPayRate("0");
        }
        //计算总付费率
        if(channelDetailAllBo.getAlivePayUser()!=null && channelDetailAllBo.getDau()!=0){
            BigDecimal totalRate = BigDecimal.valueOf(channelDetailAllBo.getAlivePayUser())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getDau()),2,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            allChannelVo.setTotalPayRate(totalRate+"%");
        }else{
            allChannelVo.setTotalPayRate("0");
        }
        //计算LTV
        if(channelDetailAllBo.getCountUser()!=0){
            BigDecimal ltv1 = channelDetailAllBo.getLtv1()
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP);
            allChannelVo.setLtv1(ltv1);
        }else{
            allChannelVo.setLtv1(BigDecimal.ZERO);
        }
        if(channelDetailAllBo.getCountUser()!=0){
            BigDecimal ltv2 = channelDetailAllBo.getLtv2()
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP);
            allChannelVo.setLtv2(ltv2);
        }else{
            allChannelVo.setLtv2(BigDecimal.ZERO);
        }
        if(channelDetailAllBo.getCountUser()!=0){
            BigDecimal ltv3 = channelDetailAllBo.getLtv3()
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP);
            allChannelVo.setLtv3(ltv3);
        }else{
            allChannelVo.setLtv3(BigDecimal.ZERO);
        }
        if(channelDetailAllBo.getCountUser()!=0){
            BigDecimal ltv4 = channelDetailAllBo.getLtv4()
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP);
            allChannelVo.setLtv4(ltv4);
        }else{
            allChannelVo.setLtv4(BigDecimal.ZERO);
        }
        if(channelDetailAllBo.getCountUser()!=0){
            BigDecimal ltv5 = channelDetailAllBo.getLtv5()
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP);
            allChannelVo.setLtv5(ltv5);
        }else{
            allChannelVo.setLtv5(BigDecimal.ZERO);
        }
        if(channelDetailAllBo.getCountUser()!=0){
            BigDecimal ltv6 = channelDetailAllBo.getLtv6()
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP);
            allChannelVo.setLtv6(ltv6);
        }else{
            allChannelVo.setLtv6(BigDecimal.ZERO);
        }
        if(channelDetailAllBo.getCountUser()!=0){
            BigDecimal ltv7 = channelDetailAllBo.getLtv7()
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP);
            allChannelVo.setLtv7(ltv7);
        }else{
            allChannelVo.setLtv7(BigDecimal.ZERO);
        }
        //计算留存
        if(channelDetailAllBo.getLoyal()!=null && channelDetailAllBo.getCountUser()!=0){
            BigDecimal loyal = BigDecimal.valueOf(channelDetailAllBo.getLoyal())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            allChannelVo.setLoyal(loyal+"%");
        }else{
            allChannelVo.setLoyal("0");
        }
        if(channelDetailAllBo.getLoyal3()!=null && channelDetailAllBo.getCountUser()!=0){
            BigDecimal loyal3 = BigDecimal.valueOf(channelDetailAllBo.getLoyal3())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            allChannelVo.setLoyal3(loyal3+"%");
        }else{
            allChannelVo.setLoyal3("0");
        }
        if(channelDetailAllBo.getLoyal4()!=null && channelDetailAllBo.getCountUser()!=0){
            BigDecimal loyal4 = BigDecimal.valueOf(channelDetailAllBo.getLoyal4())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            allChannelVo.setLoyal4(loyal4+"%");
        }else{
            allChannelVo.setLoyal4("0");
        }
        if(channelDetailAllBo.getLoyal5()!=null && channelDetailAllBo.getCountUser()!=0){
            BigDecimal loyal5 = BigDecimal.valueOf(channelDetailAllBo.getLoyal5())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            allChannelVo.setLoyal5(loyal5+"%");
        }else{
            allChannelVo.setLoyal5("0");
        }
        if(channelDetailAllBo.getLoyal6()!=null && channelDetailAllBo.getCountUser()!=0){
            BigDecimal loyal6 = BigDecimal.valueOf(channelDetailAllBo.getLoyal6())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            allChannelVo.setLoyal6(loyal6+"%");
        }else{
            allChannelVo.setLoyal6("0");
        }
        if(channelDetailAllBo.getLoyal7()!=null && channelDetailAllBo.getCountUser()!=0){
            BigDecimal loyal7 = BigDecimal.valueOf(channelDetailAllBo.getLoyal7())
                .divide(BigDecimal.valueOf(channelDetailAllBo.getCountUser()),2,RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
            allChannelVo.setLoyal7(loyal7+"%");
        }else{
            allChannelVo.setLoyal7("0");
        }
        list.add(allChannelVo);
        return list;

    }

    @Override
    public ModelAndView exportExcel(HttpServletRequest request, ChannelDetailDto channelDetailDto,
        Class<ChannelDetailModal> channelDetailModalClass, String title) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String  username = JwtUtil.getUserNameByToken(request);
        //获取查询的数据
        List<ChannelDetailVo>resList = this.queryList(channelDetailDto);
        List<ChannelDetailModal>exportList = new ArrayList<>();
        for (ChannelDetailVo channelDetailVo : resList) {
            ChannelDetailModal channelDetailModal = new ChannelDetailModal();
            BeanUtils.copyProperties(channelDetailVo,channelDetailModal);
            exportList.add(channelDetailModal);
        }
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, channelDetailModalClass);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }
}
