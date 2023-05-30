package org.jeecg.modules.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author chenyw
 * @version V1.0
 * @description: ios获取非默认支付列表请求参数
 * @date: 2023/1/4
 **/
@Data
@ApiModel(value="ios切支付参数", description="ios切支付参数")
public class IosPayListDto extends OpenDto{

    /**
     * 版本
     */
    @ApiModelProperty(value = "版本")
    private String version;

    /**
     * 支付类型列表
     */
    @ApiModelProperty(value = "构建")
    private String build;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;

}
