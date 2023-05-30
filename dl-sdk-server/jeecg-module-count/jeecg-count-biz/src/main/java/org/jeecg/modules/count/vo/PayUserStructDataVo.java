package org.jeecg.modules.count.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: PayUserStructDataVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/11 10:48
 */
@Data
public class PayUserStructDataVo {

    /**
     * 用户注册日期
     */
    private String userCreateTime;

    /**
     * 金额
     */
    private BigDecimal money;
}
