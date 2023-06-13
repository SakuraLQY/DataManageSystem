package org.jeecg.modules.count.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 账号报表EXCEL模型类
 * @author: chenglin
 * @date: 2023年05月30日 16:28
 */
@Data
public class ReportAccountModal implements Serializable {
    private static final long serialVersionUID = 1L;
    /**账号昵称*/
    @Excel(name = "账号昵称", width = 15)
    @ApiModelProperty(value = "账号昵称")
    private String nickName;
    /**账号*/
    @Excel(name = "账号", width = 15)
    @ApiModelProperty(value = "账号")
    private String account;
    /**游戏*/
    @Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private String subGameIds;
    /**渠道*/
    @Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private String channelName;
    /**投放消耗*/
    @Excel(name = "投放消耗", width = 15)
    @ApiModelProperty(value = "投放消耗")
    private BigDecimal outCostMoney;
    /**账号余额*/
    @Excel(name = "账号余额", width = 15)
    @ApiModelProperty(value = "账号余额")
    private BigDecimal surplusAmount;
    /**曝光*/
    @Excel(name = "曝光", width = 15)
    @ApiModelProperty(value = "曝光")
    private BigDecimal exhibition;
    /**下载*/
    @Excel(name = "下载", width = 15)
    @ApiModelProperty(value = "下载")
    private BigDecimal download ;
    /**负责人*/
    @Excel(name = "负责人", width = 15)
    @ApiModelProperty(value = "负责人")
    private String principalUser;
    /**创建时间*/
    @Excel(name = "创建时间", width = 15,format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**更新时间*/
    @Excel(name = "更新时间", width = 15,format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
