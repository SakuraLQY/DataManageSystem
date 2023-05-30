package org.jeecg.modules.count.vo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: ct_daily_summary
 * @Author: jeecg-boot
 * @Date: 2023-04-28
 * @Version: V1.0
 */
@Data
@TableName("ct_hour")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ct_hour对象", description = "ct_hour")
public class CtDailySummary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 广告列表
     */
    @Excel(name = "广告列表", width = 15)
    @ApiModelProperty(value = "广告列表")
    private Integer dealId;
    /**
     * 游戏项目
     */
    @Excel(name = "游戏项目", width = 15)
    @ApiModelProperty(value = "游戏项目")
    private Integer[] gameId;
    /**
     * 游戏名称
     */
    @Excel(name = "游戏名称", width = 15)
    @ApiModelProperty(value = "游戏名称")
    private Integer[] subGameId;
    /**
     * 渠道游戏包
     */
    @Excel(name = "渠道游戏包", width = 15)
    @ApiModelProperty(value = "渠道游戏包")
    private Integer[] pkgId;
    /**
     * 渠道类型
     */
    @Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private Integer[] channelTypeId;
    /**
     * 渠道名称
     */
    @Excel(name = "渠道名称", width = 15)
    @ApiModelProperty(value = "渠道名称")
    private Integer[] channelId;
    /**
     * 二级渠道
     */
    @Excel(name = "二级渠道", width = 15)
    @ApiModelProperty(value = "二级渠道")
    private Integer[] channelSubAccountId;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
}
