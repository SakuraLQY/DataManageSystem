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
 * @Description: op_jrtt_aweme
 * @Author: jeecg-boot
 * @Date:   2023-02-22
 * @Version: V1.0
 */
@Data
@TableName("op_jrtt_aweme")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_jrtt_aweme对象", description="op_jrtt_aweme")
public class OpJrttAweme implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**账号ID*/
	@Excel(name = "账号ID", width = 15)
    @ApiModelProperty(value = "账号ID")
    private java.lang.Integer accountId;
	/**抖音号*/
	@Excel(name = "抖音号", width = 15)
    @ApiModelProperty(value = "抖音号")
    private java.lang.String awemeId;
    /**抖音号*/
    @Excel(name = "抖音账号名称", width = 15)
    @ApiModelProperty(value = "抖音账号名称")
    private java.lang.String awemeName;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "授权开始时间")
    private java.util.Date startTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "授权结束时间")
    private java.util.Date endTime;
}
