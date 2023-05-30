package org.jeecg.modules.count.service;

import com.alibaba.fastjson.JSONObject;

public interface IKafkaLogService {

    JSONObject searchKafkaLog(Long position,Integer pageNo, Integer pageSize);

    Long searchKafkaSum();

}
