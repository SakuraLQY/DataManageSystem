package org.jeecg.modules.count.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
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
    private Integer id;
    @ApiModelProperty(value = "对比维度 0=注册数,1=激活数,2=付费总额,3=新用户付费总额,4=DAU")
    private String level;
    /**游戏id*/
    @ApiModelProperty(value = "游戏id")
    private List<Integer> gameId;
    /**子游戏id*/
    @ApiModelProperty(value = "子游戏id")
    private List<Integer>  subGameId;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    private List<Integer>  channelTypeId;
    /**渠道id*/
    @ApiModelProperty(value = "渠道id")
    private List<Integer>  channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private List<Integer>  channelSubAccountId;
    @ApiModelProperty(value = "渠道游戏包Id")
    private List<Integer>  pkgId;
    /**广告-id*/
    @ApiModelProperty(value = "广告-id")
    private List<Integer>  dealId;
    /**新增-激活数*/
    @ApiModelProperty(value = "新增-激活数")
    private Integer countActive;
    /**新增-注册数*/
    @ApiModelProperty(value = "新增-注册数")
    private Integer countUser;
    /**新增-活跃用户*/
    @ApiModelProperty(value = "新增-活跃用户")
    private Integer countDau;
    /**首日-付费金额*/
    @ApiModelProperty(value = "首日-付费金额")
    private BigDecimal firstMoney;
    /**累积-付费金额*/
    @ApiModelProperty(value = "累积-付费金额")
    private BigDecimal totalMoney;
    /**次日留存*/
    @ApiModelProperty(value = "次日留存")
    private Integer loyal2;
    /**3日留存*/
    @ApiModelProperty(value = "3日留存")
    private Integer loyal3;
    /**4日留存*/
    @ApiModelProperty(value = "4日留存")
    private Integer loyal4;
    /**5日留存*/
    @ApiModelProperty(value = "5日留存")
    private Integer loyal5;
    /**6日留存*/
    @ApiModelProperty(value = "6日留存")
    private Integer loyal6;
    /**7日留存*/
    @ApiModelProperty(value = "7日留存")
    private Integer loyal7;
    /**15日留存*/
    @ApiModelProperty(value = "15日留存")
    private Integer loyal15;
    /**30日留存*/
    @ApiModelProperty(value = "30日留存")
    private Integer loyal30;
    /**45日留存*/
    @ApiModelProperty(value = "45日留存")
    private Integer loyal45;
    /**60日留存*/
    @ApiModelProperty(value = "60日留存")
    private Integer loyal60;
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
