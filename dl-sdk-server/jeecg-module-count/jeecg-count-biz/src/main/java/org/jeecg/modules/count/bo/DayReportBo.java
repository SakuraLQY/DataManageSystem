package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="查询ctDaily表中的数据", description="查询ctDaily表中的数据")
public class DayReportBo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**日期*/
    @ApiModelProperty(value = "日期")
    private String timeDaily;
//    /**游戏id*/
//    @ApiModelProperty(value = "游戏id")
//    private java.lang.Integer gameId;
//    /**子游戏id*/
//    @ApiModelProperty(value = "子游戏id")
//    private java.lang.Integer subGameId;
    /**渠道游戏包id*/
    @ApiModelProperty(value = "渠道游戏包id")
    private java.lang.Integer pkgId;
//    /**渠道类型id*/
//    @ApiModelProperty(value = "渠道类型id")
//    private java.lang.Integer channelTypeId;
    /**渠道id*/
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private java.lang.Integer channelSubAccountId;
    /**广告-id*/
    @ApiModelProperty(value = "广告-id")
    private java.lang.Integer dealId;
    /**新增-注册数*/
    @ApiModelProperty(value = "新增-注册数")
    private Integer countUser;
    /**首日-付费人数*/
    @ApiModelProperty(value = "首日-付费人数")
    private Integer firstPayuser;
    /**活跃-付费人数*/
    @ApiModelProperty(value = "活跃-付费人数")
    private Integer alivePayuser;
    /**首日-付费金额*/
    @ApiModelProperty(value = "首日-付费金额")
    private BigDecimal firstMoney;
    /**活跃-付费金额*/
    @ApiModelProperty(value = "活跃-付费金额")
    private BigDecimal aliveMoney;
    /**首日-付费金额-ios*/
    @ApiModelProperty(value = "首日-付费金额-ios")
    private java.math.BigDecimal firstMoneyIos;
    /**活跃-付费金额-ios*/
    @ApiModelProperty(value = "活跃-付费金额-ios")
    private java.math.BigDecimal aliveMoneyIos;


}
