package org.jeecg.modules.pay.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.modules.pay.entity.OpOrderCount;
import org.jeecg.modules.pay.mapper.OpOrderCountMapper;
import org.jeecg.modules.pay.service.IOpOrderCountService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_order_count
 * @Author: jeecg-boot
 * @Date:   2022-12-21
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpOrderCountServiceImpl extends ServiceImpl<OpOrderCountMapper, OpOrderCount> implements
    IOpOrderCountService {

}
