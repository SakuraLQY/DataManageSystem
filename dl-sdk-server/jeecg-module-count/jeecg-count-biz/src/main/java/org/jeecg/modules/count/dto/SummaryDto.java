package org.jeecg.modules.count.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: SummaryDto
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给后端对象", description="传给后端对象")
public class SummaryDto implements Serializable {
    private static final long serialVersionUID = 1L;

	/**游戏*/
    @ApiModelProperty(value = "游戏")
    private List<Integer> gameId;
	/**子游戏*/
    @ApiModelProperty(value = "子游戏")
    private List<Integer> subGameId;
	/**渠道游戏包*/
    @ApiModelProperty(value = "渠道游戏包")
    private List<Integer> pkgId;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    private List<Integer> channelTypeId;
    /**渠道id*/
    @ApiModelProperty(value = "渠道id")
    private List<Integer> channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private List<Integer> channelSubAccountId;
	/**广告ID*/
    @ApiModelProperty(value = "广告ID")
    private List<Integer> dealId;
    /**投放账号ID*/
    @ApiModelProperty(value = "投放账号id")
    private List<Integer> accountId;
    /**注册开始日期*/
    @ApiModelProperty(value = "注册开始日期")
    private String regStartTime;
    /**注册结束日期*/
    @ApiModelProperty(value = "注册结束日期")
    private String regEndTime;
    /**注册开始日期*/
    @ApiModelProperty(value = "付费开始日期")
    private String payStartTime;
    /**注册结束日期*/
    @ApiModelProperty(value = "付费结束日期")
    private String payEndTime;
    /**归类方式*/
    @ApiModelProperty(value = "归类方式")
    private Integer groupType;
    /**成本平台*/
    @ApiModelProperty(value = "成本平台")
    private String costPlatform;
    /**展示平台币*/
    @ApiModelProperty(value = "展示平台币")
    private String showPlatform;
    /**账号人员*/
    @ApiModelProperty(value = "账号人员")
    private String createUser;
}
