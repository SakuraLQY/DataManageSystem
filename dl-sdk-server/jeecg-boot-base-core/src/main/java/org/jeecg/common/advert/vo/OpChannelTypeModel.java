package org.jeecg.common.advert.vo;

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
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_channel_type
 * @Author: jeecg-boot
 * @Date:   2023-01-05
 * @Version: V1.0
 */
@Data
@TableName("op_channel_type")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_channel_type对象", description="op_channel_type")
public class OpChannelTypeModel implements Serializable {
    private static final long serialVersionUID = 1L;

	/**渠道类型-id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "渠道类型-id")
    private Integer id;
	/**渠道类型名称*/
	@Excel(name = "渠道类型名称", width = 15)
    @ApiModelProperty(value = "渠道类型名称")
    private String typeName;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String typeDesc;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private String createBy;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
