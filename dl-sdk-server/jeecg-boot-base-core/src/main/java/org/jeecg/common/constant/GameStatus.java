package org.jeecg.common.constant;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.common.constant
 * @className: GameStatus
 * @author: Eric
 * @description: TODO
 * @date: 2022/12/21 19:37
 * @version: 1.0
 */
public class GameStatus {
    //发货成功
    public static final Integer GAME_STATUS_SUCCESS = 1000;
    //平台币充值失败
    public static final Integer GAME_STATUS_PLATFORM_CURRENCY_FAIL = -1002;
    //发货返回结果为空
    public static final Integer GAME_STATUS_RESULT_NULL = -1001;
    //响应码为空
    public static final Integer GAME_STATUS_RET_CODE_NULL = -1003;
    //发货结果为不成功
    public static final Integer GAME_STATUS_RESULT_FAIL = -1004;
}
