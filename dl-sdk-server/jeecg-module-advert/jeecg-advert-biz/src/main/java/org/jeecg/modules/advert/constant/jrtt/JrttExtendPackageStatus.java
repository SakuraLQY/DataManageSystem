package org.jeecg.modules.advert.constant.jrtt;

/**
 * @Description: 今日头条应用分包状态
 * @Author: chenyw
 * @Date: 2023-02-16
 * @Version: V1.0
 */
public class JrttExtendPackageStatus {

    /**
     * 全部（默认值）
     **/
    public static final String ALL = "ALL";

    /**
     * 审核中
     **/
    public static final String AUDIT_DOING = "AUDIT_DOING";

    /**
     * 审核失败
     **/
    public static final String AUDIT_REJECTED = "AUDIT_REJECTED";

    /**
     * 审核成功，即待发布
     **/
    public static final String AUDIT_ACCEPTED = "AUDIT_ACCEPTED";

    /**
     * 已发布
     **/
    public static final String ENABLE = "ENABLE";

}
