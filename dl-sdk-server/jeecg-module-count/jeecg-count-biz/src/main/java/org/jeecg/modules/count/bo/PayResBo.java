package org.jeecg.modules.count.bo;

import cn.hutool.core.date.DateTime;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @description: payRes的查询数据
 * @author: chenglin
 * @date: 2023年05月17日 18:12
 */
@Data
public class PayResBo implements Serializable {

    private Date dateTime;

    private Integer userId;

    private Integer payUserLoginMask;

    private Date firstPayTime;
}
