package org.jeecg.modules.advert.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2023/3/14 15:56
 **/
@Data
@ApiModel(value = "快手广告计划信息", description = "快手广告计划信息")
public class OpKuaishouDealPlanVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告ID")
    private Integer id;

    @ApiModelProperty(value = "广告计划名称")
    private String dealName;

    @ApiModelProperty(value = "游戏名")
    private String gameName;

    @ApiModelProperty(value = "游戏ID")
    private Integer gameId;

    @ApiModelProperty(value = "子游戏名")
    private String subGameName;

    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @ApiModelProperty(value = "游戏包名")
    private String pkgName;

    @ApiModelProperty(value = "游戏包ID")
    private Integer pkgId;

    @ApiModelProperty(value = "子游戏类型")
    private Integer subGameType;

    @ApiModelProperty(value = "投放账号名")
    private String accountName;

    @ApiModelProperty(value = "投放账号ID")
    private Integer accountId;

    @ApiModelProperty(value = "渠道子账号名")
    private String channelSubAccountName;

    @ApiModelProperty(value = "渠道子账号ID")
    private Integer channelSubAccountId;

    @ApiModelProperty(value = "广告参数")
    private String dealArgs;

    @ApiModelProperty(value = "广告描述")
    private String dealDesc;

    @ApiModelProperty(value = "安装包下载链接")
    private String pkgUrl;

    @ApiModelProperty(value = "打包状态")
    private Integer packState;

    @ApiModelProperty(value = "创建人")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "头条广告计划ID")
    private String campaignId;

    @ApiModelProperty(value = "预算类型")
    private Integer budgetMode;

    @ApiModelProperty(value = "计划预算")
    private String budget;
}
