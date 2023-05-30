package org.jeecg.modules.advert.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2023/3/7 11:28
 **/
@Data
@ApiModel(value = "星图投放广告信息", description = "星图投放广告信息")
public class OpXingtuDropDealDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "生成广告条数")
    private Integer dealNumbers;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "广告组名称")
    private String campaignName;

    @ApiModelProperty(value = "游戏ID")
    private Integer gameId;

    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @ApiModelProperty(value = "游戏包ID")
    private Integer pkgId;

    @ApiModelProperty(value = "投放账号ID")
    private String accountIds;

    @ApiModelProperty(value = "主播")
    private Integer anchorPlanId;

    @ApiModelProperty(value = "广告参数")
    private String dealArgs;

    @ApiModelProperty(value = "广告描述")
    private String dealDesc;

    @ApiModelProperty(value = "安装包下载链接")
    private String downloadUrl;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "广告组状态")
    private String operation;

    @ApiModelProperty(value = "推广目的")
    private String landingType;

    @ApiModelProperty(value = "预算类型")
    private String budgetMode;

    @ApiModelProperty(value = "预算择优分配")
    private String campaignBudgetOptimization;

    @ApiModelProperty(value = "预算金额")
    private Double budget = 0.0;
}
