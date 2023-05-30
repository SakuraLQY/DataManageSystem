package org.jeecg.modules.count.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @description: 数据库暂时查询注册数以及
* @author: chenglin
* @date: 2023年05月17日 15:54
*/
@Data
public class RetainAnalyzeBo implements Serializable {

    /**日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "时间")
    private Date dateTime;

    /**新增注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer regCount;

    @ApiModelProperty(value = "新增付费用户")
    private BigDecimal firstPayUser;
}
