package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @Description: 广告最近七天数据
 * @Author: jeecg-boot
 * @Date:   2023-05-8
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class SummaryDealInfoDataVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**广告最近七天数据*/
    @ApiModelProperty(value = "广告最近七天数据")
    List<SummaryDealSevenDayVo> sevenDay;

    /**同账号广告数据*/
    @ApiModelProperty(value = "同账号广告数据")
    List<SummaryDealSameAccountVo> sameAccount;

    /**渠道游戏包名称*/
    @ApiModelProperty(value = "渠道游戏包名称")
    private String pkgName;

    /**渠道子账号名称*/
    @ApiModelProperty(value = "渠道子账号名称")
    private String subChannelName;

    /**投放账号名*/
    @ApiModelProperty(value = "投放账号名")
    private String accountName;

}
