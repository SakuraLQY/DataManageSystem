package org.jeecg.modules.count.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description:
 * @author: chenglin
 * @date: 2023年05月11日 17:21
 */
@Data
@TableName("open_gateway.op_cost")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "summary_launch对象", description = "数据投放")
public class SummaryLaunch implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 游戏选择
     */
    @Excel(name = "游戏选择", width = 15)
    @ApiModelProperty(value = "游戏选择")
    private Integer gameId;
    /**
     * 子游戏
     */
    @Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    private Integer subGameId;
    /**
     * 游戏包
     */
    @Excel(name = "游戏包", width = 15)
    @ApiModelProperty(value = "游戏包")
    private Integer pkgId;
    /**
     * 渠道类型
     */
    @Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private Integer channelTypeId;
    /**
     * 渠道
     */
    @Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private Integer channelId;
    /**
     * 渠道子账号
     */
    @Excel(name = "渠道子账号", width = 15)
    @ApiModelProperty(value = "渠道子账号")
    private Integer channelSubAccountId;
    /**
     * 广告编号
     */
    @Excel(name = "广告编号", width = 15)
    @ApiModelProperty(value = "广告编号")
    private Integer[] dealId;
    /**
     * 投放账号
     */
    @Excel(name = "投放账号", width = 15)
    @ApiModelProperty(value = "投放账号")
    private Integer[] accountId;
    /**
     * 注册时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "注册起始时间")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "注册结束时间")
    private Date endTime;
    /**
     * 归类方式
     */
    @ApiModelProperty(value = "归类方式")
    private String type;
}
