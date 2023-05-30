package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.entity.OpPutAccount;

/**
 * @Description: op_put_account
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
@InterceptorIgnore(tenantLine = "true")
public interface OpPutAccountMapper extends BaseMapper<OpPutAccount> {

}
