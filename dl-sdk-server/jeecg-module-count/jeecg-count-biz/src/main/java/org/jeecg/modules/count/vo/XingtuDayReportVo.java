package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class XingtuDayReportVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**日期*/
    @Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private String roiDate;
	/**总消耗*/
    @Excel(name = "总消耗", width = 15)
    @ApiModelProperty(value = "总消耗")
    private BigDecimal costMoney=BigDecimal.ZERO;
    /**安卓消耗*/
    @Excel(name = "安卓消耗", width = 15)
    @ApiModelProperty(value = "安卓消耗")
    private BigDecimal androidCostMoney=BigDecimal.ZERO;
    /**IOS消耗*/
    @Excel(name = "IOS消耗", width = 15)
    @ApiModelProperty(value = "IOS消耗")
    private BigDecimal iosCostMoney=BigDecimal.ZERO;
    /**曝光量*/
    @Excel(name = "曝光量", width = 15)
    @ApiModelProperty(value = "曝光量")
    private Integer exhibition=0;
    /**点击量*/
    @Excel(name = "点击量", width = 15)
    @ApiModelProperty(value = "点击量")
    private java.lang.Integer click=0;
    /**点击率*/
    @Excel(name = "点击率", width = 15)
    @ApiModelProperty(value = "点击率")
    private BigDecimal clickRate=BigDecimal.ZERO;
    /**点击均价*/
    @Excel(name = "点击均价", width = 15)
    @ApiModelProperty(value = "点击均价")
    private BigDecimal clickPrice=BigDecimal.ZERO;
    /**下载*/
    @Excel(name = "下载", width = 15)
    @ApiModelProperty(value = "下载")
    private java.lang.Integer download=0;
    /**点击下载率*/
    @Excel(name = "点击下载率", width = 15)
    @ApiModelProperty(value = "点击下载率")
    private BigDecimal downloadRate=BigDecimal.ZERO;
    /**下载均价*/
    @Excel(name = "下载均价", width = 15)
    @ApiModelProperty(value = "下载均价")
    private BigDecimal downloadPrice=BigDecimal.ZERO;
    /**激活量*/
    @Excel(name = "激活量", width = 15)
    @ApiModelProperty(value = "激活量")
    private java.lang.Integer countActive=0;
    /**注册量*/
    @Excel(name = "注册量", width = 15)
    @ApiModelProperty(value = "注册量")
    private java.lang.Integer countUser=0;
    /**下载注册率*/
    @Excel(name = "下载注册率", width = 15)
    @ApiModelProperty(value = "下载注册率")
    private BigDecimal downloadRegRate=BigDecimal.ZERO;
    /**激活注册率*/
    @Excel(name = "激活注册率", width = 15)
    @ApiModelProperty(value = "激活注册率")
    private BigDecimal activeRegRate=BigDecimal.ZERO;
    /**注册单价*/
    @Excel(name = "注册单价", width = 15)
    @ApiModelProperty(value = "注册单价")
    private BigDecimal regUnitPrice=BigDecimal.ZERO;
    /**新增付费数*/
    @Excel(name = "新增付费数", width = 15)
    @ApiModelProperty(value = "新增付费数")
    private Integer costCount=0;
    /**新增付费率*/
    @Excel(name = "新增付费率", width = 15)
    @ApiModelProperty(value = "新增付费率")
    private BigDecimal costProbability=BigDecimal.ZERO;
    /**新增付费*/
    @Excel(name = "新增付费", width = 15)
    @ApiModelProperty(value = "新增付费")
    private BigDecimal addCostPrice=BigDecimal.ZERO;
    /**总消费*/
    @Excel(name = "总消费", width = 15)
    @ApiModelProperty(value = "总消费")
    private BigDecimal aliveMoney=BigDecimal.ZERO;
    /**付费单价*/
    @Excel(name = "付费单价", width = 15)
    @ApiModelProperty(value = "付费单价")
    private BigDecimal costUnitPrice=BigDecimal.ZERO;
    /**新增ARPU*/
    @Excel(name = "新增ARPU", width = 15)
    @ApiModelProperty(value = "新增ARPU")
    private java.math.BigDecimal firstArpu=BigDecimal.ZERO;
    /**首日ROI*/
    @Excel(name = "首日ROI", width = 15)
    @ApiModelProperty(value = "首日ROI")
    private BigDecimal firstROI=BigDecimal.ZERO;
    /**回收率*/
    @Excel(name = "回收率", width = 15)
    @ApiModelProperty(value = "回收率")
    private BigDecimal recoveryRate=BigDecimal.ZERO;
    /**直播间观看人数*/
    @Excel(name = "直播间观看人数", width = 15)
    @ApiModelProperty(value = "直播间观看人数")
    private Integer lubanLiveEnterCnt=0;
    /**超过1分钟观看数*/
    @Excel(name = "超过1分钟观看数", width = 15)
    @ApiModelProperty(value = "超过1分钟观看数")
    private Integer liveWatchOneMinuteCount=0;
    /**直播间打赏次数*/
    @Excel(name = "直播间打赏次数", width = 15)
    @ApiModelProperty(value = "直播间打赏次数")
    private Integer lubanLiveGiftCnt=0;
    /**直播间评论数*/
    @Excel(name = "直播间评论数", width = 15)
    @ApiModelProperty(value = "直播间评论数")
    private Integer lubanLiveCommentCnt=0;
    /**直播间关注数*/
    @Excel(name = "直播间关注数", width = 15)
    @ApiModelProperty(value = "直播间关注数")
    private Integer lubanLiveFollowCnt=0;
    /**直播间加入粉丝团*/
    @Excel(name = "直播间加入粉丝团", width = 15)
    @ApiModelProperty(value = "直播间加入粉丝团")
    private Integer liveFansClubJoinCnt=0;
    /**直播间分享数*/
    @Excel(name = "直播间分享数", width = 15)
    @ApiModelProperty(value = "直播间分享数")
    private Integer lubanLiveShareCnt=0;

}
