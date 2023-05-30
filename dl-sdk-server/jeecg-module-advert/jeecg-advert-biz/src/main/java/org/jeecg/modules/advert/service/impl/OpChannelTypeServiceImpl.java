package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.modules.advert.entity.OpChannelType;
import org.jeecg.modules.advert.mapper.OpChannelTypeMapper;
import org.jeecg.modules.advert.service.IOpChannelTypeService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_channel_type
 * @Author: jeecg-boot
 * @Date:   2023-01-05
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpChannelTypeServiceImpl extends ServiceImpl<OpChannelTypeMapper, OpChannelType> implements IOpChannelTypeService {

}
