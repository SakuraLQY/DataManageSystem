package org.jeecg.modules.users.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.users.entity.OpUserRealnamedGame;
import org.jeecg.modules.users.mapper.OpUserRealnamedGameMapper;
import org.jeecg.modules.users.service.IOpUserRealnamedGameService;
import org.jeecg.modules.users.util.RedisUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description: op_user_realnamed_game
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpUserRealnamedGameServiceImpl extends
    ServiceImpl<OpUserRealnamedGameMapper, OpUserRealnamedGame> implements
    IOpUserRealnamedGameService {

    @Resource
    private OpUserRealnamedGameMapper opUserRealnamedGameMapper;

    @Override
    @CacheEvict(cacheNames = RedisUtils.OP_USER_REALNAMED_GAME, key = "#realnamedGame.userId + ':' + #realnamedGame.pkgId")
    public void editUserRealnamedGame(OpUserRealnamedGame realnamedGame) {
        OpUserRealnamedGame userGame = getOpUserRealnamedGameByUserIdAndPkgId(
            realnamedGame.getUserId(), realnamedGame.getPkgId());
        realnamedGame.setUpdateTime(new Date());
        // 存在更新，不存在插入
        if (oConvertUtils.isEmpty(userGame)) {
            realnamedGame.setCreateTime(new Date());
            opUserRealnamedGameMapper.insert(realnamedGame);
        } else {
            realnamedGame.setId(userGame.getId());
            opUserRealnamedGameMapper.updateById(realnamedGame);
        }
    }

    @Override
    @Cacheable(cacheNames = RedisUtils.OP_USER_REALNAMED_GAME, key = "#userId + ':' + #pkgId")
    public OpUserRealnamedGame getOpUserRealnamedGameByUserIdAndPkgId(Integer userId,
        Integer pkgId) {
        LambdaQueryWrapper<OpUserRealnamedGame> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpUserRealnamedGame::getUserId, userId);
        wrapper.eq(OpUserRealnamedGame::getPkgId, pkgId);
        return opUserRealnamedGameMapper.selectOne(wrapper);
    }
}
