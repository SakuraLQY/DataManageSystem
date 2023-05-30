package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.project.bo
 * @className: UpdateProjectError
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/22 15:41
 * @version: 1.0
 */
@Data
public class UpdateProjectError {

    @JSONField(name = "object_type")
    private String  objectType;

    @JSONField(name = "error_code")
    private Integer errorCode;

    @JSONField(name = "error_message")
    private String errorMessage;
}
