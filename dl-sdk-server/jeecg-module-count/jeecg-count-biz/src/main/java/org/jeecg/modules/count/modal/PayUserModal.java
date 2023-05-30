package org.jeecg.modules.count.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象-付费用户数据", description="传给前端对象-付费用户数据")
public class PayUserModal implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
    /**游戏*/
    @Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private java.lang.String gameName;
    /**子游戏*/
    @Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    private java.lang.String subGameName;
    /**渠道游戏包*/
    @Excel(name = "渠道游戏包", width = 15)
    @ApiModelProperty(value = "渠道游戏包")
    private java.lang.String pkgName;
    /**渠道ID*/
    @Excel(name = "所属渠道", width = 15)
    @ApiModelProperty(value = "所属渠道")
    private java.lang.String channelName;
	/**广告ID*/
    @Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "广告ID")
    private Integer dealId;
    /**广告名*/
    @Excel(name = "广告名", width = 15)
    @ApiModelProperty(value = "广告名")
    private java.lang.String dealName;
	/**用户ID*/
    @Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**账号*/
    @Excel(name = "账号", width = 15)
    @ApiModelProperty(value = "账号")
    private String userName;
    /**手机号*/
    @Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private String userPhone;
	/**首服ID*/
    @Excel(name = "首服ID", width = 15)
    @ApiModelProperty(value = "首服ID")
    private Integer serverInit;
	/**新服ID*/
    @Excel(name = "新服ID", width = 15)
    @ApiModelProperty(value = "新服ID")
    private Integer serverLast;
	/**在线时长*/
    @Excel(name = "在线时长", width = 15)
    @ApiModelProperty(value = "在线时长")
    private String onlineTimeStr;
	/**注册时间*/
    @Excel(name = "注册时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "注册时间")
    private Date registerTime;
	/**最近登录时间*/
    @Excel(name = "最近登录时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最近登录时间")
    private Date loginTime;
	/**首次充值时间*/
    @Excel(name = "首次充值时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "首次充值时间")
    private Date firstPayTime;
    /**最后充值时间*/
    @Excel(name = "最后充值时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后充值时间")
    private Date payTime;
    /**充值总额*/
    @Excel(name = "充值总额", width = 15)
    @ApiModelProperty(value = "充值总额")
    private BigDecimal totalMoney;

}
