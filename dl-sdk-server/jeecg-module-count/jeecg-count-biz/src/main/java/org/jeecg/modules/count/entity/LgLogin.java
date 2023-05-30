package org.jeecg.modules.count.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
 * @Description: lg_login
 * @Author: jeecg-boot
 * @Date:   2023-04-19
 * @Version: V1.0
 */
@Data
@TableName("lg_login")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="lg_login对象", description="lg_login")
public class LgLogin implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**广告-id*/
	@Excel(name = "广告-id", width = 15)
    @ApiModelProperty(value = "广告-id")
    private java.lang.Integer dealId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private java.lang.Integer userId;
	/**游戏id*/
	@Excel(name = "游戏id", width = 15)
    @ApiModelProperty(value = "游戏id")
    private java.lang.Integer gameId;
	/**子游戏id*/
	@Excel(name = "子游戏id", width = 15)
    @ApiModelProperty(value = "子游戏id")
    private java.lang.Integer subGameId;
	/**渠道游戏包id*/
	@Excel(name = "渠道游戏包id", width = 15)
    @ApiModelProperty(value = "渠道游戏包id")
    private java.lang.Integer pkgId;
	/**渠道id*/
	@Excel(name = "渠道id", width = 15)
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
	/**渠道类型id*/
	@Excel(name = "渠道类型id", width = 15)
    @ApiModelProperty(value = "渠道类型id")
    private java.lang.Integer channelTypeId;
	/**渠道子账号id*/
	@Excel(name = "渠道子账号id", width = 15)
    @ApiModelProperty(value = "渠道子账号id")
    private java.lang.Integer channelSubAccountId;
	/**设备id*/
	@Excel(name = "设备id", width = 15)
    @ApiModelProperty(value = "设备id")
    private java.lang.String uniqueId;
	/**时间-登录*/
	@Excel(name = "时间-登录", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "时间-登录")
    private java.util.Date loginTime;
	/**时间-注册*/
	@Excel(name = "时间-注册", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "时间-注册")
    private java.util.Date registerTime;
	/**登录ip*/
	@Excel(name = "登录ip", width = 15)
    @ApiModelProperty(value = "登录ip")
    private java.lang.String loginIp;
}
