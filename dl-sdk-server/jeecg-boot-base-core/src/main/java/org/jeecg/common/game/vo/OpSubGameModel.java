package org.jeecg.common.game.vo;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import lombok.Data;
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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="子游戏信息", description="子游戏信息")
public class OpSubGameModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**子游戏id*/
    private java.lang.Integer id;
    /**游戏ID*/
    private java.lang.Integer gameId;
    /**厂商ID*/
    private java.lang.Integer vendorId;
    /**子游戏名*/
    private java.lang.String gameName;
    /**子游戏类型*/
    private java.lang.Integer gameType;
    /**充值发货url*/
    private java.lang.String deliverUrl;
    /**安装包-下载链接(IOS)*/
    private java.lang.String game2PkgUrl;
    /**是否开启平台币*/
    private java.lang.Integer platformCurrencySwitch;
    /**折扣配置*/
    private java.lang.Integer platformCurrencyDiscount;
    /**包id配置*/
    private java.lang.String platformCurrencyPkgConfig;
    /**包名称*/
    private java.lang.String packName;
    /**绑定手机开关*/
    private java.lang.Integer phoneSwitch;
    /**隐私政策开关*/
    private java.lang.Integer privacySwitch;
    /**实名验证窗口*/
    private java.lang.Integer idAuthSwitch;
    /**是否调用接口*/
    private java.lang.Integer idAuthApi;
    /**接入中宣部*/
    private java.lang.Integer officialAntiIndulgeSwitch;
    /**备案识别码*/
    private java.lang.String officialBizId;
    /**防沉迷开关*/
    private java.lang.Integer antiIndulgeSwitch;
    /**游戏key*/
    private java.lang.String gameKey;
    /**充值公钥*/
    private java.lang.String payPubkey;
    /**充值私钥*/
    private java.lang.String payPrikey;
    /**备注*/
    private java.lang.String descs;
    /**删除状态（0：存在，1：已删）*/
    private java.lang.Integer status;
}
