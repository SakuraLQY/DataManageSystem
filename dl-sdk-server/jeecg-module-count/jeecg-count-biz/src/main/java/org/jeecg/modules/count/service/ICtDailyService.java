package org.jeecg.modules.count.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.modules.count.bo.DetailDailyBo;
import org.jeecg.modules.count.bo.GetWeekReportDailyBo;
import org.jeecg.modules.count.bo.SummaryAdvertDailyBo;
import org.jeecg.modules.count.bo.UserPayRateDailyBo;
import org.jeecg.modules.count.bo.WeekReportDailyBo;
import org.jeecg.modules.count.dto.DetailDto;
import org.jeecg.modules.count.dto.RecoveryDto;
import org.jeecg.modules.count.dto.RetentionDto;
import org.jeecg.modules.count.dto.SummaryAdvertDto;
import org.jeecg.modules.count.dto.OverViewDto;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.modules.count.bo.SummaryDailyBo;
import org.jeecg.modules.count.dto.RoiDto;
import org.jeecg.modules.count.dto.UserPayRateDto;
import org.jeecg.modules.count.dto.XingtuDayReportDto;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.modal.DayReportResultModal;
import org.jeecg.modules.count.modal.RoiModal;
import org.jeecg.modules.count.modal.XingtuDayReportModal;
import org.jeecg.modules.count.vo.DayReportVo;
import org.jeecg.modules.count.vo.FinanceUserVo;
import org.jeecg.modules.count.vo.OverViewVo;
import org.jeecg.modules.count.vo.RecoveryVo;
import org.jeecg.modules.count.vo.RetentionVo;
import org.jeecg.modules.count.vo.RoiVo;
import org.jeecg.modules.count.vo.StatCustomVo;
import org.jeecg.modules.count.vo.XingtuDayReportVo;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: ct_daily
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
public interface ICtDailyService extends IService<CtDaily> {

    /**
     * @param xingtuDayReportDto
     * @return java.util.List<org.jeecg.modules.count.vo.XingtuDayReportVo>
     * @Author lili
     * @Description 星图日报
     * @Date 17:40 2023/5/30
     **/
    List<XingtuDayReportVo> queryXingtuDayReportList(XingtuDayReportDto xingtuDayReportDto);

    /**
     * @param object
     * @param clazz
     * @param title
     * @return org.springframework.web.servlet.ModelAndView
     * @Author lili
     * @Description 星图日报导出
     * @Date 18:08 2023/5/30
     **/
    ModelAndView xingtuDayReportExportXls(XingtuDayReportDto object, Class<XingtuDayReportModal> clazz, String title);

    /**
     * @param retentionDto
     * @return java.util.List<org.jeecg.modules.count.vo.StatCustomVo>
     * @Author lili
     * @Description 合作商数据统计 合作商数据【数据】
     * @Date 17:13 2023/5/26
     **/
    List<StatCustomVo> queryStatCustomList(RetentionDto retentionDto);

    /**
     * @param retentionDto
     * @return java.util.List<org.jeecg.modules.count.vo.RetentionVo>
     * @Author lili
     * @Description 发行合作商数据统计-留存数据统计
     * @Date 13:53 2023/5/25
     **/
    List<RetentionVo> queryRetentionList(RetentionDto retentionDto);

    /**
     * @param overViewDto
     * @return org.jeecg.modules.count.modal.DayReportResultModal
     * @Author lili
     * @Description 数据报表-数据日报
     * @Date 13:39 2023/5/25
     **/
    DayReportResultModal queryDayReportList(OverViewDto overViewDto);

    /**
     * @param overViewDto
     * @return java.util.List<org.jeecg.modules.count.vo.FinanceUserVo>
     * @Author lili
     * @Description 财务数据-用户数据
     * @Date 11:28 2023/5/17
     **/
    List<FinanceUserVo> queryFinanceUserList(OverViewDto overViewDto);

    /**
     * @param overViewDto
     * @return java.util.List<org.jeecg.modules.count.vo.RecoveryVo>
     * @Author lili
     * @Description 财务数据-回收数据
     * @Date 14:49 2023/5/16
     **/
    List<RecoveryVo> queryRecoveryList(OverViewDto overViewDto);

    /**
     * @param overViewDto
     * @return java.util.List<org.jeecg.modules.count.vo.OverViewVo>
     * @Author lili
     * @Description 游戏数据-整体概况
     * @Date 14:48 2023/5/16
     **/
    List<OverViewVo> queryOverViewList(OverViewDto overViewDto);

    /**
     * @param roiDto
     * @return java.util.List<org.jeecg.modules.count.vo.RoiVo>
     * @Author lili
     * @Description ROI数据
     * @Date 11:41 2023/5/15
     **/
    List<RoiVo> queryROIList(RoiDto roiDto);

    /**
     * @param object
     * @param clazz
     * @param title
     * @return org.springframework.web.servlet.ModelAndView
     * @Author lili
     * @Description 导出ROI
     * @Date 11:42 2023/5/15
     **/
    ModelAndView exportXls(RoiDto object, Class<RoiModal> clazz, String title);

    /**
     * @param object
     * @param clazz
     * @param title
     * @return org.springframework.web.servlet.ModelAndView
     * @Author lili
     * @Description 导出财务数据-回收数据
     * @Date 10:16 2023/5/17
     **/
    ModelAndView recoveryExportXls(OverViewDto object, Class<RecoveryVo> clazz, String title);

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
     * @return java.util.List<org.jeecg.modules.count.bo.SummaryDailyBo>
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

    /**
     * @param userPayRateDto
     * @return java.util.List<org.jeecg.modules.count.bo.UserPayRateDailyBo>
     * @author chenyw
     * @description 用户付费率
     * @date 17:09 2023/5/17/017
     **/
    List<UserPayRateDailyBo> getUserPayRateDaily(UserPayRateDto userPayRateDto);

    /**
     * @param getWeekReportDailyBo
     * @return java.util.List<org.jeecg.modules.count.bo.WeekReportDailyBo>
     * @author chenyw
     * @description 获取数据周报
     * @date 20:00 2023/5/22/022
     **/
    List<WeekReportDailyBo> getWeekReportDaily(GetWeekReportDailyBo getWeekReportDailyBo);

}
