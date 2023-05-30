package org.jeecg.modules.count.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 接受前端传来的参数对象
 * @author: chenglin
 * @date: 2023年05月17日 15:30
 */
@Data
public class RetainAnalyzeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**游戏名*/
    @ApiModelProperty(value = "游戏名")
    private List<Integer> gameId;
    /**游戏名称*/
    @ApiModelProperty(value = "游戏名称")
    private List<Integer> subGameId;
    /**游戏包名*/
    @ApiModelProperty(value = "游戏包名")
    private List<Integer> pkgId;
    /**渠道类型*/
    @ApiModelProperty(value = "渠道类型")
    private List<Integer> channelTypeId;
    /**渠道名称*/
    @ApiModelProperty(value = "渠道名称")
    private List<Integer> channelId;
    /**二级渠道*/
    @ApiModelProperty(value = "二级渠道")
    private List<Integer> channelSubAccountId;
    /**广告列表*/
    @ApiModelProperty(value = "广告列表")
    private List<Integer> dealId;
    /**广告计划ID*/
    @ApiModelProperty(value = "广告计划ID")
    private List<Integer> planId;
    /**留存类型*/
    @ApiModelProperty(value = "留存类型")
    private String retainType;
    /**充值区间*/
    @Excel(name = "充值区间", width = 15)
    @ApiModelProperty(value = "充值区间")
    private BigDecimal begSection;
    /**区间结束*/
    @ApiModelProperty(value = "区间结束")
    private BigDecimal endSection;
    /**起始时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    /**结束时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    /**充值限期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "充值时间")
    private Date costTime;
    /**账号人员*/
    @ApiModelProperty(value = "账号人员")
    private String createUser;
}
