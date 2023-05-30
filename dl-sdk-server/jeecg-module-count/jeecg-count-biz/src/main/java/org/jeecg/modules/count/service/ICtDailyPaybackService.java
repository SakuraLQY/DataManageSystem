package org.jeecg.modules.count.service;

import java.math.BigDecimal;
import java.util.Date;
import org.jeecg.modules.count.entity.CtDailyPayback;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: ct_daily_payback
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
public interface ICtDailyPaybackService extends IService<CtDailyPayback> {

    /**
     * @param dailyId
     * @param userDate
     * @param money
     * @author chenyw
     * @description 解析回本
     * @date 10:12 2023/4/21/021
     **/
    void parsePayback(Integer dailyId, Date userDate, BigDecimal money, Date logDate);

}
