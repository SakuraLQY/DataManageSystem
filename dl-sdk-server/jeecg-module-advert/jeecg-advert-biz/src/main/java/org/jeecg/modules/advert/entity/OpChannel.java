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
 * @Description: op_channel
 * @Author: jeecg-boot
 * @Date:   2023-01-05
 * @Version: V1.0
 */
@Data
@TableName("op_channel")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_channel对象", description="op_channel")
public class OpChannel implements Serializable {
    private static final long serialVersionUID = 1L;

	/**渠道-id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "渠道-id")
    private java.lang.Integer id;
	/**渠道名称*/
	@Excel(name = "渠道名称", width = 15)
    @ApiModelProperty(value = "渠道名称")
    private java.lang.String channelName;
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15, dictTable = "open_gateway.op_channel_type", dicText = "type_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_channel_type", dicText = "type_name", dicCode = "id")
    @ApiModelProperty(value = "渠道类型")
    private java.lang.Integer typeId;
	/**渠道昵称*/
	@Excel(name = "渠道昵称", width = 15)
    @ApiModelProperty(value = "渠道昵称")
    private java.lang.String channelNickname;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String channelDesc;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
}
