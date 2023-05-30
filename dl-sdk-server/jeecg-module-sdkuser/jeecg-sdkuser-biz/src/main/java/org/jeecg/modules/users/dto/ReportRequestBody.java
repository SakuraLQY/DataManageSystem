package org.jeecg.modules.users.dto;

import lombok.Data;

@Data
public class ReportRequestBody {

    /* 序号 */
    private Integer no;
    /* 游戏内部会话标识 */
    private String si;
    /* 游戏行为类型 0：下线，1：上线 */
    private Integer bt;
    /* 行为发生时间戳，单位秒 */
    private Long ot;
    /* 上报类型 0：已实名，2：游客*/
    private Integer ct;
    /* 设备标识，游客模式设备标识，游客模式下必填 */
    private String di;
    /* 用户唯一标识，实名认证用户必填 */
    private String pi;


}
