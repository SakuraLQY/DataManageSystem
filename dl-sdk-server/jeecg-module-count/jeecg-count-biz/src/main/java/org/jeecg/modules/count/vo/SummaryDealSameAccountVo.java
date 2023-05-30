package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: 同账号广告数据
 * @Author: jeecg-boot
 * @Date:   2023-05-8
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class SummaryDealSameAccountVo extends SummaryDealBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**广告id*/
    @ApiModelProperty(value = "广告id")
    private String dealId;

    /**广告名*/
    @ApiModelProperty(value = "广告名")
    private String dealName;

}
