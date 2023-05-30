package org.jeecg.modules.count.service;

import java.math.BigDecimal;
import java.util.Date;
import org.jeecg.modules.count.entity.CtDailyPaybackDev;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: ct_daily_payback_dev
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
public interface ICtDailyPaybackDevService extends IService<CtDailyPaybackDev> {

    /**
     * @param dailyId
     * @param date
     * @param money
     * @author chenyw
     * @description 解析回本设备
     * @date 10:12 2023/4/21/021
     **/
    void parsePaybackDev(Integer dailyId, Date deviceDate, BigDecimal money, Date logDate);

}
