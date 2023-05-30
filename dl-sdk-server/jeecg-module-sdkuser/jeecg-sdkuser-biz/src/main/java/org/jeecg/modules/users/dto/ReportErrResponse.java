package org.jeecg.modules.users.dto;

import lombok.Data;

@Data
public class ReportErrResponse {
    private int no;
    private int errcode;
    private String errmsg;
}
