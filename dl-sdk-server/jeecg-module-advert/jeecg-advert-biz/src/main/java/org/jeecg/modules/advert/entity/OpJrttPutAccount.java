package org.jeecg.modules.advert.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: op_jrtt_put_account
 * @Author: jeecg-boot
 * @Date:   2023-01-09
 * @Version: V1.0
 */
@Data
@TableName("op_jrtt_put_account")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_jrtt_put_account对象", description="op_jrtt_put_account")
public class OpJrttPutAccount implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;
	/**账号等级，1级为一级账号，2级为二级账号*/
    @ApiModelProperty(value = "账号id")
    @Dict(dicCode = "account_id")
    private Integer accountId;
	/**渠道账号id*/
	@Excel(name = "渠道账号id", width = 15)
    @ApiModelProperty(value = "渠道账号id")
    private Long advertiserId;
	/**渠道账号列表，用了保存管家账号里面的账号列表*/
	@Excel(name = "渠道账号列表，用了保存管家账号里面的账号列表", width = 15)
    @ApiModelProperty(value = "渠道账号列表，用了保存管家账号里面的账号列表")
    private String advertiserIds;
	/**access_token*/
	@Excel(name = "access_token", width = 15)
    @ApiModelProperty(value = "access_token")
    private String accessToken;
	/**access_token过期时间*/
	@Excel(name = "access_token过期时间", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "access_token过期时间")
    private LocalDateTime accessTokenTime;
	/**refresh_token*/
	@Excel(name = "refresh_token", width = 15)
    @ApiModelProperty(value = "refresh_token")
    private String refreshToken;
	/**refresh_token过期时间*/
	@Excel(name = "refresh_token过期时间", width = 15)
    @ApiModelProperty(value = "refresh_token过期时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refreshTokenTime;
	/**授权时间*/
	@Excel(name = "授权时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "授权时间")
    private LocalDateTime authorizeTime;
}
