package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @Description: GetWeekReportDailyBo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class GetWeekReportDailyBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 游戏
     */
    private List<Integer> gameId;
    /**
     * 子游戏
     */
    private List<Integer> subGameId;
    /**
     * 渠道游戏包
     */
    private List<Integer> pkgId;
    /**
     * 渠道类型id
     */
    private List<Integer> channelTypeId;
    /**
     * 渠道id
     */
    private List<Integer> channelId;
    /**
     * 渠道子账号id
     */
    private List<Integer> channelSubAccountId;
    /**
     * 注册开始日期
     */
    private String regStartTime;
    /**
     * 注册结束日期
     */
    private String regEndTime;

}
