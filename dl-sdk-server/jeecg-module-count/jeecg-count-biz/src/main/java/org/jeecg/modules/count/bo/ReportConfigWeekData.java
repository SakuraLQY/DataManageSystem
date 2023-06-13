package org.jeecg.modules.count.bo;

import java.util.List;
import lombok.Data;

/**
 * @Description: ReportConfigWeek 周报子配置
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class ReportConfigWeekData {

    /**
     * 二级分组
     **/
    private String secondGroup;

    /**
     * 游戏名称
     **/
    private String gameNickName;

    /**
     * 渠道名称
     **/
    private String channelNickName;

    /**
     * 游戏id
     **/
    private List<Integer> gameId;

    /**
     * 子游戏id
     **/
    private List<Integer> subGameId;

    /**
     * 一级游戏包id
     **/
    private List<Integer> pkgId;

    /**
     * 渠道类型id
     **/
    private List<Integer> channelTypeId;

    /**
     * 渠道id
     **/
    private List<Integer> channelId;

    /**
     * 渠道子账号id
     **/
    private List<Integer> channelSubAccountId;

}
