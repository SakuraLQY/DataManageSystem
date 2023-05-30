package org.jeecg.modules.users.constant;

public interface OperateTypeConstant {

    /**
     * 修改密码 根据旧密码修改新密码
     */
    Integer UPDATE_PASSWORD = 1;

    /**
     * 重置密码(找回密码)
     */
    Integer RESET_PASSWD = 2;

    /**
     * 绑定手机
     */
    Integer BIND_PHONE = 3;

    /**
     * 解绑手机
     */
    Integer UNBIND_PHONE = 4;

}
