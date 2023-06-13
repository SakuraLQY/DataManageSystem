package org.jeecg.modules.count.service;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import org.jeecg.modules.count.entity.CtReportConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: ct_report_config
 * @Author: jeecg-boot
 * @Date:   2023-05-19
 * @Version: V1.0
 */
public interface ICtReportConfigService extends IService<CtReportConfig> {

    /**
     * @param configName
     * @return com.alibaba.fastjson.JSONObject
     * @author chenyw
     * @description 更具配置名称获取
     * @date 10:53 2023/5/25/025
     **/
    List<JSONObject> getByConfigName(String configName);
}
