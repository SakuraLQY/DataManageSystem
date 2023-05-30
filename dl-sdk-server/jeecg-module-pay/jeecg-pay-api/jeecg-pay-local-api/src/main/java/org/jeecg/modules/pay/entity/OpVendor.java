package org.jeecg.modules.pay.entity;

import java.io.Serializable;
import java.util.Objects;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: op_vendor
 * @Author: jeecg-boot
 * @Date:   2022-12-10
 * @Version: V1.0
 */
@Data
@TableName("op_vendor")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_vendor对象", description="op_vendor")
@NoArgsConstructor
public class OpVendor implements Serializable {
    private static final long serialVersionUID = 1L;

	public OpVendor(OpVendor vendor) {
		if(Objects.nonNull(vendor)) {
			this.id = vendor.getId();
			this.vendorName = vendor.getVendorName();
			this.aliPayVendor = vendor.getAliPayVendor();
			this.heePayVendor = vendor.getHeePayVendor();
			this.yeePayVendor = vendor.getYeePayVendor();
			this.iappPayVendor = vendor.getIappPayVendor();
			this.ipaynowPayVendor = vendor.getIpaynowPayVendor();
			this.ipaynowappPayVendor = vendor.getIpaynowappPayVendor();
			this.wxPayVendor = vendor.getWxPayVendor();
			this.createBy = vendor.getCreateBy();
			this.createTime = vendor.getCreateTime();
			this.updateBy = vendor.getUpdateBy();
			this.updateTime = vendor.getUpdateTime();
			this.note = vendor.getNote();
		}
	}

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**厂商名*/
	@Excel(name = "厂商名", width = 15)
    @ApiModelProperty(value = "厂商名")
    private java.lang.String vendorName;
	/**支付宝支付渠道ID*/
	@Excel(name = "支付宝支付渠道ID", width = 15, dictTable = "open_gateway.op_pay_vendor where pay_type = 1", dicText = "pay_vendor_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_pay_vendor where pay_type = 1", dicText = "pay_vendor_name", dicCode = "id")
    @ApiModelProperty(value = "支付宝支付渠道ID")
    private java.lang.Integer aliPayVendor;
	/**汇付宝支付渠道ID*/
	@Excel(name = "汇付宝支付渠道ID", width = 15, dictTable = "open_gateway.op_pay_vendor where pay_type = 2", dicText = "pay_vendor_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_pay_vendor where pay_type = 2", dicText = "pay_vendor_name", dicCode = "id")
    @ApiModelProperty(value = "汇付宝支付渠道ID")
    private java.lang.Integer heePayVendor;
	/**易宝支付渠道ID*/
	@Excel(name = "易宝支付渠道ID", width = 15, dictTable = "open_gateway.op_pay_vendor where pay_type = 3", dicText = "pay_vendor_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_pay_vendor where pay_type = 3", dicText = "pay_vendor_name", dicCode = "id")
    @ApiModelProperty(value = "易宝支付渠道ID")
    private java.lang.Integer yeePayVendor;
	/**爱贝支付渠道ID*/
	@Excel(name = "爱贝支付渠道ID", width = 15, dictTable = "open_gateway.op_pay_vendor where pay_type = 4", dicText = "pay_vendor_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_pay_vendor where pay_type = 4", dicText = "pay_vendor_name", dicCode = "id")
    @ApiModelProperty(value = "爱贝支付渠道ID")
    private java.lang.Integer iappPayVendor;
	/**现在支付支付渠道ID*/
	@Excel(name = "现在支付支付渠道ID", width = 15, dictTable = "open_gateway.op_pay_vendor where pay_type = 5", dicText = "pay_vendor_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_pay_vendor where pay_type = 5", dicText = "pay_vendor_name", dicCode = "id")
    @ApiModelProperty(value = "现在支付支付渠道ID")
    private java.lang.Integer ipaynowPayVendor;
	/**现在支付app支付渠道ID*/
	@Excel(name = "现在支付app支付渠道ID", width = 15, dictTable = "open_gateway.op_pay_vendor where pay_type = 6", dicText = "pay_vendor_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_pay_vendor where pay_type = 6", dicText = "pay_vendor_name", dicCode = "id")
    @ApiModelProperty(value = "现在支付app支付渠道ID")
    private java.lang.Integer ipaynowappPayVendor;
	/**微信支付支付渠道ID*/
	@Excel(name = "微信支付支付渠道ID", width = 15, dictTable = "open_gateway.op_pay_vendor where pay_type = 7", dicText = "pay_vendor_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_pay_vendor where pay_type = 7", dicText = "pay_vendor_name", dicCode = "id")
    @ApiModelProperty(value = "微信支付支付渠道ID")
    private java.lang.Integer wxPayVendor;
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
