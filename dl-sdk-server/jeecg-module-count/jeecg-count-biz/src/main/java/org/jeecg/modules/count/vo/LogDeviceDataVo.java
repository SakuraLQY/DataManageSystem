package org.jeecg.modules.count.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: LogManageVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/12 10:12
 */
@Data
public class LogDeviceDataVo {

    /**
     * 游戏名
     */
    @Excel(name = "游戏名", width = 15)
    private String gameName;

    /**
     * 所属渠道
     */
    @Excel(name = "所属渠道", width = 15)
    private String channelName;

    /**
     * 广告id
     */
    @Excel(name = "广告id", width = 15)
    private String dealId;

    /**
     * 广告名
     */
    @Excel(name = "广告名", width = 15)
    private String dealName;

    /**
     * 唯一id
     */
    @Excel(name = "唯一id", width = 15)
    private String uniqueId;

    /**
     * 设备id
     */
    @Excel(name = "设备id", width = 15)
    private String deviceId;

    /**
     * 序列号
     */
    @Excel(name = "序列号", width = 15)
    private String serialId;

    /**
     * androidId
     */
    @Excel(name = "androidId", width = 15)
    private String androidId;

    /**
     * ip
     */
    @Excel(name = "IP", width = 15)
    private String clientIp;

    /**
     * 操作系统
     */
    @Excel(name = "操作系统", width = 15)
    private String devOs;

    /**
     * 系统版本
     */
    @Excel(name = "系统版本", width = 15)
    private String devOsVer;

    /**
     * 设备型号
     */
    @Excel(name = "设备型号", width = 15)
    private String devModel;

    /**
     * 安装包名
     */
    @Excel(name = "安装包名", width = 15)
    private String pkgName;

    /**
     * 安装包版本
     */
    @Excel(name = "安装包版本", width = 15)
    private String pkgVersionCode;

    /**
     * 安装包版本名
     */
    @Excel(name = "安装包版本名", width = 15)
    private String pkgVersionName;

    /**
     * SDK版本
     */
    @Excel(name = "SDK版本", width = 15)
    private String sdkVersion;

    /**
     * 注册时间
     */
    @Excel(name = "注册日期", width = 15)
    private String registerTime;

    /**
     * 登录时间
     */
    @Excel(name = "登录时间", width = 15)
    private String loginTime;

    /**
     * 支付时间
     */
    @Excel(name = "支付时间", width = 15)
    private String payTime;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 15)
    private String createTime;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间", width = 15)
    private String updateTime;
}
