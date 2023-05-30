package org.jeecg.modules.pay.vo;

import lombok.Data;

/**
 * @author chenyw
 * @version V1.0
 * @description: ios支付列表
 * @date: 2022/1/14
 **/
@Data
public class IosPayMenuPayVo {

    /**
     * 支付方式名称
     */
    private String name;

    /**
     * 支付地址
     */
    private String url;

    /**
     * 支付类型
     */
    private Integer type;

    /**
     * 图标的静态地址
     */
    private String icon;
}
