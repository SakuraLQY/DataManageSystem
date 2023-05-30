package org.jeecg.common.constant;

/**
 * @Description: 打包状态
 * @Author: jeecg-boot
 * @Date: 2023-1-8
 * @Version: V1.0
 */
public interface PackStateConstant {

    /**
     * 等待操作
     **/
    Integer INIT = 0;

    /**
     * 待打包
     **/
    Integer WAIT = 1;

    /**
     * 打包中
     **/
    Integer PROCESSING = 2;

    /**
     * 打包成功
     **/
    Integer SUCCESS = 3;

    /**
     * 打包失败
     **/
    Integer FAILURE = 4;

    /**
     *  解析中
     **/
    Integer ANALYSIS_WAIT=5;

    /**
     * 解析成功
     **/
    Integer ANALYSIS_SUCCESS=6;

    /**
     * 解析失败
     **/
    Integer ANALYSIS_FALURE=7;
}
