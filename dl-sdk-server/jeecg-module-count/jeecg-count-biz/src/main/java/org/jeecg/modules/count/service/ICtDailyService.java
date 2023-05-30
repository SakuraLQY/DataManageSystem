package org.jeecg.modules.count.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.jeecg.modules.count.bo.DetailDailyBo;
import org.jeecg.modules.count.bo.SummaryAdvertDailyBo;
import org.jeecg.modules.count.dto.DetailDto;
import org.jeecg.modules.count.dto.SummaryAdvertDto;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.modules.count.bo.summary.SummaryDailyBo;
import org.jeecg.modules.count.dto.CtRoleDto;
import org.jeecg.modules.count.dto.RoiDto;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.modal.CtRoleModal;
import org.jeecg.modules.count.modal.ROIListResult;
import org.jeecg.modules.count.modal.RoiModal;
import org.jeecg.modules.count.vo.RoiVo;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: ct_daily
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
public interface ICtDailyService extends IService<CtDaily> {

    List<RoiVo> queryROIList(RoiDto roiDto);

    ModelAndView exportXls(RoiDto object, Class<RoiModal> clazz, String title);

    /**
     * @param parseStartDto: start解析dto
     * @param ctDevice: 查询出来的CtDevice数据
     * @return void
     * @author Fkh
     * @description start解析对CtDaily进行操作
     * @date 2023/4/23 14:28
     */
    void updateStartDaily(ParseStartDto parseStartDto, CtDevice ctDevice);

    /**
     * @param parseLoginDto: login解析dto
     * @param ctDevice: 查询出来的CtDevice数据
     * @param ctUser: 查询出来的CtUser数据
     * @return void
     * @author Fkh
     * @description login解析对CtDaily表进行操作
     * @date 2023/4/23 14:29
     */
    void updateLoginDaily(ParseLoginDto parseLoginDto,CtDevice ctDevice, CtUser ctUser);

    /**
     * @param parseAliveDto: alive解析dto
     * @param ctDevice:  查询出来的CtDevice数据
     * @param ctUser:  查询出来的CtUser数据
     * @return void
     * @author Fkh
     * @description alive解析对CtDaily对进行操作
     * @date 2023/4/23 14:30
     */
    void updateAliveDaily(ParseAliveDto parseAliveDto,CtDevice ctDevice, CtUser ctUser);

    /**
     * @param parsePayDto
     * @param ctDevice
     * @param ctUser
     * @return org.jeecg.modules.count.entity.CtDaily
     * @author chenyw
     * @description 解析支付 数据汇总
     * @date 21:42 2023/4/19/019
     **/
    void parsePayDaily(ParsePayDto parsePayDto, CtDevice ctDevice, CtUser ctUser);

    /**
     * @param summaryDto
     * @return java.util.List<org.jeecg.modules.count.bo.summary.SummaryDailyBo>
     * @author chenyw
     * @description 获取汇总表 每日数据
     * @date 15:26 2023/5/5/005
     **/
    List<SummaryDailyBo> getSummaryDaily(SummaryDto summaryDto);

    /**
     * @param detailDto
     * @return java.util.List<org.jeecg.modules.count.bo.DetailDailyBo>
     * @author chenyw
     * @description 详细分析
     * @date 10:56 2023/5/10/010
     **/
    List<DetailDailyBo> getDetailDaily(DetailDto detailDto);

    /**
     * @param summaryAdvertDto
     * @return java.util.List<org.jeecg.modules.count.bo.SummaryAdvertDailyBo>
     * @author chenyw
     * @description 渠道数据明细
     * @date 13:48 2023/5/12/012
     **/
    List<SummaryAdvertDailyBo> getSummaryAdvertDaily(SummaryAdvertDto summaryAdvertDto);
}
