package org.jeecg.modules.users.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.time.LocalDateTime;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.users.entity.OpUserOnline;
import org.springframework.stereotype.Repository;

/**
 * @Description: op_user_online
 * @Author: jeecg-boot
 * @Date:   2022-11-30
 * @Version: V1.0
 */
@Repository
public interface OpUserOnlineMapper extends BaseMapper<OpUserOnline> {

    //角色上报
    @Update("update op_user_online set logout_time = #{logoutTime} where user_id = #{userId} and sub_game_id = #{subGameId} and pkg_id = #{pkgId}")
    boolean updateLogoutTime(Integer userId, Integer subGameId, Integer pkgId, LocalDateTime logoutTime);

}
