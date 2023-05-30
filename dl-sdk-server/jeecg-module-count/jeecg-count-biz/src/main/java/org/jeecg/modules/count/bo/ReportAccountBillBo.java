package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @description: 用来查询出对账号报表的相关信息
 * @author: chenglin
 * @date: 2023年05月23日 11:34
 */
@Data
public class ReportAccountBillBo implements Serializable {
    //名称
    private String nickName;
    //账号人
    private String putUser;
    //账号
    private String account;

    private Date dateTime;

    private String createUser;
}
