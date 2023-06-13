package org.jeecg.common.exception;


public enum ErrorCode {

    /**
     * 短信发送失败
     */
    SMS_SEND_FAILED(-1, "send failed"),
    /**
     * sdk用户不存在
     */
    NO_FOUND_SDK_USER(-2, "用户不存在"),
    /**
     * session为空
     */
    SDK_USER_EMPTY_SESSION(-3, "session为空"),
    /**
     * 通过session校验subGameId错误
     */
    SDK_USER_INVALID_APP_ID(-4, "session校验游戏不一致"),
    /**
     * 通过session校验用户名为空
     */
    SDK_USER_ID_ERROR(-5, "session校验用户id不一致"),
    /**
     * 通过session校验sdk用户不存在
     */
    SDK_USER_DOES_NOT_EXIST(-6, "session校验用户不存在"),
    /**
     * 通过session校验sdk用户缓存已过期
     */
    SDK_USER_SESSION_TIMEOUT(-7, "session校验超时"),
    /**
     * 校验发现手机没有注册
     */
    SDK_USER_NO_BIND_PHONE(-8, "该手机没有注册"),
    /**
     * 验证码为空
     */
    SMS_CODE_EMPTY(-9, "code empty"),
    /**
     * 手机号非法
     */
    PHONE_ILLEGAL(-10, "手机号非法"),
    /**
     * 手机号已存在
     */
    SDK_USER_PHONE_ALREADY_EXIT(-11, "手机号已被使用"),
    /**
     * 验证码非法
     */
    SMS_CODE_ILLEGAL(-12, "验证码非法"),
    /**
     * 验证码非法
     */
    SDK_TYPE_FAULT(-13, "平台错误"),
    /**
     * 游戏已关服 被注册限制
     */
    GAME_IS_CLOSED(-14, "游戏已关服"),
    /**
     * 禁止跨游戏登录
     */
    LIMIT_CROSS_LOGIN(-15, "此账号不能登录，请重新注册登录"),
    /**
     * 限制获取验证码的频次
     */
    LIMIT_GET_CODE(-16, "操作频繁，请过十分钟后再试"),
    /**
     * 限制注册频次
     */
    LIMIT_REGISTER(-17, "您今天已超过最大注册量，请明天再试！"),
    /**
     * 手机号不能为空
     */
    PHONE_CANT_BE_EMPTY(-19, "手机号不能为空"),
    /**
     * 账号名称格式错误
     */
    USER_NAME_ILLEGAL(-20, "账号名格式错误"),
    /**
     * 密码格式错误
     */
    PASSWD_ILLEGAL(-21, "密码格式错误"),
    /**
     * 用户已存在
     */
    SDK_USER_ALREADY_EXIT(-22, "用户名已存在"),
    /**
     * 身份证号码有误
     */
    WRONG_ID_NUMBER(-24, "身份证号码错误"),
    /**
     * 身份证名字不合法
     */
    ILLEGAL_NAME(-25, "身份证名字不合法"),
    /**
     * 此身份信息已被绑定
     */
    ID_NUMBER_IS_BIND(-26, "此身份信息已被绑定"),
    /**
     * 实名信息有误
     */
    WRONG_AUTH_INFO(-27, "实名信息有误，请重新提交"),
    /**
     * 实名验证错误，请重新提交
     */
    AMARKET_ERR(-28, "实名验证错误，请重新提交"),
    /**
     * 身份证号与名字匹配错误，请重新输入
     */
    AUTH_MIS_MATCH(-29, "身份证号与名字匹配错误，请重新输入"),
    /**
     * 参数校验错误
     */
    SIGN_VERIFY_ERROR(-30, "验签失败"),
    /**
     * 密码错误
     */
    PASSWORD_ERROR(-31, "密码错误"),
    /**
     * 未知的更新类型
     */
    SDK_UPDATE_UNKNOWN_TYPE(-32, "未知的更新类型"),
    /**
     * 新密码非法
     */
    PASSWORD_ILLEGAL(-32, "新密码非法"),
    /**
     * 游戏不存在
     */
    NO_FOUND_SUB_GAME(-33, "游戏不存在"),
    /**
     * 规则不重复
     */
    RULE_NOT_REPEATING(2022, "规则不重复"),
    /**
     * 金额有误
     */
    MONEY_ERROR(-30, "金额有误"),
    /**
     * 缺少必要参数
     */
    INPUT_EMPTY(-31, "缺少必要参数"),
    /**
     * 错误的支付方式
     */
    WRONG_ORDER_TYPE(-32, "充值平台币不允许使用平台币支付方式，请重新选择支付方式"),
    /**
     * 用户密码为空
     */
    EMPTY_PASSWORD(-33, "用户密码为空"),
    /**
     * 订单已存在
     */
    ORDER_EXIST(-34, "订单已存在，请返回游戏商品界面重新发起支付"),
    /**
     * 用户平台币余额不足
     */
    NOT_ENOUGH_PC(-36, "用户平台币余额不足"),
    /**
     * 支付不可用
     */
    CANNOT_PAY(-38, "支付不可用"),
    /**
     * 该支付类型不支持使用平台币抵扣
     */
    CANNOT_USE_PC(-40, "该支付类型不支持使用平台币抵扣"),
    /**
     * 该游戏不支持使用平台币抵扣
     */
    SUB_GAME_CANNOT_USE_PC(-41, "该游戏不支持使用平台币抵扣"),
    /**
     * 创建订单错误
     */
    CREATE_ORDER_ERROR(-42, "创建订单错误"),
    /**
     * 记录扣除平台币出错
     */
    RECORD_USE_PC_ERROR(-43, "记录扣除平台币出错"),
    /**
     * 扣除用户平台币出错
     */
    DEDUCT_USER_PC(-44, "扣除用户平台币出错"),
    /**
     * update order failed
     */
    UPDATE_ORDER_ERROR(-45, "update order failed"),
    /**
     * 订单不存在
     */
    ORDER_IS_EMPTY(-47, "订单不存在"),
    /**
     * 回调通知参数有空值
     */
    CONF_EXIST_EMPTY(-48, "回调通知参数有空值"),
    /**
     * 回调通知参数有空值
     */
    ORDER_STATUS_ERROR(-49, "订单状态错误"),
    /**
     * 验签出错
     */
    SIGN_IS_ERROR(-50, "验签出错"),
    /**
     * 异常通知，请忽略
     */
    EXCEPTION_NOTIFICATION(-51, "异常通知，请忽略"),
     /**
     * 没有该游戏的支付配置
     */
    NOT_FOUND_GAME_PAY_CONF(-56, "没有该游戏的支付配置"),
    /**
     * 没有配置默认支付
     */
    NO_DEFAULT_PAY_CONF(-57, "没有配置默认支付"),
    /**
     * 微信支付构建http client出错
     */
    BUILD_HTTP_CLIENT_ERROR(-59, "微信支付构建http client出错"),
    /**
     * 微信下单失败
     */
    WECHAT_ORDER_ERROR(-60, "微信下单失败"),
    /**
     * 错误的微信下单类型
     */
    WRONG_WECHAT_ORDER(-61, "错误的微信下单类型"),
    /**
     * 非微信登录用户请用支付宝支付
     */
    PLEASE_USE_ALI_PAY(-62, "非微信登录用户请用支付宝支付"),
    /**
     * 支付参数为空
     */
    EMPTY_VENDOR(-63, "支付参数为空"),
    /**
     * 发送微信请求失败
     */
    SEND_WECHAT_FAIL(-64, "发送微信请求失败"),
    /**
     * ios校验订单接口繁忙
     */
    IOS_VERIFY_RECEIPT_SERVER_BUSY(-65, "ios校验订单接口繁忙"),
    /**
     * ios校验订单异常
     */
    IOS_VERIFY_RECEIPT_ERROR(-66, "ios校验订单异常"),
    /**
     * ios校验返回为空
     */
    IOS_VERIFY_RECEIPT_EMPTY(-67, "ios校验返回receipt为空"),
    /**
     * ios校验返回为空
     */
    IOS_VERIFY_RECEIPT_INAPP_EMPTY(-68, "ios校验返回inapp为空"),
    /**
     * 解析返回对象出错
     */
    PARSE_RESPONSE_FAIL(-69,"解析返回对象出错"),
    /**
     * 订单不存在
     */
    ORDER_NOT_EXIST(-70,"订单不存在"),
    /**
     * 银行状态非法
     */
    BANK_STATUS_INVAID(-71,"银行状态非法"),
    /**
     * 一个创意标题最多两个词包
     */
    WORD_TOO_MUSH(-72,"一个创意标题最多两个词包"),
    /**
     * 子游戏ID为空
     */
    SUBGAMEID_IS_EMPTY(-73,"子游戏ID为空"),
    /**
     * 子游戏ID为空
     */
    PKGID_IS_EMPTY(-74,"渠道游戏包为空"),
    /**
     * 标记为空
     */
    SIGN_IS_EMPTY(-75,"标记为空"),
    /**
     * 一级游戏包ID为空
     */
    OS_IS_EMPTY(-76,"一级游戏包ID为空"),
    /**
     * 用户ID为空
     */
    USERID_IS_EMPTY(-76,"用户ID为空"),
    /**
     * 错误的支付宝下单类型
     */
    WRONG_ALIPAY_ORDER(-77, "错误的支付宝下单类型"),
    /**
     * 子游戏不存在
     */
    SUB_GAME_IS_EMPTY(-78, "子游戏不存在"),
    /**
     * 一级游戏包不存在
     */
    PKG_IS_EMPTY(-79, "一级游戏包不存在"),
    /**
     * 未绑定隐私政策
     */
    PRIVACY_POLICY_UNBOUND(-80, "未绑定隐私政策"),
    /**
     * 隐私政策不存在
     */
    PRIVACY_POLICY_EMPTY(-81, "隐私政策不存在"),
    /**
     * 该一级游戏包未绑定用户协议
     */
    USER_PROTOCOL_UNBOUND(-82, "未绑定用户协议"),
    /**
     * 用户协议不存在
     */
    USER_PROTOCOL_EMPTY(-83, "用户协议不存在"),
    /**
     * 找不到商品
     */
    CANT_FIND_COMMODITY(-84, "找不到商品"),
    /**
     * 一级游戏包和子游戏都不存在
     */
    SUB_GAME_AND_PKG_EMPTY(-85, "子游戏不存在"),
    /**
     * 写入或更新角色信息错误
     */
    ADD_OR_UPDATE_ROLE_ERROR(-86, "写入或更新角色信息错误"),
    /**
     * 微信预下单失败
     */
    WEIXIN_PREPARE_ERROR(-87, "微信预下单失败"),
    /**
     * 微信预下单参数有误
     */
    WEIXIN_PREPARE_CONF_ERROR(-88, "微信预下单参数有误"),
    /**
     * 微信预下单参数路径为空
     */
    WEIXIN_PREPARE_CONF_URL_ERROR(-89, "微信预下单参数路径为空");

    private final int code;
    private final String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
