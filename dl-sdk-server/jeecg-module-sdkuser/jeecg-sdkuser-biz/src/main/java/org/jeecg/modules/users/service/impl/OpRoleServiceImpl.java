package org.jeecg.modules.users.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Date;
import org.jeecg.modules.users.entity.OpRole;
import org.jeecg.modules.users.mapper.OpRoleMapper;
import org.jeecg.modules.users.service.IOpRoleService;
import org.jeecg.modules.users.util.RedisUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Description: op_role
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpRoleServiceImpl extends ServiceImpl<OpRoleMapper, OpRole> implements IOpRoleService {

    @Cacheable(cacheNames = RedisUtils.OP_ROLE, key = "#userId.toString()+#serverId+#roleId")
    public OpRole getOpRole(Integer subGameId, Integer pkgId, Integer userId, Integer serverId, String roleId) {
        OpRole opRole = getOne(new QueryWrapper<OpRole>().lambda()
                .eq(OpRole::getSubGameId, subGameId)
                .eq(OpRole::getPkgId, pkgId)
                .eq(OpRole::getUserId, userId)
                .eq(OpRole::getServerId, serverId)
                .eq(OpRole::getRoleId, roleId));
        return opRole;
    }

    @CachePut(cacheNames = RedisUtils.OP_ROLE, key = "#opRole.getUserId().toString()+#opRole.getServerId()+#opRole.getRoleId()")
    public OpRole insertOpRoleById(OpRole opRole) {
        Date date = new Date();
        opRole.setCreateTime(date);
        opRole.setUpdateTime(date);
        save(opRole);
        return opRole;
    }

    @CachePut(cacheNames = RedisUtils.OP_ROLE, key = "#opRole.getUserId().toString()+#opRole.getServerId()+#opRole.getRoleId()")
    public OpRole updateOpRoleById(OpRole opRole) {
        Date date = new Date();
        opRole.setUpdateTime(date);
        updateById(opRole);
        return opRole;
    }
}
