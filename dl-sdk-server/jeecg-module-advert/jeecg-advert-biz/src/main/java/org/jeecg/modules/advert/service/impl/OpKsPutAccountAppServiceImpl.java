package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.modules.advert.entity.OpKsPutAccountApp;
import org.jeecg.modules.advert.mapper.OpKsPutAccountAppMapper;
import org.jeecg.modules.advert.service.IOpKsPutAccountAppService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_ks_put_account_app
 * @Author: jeecg-boot
 * @Date:   2023-03-07
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpKsPutAccountAppServiceImpl extends ServiceImpl<OpKsPutAccountAppMapper, OpKsPutAccountApp> implements IOpKsPutAccountAppService {

}
