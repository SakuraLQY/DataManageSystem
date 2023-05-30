package org.jeecg.modules.count.constant.enums;

/**
 * 汇总枚举 归类方式
 *
 * @date 2023-04-27
 * @author: jeecg-boot
 */
public enum SummaryEnum {

    /**
     * 全部
     */
    TYPE_ALL(1, "DATE(a.cost_day)", "DATE(a.time_daily)", "DATE(a.create_time)", "'全部游戏'"),
    /**
     * 按游戏
     */
    TYPE_GAME(2, "a.game_id", "a.game_id", "a.game_id", "d.game_name"),
    /**
     * 按子游戏
     */
    TYPE_SUB_GAME(3, "a.sub_game_id", "a.sub_game_id", "a.sub_game_id", "e.game_name"),
    /**
     * 按一级游戏包
     */
    TYPE_PKG(4, "a.pkg_id", "a.pkg_id", "a.pkg_id", "c.nick_name"),
    /**
     * 按渠道
     */
    TYPE_CAHNNEL(5, "a.channel_id", "a.channel_id", "a.channel_id", "f.channel_name"),
    /**
     * 按子渠道
     */
    TYPE_CHANNEL_SUB_ACCOUNT(6, "a.channel_sub_account_id", "a.channel_sub_account_id",
        "a.channel_sub_account_id", "g.sub_account_name"),
    /**
     * 按投放账号
     */
    TYPE_ACCOUNT(7, "a.account_id", "b.account_id", "b.account_id", "h.nick_name"),
    /**
     * 按广告
     */
    TYPE_DEAL(8, "a.deal_id", "a.deal_id", "a.deal_id", "b.deal_name");

    /**
     * 类型
     */
    Integer code;

    /**
     * 消耗汇总
     */
    String groupCost;

    /**
     * 日常汇总
     */
    String groupDaily;

    /**
     * 日常汇总
     */
    String groupOrder;

    /**
     * 名称
     */
    String groupName;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getGroupCost() {
        return groupCost;
    }

    public void setGroupCost(String groupCost) {
        this.groupCost = groupCost;
    }

    public String getGroupDaily() {
        return groupDaily;
    }

    public void setGroupDaily(String groupDaily) {
        this.groupDaily = groupDaily;
    }

    public String getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(String groupOrder) {
        this.groupOrder = groupOrder;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 构造器
     *
     * @param code       类型
     * @param groupCost  值
     * @param groupDaily 值
     * @param groupOrder 值
     */
    SummaryEnum(Integer code, String groupCost, String groupDaily, String groupOrder,
        String groupName) {
        this.code = code;
        this.groupCost = groupCost;
        this.groupDaily = groupDaily;
        this.groupOrder = groupOrder;
        this.groupName = groupName;
    }

    /**
     * 根据type获取枚举
     *
     * @param code
     * @return
     */
    public static SummaryEnum valueOfCode(Integer code) {
        for (SummaryEnum e : SummaryEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return TYPE_ALL;
    }
}
