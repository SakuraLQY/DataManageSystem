package org.jeecg.modules.advert.entity;

import cn.hutool.core.util.RandomUtil;
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
import lombok.NoArgsConstructor;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.PackStateConstant;
import org.jeecg.modules.advert.dto.OpXingtuDropDealDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: op_xingtu_drop_deal
 * @Author: jeecg-boot
 * @Date:   2023-03-07
 * @Version: V1.0
 */
@Data
@TableName("op_xingtu_drop_deal")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value="op_xingtu_drop_deal对象", description="op_xingtu_drop_deal")
public class OpXingtuDropDeal implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private java.lang.Integer id;
	/**广告组ID*/
	@Excel(name = "广告组ID", width = 15)
    @ApiModelProperty(value = "广告组ID")
    private java.lang.Long campaignId;
	/**广告组名称*/
	@Excel(name = "广告组名称", width = 15)
    @ApiModelProperty(value = "广告组名称")
    private java.lang.String campaignName;
	/**主播ID*/
	@Excel(name = "主播ID", width = 15)
    @ApiModelProperty(value = "主播ID")
    private java.lang.Integer anchorPlanId;
	/**游戏ID*/
	@Excel(name = "游戏ID", width = 15)
    @ApiModelProperty(value = "游戏ID")
    private java.lang.Integer gameId;
	/**子游戏ID*/
	@Excel(name = "子游戏ID", width = 15)
    @ApiModelProperty(value = "子游戏ID")
    private java.lang.Integer subGameId;
	/**子游戏类型 0 安卓 1 IOS*/
	@Excel(name = "子游戏类型 0 安卓 1 IOS", width = 15)
    @ApiModelProperty(value = "子游戏类型 0 安卓 1 IOS")
    private java.lang.Integer subGameType;
    /**游戏包ID*/
    @Excel(name = "游戏包ID", width = 15)
    @ApiModelProperty(value = "游戏包ID")
    private java.lang.Integer pkgId;
	/**投放账号ID*/
	@Excel(name = "投放账号ID", width = 15)
    @ApiModelProperty(value = "投放账号ID")
    private java.lang.Integer accountId;
	/**广告参数*/
	@Excel(name = "广告参数", width = 15)
    @ApiModelProperty(value = "广告参数")
    private java.lang.String dealArgs;
	/**广告描述*/
	@Excel(name = "广告描述", width = 15)
    @ApiModelProperty(value = "广告描述")
    private java.lang.String dealDesc;
	/**下载链接*/
	@Excel(name = "下载链接", width = 15)
    @ApiModelProperty(value = "下载链接")
    private java.lang.String downloadUrl;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;

    public OpXingtuDropDeal(OpXingtuDropDealDto opXingtuDropDealDto){
        // 广告名先拼接防止重复
        this.campaignName = opXingtuDropDealDto.getCampaignName() + RandomUtil.randomString(20);
        this.gameId = opXingtuDropDealDto.getGameId();
        this.subGameId = opXingtuDropDealDto.getSubGameId();
        this.pkgId = opXingtuDropDealDto.getPkgId();
        this.dealArgs = opXingtuDropDealDto.getDealArgs();
        this.dealDesc = opXingtuDropDealDto.getDealDesc();
        this.downloadUrl = opXingtuDropDealDto.getDownloadUrl();
        this.anchorPlanId = opXingtuDropDealDto.getAnchorPlanId();
    }
}
