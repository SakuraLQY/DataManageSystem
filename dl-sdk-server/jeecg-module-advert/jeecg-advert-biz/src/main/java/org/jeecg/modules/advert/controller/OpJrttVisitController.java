package org.jeecg.modules.advert.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.advert.dto.JrttVisitDto;
import org.jeecg.modules.advert.service.IJrttVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: op_jrtt_action
 * @Author: jeecg-boot
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Api(tags = "今日头条监测链接回调")
@RestController
@RequestMapping("/advert/jrttVisit")
@Slf4j
public class OpJrttVisitController {

    @Autowired
    private IJrttVisitService jrttVisitService;

    /**
     * 点击广告回传监测链接
     *
     * @param jrttVisitDto
     * @return
     */
    @AutoLog(value = "点击广告回传监测链接")
    @ApiOperation(value = "点击广告回传监测链接", notes = "点击广告回传监测链接")
    @GetMapping("/click")
    public void click(JrttVisitDto jrttVisitDto) {
        jrttVisitService.click(jrttVisitDto);
    }

}
