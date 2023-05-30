package org.jeecg.modules.users.bo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.constant.IsConstant;

/**
 * 判断是否进行未成年游戏限制，返回结果类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckAdultData {

    /**
     * 是否成年 true 已成年 false未成年
     */
    private Integer isAdult = IsConstant.YES;

    /**
     * 未成年剩余游戏时间
     */
    private Long remainingTime = 0L;
}
