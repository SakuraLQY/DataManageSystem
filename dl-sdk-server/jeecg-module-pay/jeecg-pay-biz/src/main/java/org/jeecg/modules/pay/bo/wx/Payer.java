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
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2022/12/31
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ApiModel(value="支付者", description="支付者信息")
public class Payer implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户的openid")
    @JSONField(name = "openid")
    private String openId;
}
