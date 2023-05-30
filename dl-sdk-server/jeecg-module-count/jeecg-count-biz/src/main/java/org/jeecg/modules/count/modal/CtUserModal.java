package org.jeecg.modules.count.modal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@ApiModel(value="导出表格对象", description="导出表格对象")
public class CtUserModal implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
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
    @Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private java.lang.String channelName;
    /**广告ID*/
    @Excel(name = "广告ID", width = 15)
    @ApiModelProperty(value = "广告ID")
    private java.lang.Integer dealId;
    /**广告名*/
    @Excel(name = "广告名", width = 15)
    @ApiModelProperty(value = "广告名")
    private java.lang.String dealName;
    /**设备ID*/
    @Excel(name = "设备ID", width = 15)
    @ApiModelProperty(value = "设备ID")
    private java.lang.String uniqueId;
    /**用户ID*/
    @Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private java.lang.Integer userId;
    /**账号*/
    @Excel(name = "账号", width = 15)
    @ApiModelProperty(value = "账号")
    private java.lang.String userName;
    /**首服ID*/
    @Excel(name = "首服ID", width = 15)
    @ApiModelProperty(value = "首服ID")
    private java.lang.Integer serverInit;
    /**新服ID*/
    @Excel(name = "新服ID", width = 15)
    @ApiModelProperty(value = "新服ID")
    private java.lang.Integer serverLast;
    /**在线时长*/
    @Excel(name = "在线时长", width = 15)
    @ApiModelProperty(value = "在线时长")
    private java.lang.String onlineTimeStr;
    /**注册时间*/
    @Excel(name = "注册时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册时间")
    private java.util.Date registerTime;
    /**最近登录时间*/
    @Excel(name = "最近登录时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "最近登录时间")
    private java.util.Date loginTime;
    /**最新登录IP*/
    @Excel(name = "最新登录IP", width = 15)
    @ApiModelProperty(value = "最新登录IP")
    private java.lang.String clientIp;
    /**IP归属地*/
    @Excel(name = "IP归属地", width = 15)
    @ApiModelProperty(value = "IP归属地")
    private java.lang.String ipPlace;

}
