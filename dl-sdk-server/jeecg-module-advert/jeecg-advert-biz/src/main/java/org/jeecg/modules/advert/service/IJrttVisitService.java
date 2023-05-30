package org.jeecg.modules.advert.service;

import org.jeecg.common.advert.dto.JrttVisitDto;

/**
 * @Description: jrtt_deal
 * @Author: jeecg-boot
 * @Date: 2023-02-14
 * @Version: V1.0
 */
public interface IJrttVisitService {

    /**
     * @param jrttVisitDto
     * @author chenyw
     * @description 今日头条广告点击事件回传
     * @date 17:23 2023/3/29/029
     **/
    void click(JrttVisitDto jrttVisitDto);

}
