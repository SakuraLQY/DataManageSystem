package org.jeecg.modules.count.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_callback
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="查询回调渠道数据", description="查询回调渠道数据")
public class CtCallbackVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**id*/
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**渠道游戏包*/
    @Excel(name = "渠道游戏包", width = 15)
    @ApiModelProperty(value = "渠道游戏包")
    private java.lang.String pkgName;
    /**所属渠道*/
    @Excel(name = "渠道id", width = 15)
    @ApiModelProperty(value = "所属渠道")
    private java.lang.String channelName;
    /**回调渠道*/
    @Excel(name = "回调渠道", width = 15)
    @ApiModelProperty(value = "回调渠道")
    private java.lang.String callbackChannel;
    /**广告-id*/
    @Excel(name = "广告-id", width = 15)
    @ApiModelProperty(value = "广告-id")
    private java.lang.Integer dealId;
    /**回调事件类型*/
    @Dict(dicCode = "callback_event_type")
    @Excel(name = "回调事件类型", width = 15)
    @ApiModelProperty(value = "回调事件类型")
    private java.lang.Integer eventType;
    /**回调数据*/
    @Excel(name = "回调数据", width = 15)
    @ApiModelProperty(value = "回调数据")
    private java.lang.String callbackData;
    /**回调次数*/
    @Excel(name = "回调次数", width = 15)
    @ApiModelProperty(value = "回调次数")
    private java.lang.Integer callbackNum;
    /**回调状态：1为待回调，2为回调成功，3为回调失败*/
    @Dict(dicCode = "callback_state")
    @Excel(name = "回调状态：1为待回调，2为回调成功，3为回调失败", width = 15)
    @ApiModelProperty(value = "回调状态：1为待回调，2为回调成功，3为回调失败")
    private java.lang.Integer callbackState;
    /**时间-创建*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "时间-创建")
    private java.util.Date createTime;
    /**时间-更新*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "时间-更新")
    private java.util.Date updateTime;

}
