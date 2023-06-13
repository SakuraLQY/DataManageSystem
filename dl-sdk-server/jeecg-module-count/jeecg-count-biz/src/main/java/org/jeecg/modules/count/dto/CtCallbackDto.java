package org.jeecg.modules.count.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_callback
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_callback对象", description="ct_callback")
public class CtCallbackDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**游戏id*/
    @ApiModelProperty(value = "游戏id")
    private java.lang.Integer gameId;
    /**子游戏id*/
    @ApiModelProperty(value = "子游戏id")
    private java.lang.Integer subGameId;
    /**渠道游戏包id*/
    @ApiModelProperty(value = "渠道游戏包id")
    private java.lang.Integer pkgId;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    private java.lang.Integer channelTypeId;
    /**渠道id*/
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private java.lang.Integer channelSubAccountId;
    /**回调渠道*/
    @ApiModelProperty(value = "回调状态")
    private java.lang.Integer callbackState;
    /**广告id*/
    @ApiModelProperty(value = "广告id")
    private java.lang.Integer dealId;
    /**创建时间-开始*/
    @ApiModelProperty(value = "创建时间-开始")
    private String startTime;
    /**创建时间-结束*/
    @ApiModelProperty(value = "创建时间-结束")
    private String endTime;
}
