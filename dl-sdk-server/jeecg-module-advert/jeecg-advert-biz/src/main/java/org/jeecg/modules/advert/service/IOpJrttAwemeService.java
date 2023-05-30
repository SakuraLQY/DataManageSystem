package org.jeecg.modules.advert.service;

import java.util.List;
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListListResponse;
import org.jeecg.modules.advert.entity.OpJrttAweme;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: op_jrtt_aweme
 * @Author: jeecg-boot
 * @Date:   2023-02-22
 * @Version: V1.0
 */
public interface IOpJrttAwemeService extends IService<OpJrttAweme> {

    /**
     * @return java.util.List<org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListListResponse>
     * @author chenyw
     * @description 同步抖音授权关系列表
     * @date 20:13 2023/2/20/020
     **/
    List<OpJrttAweme> syncAwemeByAccountId(Integer accountId);

}
