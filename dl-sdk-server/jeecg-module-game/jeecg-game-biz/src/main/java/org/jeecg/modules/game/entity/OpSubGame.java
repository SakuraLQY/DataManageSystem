package org.jeecg.modules.game.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: op_son_game
 * @Author: jeecg-boot
 * @Date:   2022-12-09
 * @Version: V1.0
 */
@Data
@TableName("op_sub_game")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_sub_game对象", description="op_sub_game")
public class OpSubGame implements Serializable {
    private static final long serialVersionUID = 1L;

	/**子游戏id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "子游戏id")
    private java.lang.Integer id;
	/**游戏ID*/
	@Excel(name = "游戏ID", width = 15)
    @ApiModelProperty(value = "游戏ID")
    private java.lang.Integer gameId;
	/**厂商ID*/
	@Excel(name = "厂商ID", width = 15)
    @ApiModelProperty(value = "厂商ID")
    private java.lang.Integer vendorId;
	/**子游戏名*/
	@Excel(name = "子游戏名", width = 15)
    @ApiModelProperty(value = "子游戏名")
    private java.lang.String gameName;
	/**子游戏类型*/
	@Excel(name = "子游戏类型", width = 15)
    @Dict(dicCode = "sub_game_type")
    @ApiModelProperty(value = "子游戏类型")
    private java.lang.Integer gameType;
	/**充值发货url*/
	@Excel(name = "充值发货url", width = 15)
    @ApiModelProperty(value = "充值发货url")
    private java.lang.String deliverUrl;
	/**安装包-下载链接(IOS)*/
	@Excel(name = "安装包-下载链接(IOS)", width = 15)
    @ApiModelProperty(value = "安装包-下载链接(IOS)")
    private java.lang.String game2PkgUrl;
	/**是否开启平台币*/
	@Excel(name = "是否开启平台币", width = 15)
    @ApiModelProperty(value = "是否开启平台币")
    private java.lang.Integer platformCurrencySwitch;
	/**折扣配置*/
	@Excel(name = "折扣配置", width = 15)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "折扣配置")
    private java.lang.Integer platformCurrencyDiscount;
	/**包id配置*/
	@Excel(name = "包id配置", width = 15)
    @ApiModelProperty(value = "包id配置")
    private java.lang.String platformCurrencyPkgConfig;
	/**包名称*/
	@Excel(name = "包名称", width = 15)
    @ApiModelProperty(value = "包名称")
    private java.lang.String packName;
	/**绑定手机开关*/
	@Excel(name = "绑定手机开关", width = 15)
    @ApiModelProperty(value = "绑定手机开关")
    private java.lang.Integer phoneSwitch;
	/**隐私政策开关*/
	@Excel(name = "隐私政策开关", width = 15)
    @ApiModelProperty(value = "隐私政策开关")
    private java.lang.Integer privacySwitch;
	/**实名验证窗口*/
	@Excel(name = "实名验证窗口", width = 15)
    @ApiModelProperty(value = "实名验证窗口")
    private java.lang.Integer idAuthSwitch;
	/**是否调用接口*/
	@Excel(name = "是否调用接口", width = 15)
    @ApiModelProperty(value = "是否调用接口")
    private java.lang.Integer idAuthApi;
	/**接入中宣部*/
	@Excel(name = "接入中宣部", width = 15)
    @ApiModelProperty(value = "接入中宣部")
    private java.lang.Integer officialAntiIndulgeSwitch;
	/**备案识别码*/
	@Excel(name = "备案识别码", width = 15)
    @ApiModelProperty(value = "备案识别码")
    private java.lang.String officialBizId;
	/**防沉迷开关*/
	@Excel(name = "防沉迷开关", width = 15)
    @ApiModelProperty(value = "防沉迷开关")
    private java.lang.Integer antiIndulgeSwitch;
	/**游戏key*/
	@Excel(name = "游戏key", width = 15)
    @ApiModelProperty(value = "游戏key")
    private java.lang.String gameKey;
	/**充值公钥*/
	@Excel(name = "充值公钥", width = 15)
    @ApiModelProperty(value = "充值公钥")
    private java.lang.String payPubkey;
	/**充值私钥*/
	@Excel(name = "充值私钥", width = 15)
    @ApiModelProperty(value = "充值私钥")
    private java.lang.String payPrikey;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String descs;
	/**删除状态（0：存在，1：已删）*/
	@Excel(name = "删除状态（0：存在，1：已删）", width = 15)
    @ApiModelProperty(value = "删除状态（0：存在，1：已删）")
    private java.lang.Integer status;
    /**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
}
