package org.jeecg.modules.count.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.count.service.IKafkaLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.controller
 * @className: KafkaLogController
 * @author: fkh
 * @description: TODO
 * @date: 2023/4/25 10:14
 */
@Api(tags="kafkaLog")
@RestController
@RequestMapping("/count/kafka")
@Slf4j
public class KafkaLogController {

    @Autowired
    private IKafkaLogService kafkaLogService;

    @ApiOperation(value="kafka日志查询")
    @GetMapping(value = "/list")
    public Result<JSONObject> queryPageList(Long position,
        @RequestParam(name="pageNo", defaultValue="1L")Integer pageNo,
        @RequestParam(name="pageSize", defaultValue="10L")Integer  pageSize,
        HttpServletRequest req) {
        JSONObject jsonObject = kafkaLogService.searchKafkaLog(position, pageNo, pageSize);
        return Result.OK(jsonObject);
    }

    @ApiOperation(value = "获取最新kafka消费者总数")
    @GetMapping(value = "/sum")
    public Result<Long> queryKafkaSum(){

        Long data = kafkaLogService.searchKafkaSum();
        return Result.OK(data);
    }
}
