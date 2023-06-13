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
public class RecoveryVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**日期*/
    @Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private String roiDate;
	/**游戏*/
    @Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private String gameName;
	/**渠道*/
    @Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private String channelName;
    /**流水*/
    @Excel(name = "流水", width = 15)
    @ApiModelProperty(value = "流水")
    private BigDecimal aliveMoney;
    /**分成金额*/
    @Excel(name = "分成金额", width = 15)
    @ApiModelProperty(value = "分成金额")
    private BigDecimal shareMoney;
    /**推广费用*/
    @Excel(name = "推广费用", width = 15)
    @ApiModelProperty(value = "推广费用")
    private BigDecimal promotionCost;
    /**回收金额*/
    @Excel(name = "回收金额", width = 15)
    @ApiModelProperty(value = "回收金额")
    private BigDecimal recoveryMoney;
    /**游戏名*/
    @ApiModelProperty(value = "游戏名")
    private String gameNameText;
    /**子游戏*/
    @ApiModelProperty(value = "子游戏")
    private String subGameNameText;
    /**渠道游戏包*/
    @ApiModelProperty(value = "渠道游戏包")
    private String pkgNameText;
    /**渠道*/
    @ApiModelProperty(value = "渠道")
    private String channelNameText;
    /**渠道类型*/
    @ApiModelProperty(value = "渠道类型")
    private String channelTypeNameText;
    /**渠道子账号*/
    @ApiModelProperty(value = "渠道子账号")
    private String subChannelNameText;

}
