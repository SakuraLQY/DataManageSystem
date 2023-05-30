package org.jeecg.modules.count.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 接受前端传来的对象
 * @author: chenglin
 * @date: 2023年05月23日 14:55
 */
@Data
public class ComputePayDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**游戏项目*/
    @Excel(name = "游戏项目", width = 15)
    @ApiModelProperty(value = "游戏项目")
    private List<Integer> gameId;
    /**游戏名称*/
    @Excel(name = "游戏名称", width = 15)
    @ApiModelProperty(value = "游戏名称")
    private List<Integer> subGameId;
    /**子游戏包*/
    @Excel(name = "子游戏包", width = 15)
    @ApiModelProperty(value = "子游戏包")
    private List<Integer> pkgId;
    /**渠道*/
    @Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private List<Integer> channelId;
    /**渠道类型*/
    @Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private List<Integer> channelTypeId;
    /**子渠道*/
    @Excel(name = "子渠道", width = 15)
    @ApiModelProperty(value = "子渠道")
    private List<Integer> channelSubAccountId;
    /**注册时间*/
    @Excel(name = "注册时间", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册时间")
    private Date startTime;
    /**结束时间*/
    @Excel(name = "结束时间", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    /**付费日期*/
    @Excel(name = "付费日期", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "付费日期")
    private Date payStart;
    /**结束日期*/
    @Excel(name = "结束日期", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private Date payEnd;
}
