package org.jeecg.modules.advert.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.advert.constant.EmptyVisitConstant;
import org.jeecg.modules.advert.constant.VisitTypeConstatnt;
import org.jeecg.common.advert.dto.JrttVisitDto;
import org.jeecg.modules.advert.service.IAtVisitService;
import org.jeecg.modules.advert.service.IJrttVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: jrtt_deal
 * @Author: jeecg-boot
 * @Date: 2023-02-14
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_gateway")
public class JrttVisitServiceImpl implements IJrttVisitService {

    @Autowired
    private IAtVisitService atVisitService;

    @Override
    public void click(JrttVisitDto jrttVisitDto) {
        if (jrttVisitDto.getDealId() == null) {
            return;
        }
        String visitData = JSONObject.toJSONString(jrttVisitDto);
        if (jrttVisitDto.getIdfa() != null && !EmptyVisitConstant.EMPTY_IDFA.equals(
            jrttVisitDto.getIdfa())
            && !EmptyVisitConstant.EMPTY_DEVICE.equals(jrttVisitDto.getIdfa())) {
            //当idfa不为空时，记录设备信息
            atVisitService.saveVisit(jrttVisitDto.getIdfa(), VisitTypeConstatnt.IDFA,
                jrttVisitDto.getDealId(), visitData);
        } else if (jrttVisitDto.getImei() != null && !EmptyVisitConstant.EMPTY_IEMI.equals(
            jrttVisitDto.getImei())) {
            //当imei不为空时，记录设备信息
            atVisitService.saveVisit(jrttVisitDto.getImei(), VisitTypeConstatnt.IMEI,
                jrttVisitDto.getDealId(), visitData);
        } else if (jrttVisitDto.getOaid() != null && !EmptyVisitConstant.EMPTY_OAID.equals(
            jrttVisitDto.getOaid())) {
            //当oaid不为空时，记录设备信息
            atVisitService.saveVisit(jrttVisitDto.getOaid(), VisitTypeConstatnt.OAID,
                jrttVisitDto.getDealId(), visitData);
        } else if (jrttVisitDto.getIp() != null && !EmptyVisitConstant.EMPTY_IP.equals(
            jrttVisitDto.getIp())) {
            atVisitService.saveVisit(jrttVisitDto.getIp(), VisitTypeConstatnt.IP,
                jrttVisitDto.getDealId(), visitData);
        }
    }

}
