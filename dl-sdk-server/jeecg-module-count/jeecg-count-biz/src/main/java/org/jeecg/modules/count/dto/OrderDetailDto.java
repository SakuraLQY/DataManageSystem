package org.jeecg.modules.count.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给后端对象", description="传给后端对象")
public class OrderDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

	/**用户ID*/
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    /**广告ID*/
    @ApiModelProperty(value = "广告ID")
    private Integer dealId;

	/**充值开始时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "充值开始时间")
    private Date payStartTime;

    /**充值结束时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "充值结束时间")
    private Date payEndTime;

}
