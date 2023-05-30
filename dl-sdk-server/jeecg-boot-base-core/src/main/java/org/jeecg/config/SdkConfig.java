package org.jeecg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * sdk配置
 * @author: jeecg-boot
 */
@Component("sdkConfig")
@ConfigurationProperties(prefix = "sdk")
@Data
public class SdkConfig {

    /**
     * 是否调试模式 调试模式不签名
     */
    private Boolean isDebug = false;

    /**
     * 拦截器统一校验的客户端请求
     */
    private String checkUrls;

    /**
     * 单独提取走post方法的拦截器校验请求 解决获取不到post请求body的问题
     */
    private String checkPostUrls;

    /**
     * 登录session盐值
     */
    private String loginSessionSalt;

    /**
     * ios跳转统一支付url
     */
    private String iosJumpUrl;


    /**
     * 支付宝图标
     */
    private String aliIcon;

    /**
     * 微信图标
     */
    private String wxIcon;

    /**
     * 支付宝下单地址
     */
    private String aliH5OpenUrl;

    /**
     * 微信下单地址
     */
    private String wxH5OpenUrl;

    /**
     * 打包工具地址
     */
    private String packToolPath;

}
