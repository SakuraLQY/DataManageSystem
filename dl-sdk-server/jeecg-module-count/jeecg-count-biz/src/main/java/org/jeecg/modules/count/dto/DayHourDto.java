package org.jeecg.modules.count.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @description: 接受前端传来的对象
 * @author: chenglin
 * @date: 2023年04月24日 16:47
 */
@Data
@TableName("ct_hour")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_hour对象", description="ct_hour")
public class DayHourDto implements Serializable {
    /**id*/
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    @ApiModelProperty(value = "对比维度 0=注册数,1=激活数,2=付费总额,3=新用户付费总额,4=DAU")
    private java.lang.String level;
    /**游戏id*/
    @ApiModelProperty(value = "游戏id")
    private java.lang.Integer gameId;
    /**子游戏id*/
    @ApiModelProperty(value = "子游戏id")
    private java.lang.Integer subGameId;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    private java.lang.Integer channelTypeId;
    /**渠道id*/
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private java.lang.Integer channelSubAccountId;
    @ApiModelProperty(value = "渠道游戏包Id")
    private Integer pkgId;
    /**广告-id*/
    @ApiModelProperty(value = "广告-id")
    private java.lang.Integer dealId;
    /**新增-激活数*/
    @ApiModelProperty(value = "新增-激活数")
    private java.lang.Integer countActive;
    /**新增-注册数*/
    @ApiModelProperty(value = "新增-注册数")
    private java.lang.Integer countUser;
    /**新增-活跃用户*/
    @ApiModelProperty(value = "新增-活跃用户")
    private java.lang.Integer countDau;
    /**首日-付费金额*/
    @ApiModelProperty(value = "首日-付费金额")
    private java.math.BigDecimal firstMoney;
    /**累积-付费金额*/
    @ApiModelProperty(value = "累积-付费金额")
    private java.math.BigDecimal totalMoney;
    /**次日留存*/
    @ApiModelProperty(value = "次日留存")
    private java.lang.Integer loyal2;
    /**3日留存*/
    @ApiModelProperty(value = "3日留存")
    private java.lang.Integer loyal3;
    /**4日留存*/
    @ApiModelProperty(value = "4日留存")
    private java.lang.Integer loyal4;
    /**5日留存*/
    @ApiModelProperty(value = "5日留存")
    private java.lang.Integer loyal5;
    /**6日留存*/
    @ApiModelProperty(value = "6日留存")
    private java.lang.Integer loyal6;
    /**7日留存*/
    @ApiModelProperty(value = "7日留存")
    private java.lang.Integer loyal7;
    /**15日留存*/
    @ApiModelProperty(value = "15日留存")
    private java.lang.Integer loyal15;
    /**30日留存*/
    @ApiModelProperty(value = "30日留存")
    private java.lang.Integer loyal30;
    /**45日留存*/
    @ApiModelProperty(value = "45日留存")
    private java.lang.Integer loyal45;
    /**60日留存*/
    @ApiModelProperty(value = "60日留存")
    private java.lang.Integer loyal60;
    /**时间-创建*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "时间-创建")
    private java.util.Date startTime;
    /**时间-更新*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "时间-更新")
    private java.util.Date endTime;
}
