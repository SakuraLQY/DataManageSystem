package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: 广告最近七天数据
 * @Author: jeecg-boot
 * @Date:   2023-05-8
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class SummaryDealSevenDayVo extends SummaryDealBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**日期*/
    @ApiModelProperty(value = "日期")
    private String date;

}
