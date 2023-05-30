package org.jeecg.modules.pay.bo.wx;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author xmh
 * @version V1.0
 * @description: h5场景信息
 * @date: 2022/12/31
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="h5V2场景信息", description="h5V2场景信息")
public class H5InfoV2 implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场景类型")
    private String type;

    @ApiModelProperty(value = "应用名")
    @JSONField(name = "app_name")
    private String appName;

    @ApiModelProperty(value = "Android包名")
    @JSONField(name = "package_name")
    private String packageName;

    @ApiModelProperty(value = "IOS移动应用-bundle_id")
    @JSONField(name = "bundle_id")
    private String bundleId;



}
