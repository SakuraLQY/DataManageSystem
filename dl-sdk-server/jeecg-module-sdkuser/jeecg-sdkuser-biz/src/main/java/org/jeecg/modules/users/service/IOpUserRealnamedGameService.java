package org.jeecg.modules.users.service;

import org.jeecg.modules.users.entity.OpUserRealnamedGame;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: op_user_realnamed_game
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
public interface IOpUserRealnamedGameService extends IService<OpUserRealnamedGame> {

    /**
     * @param realnamedGame: 用户游戏实名信息
     * @return: void
     * @description: 保存用户游戏实名信息
     * @author: xmh
     * @date: 2022/12/6 10:57
     */
    void editUserRealnamedGame(OpUserRealnamedGame realnamedGame);

    /**
     * @param userId:    用户ID
     * @param pkgId: 一级游戏包ID
     * @return: OpUserRealnamedGame
     * @description: 获取用户已经实名认证的游戏
     * @author: xmh
     * @date: 2022/12/6 11:02
     */
    OpUserRealnamedGame getOpUserRealnamedGameByUserIdAndPkgId(Integer userId,
        Integer pkgId);
}
