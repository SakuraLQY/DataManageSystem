package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Description: OrderNumGroupRegTimeCreateTimeBo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class OrderNumGroupRegTimeCreateTimeBo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单数量
     **/
    private Integer num;

    /**
     * 用户创建时间
     **/
    private Date userCreateTime;

    /**
     * 创建时间
     **/
    private Date createTime;

}
