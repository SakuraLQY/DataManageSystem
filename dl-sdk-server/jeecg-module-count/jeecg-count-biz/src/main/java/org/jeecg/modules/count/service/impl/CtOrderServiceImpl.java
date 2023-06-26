package org.jeecg.modules.count.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.enums.PayTypeEnum;
import org.jeecg.common.exception.JeecgBootException;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtil;
import org.jeecg.modules.count.bo.GetOrderGroupBo;
import org.jeecg.modules.count.bo.OrderDateGroupBo;
import org.jeecg.modules.count.bo.OrderMoneyGroupRegTimeCreateTimeBo;
import org.jeecg.modules.count.bo.OrderNumGroupRegTimeCreateTimeBo;
import org.jeecg.modules.count.constant.enums.SummaryEnum;
import org.jeecg.modules.count.bo.OrderMoneyGroupBo;
import org.jeecg.modules.count.dto.DetailDto;
import org.jeecg.modules.count.vo.OrderDateGroupRateVo;
import org.jeecg.modules.count.vo.OrderMoneyGroupRateVo;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.dto.OrderDetailDto;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.bo.SummaryOrderBo;
import org.jeecg.modules.count.bo.SummaryOrderDevBo;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtOrder;
import org.jeecg.modules.count.entity.CtRole;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.mapper.CtOrderMapper;
import org.jeecg.modules.count.modal.OrderDetailModal;
import org.jeecg.modules.count.service.ICtOrderService;
import org.jeecg.modules.count.vo.OrderDetailVo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: ct_order
 * @Author: jeecg-boot
 * @Date: 2023-04-19
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class CtOrderServiceImpl extends ServiceImpl<CtOrderMapper, CtOrder> implements
    ICtOrderService {

    @Autowired
    private CtOrderMapper ctOrderMapper;
    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Override
    public OrderDetailModal getOrderDetail(OrderDetailDto orderDetailDto) {
        if (null == orderDetailDto.getUserId() || null == orderDetailDto.getDealId()) {
            throw new JeecgBootException("缺少参数");
        }
        Date startTime = null;
        Date endTime = null;
        try {
            if (null != orderDetailDto.getPayStartTime()) {
                startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(orderDetailDto.getPayStartTime(),
                        "yyyy-MM-dd 00:00:00"));
            }
            if (null != orderDetailDto.getPayEndTime()) {
                endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(orderDetailDto.getPayEndTime(), "yyyy-MM-dd 23:59:59"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LambdaQueryWrapper<CtOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CtOrder::getUserId, orderDetailDto.getUserId());
        wrapper.eq(CtOrder::getDealId, orderDetailDto.getDealId());
        if (null != startTime) {
            wrapper.ge(CtOrder::getCreateTime, startTime);
        }
        if (null != endTime) {
            wrapper.le(CtOrder::getCreateTime, endTime);
        }
        wrapper.orderByDesc(CtOrder::getCreateTime);
        List<CtOrder> list = ctOrderMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        }
        BigDecimal totalMoney = new BigDecimal("0");
        OrderDetailModal orderDetailModal = new OrderDetailModal();
        List<OrderDetailVo> orderDetailVoList = new ArrayList<>();
        for (CtOrder ctOrder : list) {
            OrderDetailVo orderDetailVo = new OrderDetailVo();
            orderDetailVo.setOrderId(ctOrder.getOrderId());
            orderDetailVo.setMoney(ctOrder.getMoney());
            orderDetailVo.setPayType(PayTypeEnum.getTemplatePathByConfig(ctOrder.getPayType()));
            orderDetailVo.setCreateTime(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ctOrder.getCreateTime()));
            totalMoney = totalMoney.add(orderDetailVo.getMoney());
            orderDetailVoList.add(orderDetailVo);
        }
        orderDetailModal.setOrderObj(orderDetailVoList);
        orderDetailModal.setTotalMoney(totalMoney);
        return orderDetailModal;
    }

    @Override
    public CtOrder getCtOrder(Integer dealId, String orderId) {
        LambdaQueryWrapper<CtOrder> ctOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctOrderLambdaQueryWrapper.eq(CtOrder::getDealId, dealId);
        ctOrderLambdaQueryWrapper.eq(CtOrder::getOrderId, orderId);
        CtOrder ctOrder = getOne(ctOrderLambdaQueryWrapper);
        return ctOrder;
    }

    @Override
    public void parsePayOrder(ParsePayDto parsePayDto, CtRole ctRole, CtDevice ctDevice,
        CtUser ctUser) {
        CtOrder ctOrder = new CtOrder();
        Date logTime = DateUtils.getDate(parsePayDto.getTime());
        ctOrder.setGameId(parsePayDto.getGameId()).setSubGameId(parsePayDto.getSubGameId())
            .setPkgId(parsePayDto.getPkgId()).setChannelId(parsePayDto.getChannelId())
            .setChannelId(parsePayDto.getChannelSubAccountId()).setDealId(parsePayDto.getDealId())
            .setUniqueId(parsePayDto.getUniqueId()).setUserId(parsePayDto.getUserId())
            .setServerInit(ctUser.getServerInit()).setServerId(parsePayDto.getServerId())
            .setPayType(parsePayDto.getPayType()).setMoney(parsePayDto.getMoney())
            .setOrderId(parsePayDto.getOrderId()).setClientIp(parsePayDto.getClientIp())
            .setCouponPrice(parsePayDto.getCouponPrice()).setOrderType(parsePayDto.getOrderType())
            .setRoleId(parsePayDto.getRoleId()).setRoleLevel(parsePayDto.getRoleLevel())
            .setOnlineTime(ctRole.getOnlineTime()).setPayVendorId(parsePayDto.getPayVendorId())
            .setRegion(parsePayDto.getRegion()).setLogTime(logTime).setCreateTime(logTime)
            .setUpdateTime(logTime).setUserCreateTime(ctUser.getCreateTime())
            .setDeviceCreateTime(ctDevice.getCreateTime());
        save(ctOrder);
    }

    @Override
    public List<SummaryOrderBo> getSummaryOrder(SummaryDto summaryDto) {
        if (StringUtils.isBlank(summaryDto.getPayStartTime()) || StringUtils.isBlank(
            summaryDto.getPayEndTime())) {
            // 如果没有选择支付时间 则返回空
            return null;
        }
        QueryWrapper<CtOrder> wrapper = getCtOrderSummaryQueryWrapper(
            summaryDto);
        wrapper.ge(oConvertUtils.isNotEmpty(summaryDto.getRegStartTime()), "a.user_create_time",
            summaryDto.getRegStartTime());
        wrapper.le(oConvertUtils.isNotEmpty(summaryDto.getRegEndTime()), "a.user_create_time",
            summaryDto.getRegEndTime());
        SummaryEnum summaryEnum = SummaryEnum.valueOfCode(summaryDto.getGroupType());
        wrapper.groupBy(summaryEnum.getGroupOrder());
        List<SummaryOrderBo> summaryOrderBoList = baseMapper.getSummaryOrder(
            summaryEnum.getGroupOrder(), wrapper);
        return summaryOrderBoList;
    }

    @Override
    public List<SummaryOrderDevBo> getSummaryOrderDev(SummaryDto summaryDto) {
        if (StringUtils.isBlank(summaryDto.getPayStartTime()) || StringUtils.isBlank(
            summaryDto.getPayEndTime())) {
            // 如果没有选择支付时间 则返回空
            return null;
        }
        QueryWrapper<CtOrder> wrapper = getCtOrderSummaryQueryWrapper(
            summaryDto);
        wrapper.ge(oConvertUtils.isNotEmpty(summaryDto.getRegStartTime()), "a.device_create_time",
            summaryDto.getRegStartTime());
        wrapper.le(oConvertUtils.isNotEmpty(summaryDto.getRegEndTime()), "a.device_create_time",
            summaryDto.getRegEndTime());
        SummaryEnum summaryEnum = SummaryEnum.valueOfCode(summaryDto.getGroupType());
        wrapper.groupBy(summaryEnum.getGroupOrder());
        List<SummaryOrderDevBo> summaryOrderDevBoList = baseMapper.getSummaryOrderDev(
            summaryEnum.getGroupOrder(), wrapper);
        return summaryOrderDevBoList;
    }

    @Override
    public List<OrderMoneyGroupRateVo> getFirstMoneyGroup(GetOrderGroupBo getOrderGroupBo) {
        QueryWrapper<CtOrder> wrapper = new QueryWrapper<>();
        groupSelectWrapper(getOrderGroupBo, wrapper);
        wrapper.apply("DATE(a.create_time) = DATE(a.user_create_time)");
        return getMoneyGroup(wrapper);
    }

    @Override
    public List<OrderMoneyGroupRateVo> getAliveMoneyGroup(GetOrderGroupBo getOrderGroupBo) {
        QueryWrapper<CtOrder> wrapper = new QueryWrapper<>();
        groupSelectWrapper(getOrderGroupBo, wrapper);
        return getMoneyGroup(wrapper);
    }

    @Override
    public List<OrderDateGroupRateVo> getRegDateGroup(GetOrderGroupBo getOrderGroupBo) {
        QueryWrapper<CtOrder> wrapper = new QueryWrapper<>();
        groupSelectWrapper(getOrderGroupBo, wrapper);
        return getDateGroup(wrapper);
    }

    @Override
    public Map<String, Integer> getNumGroupRegTimeCreateTime(GetOrderGroupBo getOrderGroupBo) {
        Map<String, Integer> result = new HashMap();
        QueryWrapper queryWrapper = new QueryWrapper();
        groupRegTimeCreateTimeSelectWrapper(getOrderGroupBo, queryWrapper);
        List<OrderNumGroupRegTimeCreateTimeBo> orderNumGroupRegTimeCreateTimeBos = baseMapper.selectNumGroupRegTimeCreateTime(
            queryWrapper);
        for (OrderNumGroupRegTimeCreateTimeBo orderNumGroupRegTimeCreateTimeBo : orderNumGroupRegTimeCreateTimeBos) {
            Integer dateDiff = DateUtils.dateToDiff('d',
                orderNumGroupRegTimeCreateTimeBo.getCreateTime(),
                orderNumGroupRegTimeCreateTimeBo.getUserCreateTime()
            );
            if (dateDiff > 0) {
                result.put(DateUtils.date2Str(orderNumGroupRegTimeCreateTimeBo.getUserCreateTime(),
                        DateUtils.date_sdf.get()) + "-" + dateDiff,
                    orderNumGroupRegTimeCreateTimeBo.getNum());
            }
        }
        return result;
    }

    @Override
    public Map<String, BigDecimal> getMoneyGroupRegTimeCreateTime(GetOrderGroupBo getOrderGroupBo) {
        Map<String, BigDecimal> result = new HashMap();
        QueryWrapper queryWrapper = new QueryWrapper();
        groupRegTimeCreateTimeSelectWrapper(getOrderGroupBo, queryWrapper);
        List<OrderMoneyGroupRegTimeCreateTimeBo> orderNumGroupRegTimeCreateTimeBos = baseMapper.selectMoneyGroupRegTimeCreateTime(
            queryWrapper);
        for (OrderMoneyGroupRegTimeCreateTimeBo orderMoneyGroupRegTimeCreateTimeBo : orderNumGroupRegTimeCreateTimeBos) {
            Integer dateDiff = DateUtils.dateToDiff('d',
                orderMoneyGroupRegTimeCreateTimeBo.getCreateTime(),
                orderMoneyGroupRegTimeCreateTimeBo.getUserCreateTime());
            if (dateDiff > 0) {
                result.put(
                    DateUtils.date2Str(orderMoneyGroupRegTimeCreateTimeBo.getUserCreateTime(),
                        DateUtils.date_sdf.get()) + "-" + dateDiff,
                    orderMoneyGroupRegTimeCreateTimeBo.getMoney());
            }
        }
        return result;
    }

    @Override
    public ModelAndView exportFirstMoneyRateXls(DetailDto object,
        Class<OrderMoneyGroupRateVo> clazz, String title) {
        GetOrderGroupBo getOrderGroupBo = new GetOrderGroupBo();
        BeanUtils.copyProperties(object, getOrderGroupBo);
        List<OrderMoneyGroupRateVo> exportList = getFirstMoneyGroup(getOrderGroupBo);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams = new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(),
            title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    @Override
    public ModelAndView exportAliveMoneyRateXls(DetailDto object,
        Class<OrderMoneyGroupRateVo> clazz, String title) {
        GetOrderGroupBo getOrderGroupBo = new GetOrderGroupBo();
        BeanUtils.copyProperties(object, getOrderGroupBo);
        List<OrderMoneyGroupRateVo> exportList = getAliveMoneyGroup(getOrderGroupBo);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams = new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(),
            title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }


    /**
     * @param wrapper
     * @return java.util.List<org.jeecg.modules.count.vo.OrderMoneyGroupRateVo>
     * @author chenyw
     * @description 获取金额分布
     * @date 19:16 2023/5/12/012
     **/
    private List<OrderMoneyGroupRateVo> getMoneyGroup(QueryWrapper<CtOrder> wrapper) {
        wrapper.groupBy("a.money");
        List<OrderMoneyGroupRateVo> result = new ArrayList<>();
        List<OrderMoneyGroupBo> orderMoneyGroupBoList = baseMapper.selectMoneyGroup(wrapper);
        if (CollectionUtil.isNotEmpty(orderMoneyGroupBoList)) {
            int total = 0;
            for (OrderMoneyGroupBo orderMoneyGroupBo : orderMoneyGroupBoList) {
                total += orderMoneyGroupBo.getCount();
            }
            for (OrderMoneyGroupBo orderMoneyGroupBo : orderMoneyGroupBoList) {
                OrderMoneyGroupRateVo orderMoneyGroupRateVo = new OrderMoneyGroupRateVo();
                orderMoneyGroupRateVo.setRate(BigDecimal.valueOf(orderMoneyGroupBo.getCount())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP));
                orderMoneyGroupRateVo.setMoney(orderMoneyGroupBo.getMoney());
                orderMoneyGroupRateVo.setCount(orderMoneyGroupBo.getCount());
                result.add(orderMoneyGroupRateVo);
            }
        }
        return result;
    }

    private List<OrderDateGroupRateVo> getDateGroup(QueryWrapper<CtOrder> wrapper) {
        wrapper.groupBy("DATE(a.user_create_time)");
        List<OrderDateGroupRateVo> result = new ArrayList<>();
        List<OrderDateGroupBo> orderDateGroupBoList = baseMapper.selectDateGroup(wrapper);
        if (CollectionUtil.isNotEmpty(orderDateGroupBoList)) {
            int total = 0;
            for (OrderDateGroupBo orderDateGroupBo : orderDateGroupBoList) {
                total += orderDateGroupBo.getCount();
            }
            for (OrderDateGroupBo orderDateGroupBo : orderDateGroupBoList) {
                OrderDateGroupRateVo orderDateGroupRateVo = new OrderDateGroupRateVo();
                orderDateGroupRateVo.setRate(BigDecimal.valueOf(orderDateGroupBo.getCount())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP));
                orderDateGroupRateVo.setDay(orderDateGroupBo.getDay());
                orderDateGroupRateVo.setCount(orderDateGroupBo.getCount());
                result.add(orderDateGroupRateVo);
            }
        }
        return result;

    }

    /**
     * @param getOrderGroupBo
     * @param wrapper
     * @author chenyw
     * @description 分布图统一queryWrapper
     * @date 19:17 2023/5/12/012
     **/
    private void groupSelectWrapper(GetOrderGroupBo getOrderGroupBo,
        QueryWrapper<CtOrder> wrapper) {
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getGameId()), "a.game_id",
            getOrderGroupBo.getGameId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getSubGameId()), "a.sub_game_id",
            getOrderGroupBo.getSubGameId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getPkgId()), "a.pkg_id",
            getOrderGroupBo.getPkgId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getDealId()), "a.deal_id",
            getOrderGroupBo.getDealId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getChannelTypeId()),
            "a.channel_type_id", getOrderGroupBo.getChannelTypeId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getChannelId()), "a.channel_id",
            getOrderGroupBo.getChannelId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getChannelSubAccountId()),
            "a.channel_sub_account_id", getOrderGroupBo.getChannelSubAccountId());
        wrapper.ge(oConvertUtils.isNotEmpty(getOrderGroupBo.getRegStartTime()), "a.create_time",
            getOrderGroupBo.getRegStartTime());
        wrapper.le(oConvertUtils.isNotEmpty(getOrderGroupBo.getRegEndTime()), "a.create_time",
            getOrderGroupBo.getRegEndTime());
    }

    /**
     * @param getOrderGroupBo
     * @param wrapper
     * @author chenyw
     * @description 统一构造器 查询按创建时间和注册时间归类的订单
     * @date 15:02 2023/5/17/017
     **/
    private void groupRegTimeCreateTimeSelectWrapper(GetOrderGroupBo getOrderGroupBo,
        QueryWrapper<CtOrder> wrapper) {
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getGameId()), "a.game_id",
            getOrderGroupBo.getGameId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getSubGameId()), "a.sub_game_id",
            getOrderGroupBo.getSubGameId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getPkgId()), "a.pkg_id",
            getOrderGroupBo.getPkgId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getDealId()), "a.deal_id",
            getOrderGroupBo.getDealId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getChannelTypeId()),
            "a.channel_type_id", getOrderGroupBo.getChannelTypeId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getChannelId()), "a.channel_id",
            getOrderGroupBo.getChannelId());
        wrapper.in(oConvertUtils.isNotEmpty(getOrderGroupBo.getChannelSubAccountId()),
            "a.channel_sub_account_id", getOrderGroupBo.getChannelSubAccountId());
        Date start = DateUtils.str2Date(getOrderGroupBo.getRegStartTime(),
            DateUtils.date_sdf.get());
        Date end = DateUtils.str2Date(getOrderGroupBo.getRegEndTime(), DateUtils.date_sdf.get());
        // 相差的天数
        int diffDays = DateUtils.dateToDiff('d', end, start);
        for (int i = 0; i <= diffDays; i++) {
            String date = DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(),
                "yyyy-MM-dd", i);
            String date1 =
                DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(), "yyyy-MM-dd", i)
                    + " 00:00:00";
            String date2 =
                DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(), "yyyy-MM-dd", i + 6)
                    + " 23:59:59";
            String date3 =
                DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(), "yyyy-MM-dd", i + 14)
                    + " 00:00:00";
            String date4 =
                DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(), "yyyy-MM-dd", i + 14)
                    + " 23:59:59";
            String date5 =
                DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(), "yyyy-MM-dd", i + 29)
                    + " 00:00:00";
            String date6 =
                DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(), "yyyy-MM-dd", i + 29)
                    + " 23:59:59";
            String date7 =
                DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(), "yyyy-MM-dd", i + 44)
                    + " 00:00:00";
            String date8 =
                DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(), "yyyy-MM-dd", i + 44)
                    + " 23:59:59";
            String date9 =
                DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(), "yyyy-MM-dd", i + 59)
                    + " 00:00:00";
            String date10 =
                DateUtils.formatAddDate(getOrderGroupBo.getRegStartTime(), "yyyy-MM-dd", i + 59)
                    + " 23:59:59";
            wrapper.or(w1 -> w1.eq("DATE(a.user_create_time)", date).and(
                w2 -> w2.between("a.create_time", date1, date2)
                    .or(w3 -> w3.between("a.create_time", date3, date4))
                    .or(w3 -> w3.between("a.create_time", date5, date6))
                    .or(w3 -> w3.between("a.create_time", date7, date8))
                    .or(w3 -> w3.between("a.create_time", date9, date10))
            ));
        }
        wrapper.groupBy("a.user_create_time, a.create_time");
    }

    /**
     * @param summaryDto
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<org.jeecg.modules.count.entity.CtOrder>
     * @author chenyw
     * @description 基础构造器
     * @date 21:39 2023/5/5/005
     **/
    private QueryWrapper<CtOrder> getCtOrderSummaryQueryWrapper(SummaryDto summaryDto) {
        QueryWrapper<CtOrder> wrapper = new QueryWrapper();
        wrapper.in(oConvertUtils.isNotEmpty(summaryDto.getGameId()), "a.game_id",
            summaryDto.getGameId());
        wrapper.in(oConvertUtils.isNotEmpty(summaryDto.getSubGameId()), "a.sub_game_id",
            summaryDto.getSubGameId());
        wrapper.eq(oConvertUtils.isNotEmpty(summaryDto.getCostPlatform()), "a.platform",
            summaryDto.getCostPlatform());
        wrapper.in(oConvertUtils.isNotEmpty(summaryDto.getPkgId()), "a.pkg_id",
            summaryDto.getPkgId());
        wrapper.in(oConvertUtils.isNotEmpty(summaryDto.getAccountId()), "b.account_id",
            summaryDto.getAccountId());
        wrapper.in(oConvertUtils.isNotEmpty(summaryDto.getDealId()), "a.deal_id",
            summaryDto.getDealId());
        wrapper.in(oConvertUtils.isNotEmpty(summaryDto.getChannelTypeId()), "a.channel_type_id",
            summaryDto.getChannelTypeId());
        wrapper.in(oConvertUtils.isNotEmpty(summaryDto.getChannelId()), "a.channel_id",
            summaryDto.getChannelId());
        wrapper.in(oConvertUtils.isNotEmpty(summaryDto.getChannelSubAccountId()),
            "a.channel_sub_account_id",
            summaryDto.getChannelSubAccountId());
        wrapper.ge(oConvertUtils.isNotEmpty(summaryDto.getPayStartTime()), "a.create_time",
            summaryDto.getPayStartTime());
        wrapper.le(oConvertUtils.isNotEmpty(summaryDto.getPayEndTime()), "a.create_time",
            summaryDto.getPayEndTime());
        // 创建用户
        wrapper.eq(oConvertUtils.isNotEmpty(summaryDto.getCreateUser()), "b.create_by",
            summaryDto.getCreateUser());
        return wrapper;
    }

}
