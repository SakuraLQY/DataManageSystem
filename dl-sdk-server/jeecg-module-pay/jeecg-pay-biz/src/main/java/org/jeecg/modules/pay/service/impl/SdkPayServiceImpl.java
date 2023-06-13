package org.jeecg.modules.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.common.constant.BankStatus;
import org.jeecg.common.constant.OrderType;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpCommodityModel;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.util.IpUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.sign.context.SdkContext;
import org.jeecg.config.sign.context.SdkInfo;
import org.jeecg.modules.api.ISdkuserApi;
import org.jeecg.modules.pay.constant.AlipayConstant;
import org.jeecg.modules.pay.constant.OpenConstant;
import org.jeecg.common.constant.PayTypeConstant;
import org.jeecg.modules.pay.constant.WechatPayConstant;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.entity.OpOrder;
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.pay.entity.OpPlatformCurrencyLog;
import org.jeecg.modules.pay.service.IOpAlipayService;
import org.jeecg.modules.pay.service.IOpOrderService;
import org.jeecg.modules.pay.service.IOpPlatformCurrencyLogService;
import org.jeecg.modules.pay.service.IOpVendorService;
import org.jeecg.modules.pay.service.ISdkPayService;
import org.jeecg.modules.pay.service.IWechatPayService;
import org.jeecg.modules.pay.util.GenOrderUtils;
import org.jeecg.modules.pay.vo.IosPayInfoVo;
import org.jeecg.modules.pay.vo.OpVendorVo;
import org.jeecg.modules.pay.vo.WechatAppVo;
import org.jeecg.modules.pay.vo.pay.AllUsePCVo;
import org.jeecg.modules.vo.OpUserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2022/12/19
 **/
@Service
@Slf4j
@DS("open_gateway")
public class SdkPayServiceImpl implements ISdkPayService {

    @Resource
    private ISdkuserApi sdkuserApi;
    @Resource
    private IOpOrderService opOrderService;
    @Resource
    private IOpVendorService opVendorService;
    @Resource
    private IWechatPayService wechatPayService;
    @Resource
    private IOpAlipayService opAlipayService;
    @Resource
    private IOpPlatformCurrencyLogService opPlatformCurrencyLogService;
    @Resource
    private IGameApi gameApi;
    @Resource
    private IAdvertApi advertApi;

    // 订单号
    private String orderId;
    // 是否全使用平台币
    private boolean isAllUsePC = false;
    // 子游戏绑定支付厂商信息
    private OpVendorVo opVendorVo;
    // 用户信息
    private OpUserVo opUserVo;
    // 广告信息
    private OpDealModel opDealModel;
    // 区域
    private String region = "";
    // 厂商渠道id
    private Integer payVendorId = 0;


    @Override
    @Transactional
    public void wechatH5Pay(OpenDto openDto, HttpServletResponse response) {
        // 前置处理
        preProcess(openDto, PayTypeConstant.PAY_TYPE_WEIXIN_WEB);
        String h5Url = "";
        // 是否全使用平台币抵扣
        if (isAllUsePC) {
            // 平台币处理
            platformCurrencyProcess();
            // TODO 303重定向
            response.setStatus(HttpServletResponse.SC_SEE_OTHER);
            response.setHeader("location", "https://www.baidu.com/");
        } else {
            // 获取微信支付渠道参数
            OpPayVendor wechatVendorConf = opVendorVo.getWxPayVendorData();
            if (oConvertUtils.isEmpty(wechatVendorConf)) {
                throw new IdeaRunTimeException(ErrorCode.EMPTY_VENDOR);
            }
            JSONObject vendorConf = JSONObject.parseObject(wechatVendorConf.getPayVendorConf());
            JSONObject responseData = null;

            if (vendorConf.getString(WechatPayConstant.VENDOR_KEY_VERSION).equals(WechatPayConstant.VERSION_V2)) {
                Map<String, String> res = wechatPayService.wechatV2Open(
                    WechatPayConstant.H5_V2_ORDER,
                    orderId,
                    openDto, wechatVendorConf, null);
                if (oConvertUtils.isEmpty(res.get("mweb_url"))) {
                    throw new IdeaRunTimeException(ErrorCode.WEIXIN_PREPARE_CONF_URL_ERROR);
                }
                h5Url = res.get("mweb_url");
            } else if (vendorConf.getString(WechatPayConstant.VENDOR_KEY_VERSION).equals(WechatPayConstant.VERSION_V3)) {
                responseData = wechatPayService.wechatOpen(WechatPayConstant.H5_V3_ORDER,
                    orderId,
                    openDto, wechatVendorConf, null);
                if (oConvertUtils.isEmpty(responseData)) {
                    throw new IdeaRunTimeException(ErrorCode.PARSE_RESPONSE_FAIL);
                }
                h5Url = responseData.getString("h5_url");
                String wechatRedirectUrl = openDto.getWechatRedirectUrl();
                if (oConvertUtils.isNotEmpty(wechatRedirectUrl)) {
                    h5Url = h5Url.concat(wechatRedirectUrl);
                }

            }
            // TODO 303重定向
            response.setStatus(HttpServletResponse.SC_SEE_OTHER);
            response.setHeader("location", h5Url);
        }
    }

    @Override
    @Transactional
    public void alipayH5Pay(OpenDto openDto, HttpServletResponse response) {
        // 前置处理
        preProcess(openDto, PayTypeConstant.PAY_TYPE_H5_ALI);

        // 是否全使用平台币抵扣
        if (isAllUsePC) {
            // 平台币处理
            platformCurrencyProcess();
        } else {
            // 获取支付宝支付渠道参数
            OpPayVendor alipayVendorConf = opVendorVo.getAliPayVendorData();
            if (oConvertUtils.isEmpty(alipayVendorConf)) {
                throw new IdeaRunTimeException(ErrorCode.EMPTY_VENDOR);
            }
            String responseData = opAlipayService.alipayOpen(AlipayConstant.H5_ORDER,
                orderId,
                openDto, alipayVendorConf, null);
            response.setStatus(HttpServletResponse.SC_SEE_OTHER);
            response.setHeader("location", responseData);

        }

    }

    @Override
    public IosPayInfoVo iosPay(OpenDto openDto) {
        // 用金额匹配商品id
        OpCommodityModel opCommodityModel = gameApi.getOpCommdityByMoney(openDto.getSubGameId(),
            openDto.getPkgId(), openDto.getMmm());
        IosPayInfoVo iosPayInfoVo = new IosPayInfoVo();
        if (null == opCommodityModel) {
            throw new IdeaRunTimeException(ErrorCode.CANT_FIND_COMMODITY);
        }
        iosPayInfoVo.setProductId(opCommodityModel.getGoodsId());
        // 前置处理
        preProcess(openDto, PayTypeConstant.PAY_TYPE_APPLE);

        iosPayInfoVo.setOrderId(orderId);
        return iosPayInfoVo;
    }

    @Override
    @Transactional
    public SdkResult<WechatAppVo> wechatAppPay(OpenDto openDto) {
        // 前置处理
        preProcess(openDto, PayTypeConstant.PAY_TYPE_WEIXIN);
        // 是否全使用平台币抵扣
        if (isAllUsePC) {
            // 平台币处理
            platformCurrencyProcess();

            return SdkResult.success(new WechatAppVo(true));
        } else {
            // 获取微信支付渠道参数
            OpPayVendor wechatVendorConf = opVendorVo.getWxPayVendorData();
            if (oConvertUtils.isEmpty(wechatVendorConf)) {
                throw new IdeaRunTimeException(ErrorCode.EMPTY_VENDOR);
            }

            JSONObject responseData = wechatPayService.wechatOpen(WechatPayConstant.APP_ORDER,
                orderId,
                openDto, wechatVendorConf, null);

            String prepayId = responseData.getString("prepay_id");
            return SdkResult.success(new WechatAppVo(prepayId));
        }
    }

    @Override
    @Transactional
    public SdkResult<AllUsePCVo> platformCurrencyPay(OpenDto openDto) {
        // 前置处理
        preProcess(openDto, PayTypeConstant.PAY_TYPE_PC);
        BigDecimal money = openDto.getMmm();
        Integer userId = opUserVo.getId();

        // 全使用平台币抵扣
        if (isAllUsePC) {
            // 平台币处理
            platformCurrencyProcess();
            return SdkResult.success(new AllUsePCVo(orderId));
        }
        // 正常平台币支付
        // 需要支付的金额 = 扣除优惠金额
        BigDecimal realMoney = money.subtract(openDto.getCouponPrice());
        BigDecimal loss = realMoney.multiply(OpenConstant.MONEY_COMPARE_PC)
            .setScale(0, RoundingMode.DOWN);
        boolean lossResult = sdkuserApi.updateUserPC(userId, loss.intValue(), false);
        if (!lossResult) {
            throw new IdeaRunTimeException(ErrorCode.DEDUCT_USER_PC);
        }

        // 平台币处理
        platformCurrencyProcess();
        return SdkResult.success(null);
    }

    /**
     * @author xmh
     * @description 平台币处理
     * @date 2023/1/11 17:51
     */
    private void platformCurrencyProcess() {
        // 更新游戏支付状态
        boolean updateResult = opOrderService.updateOrderStatus(orderId,
            BankStatus.NORMAL);
        if (!updateResult) {
            throw new IdeaRunTimeException(ErrorCode.UPDATE_ORDER_ERROR);
        }
        // 更新平台币记录
        boolean updatePcResult = opPlatformCurrencyLogService.updateStatus(orderId,
            OpenConstant.PC_USE);
        if (!updatePcResult) {
            throw new IdeaRunTimeException(ErrorCode.RECORD_USE_PC_ERROR);
        }
        // 发货
        opOrderService.deliverOnce(orderId);
    }

    /**
     * @param openDto: 下单参数
     * @param payType: 支付方式
     * @author xmh
     * @description 前置处理
     * @date 2023/1/9 17:02
     */
    public void preProcess(OpenDto openDto, Integer payType) {
        BigDecimal money = openDto.getMmm();
        Integer userId = openDto.getUserId();
        // 获取子游戏信息
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        OpSubGameModel subGameModel = sdkInfo.getOpSubGameModel();
        OpPkgModel opPkgModel = sdkInfo.getOpPkgModel();
        // 设置ip
        openDto.setClientIp(sdkInfo.getIp());
        try {
            region = IpUtils.getCityInfo(sdkInfo.getIp());
        } catch (Exception e) {
            log.error("根据ip:{}获取地址异常", sdkInfo.getIp());
        }
        // 获取广告信息
        opDealModel = advertApi.getOpDeal(openDto.getDealId());
        if(null == opDealModel){
            opDealModel = new OpDealModel();
        }
        Integer gameId = subGameModel.getGameId();
        // 获取用户信息
        opUserVo = sdkuserApi.getOpUserVoById(userId);
        // 前置判断
        Integer subGameId = openDto.getSubGameId();
        Integer orderType = openDto.getOrderType();
        check(opUserVo, userId, payType, gameId, subGameId, orderType,
            openDto.getDevice(),
            openDto.getUserPassword(), openDto.getClientIp());

        // 统一支付金额格式 3位小数 四舍五入
        BigDecimal moneyFormat = money.setScale(OpenConstant.SAVE_POINT_NUM, RoundingMode.HALF_UP);
        openDto.setMmm(moneyFormat);
        if (moneyFormat.compareTo(OpenConstant.MONEY_MIN) <= 0
            || moneyFormat.compareTo(OpenConstant.MONEY_MAX) > 0) {
            throw new IdeaRunTimeException(ErrorCode.MONEY_ERROR);
        }
        // 获取支付厂商信息
        if (oConvertUtils.isNotEmpty(opPkgModel)) {
            opVendorVo = opVendorService.getOpVendorVoById(opPkgModel.getVendorId());
        } else {
            opVendorVo = opVendorService.getOpVendorVoById(subGameModel.getVendorId());
        }
        if (oConvertUtils.isEmpty(opVendorVo)) {
            throw new IdeaRunTimeException(ErrorCode.EMPTY_VENDOR);
        }
        OpPayVendor opPayVendor = opVendorVo.getOpPayVendorByType(payType);
        if(opPayVendor != null){
            // 支付厂商id
            payVendorId = opPayVendor.getId();
        }
        String subGameOrderId = openDto.getSubGameOrderId();
        // 平台币充值
        if (orderType == OrderType.PLATFORM_CURRENCY) {
            // 平台币充值时需要生成一个内部订单
            subGameOrderId = GenOrderUtils.genPcOrderId();
            openDto.setSubGameOrderId(subGameOrderId);
        } else {
            if (oConvertUtils.isEmpty(subGameOrderId)) {
                throw new IdeaRunTimeException(ErrorCode.INPUT_EMPTY);
            }
        }
        // 判断订单是否重复
        OpOrder opOrder = opOrderService.getPkgIdOrder(openDto.getPkgId(), subGameOrderId);
        if (oConvertUtils.isNotEmpty(opOrder)) {
            if(!BankStatus.INIT.equals(opOrder.getBankStatus())){
                throw new IdeaRunTimeException(ErrorCode.ORDER_EXIST);
            }else{
                // 如果订单已存在则使用历史订单数据
                orderId = opOrder.getOrderId();
                return;
            }
        }
        // 生成订单号
        orderId = GenOrderUtils.genOrderId();
        // 根据充值类型实现不同的创建订单方式
        if (orderType == OrderType.PLATFORM_CURRENCY) {
            // 平台币充值
            chargeGameOrPlatformCurrency(orderId, payType, gameId, moneyFormat, openDto, null);
        } else {
            if (openDto.getUsePlatformCurrency() == OpenConstant.USE_PC) {
                // 游戏充值，使用平台币抵扣
                chargeGameUsePlatformCurrency(orderId, payType, gameId, moneyFormat, opUserVo,
                    openDto,
                    subGameModel, opPkgModel);
            } else {
                // 游戏充值
                chargeGameOrPlatformCurrency(orderId, payType, gameId, moneyFormat, openDto,
                    opUserVo);
            }
        }
    }

    /**
     * @param opUserVo:  用户信息
     * @param userId:    用户ID
     * @param payType:   支付方式
     * @param gameId:    游戏ID
     * @param subGameId: 子游戏ID
     * @param orderType: 充值类型
     * @param device:    设备
     * @param password:  密码
     * @param clientIp:  IP
     * @author xmh
     * @description 前置检查
     * @date 2022/12/23 20:28
     */
    private void check(OpUserVo opUserVo, Integer userId, Integer payType, Integer gameId,
        Integer subGameId, Integer orderType, String device, String password,
        String clientIp) {
        // 平台币充值时不允许使用平台币支付方式
        if (orderType == OrderType.PLATFORM_CURRENCY && payType == PayTypeConstant.PAY_TYPE_PC) {
            throw new IdeaRunTimeException(ErrorCode.WRONG_ORDER_TYPE);
        }

        // 读取用户信息
        if (oConvertUtils.isEmpty(opUserVo)) {
            throw new IdeaRunTimeException(ErrorCode.NO_FOUND_SDK_USER);
        }

        // 平台币支付判断
        if (payType == PayTypeConstant.PAY_TYPE_PC) {
            // 平台币支付方式需要用户输入密码
            if (oConvertUtils.isEmpty(password)) {
                throw new IdeaRunTimeException(ErrorCode.EMPTY_PASSWORD);
            }

            // 判断密码
            if (!password.equals(opUserVo.getUserPassword())) {
                throw new IdeaRunTimeException(ErrorCode.PASSWORD_ERROR);
            }

            // 判断用户平台币余额
            BigDecimal platformCurrency = opUserVo.getPlatformCurrency();
            if (platformCurrency.compareTo(BigDecimal.ZERO) == 0) {
                throw new IdeaRunTimeException(ErrorCode.NOT_ENOUGH_PC);
            }
        }

        // 恶意支付判断
        boolean cannotPay = sdkuserApi.checkBlackUser(userId, gameId, subGameId, clientIp, device);
        if (cannotPay) {
            throw new IdeaRunTimeException(ErrorCode.CANNOT_PAY);
        }
    }

    /**
     * @param orderId: 订单ID
     * @param payType: 支付方式
     * @param gameId:  游戏ID
     * @param money:   金额
     * @param openDto: 基础信息
     * @param user:    用户信息
     * @author xmh
     * @description 游戏充值或平台币充值
     * @date 2022/12/23 20:30
     */
    private void chargeGameOrPlatformCurrency(String orderId, Integer payType, Integer gameId,
        BigDecimal money,
        OpenDto openDto,
        OpUserVo user) {
        // 使用平台币支付时判断平台币余额
        if (payType == PayTypeConstant.PAY_TYPE_PC) {
            BigDecimal platformCurrency = user.getPlatformCurrency();
            // 需要支付的金额
            BigDecimal realMoney = money.subtract(openDto.getCouponPrice());
            // 需要的平台币
            BigDecimal need = realMoney.multiply(OpenConstant.MONEY_COMPARE_PC)
                .setScale(0, RoundingMode.DOWN);
            if (platformCurrency.compareTo(need) < 0) {
                throw new IdeaRunTimeException(ErrorCode.NOT_ENOUGH_PC);
            }
            // 添加平台币扣除记录, 优惠金额等于需要支付的金额
            addPlatformCurrencyLog(orderId, money, realMoney, openDto, gameId);
        }
        // 构建统计数据
        LocalDateTime time = LocalDateTime.now();
        String extraData1 = buildExtraData(orderId, payType, money, BigDecimal.ZERO, openDto, gameId, time);
        OpOrder opOrder = buildOrder(openDto, payType, orderId, extraData1, money, gameId, time);

        // 创建订单
        boolean result = opOrderService.save(opOrder);
        if (!result) {
            throw new IdeaRunTimeException(ErrorCode.CREATE_ORDER_ERROR);
        }
    }

    /**
     * @param orderId:      订单ID
     * @param payType:      支付方式
     * @param gameId:       游戏ID
     * @param money:        金额
     * @param user:         用户信息
     * @param openDto:      基础信息
     * @param subGameModel: 子游戏信息
     * @author xmh
     * @description 游戏充值，使用平台币抵扣
     * @date 2022/12/23 20:31
     */
    private void chargeGameUsePlatformCurrency(String orderId, Integer payType, Integer gameId,
        BigDecimal money,
        OpUserVo user,
        OpenDto openDto, OpSubGameModel subGameModel, OpPkgModel opPkgModel) {
        // 判断该支付类型是否可以使用平台币抵扣
        int a = Arrays.binarySearch(PayTypeConstant.CANNOT_USE_PC, payType);
        if (a >= 0) {
            throw new IdeaRunTimeException(ErrorCode.CANNOT_USE_PC);
        }

        // 读取出平台币抵扣规则
        boolean platformCurrencyState = subGameModel.getPlatformCurrencySwitch() == 1;
        Integer platformCurrencyDiscount = subGameModel.getPlatformCurrencyDiscount();
        if (oConvertUtils.isNotEmpty(opPkgModel)) {
            platformCurrencyState = opPkgModel.getPlatformCurrencySwitch() == 1;
            platformCurrencyDiscount = opPkgModel.getPlatformCurrencyDiscount();
        }
        if (!platformCurrencyState) {
            throw new IdeaRunTimeException(ErrorCode.SUB_GAME_CANNOT_USE_PC);
        }

        // 原来的优惠金额
        BigDecimal oldCouponPrice = openDto.getCouponPrice();
        // 需要支付的金额
        BigDecimal realMoney = money.subtract(oldCouponPrice);
        // 单位为元
        BigDecimal couponPrice = BigDecimal.ZERO;
        // 扣除的平台币
        BigDecimal lossPc = BigDecimal.ZERO;
        BigDecimal platformCurrency = user.getPlatformCurrency();
        // 如果平台币抵扣比例不为0并且用户平台币也不为0
        if (platformCurrencyDiscount != 0 && (platformCurrency.compareTo(BigDecimal.ZERO) > 0)) {
            // 用原始的比例乘以金额得到可以抵扣的金额
            BigDecimal discountMoney = realMoney.multiply(
                BigDecimal.valueOf(platformCurrencyDiscount / OpenConstant.PC_DISCOUNT));
            // 金额乘以平台币兑换比得到需要扣除的平台币
            BigDecimal maxLoss = discountMoney.multiply(OpenConstant.MONEY_COMPARE_PC);
            // 舍弃小数
            BigDecimal maxNum = maxLoss.setScale(0, RoundingMode.DOWN);
            if (platformCurrency.compareTo(maxNum) >= 0) {
                lossPc = maxNum;
            } else {
                lossPc = platformCurrency;
            }

            // 扣除的平台币除以平台币兑换比得到优惠金额, 3位小数 取整
            couponPrice = lossPc.divide(OpenConstant.MONEY_COMPARE_PC, OpenConstant.SAVE_POINT_NUM,
                RoundingMode.DOWN);
        }

        // 全平台币支付(抵扣比例为百分百)把支付类型改为 1 平台币支付
        BigDecimal fullPc = realMoney.multiply(OpenConstant.MONEY_COMPARE_PC);
        if (lossPc.compareTo(fullPc) >= 0) {
            isAllUsePC = true;
            payType = PayTypeConstant.PAY_TYPE_PC;
        }

        // 构建统计数据
        LocalDateTime time = LocalDateTime.now();
        // 原优惠金额加平台币优惠金额
        BigDecimal finalCouponPrice = couponPrice.add(oldCouponPrice);
        openDto.setCouponPrice(finalCouponPrice);
        String extraData1 = buildExtraData(orderId, payType, money, finalCouponPrice, openDto,
            gameId, time);
        OpOrder opOrder = buildOrder(openDto, payType, orderId, extraData1, money, gameId, time);

        // 创建订单
        boolean result = opOrderService.save(opOrder);
        if (!result) {
            throw new IdeaRunTimeException(ErrorCode.CREATE_ORDER_ERROR);
        }

        // 添加平台币扣除记录
        if (lossPc.compareTo(BigDecimal.ZERO) > 0) {
            addPlatformCurrencyLog(orderId, money, couponPrice, openDto, gameId);

            // 扣除用户平台币
            boolean updateResult = sdkuserApi.updateUserPC(user.getId(), lossPc.intValue(), false);
            if (!updateResult) {
                throw new IdeaRunTimeException(ErrorCode.DEDUCT_USER_PC);
            }
        }
    }

    /**
     * @param openDto:    基础信息
     * @param payType:    支付方式
     * @param orderId:    订单ID
     * @param extraData1: 统计信息
     * @param money:      金额
     * @param gameId:     游戏ID
     * @param time:       时间
     * @return OpOrder
     * @author xmh
     * @description 构建订单对象
     * @date 2022/12/23 20:32
     */
    private OpOrder buildOrder(OpenDto openDto, Integer payType, String orderId, String extraData1,
        BigDecimal money,
        Integer gameId, LocalDateTime time) {
        OpOrder opOrder = new OpOrder();
        opOrder.setGameId(gameId);
        opOrder.setSubGameId(openDto.getSubGameId());
        opOrder.setPkgId(openDto.getPkgId());
        opOrder.setUserId(openDto.getUserId());
        opOrder.setOrderId(orderId);
        opOrder.setMoney(money);
        opOrder.setCouponPrice(openDto.getCouponPrice());
        opOrder.setStatus(OpenConstant.CREATE_ORDER_STATUS);
        opOrder.setGameOrderId(openDto.getSubGameOrderId());
        opOrder.setBankType(payType);
        opOrder.setGameData(openDto.getSubGameData());
        opOrder.setExtraData1(extraData1);
        opOrder.setOrderType(openDto.getOrderType());
        opOrder.setOpenTime(time);
        opOrder.setStatusTime(time);
        opOrder.setClientIp(openDto.getClientIp());
        opOrder.setRegion(region);
        opOrder.setPayVendorId(payVendorId);
        return opOrder;
    }

    /**
     * @param orderId:     订单ID
     * @param payType:     支付方式
     * @param money:       金额
     * @param couponPrice: 优惠金额
     * @param openDto:     基础信息
     * @param time:        时间
     * @return String
     * @author xmh
     * @description 构建统计信息
     * @date 2022/12/23 20:32
     */
    private String buildExtraData(String orderId, Integer payType, BigDecimal money,
        BigDecimal couponPrice, OpenDto openDto, Integer gameId, LocalDateTime time) {
        Integer dealId = openDto.getDealId();
        ParsePayDto parsePayDto = new ParsePayDto();
        if (oConvertUtils.isNotEmpty(dealId)) {
            Long timestamp = time.toInstant(ZoneOffset.of("+8")).toEpochMilli();
            parsePayDto.setGameId(gameId)
                .setSubGameId(openDto.getSubGameId())
                .setChannelId(opDealModel.getChannelId() == null ? 0 : opDealModel.getChannelId())
                .setChannelTypeId(opDealModel.getChannelTypeId() == null ? 0 : opDealModel.getChannelTypeId())
                .setChannelSubAccountId(opDealModel.getChannelSubAccountId() == null ? 0 : opDealModel.getChannelTypeId())
                .setDealId(dealId)
                .setPkgId(openDto.getPkgId())
                .setUniqueId(openDto.getDevice())
                .setUserId(openDto.getUserId())
                .setMoney(money).setOrderId(orderId)
                .setPayType(payType)
                .setOrderType(openDto.getOrderType())
                .setCouponPrice(couponPrice)
                .setServerId(openDto.getServerId())
                .setRoleId(openDto.getRoleId())
                .setRoleLevel(openDto.getRoleLevel())
                .setPayVendorId(payVendorId)
                .setClientIp(openDto.getClientIp())
                .setRegion(region)
                .setTime(timestamp);
        }
        return JSONObject.toJSONString(parsePayDto);
    }

    /**
     * @param orderId:     订单编号
     * @param money:       金额
     * @param couponPrice: 优惠金额
     * @param openDto:     基础信息
     * @param gameId:      游戏ID
     * @author xmh
     * @description 添加平台币扣除记录
     * @date 2022/12/24 11:48
     */
    private void addPlatformCurrencyLog(String orderId, BigDecimal money, BigDecimal couponPrice,
        OpenDto openDto, Integer gameId) {
        OpPlatformCurrencyLog opPlatformCurrencyLog = new OpPlatformCurrencyLog();
        opPlatformCurrencyLog.setGameId(gameId);
        opPlatformCurrencyLog.setSubGameId(openDto.getSubGameId());
        opPlatformCurrencyLog.setPkgId(openDto.getPkgId());
        opPlatformCurrencyLog.setUserId(openDto.getUserId());
        opPlatformCurrencyLog.setOrderId(orderId);
        opPlatformCurrencyLog.setMoney(money);
        opPlatformCurrencyLog.setCouponPrice(couponPrice);
        opPlatformCurrencyLog.setStatus(OpenConstant.PC_FOR_NOW);
        opPlatformCurrencyLog.setCreateTime(new Date());

        boolean resultPC = opPlatformCurrencyLogService.save(opPlatformCurrencyLog);
        if (!resultPC) {
            throw new IdeaRunTimeException(ErrorCode.RECORD_USE_PC_ERROR);
        }
    }
}
