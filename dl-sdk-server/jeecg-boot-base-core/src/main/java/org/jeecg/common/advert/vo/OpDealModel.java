package org.jeecg.common.advert.vo;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.PackStateConstant;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

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
public class OpDealModel implements Serializable {
    private static final long serialVersionUID = 1L;

	/**广告ID*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "广告ID")
    private Integer id;
	/**广告名称*/
	@Excel(name = "广告名称", width = 15)
    @ApiModelProperty(value = "广告名称")
    private String dealName;
	/**游戏ID*/
	@Excel(name = "游戏ID", width = 15)
    @ApiModelProperty(value = "游戏ID")
    private Integer gameId;
	/**子游戏ID*/
	@Excel(name = "子游戏ID", width = 15)
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;
	/**子游戏类型 0 安卓 1 IOS*/
	@Excel(name = "子游戏类型 0 安卓 1 IOS", width = 15)
    @ApiModelProperty(value = "子游戏类型 0 安卓 1 IOS")
    private Integer subGameType;
	/**游戏包ID*/
	@Excel(name = "游戏包ID", width = 15)
    @ApiModelProperty(value = "游戏包ID")
    private Integer pkgId;
	/**投放账号ID*/
	@Excel(name = "投放账号ID", width = 15)
    @ApiModelProperty(value = "投放账号ID")
    private Integer accountId;
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private Integer channelTypeId;
	/**渠道ID*/
	@Excel(name = "渠道ID", width = 15)
    @ApiModelProperty(value = "渠道ID")
    private Integer channelId;
	/**渠道子账号ID*/
	@Excel(name = "渠道子账号ID", width = 15)
    @ApiModelProperty(value = "渠道子账号ID")
    private Integer channelSubAccountId;
	/**广告参数*/
	@Excel(name = "广告参数", width = 15)
    @ApiModelProperty(value = "广告参数")
    private String dealArgs;
	/**广告描述*/
	@Excel(name = "广告描述", width = 15)
    @ApiModelProperty(value = "广告描述")
    private String dealDesc;
	/**广告控量参数*/
	@Excel(name = "广告控量参数", width = 15)
    @ApiModelProperty(value = "广告控量参数")
    private String controlConf;
	/**二次归因，1为不需要，2为需要*/
	@Excel(name = "二次归因，1为不需要，2为需要", width = 15)
    @ApiModelProperty(value = "二次归因，1为不需要，2为需要")
    private Integer twoAtrb;
	/**安装包下载链接*/
	@Excel(name = "安装包下载链接", width = 15)
    @ApiModelProperty(value = "安装包下载链接")
    private String pkgUrl;
	/**母包md5*/
	@Excel(name = "母包md5", width = 15)
    @ApiModelProperty(value = "母包md5")
    private String pkgMd5;
	/**打包配置*/
	@Excel(name = "打包配置", width = 15)
    @ApiModelProperty(value = "打包配置")
    private String packConfig;
	/**打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败*/
	@Excel(name = "打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败", width = 15)
    @ApiModelProperty(value = "打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败")
    private Integer packState;
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
    private Integer videoId;
	/**素材编号*/
	@Excel(name = "素材编号", width = 15)
    @ApiModelProperty(value = "素材编号")
    private String materialId;
	/**广告cpa单价成本*/
	@Excel(name = "广告cpa单价成本", width = 15)
    @ApiModelProperty(value = "广告cpa单价成本")
    private Double cpaCost;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;

}
