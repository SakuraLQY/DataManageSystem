package org.jeecg.modules.advert.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
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
 * @Description: op_ks_put_account
 * @Author: jeecg-boot
 * @Date:   2023-03-07
 * @Version: V1.0
 */
@Data
@TableName("op_ks_put_account")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_ks_put_account对象", description="op_ks_put_account")
public class OpKsPutAccount implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**账号id*/
	@Excel(name = "账号id", width = 15)
    @ApiModelProperty(value = "账号id")
    private java.lang.Integer accountId;
	/**渠道账号id*/
	@Excel(name = "渠道账号id", width = 15)
    @ApiModelProperty(value = "渠道账号id")
    private java.lang.Long advertiserId;
	/**渠道账号列表，用了保存管家账号里面的账号列表*/
	@Excel(name = "渠道账号列表，用了保存管家账号里面的账号列表", width = 15)
    @ApiModelProperty(value = "渠道账号列表，用了保存管家账号里面的账号列表")
    private java.lang.String advertiserIds;
	/**access_token*/
	@Excel(name = "access_token", width = 15)
    @ApiModelProperty(value = "access_token")
    private java.lang.String accessToken;
	/**access_token过期时间*/
	@Excel(name = "access_token过期时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "access_token过期时间")
    private LocalDateTime accessTokenTime;
	/**refresh_token*/
	@Excel(name = "refresh_token", width = 15)
    @ApiModelProperty(value = "refresh_token")
    private java.lang.String refreshToken;
	/**refresh_token过期时间*/
	@Excel(name = "refresh_token过期时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "refresh_token过期时间")
    private LocalDateTime refreshTokenTime;
	/**授权时间*/
	@Excel(name = "授权时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "授权时间")
    private LocalDateTime authorizeTime;
}
