package org.jeecg.modules.game.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_pack_material
 * @Author: chenyw
 * @Date: 2023-01-31
 * @Version: V1.0
 */
@Data
@ApiModel(value = "一级游戏包对象", description = "一级游戏包对象")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OpPkgVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**id*/
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**游戏id*/
    @ApiModelProperty(value = "游戏id")
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    private java.lang.Integer gameId;
    /**子游戏id*/
    @ApiModelProperty(value = "子游戏id")
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    private java.lang.Integer subGameId;
    @ApiModelProperty(value = "游戏名")
    private String gameName;
    @ApiModelProperty(value = "子游戏名")
    private String subGameName;
    /**厂商ID*/
    @ApiModelProperty(value = "厂商ID")
    @Dict(dictTable = "open_gateway.op_vendor", dicText = "vendor_name", dicCode = "id")
    private java.lang.Integer vendorId;
    /**渠道游戏包名字*/
    @ApiModelProperty(value = "渠道游戏包名字")
    private java.lang.String pkgName;
    /**游戏名*/
    @ApiModelProperty(value = "应用名")
    private java.lang.String nickName;
    /**游戏包名*/
    @ApiModelProperty(value = "游戏包名")
    private java.lang.String packageName;
    /**游戏版本*/
    @ApiModelProperty(value = "游戏版本")
    private java.lang.String versionName;
    /**游戏构建版本*/
    @ApiModelProperty(value = "游戏构建版本")
    private java.lang.String versionCode;
    /**下载链接*/
    @ApiModelProperty(value = "下载链接")
    private java.lang.String downloadUrl;
    /**渠道id*/
    @Dict(dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
    /**渠道配置*/
    @ApiModelProperty(value = "渠道配置")
    private java.lang.String channelConf;
    /**icon图打包素材路径*/
    @ApiModelProperty(value = "icon图打包素材路径")
    private java.lang.String iconMaterialPackPath;
    /**闪屏图打包素材路径*/
    @ApiModelProperty(value = "闪屏图打包素材路径")
    private java.lang.String screenMaterialPackPath;
    /**加载图打包素材路径*/
    @ApiModelProperty(value = "加载图打包素材路径")
    private java.lang.String loadingMaterialPackPath;
    /**登录图打包素材路径*/
    @ApiModelProperty(value = "登录图打包素材路径")
    private java.lang.String loginMaterialPackPath;
    /**logo图打包素材路径*/
    @ApiModelProperty(value = "logo图打包素材路径")
    private java.lang.String logoMaterialPackPath;
    /**打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败*/
    /**素材类型*/
    @Dict(dicCode = "pack_state")
    @ApiModelProperty(value = "打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败")
    private java.lang.Integer packState;
    /**备注*/
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
    /**本地下载路径*/
    @ApiModelProperty(value = "本地下载路径")
    private java.lang.String apkPath;
    /**是否开启平台币*/
    @ApiModelProperty(value = "是否开启平台币")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer platformCurrencySwitch;
    /**折扣配置*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "折扣配置")
    private java.lang.Integer platformCurrencyDiscount;
    /**包id配置*/
    @ApiModelProperty(value = "包id配置")
    private java.lang.String platformCurrencyPkgConfig;
    /**绑定手机开关*/
    @ApiModelProperty(value = "绑定手机开关")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer phoneSwitch;
    /**隐私政策开关*/
    @ApiModelProperty(value = "隐私政策开关")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer privacySwitch;
    /**实名验证窗口*/
    @ApiModelProperty(value = "实名验证窗口")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer idAuthSwitch;
    /**是否调用接口*/
    @ApiModelProperty(value = "是否调用接口")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer idAuthApi;
    /**接入中宣部*/
    @ApiModelProperty(value = "接入中宣部")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer officialAntiIndulgeSwitch;
    /**备案识别码*/
    @ApiModelProperty(value = "备案识别码")
    private java.lang.String officialBizId;
    /**防沉迷开关*/
    @ApiModelProperty(value = "防沉迷开关")
    @Dict(dicCode = "game_is_open")
    private java.lang.Integer antiIndulgeSwitch;
}
