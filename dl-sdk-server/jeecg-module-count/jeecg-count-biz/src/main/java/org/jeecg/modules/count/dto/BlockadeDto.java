package org.jeecg.modules.count.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
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
@ApiModel(value="传给后端对象", description="传给后端对象")
public class BlockadeDto implements Serializable {
    private static final long serialVersionUID = 1L;

	/**广告ID*/
    @ApiModelProperty(value = "广告ID")
    @Dict(dictTable = "open_gateway.op_deal", dicText = "deal_name", dicCode = "id")
    private Integer dealId;
	/**用户ID*/
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
	/**最新登录IP*/
    @ApiModelProperty(value = "最新登录IP")
    private String clientIp;

    @ApiModelProperty(value = "封号、封IP操作")
    private Integer type;
}
