package org.jeecg.modules.users.service;

import org.jeecg.modules.users.entity.OpHolidayConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.users.dto.HolidayTimeDTO;

import java.text.ParseException;
import java.util.List;

/**
 * @Description: op_holiday_config
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
public interface IOpHolidayConfigService extends IService<OpHolidayConfig> {

  /**
   * @Author lili
   * @Description 删除过期时间
   * @Date 14:22 2022/12/15
   **/
  void remove();

  /**
   * @param holidayTimeDTO
   * @return java.util.List<java.lang.String>
   * @Author lili
   * @Description 查询哪些是节假日
   * @Date 14:22 2022/12/15
   **/
  List<String> syncList(HolidayTimeDTO holidayTimeDTO);

  /**
   * @param dBegin 开始时间
   * @param dEnd   结束时间
   * @return java.util.List<java.lang.String>
   * @Author lili
   * @Description 获取某个时间段每天的日期
   * @Date 14:22 2022/12/15
   **/
  List<String> findDates(String dBegin, String dEnd) throws ParseException;

  /**
   * @param date 日期
   * @return java.lang.Boolean
   * @Author lili
   * @Description 日期是否存在数据库
   * @Date 14:23 2022/12/15
   **/
  Boolean isExistDate(String date);


}
