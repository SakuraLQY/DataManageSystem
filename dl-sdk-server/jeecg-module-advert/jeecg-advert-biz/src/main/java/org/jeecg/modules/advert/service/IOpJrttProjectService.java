package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.advert.api.jrtt.project.bo.UpdateProjectResponse;
import org.jeecg.modules.advert.entity.OpJrttProject;
import org.jeecg.modules.advert.vo.CreateProjectRspVo;
import org.jeecg.modules.advert.vo.OpJrttProjectVo;

/**
 * @Description: op_jrtt_project
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
public interface IOpJrttProjectService extends IService<OpJrttProject> {

    Result<List<CreateProjectRspVo>> batchCreateProject(OpJrttProjectVo opJrttProjectVo);

    Result<UpdateProjectResponse> updateProject(OpJrttProject opJrttProject);
}
