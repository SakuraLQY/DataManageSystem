package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.modules.advert.dto.LogCallbackDataDto;
import org.jeecg.modules.advert.entity.AtVisit;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: jrtt_deal
 * @Author: jeecg-boot
 * @Date: 2023-02-14
 * @Version: V1.0
 */
public interface IAtVisitService extends IService<AtVisit> {

    /**
     * @param uniqueId   唯一设备标识
     * @param uniqueType 唯一设备类型
     * @param dealId     广告id
     * @param visitData  回调参数
     * @author chenyw
     * @description 保存归因
     * @date 16:10 2023/3/31/031
     **/
    void saveVisit(String uniqueId, String uniqueType, Integer dealId, String visitData);

    /**
     * @param uniqueId
     * @param dealId
     * @author chenyw
     * @description 获取归因
     * @date 16:32 2023/3/31/031
     **/
    AtVisit getVisit(String uniqueId, Integer dealId);

    /**
     * @param null
     * @return null
     * @author chenyw
     * @description 根据idfa获取归因
     * @date 16:03 2023/4/24/024
     **/
    AtVisit getVisitByIdfaOrIp(Integer pkgId, String idfa, String ip);


    /**
     * @param page:
     * @param logCallbackDataDto:
     * @return IPage
     * @author Fkh
     * @description 获取回调数据
     * @date 2023/5/13 14:39
     */
    IPage getCallbackData(Page page, LogCallbackDataDto logCallbackDataDto);

    ModelAndView callbackDataExportXls(HttpServletRequest request, LogCallbackDataDto logCallbackDataDto, String title);

}
