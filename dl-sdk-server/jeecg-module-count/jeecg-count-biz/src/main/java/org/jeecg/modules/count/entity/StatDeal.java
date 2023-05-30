package org.jeecg.modules.count.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: stat_deal
 * @Author: jeecg-boot
 * @Date:   2023-05-30
 * @Version: V1.0
 */
@Data
@TableName("ct_daily")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="stat_deal对象", description="stat_deal")
public class StatDeal implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**日期*/
//	@Excel(name = "日期", width = 15)
    @TableField("time_daily")
    @ApiModelProperty(value = "日期")
    private Date dateTime;
	/**广告名称*/
//	@Excel(name = "广告名称", width = 15)
    @TableField(exist = false)
    @ApiModelProperty(value = "广告名称")
    private String dealName;
	/**游戏名称*/
    @TableField(exist = false)
//	@Excel(name = "游戏名称", width = 15)
    @ApiModelProperty(value = "游戏名称")
    private String gameName;
	/**激活数*/
	@Excel(name = "激活数", width = 15)
    @TableField("count_active")
    @ApiModelProperty(value = "激活数")
    private Integer countActive;
	/**注册数*/
	@Excel(name = "注册数", width = 15)
    @TableField("count_user")
    @ApiModelProperty(value = "注册数")
    private Integer regCount;
	/**有效注册数*/
    @TableField("count_valid_user")
	@Excel(name = "有效注册数", width = 15)
    @ApiModelProperty(value = "有效注册数")
    private Integer validReg;
	/**新增付费人数*/
    @TableField("first_payuser")
	@Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayUser;
	/**新增付费金额*/
    @TableField("first_money")
	@Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private Integer firstPayMoney;
	/**活跃人数*/
    @TableField("count_dau")
	@Excel(name = "活跃人数", width = 15)
    @ApiModelProperty(value = "活跃人数")
    private Integer countDau;
	/**活跃付费人数*/
    @TableField("alive_payuser")
	@Excel(name = "活跃付费人数", width = 15)
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayUser;
	/**付费总额*/
    @TableField("total_money")
	@Excel(name = "付费总额", width = 15)
    @ApiModelProperty(value = "付费总额")
    private Integer totalMoney;
}
