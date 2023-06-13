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
                channelDetailVo.setGameName(gameName);
                channelDetailVo.setSubGameName(subGameName);
                channelDetailVo.setChannel(channelName);
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
                    .divide(BigDecimal.valueOf(tempDatum.getCountUser()),2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setArpu(firstArpu);
            }else{
                channelDetailVo.setFirstArpu(BigDecimal.ZERO);
            }
            //计算新增arppu
            if (tempDatum.getFirstPayUser() != 0) {
                BigDecimal firstArppu = BigDecimal.valueOf(tempDatum.getFirstMoney())
                    .divide(BigDecimal.valueOf(tempDatum.getFirstPayUser()),2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setFirstArppu(firstArppu);
            }else{
                channelDetailVo.setFirstArppu(BigDecimal.ZERO);
            }
            //计算老用户付费数
            Integer oldPayUser = tempDatum.getCountUser() - tempDatum.getFirstPayUser();
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
                BigDecimal oldPayRate = BigDecimal.valueOf(oldPayUser).divide(oldDau,2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setOldPayRate(oldPayRate+"%");
            }else{
                channelDetailVo.setOldPayRate(String.valueOf(BigDecimal.ZERO));
            }
            //计算老用户的arpu
            if(!oldDau.equals(BigDecimal.ZERO)){
                BigDecimal oldArpu = oldMoney.divide(oldDau,2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setOldArpu(oldArpu);
            }else{
                channelDetailVo.setOldArpu(BigDecimal.ZERO);
            }
            //计算老用户的arppu
            if(oldPayUser!=0){
                BigDecimal oldArppu = oldMoney.divide(BigDecimal.valueOf(oldPayUser),2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setOldArppu(oldArppu);
            }else{
                channelDetailVo.setOldArppu(BigDecimal.ZERO);
            }
            //计算arpu
            if(tempDatum.getDau()!=0){
                BigDecimal arpu = BigDecimal.valueOf(tempDatum.getAlivePayUser())
                    .divide(BigDecimal.valueOf(tempDatum.getDau()),2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
                channelDetailVo.setArpu(arpu);
            }else{
                channelDetailVo.setArpu(BigDecimal.ZERO);
            }
            //计算arppu
            if(tempDatum.getAlivePayUser()!=0){
                BigDecimal arppu = BigDecimal.valueOf(tempDatum.getAliveMoney())
                    .divide(BigDecimal.valueOf(tempDatum.getAlivePayUser()),2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
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
            list.add(channelDetailVo);
        }
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
