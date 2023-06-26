package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="查询投入成本", description="查询投入成本")
public class CostDataVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**游戏*/
    @ApiModelProperty(value = "游戏")
    private Integer gameId;
    /**子游戏*/
    @ApiModelProperty(value = "子游戏")
    private Integer subGameId;
    /**渠道游戏包*/
    @ApiModelProperty(value = "渠道游戏包")
    private Integer pkgId;
    /**渠道ID*/
    @ApiModelProperty(value = "渠道ID")
    private Integer channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private Integer channelSubAccountId;
    /**广告ID*/
    @ApiModelProperty(value = "广告ID")
    private Integer dealId;
	/**成本日期*/
    @ApiModelProperty(value = "成本日期")
    private String costDay;
	/**成本金额*/
    @ApiModelProperty(value = "成本金额")
    private BigDecimal costMoney = BigDecimal.ZERO;
    /**下载*/
    @ApiModelProperty(value = "下载")
    private Integer download = 0;
    /**展示*/
    @ApiModelProperty(value = "展示")
    private Integer exhibition = 0;
    /**主播id*/
    @ApiModelProperty(value = "主播id")
    private java.lang.String anchorPlanId;
    /**直播间观看人数*/
    @ApiModelProperty(value = "直播间观看人数")
    private java.lang.Integer lubanLiveEnterCnt = 0;
    /**超过1分钟观看数*/
    @ApiModelProperty(value = "超过1分钟观看数")
    private java.lang.Integer liveWatchOneMinuteCount = 0;
    /**直播间打赏次数*/
    @ApiModelProperty(value = "直播间打赏次数")
    private java.lang.Integer lubanLiveGiftCnt = 0;
    /**直播间评论数*/
    @ApiModelProperty(value = "直播间评论数")
    private java.lang.Integer lubanLiveCommentCnt = 0;
    /**直播间关注数*/
    @ApiModelProperty(value = "直播间关注数")
    private java.lang.Integer lubanLiveFollowCnt = 0;
    /**直播间加入粉丝团*/
    @ApiModelProperty(value = "直播间加入粉丝团")
    private java.lang.Integer liveFansClubJoinCnt = 0;
    /**直播间分享数*/
    @ApiModelProperty(value = "直播间分享数")
    private java.lang.Integer lubanLiveShareCnt = 0;
    /**成本平台*/
    @ApiModelProperty(value = "成本平台")
    private Integer platform;
    /**点击*/
    @ApiModelProperty(value = "点击")
    private Integer click = 0;

}
