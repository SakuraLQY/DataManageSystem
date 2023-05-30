package org.jeecg.modules.advert.vo;

import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: JrttSiteListVo
 * @author: Eric
 * @description: 分页查询视图数据
 * @date: 2023/3/2 15:14
 * @version: 1.0
 */
@Data
public class JrttSiteListVo {

    private Integer id;
    private String siteName;
    private String gameName;
    private String subGameName;
    private String siteContent;
}
