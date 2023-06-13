package org.jeecg.modules.count.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: CallbackOperationDto
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_callback对象", description="ct_callback")
public class CallbackOperationDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;

    /**操作类型*/
    @ApiModelProperty(value = "操作类型")
    private java.lang.Integer operationType;

}
