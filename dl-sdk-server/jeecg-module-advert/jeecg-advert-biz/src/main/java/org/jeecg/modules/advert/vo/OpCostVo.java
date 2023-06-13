package org.jeecg.modules.advert.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author chenyw
 * @version V1.0
 * @description:
 * @date: 2023/3/7 14:51
 **/
@Data
@AllArgsConstructor
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class OpCostVo {

    private static final long serialVersionUID = 1L;

    /**id*/
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
    /**渠道游戏包id*/
    @Excel(name = "渠道游戏包id", width = 15)
    @ApiModelProperty(value = "渠道游戏包id")
    private java.lang.Integer pkgId;
    /**渠道类型id*/
    @Excel(name = "渠道类型id", width = 15)
    @ApiModelProperty(value = "渠道类型id")
    private java.lang.Integer channelTypeId;
    /**渠道id*/
    @Excel(name = "渠道id", width = 15)
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
    /**渠道子账号id*/
    @Excel(name = "渠道子账号id", width = 15)
    @ApiModelProperty(value = "渠道子账号id")
    private java.lang.Integer channelSubAccountId;
    /**广告id*/
    @Excel(name = "广告id", width = 15)
    @ApiModelProperty(value = "广告id")
    private java.lang.Integer dealId;
    /**渠道广告id*/
    @Excel(name = "渠道广告id", width = 15)
    @ApiModelProperty(value = "渠道广告id")
    private java.lang.Long channelDealId;
    /**成本日期 */
    @Excel(name = "成本日期 ", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "成本日期 ")
    private java.util.Date costDay;
    /**成本金额*/
    @Excel(name = "成本金额", width = 15)
    @ApiModelProperty(value = "成本金额")
    private java.math.BigDecimal costMoney;
    /**下载*/
    @Excel(name = "下载", width = 15)
    @ApiModelProperty(value = "下载")
    private java.lang.Integer download;
    /**点击*/
    @Excel(name = "点击", width = 15)
    @ApiModelProperty(value = "点击")
    private java.lang.Integer click;
    /**展示*/
    @Excel(name = "展示", width = 15)
    @ApiModelProperty(value = "展示")
    private java.lang.Integer exhibition;
    /**投放账号id*/
    @Excel(name = "投放账号id", width = 15)
    @ApiModelProperty(value = "投放账号id")
    private java.lang.Integer accountId;
    /**成本平台，0表示全部平台，1为安卓，2为ios*/
    @Dict(dicCode = "cost_platform_item")
    @Excel(name = "成本平台，0表示全部平台，1为安卓，2为ios", width = 15)
    @ApiModelProperty(value = "成本平台，0表示全部平台，1为安卓，2为ios")
    private java.lang.Integer platform;
    /**成本-描述*/
    @Excel(name = "成本-描述", width = 15)
    @ApiModelProperty(value = "成本-描述")
    private java.lang.String costDesc;
    /**创建-时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建-时间")
    private java.util.Date createTime;
    /**更新-时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新-时间")
    private java.util.Date updateTime;
    /**游戏*/
    @Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private java.lang.String gameName;
    /**子游戏*/
    @Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    private java.lang.String subGameName;
    /**渠道游戏包*/
    @Excel(name = "渠道游戏包", width = 15)
    @ApiModelProperty(value = "渠道游戏包")
    private java.lang.String pkgName;
    /**渠道*/
    @Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private java.lang.String channelName;
    /**渠道子账号id*/
    @Excel(name = "渠道子账号", width = 15)
    @ApiModelProperty(value = "渠道子账号")
    private java.lang.String channelSubAccountName;
    /**广告*/
    @Excel(name = "广告", width = 15)
    @ApiModelProperty(value = "广告")
    private java.lang.String dealName;
    /**投放账号*/
    @Excel(name = "投放账号", width = 15)
    @ApiModelProperty(value = "广告")
    private java.lang.String accountName;

}
