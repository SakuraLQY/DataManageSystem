package org.jeecg.modules.users.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.users.entity.OpUserRealnameInfo;
import org.jeecg.modules.users.mapper.OpUserRealnameInfoMapper;
import org.jeecg.modules.users.service.IOpUserRealnameInfoService;
import org.jeecg.modules.users.util.RedisUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description: op_user_realname_info
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpUserRealnameInfoServiceImpl extends
    ServiceImpl<OpUserRealnameInfoMapper, OpUserRealnameInfo> implements
    IOpUserRealnameInfoService {

    @Resource
    private OpUserRealnameInfoMapper opUserRealnameInfoMapper;

    @Override
    @CacheEvict(cacheNames = RedisUtils.OP_USER_REALNAME_INFO, key = "#opUserRealnameInfo.userId")
    public void editUserRealnameInfo(OpUserRealnameInfo opUserRealnameInfo) {
        OpUserRealnameInfo realnameInfo = getOpUserRealnameInfoByUserId(
            opUserRealnameInfo.getUserId());
        opUserRealnameInfo.setUpdateTime(new Date());
        // 存在更新，不存在插入
        if (oConvertUtils.isEmpty(realnameInfo)) {
            opUserRealnameInfo.setCreateTime(new Date());
            opUserRealnameInfoMapper.insert(opUserRealnameInfo);
        } else {
            opUserRealnameInfo.setId(realnameInfo.getId());
            opUserRealnameInfoMapper.updateById(opUserRealnameInfo);
        }
    }

    @Override
    @Cacheable(cacheNames = RedisUtils.OP_USER_REALNAME_INFO, key = "#userId")
    public OpUserRealnameInfo getOpUserRealnameInfoByUserId(Integer userId) {
        LambdaQueryWrapper<OpUserRealnameInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpUserRealnameInfo::getUserId, userId);
        return opUserRealnameInfoMapper.selectOne(wrapper);
    }
}
