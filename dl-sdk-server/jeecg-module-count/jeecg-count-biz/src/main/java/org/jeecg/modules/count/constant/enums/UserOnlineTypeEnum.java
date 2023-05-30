package org.jeecg.modules.count.constant.enums;

/**
 * （游戏，子游戏，渠道游戏包，广告）
 * @author: lili
 */
public enum UserOnlineTypeEnum {

    /**
     * 游戏
     */
    GAME(1,"game_id"),

    /**
     * 子游戏
     */
    SUB_GAME(2,"sub_game_id"),

    /**
     * 广告
     */
    DEAL(3,"deal_id"),

    /**
     * 渠道
     */
    CHANNEL(4,"channel_id");

    private Integer code;
    private String columnName;

    /**
     * 构造器
     *
     * @param columnName 名称
     * @param code 模板编码
     */
    private UserOnlineTypeEnum(int code, String columnName) {
        this.code = code;
        this.columnName = columnName;
    }

    /**
     * 根据code获取名称
     *
     * @param code
     * @return
     */
    public static String getTemplatePathByConfig(Integer code) {
        return getCgformEnumByConfig(code).columnName;
    }


    /**
     * 根据code找枚举
     *
     * @param code
     * @return
     */
    public static UserOnlineTypeEnum getCgformEnumByConfig(Integer code) {
        for (UserOnlineTypeEnum e : UserOnlineTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

}
