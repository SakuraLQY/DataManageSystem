package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.advert.entity.OpPutAccount;
import org.jeecg.modules.advert.mapper.OpPutAccountMapper;
import org.jeecg.modules.advert.service.IOpPutAccountService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_put_account
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
@Slf4j
public class OpPutAccountServiceImpl extends
    ServiceImpl<OpPutAccountMapper, OpPutAccount> implements
    IOpPutAccountService {


}
