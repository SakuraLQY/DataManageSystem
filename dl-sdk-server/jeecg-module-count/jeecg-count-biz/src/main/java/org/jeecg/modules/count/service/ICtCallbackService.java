package org.jeecg.modules.count.service;

import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.kafka.dto.ParseRegisterDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.modules.count.entity.CtCallback;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: ct_callback
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
public interface ICtCallbackService extends IService<CtCallback> {

    /**
     * @param parsePayDto 
     * @author chenyw
     * @description 上报支付
     * @date 10:28 2023/4/24/024
     **/
    void parsePayCallback(ParsePayDto parsePayDto);

    /**
     * @param parseStartDto
     * @author chenyw
     * @description 上报激活
     * @date 20:45 2023/4/24/024
     **/
    void parseStartCallback(ParseStartDto parseStartDto);

    /**
     * @param parseLoginDto
     * @author chenyw
     * @description 上报注册
     * @date 20:45 2023/4/24/024
     **/
    void parseRegisterCallback(ParseLoginDto parseLoginDto);

}
