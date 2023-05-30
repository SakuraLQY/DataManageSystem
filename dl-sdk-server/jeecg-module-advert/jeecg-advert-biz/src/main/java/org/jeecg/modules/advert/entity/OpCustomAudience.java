package org.jeecg.modules.advert.entity;

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
 * @Description: op_channel
 * @Author: jeecg-boot
 * @Date:   2023-01-05
 * @Version: V1.0
 */
@Data
@TableName("op_custom_audience")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_custom_audience对象", description="op_custom_audience")
public class OpCustomAudience implements Serializable {
    private static final long serialVersionUID = 1L;

	/**渠道-id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;
	/**渠道名称*/
	@Excel(name = "渠道名称", width = 15)
    @ApiModelProperty(value = "渠道名称")
    private String channelName;
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15, dictTable = "open_gateway.op_channel_type", dicText = "type_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_channel_type", dicText = "type_name", dicCode = "id")
    @ApiModelProperty(value = "渠道类型")
    private Integer typeId;
	/**渠道昵称*/
	@Excel(name = "渠道昵称", width = 15)
    @ApiModelProperty(value = "渠道昵称")
    private String channelNickname;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String channelDesc;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd ")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
