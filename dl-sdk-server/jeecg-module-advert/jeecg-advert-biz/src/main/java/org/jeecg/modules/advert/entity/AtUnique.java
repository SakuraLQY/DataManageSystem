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
 * @Description: at_unique
 * @Author: jeecg-boot
 * @Date:   2023-04-24
 * @Version: V1.0
 */
@Data
@TableName("at_unique")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="at_unique对象", description="at_unique")
public class AtUnique implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**广告id*/
	@Excel(name = "广告id", width = 15)
    @ApiModelProperty(value = "广告id")
    private java.lang.Integer dealId;
	/**唯一设备标识*/
	@Excel(name = "唯一设备标识", width = 15)
    @ApiModelProperty(value = "唯一设备标识")
    private java.lang.String uniqueId;
	/**回调参数*/
	@Excel(name = "回调参数", width = 15)
    @ApiModelProperty(value = "回调参数")
    private java.lang.String visitData;
	/**时间-创建*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "时间-创建")
    private java.util.Date createTime;
	/**时间-更新*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "时间-更新")
    private java.util.Date updateTime;
}
