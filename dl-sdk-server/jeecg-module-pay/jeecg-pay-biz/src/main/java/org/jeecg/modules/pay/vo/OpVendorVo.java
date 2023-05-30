package org.jeecg.modules.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jeecg.common.constant.PayTypeConstant;
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.pay.constant.PayVendorTypeConstant;
import org.jeecg.modules.pay.entity.OpVendor;

/**
 * 支付厂商vo对象
 *
 * @author: xmh
 * @date: 2022/12/12 9:48
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_vendor vo", description="op_vendor vo")
public class OpVendorVo extends OpVendor {

    /**
     * 必须创建的构造器
     * 用于 opVendor 向 opVendorVo 转换
     * @param vendor:
     * @return: null
     * @description: TODO
     * @author: xmh
     * @date: 2022/12/12 11:02
     */
    public OpVendorVo(OpVendor vendor) {
        super(vendor);
    }

    /* 支付宝支付渠道数据 */
    @ApiModelProperty(value = "支付宝支付渠道数据")
    private OpPayVendor aliPayVendorData;
    /* 汇付宝支付渠道数据 */
    @ApiModelProperty(value = "汇付宝支付渠道数据")
    private OpPayVendor heePayVendorData;
    /* 易宝支付渠道数据 */
    @ApiModelProperty(value = "易宝支付渠道数据")
    private OpPayVendor yeePayVendorData;
    /* 爱贝支付渠道数据 */
    @ApiModelProperty(value = "爱贝支付渠道数据")
    private OpPayVendor iappPayVendorData;
    /* 现在支付支付渠道数据 */
    @ApiModelProperty(value = "现在支付支付渠道数据")
    private OpPayVendor ipaynowPayVendorData;
    /* 现在支付app支付渠道数据 */
    @ApiModelProperty(value = "现在支付app支付渠道数据")
    private OpPayVendor ipaynowappPayVendorData;
    /* 微信支付支付渠道数据 */
    @ApiModelProperty(value = "微信支付支付渠道数据")
    private OpPayVendor wxPayVendorData;

    /**
     * 属性注入
     * @param opPayVendor:
     * @return: void
     * @description: TODO
     * @author: xmh
     * @date: 2022/12/12 11:49
     */
    public void addPayVendorData(OpPayVendor opPayVendor) {
        int payType = opPayVendor.getPayType();
        switch (payType) {
            case PayVendorTypeConstant.ALI:
                this.aliPayVendorData = opPayVendor;
                break;
            case PayVendorTypeConstant.HEE:
                this.heePayVendorData = opPayVendor;
                break;
            case PayVendorTypeConstant.YEE:
                this.yeePayVendorData = opPayVendor;
                break;
            case PayVendorTypeConstant.IAPP:
                this.iappPayVendorData = opPayVendor;
                break;
            case PayVendorTypeConstant.IPAYNOW:
                this.ipaynowPayVendorData = opPayVendor;
                break;
            case PayVendorTypeConstant.IPAYNOWAPP:
                this.ipaynowappPayVendorData = opPayVendor;
                break;
            case PayVendorTypeConstant.WX:
                this.wxPayVendorData = opPayVendor;
        }
    }

    /**
     * @param payType
     * @return org.jeecg.modules.pay.entity.OpPayVendor
     * @author chenyw
     * @description 根据支付类型 返回支付厂商
     * @date 14:21 2023/4/18/018
     **/
    public OpPayVendor getOpPayVendorByType(Integer payType){
        if(PayTypeConstant.WECAHT_PAY.contains(payType)){
            return this.wxPayVendorData;
        }else if(PayTypeConstant.ALI_PAY.contains(payType)){
            return this.aliPayVendorData;
        }
        return null;
    }
}
