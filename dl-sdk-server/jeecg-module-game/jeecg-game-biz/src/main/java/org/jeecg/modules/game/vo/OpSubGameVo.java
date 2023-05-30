package org.jeecg.modules.game.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_son_game
 * @Author: jeecg-boot
 * @Date: 2022-12-09
 * @Version: V1.0
 */
@Data
@TableName("op_sub_game")
@Accessors(chain = true)

@ApiModel(value = "op_sub_game对象", description = "op_sub_game")
public class OpSubGameVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "子游戏id")
    private Integer id;

    @ApiModelProperty(value = "游戏id")
    private Integer gameId;

    @ApiModelProperty(value = "游戏名")
    private String faGameName;

    @ApiModelProperty(value = "厂商ID")
    private Integer vendorId;

    @ApiModelProperty(value = "厂商")
    private String vendorName;

    @ApiModelProperty(value = "子游戏名")
    private String gameName;

    @ApiModelProperty(value = "子游戏类型")
    private String gameType;

    @ApiModelProperty(value = "充值发货url")
    private String deliverUrl;

    @ApiModelProperty(value = "安装包-下载链接(IOS)")
    private String game2PkgUrl;

    @ApiModelProperty(value = "是否开启平台币")
    private String platformCurrencySwitch;

    @ApiModelProperty(value = "折扣配置")
    private Integer platformCurrencyDiscount;

    @ApiModelProperty(value = "包id配置")
    private String platformCurrencyPkgConfig;

    @ApiModelProperty(value = "包名称")
    private String packName;

    @ApiModelProperty(value = "绑定手机开关")
    private String phoneSwitch;

    @ApiModelProperty(value = "隐私政策开关")
    private String privacySwitch;

    @ApiModelProperty(value = "实名验证窗口")
    private String idAuthSwitch;

    @ApiModelProperty(value = "是否调用接口")
    private String idAuthApi;

    @ApiModelProperty(value = "接入中宣部")
    private String officialAntiIndulgeSwitch;

    @ApiModelProperty(value = "备案识别码")
    private String  officialBizId;

    @ApiModelProperty(value = "防沉迷开关")
    private String antiIndulgeSwitch;

    @ApiModelProperty(value = "游戏key")
    private String gameKey;
    
    @ApiModelProperty(value = "充值公钥")
    private String payPubkey;

    @ApiModelProperty(value = "充值私钥")
    private String payPrikey;
    @ApiModelProperty(value = "备注")
    private String descs;

    @ApiModelProperty(value = "参数")
    private String conf;

    @ApiModelProperty(value = "删除状态")
    private Integer status;

    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
}
