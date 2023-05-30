package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.project.bo
 * @className: UpdateProjectResponse
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/22 15:39
 * @version: 1.0
 */
@Data
public class UpdateProjectResponse {
    @JSONField(name = "project_id")
    private Long projectId;

    @JSONField(name = "error_list")
    private List<UpdateProjectError> errorList;
}
