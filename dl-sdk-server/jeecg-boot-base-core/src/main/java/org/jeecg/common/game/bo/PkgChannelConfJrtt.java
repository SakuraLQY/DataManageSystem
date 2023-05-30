package org.jeecg.common.game.bo;

import lombok.Data;

/**
 * @Description: 今日头条渠道参数
 * @Author: jeecg-boot
 * @Date:   2022-12-12
 * @Version: V1.0
 */
@Data
public class PkgChannelConfJrtt {

    /**
     * appId
     **/
    public String appId;

    /**
     * appKey 用于sdk事件回传(支付)
     **/
    public String appKey;

    /**
     * 应用包id
     **/
    public String packageId;

    /**
     * 包名
     **/
    public String packageName;

    /**
     * 创建者账号id
     **/
    public Integer createAccountId;

}
