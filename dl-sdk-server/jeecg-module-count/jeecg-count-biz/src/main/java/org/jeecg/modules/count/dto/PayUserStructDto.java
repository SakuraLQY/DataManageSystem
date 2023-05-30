package org.jeecg.modules.count.dto;

import java.util.ArrayList;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.dto
 * @className: PayUserStructDto
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/11 10:25
 */
@Data
public class PayUserStructDto {

    /**
     * 游戏id
     */
    private ArrayList gameId;

    /**
     * 子游戏ID
     */
    private ArrayList subGameId;

    /**
     * 渠道类型ID
     */
    private ArrayList channelTypeId;

    /**
     * 渠道ID
     */
    private ArrayList channelId;

    /**
     * 渠道子账号ID
     */
    private ArrayList channelSubAccountId;

    /**
     * pkgId
     */
    private ArrayList pkgId;

    /**
     * 广告id
     */
    private ArrayList dealId;


    /**
     * 用户注册起始时间
     */
    private String userStartTime;

    /**
     * 用户注册结束时间
     */
    private String userEndTime;

    /**
     * 数据类型
     */
    private String dataType;
}
