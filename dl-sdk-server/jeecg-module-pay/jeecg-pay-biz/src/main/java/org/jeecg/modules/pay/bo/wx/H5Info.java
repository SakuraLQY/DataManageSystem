package org.jeecg.modules.pay.bo.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author xmh
 * @version V1.0
 * @description: h5场景信息
 * @date: 2022/12/31
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ApiModel(value="h5场景信息", description="h5场景信息")
public class H5Info implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场景类型")
    private String type;

    public static H5Info wapH5Info() {
        return new H5Info("Wap");
    }
}
