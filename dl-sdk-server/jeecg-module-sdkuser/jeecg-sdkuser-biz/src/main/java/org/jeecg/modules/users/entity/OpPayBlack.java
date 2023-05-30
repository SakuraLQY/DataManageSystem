package org.jeecg.modules.users.entity;

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
 * @Description: op_pay_black
 * @Author: jeecg-boot
 * @Date:   2022-12-19
 * @Version: V1.0
 */
@Data
@TableName("op_pay_black")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_pay_black对象", description="op_pay_black")
public class OpPayBlack implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**规则类型*/
	@Excel(name = "规则类型", width = 15, dicCode = "pay_rule_type")
	@Dict(dicCode = "pay_rule_type")
    @ApiModelProperty(value = "规则类型")
    private java.lang.Integer ruleType;
	/**规则ID*/
	@Excel(name = "规则ID", width = 15)
    @ApiModelProperty(value = "规则ID")
    private java.lang.Integer ruleId;
	/**支付限制用户ID*/
	@Excel(name = "支付限制用户ID", width = 15)
    @ApiModelProperty(value = "支付限制用户ID")
    private java.lang.String payLimitUserId;
	/**支付限制IP*/
	@Excel(name = "支付限制IP", width = 15)
    @ApiModelProperty(value = "支付限制IP")
    private java.lang.String payLimitIp;
	/**支付限制设备*/
	@Excel(name = "支付限制设备", width = 15)
    @ApiModelProperty(value = "支付限制设备")
    private java.lang.String payLimitDevice;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String descs;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新用户*/
    @ApiModelProperty(value = "更新用户")
    private java.lang.String updateBy;
	/**更新时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
