package org.jeecg.common.kafka.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 解析激活dto
 * @Author: chenyw
 * @Date: 2022-04-06
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ParseStartDto implements Serializable {

    /**
     * 子游戏Id
     */
    private Integer subGameId;

    /**
     *  游戏Id
     */
    private Integer gameId;

    /**
     *  渠道Id
     */
    private Integer channelId;

    /**
     *  渠道类型Id
     */
    private Integer channelTypeId;

    /**
     *  渠道子账号Id
     */
    private Integer channelSubAccountId;

    /**
     * 广告id
     */
    private Integer dealId;

    /**
     * 一级游戏包ID
     */
    private Integer pkgId;

    /**
     * 唯一设备标识
     */
    private String uniqueId;

    /**
     * 设备号
     */
    private String deviceId;

    /**
     * 安卓id
     */
    private String androidId;

    /**
     * 序列号
     */
    private String serialId;

    /**
     * 操作系统
     */
    private String devOs;

    /**
     * 操作系统版本
     */
    private String devOsVer;

    /**
     * 设备型号
     */
    private String devModel;

    /**
     * 游戏应用包名
     */
    private String pkgName;

    /**
     * 游戏应用包版本号
     */
    private String pkgVersionCode;

    /**
     * 游戏应用包版本名
     */
    private String pkgVersionName;

    /**
     * sdk版本
     */
    private String sdkVersion;

    /**
     * 时间戳(秒)
     */
    private Long time;

    /**
     * 设备ip
     */
    private String clientIp;

    /**
     * 是否第一次激活
     */
    private Integer firstActive;

}
