package org.jeecg.modules.users.service;

import org.jeecg.modules.users.entity.OpUserRealnameInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: op_user_realname_info
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
public interface IOpUserRealnameInfoService extends IService<OpUserRealnameInfo> {

    /**
     * @param opUserRealnameInfo: 用户实名信息
     * @return: void
     * @description: 保存用户实名信息
     * @author: xmh
     * @date: 2022/12/6 14:21
     */
    void editUserRealnameInfo(OpUserRealnameInfo opUserRealnameInfo);

    /**
     * @param userId: 用户ID
     * @return: OpUserRealnameInfo
     * @description: 获取用户实名信息
     * @author: xmh
     * @date: 2022/12/6 14:04
     */
    OpUserRealnameInfo getOpUserRealnameInfoByUserId(Integer userId);
}
