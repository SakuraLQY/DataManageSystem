package org.jeecg.modules.count.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.kafka.dto.ParseRegisterDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.modules.count.dto.CallbackOperationDto;
import org.jeecg.modules.count.dto.CtCallbackDto;
import org.jeecg.modules.count.entity.CtCallback;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.vo.CtCallbackVo;

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

    /**
     * @param page
     * @param ctCallbackDto
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author chenyw
     * @description 分页获取渠道回调数据
     * @date 16:16 2023/5/15/015
     **/
    IPage<CtCallbackVo> getDeviceCallbackData(Page page, CtCallbackDto ctCallbackDto);

    /**
     * @param callbackOperationDto 
     * @author chenyw
     * @description 回调渠道数据操作
     * @date 19:39 2023/5/15/015
     **/
    void callbackOperation(CallbackOperationDto callbackOperationDto);

}
