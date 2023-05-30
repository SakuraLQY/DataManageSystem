package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.modules.advert.dto.LogDeviceCallbackDataDto;
import org.jeecg.modules.advert.entity.AtUnique;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: at_unique
 * @Author: jeecg-boot
 * @Date: 2023-04-24
 * @Version: V1.0
 */
public interface IAtUniqueService extends IService<AtUnique> {

    /**
     * @param dealId 渠道游戏包id
     * @param uniqueId 第一设备id
     * @param deviceId 第二设备id
     * @param serialId 第三设备id
     * @param androidId 第四设备id
     * @param clientIp ip
     * @return java.lang.String 归因回调参数
     * @author chenyw
     * @description  编辑唯一归因
     * @date 14:38 2023/4/24/024
     **/
    String getUniqueVisit(Integer dealId, String uniqueId, String deviceId,
        String serialId, String androidId, String clientIp);


    /**
     * @param page: 分页
     * @param logDeviceCallbackDataDto:
     * @return IPage
     * @author Fkh
     * @description 获取设备回调数据
     * @date 2023/5/13 14:38
     */
    IPage getDeviceCallbackData(Page page, LogDeviceCallbackDataDto logDeviceCallbackDataDto);

    ModelAndView deviceCallbackDataExportXls(HttpServletRequest request, LogDeviceCallbackDataDto logDeviceCallbackDataDto, String title);
}
