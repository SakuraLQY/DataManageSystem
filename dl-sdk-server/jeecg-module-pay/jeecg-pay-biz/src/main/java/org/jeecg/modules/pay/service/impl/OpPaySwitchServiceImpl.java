package org.jeecg.modules.pay.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.IsConstant;
import org.jeecg.common.util.DateUtil;
import org.jeecg.common.util.IpUtils;
import org.jeecg.config.sign.context.SdkContext;
import org.jeecg.config.sign.context.SdkInfo;
import org.jeecg.common.constant.PayTypeConstant;
import org.jeecg.modules.pay.dto.IosPayListDto;
import org.jeecg.modules.pay.dto.IosPayMenuDto;
import org.jeecg.modules.pay.dto.OpPaySwitchDto;
import org.jeecg.modules.pay.entity.OpOrderCount;
import org.jeecg.modules.pay.entity.OpPaySwitch;
import org.jeecg.modules.pay.mapper.OpOrderCountMapper;
import org.jeecg.modules.pay.mapper.OpPaySwitchMapper;
import org.jeecg.modules.pay.service.IIosPayService;
import org.jeecg.modules.pay.service.IOpPaySwitchService;
import org.jeecg.modules.pay.service.ISdkPayService;
import org.jeecg.modules.pay.vo.IosCheckPayModelVo;
import org.jeecg.modules.pay.vo.IosPayInfoVo;
import org.jeecg.modules.pay.vo.IosPayMenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Description: op_pay_switch
 * @Author: jeecg-boot
 * @Date: 2022-12-12
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpPaySwitchServiceImpl extends ServiceImpl<OpPaySwitchMapper, OpPaySwitch> implements
    IOpPaySwitchService {

    @Resource
    OpPaySwitchMapper opPaySwitchMapper;

    @Resource
    private OpOrderCountMapper opOrderCountMapper;

    @Resource
    private ISdkPayService sdkPayService;

    @Resource
    private IIosPayService iosPayService;

    @Override
    public IPage<OpPaySwitch> selectList(Page<OpPaySwitch> page, OpPaySwitchDto opPaySwitchDto) {
        LambdaQueryWrapper<OpPaySwitch> wrapper = new LambdaQueryWrapper<>();
        if (null != opPaySwitchDto.getGameVersion()) {
            wrapper.eq(OpPaySwitch::getGameVersion, opPaySwitchDto.getGameVersion());
        }
        if (null != opPaySwitchDto.getPkgIdList() && !opPaySwitchDto.getPkgIdList().isEmpty()) {
            List<Integer> gameIdList = new ArrayList<>();
            List<Integer> subGameIdList = new ArrayList<>();
            List<Integer> pkgIdList = new ArrayList<>();
            for (String id : opPaySwitchDto.getPkgIdList()) {
                if (id.contains("game")) {
                    id = id.replace("game", "");
                    id = id.replaceAll(" ", "");
                    gameIdList.add(Integer.valueOf(id));
                } else if (id.contains("subGame")) {
                    id = id.replace("subGame", "");
                    id = id.replaceAll(" ", "");
                    subGameIdList.add(Integer.valueOf(id));
                } else {
                    pkgIdList.add(Integer.valueOf(id));
                }
            }
            if (!gameIdList.isEmpty()) {
                wrapper.or().in(OpPaySwitch::getGameId, gameIdList);
            }
            if (!subGameIdList.isEmpty()) {
                wrapper.or().in(OpPaySwitch::getSubGameId, subGameIdList);
            }
            if (!pkgIdList.isEmpty()) {
                wrapper.or().in(OpPaySwitch::getPkgId, pkgIdList);
            }
        }
        List<OpPaySwitch> list = opPaySwitchMapper.selectList(wrapper);
        IPage<OpPaySwitch> pageList = new Page<>(page.getCurrent(), page.getSize());
        pageList.setRecords(Lists.reverse(list));
        pageList.setTotal(list.size());
        return pageList;
    }

    @Override
    public void add(OpPaySwitchDto opPaySwitchDto) {
        List<String> pkgIdList = opPaySwitchDto.getPkgIdList();
        Integer pkgId = Integer.valueOf(pkgIdList.get(2));
        Integer gameId = Integer.valueOf(pkgIdList.get(0).substring(4, pkgIdList.get(0).length()));
        Integer subGameId = Integer.valueOf(
            pkgIdList.get(1).substring(7, pkgIdList.get(1).length()));
        OpPaySwitch opPaySwitch = new OpPaySwitch();
        BeanUtils.copyProperties(opPaySwitchDto, opPaySwitch);
        opPaySwitch.setGameId(gameId);
        opPaySwitch.setSubGameId(subGameId);
        opPaySwitch.setPkgId(pkgId);
        save(opPaySwitch);
    }

    /**
     * @param subGameId:  游戏id
     * @param userId:     用户id
     * @param version:    游戏版本
     * @param build:      游戏构建
     * @param orderMoney: 单笔订单金额
     * @return 默认支付列表或者非默认支付列表
     * @author czb
     * @description 切支付逻辑
     * @date 2022/12/15 18:23
     */
    @Override
    public List<Integer> checkPayMode(Integer subGameId, Integer pkgId, String userId,
        String version,
        String build, BigDecimal orderMoney) {
        // 如果是母包的话 不限制支付
        if(pkgId == 0){
            return PayTypeConstant.DEFAULT_PAY;
        }
        // 安卓不切支付，直走默认支付
        OpPaySwitch paySwitchConf = opPaySwitchMapper
            .selectOne(new LambdaQueryWrapper<OpPaySwitch>().eq(OpPaySwitch::getSubGameId, subGameId)
                .eq(OpPaySwitch::getPkgId, pkgId));
        if(null == paySwitchConf){
            return new ArrayList<>();
        }
        //默认支付
        String defaultPayStr = paySwitchConf.getDefaultPay();
        List<Integer> defaultPay = new LinkedList<>();
        if (defaultPayStr != null) {
            defaultPay = Arrays.stream(defaultPayStr.split(","))
                .map(Integer::valueOf).collect(Collectors.toList());
        }
        return defaultPay;
    }

    @Override
    public IosCheckPayModelVo checkPayModeIos(IosPayListDto iosPayListDto, String serverName,
        int port) {
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        iosPayListDto.setClientIp(sdkInfo.getIp());
        IosCheckPayModelVo result = new IosCheckPayModelVo();
        // 支付方式列表
        List<Integer> ztList = iosPayList(iosPayListDto.getSubGameId(),
            iosPayListDto.getUserId().toString(),
            iosPayListDto.getVersion(), iosPayListDto.getBuild(), iosPayListDto.getMmm(),
            iosPayListDto.getClientIp());
        if (ztList.contains(PayTypeConstant.PAY_TYPE_APPLE)) {
            // 如果存在苹果支付 返回苹果支付的订单号和商品号
            result.setState(IsConstant.YES);
            IosPayInfoVo iosPayInfoVo = sdkPayService.iosPay(iosPayListDto);
            result.setIosInfo(iosPayInfoVo);
        } else {
            result.setState(IsConstant.NO);
            IosPayMenuDto iosPayMenuDto = new IosPayMenuDto();
            BeanUtils.copyProperties(iosPayListDto, iosPayMenuDto);
            iosPayMenuDto.setRoleName(iosPayListDto.getRoleName());
            iosPayMenuDto.setZtTypeList(ztList);
            IosPayMenuVo iosPayMenuVo = iosPayService.iosPayMenu(iosPayMenuDto, serverName, port);
            result.setThirdMenu(iosPayMenuVo);
        }
        return result;
    }

    /**
     * @param subGameId
     * @param userId
     * @param version
     * @param build
     * @param orderMoney
     * @param ip
     * @return java.util.List<java.lang.Integer>
     * @author chenyw
     * @description ios支付列表
     * @date 15:32 2023/3/30/030
     **/
    private List<Integer> iosPayList(Integer subGameId, String userId, String version,
        String build, BigDecimal orderMoney, String ip) {
        //默认ios支付
        List<Integer> iosPay = Collections.singletonList(PayTypeConstant.PAY_TYPE_APPLE);

        //查询当前版本支付配置
        OpPaySwitch paySwitchConf = opPaySwitchMapper
            .selectOne(new LambdaQueryWrapper<OpPaySwitch>().eq(OpPaySwitch::getGameBuild, build));
        //没有配置
        if (paySwitchConf == null) {
            return iosPay;
        }
        //默认支付
//        String defaultPayStr = Optional.ofNullable(paySwitchConf.getDefaultPay()).orElse("");
        String defaultPayStr = paySwitchConf.getDefaultPay();
        List<Integer> defaultPay = new LinkedList<>();
        if (defaultPayStr != null) {
            defaultPay = Arrays.stream(defaultPayStr.split(","))
                .map(Integer::valueOf).collect(Collectors.toList());
        }

        //黑名单
        String blackListStr = paySwitchConf.getBlackList();
        List<String> blackList = new LinkedList<>();
        if (blackListStr != null) {
            blackList = Arrays.asList(blackListStr.split(";"));
        }

        //非默认支付
        String noDefaultStr = paySwitchConf.getNoDefaultPay();
        List<Integer> noDefaultPay = new LinkedList<>();
        if (StringUtils.isNotEmpty(noDefaultStr)) {
            noDefaultPay = Arrays.stream(noDefaultStr.split(","))
                .map(Integer::valueOf).collect(Collectors.toList());
        }
        //在黑名单或者没有配置非默认支付
        if (blackList.contains(userId) || noDefaultPay.size() == 0) {
            return defaultPay;
        }
        String whiteListStr = paySwitchConf.getWhiteList();
        List<String> whiteList = new LinkedList<>();
        if (whiteListStr != null) {
            whiteList = Arrays.asList(whiteListStr.split(";"));
        }
        //在白名单
        if (whiteList.contains(userId)) {
            return noDefaultPay;
        }
        //游戏版本合构建版本匹配。默认支付
        if (version.equals(paySwitchConf.getGameVersion()) && build
            .equals(paySwitchConf.getGameBuild())) {
            return defaultPay;
        }
        //时间
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String rangeTimeStr = paySwitchConf.getRangeTime();
        if (rangeTimeStr != null && !DateUtil.timeIsInRound(dateFormat.format(calendar.getTime()),
            rangeTimeStr)) {
            return defaultPay;
        }
        //地区
        String regionsStr = paySwitchConf.getRegions();
        if (regionsStr != null) {
            List<String> regions = Arrays.asList(regionsStr.split(","));
            try {
                String region = IpUtils.getCityInfo(ip);
                if (!(regions.contains("中国") && regions.contains(region))) {
                    return defaultPay;
                } else {
                    if (!regions.contains("其他地区(包含海外)")) {
                        return defaultPay;
                    }
                }
            } catch (Exception e) {
                log.error("解析ip地区异常");
                return defaultPay;
            }
        }

        OpOrderCount orderCount = opOrderCountMapper.selectOne(
            new LambdaQueryWrapper<OpOrderCount>().eq(OpOrderCount::getGameId, subGameId)
                .eq(OpOrderCount::getUserId, userId));
        OpOrderCount orderCount1 = Optional.ofNullable(orderCount)
            .orElse(new OpOrderCount(0, BigDecimal.ZERO));
        //金额小于【订单金额】 -->默认支付
        if (paySwitchConf.getOrderMoney() != null
            && orderMoney.compareTo(paySwitchConf.getOrderMoney()) < 0) {
            return defaultPay;
        }
        //次数小于【充值次数】 --> 默认支付
        if (paySwitchConf.getRechargeTimes() != null
            && orderCount1.getNumber() < paySwitchConf.getRechargeTimes()) {
            return defaultPay;
        }
        // 总充值小于【累充金额】-->默认支付
        if (paySwitchConf.getTotalMoney() != null
            || orderCount1.getTotalMoney().compareTo(paySwitchConf.getTotalMoney()) < 0) {
            return defaultPay;
        }

        return noDefaultPay;
    }

}
