package org.jeecg.modules.advert.entity;

import cn.hutool.core.util.RandomUtil;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.ChannelTypeConstant;
import org.jeecg.common.constant.PackStateConstant;
import org.jeecg.modules.advert.dto.OpDealDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: ad_deal
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
@TableName("op_deal")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value="op_deal对象", description="op_deal")
public class OpDeal implements Serializable {
    private static final long serialVersionUID = 1L;

	/**广告ID*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "广告ID")
    private java.lang.Integer id;
	/**广告名称*/
	@Excel(name = "广告名称", width = 15)
    @ApiModelProperty(value = "广告名称")
    private java.lang.String dealName;
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
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private java.lang.Integer channelTypeId;
	/**渠道ID*/
	@Excel(name = "渠道ID", width = 15)
    @ApiModelProperty(value = "渠道ID")
    private java.lang.Integer channelId;
	/**渠道子账号ID*/
	@Excel(name = "渠道子账号ID", width = 15)
    @ApiModelProperty(value = "渠道子账号ID")
    private java.lang.Integer channelSubAccountId;
	/**广告参数*/
	@Excel(name = "广告参数", width = 15)
    @ApiModelProperty(value = "广告参数")
    private java.lang.String dealArgs;
	/**广告描述*/
	@Excel(name = "广告描述", width = 15)
    @ApiModelProperty(value = "广告描述")
    private java.lang.String dealDesc;
	/**广告控量参数*/
	@Excel(name = "广告控量参数", width = 15)
    @ApiModelProperty(value = "广告控量参数")
    private java.lang.String controlConf;
	/**二次归因，1为不需要，2为需要*/
	@Excel(name = "二次归因，1为不需要，2为需要", width = 15)
    @ApiModelProperty(value = "二次归因，1为不需要，2为需要")
    private java.lang.Integer twoAtrb;
	/**安装包下载链接*/
	@Excel(name = "安装包下载链接", width = 15)
    @ApiModelProperty(value = "安装包下载链接")
    private java.lang.String pkgUrl;
	/**母包md5*/
	@Excel(name = "母包md5", width = 15)
    @ApiModelProperty(value = "母包md5")
    private java.lang.String pkgMd5;
	/**打包配置*/
	@Excel(name = "打包配置", width = 15)
    @ApiModelProperty(value = "打包配置")
    private java.lang.String packConfig;
	/**打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败*/
	@Excel(name = "打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败", width = 15)
    @ApiModelProperty(value = "打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败")
    private java.lang.Integer packState;
	/**安装包更新时间*/
	@Excel(name = "安装包更新时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "安装包更新时间")
    private java.util.Date pkgUpdateTime;
	/**安装包发布时间*/
	@Excel(name = "安装包发布时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "安装包发布时间")
    private java.util.Date pkgPublishTime;
	/**视频ID*/
	@Excel(name = "视频ID", width = 15)
    @ApiModelProperty(value = "视频ID")
    private java.lang.Integer videoId;
	/**素材编号*/
	@Excel(name = "素材编号", width = 15)
    @ApiModelProperty(value = "素材编号")
    private java.lang.String materialId;
	/**广告cpa单价成本*/
	@Excel(name = "广告cpa单价成本", width = 15)
    @ApiModelProperty(value = "广告cpa单价成本")
    private java.lang.Double cpaCost;
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

    /**
     * @author xmh
     * @description 根据opDealDto生成OpDeal
     * @date 2023/2/15 14:06
     * @param opDealDto: 广告信息
     * @param channelId: 渠道ID
     */
    public OpDeal(OpDealDto opDealDto, Integer channelId) {
        // 广告名先拼接防止重复
        this.dealName = opDealDto.getDealName() + RandomUtil.randomString(20);
        this.gameId = opDealDto.getGameId();
        this.subGameId = opDealDto.getSubGameId();
        this.pkgId = opDealDto.getPkgId();
        this.dealArgs = opDealDto.getDealArgs();
        this.dealDesc = opDealDto.getDealDesc();
        this.channelTypeId = ChannelTypeConstant.MEDIA;
        this.channelId = channelId;
        this.channelSubAccountId = opDealDto.getChannelSubAccountId();
        this.packState = PackStateConstant.WAIT;
        this.pkgUrl = opDealDto.getPkgUrl();
    }
}
