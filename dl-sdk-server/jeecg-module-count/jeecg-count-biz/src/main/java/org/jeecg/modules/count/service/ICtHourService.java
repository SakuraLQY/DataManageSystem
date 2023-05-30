package org.jeecg.modules.count.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Date;
import java.util.List;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.modules.count.bo.GetHourBo;
import org.jeecg.modules.count.bo.HourBo;
import org.jeecg.modules.count.entity.CtDevice;
import java.util.Date;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.modules.count.entity.CtHour;
import org.jeecg.modules.count.entity.CtUser;

/**
 * @Description: ct_hour
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
public interface ICtHourService extends IService<CtHour> {

    /**
     * @param parseStartDto: start解析dto
     * @param ctDevice: 查询出来的CtDevice数据
     * @return void
     * @author Fkh
     * @description start解析对CtHour表进行操作
     * @date 2023/4/23 14:35
     */
    void updateStartHour(ParseStartDto parseStartDto, CtDevice ctDevice);

    /**
     * @param parseLoginDto: login解析dto
     * @param countUser: 新增注册数
     * @param countUserDev: 新增注册数（按设备）
     * @param countDau: 新增活跃数
     * @param countDauDev: 新增活跃数（按设备）
     * @return void
     * @author Fkh
     * @description login解析对CtHour表进行更新字段
     * @date 2023/4/23 14:36
     */
    void updateLoginHourState(ParseLoginDto parseLoginDto,Integer countUser,Integer countUserDev,Integer countDau,Integer countDauDev);

    /**
     * @param parseLoginDto: login解析dto
     * @param ctUser: 查询出来的CtUser数据
     * @return void
     * @author Fkh
     * @description 更新CtHour表留存
     * @date 2023/4/23 14:38
     */
    void updateLogin(ParseLoginDto parseLoginDto, CtUser ctUser);

    /**
     * @param parseAliveDto: alive解析dto
     * @param countValid: 新增有效注册数
     * @param countValidDev:  新增有效注册数（按设备）
     * @param userTime:  用户创建时间
     * @return void
     * @author Fkh
     * @description 根据用户创建时间更新以上字段
     * @date 2023/4/23 14:39
     */
    void updateAliveHourState(ParseAliveDto parseAliveDto,Integer countValid,Integer countValidDev,
        Date userTime);

    /**
     * @param parsePayDto 广告id
     * @param hourDate 统计时间
     * @param logTime 日志时间
     * @return org.jeecg.modules.count.entity.CtHour
     * @author chenyw
     * @description 初始化小时统计表对象
     * @date 16:54 2023/4/20/020
     **/
    CtHour initPayHour(ParsePayDto parsePayDto, Date hourDate, Date logTime);
    
    /**
     * @param getHourBo 
     * @return java.util.List<org.jeecg.modules.count.bo.HourBo>
     * @author chenyw
     * @description 获取小时表数据
     * @date 16:44 2023/5/13/013
     **/
    List<HourBo> getHourBo(GetHourBo getHourBo);

}
