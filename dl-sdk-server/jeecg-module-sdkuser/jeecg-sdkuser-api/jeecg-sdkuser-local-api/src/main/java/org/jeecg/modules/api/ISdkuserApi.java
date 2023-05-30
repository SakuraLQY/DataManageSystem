package org.jeecg.modules.api;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import java.math.BigDecimal;
import java.util.List;
import org.jeecg.modules.vo.OpRegisterLoginSwitchVo;
import org.jeecg.modules.vo.OpUserVo;

public interface ISdkuserApi {

    /**
     * @param userId    用户id
     * @param gameId    游戏id
     * @param subGameId 子游戏id
     * @param ip        ip
     * @param device    设备号
     * @return java.lang.Boolean
     * @author chenyw
     * @description 校验支付黑名单
     * @date 19:12 2022/12/22/022
     **/
    Boolean checkBlackUser(Integer userId, Integer gameId, Integer subGameId, String ip,
        String device);

    /**
     * @param id:
     * @return OpUserVo
     * @author xmh
     * @description 根据 id 获取用户信息
     * @date 2022/12/23 15:03
     */
    OpUserVo getOpUserVoById(Integer id);

    /**
     * @param userId:          用户ID
     * @param platformCurrent: 减少的平台币
     * @return boolean
     * @author xmh
     * @description 更新用户平台币
     * @date 2022/12/24 11:04
     */
    boolean updateUserPC(Integer userId, Integer platformCurrent, boolean isAdd);

    /**
     * @param dealIdList
     * @return java.util.List<org.jeecg.modules.vo.OpRegisterLoginSwitchVo>
     * @Author lili
     * @Description 通过广告ID列表得到数据
     * @Date 18:08 2023/5/4
     **/
    List<OpRegisterLoginSwitchVo> getListByDealIdList(List<Integer> dealIdList);

    Boolean saveOpRegisterLoginSwitch(OpRegisterLoginSwitchVo opRegisterLoginSwitchVo);

}
