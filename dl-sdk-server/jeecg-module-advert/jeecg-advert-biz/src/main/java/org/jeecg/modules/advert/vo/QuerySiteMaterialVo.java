package org.jeecg.modules.advert.vo;

import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: QuerySiteMaterialVo
 * @author: Eric
 * @description: 落地页素材查询对象
 * @date: 2023/3/2 9:57
 * @version: 1.0
 */
@Data
public class QuerySiteMaterialVo {

    private Integer gameId;
    private Integer subGameId;
    private String createBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
