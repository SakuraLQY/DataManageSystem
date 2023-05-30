package org.jeecg.modules.advert.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: AudienceVo
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/9 14:04
 * @version: 1.0
 */
@Data
public class AudienceVo {

    private Long audiencePackageId;

    private String name;

    private String description;
}
