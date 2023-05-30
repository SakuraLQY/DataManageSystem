package org.jeecg.common.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ParseAliveDto {

    /**
     * 子游戏Id
     */
    private Integer subGameId;

    /**
     *  游戏Id
     */
    private Integer gameId;

    /**
     *  渠道Id
     */
    private Integer channelId;

    /**
     *  渠道类型Id
     */
    private Integer channelTypeId;

    /**
     *  渠道子账号Id
     */
    private Integer channelSubAccountId;

    /**
     * 广告id
     */
    private Integer dealId;

    /**
     * 一级游戏包ID
     */
    private Integer pkgId;

    /**
     * 唯一设备标识
     */
    private String uniqueId;

    /**
     * 时间戳(秒)
     */
    private Long time;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 首服ID
     */
    private Integer serverId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色等级
     */
    private Integer roleLevel;

    /**
     * 角色名字
     */
    private String roleName;

    /**
     * IP地址
     */
    private String ClientIp;

    /**
     * 服务器名
     */
    private String serverName;
}
