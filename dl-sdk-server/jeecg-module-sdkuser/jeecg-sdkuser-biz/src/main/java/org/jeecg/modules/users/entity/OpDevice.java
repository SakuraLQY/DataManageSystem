package org.jeecg.modules.users.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_device
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@TableName("op_device")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_device对象", description="op_device")
public class OpDevice implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**游戏id*/
	@Excel(name = "游戏id", width = 15)
    @ApiModelProperty(value = "游戏id")
    private java.lang.Integer gameId;
	/**子游戏id*/
	@Excel(name = "子游戏id", width = 15)
    @ApiModelProperty(value = "子游戏id")
    private java.lang.Integer subGameId;
	/**渠道id*/
	@Excel(name = "渠道id", width = 15)
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
	/**渠道类型id*/
	@Excel(name = "渠道类型id", width = 15)
    @ApiModelProperty(value = "渠道类型id")
    private java.lang.Integer channelTypeId;
	/**渠道子账号id*/
	@Excel(name = "渠道子账号id", width = 15)
    @ApiModelProperty(value = "渠道子账号id")
    private java.lang.Integer channelSubAccountId;
	/**渠道游戏包id*/
	@Excel(name = "渠道游戏包id", width = 15)
    @ApiModelProperty(value = "渠道游戏包id")
    private java.lang.Integer pkgId;
	/**广告-id*/
	@Excel(name = "广告-id", width = 15)
    @ApiModelProperty(value = "广告-id")
    private java.lang.Integer dealId;
	/**唯一id*/
	@Excel(name = "唯一id", width = 15)
    @ApiModelProperty(value = "唯一id")
    private java.lang.String uniqueId;
	/**设备号*/
	@Excel(name = "设备号", width = 15)
    @ApiModelProperty(value = "设备号")
    private java.lang.String deviceId;
	/**android_id*/
	@Excel(name = "android_id", width = 15)
    @ApiModelProperty(value = "android_id")
    private java.lang.String androidId;
	/**序列号*/
	@Excel(name = "序列号", width = 15)
    @ApiModelProperty(value = "序列号")
    private java.lang.String serialId;
	/**ip地址*/
	@Excel(name = "ip地址", width = 15)
    @ApiModelProperty(value = "ip地址")
    private java.lang.String clientIp;
	/**操作系统*/
	@Excel(name = "操作系统", width = 15)
    @ApiModelProperty(value = "操作系统")
    private java.lang.String devOs;
	/**操作系统版本*/
	@Excel(name = "操作系统版本", width = 15)
    @ApiModelProperty(value = "操作系统版本")
    private java.lang.String devOsVer;
	/**设备型号*/
	@Excel(name = "设备型号", width = 15)
    @ApiModelProperty(value = "设备型号")
    private java.lang.String devModel;
	/**安装包名*/
	@Excel(name = "安装包名", width = 15)
    @ApiModelProperty(value = "安装包名")
    private java.lang.String pkgName;
	/**安装包版本号*/
	@Excel(name = "安装包版本号", width = 15)
    @ApiModelProperty(value = "安装包版本号")
    private java.lang.String pkgVersionCode;
	/**安装包版本名*/
	@Excel(name = "安装包版本名", width = 15)
    @ApiModelProperty(value = "安装包版本名")
    private java.lang.String pkgVersionName;

    /**
     * sdk版本号
     */
    @Excel(name = "sdk版本号",width = 15)
    @ApiModelProperty(value = "sdk版本号")
    private String sdkVersion;
	/**激活时间*/
	@Excel(name = "激活时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "激活时间")
    private java.util.Date startupTime;
	/**注册时间*/
	@Excel(name = "注册时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册时间")
    private java.util.Date registerTime;
	/**登录时间*/
	@Excel(name = "登录时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "登录时间")
    private java.util.Date loginTime;
	/**充值时间*/
	@Excel(name = "充值时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "充值时间")
    private java.util.Date payTime;
	/**上报时间*/
	@Excel(name = "上报时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "上报时间")
    private java.util.Date aliveTime;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
