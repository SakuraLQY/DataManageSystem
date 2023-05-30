package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.project.bo
 * @className: Geolocation
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/3 11:11
 * @version: 1.0
 */
@Data
public class Geolocation {
    private Integer radius;
    private String name;
    @JSONField(name = "long")
    private Float longitude;
    private Float lat;
}
