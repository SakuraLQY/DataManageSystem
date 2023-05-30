package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.ChannelDetailBo;
import org.jeecg.modules.count.bo.ChannelTotalBo;
import org.jeecg.modules.count.entity.ChannelDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 渠道明细表数据
 * @Author: jeecg-boot
 * @Date:   2023-05-11
 * @Version: V1.0
 */

public interface ChannelDetailsMapper extends BaseMapper<ChannelDetails> {

    /**@Params gameId,channelId,subGameId
     * @author chenglin
     * @description 动态传参数查询渠道详细信息
     * @date 13:07 2023/05/12
     **/
    List<ChannelDetailBo> queryListByDetail(@Param(Constants.WRAPPER) QueryWrapper q
        ,@Param("gameId")String gameId,@Param("channelId")String channelId,@Param("subGameId")String subGameId);

    /**params gameId,channelId,subGameId
     * @author chenglin
     * @description 动态传参数查询渠道完整信息
     * @date  13:06 2023/05/12
     **/
    List<ChannelDetailBo> queryListByTotal(@Param(Constants.WRAPPER) QueryWrapper  q);

    /**params gameId
     * @author chenglin
     * @description 获取游戏的名字
     * @date 17:05 2023/05/13
     **/
    String getGameNameById(Integer gameId);

    /**params subGameId
     * @author chenglin
     * @description 获取子游戏的名字
     * @date  17:05 2023/05/13
     **/
    String getSubGameNameByid(Integer subGameId);

    /**params channelId
     * @author chenglin
     * @description 获取渠道信息名字
     * @date  17:05 2023/05/13
     **/
    String getChannelNameById(Integer channelId);
}
