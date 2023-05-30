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
 * @date: 2023/3/7 15:59
 **/
@Data
@ApiModel(value = "星图投放广告信息", description = "星图投放广告信息")
public class OpXingtuDropDealVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "投放广告ID")
    private Integer id;

    @ApiModelProperty(value = "头条广告ID")
    private String campaignId;

    @ApiModelProperty(value = "广告组名称")
    private String campaignName;

    @ApiModelProperty(value = "主播ID")
    private Integer anchorPlanId;

    @ApiModelProperty(value = "主播名称")
    private String anchorPlanName;

    @ApiModelProperty(value = "游戏ID")
    private Integer gameId;

    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @ApiModelProperty(value = "子游戏名")
    private String subGameName;

    @ApiModelProperty(value = "子游戏类型 0 安卓 1 IOS")
    private Integer subGameType;

    @ApiModelProperty(value = "游戏包名")
    private String pkgName;

    @ApiModelProperty(value = "游戏包ID")
    private Integer pkgId;

    @ApiModelProperty(value = "投放账号ID")
    private Integer accountId;

    @ApiModelProperty(value = "投放账号名称")
    private String accountName;

    @ApiModelProperty(value = "广告参数")
    private String dealArgs;

    @ApiModelProperty(value = "广告描述")
    private String dealDesc;

    @ApiModelProperty(value = "下载链接")
    private String downloadUrl;

    @ApiModelProperty(value = "创建人")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    private String createBy;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    private String updateBy;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
}
