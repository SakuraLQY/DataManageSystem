package org.jeecg.modules.users.service;

import org.jeecg.modules.users.bo.CheckAdultData;
import org.jeecg.modules.users.entity.OpUserOnline;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: op_user_online
 * @Author: jeecg-boot
 * @Date:   2022-11-30
 * @Version: V1.0
 */
public interface IOpUserOnlineService extends IService<OpUserOnline> {

    void report();

    /**
     *
     * @param subGameId 子游戏id
     * @param userId 用户id
     * @param bt 上报类型 0：下线，1：上线
     * @param bizId 游戏备案码
     * @param device 设备
     * @param pi 实名认证
     * @return
     */
    boolean saveOpUserOnline(Integer subGameId, Integer pkgId, Integer userId, Integer bt, String bizId, String device, String pi);

    /**
     * 检测用用户是否成年
     * @param idNum 身份证
     * @return CheckAdultData：用户是否成年和剩余时间
     */
    CheckAdultData checkAdult(String idNum);
}
