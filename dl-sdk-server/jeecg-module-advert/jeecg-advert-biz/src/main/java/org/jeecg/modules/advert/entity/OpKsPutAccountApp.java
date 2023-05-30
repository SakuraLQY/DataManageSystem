package org.jeecg.modules.advert.entity;

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
 * @Description: op_ks_put_account_app
 * @Author: jeecg-boot
 * @Date:   2023-03-07
 * @Version: V1.0
 */
@Data
@TableName("op_ks_put_account_app")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_ks_put_account_app对象", description="op_ks_put_account_app")
public class OpKsPutAccountApp implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**应用名*/
	@Excel(name = "应用名", width = 15)
    @ApiModelProperty(value = "应用名")
    private java.lang.String appName;
	/**应用appid*/
	@Excel(name = "应用appid", width = 15)
    @ApiModelProperty(value = "应用appid")
    private java.lang.String appId;
	/**应用secret*/
	@Excel(name = "应用secret", width = 15)
    @ApiModelProperty(value = "应用secret")
    private java.lang.String appSecret;
	/**应用备注*/
	@Excel(name = "应用备注", width = 15)
    @ApiModelProperty(value = "应用备注")
    private java.lang.String appDesc;
	/**回调地址*/
	@Excel(name = "回调地址", width = 15)
    @ApiModelProperty(value = "回调地址")
    private java.lang.String redirectUri;
	/**授权地址*/
	@Excel(name = "授权地址", width = 15)
    @ApiModelProperty(value = "授权地址")
    private java.lang.String authUrl;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;
}
