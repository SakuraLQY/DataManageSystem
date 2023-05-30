package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="查询投入成本", description="查询投入成本")
public class CostDataVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**成本日期*/
    @ApiModelProperty(value = "costDay")
    private String costDay;
	/**成本金额*/
    @ApiModelProperty(value = "costMoney")
    private BigDecimal costMoney;

}
