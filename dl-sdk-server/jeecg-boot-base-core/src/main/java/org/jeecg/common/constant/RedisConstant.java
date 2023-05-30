package org.jeecg.common.constant;

public class RedisConstant {

    /**
     * 角色
     */
    public static final String OP_ROLE = "op_role";

    /**
     * 用户
     */
    public static final String OP_USER = "op_user";

    /**
     * 注册设备限制
     */
    public static final String REGISTER_LIMIT_DEVICE = "register_limit_device:";

    /**
     * 注册ip限制
     */
    public static final String REGISTER_LIMIT_IP = "register_limit_ip:";

    /**
     * 验证码 设备发送限制
     */
    public static final String CODE_LIMIT_DEVICE = "code_limit_device:";

    /**
     * 验证码 用户名发送限制
     */
    public static final String CODE_LIMIT_USERNAME = "code_limit_username:";

    /**
     * 验证码 ip发送限制
     */
    public static final String CODE_LIMIT_IP = "code_limit_ip:";

    /**
     * 验证码key
     */
    public static final String CODE_KEY = "code:";

    public static String getRegisterLimitDeviceKey(String device, String date) {
        return REGISTER_LIMIT_DEVICE + date + ":" + device + ":";
    }

    public static String getRegisterLimitIpKey(String ip, String date) {
        return REGISTER_LIMIT_IP + date + ":" + ip + ":";
    }

    public static String getCodeLimitDevice(String device) {
        return CODE_LIMIT_DEVICE + device + ":";
    }

    public static String getCodeLimitUsername(String username) {
        return CODE_LIMIT_USERNAME + username + ":";
    }

    public static String getCodeLimitIp(String ip) {
        return CODE_LIMIT_IP + ip + ":";
    }

    public static String getCodeKey(String phone) {
        return CODE_KEY + phone + ":";
    }

}
