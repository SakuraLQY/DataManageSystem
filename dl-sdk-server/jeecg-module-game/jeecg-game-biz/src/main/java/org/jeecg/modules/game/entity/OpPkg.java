package org.jeecg.modules.game.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @Description: op_pkg
 * @Author: jeecg-boot
 * @Date:   2023-01-12
 * @Version: V1.0
 */
@Data
@TableName("op_pkg")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_pkg对象", description="op_pkg")
public class OpPkg implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**游戏id*/
	@Excel(name = "游戏id", width = 15)
    @ApiModelProperty(value = "游戏id")
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    private java.lang.Integer gameId;
	/**子游戏id*/
	@Excel(name = "子游戏id", width = 15)
    @ApiModelProperty(value = "子游戏id")
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    private java.lang.Integer subGameId;
    /**厂商ID*/
    @Excel(name = "厂商ID", width = 15)
    @ApiModelProperty(value = "厂商ID")
    @Dict(dictTable = "open_gateway.op_vendor", dicText = "vendor_name", dicCode = "id")
    private java.lang.Integer vendorId;
	/**游戏名*/
	@Excel(name = "游戏名", width = 15)
    @ApiModelProperty(value = "游戏名")
    private java.lang.String nickName;
    /**渠道游戏包名字*/
    @Excel(name = "渠道游戏包名字", width = 15)
    @ApiModelProperty(value = "渠道游戏包名字")
    private java.lang.String pkgName;
    /**游戏包名*/
    @Excel(name = "游戏包名", width = 15)
    @ApiModelProperty(value = "游戏包名")
    private java.lang.String packageName;
	/**游戏版本*/
	@Excel(name = "游戏版本", width = 15)
    @ApiModelProperty(value = "游戏版本")
    private java.lang.String versionName;
	/**游戏构建版本*/
	@Excel(name = "游戏构建版本", width = 15)
    @ApiModelProperty(value = "游戏构建版本")
    private java.lang.String versionCode;
	/**下载链接*/
	@Excel(name = "下载链接", width = 15)
    @ApiModelProperty(value = "下载链接")
    private java.lang.String downloadUrl;
	/**渠道id*/
	@Excel(name = "渠道id", width = 15, dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
	/**渠道配置*/
	@Excel(name = "渠道配置", width = 15)
    @ApiModelProperty(value = "渠道配置")
    private java.lang.String channelConf;
	/**icon图打包素材路径*/
	@Excel(name = "icon图打包素材路径", width = 15)
    @ApiModelProperty(value = "icon图打包素材路径")
    private java.lang.String iconMaterialPackPath;
	/**闪屏图打包素材路径*/
	@Excel(name = "闪屏图打包素材路径", width = 15)
    @ApiModelProperty(value = "闪屏图打包素材路径")
    private java.lang.String screenMaterialPackPath;
	/**加载图打包素材路径*/
	@Excel(name = "加载图打包素材路径", width = 15)
    @ApiModelProperty(value = "加载图打包素材路径")
    private java.lang.String loadingMaterialPackPath;
	/**登录图打包素材路径*/
	@Excel(name = "登录图打包素材路径", width = 15)
    @ApiModelProperty(value = "登录图打包素材路径")
    private java.lang.String loginMaterialPackPath;
	/**logo图打包素材路径*/
	@Excel(name = "logo图打包素材路径", width = 15)
    @ApiModelProperty(value = "logo图打包素材路径")
    private java.lang.String logoMaterialPackPath;
	/**打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败*/
    /**素材类型*/
    @Excel(name = "打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败", width = 15, dicCode = "pack_state")
    @Dict(dicCode = "pack_state")
    @ApiModelProperty(value = "打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败")
    private java.lang.Integer packState;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String descs;
    /**隐私政策地址*/
    @ApiModelProperty(value = "隐私政策地址")
    private java.lang.String privacyPolicyUrl;
    /**用户协议地址*/
    @ApiModelProperty(value = "用户协议地址")
    private java.lang.String userAgreementUrl;
    /**客服地址*/
    @ApiModelProperty(value = "客服地址")
    private java.lang.String customerServiceUrl;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private java.lang.String updateBy;
	/**修改日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改日期")
    private java.util.Date updateTime;
    /**删除状态（0：存在，1：已删）*/
    @Excel(name = "删除状态（0：存在，1：已删）", width = 15)
    @ApiModelProperty(value = "删除状态（0：存在，1：已删）")
    private java.lang.Integer status;
    /**是否开启平台币*/
    @Excel(name = "是否开启平台币", width = 15)
    @ApiModelProperty(value = "是否开启平台币")
    @Dict(dicCode = "game_is_open")
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
    /**绑定手机开关*/
    @Excel(name = "绑定手机开关", width = 15)
    @ApiModelProperty(value = "绑定手机开关")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer phoneSwitch;
    /**隐私政策开关*/
    @Excel(name = "隐私政策开关", width = 15)
    @ApiModelProperty(value = "隐私政策开关")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer privacySwitch;
    /**实名验证窗口*/
    @Excel(name = "实名验证窗口", width = 15)
    @ApiModelProperty(value = "实名验证窗口")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer idAuthSwitch;
    /**是否调用接口*/
    @Excel(name = "是否调用接口", width = 15)
    @ApiModelProperty(value = "是否调用接口")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer idAuthApi;
    /**接入中宣部*/
    @Excel(name = "接入中宣部", width = 15)
    @ApiModelProperty(value = "接入中宣部")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer officialAntiIndulgeSwitch;
    /**备案识别码*/
    @Excel(name = "备案识别码", width = 15)
    @ApiModelProperty(value = "备案识别码")
    private java.lang.String officialBizId;
    /**防沉迷开关*/
    @Excel(name = "防沉迷开关", width = 15)
    @ApiModelProperty(value = "防沉迷开关")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer antiIndulgeSwitch;
}
