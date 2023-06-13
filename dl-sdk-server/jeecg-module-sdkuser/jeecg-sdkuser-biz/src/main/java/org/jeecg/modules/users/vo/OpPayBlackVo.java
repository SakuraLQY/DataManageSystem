package org.jeecg.modules.users.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_pay_black
 * @Author: jeecg-boot
 * @Date: 2022-12-19
 * @Version: V1.0
 */
@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class OpPayBlackVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "规则类型")
    private Integer ruleType;
    @ApiModelProperty(value = "规则ID")
    private Integer ruleId;
    @ApiModelProperty(value = "游戏名")
    private String gameName;
    @ApiModelProperty(value = "子游戏名")
    private String subGameName;
    @ApiModelProperty(value = "渠道游戏包名")
    private String pkgName;
    @ApiModelProperty(value = "支付限制用户ID")
    private String payLimitUserId;
    @ApiModelProperty(value = "支付限制IP")
    private String payLimitIp;
    @ApiModelProperty(value = "支付限制设备")
    private String payLimitDevice;
    @ApiModelProperty(value = "备注")
    private String descs;
    @ApiModelProperty(value = "创建用户")
    private String createBy;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新用户")
    private String updateBy;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
