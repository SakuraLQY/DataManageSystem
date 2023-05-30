package org.jeecg.modules.count.modal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.jeecg.modules.count.vo.OrderDetailVo;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class OrderDetailModal implements Serializable {
    private static final long serialVersionUID = 1L;

	/**订单对象*/
    @ApiModelProperty(value = "订单对象")
    private List<OrderDetailVo> orderObj;
    /**总金额*/
    @Excel(name = "总金额", width = 15)
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalMoney;
}
