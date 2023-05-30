package org.jeecg.modules.users.service;

import org.jeecg.modules.users.entity.OpRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: op_role
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
public interface IOpRoleService extends IService<OpRole> {

    /**
     * getOpRole.
     *
     * @description
     * @author chenyw
     * @date 2022/12/9/009 12:36
     * @param: gameId
     * @param: userId
     * @param: serverId
     * @param: roleId
     * @return: org.jeecg.modules.users.entity.OpRole
     */
    OpRole getOpRole(Integer subGameId, Integer pkgId, Integer userId, Integer serverId, String roleId);

    /**
     * insertOpRoleById.
     *
     * @description
     * @author chenyw
     * @date 2022/12/9/009 12:36
     * @param: opRole
     * @return: org.jeecg.modules.users.entity.OpRole
     */
    OpRole insertOpRoleById(OpRole opRole);

    /**
     * updateOpRoleById.
     *
     * @description
     * @author chenyw
     * @date 2022/12/9/009 12:36
     * @param: opRole
     * @return: org.jeecg.modules.users.entity.OpRole
     */
    OpRole updateOpRoleById(OpRole opRole);
}
