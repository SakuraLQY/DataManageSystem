package org.jeecg.modules.count.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.UserDataPermissionRule;
import org.jeecg.modules.count.dto.DayPayReportDto;
import org.jeecg.modules.count.dto.MonthPayReportDto;
import org.jeecg.modules.count.dto.OrderPurchaseVolumeDto;
import org.jeecg.modules.count.dto.PayOrderDto;
import org.jeecg.modules.count.dto.SupplementaryOrderDto;
import org.jeecg.modules.count.dto.UserOrderDto;
import org.jeecg.modules.count.mapper.PayReportMapper;
import org.jeecg.modules.count.service.IPayReportService;
import org.jeecg.modules.count.vo.DayPayReportVo;
import org.jeecg.modules.count.vo.MonthPayReportVo;
import org.jeecg.modules.count.vo.OrderPurchaseVolumeVo;
import org.jeecg.modules.count.vo.PayOrderTotalVo;
import org.jeecg.modules.count.vo.PayOrderVo;
import org.jeecg.modules.count.vo.UserOrderVo;
import org.jeecg.modules.pay.api.IPayBaseAPI;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.service.impl
 * @className: PayReportServiceImpl
 * @author: fkh
 * @description: TODO
 * @date: 2023/4/26 17:06
 */
@Slf4j
@Service
@DS("open_countly")
public class PayReportServiceImpl  implements IPayReportService {


    @Autowired
    private PayReportMapper payReportMapper;

    @Autowired
    private IPayBaseAPI iPayBaseAPI;

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Override
    public IPage pageDay(Page page, DayPayReportDto payReportDto){

        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(payReportDto.getSubGameId())){
                queryWrapper.in("co.game_id",payReportDto.getGameId());
        }
        if (ObjectUtils.isNotEmpty(payReportDto.getSubGameId())){
            queryWrapper.in("co.sub_game_id",payReportDto.getSubGameId());
        }
        if (ObjectUtils.isNotEmpty(payReportDto.getChannelTypeId())){
            queryWrapper.in("co.channel_type_id",payReportDto.getChannelId());
        }
        if (ObjectUtils.isNotEmpty(payReportDto.getChannelId())){
            queryWrapper.in("co.channel_id",payReportDto.getChannelId());
        }
        if (ObjectUtils.isNotEmpty(payReportDto.getChannelSubAccountId())){
            queryWrapper.in("co.channel_sub_account_id",payReportDto.getChannelSubAccountId());
        }
        if (ObjectUtils.isNotEmpty(payReportDto.getPkgId())){
            queryWrapper.in("co.pkg_id",payReportDto.getPkgId());
        }
        if (ObjectUtils.isNotEmpty(payReportDto.getOrderStartTime())){
            queryWrapper.ge("co.create_time",payReportDto.getOrderStartTime());
        }
        if (ObjectUtils.isNotEmpty(payReportDto.getOrderEndTime())){
            queryWrapper.le("co.create_time",payReportDto.getOrderEndTime());
        }
        if (ObjectUtils.isNotEmpty(payReportDto.getUserStartTime())){
            queryWrapper.ge("co.user_create_time",payReportDto.getUserStartTime());
        }
        if (ObjectUtils.isNotEmpty(payReportDto.getUserEndTime())){
            queryWrapper.le("co.user_create_time",payReportDto.getUserEndTime());
        }
        queryWrapper.groupBy("createTime","co.game_id","co.pay_type");
        queryWrapper.orderByDesc("createTime");
        List<DayPayReportVo> dayPayReportVos = payReportMapper.queryNewList(page, queryWrapper);
        if (ObjectUtils.isNotEmpty(dayPayReportVos)){
            for (DayPayReportVo dayPayReportVo : dayPayReportVos) {
                if (ObjectUtils.isNotEmpty(dayPayReportVo.getMoney()) && ObjectUtils.isNotEmpty(dayPayReportVo.getCountUser())){
                    //计算arpu值
                    Double arpuNum=dayPayReportVo.getMoney().divide(new BigDecimal(dayPayReportVo.getCountUser()),2,
                        RoundingMode.HALF_UP).doubleValue();
                    dayPayReportVo.setArpuNum(arpuNum);
                }else {
                    dayPayReportVo.setArpuNum(0d);
                }
            }
        }
        return  page.setRecords(dayPayReportVos);
    }

    /**
     * 日充值导出excel
     *
     * @param request
     */
    @Override
    public ModelAndView exportXls(HttpServletRequest request, DayPayReportDto dayPayReportDto, String title) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer pageNo=1;
        Integer pageSize=1000;
        Page<DayPayReportVo> objectPage = new Page<DayPayReportVo>(pageNo, pageSize);
        IPage iPage = this.pageDay(objectPage, dayPayReportDto);

        ArrayList<Object> arrayList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(iPage.getRecords())){

            arrayList.addAll(iPage.getRecords());

            while (true){
                pageNo++;
                objectPage.setCurrent(pageNo);

                IPage newResult = this.pageDay(objectPage, dayPayReportDto);
                if (ObjectUtils.isEmpty(newResult.getRecords())){
                    break;
                }
                arrayList.addAll(newResult.getRecords());
            }
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, DayPayReportVo.class);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, arrayList);
        return mv;
    }

    @Override
    public  IPage pageMonth(Page page, MonthPayReportDto monthPayReportDto){
        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(monthPayReportDto.getGameId())){
            queryWrapper.in("co.game_id",monthPayReportDto.getGameId());
        }
        if (ObjectUtils.isNotEmpty(monthPayReportDto.getSubGameId())){
            queryWrapper.in("co.sub_game_id",monthPayReportDto.getSubGameId());
        }
        if (ObjectUtils.isNotEmpty(monthPayReportDto.getChannelTypeId())){
            queryWrapper.in("co.channel_type_id",monthPayReportDto.getChannelId());
        }
        if (ObjectUtils.isNotEmpty(monthPayReportDto.getChannelId())){
            queryWrapper.in("co.channel_id",monthPayReportDto.getChannelId());
        }
        if (ObjectUtils.isNotEmpty(monthPayReportDto.getChannelSubAccountId())){
            queryWrapper.in("co.channel_sub_account_id",monthPayReportDto.getChannelSubAccountId());
        }
        if (ObjectUtils.isNotEmpty(monthPayReportDto.getPkgId())){
            queryWrapper.in("co.pkg_id",monthPayReportDto.getPkgId());
        }
        if (ObjectUtils.isNotEmpty(monthPayReportDto.getOrderStartTime())){
            queryWrapper.ge("co.create_time",monthPayReportDto.getOrderStartTime());
        }
        if (ObjectUtils.isNotEmpty(monthPayReportDto.getOrderEndTime())){
            queryWrapper.le("co.create_time",monthPayReportDto.getOrderEndTime());
        }
        if (ObjectUtils.isNotEmpty(monthPayReportDto.getUserStartTime())){
            queryWrapper.ge("co.user_create_time",monthPayReportDto.getUserStartTime());
        }
        if (ObjectUtils.isNotEmpty(monthPayReportDto.getUserEndTime())){
            queryWrapper.le("co.user_create_time",monthPayReportDto.getUserEndTime());
        }
        queryWrapper.groupBy("createTime","co.game_id","co.pay_type");
        queryWrapper.orderByDesc("createTime");
        List<MonthPayReportVo> monthPayReportVos = payReportMapper.queryMonthList(page, queryWrapper);

        if (ObjectUtils.isNotEmpty(monthPayReportVos)){
            for (MonthPayReportVo monthPayReportVo : monthPayReportVos) {
                if (ObjectUtils.isNotEmpty(monthPayReportVo.getMoney()) && ObjectUtils.isNotEmpty(monthPayReportVo.getCountUser())){
                    //计算arpu值
                    Double arpuNum=monthPayReportVo.getMoney().divide(new BigDecimal(monthPayReportVo.getCountUser()),2,
                        RoundingMode.HALF_UP).doubleValue();
                    monthPayReportVo.setArpuNum(arpuNum);
                }else {
                    monthPayReportVo.setArpuNum(0d);
                }

            }
        }
        return  page.setRecords(monthPayReportVos);
    }

    /**
     * 月充值导出excel
     *
     * @param request
     */
    @Override
    public ModelAndView monthExportXls(HttpServletRequest request, MonthPayReportDto monthPayReportDto, String title) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer pageNo=1;
        Integer pageSize=1000;
        Page<MonthPayReportVo> objectPage = new Page<MonthPayReportVo>(pageNo, pageSize);
        IPage iPage = this.pageMonth(objectPage, monthPayReportDto);

        ArrayList<Object> arrayList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(iPage.getRecords())){

            arrayList.addAll(iPage.getRecords());

            while (true){
                pageNo++;
                objectPage.setCurrent(pageNo);

                IPage newResult = this.pageMonth(objectPage, monthPayReportDto);
                if (ObjectUtils.isEmpty(newResult.getRecords())){
                    break;
                }
                arrayList.addAll(newResult.getRecords());
            }
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, MonthPayReportVo.class);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, arrayList);
        return mv;
    }

    @Override
    public  IPage queryPayOrderList(Page page, PayOrderDto payOrderDto){

        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(payOrderDto.getOrderId())){
            queryWrapper.eq("oo.order_id",payOrderDto.getOrderId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getGameOrderId())){
            queryWrapper.eq("oo.game_order_id",payOrderDto.getGameOrderId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getBankOrderId())){
            queryWrapper.eq("oo.bank_order_id",payOrderDto.getBankOrderId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getUserId())){
            queryWrapper.eq("oo.user_id",payOrderDto.getUserId());
        }
        if (ObjectUtil.equal(1,payOrderDto.getType())){
            queryWrapper.eq("oo.game_status","1000");
            queryWrapper.eq("oo.bank_status","1000");
        }else if (ObjectUtil.equal(0,payOrderDto.getType())){

            if (ObjectUtil.equal(1,payOrderDto.getSendStatus())){
                queryWrapper.ne("oo.game_status","1000");
            }else if (ObjectUtil.equal(2,payOrderDto.getSendStatus())){
                queryWrapper.ne("oo.bank_status","1000");
            }
        }

        if (ObjectUtils.isNotEmpty(payOrderDto.getOrderType())){
            queryWrapper.eq("oo.order_type",payOrderDto.getOrderType());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getDealId())){
            queryWrapper.in("co.deal_id",payOrderDto.getDealId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getChannelTypeId())){
            queryWrapper.eq("co.channel_type_id",payOrderDto.getChannelTypeId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getBankType())){
            queryWrapper.eq("oo.bank_type",payOrderDto.getBankType());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getPhoneOs())){
            queryWrapper.eq("osg.game_type",payOrderDto.getPhoneOs());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getPayVendorId())){
            queryWrapper.eq("oo.pay_vendor_id",payOrderDto.getPayVendorId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getOrderStartTime())){
            queryWrapper.ge("oo.open_time",payOrderDto.getOrderStartTime());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getOrderEndTime())){
            queryWrapper.le("oo.open_time",payOrderDto.getOrderEndTime());
        }

        // 由于op_order表没有渠道字段无法使用统一权限 手动载入权限
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String userId = loginUser.getId();
        UserDataPermissionRule userDataPermissionRule = sysBaseAPI.getUserDataPermissionRule(userId);
        List<Integer> gameIdList = new ArrayList<>();
        if (payOrderDto.getGameId() != null) {
            gameIdList.add(payOrderDto.getGameId());
        }
        if (CollectionUtil.isNotEmpty(userDataPermissionRule.getGameId())) {
            gameIdList.addAll(userDataPermissionRule.getGameId());
        }
        if(CollectionUtil.isNotEmpty(gameIdList)){
            queryWrapper.in("oo.game_id", gameIdList);
        }

        List<Integer> subGameIdList = new ArrayList<>();
        if(payOrderDto.getSubGameId() != null){
            subGameIdList.add(payOrderDto.getSubGameId());
        }
        if(CollectionUtil.isNotEmpty(userDataPermissionRule.getSubGameId())){
            subGameIdList.addAll(userDataPermissionRule.getSubGameId());
        }
        if(CollectionUtil.isNotEmpty(subGameIdList)){
            queryWrapper.in("oo.sub_game_id", subGameIdList);
        }

        List<Integer> pkgIdList = new ArrayList<>();
        if(payOrderDto.getPkgId()!= null){
            pkgIdList.add(payOrderDto.getPkgId());
        }
        if(CollectionUtil.isNotEmpty(userDataPermissionRule.getPkgId())){
            pkgIdList.addAll(userDataPermissionRule.getPkgId());
        }
        if(CollectionUtil.isNotEmpty(pkgIdList)){
            queryWrapper.in("oo.pkg_id", pkgIdList);
        }

        // 只有充值成功的订单有带渠道判断
        if (Objects.equals(1, payOrderDto.getType())) {
            List<Integer> channelIdList = new ArrayList();
            if (payOrderDto.getChannelId() != null) {
                channelIdList.add(payOrderDto.getChannelId());
            }
            if(CollectionUtil.isNotEmpty(userDataPermissionRule.getChannelId())){
                channelIdList.addAll(userDataPermissionRule.getChannelId());
            }
            if(CollectionUtil.isNotEmpty(channelIdList)){
                queryWrapper.in("co.channel_id", channelIdList);
            }
            List<Integer> channelSubIdList = new ArrayList();
            if(payOrderDto.getChannelSubAccountId() != null){
                channelSubIdList.add(payOrderDto.getChannelSubAccountId());
            }
            if(CollectionUtil.isNotEmpty(userDataPermissionRule.getChannelSubAccountId())){
                channelSubIdList.addAll(userDataPermissionRule.getChannelSubAccountId());
            }
            if(CollectionUtil.isNotEmpty(channelSubIdList)){
                queryWrapper.in("co.channel_sub_account_id", channelSubIdList);
            }
        }
        List<PayOrderVo> payOrderVos = payReportMapper.queryPayOrderList(page, queryWrapper);

        if (ObjectUtils.isNotEmpty(payOrderVos)){

            for (PayOrderVo payOrderVo : payOrderVos) {
                payOrderVo.setSendStatus(payOrderDto.getSendStatus());
            }
        }

        return page.setRecords(payOrderVos);
    }

    /**
     * 充值订单导出excel
     *
     * @param request
     */
    @Override
    public ModelAndView payOrderExportXls(HttpServletRequest request, PayOrderDto payOrderDto, String title) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer pageNo=1;
        Integer pageSize=1000;
        Page<PayOrderVo> objectPage = new Page<PayOrderVo>(pageNo, pageSize);
        IPage iPage = this.queryPayOrderList(objectPage, payOrderDto);

        ArrayList<Object> arrayList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(iPage.getRecords())){

            arrayList.addAll(iPage.getRecords());

            while (true){
                pageNo++;
                objectPage.setCurrent(pageNo);

                IPage newResult = this.queryPayOrderList(objectPage, payOrderDto);
                if (ObjectUtils.isEmpty(newResult.getRecords())){
                    break;
                }
                arrayList.addAll(newResult.getRecords());
            }
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, PayOrderVo.class);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, arrayList);
        return mv;
    }

    @Override
    public void check(SupplementaryOrderDto supplementaryOrderDto){

        if (!"xlAQQZjy0OHMZ9hWLVv1V4wUsvcQAdd5".equals(supplementaryOrderDto.getValidatePassword())){
            throw new RuntimeException("密码错误");
        }

        if (ObjectUtil.equal("1",supplementaryOrderDto.getSendStatus())){
            iPayBaseAPI.deliverOnce(supplementaryOrderDto.getOrderId());
        }
        if (ObjectUtil.equal("2",supplementaryOrderDto.getSendStatus())){
            iPayBaseAPI.updateOrderStatus(supplementaryOrderDto.getOrderId(),1000);
        }
    }

    @Override
    public PayOrderTotalVo getSumPayOrder(PayOrderDto payOrderDto){
        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(payOrderDto.getOrderId())){
            queryWrapper.eq("oo.order_id",payOrderDto.getOrderId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getGameOrderId())){
            queryWrapper.eq("oo.game_order_id",payOrderDto.getGameOrderId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getBankOrderId())){
            queryWrapper.eq("oo.bank_order_id",payOrderDto.getBankOrderId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getUserId())){
            queryWrapper.eq("oo.user_id",payOrderDto.getUserId());
        }
        if (ObjectUtil.equal(1,payOrderDto.getType())){
            queryWrapper.eq("oo.game_status","1000");
            queryWrapper.eq("oo.bank_status","1000");
        }else if (ObjectUtil.equal(0,payOrderDto.getType())){

            if (ObjectUtil.equal(1,payOrderDto.getSendStatus())){
                queryWrapper.ne("oo.game_status","1000");
            }else if (ObjectUtil.equal(2,payOrderDto.getSendStatus())){
                queryWrapper.ne("oo.bank_status","1000");
            }
        }

        if (ObjectUtils.isNotEmpty(payOrderDto.getOrderType())){
            queryWrapper.eq("oo.order_type",payOrderDto.getOrderType());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getDealId())){
            queryWrapper.in("co.deal_id",payOrderDto.getDealId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getChannelTypeId())){
            queryWrapper.eq("co.channel_type_id",payOrderDto.getChannelTypeId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getBankType())){
            queryWrapper.eq("oo.bank_type",payOrderDto.getBankType());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getPhoneOs())){
            queryWrapper.eq("osg.game_type",payOrderDto.getPhoneOs());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getPayVendorId())){
            queryWrapper.eq("oo.pay_vendor_id",payOrderDto.getPayVendorId());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getOrderStartTime())){
            queryWrapper.ge("oo.open_time",payOrderDto.getOrderStartTime());
        }
        if (ObjectUtils.isNotEmpty(payOrderDto.getOrderEndTime())){
            queryWrapper.le("oo.open_time",payOrderDto.getOrderEndTime());
        }

        // 由于op_order表没有渠道字段无法使用统一权限 手动载入权限
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String userId = loginUser.getId();
        UserDataPermissionRule userDataPermissionRule = sysBaseAPI.getUserDataPermissionRule(userId);
        List<Integer> gameIdList = new ArrayList<>();
        if (payOrderDto.getGameId() != null) {
            gameIdList.add(payOrderDto.getGameId());
        }
        if (CollectionUtil.isNotEmpty(userDataPermissionRule.getGameId())) {
            gameIdList.addAll(userDataPermissionRule.getGameId());
        }
        if(CollectionUtil.isNotEmpty(gameIdList)){
            queryWrapper.in("oo.game_id", gameIdList);
        }

        List<Integer> subGameIdList = new ArrayList<>();
        if(payOrderDto.getSubGameId() != null){
            subGameIdList.add(payOrderDto.getSubGameId());
        }
        if(CollectionUtil.isNotEmpty(userDataPermissionRule.getSubGameId())){
            subGameIdList.addAll(userDataPermissionRule.getSubGameId());
        }
        if(CollectionUtil.isNotEmpty(subGameIdList)){
            queryWrapper.in("oo.sub_game_id", subGameIdList);
        }

        List<Integer> pkgIdList = new ArrayList<>();
        if(payOrderDto.getPkgId()!= null){
            pkgIdList.add(payOrderDto.getPkgId());
        }
        if(CollectionUtil.isNotEmpty(userDataPermissionRule.getPkgId())){
            pkgIdList.addAll(userDataPermissionRule.getPkgId());
        }
        if(CollectionUtil.isNotEmpty(pkgIdList)){
            queryWrapper.in("oo.pkg_id", pkgIdList);
        }

        // 只有充值成功的订单有带渠道判断
        if (Objects.equals(1, payOrderDto.getType())) {
            List<Integer> channelIdList = new ArrayList();
            if (payOrderDto.getChannelId() != null) {
                channelIdList.add(payOrderDto.getChannelId());
            }
            if(CollectionUtil.isNotEmpty(userDataPermissionRule.getChannelId())){
                channelIdList.addAll(userDataPermissionRule.getChannelId());
            }
            if(CollectionUtil.isNotEmpty(channelIdList)){
                queryWrapper.in("co.channel_id", channelIdList);
            }
            List<Integer> channelSubIdList = new ArrayList();
            if(payOrderDto.getChannelSubAccountId() != null){
                channelSubIdList.add(payOrderDto.getChannelSubAccountId());
            }
            if(CollectionUtil.isNotEmpty(userDataPermissionRule.getChannelSubAccountId())){
                channelSubIdList.addAll(userDataPermissionRule.getChannelSubAccountId());
            }
            if(CollectionUtil.isNotEmpty(channelSubIdList)){
                queryWrapper.in("co.channel_sub_account_id", channelSubIdList);
            }
        }

        PayOrderTotalVo sumPayOrder = payReportMapper.getSumPayOrder(queryWrapper);

        return  sumPayOrder;
    }

    @Override
    public IPage orderPurchaseVolumeList(Page page, OrderPurchaseVolumeDto orderPurchaseVolumeDto) {
        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getUserName())) {
            queryWrapper.eq("ou.user_name", orderPurchaseVolumeDto.getUserName());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getUserId())) {
            queryWrapper.eq("co.user_id", orderPurchaseVolumeDto.getUserId());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getGameId())) {
            queryWrapper.eq("co.game_id", orderPurchaseVolumeDto.getGameId());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getSubGameId())) {
            queryWrapper.eq("co.sub_game_id", orderPurchaseVolumeDto.getSubGameId());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getPkgId())) {
            queryWrapper.eq("co.pkg_id", orderPurchaseVolumeDto.getPkgId());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getDealId())) {
            queryWrapper.in("co.deal_id", orderPurchaseVolumeDto.getDealId());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getChannelTypeId())) {
            queryWrapper.eq("co.channel_type_id", orderPurchaseVolumeDto.getChannelTypeId());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getChannelId())) {
            queryWrapper.eq("co.channel_id", orderPurchaseVolumeDto.getChannelId());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getChannelSubAccountId())) {
            queryWrapper.eq("co.channel_sub_account_id", orderPurchaseVolumeDto.getChannelSubAccountId());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getOrderStartTime())){
            queryWrapper.ge("co.create_time",orderPurchaseVolumeDto.getOrderStartTime());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getOrderEndTime())){
            queryWrapper.le("co.create_time",orderPurchaseVolumeDto.getOrderEndTime());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getUserStartTime())){
            queryWrapper.ge("co.user_create_time",orderPurchaseVolumeDto.getUserStartTime());
        }

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeDto.getUserEndTime())){
            queryWrapper.le("co.user_create_time",orderPurchaseVolumeDto.getUserEndTime());
        }

        List<OrderPurchaseVolumeVo> orderPurchaseVolumeVos = payReportMapper
            .orderPurchaseVolumeList(page, queryWrapper);

        if (ObjectUtils.isNotEmpty(orderPurchaseVolumeVos)){

            for (OrderPurchaseVolumeVo orderPurchaseVolumeVo : orderPurchaseVolumeVos) {
                orderPurchaseVolumeVo.setArea(orderPurchaseVolumeVo.getRegion()+'|'+orderPurchaseVolumeVo.getClientIp());
            }
        }

        return page.setRecords(orderPurchaseVolumeVos);
    }


    /**
     * 充值订单导出excel
     *
     * @param request
     */
    @Override
    public ModelAndView orderPurchaseVolumeExportXls(HttpServletRequest request, OrderPurchaseVolumeDto orderPurchaseVolumeDto, String title) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer pageNo=1;
        Integer pageSize=1000;
        Page<OrderPurchaseVolumeVo> objectPage = new Page<OrderPurchaseVolumeVo>(pageNo, pageSize);
        IPage iPage = this.orderPurchaseVolumeList(objectPage, orderPurchaseVolumeDto);

        ArrayList<Object> arrayList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(iPage.getRecords())){

            arrayList.addAll(iPage.getRecords());

            while (true){
                pageNo++;
                objectPage.setCurrent(pageNo);

                IPage newResult = this.orderPurchaseVolumeList(objectPage, orderPurchaseVolumeDto);
                if (ObjectUtils.isEmpty(newResult.getRecords())){
                    break;
                }
                arrayList.addAll(newResult.getRecords());
            }
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, OrderPurchaseVolumeVo.class);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, arrayList);
        return mv;
    }

    @Override
    public IPage userOrderSearch(Page page, UserOrderDto userOrderDto){
        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isEmpty(userOrderDto.getUserId()) && ObjectUtils.isEmpty(userOrderDto.getUserName())){
            return page.setRecords(null);
        }

        if (ObjectUtils.isNotEmpty(userOrderDto.getUserId())){
            queryWrapper.eq("co.user_id",userOrderDto.getUserId());
        }

        if (ObjectUtils.isNotEmpty(userOrderDto.getUserName())){
            queryWrapper.eq("ou.user_name",userOrderDto.getUserName());
        }
        queryWrapper.orderByDesc("co.create_time");

        List<UserOrderVo> userOrderVos = payReportMapper.userOrderSearch(page, queryWrapper);

        return page.setRecords(userOrderVos);
    }

    @Override
    public BigDecimal getUserOrderMoney(UserOrderDto userOrderDto){
        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isEmpty(userOrderDto.getUserId()) && ObjectUtils.isEmpty(userOrderDto.getUserName())){
            return null;
        }

        if (ObjectUtils.isNotEmpty(userOrderDto.getUserId())){
            queryWrapper.eq("co.user_id",userOrderDto.getUserId());
        }

        if (ObjectUtils.isNotEmpty(userOrderDto.getUserName())){
            queryWrapper.eq("ou.user_name",userOrderDto.getUserName());
        }

        BigDecimal userOrderMoney = payReportMapper.getUserOrderMoney(queryWrapper);

        return userOrderMoney;
    }
}
