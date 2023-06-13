package org.jeecg.modules.advert.entity;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: op_cost
 * @Author: jeecg-boot
 * @Date:   2023-04-27
 * @Version: V1.0
 */
@Data
@TableName("op_cost")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_cost对象", description="op_cost")
public class OpCost implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
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
	@Excel(name = "成本平台，0表示全部平台，1为安卓，2为ios", width = 15)
    @ApiModelProperty(value = "成本平台，0表示全部平台，1为安卓，2为ios")
    private java.lang.Integer platform;
	/**成本-描述*/
	@Excel(name = "成本-描述", width = 15)
    @ApiModelProperty(value = "成本-描述")
    private java.lang.String costDesc;
    /**主播id*/
    @Excel(name = "主播id", width = 15)
    @ApiModelProperty(value = "主播id")
    private java.lang.String anchorPlanId;
    @Excel(name = "直播间观看人数", width = 15)
    @ApiModelProperty(value = "直播间观看人数")
    private java.lang.Integer lubanLiveEnterCnt;
    /**超过1分钟观看数*/
    @Excel(name = "超过1分钟观看数", width = 15)
    @ApiModelProperty(value = "超过1分钟观看数")
    private java.lang.Integer liveWatchOneMinuteCount;
    /**直播间打赏次数*/
    @Excel(name = "直播间打赏次数", width = 15)
    @ApiModelProperty(value = "直播间打赏次数")
    private java.lang.Integer lubanLiveGiftCnt;
    /**直播间评论数*/
    @Excel(name = "直播间评论数", width = 15)
    @ApiModelProperty(value = "直播间评论数")
    private java.lang.Integer lubanLiveCommentCnt;
    /**直播间关注数*/
    @Excel(name = "直播间关注数", width = 15)
    @ApiModelProperty(value = "直播间关注数")
    private java.lang.Integer lubanLiveFollowCnt;
    /**直播间加入粉丝团*/
    @Excel(name = "直播间加入粉丝团", width = 15)
    @ApiModelProperty(value = "直播间加入粉丝团")
    private java.lang.Integer liveFansClubJoinCnt;
    /**直播间分享数*/
    @Excel(name = "直播间分享数", width = 15)
    @ApiModelProperty(value = "直播间分享数")
    private java.lang.Integer lubanLiveShareCnt;
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
}
