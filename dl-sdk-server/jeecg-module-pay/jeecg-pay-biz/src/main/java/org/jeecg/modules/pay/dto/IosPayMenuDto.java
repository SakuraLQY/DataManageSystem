package org.jeecg.modules.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author chenyw
 * @version V1.0
 * @description: ios获取非默认支付列表请求参数
 * @date: 2023/1/4
 **/
@Data
@ApiModel(value="ios获取非默认支付列表请求参数", description="ios获取非默认支付列表请求参数")
public class IosPayMenuDto extends OpenDto{

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 支付类型列表
     */
    @ApiModelProperty(value = "支付类型列表")
    @NotEmpty(message = "支付类型列表不能为空")
    private List<Integer> ztTypeList;

}
