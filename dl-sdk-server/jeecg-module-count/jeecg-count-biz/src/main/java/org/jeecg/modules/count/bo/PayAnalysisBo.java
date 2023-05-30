package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: 用于数据库返回我们付费数据
 * @author: chenglin
 * @date: 2023年05月24日 15:13
 */
@Data
public class PayAnalysisBo implements Serializable {
    private Date dateTime;

    private Integer id;

    private Integer countUser;

    private Integer firstPayUser;

    private BigDecimal firstMoney;
}
