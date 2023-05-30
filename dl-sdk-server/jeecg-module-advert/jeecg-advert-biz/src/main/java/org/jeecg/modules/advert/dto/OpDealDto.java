package org.jeecg.modules.advert.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2023/2/14 11:22
 **/
@Data
@ApiModel(value = "广告基本信息", description = "广告基本信息")
public class OpDealDto implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "生成广告条数")
    private Integer dealNumbers;

    @ApiModelProperty(value = "广告ID")
    private Integer id;

    @ApiModelProperty(value = "广告名称")
    private String dealName;

    @ApiModelProperty(value = "游戏ID")
    private Integer gameId;

    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @ApiModelProperty(value = "游戏包ID")
    private Integer pkgId;

    @ApiModelProperty(value = "投放账号ID")
    private String accountIds;

    @ApiModelProperty(value = "渠道子账号ID")
    private Integer channelSubAccountId;

    @ApiModelProperty(value = "广告参数")
    private String dealArgs;

    @ApiModelProperty(value = "广告描述")
    private String dealDesc;

    @ApiModelProperty(value = "安装包下载链接")
    private String pkgUrl;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
