package org.jeecg.modules.count.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
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
public class UserLogVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**广告ID*/
    @ApiModelProperty(value = "广告ID")
    private Integer dealId;
	/**最近登录时间*/
    @ApiModelProperty(value = "登录时间")
    private String loginTime;
	/**最新登录IP*/
    @ApiModelProperty(value = "登录IP")
    private String loginIp;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private Integer num;
}
