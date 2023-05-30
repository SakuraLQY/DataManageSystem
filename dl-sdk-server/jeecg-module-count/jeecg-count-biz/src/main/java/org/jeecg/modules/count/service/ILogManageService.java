package org.jeecg.modules.count.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.modules.count.dto.LogDeviceDataDto;
import org.springframework.web.servlet.ModelAndView;

public interface ILogManageService {

    /**
     * @param page:
     * @param logDeviceDataDto:
     * @return IPage
     * @author Fkh
     * @description 获取设备数据
     * @date 2023/5/13 14:46
     */
    IPage getDeviceData(Page page,LogDeviceDataDto logDeviceDataDto);

    ModelAndView deviceDataExportXls(HttpServletRequest request, LogDeviceDataDto logDeviceDataDto, String title);
}
