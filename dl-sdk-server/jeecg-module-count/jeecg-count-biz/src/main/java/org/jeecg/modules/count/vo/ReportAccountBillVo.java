package org.jeecg.modules.count.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @description: 返回账单的信息
 * @author: chenglin
 * @date: 2023年05月23日 11:02
 */
@Data
public class ReportAccountBillVo implements Serializable {
    /**账号昵称**/
    private String nickName;
    /**账号**/
    private String account;
    /**日期**/
    private String dateTime;
    /**类型**/
    private String type;
    /**金额**/
    private BigDecimal cost;
    /**账号余额**/
    private BigDecimal surplusAmount;
    /**创建人**/
    private String createUser;
    /**备注**/
    private String desc;
    /**
     游戏+展示+下载拼接
     **/
    private String OtherData;
}
