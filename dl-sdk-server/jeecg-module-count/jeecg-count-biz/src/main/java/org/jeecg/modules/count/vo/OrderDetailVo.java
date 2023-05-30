package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class OrderDetailVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**订单号*/
    @ApiModelProperty(value = "订单号")
    private String orderId;
	/**充值金额*/
    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;
	/**充值方式*/
    @ApiModelProperty(value = "充值方式")
    private String payType;
	/**充值时间*/
	@Excel(name = "充值时间", width = 15)
    @ApiModelProperty(value = "充值时间")
    private String createTime;

}
