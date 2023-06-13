package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="剩余DAU和付费DAU", description="剩余DAU和付费DAU")
public class DauDataVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**注册时间*/
    @ApiModelProperty(value = "注册时间")
    private String registerTime;
	/**充值时间*/
    @ApiModelProperty(value = "充值时间")
    private String payTime;
    /**数量*/
    @ApiModelProperty(value = "数量")
    private Integer counts=0;

}
