package org.jeecg.modules.pay.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Description: op_pay_vendor
 * @Author: jeecg-boot
 * @Date:   2022-12-10
 * @Version: V1.0
 */
@Data
@TableName("op_pay_vendor")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_pay_vendor对象", description="op_pay_vendor")
public class OpPayVendor implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**支付类型,1支付宝2汇付宝3易宝4爱贝5现在支付6现在支付app7微信*/
	@Excel(name = "支付类型", width = 15)
    @ApiModelProperty(value = "支付类型")
    private java.lang.Integer payType;
	/**支付渠道名称*/
	@Excel(name = "支付渠道名称", width = 15)
    @ApiModelProperty(value = "支付渠道名称")
    private java.lang.String payVendorName;
	/**支付渠道配置*/
	@Excel(name = "支付渠道配置", width = 15)
    @ApiModelProperty(value = "支付渠道配置")
    private java.lang.String payVendorConf;
	/**创建人*/
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String note;
}
