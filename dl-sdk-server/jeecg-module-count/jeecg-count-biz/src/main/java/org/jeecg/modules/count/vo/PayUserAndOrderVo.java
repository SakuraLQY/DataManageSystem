package org.jeecg.modules.count.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class PayUserAndOrderVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**广告ID*/
    @ApiModelProperty(value = "广告ID")
    private Integer dealId;
	/**用户ID*/
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**总金额*/
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalMoney;


}
