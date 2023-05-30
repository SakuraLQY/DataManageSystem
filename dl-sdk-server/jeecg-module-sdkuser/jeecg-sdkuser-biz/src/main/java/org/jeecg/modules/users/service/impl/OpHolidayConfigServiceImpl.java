package org.jeecg.modules.users.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.crypto.MacSpi;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.RestUtil;
import org.jeecg.modules.users.entity.OpHolidayConfig;
import org.jeecg.modules.users.mapper.OpHolidayConfigMapper;
import org.jeecg.modules.users.dto.HolidayTimeDTO;
import org.jeecg.modules.users.service.IOpHolidayConfigService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: op_holiday_config
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpHolidayConfigServiceImpl extends
    ServiceImpl<OpHolidayConfigMapper, OpHolidayConfig> implements IOpHolidayConfigService {

    @Resource
    private OpHolidayConfigMapper opHolidayConfigMapper;

    @Override
    public void remove() {
        LambdaQueryWrapper<OpHolidayConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.lt(OpHolidayConfig::getHoliday, DateUtils.formatDate());
        opHolidayConfigMapper.delete(wrapper);
    }

    @Override
    public List<String> syncList(HolidayTimeDTO holidayTimeDTO) {
        List<String> msgList = new ArrayList<>();
        try {
            List<String> dateStr = findDates(holidayTimeDTO.getBeginTime(),
                holidayTimeDTO.getEndTime());
            if (dateStr == null || dateStr.isEmpty()) {
                throw new JeecgBootException("异常");
            }
            String param = String.join(",", dateStr);
            param = param.replaceAll("-", "");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String url = "https://api.apihubs.cn/holiday/get";
            JSONObject params = new JSONObject();
            params.put("cn", 1);
            params.put("workday", 2);
            params.put("size", 366);
            params.put("date", param);
            JSONObject result = RestUtil.get(url, params, null);
            JSONObject data = result.getJSONObject("data");
            JSONArray list = data.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                HashMap l = (HashMap) list.get(i);
                String holidayCn = l.get("holiday_cn").toString();
                String holidayRecessCn = l.get("holiday_recess_cn").toString();
                String date = l.get("date").toString();
                Date date1Date = sdf1.parse(date);
                //将类似20230101-》2023-01-01
                String newDate = sdf2.format(date1Date);
                Boolean flag = isExistDate(newDate);
                String msg = "";
                if (!flag) {
                    if (!holidayCn.equals("非节假日")) {
                        if (holidayRecessCn.equals("假期节假日")) {
                            msg = newDate + "是" + holidayCn + "放假";
                        }
                    } else {
                        msg = newDate + "是周末";
                    }
                    OpHolidayConfig opHolidayConfig = new OpHolidayConfig();
                    opHolidayConfig.setHoliday(date1Date);
                    opHolidayConfigMapper.insert(opHolidayConfig);
                } else {
                    msg = newDate + "已存在";
                }
                if (!msg.equals("")) {
                    msgList.add(msg);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return msgList;
    }

    @Override
    //获取某个时间段的所有日期
    public List<String> findDates(String dBegin, String dEnd) throws ParseException {
        //日期工具类准备
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //设置开始时间
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(format.parse(dBegin));

        //设置结束时间
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(format.parse(dEnd));

        //装返回的日期集合容器
        List<String> Datelist = new ArrayList<String>();
        //将第一个月添加里面去
        Datelist.add(format.format(calBegin.getTime()));
        // 每次循环给calBegin日期加一天，直到calBegin.getTime()时间等于dEnd
        while (format.parse(dEnd).after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            Datelist.add(format.format(calBegin.getTime()));
        }

        return Datelist;
    }

    @Override
    public Boolean isExistDate(String date) {
        Map map = new HashMap();
        map.put("holiday", date);
        boolean flag = false;
        List<OpHolidayConfig> list = opHolidayConfigMapper.selectByMap(map);
        if (list != null && !list.isEmpty()) {
            flag = true;
        }
        return flag;
    }
}
