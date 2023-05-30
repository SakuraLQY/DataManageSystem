package org.jeecg.modules.count.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Date;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 展示给前端的数据信息
 * @author: chenglin
 * @date: 2023年05月22日 16:06
 */
@Data
@ApiModel(value="op_report对象", description="返回给前端的数据")
public class ReportAccountVo implements Serializable {
    private Integer id;
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
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**更新时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    /**账单*/
    @Excel(name = "账单", width = 15)
    @ApiModelProperty(value = "账单")
    private String bill;
}
