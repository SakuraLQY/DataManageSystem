package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;


/**
 * @Description: SummaryAdvertDailyBo 获取小时数据
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class GetHourBo {

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
     * 广告ID
     */
    private List<Integer> dealId;
    /**
     * 注册开始日期
     */
    private String regStartTime;
    /**
     * 注册结束日期
     */
    private String regEndTime;
    /**
     * 字段类型
     */
    private String level;

}
