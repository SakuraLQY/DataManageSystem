package org.jeecg.modules.count.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 接受渠道明细前端的数据
 * @author: chenglin
 * @date: 2023年05月11日 18:56
 */
@Data
@ApiModel(value = "前端传来对象", description = "前端传来对象")
public class ChannelDetailDto implements Serializable {
    @ApiModelProperty(value = "游戏项目")
    private Integer[] gameId;
    /**游戏名称*/
    @ApiModelProperty(value = "游戏名称")
    private Integer[] subGameId;
    /**渠道类型*/
    @ApiModelProperty(value = "渠道类型")
    private Integer[] channelTypeId;
    /**渠道名称*/
    @ApiModelProperty(value = "渠道名称")
    private Integer[] channelId;
    /**起始日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "起始日期")
    private Date startTime;
    /**结束日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private Date endTime;
    /**展示方式*/
    @ApiModelProperty(value = "展示方式")
    private String showType;

}
