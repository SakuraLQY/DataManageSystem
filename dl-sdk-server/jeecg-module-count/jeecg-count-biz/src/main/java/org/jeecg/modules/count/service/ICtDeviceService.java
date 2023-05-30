package org.jeecg.modules.count.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParseRegisterDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.modules.count.entity.CtDevice;

/**
 * @Description: ct_device
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
public interface ICtDeviceService extends IService<CtDevice> {

    /**
     * @param parseStartDto: start解析dto
     * @return CtDevice
     * @author Fkh
     * @description  start解析对device表进行操作
     * @date 2023/4/23 14:19
     */
    CtDevice searchStartDevice(ParseStartDto parseStartDto);

    /**
     * @param parseLoginDto: login解析dto
     * @return CtDevice
     * @author Fkh
     * @description  Login解析对device表进行操作
     * @date 2023/4/23 14:19
     */
    CtDevice searchLoginDevice(ParseLoginDto parseLoginDto);

    /**
     * @param parseRegisterDto: register解析dto
     * @return void
     * @author Fkh
     * @description register解析对device表进行操作
     * @date 2023/4/23 14:25
     */
    void searchRegisterDevice(ParseRegisterDto parseRegisterDto);

    /**
     * @param parseAliveDto: alive解析dto
     * @return CtDevice
     * @author Fkh
     * @description alive解析对device表进行操作
     * @date 2023/4/23 14:27
     */
    CtDevice searchAliveDevice(ParseAliveDto parseAliveDto);

    /**
     * @param parsePayDto pay解析dto
     * @return org.jeecg.modules.count.entity.CtDevice
     * @author chenyw
     * @description pay解析对device表进行操作
     * @date 16:41 2023/4/23/023
     **/
    CtDevice parsePayDevice(ParsePayDto parsePayDto);

}
