package org.jeecg.modules.advert.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.modules.advert.entity.OpJrttPutAccountApp;
import org.jeecg.modules.advert.mapper.OpPutAccountAppMapper;
import org.jeecg.modules.advert.service.IOpJrttPutAccountAppService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_jrtt_put_account_app
 * @Author: jeecg-boot
 * @Date:   2023-01-09
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpJrttPutAccountAppServiceImpl extends ServiceImpl<OpPutAccountAppMapper, OpJrttPutAccountApp> implements
    IOpJrttPutAccountAppService {

}
