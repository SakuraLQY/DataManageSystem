package org.jeecg.modules.count.constant;

/**
 * @Description: ct_callback回调事件类型
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
public class BlockadeType {

    /**
     * 封号
     **/
    public static final Integer BLOCKADE_USER = 1;

    /**
     * 解封
     **/
    public static final Integer NOT_BLOCKADE_USER = 2;

    /**
     * 封IP
     **/
    public static final Integer BLOCKADE_IP = 3;

    /**
     * 解封IP
     **/
    public static final Integer NOT_BLOCKADE_IP = 4;

}
