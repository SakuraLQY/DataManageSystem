package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.servlet.http.HttpServletRequest;
import net.sf.saxon.functions.Round;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.bo.LaunchTempBo;
import org.jeecg.modules.count.bo.LaunchTotalBo;
import org.jeecg.modules.count.modal.SummaryLaunchModal;
import org.jeecg.modules.count.vo.SummaryLaunchVo;
import org.jeecg.modules.count.mapper.SummaryLaunchMapper;
import org.jeecg.modules.count.service.ISummaryLaunchService;
import org.jeecg.modules.count.dto.SummaryLaunchDto;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 数据投放
 * @Author: jeecg-boot
 * @Date: 2023-05-10
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class SummaryLaunchServiceImpl extends
    ServiceImpl<SummaryLaunchMapper, SummaryLaunchDto> implements ISummaryLaunchService {

    @Resource
    private SummaryLaunchMapper summaryLaunchMapper;

    private static final String ALL = "cost_day";
    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    /**
     * @param
     * @author chenglin
     * @description
     * @date 17:14 2023/05/11
     **/
    @Override
    public List<SummaryLaunchVo> queryList(SummaryLaunchDto summaryLaunch) {
        //按全部
        QueryWrapper<SummaryLaunchDto> q = new QueryWrapper();
        QueryWrapper<SummaryLaunchDto> wrapper = new QueryWrapper<>();
        List<SummaryLaunchVo> list = new ArrayList<>();
        //拼接where子句

        if (oConvertUtils.isNotEmpty(summaryLaunch.getGameId())) {
            q.eq("a.game_id", summaryLaunch.getGameId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getSubGameId())) {
            q.eq("a.sub_game_id", summaryLaunch.getSubGameId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getPkgId())) {
            q.eq("a.pkg_id", summaryLaunch.getPkgId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getChannelId())) {
            q.eq("a.channel_id", summaryLaunch.getChannelId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getChannelSubAccountId())) {
            q.eq("a.channel_sub_account_id", summaryLaunch.getChannelSubAccountId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getChannelTypeId())) {
            q.eq("a.channel_type_id", summaryLaunch.getChannelTypeId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getDealId())) {
            q.in("a.deal_id", summaryLaunch.getDealId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getAccountId()) && summaryLaunch.getAccountId().size()>0) {
            q.in("a.account_id", summaryLaunch.getAccountId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getStartTime()) && oConvertUtils.isNotEmpty(
            summaryLaunch.getEndTime())) {
            q.ge("a.cost_day", DateUtil.beginOfMinute(summaryLaunch.getStartTime()));
            q.le("a.cost_day", DateUtil.endOfDay(summaryLaunch.getEndTime()));
        }
        //第二个的查询条件
        if (oConvertUtils.isNotEmpty(summaryLaunch.getGameId())) {
            wrapper.eq("a.game_id", summaryLaunch.getGameId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getSubGameId())) {
            wrapper.eq("a.sub_game_id", summaryLaunch.getSubGameId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getPkgId())) {
            wrapper.eq("a.pkg_id", summaryLaunch.getPkgId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getChannelId())) {
            wrapper.eq("a.channel_id", summaryLaunch.getChannelId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getChannelSubAccountId())) {
            wrapper.eq("a.channel_sub_account_id", summaryLaunch.getChannelSubAccountId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getChannelTypeId())) {
            wrapper.eq("a.channel_type_id", summaryLaunch.getChannelTypeId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getDealId())) {
            wrapper.in("a.deal_id", summaryLaunch.getDealId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getAccountId())&& summaryLaunch.getAccountId().size()>0) {
            wrapper.in("b.account_id", summaryLaunch.getAccountId());
        }
        if (oConvertUtils.isNotEmpty(summaryLaunch.getStartTime()) && oConvertUtils.isNotEmpty(
            summaryLaunch.getEndTime())) {
            wrapper.ge("a.time_daily", DateUtil.beginOfMinute(summaryLaunch.getStartTime()));
            wrapper.le("a.time_daily", DateUtil.endOfDay(summaryLaunch.getEndTime()));
        }
        List<LaunchTempBo> tempDatas;
        if (summaryLaunch.getType()==null||summaryLaunch.getType().equals(ALL)) {
            //添加where条件子句
            tempDatas = summaryLaunchMapper.selectByAll(q);
        } else {
            //根据类型来查询对应的ID与成本
            String type = "a." + summaryLaunch.getType();
            tempDatas = summaryLaunchMapper.selectByOthers(q, type);
        }
        List<LaunchTotalBo> othersDatas;
        //根据类型拿到不同的数据
        if (summaryLaunch.getType()==null||summaryLaunch.getType().equals(ALL)) {
            String source = "a.time_daily";
            othersDatas = summaryLaunchMapper.selectByTypeOthers(wrapper, source);
        } else {
            String source = "a." + summaryLaunch.getType();
            othersDatas = summaryLaunchMapper.selectByTypeOthers(wrapper, source);
        }
        if (summaryLaunch.getType()!=null){
            for (LaunchTotalBo othersData : othersDatas) {
                SummaryLaunchVo data = new SummaryLaunchVo();
                data.setCost(BigDecimal.ZERO);
                //拿到成本
                for (LaunchTempBo tempData : tempDatas) {
                    if (tempData.getID().equals(othersData.getID())) {
                        data.setCost(tempData.getCost());
                        continue;
                    }
                }
                String name = "";
                if (ALL.equals(summaryLaunch.getType())) {
                    data.setName("全部游戏");
                }
                if (summaryLaunch.getType().equals("deal_id")) {
                    name = summaryLaunchMapper.getNameByDeal((int) othersData.getID());
                    if (oConvertUtils.isNotEmpty(name)) {
                        data.setName(name);
                    } else {
                        data.setName("未匹配");
                    }
                }
                if (summaryLaunch.getType().equals("channel_id")) {
                    name = summaryLaunchMapper.getNameByChannel((int) othersData.getID());
                    data.setName(name);
                }
                if (summaryLaunch.getType().equals("channel_sub_account_id")) {
                    name = summaryLaunchMapper.getNameBySubChannel((int) othersData.getID());
                    data.setName(name);
                }
                if (summaryLaunch.getType().equals("game_id")) {
                    name = summaryLaunchMapper.getNameByGame((int) othersData.getID());
                    data.setName(name);
                }
                if (summaryLaunch.getType().equals("pkg_id")) {
                    name = summaryLaunchMapper.getNameByPkg((int) othersData.getID());
                    data.setName(name);
                }
                if (summaryLaunch.getType().equals("sub_game_id")) {
                    name = summaryLaunchMapper.getNameBySubGame((int) othersData.getID());
                    data.setName(name);
                }
                if (summaryLaunch.getType().equals("account_id")) {
                    name = summaryLaunchMapper.getNameByAccountId((int) othersData.getID());
                    if (oConvertUtils.isNotEmpty(name)) {
                        data.setName(name);
                    } else {
                        data.setName("未匹配");
                    }
                }
                //计算对应的百分数
                data.setLanuchId(String.valueOf(othersData.getID()));
                if (summaryLaunch.getType().equals(ALL)) {
                    String id = String.valueOf(othersData.getID());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = sdf.parse(id);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String format = sdf.format(date);
                    data.setLanuchId(format);
                }
                data.setCountDau(othersData.getCountDau());
                data.setCountActive(othersData.getCountActive());
                data.setCountUser(othersData.getCountUser());
                data.setCountValidUser(othersData.getCountValidUser());
                data.setFirstMoney(othersData.getFirstMoney());
                data.setFirstPayuser(othersData.getFirstPayUser());
                //计算激活注册率
                if (othersData.getCountActiveDev() != 0) {
                    BigDecimal activeRate = BigDecimal.valueOf(othersData.getCountUser())
                        .divide(BigDecimal.valueOf(othersData.getCountActiveDev()),2,RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                    data.setRegistrationRate(activeRate + "%");
                } else {
                    data.setRegistrationRate("0");
                }
                //计算注册单价
                if (othersData.getCountUser() != 0) {
                    BigDecimal singlePrice = (data.getCost())
                        .divide(BigDecimal.valueOf(othersData.getCountUser()),2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                    data.setRegistryPrice(singlePrice);
                } else {
                    data.setRegistryPrice(BigDecimal.ZERO);
                }
                //计算首日付费率
                if (othersData.getCountUser() != 0) {
                    BigDecimal firstRate = BigDecimal.valueOf(othersData.getFirstPayUser())
                        .divide(BigDecimal.valueOf(othersData.getCountUser()),2,RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                    data.setFirstPayrate(firstRate + "%");
                } else {
                    data.setFirstPayrate("0");
                }
                //计算首日付费单价
                if (othersData.getFirstPayUser() != 0) {
                    BigDecimal fistPrice = othersData.getFirstMoney().
                        divide(BigDecimal.valueOf(othersData.getFirstPayUser()),2,RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                    data.setFirstPayprice(fistPrice);
                } else {
                    data.setFirstPayprice(BigDecimal.ZERO);
                }
                //计算首日arpu
                if (othersData.getCountUser() != 0) {
                    BigDecimal arpu = BigDecimal.valueOf(othersData.getTotalMoney())
                        .divide(BigDecimal.valueOf(othersData.getCountDau()),2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                    data.setFirstArpu(arpu);
                } else {
                    data.setFirstArpu(BigDecimal.ZERO);
                }
                //计算首日arrpu
                if (othersData.getFirstPayUser() != 0) {
                    BigDecimal arrpu = othersData.getFirstMoney()
                        .divide(BigDecimal.valueOf(othersData.getFirstPayUser()),2,RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                    data.setFirstArppu(arrpu);
                } else {
                    data.setFirstArppu(BigDecimal.ZERO);
                }
                //计算首日roi
                if (!data.getCost().equals(BigDecimal.ZERO)) {
                    BigDecimal roi = BigDecimal.valueOf((othersData.getTotalMoney()))
                        .subtract(data.getCost())
                        .divide(data.getCost(),2,RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                    data.setFirstRoi(roi + "%");
                } else {
                    data.setFirstRoi("0");
                }
                list.add(data);
            }
        }
        return list;
    }

    @Override
    public ModelAndView exportExcel(HttpServletRequest request, SummaryLaunchDto summaryLaunchDto,
        Class<SummaryLaunchModal> summaryLaunchModalClass, String title) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String  username = JwtUtil.getUserNameByToken(request);
        //1.获取到查询的数据
        List<SummaryLaunchVo>resultList = this.queryList(summaryLaunchDto);
        List<SummaryLaunchModal>exportList = new ArrayList<>();
        for (SummaryLaunchVo summaryLaunchVo : resultList) {
            SummaryLaunchModal summaryLaunchModal = new SummaryLaunchModal();
            BeanUtils.copyProperties(summaryLaunchVo,summaryLaunchModal);
            exportList.add(summaryLaunchModal);
        }
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, summaryLaunchModalClass);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }
}
