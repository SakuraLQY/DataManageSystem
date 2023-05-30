package org.jeecg.modules.count.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 接受前端传来的实体类
 * @author: chenglin
 * @date: 2023年04月28日 16:17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_hour对象", description="ct_hour")
public class DailyAnalyzeDto implements Serializable {
    /**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
    /**广告-id*/
    @ApiModelProperty(value = "广告-id")
    private Integer dealId;
    /**游戏id*/
    @ApiModelProperty(value = "游戏id")
    private Integer[] gameId;
    /**子游戏id*/
    @ApiModelProperty(value = "子游戏id")
    private Integer[] subGameId;
    @ApiModelProperty(value = "渠道游戏包Id")
    private Integer pkgId;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    private Integer[] channelTypeId;
    /**渠道id*/
    @ApiModelProperty(value = "渠道id")
    private Integer[] channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private Integer[] channelSubAccountId;
    /**时间-開始*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "起始时间")
    private Date startTime;
    /**時間-結束*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

}
