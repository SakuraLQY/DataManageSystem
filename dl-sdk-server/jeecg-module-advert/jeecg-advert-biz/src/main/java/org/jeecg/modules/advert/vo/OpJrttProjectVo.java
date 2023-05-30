package org.jeecg.modules.advert.vo;

import java.util.List;
import lombok.Data;
import org.jeecg.modules.advert.entity.OpJrttProject;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: OpJrttProjectVo
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/16 19:29
 * @version: 1.0
 */
@Data
public class OpJrttProjectVo {

    private List<Integer> dealIds;

    private OpJrttProject opJrttProject;

}
