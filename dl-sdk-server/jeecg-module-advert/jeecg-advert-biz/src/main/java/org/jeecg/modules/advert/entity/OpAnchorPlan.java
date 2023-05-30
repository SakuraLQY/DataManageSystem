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
 * @Description: op_anchor_plan
 * @Author: jeecg-boot
 * @Date:   2023-02-28
 * @Version: V1.0
 */
@Data
@TableName("op_anchor_plan")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_anchor_plan对象", description="op_anchor_plan")
public class OpAnchorPlan implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主播计划ID*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主播计划ID")
    private java.lang.Integer id;
	/**计划名(主播)*/
	@Excel(name = "计划名(主播)", width = 15)
    @ApiModelProperty(value = "计划名(主播)")
    @Dict(dictTable = "open_gateway.op_anchor_plan", dicText = "plan_name", dicCode = "id")
    private java.lang.String planName;
	/**抖音ID*/
	@Excel(name = "抖音ID", width = 15)
    @ApiModelProperty(value = "抖音ID")
    private java.lang.Long trillId;
	/**绑定广告ID*/
	@Excel(name = "绑定广告ID", width = 15)
    @ApiModelProperty(value = "绑定广告ID")
    @Dict(dictTable = "open_gateway.op_deal", dicText = "deal_name", dicCode = "id")
    private java.lang.Long dealId;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String planDesc;
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
