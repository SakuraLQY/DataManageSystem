package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.jeecg.modules.advert.entity.OpChannel;
import org.jeecg.modules.advert.mapper.OpChannelMapper;
import org.jeecg.modules.advert.service.IOpChannelService;
import org.jeecg.modules.advert.vo.LogCallbackDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: op_channel
 * @Author: jeecg-boot
 * @Date:   2023-01-05
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpChannelServiceImpl extends ServiceImpl<OpChannelMapper, OpChannel> implements
    IOpChannelService {

    @Autowired
    private OpChannelMapper opChannelMapper;

    @Override
    public List<OpChannel> selectByTypeId(Integer typeId) {
        LambdaQueryWrapper<OpChannel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpChannel::getTypeId,typeId);
        return opChannelMapper.selectList(wrapper);
    }

    public IPage getCallback(Integer tableName,Page page, QueryWrapper queryWrapper){

        List<LogCallbackDataVo> callbackData = opChannelMapper
            .getCallbackData(tableName, page, queryWrapper);

        return page.setRecords(callbackData);
    }

    public OpChannel selectById(Integer id){
        QueryWrapper objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("id",id);
        OpChannel opChannel = opChannelMapper.selectOne(objectQueryWrapper);
        return opChannel;
    }
}
