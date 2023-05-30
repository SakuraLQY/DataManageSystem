package org.jeecg.modules.pay.bo.wx;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author lili
 * @version V1.0
 * @description: 场景信息
 * @date: 2022/12/31
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ApiModel(value="h5V2场景信息", description="h5V2场景信息")
public class SceneInfoH5V2 implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "h5场景信息")
    @JSONField(name = "h5_info")
    private H5Info h5Info;
}
