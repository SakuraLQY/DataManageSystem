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
 * @Description: op_jrtt_events
 * @Author: jeecg-boot
 * @Date:   2023-02-16
 * @Version: V1.0
 */
@Data
@TableName("op_jrtt_events")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_jrtt_events对象", description="op_jrtt_events")
public class OpJrttEvents implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**所属资产*/
	@Excel(name = "所属资产", width = 15, dictTable = "open_gateway.op_jrtt_assets", dicText = "asset_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_jrtt_assets", dicText = "asset_name", dicCode = "id")
    @ApiModelProperty(value = "所属资产")
    private java.lang.Integer assetsId;
	/**事件ID*/
	@Excel(name = "事件ID", width = 15)
    @Dict(dicCode = "event-ANDROID")
    @ApiModelProperty(value = "事件ID")
    private java.lang.Integer eventId;
	/**回传方式*/
	@Excel(name = "回传方式", width = 15, dicCode = "track_type")
	@Dict(dicCode = "track_type")
    @ApiModelProperty(value = "回传方式")
    private java.lang.Integer trackType;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    @Dict(dictTable = "sys_user", dicText = "username", dicCode = "username")
    private java.lang.String createBy;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
}
