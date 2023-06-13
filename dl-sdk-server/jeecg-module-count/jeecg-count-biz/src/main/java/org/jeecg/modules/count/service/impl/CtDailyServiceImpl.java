package org.jeecg.modules.count.service.impl;

import static java.lang.System.getProperty;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.PayTypeConstant;
import org.jeecg.common.constant.RuleTypeConstant;
import org.jeecg.common.function.bo.GetNameByIdDto;
import org.jeecg.modules.count.bo.DayReportBo;
import org.jeecg.modules.count.bo.RetentionBo;
import org.jeecg.modules.count.bo.StatCustomBo;
import org.jeecg.modules.count.bo.GetWeekReportDailyBo;
import org.jeecg.modules.count.bo.SummaryAdvertDailyBo;
import org.jeecg.modules.count.bo.UserPayRateDailyBo;
import org.jeecg.modules.count.bo.WeekReportDailyBo;
import org.jeecg.modules.count.constant.PlatformType;
import org.jeecg.modules.count.constant.SummaryAdvertType;
import org.jeecg.modules.count.constant.enums.SummaryEnum;
import org.jeecg.modules.count.bo.DetailDailyBo;
import org.jeecg.modules.count.bo.OverViewBo;
import org.jeecg.modules.count.dto.DetailDto;
import org.jeecg.modules.count.dto.RecoveryDto;
import org.jeecg.modules.count.dto.RetentionDto;
import org.jeecg.modules.count.dto.SummaryAdvertDto;
import org.jeecg.modules.count.dto.OverViewDto;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.CountUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.bo.SummaryDailyBo;
import org.jeecg.modules.count.dto.RoiDto;
import org.jeecg.modules.count.dto.UserPayRateDto;
import org.jeecg.modules.count.dto.XingtuDayReportDto;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtHour;
import org.jeecg.modules.count.entity.CtReportConfig;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.mapper.CtDailyMapper;
import org.jeecg.modules.count.mapper.CtReportConfigMapper;
import org.jeecg.modules.count.modal.AllRoiDailyModal;
import org.jeecg.modules.count.modal.DayReportResultModal;
import org.jeecg.modules.count.modal.RoiModal;
import org.jeecg.modules.count.modal.XingtuDayReportModal;
import org.jeecg.modules.count.service.ICtDailyPaybackDevService;
import org.jeecg.modules.count.service.ICtDailyPaybackService;
import org.jeecg.modules.count.service.ICtDailyService;
import org.jeecg.modules.count.service.ICtHourService;
import org.jeecg.modules.count.service.ICtUserService;
import org.jeecg.modules.count.vo.CostDayDataVo;
import org.jeecg.modules.count.vo.DailyPaybackVo;
import org.jeecg.modules.count.vo.DauDataVo;
import org.jeecg.modules.count.vo.DayReportVo;
import org.jeecg.modules.count.vo.FinanceUserVo;
import org.jeecg.modules.count.vo.OverViewVo;
import org.jeecg.modules.count.vo.RecoveryVo;
import org.jeecg.modules.count.vo.RetentionVo;
import org.jeecg.modules.count.vo.RoiVo;
import org.jeecg.modules.count.vo.CostDataVo;
import org.jeecg.modules.count.vo.StatCustomVo;
import org.jeecg.modules.count.vo.XingtuDayReportVo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: ct_daily
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_countly")
public class CtDailyServiceImpl extends ServiceImpl<CtDailyMapper, CtDaily> implements
    ICtDailyService {

    @Autowired
    private CtDailyMapper ctDailyMapper;
    @Autowired
    private CtReportConfigMapper ctReportConfigMapper;
    @Autowired
    private ICtHourService ctHourService;
    @Autowired
    private ICtUserService ctUserService;
    @Autowired
    private ICtDailyPaybackDevService ctDailyPaybackDevService;
    @Autowired
    private ICtDailyPaybackService ctDailyPaybackService;
    @Autowired
    private CtDailyLoyalServiceImpl ctDailyLoyalService;
    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Autowired
    private CtDailyLoyalDevServiceImpl ctDailyLoyalDevService;

    @Override
    public List<XingtuDayReportVo> queryXingtuDayReportList(XingtuDayReportDto xingtuDayReportDto) {
        QueryWrapper<XingtuDayReportDto> where = new QueryWrapper<>();
        QueryWrapper<XingtuDayReportDto> costWhere = new QueryWrapper<>();
        if (null != xingtuDayReportDto.getGameId() && !xingtuDayReportDto.getGameId().isEmpty()) {
            where.in("a.game_id", xingtuDayReportDto.getGameId());
            costWhere.in("a.game_id", xingtuDayReportDto.getGameId());
        }
        if (null != xingtuDayReportDto.getSubGameId() && !xingtuDayReportDto.getSubGameId()
            .isEmpty()) {
            where.in("a.sub_game_id", xingtuDayReportDto.getSubGameId());
            costWhere.in("a.sub_game_id", xingtuDayReportDto.getSubGameId());
        }
        if (null != xingtuDayReportDto.getPkgId() && !xingtuDayReportDto.getPkgId().isEmpty()) {
            where.in("a.pkg_id", xingtuDayReportDto.getPkgId());
            costWhere.in("a.pkg_id", xingtuDayReportDto.getPkgId());
        }
        if (null != xingtuDayReportDto.getAnchorPlanId() && !xingtuDayReportDto.getAnchorPlanId()
            .isEmpty()) {
            where.in("b.anchor_plan_id", xingtuDayReportDto.getAnchorPlanId());
            costWhere.in("b.anchor_plan_id", xingtuDayReportDto.getAnchorPlanId());
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != xingtuDayReportDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(xingtuDayReportDto.getStartTime(),
                        "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
                costWhere.ge("a.cost_day", startDate);
            }
            if (null != xingtuDayReportDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(xingtuDayReportDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
                costWhere.le("a.cost_day", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        costWhere.groupBy("costDay, platform");
        //展示计划列表
        List<CostDataVo> costList = ctDailyMapper.getCostV3Data(costWhere);
        Map<String, CostDataVo> costMap = new HashMap<>();
        Map<String, BigDecimal> androidCostMap = new HashMap<>();
        Map<String, BigDecimal> iosCostMap = new HashMap<>();
        List<String> costDayList = new ArrayList<>();
        if (null != costList && !costList.isEmpty()) {
            for (CostDataVo costDataVo : costList) {
                costDayList.add(costDataVo.getCostDay());
                if (costMap.containsKey(costDataVo.getCostDay())) {
                    //成本金额
                    costDataVo.setCostMoney(costMap.get(costDataVo.getCostDay()).getCostMoney()
                        .add(costDataVo.getCostMoney()));
                    //展示
                    costDataVo.setExhibition(costMap.get(costDataVo.getCostDay()).getExhibition()
                        + costDataVo.getExhibition());
                    //下载
                    costDataVo.setDownload(costMap.get(costDataVo.getCostDay()).getDownload()
                        + costDataVo.getDownload());
                    //点击
                    costDataVo.setClick(costMap.get(costDataVo.getCostDay()).getClick()
                        + costDataVo.getClick());
                    //直播间观看人数
                    costDataVo.setLubanLiveEnterCnt(
                        costMap.get(costDataVo.getCostDay()).getLubanLiveEnterCnt()
                            + costDataVo.getLubanLiveEnterCnt());
                    //超过1分钟观看数
                    costDataVo.setLiveWatchOneMinuteCount(
                        costMap.get(costDataVo.getCostDay()).getLiveWatchOneMinuteCount()
                            + costDataVo.getLiveWatchOneMinuteCount());
                    //直播间打赏次数
                    costDataVo.setLubanLiveGiftCnt(
                        costMap.get(costDataVo.getCostDay()).getLubanLiveGiftCnt()
                            + costDataVo.getLubanLiveGiftCnt());
                    //直播间评论数
                    costDataVo.setLubanLiveCommentCnt(
                        costMap.get(costDataVo.getCostDay()).getLubanLiveCommentCnt()
                            + costDataVo.getLubanLiveCommentCnt());
                    //直播间关注数
                    costDataVo.setLubanLiveFollowCnt(
                        costMap.get(costDataVo.getCostDay()).getLubanLiveFollowCnt()
                            + costDataVo.getLubanLiveFollowCnt());
                    //直播间加入粉丝团
                    costDataVo.setLiveFansClubJoinCnt(
                        costMap.get(costDataVo.getCostDay()).getLiveFansClubJoinCnt()
                            + costDataVo.getLiveFansClubJoinCnt());
                    //直播间分享数
                    costDataVo.setLubanLiveShareCnt(
                        costMap.get(costDataVo.getCostDay()).getLubanLiveShareCnt()
                            + costDataVo.getLubanLiveShareCnt());
                }
                costMap.put(costDataVo.getCostDay(), costDataVo);
                if (Objects.equals(costDataVo.getPlatform(), PlatformType.ANDROID)) {
                    androidCostMap.put(costDataVo.getCostDay(), costDataVo.getCostMoney());
                } else if (Objects.equals(costDataVo.getPlatform(), PlatformType.IOS)) {
                    iosCostMap.put(costDataVo.getCostDay(), costDataVo.getCostMoney());
                }
            }
        }
        where.groupBy("timeDaily");
        List<OverViewBo> list = ctDailyMapper.getDailyData(where);
        Map<String, OverViewBo> dailyMap = new HashMap<>();
        if (null != list && !list.isEmpty()) {
            for (OverViewBo overViewBo : list) {
                if (dailyMap.containsKey(overViewBo.getTimeDaily())) {
                    //激活数
                    overViewBo.setCountActive(
                        dailyMap.get(overViewBo.getTimeDaily()).getCountActive()
                            + overViewBo.getCountActive());
                    //注册数
                    overViewBo.setCountUser(
                        dailyMap.get(overViewBo.getTimeDaily()).getCountUser()
                            + overViewBo.getCountUser());
                    //首日-付费金额
                    overViewBo.setFirstMoney(dailyMap.get(overViewBo.getTimeDaily()).getFirstMoney()
                        .add(overViewBo.getFirstMoney()));
                    //首日-付费人数
                    overViewBo.setFirstPayuser(
                        dailyMap.get(overViewBo.getTimeDaily()).getFirstPayuser()
                            + overViewBo.getFirstPayuser());
                    //活跃-付费金额
                    overViewBo.setAliveMoney(dailyMap.get(overViewBo.getTimeDaily()).getAliveMoney()
                        .add(overViewBo.getAliveMoney()));
                }
                dailyMap.put(overViewBo.getTimeDaily(), overViewBo);
            }
        }
        List<XingtuDayReportVo> resList = new ArrayList<>();
        XingtuDayReportVo summaryXingtuDayReport = new XingtuDayReportVo();
        if (!costDayList.isEmpty()) {
            for (String key : costDayList) {
                XingtuDayReportVo xingtuDayReportVo = new XingtuDayReportVo();
                //日期
                xingtuDayReportVo.setRoiDate(key);
                //安卓消耗
                if (androidCostMap.containsKey(key)) {
                    xingtuDayReportVo.setAndroidCostMoney(androidCostMap.get(key));
                }
                //IOS消耗
                if (iosCostMap.containsKey(key)) {
                    xingtuDayReportVo.setIosCostMoney(iosCostMap.get(key));
                }
                //查询总成本数据
                if (costMap.containsKey(key)) {
                    //总消耗
                    xingtuDayReportVo.setCostMoney(costMap.get(key).getCostMoney());
                    //曝光量
                    xingtuDayReportVo.setExhibition(costMap.get(key).getExhibition());
                    //点击量
                    xingtuDayReportVo.setClick(costMap.get(key).getClick());
                    //下载
                    xingtuDayReportVo.setDownload(costMap.get(key).getDownload());
                    //直播间观看人数
                    xingtuDayReportVo.setLubanLiveEnterCnt(costMap.get(key).getLubanLiveEnterCnt());
                    //超过1分钟观看数
                    xingtuDayReportVo.setLiveWatchOneMinuteCount(
                        costMap.get(key).getLiveWatchOneMinuteCount());
                    //直播间打赏次数
                    xingtuDayReportVo.setLubanLiveGiftCnt(costMap.get(key).getLubanLiveGiftCnt());
                    //直播间评论数
                    xingtuDayReportVo.setLubanLiveCommentCnt(
                        costMap.get(key).getLubanLiveCommentCnt());
                    //直播间关注数
                    xingtuDayReportVo.setLubanLiveFollowCnt(
                        costMap.get(key).getLubanLiveFollowCnt());
                    //直播间加入粉丝团
                    xingtuDayReportVo.setLiveFansClubJoinCnt(
                        costMap.get(key).getLiveFansClubJoinCnt());
                    //直播间分享数
                    xingtuDayReportVo.setLubanLiveShareCnt(costMap.get(key).getLubanLiveShareCnt());
                }
                //查询注册支付数据
                if (dailyMap.containsKey(key)) {
                    //注册量
                    xingtuDayReportVo.setCountUser(dailyMap.get(key).getCountUser());
                    //激活量
                    xingtuDayReportVo.setCountActive(dailyMap.get(key).getCountActive());
                    //新增付费
                    xingtuDayReportVo.setAddCostPrice(dailyMap.get(key).getFirstMoney());
                    //总消费
                    xingtuDayReportVo.setAliveMoney(dailyMap.get(key).getAliveMoney());
                    //新增付费数
                    xingtuDayReportVo.setCostCount(dailyMap.get(key).getFirstPayuser());
                }
                //点击率
                xingtuDayReportVo.setClickRate(new BigDecimal(0.00));
                if (xingtuDayReportVo.getExhibition() > 0) {
                    xingtuDayReportVo.setClickRate(new BigDecimal(
                        xingtuDayReportVo.getClick() / xingtuDayReportVo.getExhibition()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //点击均价
                xingtuDayReportVo.setClickPrice(new BigDecimal(0.00));
                if (xingtuDayReportVo.getClick() > 0) {
                    xingtuDayReportVo.setClickPrice(
                        xingtuDayReportVo.getCostMoney().divide(new BigDecimal(
                                xingtuDayReportVo.getClick()), BigDecimal.ROUND_CEILING)
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //点击下载率
                xingtuDayReportVo.setDownloadRate(new BigDecimal(0.00));
                if (xingtuDayReportVo.getClick() > 0) {
                    xingtuDayReportVo.setDownloadRate(new BigDecimal(
                        xingtuDayReportVo.getDownload() / xingtuDayReportVo.getClick()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //下载均价
                xingtuDayReportVo.setDownloadPrice(new BigDecimal(0.00));
                if (xingtuDayReportVo.getDownload() > 0) {
                    xingtuDayReportVo.setDownloadPrice(
                        xingtuDayReportVo.getCostMoney().divide(new BigDecimal(
                                xingtuDayReportVo.getDownload()), BigDecimal.ROUND_CEILING)
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //下载注册率
                xingtuDayReportVo.setDownloadRegRate(new BigDecimal(0.00));
                if (xingtuDayReportVo.getDownload() > 0) {
                    xingtuDayReportVo.setDownloadRegRate(new BigDecimal(
                        xingtuDayReportVo.getCountUser() / xingtuDayReportVo.getDownload()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //激活注册率
                xingtuDayReportVo.setActiveRegRate(new BigDecimal(0.00));
                if (xingtuDayReportVo.getCountActive() > 0) {
                    xingtuDayReportVo.setActiveRegRate(new BigDecimal(
                        xingtuDayReportVo.getCountUser() / xingtuDayReportVo.getCountActive()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //注册单价
                xingtuDayReportVo.setRegUnitPrice(new BigDecimal(0.00));
                if (xingtuDayReportVo.getCountUser() > 0) {
                    xingtuDayReportVo.setRegUnitPrice(
                        xingtuDayReportVo.getCostMoney().divide(new BigDecimal(
                                xingtuDayReportVo.getCountUser()), BigDecimal.ROUND_CEILING)
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //新增付费率
                xingtuDayReportVo.setCostProbability(new BigDecimal(0.00));
                if (xingtuDayReportVo.getCountUser() > 0) {
                    xingtuDayReportVo.setCostProbability(new BigDecimal(
                        xingtuDayReportVo.getCostCount() / xingtuDayReportVo.getCountUser()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //付费单价
                xingtuDayReportVo.setCostUnitPrice(new BigDecimal(0.00));
                if (xingtuDayReportVo.getCostCount() > 0) {
                    xingtuDayReportVo.setCostUnitPrice(
                        xingtuDayReportVo.getCostMoney().divide(new BigDecimal(
                                xingtuDayReportVo.getCostCount()), BigDecimal.ROUND_CEILING)
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //新增ARPU
                xingtuDayReportVo.setFirstArpu(new BigDecimal(0.00));
                if (xingtuDayReportVo.getCountUser() > 0) {
                    xingtuDayReportVo.setFirstArpu(
                        xingtuDayReportVo.getAddCostPrice().divide(new BigDecimal(
                                xingtuDayReportVo.getCountUser()), BigDecimal.ROUND_CEILING)
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //首日ROI
                xingtuDayReportVo.setFirstROI(new BigDecimal(0.00));
                if (xingtuDayReportVo.getCostMoney().compareTo(new BigDecimal(0)) > 0) {
                    xingtuDayReportVo.setFirstROI(
                        xingtuDayReportVo.getAddCostPrice().divide(
                                xingtuDayReportVo.getCostMoney(), BigDecimal.ROUND_CEILING)
                            .multiply(new BigDecimal(100))
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //回收率
                xingtuDayReportVo.setRecoveryRate(new BigDecimal(0.00));
                if (xingtuDayReportVo.getCostMoney().compareTo(new BigDecimal(0)) > 0) {
                    xingtuDayReportVo.setRecoveryRate(
                        xingtuDayReportVo.getAliveMoney().divide(
                                xingtuDayReportVo.getCostMoney(), BigDecimal.ROUND_CEILING)
                            .multiply(new BigDecimal(100))
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                resList.add(xingtuDayReportVo);
                //合计-------------------------------------------------------------
                //总消耗
                summaryXingtuDayReport.setCostMoney(
                    summaryXingtuDayReport.getCostMoney().add(xingtuDayReportVo.getCostMoney()));
                //安卓消耗
                summaryXingtuDayReport.setAndroidCostMoney(
                    summaryXingtuDayReport.getAndroidCostMoney()
                        .add(xingtuDayReportVo.getAndroidCostMoney()));
                //IOS消耗
                summaryXingtuDayReport.setIosCostMoney(
                    summaryXingtuDayReport.getIosCostMoney()
                        .add(xingtuDayReportVo.getIosCostMoney()));
                //曝光量
                summaryXingtuDayReport.setExhibition(
                    summaryXingtuDayReport.getExhibition() + xingtuDayReportVo.getExhibition());
                //点击量
                summaryXingtuDayReport.setClick(
                    summaryXingtuDayReport.getClick() + xingtuDayReportVo.getClick());
                //下载
                summaryXingtuDayReport.setDownload(
                    summaryXingtuDayReport.getDownload() + xingtuDayReportVo.getDownload());
                //激活量
                summaryXingtuDayReport.setCountActive(
                    summaryXingtuDayReport.getCountActive() + xingtuDayReportVo.getCountActive());
                //注册量
                summaryXingtuDayReport.setCountUser(
                    summaryXingtuDayReport.getCountUser() + xingtuDayReportVo.getCountUser());
                //新增付费数
                summaryXingtuDayReport.setCostCount(
                    summaryXingtuDayReport.getCostCount() + xingtuDayReportVo.getCostCount());
                //新增付费
                summaryXingtuDayReport.setAddCostPrice(
                    summaryXingtuDayReport.getAddCostPrice()
                        .add(xingtuDayReportVo.getAddCostPrice()));
                //总消费
                summaryXingtuDayReport.setAliveMoney(
                    summaryXingtuDayReport.getAliveMoney().add(xingtuDayReportVo.getAliveMoney()));
                //直播间观看人数
                summaryXingtuDayReport.setLubanLiveEnterCnt(
                    summaryXingtuDayReport.getLubanLiveEnterCnt()
                        + xingtuDayReportVo.getLubanLiveEnterCnt());
                //超过1分钟观看数
                summaryXingtuDayReport.setLiveWatchOneMinuteCount(
                    summaryXingtuDayReport.getLiveWatchOneMinuteCount()
                        + xingtuDayReportVo.getLiveWatchOneMinuteCount());
                //直播间打赏次数
                summaryXingtuDayReport.setLubanLiveGiftCnt(
                    summaryXingtuDayReport.getLubanLiveGiftCnt()
                        + xingtuDayReportVo.getLubanLiveGiftCnt());
                //直播间评论数
                summaryXingtuDayReport.setLubanLiveCommentCnt(
                    summaryXingtuDayReport.getLubanLiveCommentCnt()
                        + xingtuDayReportVo.getLubanLiveCommentCnt());
                //直播间关注数
                summaryXingtuDayReport.setLubanLiveFollowCnt(
                    summaryXingtuDayReport.getLubanLiveFollowCnt()
                        + xingtuDayReportVo.getLubanLiveFollowCnt());
                //直播间加入粉丝团
                summaryXingtuDayReport.setLiveFansClubJoinCnt(
                    summaryXingtuDayReport.getLiveFansClubJoinCnt()
                        + xingtuDayReportVo.getLiveFansClubJoinCnt());
                //直播间分享数
                summaryXingtuDayReport.setLubanLiveShareCnt(
                    summaryXingtuDayReport.getLubanLiveShareCnt()
                        + xingtuDayReportVo.getLubanLiveShareCnt());
            }
        }
        //合计-------------------------------------
        //日期
        summaryXingtuDayReport.setRoiDate("合计");
        //点击率
        summaryXingtuDayReport.setClickRate(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getExhibition() > 0) {
            summaryXingtuDayReport.setClickRate(new BigDecimal(
                summaryXingtuDayReport.getClick() / summaryXingtuDayReport.getExhibition()
                    * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //点击均价
        summaryXingtuDayReport.setClickPrice(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getClick() > 0) {
            summaryXingtuDayReport.setClickPrice(
                summaryXingtuDayReport.getCostMoney().divide(new BigDecimal(
                        summaryXingtuDayReport.getClick()), BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //点击下载率
        summaryXingtuDayReport.setDownloadRate(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getClick() > 0) {
            summaryXingtuDayReport.setDownloadRate(new BigDecimal(
                summaryXingtuDayReport.getDownload() / summaryXingtuDayReport.getClick()
                    * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //下载均价
        summaryXingtuDayReport.setDownloadPrice(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getDownload() > 0) {
            summaryXingtuDayReport.setDownloadPrice(
                summaryXingtuDayReport.getCostMoney().divide(new BigDecimal(
                        summaryXingtuDayReport.getDownload()), BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //下载注册率
        summaryXingtuDayReport.setDownloadRegRate(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getDownload() > 0) {
            summaryXingtuDayReport.setDownloadRegRate(new BigDecimal(
                summaryXingtuDayReport.getCountUser() / summaryXingtuDayReport.getDownload()
                    * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //激活注册率
        summaryXingtuDayReport.setActiveRegRate(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getCountActive() > 0) {
            summaryXingtuDayReport.setActiveRegRate(new BigDecimal(
                summaryXingtuDayReport.getCountUser() / summaryXingtuDayReport.getCountActive()
                    * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //注册单价
        summaryXingtuDayReport.setRegUnitPrice(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getCountUser() > 0) {
            summaryXingtuDayReport.setRegUnitPrice(
                summaryXingtuDayReport.getCostMoney().divide(new BigDecimal(
                        summaryXingtuDayReport.getCountUser()), BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //新增付费率
        summaryXingtuDayReport.setCostProbability(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getCountUser() > 0) {
            summaryXingtuDayReport.setCostProbability(new BigDecimal(
                summaryXingtuDayReport.getCostCount() / summaryXingtuDayReport.getCountUser()
                    * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //付费单价
        summaryXingtuDayReport.setCostUnitPrice(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getCostCount() > 0) {
            summaryXingtuDayReport.setCostUnitPrice(
                summaryXingtuDayReport.getCostMoney().divide(new BigDecimal(
                        summaryXingtuDayReport.getCostCount()), BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //新增ARPU
        summaryXingtuDayReport.setFirstArpu(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getCountUser() > 0) {
            summaryXingtuDayReport.setFirstArpu(
                summaryXingtuDayReport.getAddCostPrice().divide(new BigDecimal(
                        summaryXingtuDayReport.getCountUser()), BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //首日ROI
        summaryXingtuDayReport.setFirstROI(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getCostMoney().compareTo(new BigDecimal(0)) > 0) {
            summaryXingtuDayReport.setFirstROI(
                summaryXingtuDayReport.getAddCostPrice().divide(
                        summaryXingtuDayReport.getCostMoney(), BigDecimal.ROUND_CEILING)
                    .multiply(new BigDecimal(100))
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //回收率
        summaryXingtuDayReport.setRecoveryRate(new BigDecimal(0.00));
        if (summaryXingtuDayReport.getCostMoney().compareTo(new BigDecimal(0)) > 0) {
            summaryXingtuDayReport.setRecoveryRate(
                summaryXingtuDayReport.getAliveMoney().divide(
                        summaryXingtuDayReport.getCostMoney(), BigDecimal.ROUND_CEILING)
                    .multiply(new BigDecimal(100))
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        resList.add(summaryXingtuDayReport);

        return resList;
    }

    @Override
    public ModelAndView xingtuDayReportExportXls(XingtuDayReportDto object,
        Class<XingtuDayReportModal> clazz, String title) {
        List<XingtuDayReportVo> list = queryXingtuDayReportList(object);
        List<XingtuDayReportModal> exportList = new ArrayList<>();
        for (XingtuDayReportVo xingtuDayReportVo : list) {
            XingtuDayReportModal xingtuDayReportModal = new XingtuDayReportModal();
            BeanUtils.copyProperties(xingtuDayReportVo, xingtuDayReportModal);
            xingtuDayReportModal.setClickRate(xingtuDayReportVo.getClickRate() + "%");
            xingtuDayReportModal.setDownloadRate(xingtuDayReportVo.getDownloadRate() + "%");
            xingtuDayReportModal.setDownloadRegRate(xingtuDayReportVo.getDownloadRegRate() + "%");
            xingtuDayReportModal.setActiveRegRate(xingtuDayReportVo.getActiveRegRate() + "%");
            xingtuDayReportModal.setCostProbability(xingtuDayReportVo.getCostProbability() + "%");
            xingtuDayReportModal.setFirstROI(xingtuDayReportVo.getFirstROI() + "%");
            xingtuDayReportModal.setRecoveryRate(xingtuDayReportVo.getRecoveryRate() + "%");
            exportList.add(xingtuDayReportModal);
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams = new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(),
            title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    @Override
    public List<StatCustomVo> queryStatCustomList(RetentionDto retentionDto) {
        QueryWrapper<OverViewDto> where = new QueryWrapper<>();
        if (null != retentionDto.getGameId() && !retentionDto.getGameId().isEmpty()) {
            where.in("a.game_id", retentionDto.getGameId());
        }
        if (null != retentionDto.getSubGameId() && !retentionDto.getSubGameId().isEmpty()) {
            where.in("a.sub_game_id", retentionDto.getSubGameId());
        }
        if (null != retentionDto.getPkgId() && !retentionDto.getPkgId().isEmpty()) {
            where.in("a.pkg_id", retentionDto.getPkgId());
        }
        if (null != retentionDto.getChannelTypeId() && !retentionDto.getChannelTypeId().isEmpty()) {
            where.in("a.channel_type_id", retentionDto.getChannelTypeId());
        }
        if (null != retentionDto.getChannelId() && !retentionDto.getChannelId().isEmpty()) {
            where.in("a.channel_id", retentionDto.getChannelId());
        }
        if (null != retentionDto.getChannelSubAccountId() && !retentionDto.getChannelSubAccountId()
            .isEmpty()) {
            where.in("a.channel_sub_account_id", retentionDto.getChannelSubAccountId());
        }
        if (null != retentionDto.getDealId() && !retentionDto.getDealId().isEmpty()) {
            where.in("a.deal_id", retentionDto.getDealId());
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != retentionDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(retentionDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
            }
            if (null != retentionDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(retentionDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (retentionDto.getType() == 0) {
            where.groupBy("id");
        } else {
            where.groupBy("id, name");
        }
        String groupBy = "";
        String groupName = "";

        switch (retentionDto.getType()) {
            case 0:
                groupBy = "DATE(a.time_daily)";
                break;
            case 1:
                groupBy = "a.game_id";
                groupName = "d.game_name as name,";
                break;
            case 2:
                groupBy = "a.sub_game_id";
                groupName = "e.game_name as name,";
                break;
            case 3:
                groupBy = "a.deal_id";
                groupName = "b.deal_name as name,";
                break;
            case 4:
                groupBy = "a.channel_id";
                groupName = "f.channel_name as name,";
                break;
            case 5:
                groupBy = "a.pkg_id";
                groupName = "c.pkg_name as name,";
                break;
            case 7:
                groupBy = "a.channel_sub_account_id";
                groupName = "g.sub_account_name as name,";
                break;
        }
        String paybackSql = "";
        for (int i = 1; i <= 90; i++) {
            paybackSql += "IFNULL(sum(p.day" + i + "), 0) as payback" + i + ",";
        }
        paybackSql = paybackSql.substring(0, paybackSql.length() - 1);

        List<StatCustomBo> list = ctDailyMapper.getStatCustom(groupBy, groupName, paybackSql,
            where);
        List<StatCustomVo> resList = new ArrayList<>();
        StatCustomVo summaryStatCustom = new StatCustomVo();
        StatCustomBo summaryStatCustomBo = new StatCustomBo();
        if (null != list && !list.isEmpty()) {
            for (StatCustomBo statCustomBo : list) {
                StatCustomVo statCustomVo = new StatCustomVo();
                BeanUtils.copyProperties(statCustomBo, statCustomVo);
                if (retentionDto.getType() == 0) {
                    statCustomVo.setName("全部游戏");
                }
                statCustomVo.setLtv1(statCustomBo.getPayback1());
                Map<String, BigDecimal> ltvMap = new HashMap<>();
                ltvMap.put("ltv1", statCustomVo.getLtv1());
                Class<StatCustomBo> statCustomBoClass = StatCustomBo.class;
                for (int i = 2; i <= 90; i++) {
                    Field declaredField = null;
                    try {
                        declaredField = statCustomBoClass.getDeclaredField("payback" + i);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    declaredField.setAccessible(true);
                    BigDecimal value = null;
                    try {
                        value = (BigDecimal) declaredField.get(statCustomBo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (Objects.equals(value, BigDecimal.ZERO)) {
                        ltvMap.put("ltv" + i, ltvMap.get("ltv" + (i - 1)));
                    } else {
                        ltvMap.put("ltv" + i, ltvMap.get("ltv" + (i - 1)).add(value));
                    }

                }
                //新增付费率
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setAddCostRate(new BigDecimal(
                        statCustomVo.getFirstPayuser() / statCustomVo.getCountUserDev()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //活跃付费率
                if (statCustomVo.getCountDau() > 0) {
                    statCustomVo.setAliveMoneyRate(new BigDecimal(
                        statCustomVo.getAlivePayuser() / statCustomVo.getCountDau()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //首日ARPU
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setFirstArpu(statCustomVo.getFirstMoney()
                        .divide(new BigDecimal(statCustomVo.getCountUserDev()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //总ARPU
                if (statCustomVo.getCountDau() > 0) {
                    statCustomVo.setTotalArpu(statCustomVo.getAliveMoney()
                        .divide(new BigDecimal(statCustomVo.getCountDau()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //次留
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setDay2(new BigDecimal(
                        statCustomBo.getLoyal2() / statCustomVo.getCountUserDev()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //3日留存
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setDay3(new BigDecimal(
                        statCustomBo.getLoyal3() / statCustomVo.getCountUserDev()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //7日留存
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setDay7(new BigDecimal(
                        statCustomBo.getLoyal7() / statCustomVo.getCountUserDev()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //15日留存
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setDay15(new BigDecimal(
                        statCustomBo.getLoyal15() / statCustomVo.getCountUserDev()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //30日留存
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setDay30(new BigDecimal(
                        statCustomBo.getLoyal30() / statCustomVo.getCountUserDev()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //LTV1
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setLtv1(ltvMap.get("ltv1")
                        .divide(new BigDecimal(statCustomVo.getCountUserDev()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //LTV3
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setLtv3(ltvMap.get("ltv3")
                        .divide(new BigDecimal(statCustomVo.getCountUserDev()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //LTV7
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setLtv7(ltvMap.get("ltv7")
                        .divide(new BigDecimal(statCustomVo.getCountUserDev()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //LTV30
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setLtv30(ltvMap.get("ltv30")
                        .divide(new BigDecimal(statCustomVo.getCountUserDev()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //LTV60
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setLtv60(ltvMap.get("ltv60")
                        .divide(new BigDecimal(statCustomVo.getCountUserDev()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //LTV90
                if (statCustomVo.getCountUserDev() > 0) {
                    statCustomVo.setLtv90(ltvMap.get("ltv90")
                        .divide(new BigDecimal(statCustomVo.getCountUserDev()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                resList.add(statCustomVo);
                log.info("合计-----------------------------------------------");
                summaryStatCustomBo.setId("合计");
                summaryStatCustomBo.setName("合计");
                //激活数
                summaryStatCustomBo.setCountActive(
                    summaryStatCustomBo.getCountActive() + statCustomVo.getCountActive());
                //注册数
                summaryStatCustomBo.setCountUser(
                    summaryStatCustomBo.getCountUser() + statCustomVo.getCountUser());
                //有效注册数
                summaryStatCustomBo.setCountValidUser(
                    summaryStatCustomBo.getCountValidUser() + statCustomVo.getCountValidUser());
                //新增付费金额
                summaryStatCustomBo.setFirstMoney(
                    summaryStatCustomBo.getFirstMoney().add(statCustomVo.getFirstMoney()));
                //活跃人数
                summaryStatCustomBo.setCountDau(
                    summaryStatCustomBo.getCountDau() + statCustomVo.getCountDau());
                //活跃付费人数
                summaryStatCustomBo.setAlivePayuser(
                    summaryStatCustomBo.getAlivePayuser() + statCustomVo.getAlivePayuser());
                //付费总额
                summaryStatCustomBo.setAliveMoney(
                    summaryStatCustomBo.getAliveMoney().add(statCustomVo.getAliveMoney()));
                //注册设备数
                summaryStatCustomBo.setCountUserDev(
                    summaryStatCustomBo.getCountUserDev() + statCustomVo.getCountUserDev());
                //次留
                summaryStatCustomBo.setLoyal2(
                    summaryStatCustomBo.getLoyal2() + statCustomBo.getLoyal2());
                //3日留存
                summaryStatCustomBo.setLoyal3(
                    summaryStatCustomBo.getLoyal3() + statCustomBo.getLoyal3());
                //7日留存
                summaryStatCustomBo.setLoyal7(
                    summaryStatCustomBo.getLoyal7() + statCustomBo.getLoyal7());
                //15日留存
                summaryStatCustomBo.setLoyal15(
                    summaryStatCustomBo.getLoyal15() + statCustomBo.getLoyal15());
                //30日留存
                summaryStatCustomBo.setLoyal30(
                    summaryStatCustomBo.getLoyal30() + statCustomBo.getLoyal30());
                BeanUtils.copyProperties(summaryStatCustomBo, summaryStatCustom);
                //ltv1
                summaryStatCustom.setLtv1(
                    summaryStatCustom.getLtv1().add(statCustomVo.getLtv1()));
                //ltv3
                summaryStatCustom.setLtv3(
                    summaryStatCustom.getLtv3().add(statCustomVo.getLtv3()));
                //ltv7
                summaryStatCustom.setLtv7(
                    summaryStatCustom.getLtv7().add(statCustomVo.getLtv7()));
                //ltv30
                summaryStatCustom.setLtv30(
                    summaryStatCustom.getLtv30().add(statCustomVo.getLtv30()));
                //ltv60
                summaryStatCustom.setLtv60(
                    summaryStatCustom.getLtv60().add(statCustomVo.getLtv60()));
                //ltv90
                summaryStatCustom.setLtv90(
                    summaryStatCustom.getLtv90().add(statCustomVo.getLtv90()));

            }
            log.info("合计*************************************************************");
            //新增付费率
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setAddCostRate(new BigDecimal(
                    summaryStatCustom.getFirstPayuser() / summaryStatCustom.getCountUserDev()
                        * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //活跃付费率
            if (summaryStatCustom.getCountDau() > 0) {
                summaryStatCustom.setAliveMoneyRate(new BigDecimal(
                    summaryStatCustom.getAlivePayuser() / summaryStatCustom.getCountDau()
                        * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //首日ARPU
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setFirstArpu(summaryStatCustom.getFirstMoney()
                    .divide(new BigDecimal(summaryStatCustom.getCountUserDev()),
                        BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //总ARPU
            if (summaryStatCustom.getCountDau() > 0) {
                summaryStatCustom.setTotalArpu(summaryStatCustom.getAliveMoney()
                    .divide(new BigDecimal(summaryStatCustom.getCountDau()),
                        BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //次留
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setDay2(new BigDecimal(
                    summaryStatCustomBo.getLoyal2() / summaryStatCustom.getCountUserDev()
                        * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //3日留存
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setDay3(new BigDecimal(
                    summaryStatCustomBo.getLoyal3() / summaryStatCustom.getCountUserDev()
                        * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //7日留存
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setDay7(new BigDecimal(
                    summaryStatCustomBo.getLoyal7() / summaryStatCustom.getCountUserDev()
                        * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //15日留存
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setDay15(new BigDecimal(
                    summaryStatCustomBo.getLoyal15() / summaryStatCustom.getCountUserDev()
                        * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //30日留存
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setDay30(new BigDecimal(
                    summaryStatCustomBo.getLoyal30() / summaryStatCustom.getCountUserDev()
                        * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //LTV1
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setLtv1(summaryStatCustom.getLtv1()
                    .divide(new BigDecimal(summaryStatCustom.getCountUserDev()),
                        BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //LTV3
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setLtv3(summaryStatCustom.getLtv3()
                    .divide(new BigDecimal(summaryStatCustom.getCountUserDev()),
                        BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //LTV7
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setLtv7(summaryStatCustom.getLtv7()
                    .divide(new BigDecimal(summaryStatCustom.getCountUserDev()),
                        BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //LTV30
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setLtv30(summaryStatCustom.getLtv30()
                    .divide(new BigDecimal(summaryStatCustom.getCountUserDev()),
                        BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //LTV60
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setLtv60(summaryStatCustom.getLtv60()
                    .divide(new BigDecimal(summaryStatCustom.getCountUserDev()),
                        BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //LTV90
            if (summaryStatCustom.getCountUserDev() > 0) {
                summaryStatCustom.setLtv90(summaryStatCustom.getLtv90()
                    .divide(new BigDecimal(summaryStatCustom.getCountUserDev()),
                        BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            resList.add(summaryStatCustom);
        }
        return resList;
    }

    @Override
    public List<RetentionVo> queryRetentionList(RetentionDto retentionDto) {
        QueryWrapper<RetentionDto> where = new QueryWrapper<>();
        if (null != retentionDto.getGameId() && !retentionDto.getGameId().isEmpty()) {
            where.in("a.game_id", retentionDto.getGameId());
        }
        if (null != retentionDto.getSubGameId() && !retentionDto.getSubGameId().isEmpty()) {
            where.in("a.sub_game_id", retentionDto.getSubGameId());
        }
        if (null != retentionDto.getPkgId() && !retentionDto.getPkgId().isEmpty()) {
            where.in("a.pkg_id", retentionDto.getPkgId());
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != retentionDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(retentionDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
            }
            if (null != retentionDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(retentionDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sql = "";
        switch (retentionDto.getType()) {
            case 0:
                sql = "DATE(a.time_daily)";
                break;
            case 1:
                sql = "a.game_id";
                break;
            case 2:
                sql = "a.sub_game_id";
                break;
            case 3:
                sql = "a.deal_id";
                break;
            case 4:
                sql = "a.channel_id";
                break;
            case 5:
                sql = "a.pkg_id";
                break;
        }
        List<RetentionBo> list = ctDailyMapper.getRetentionData(where, sql);
        List<RetentionVo> resList = new ArrayList<>();
        for (RetentionBo retentionBo : list) {
            RetentionVo retentionVo = new RetentionVo();
            retentionBo.setName(retentionBo.getId());
            if (0 != retentionDto.getType()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setId(Integer.valueOf(retentionBo.getId()));
                getNameByIdDto.setType(retentionDto.getType());
                if (oConvertUtils.isNotEmpty(ctUserService.getNameById(getNameByIdDto))) {
                    retentionBo.setName(ctUserService.getNameById(getNameByIdDto).getName());
                }
            }
            Integer countUser = 1;
            if (retentionBo.getCountUser() > 0) {
                countUser = retentionBo.getCountUser();
            }
            BeanUtils.copyProperties(retentionBo, retentionVo);
            if (null == retentionBo.getDay2()) {
                retentionBo.setDay2(0);
            }
            retentionVo.setDay2(
                new BigDecimal(retentionBo.getDay2() * 100 / countUser).setScale(2,
                    BigDecimal.ROUND_HALF_EVEN));
            if (null == retentionBo.getDay3()) {
                retentionBo.setDay3(0);
            }
            retentionVo.setDay3(
                new BigDecimal(retentionBo.getDay3() * 100 / countUser).setScale(2,
                    BigDecimal.ROUND_HALF_EVEN));
            if (null == retentionBo.getDay7()) {
                retentionBo.setDay7(0);
            }
            retentionVo.setDay7(
                new BigDecimal(retentionBo.getDay7() * 100 / countUser).setScale(2,
                    BigDecimal.ROUND_HALF_EVEN));
            if (null == retentionBo.getDay15()) {
                retentionBo.setDay15(0);
            }
            retentionVo.setDay15(
                new BigDecimal(retentionBo.getDay15() * 100 / countUser).setScale(2,
                    BigDecimal.ROUND_HALF_EVEN));
            if (null == retentionBo.getDay30()) {
                retentionBo.setDay30(0);
            }
            retentionVo.setDay30(
                new BigDecimal(retentionBo.getDay30() * 100 / countUser).setScale(2,
                    BigDecimal.ROUND_HALF_EVEN));
            if (null == retentionBo.getDay45()) {
                retentionBo.setDay45(0);
            }
            retentionVo.setDay45(
                new BigDecimal(retentionBo.getDay45() * 100 / countUser).setScale(2,
                    BigDecimal.ROUND_HALF_EVEN));
            if (null == retentionBo.getDay60()) {
                retentionBo.setDay60(0);
            }
            retentionVo.setDay60(
                new BigDecimal(retentionBo.getDay60() * 100 / countUser).setScale(2,
                    BigDecimal.ROUND_HALF_EVEN));
            resList.add(retentionVo);
        }
        return resList;
    }

    @Override
    public DayReportResultModal queryDayReportList(OverViewDto overViewDto) {
        QueryWrapper<OverViewDto> where = new QueryWrapper<>();
        QueryWrapper<RoiDto> costWhere = new QueryWrapper<>();
        if (null != overViewDto.getGameId() && !overViewDto.getGameId().isEmpty()) {
            where.in("game_id", overViewDto.getGameId());
            costWhere.in("game_id", overViewDto.getGameId());
        }
        if (null != overViewDto.getSubGameId() && !overViewDto.getSubGameId().isEmpty()) {
            where.in("sub_game_id", overViewDto.getSubGameId());
            costWhere.in("sub_game_id", overViewDto.getSubGameId());
        }
        if (null != overViewDto.getPkgId() && !overViewDto.getPkgId().isEmpty()) {
            where.in("pkg_id", overViewDto.getPkgId());
            costWhere.in("pkg_id", overViewDto.getPkgId());
        }
        if (null != overViewDto.getChannelTypeId() && !overViewDto.getChannelTypeId().isEmpty()) {
            where.in("channel_type_id", overViewDto.getChannelTypeId());
            costWhere.in("channel_type_id", overViewDto.getChannelTypeId());
        }
        if (null != overViewDto.getChannelId() && !overViewDto.getChannelId().isEmpty()) {
            where.in("channel_id", overViewDto.getChannelId());
            costWhere.in("channel_id", overViewDto.getChannelId());
        }
        if (null != overViewDto.getChannelSubAccountId() && !overViewDto.getChannelSubAccountId()
            .isEmpty()) {
            where.in("channel_sub_account_id", overViewDto.getChannelSubAccountId());
            costWhere.in("channel_sub_account_id", overViewDto.getChannelSubAccountId());
        }
        Date date = null;
        try {
            if (null != overViewDto.getStartTime()) {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(overViewDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.eq("time_daily", date);
                costWhere.eq("cost_day", date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sql = " GROUP BY pkgId,channelId,channelSubAccountId,costDay ORDER BY pkgId ASC,channelId ASC,channelSubAccountId ASC";
        List<CostDataVo> costList = ctDailyMapper.getCostV2Data(costWhere, sql);
        Map<String, CostDataVo> costMap = new HashMap<>();
        List<String> ratioData1 = new ArrayList<>();
        if (null != costList && !costList.isEmpty()) {
            for (CostDataVo costDataVo : costList) {
                CostDataVo obj = new CostDataVo();
                obj.setCostMoney(costDataVo.getCostMoney());
                obj.setExhibition(costDataVo.getExhibition());
                obj.setDownload(costDataVo.getDownload());
                costMap.put(costDataVo.getPkgId() + "" + costDataVo.getChannelId() + ""
                    + costDataVo.getChannelSubAccountId() + costDataVo.getCostDay(), obj);
                ratioData1.add(costDataVo.getPkgId() + "_" + costDataVo.getChannelId() + "_"
                    + costDataVo.getChannelSubAccountId() + "_" + costDataVo.getCostDay());
            }
        }
        where.groupBy("pkgId,channelId,channelSubAccountId,timeDaily");
        where.orderByAsc("pkgId asc,channelId asc,channelSubAccountId");
        List<DayReportBo> list = ctDailyMapper.getDayReportData(where);
        //处理有成本没数据的记录
        List<String> ratioData2 = new ArrayList<>();
        Map<String, DayReportBo> newMap = new HashMap<>();
        if (null != list && !list.isEmpty()) {
            for (DayReportBo dayReportBo : list) {
                ratioData2.add(dayReportBo.getPkgId() + "_" + dayReportBo.getChannelId() + "_"
                    + dayReportBo.getChannelSubAccountId() + "_" + dayReportBo.getTimeDaily());
                newMap.put(dayReportBo.getPkgId() + "" + dayReportBo.getChannelId() + ""
                        + dayReportBo.getChannelSubAccountId() + dayReportBo.getTimeDaily(),
                    dayReportBo);
            }
        }
        ratioData1.removeAll(ratioData2);
        if (!ratioData1.isEmpty()) {
            for (String s : ratioData1) {
                List<String> data = Arrays.asList(s.split("_"));
                DayReportBo bo = new DayReportBo();
                bo.setTimeDaily(data.get(3));
                bo.setPkgId(Integer.valueOf(data.get(0)));
                bo.setChannelId(Integer.valueOf(data.get(1)));
                bo.setChannelSubAccountId(Integer.valueOf(data.get(2)));
                bo.setCountUser(0);
                bo.setFirstPayuser(0);
                bo.setFirstMoney(new BigDecimal(0));
                bo.setAliveMoney(new BigDecimal(0));
                bo.setFirstMoneyIos(new BigDecimal(0));
                bo.setAliveMoneyIos(new BigDecimal(0));
                newMap.put(bo.getPkgId() + "" + bo.getChannelId() + ""
                    + bo.getChannelSubAccountId() + bo.getTimeDaily(), bo);
            }
        }
        Map<String, CostDayDataVo> dataListZj = new HashMap<>();
        if (!newMap.isEmpty()) {
            for (String key : newMap.keySet()) {
                DayReportBo bo = new DayReportBo();
                //日期
                List<String> dateStr = Arrays.asList(newMap.get(key).getTimeDaily().split("-"));
                bo.setTimeDaily(dateStr.get(1) + "月" + dateStr.get(2) + "日");
                //现金消耗
                String costKey =
                    newMap.get(key).getPkgId() + "" + newMap.get(key).getChannelId() + ""
                        + newMap.get(key)
                        .getChannelSubAccountId() + newMap.get(key).getTimeDaily();
                BigDecimal realCostMoney = new BigDecimal(0);
                if (costMap.containsKey(costKey)) {
                    realCostMoney = costMap.get(costKey).getCostMoney();
                }
                //成本
                BigDecimal costMoney = realCostMoney.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                //曝光
                Integer exhibition = 0;
                if (costMap.containsKey(costKey)) {
                    exhibition = costMap.get(costKey).getExhibition();
                }
                //下载
                Integer download = 0;
                if (costMap.containsKey(costKey)) {
                    download = costMap.get(costKey).getDownload();
                }
                //新增分成所得
                BigDecimal profit = newMap.get(key).getFirstMoney().subtract(
                    newMap.get(key).getFirstMoneyIos()
                        .multiply(new BigDecimal(String.valueOf(0.29))));
                //活跃付费分成所得
                BigDecimal aliveProfit = newMap.get(key).getAliveMoney().subtract(
                    newMap.get(key).getAliveMoneyIos()
                        .multiply(new BigDecimal(String.valueOf(0.29))));
                //付费求整
                Integer firstMoney = newMap.get(key).getFirstMoney().intValue();
                Integer aliveMoney = newMap.get(key).getAliveMoney().intValue();
                //注册数
                Integer countUser = newMap.get(key).getCountUser();
                //新增付费人数
                Integer firstUser = newMap.get(key).getFirstPayuser();
                String adComposeKey = "" +
                    newMap.get(key).getPkgId() + newMap.get(key).getChannelId() + newMap.get(key)
                    .getChannelSubAccountId();

                if (null != dataListZj.get(adComposeKey)) {
                    //存在追加数据
                    //现金消耗
                    realCostMoney = dataListZj.get(adComposeKey).getRealCostMoney()
                        .add(realCostMoney);
                    //成本
                    costMoney = dataListZj.get(adComposeKey).getCostMoney()
                        .add(costMoney);
                    //曝光
                    exhibition += dataListZj.get(adComposeKey).getExhibition();
                    //下载
                    download += dataListZj.get(adComposeKey).getDownload();
                    //新增分成所得
                    profit = dataListZj.get(adComposeKey).getProfit()
                        .add(profit);
                    //活跃付费分成所得
                    aliveProfit = dataListZj.get(adComposeKey).getAliveProfit()
                        .add(aliveProfit);
                    //新增付费
                    firstMoney += dataListZj.get(adComposeKey).getFirstMoney();
                    //活跃付费
                    aliveMoney += dataListZj.get(adComposeKey).getAliveMoney();
                    //注册数
                    countUser += dataListZj.get(adComposeKey).getCountUser();
                    //新增付费人数
                    firstUser += dataListZj.get(adComposeKey).getFirstUser();
                }
                CostDayDataVo costDayDataVo = new CostDayDataVo();
                costDayDataVo.setRoiDate(bo.getTimeDaily());
//                costDayDataVo.setGameId(newMap.get(key).getGameId());
//                costDayDataVo.setSubGameId(newMap.get(key).getSubGameId());
                costDayDataVo.setPkgId(newMap.get(key).getPkgId());
                costDayDataVo.setChannelId(newMap.get(key).getChannelId());
//                costDayDataVo.setChannelTypeId(newMap.get(key).getChannelTypeId());
                costDayDataVo.setChannelSubAccountId(newMap.get(key).getChannelSubAccountId());
                costDayDataVo.setRealCostMoney(realCostMoney);
                costDayDataVo.setCountUser(countUser);
                costDayDataVo.setCostMoney(costMoney);
                costDayDataVo.setExhibition(exhibition);
                costDayDataVo.setDownload(download);
                costDayDataVo.setProfit(profit);
                costDayDataVo.setAliveProfit(aliveProfit);
                costDayDataVo.setFirstUser(firstUser);
                costDayDataVo.setFirstMoney(firstMoney);
                costDayDataVo.setAliveMoney(aliveMoney);
                dataListZj.put(adComposeKey, costDayDataVo);
            }
        }
        List<DayReportVo> dataList = new ArrayList<>();
        if (!dataListZj.isEmpty()) {
            for (String key : dataListZj.keySet()) {
                DayReportVo dayReportVo = new DayReportVo();
                dayReportVo.setRoiDate(dataListZj.get(key).getRoiDate());
                dayReportVo.setPkgId(dataListZj.get(key).getPkgId());
                dayReportVo.setChannelId(dataListZj.get(key).getChannelId());
                dayReportVo.setChannelSubAccountId(dataListZj.get(key).getChannelSubAccountId());
                dayReportVo.setRealCostMoney(dataListZj.get(key).getRealCostMoney());
                dayReportVo.setCostMoney(dataListZj.get(key).getCostMoney());
                dayReportVo.setProfit(dataListZj.get(key).getProfit());
                dayReportVo.setAliveProfit(dataListZj.get(key).getAliveProfit());
                dayReportVo.setFirstMoney(dataListZj.get(key).getFirstMoney());
                dayReportVo.setAliveMoney(dataListZj.get(key).getAliveMoney());
                dayReportVo.setCountUser(dataListZj.get(key).getCountUser());
                dayReportVo.setFirstUser(dataListZj.get(key).getFirstUser());
                dayReportVo.setDownload(dataListZj.get(key).getDownload());
                dayReportVo.setExhibition(dataListZj.get(key).getExhibition());
                //下载率
                dayReportVo.setDownloadRate(new BigDecimal(0));
                if (dataListZj.get(key).getExhibition() > 0) {
                    dayReportVo.setDownloadRate(new BigDecimal(
                        dataListZj.get(key).getDownload() / dataListZj.get(key).getExhibition()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //下载单价
                dayReportVo.setDownloadUnitPrice(new BigDecimal(0));
                if (dataListZj.get(key).getDownload() > 0) {
                    dayReportVo.setDownloadUnitPrice(dataListZj.get(key).getRealCostMoney()
                        .divide(new BigDecimal(dataListZj.get(key).getDownload()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //注册率
                dayReportVo.setRegProbability(new BigDecimal(0));
                if (dataListZj.get(key).getDownload() > 0) {
                    dayReportVo.setRegProbability(new BigDecimal(
                        dataListZj.get(key).getCountUser() / dataListZj.get(key).getDownload()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //注册单价
                dayReportVo.setRegUnitPrice(new BigDecimal(0));
                if (dataListZj.get(key).getCountUser() > 0) {
                    dayReportVo.setRegUnitPrice(dataListZj.get(key).getRealCostMoney()
                        .divide(new BigDecimal(dataListZj.get(key).getCountUser()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //新增付费单价
                dayReportVo.setCostUnitPrice(new BigDecimal(0));
                if (dataListZj.get(key).getFirstUser() > 0) {
                    dayReportVo.setCostUnitPrice(dataListZj.get(key).getRealCostMoney()
                        .divide(new BigDecimal(dataListZj.get(key).getFirstUser()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //新增付费率
                dayReportVo.setAddCostProbability(new BigDecimal(0));
                if (dataListZj.get(key).getCountUser() > 0) {
                    dayReportVo.setAddCostProbability(new BigDecimal(
                        dataListZj.get(key).getFirstUser() / dataListZj.get(key).getCountUser()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //新增ARPU
                dayReportVo.setFirstArpu(new BigDecimal(0));
                if (dataListZj.get(key).getCountUser() > 0) {
                    dayReportVo.setFirstArpu(new BigDecimal(
                        dataListZj.get(key).getFirstUser() / dataListZj.get(key).getCountUser()
                    ).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //首日ROI
                dayReportVo.setFirstROI(new BigDecimal(0));
                if (dataListZj.get(key).getRealCostMoney().compareTo(new BigDecimal(0)) > 0) {
                    dayReportVo.setFirstROI(dataListZj.get(key).getProfit()
                        .divide(dataListZj.get(key).getRealCostMoney(),
                            BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100))
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //投产比
                dayReportVo.setProductionRatio(new BigDecimal(0));
                if (dataListZj.get(key).getRealCostMoney().compareTo(new BigDecimal(0)) > 0) {
                    dayReportVo.setProductionRatio(dataListZj.get(key).getAliveProfit()
                        .divide(dataListZj.get(key).getRealCostMoney(),
                            BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100))
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                dataList.add(dayReportVo);
            }
        }
        //合计汇总
        Map<Integer, DayReportVo> countDataList = new HashMap<>();
        //大盘汇总
        DayReportVo countAllVo = new DayReportVo();
        //成本
        countAllVo.setCostMoney(new BigDecimal(0));
        //现金消耗
        countAllVo.setRealCostMoney(new BigDecimal(0));
        //曝光
        countAllVo.setExhibition(0);
        //下载
        countAllVo.setDownload(0);
        //注册数
        countAllVo.setCountUser(0);
        //新增付费人数
        countAllVo.setFirstUser(0);
        //新增付费金额
        countAllVo.setFirstMoney(0);
        //新增分成所得
        countAllVo.setProfit(new BigDecimal(0));
        //活跃付费金额
        countAllVo.setAliveMoney(0);
        //活跃付费分成所得
        countAllVo.setAliveProfit(new BigDecimal(0));
        if (!dataList.isEmpty()) {
            for (DayReportVo dayReportVo : dataList) {
                //游戏名
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setId(dayReportVo.getPkgId());
                getNameByIdDto.setType(RuleTypeConstant.PKG);
                if (oConvertUtils.isNotEmpty(ctUserService.getNameById(getNameByIdDto))) {
                    dayReportVo.setGameName(ctUserService.getNameById(getNameByIdDto).getName());
                } else {
                    dayReportVo.setGameName("未知游戏");
                }
                //渠道名
                getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setId(dayReportVo.getChannelId());
                getNameByIdDto.setType(RuleTypeConstant.CHANNEL);
                if (oConvertUtils.isNotEmpty(ctUserService.getNameById(getNameByIdDto))) {
                    dayReportVo.setChannelName(ctUserService.getNameById(getNameByIdDto).getName());
                } else {
                    dayReportVo.setChannelName("母包渠道");
                }
                getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setId(dayReportVo.getChannelSubAccountId());
                getNameByIdDto.setType(RuleTypeConstant.SUB_CHANNEL);
                if (oConvertUtils.isNotEmpty(ctUserService.getNameById(getNameByIdDto))) {
                    dayReportVo.setChannelName(ctUserService.getNameById(getNameByIdDto).getName());
                }
                //合计数据***************************************
                DayReportVo countVo = new DayReportVo();
                //成本
                if (!countDataList.containsKey(dayReportVo.getPkgId())) {
                    //成本
                    countVo.setCostMoney(new BigDecimal(0));
                    //现金消耗
                    countVo.setRealCostMoney(new BigDecimal(0));
                    //曝光
                    countVo.setExhibition(0);
                    //下载
                    countVo.setDownload(0);
                    //注册数
                    countVo.setCountUser(0);
                    //新增付费人数
                    countVo.setFirstUser(0);
                    //新增付费金额
                    countVo.setFirstMoney(0);
                    //新增分成所得
                    countVo.setProfit(new BigDecimal(0));
                    //活跃付费金额
                    countVo.setAliveMoney(0);
                    //活跃付费分成所得
                    countVo.setAliveProfit(new BigDecimal(0));
                    countDataList.put(dayReportVo.getPkgId(), countVo);
                }
                //成本
                countVo.setCostMoney(countDataList.get(dayReportVo.getPkgId()).getCostMoney()
                    .add(dayReportVo.getCostMoney()));
                //现金消耗
                countVo.setRealCostMoney(
                    countDataList.get(dayReportVo.getPkgId()).getRealCostMoney()
                        .add(dayReportVo.getRealCostMoney()));
                //曝光
                countVo.setExhibition(countDataList.get(dayReportVo.getPkgId()).getExhibition()
                    + dayReportVo.getExhibition());
                //下载
                countVo.setDownload(countDataList.get(dayReportVo.getPkgId()).getDownload()
                    + dayReportVo.getDownload());
                //注册数
                countVo.setCountUser(countDataList.get(dayReportVo.getPkgId()).getCountUser()
                    + dayReportVo.getCountUser());
                //新增付费人数
                countVo.setFirstUser(countDataList.get(dayReportVo.getPkgId()).getFirstUser()
                    + dayReportVo.getFirstUser());
                //新增付费金额
                countVo.setFirstMoney(countDataList.get(dayReportVo.getPkgId()).getFirstMoney()
                    + dayReportVo.getFirstMoney());
                //新增分成所得
                countVo.setProfit(countDataList.get(dayReportVo.getPkgId()).getProfit()
                    .add(dayReportVo.getProfit()));
                //活跃付费金额
                countVo.setAliveMoney(countDataList.get(dayReportVo.getPkgId()).getAliveMoney()
                    + dayReportVo.getAliveMoney());
                //活跃付费分成所得
                countVo.setAliveProfit(
                    countDataList.get(dayReportVo.getPkgId()).getAliveProfit()
                        .add(dayReportVo.getAliveProfit()));
                countDataList.put(dayReportVo.getPkgId(), countVo);
                //大盘合计数据***************************************
                //成本
                countAllVo.setCostMoney(countAllVo.getCostMoney().add(dayReportVo.getCostMoney()));
                //现金消耗
                countAllVo.setRealCostMoney(
                    countAllVo.getRealCostMoney().add(dayReportVo.getRealCostMoney()));
                //曝光
                countAllVo.setExhibition(countAllVo.getExhibition() + dayReportVo.getExhibition());
                //下载
                countAllVo.setDownload(countAllVo.getDownload() + dayReportVo.getDownload());
                //注册数
                countAllVo.setCountUser(countAllVo.getCountUser() + dayReportVo.getCountUser());
                //新增付费人数
                countAllVo.setFirstUser(countAllVo.getFirstUser() + dayReportVo.getFirstUser());
                //新增付费金额
                countAllVo.setFirstMoney(countAllVo.getFirstMoney() + dayReportVo.getFirstMoney());
                //新增分成所得
                countAllVo.setProfit(countAllVo.getProfit().add(dayReportVo.getProfit()));
                //活跃付费金额
                countAllVo.setAliveMoney(countAllVo.getAliveMoney() + dayReportVo.getAliveMoney());
                //活跃付费分成所得
                countAllVo.setAliveProfit(
                    countAllVo.getAliveProfit().add(dayReportVo.getAliveProfit()));
            }
        }
        if (!countDataList.isEmpty()) {
            for (Integer key : countDataList.keySet()) {
                //下载率
                countDataList.get(key).setDownloadRate(new BigDecimal(0));
                if (countDataList.get(key).getExhibition() > 0) {
                    countDataList.get(key).setDownloadRate(new BigDecimal(
                        countDataList.get(key).getDownload() / countDataList.get(key)
                            .getExhibition()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //下载单价
                countDataList.get(key).setDownloadUnitPrice(new BigDecimal(0));
                if (countDataList.get(key).getDownload() > 0) {
                    countDataList.get(key)
                        .setDownloadUnitPrice(countDataList.get(key).getRealCostMoney()
                            .divide(new BigDecimal(countDataList.get(key).getDownload()),
                                BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //注册率
                countDataList.get(key).setRegProbability(new BigDecimal(0));
                if (countDataList.get(key).getDownload() > 0) {
                    countDataList.get(key).setRegProbability(new BigDecimal(
                        countDataList.get(key).getCountUser() / countDataList.get(key).getDownload()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //注册单价
                countDataList.get(key).setRegUnitPrice(new BigDecimal(0));
                if (countDataList.get(key).getCountUser() > 0) {
                    countDataList.get(key).setRegUnitPrice(countDataList.get(key).getRealCostMoney()
                        .divide(new BigDecimal(countDataList.get(key).getCountUser()),
                            BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //新增付费单价
                countDataList.get(key).setCostUnitPrice(new BigDecimal(0));
                if (countDataList.get(key).getFirstUser() > 0) {
                    countDataList.get(key)
                        .setCostUnitPrice(countDataList.get(key).getRealCostMoney()
                            .divide(new BigDecimal(countDataList.get(key).getFirstUser()),
                                BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //新增付费率
                countDataList.get(key).setAddCostProbability(new BigDecimal(0));
                if (countDataList.get(key).getCountUser() > 0) {
                    countDataList.get(key).setAddCostProbability(new BigDecimal(
                        countDataList.get(key).getFirstUser() / countDataList.get(key)
                            .getCountUser()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //新增ARPU
                countDataList.get(key).setFirstArpu(new BigDecimal(0));
                if (countDataList.get(key).getCountUser() > 0) {
                    countDataList.get(key).setFirstArpu(new BigDecimal(
                        countDataList.get(key).getFirstMoney() / countDataList.get(key)
                            .getCountUser()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //首日ROI
                countDataList.get(key).setFirstROI(new BigDecimal(0));
                if (countDataList.get(key).getRealCostMoney().compareTo(new BigDecimal(0)) > 0) {
                    countDataList.get(key).setFirstROI(countDataList.get(key).getProfit()
                        .divide(countDataList.get(key).getRealCostMoney(),
                            BigDecimal.ROUND_CEILING)
                        .multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //投产比
                countDataList.get(key).setProductionRatio(new BigDecimal(0));
                if (countDataList.get(key).getRealCostMoney().compareTo(new BigDecimal(0)) > 0) {
                    countDataList.get(key)
                        .setProductionRatio(countDataList.get(key).getAliveProfit()
                            .divide(countDataList.get(key).getRealCostMoney(),
                                BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100))
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
            }
        }
        //大盘汇总
        //下载率
        countAllVo.setDownloadRate(new BigDecimal(0));
        if (countAllVo.getExhibition() > 0) {
            countAllVo.setDownloadRate(new BigDecimal(
                countAllVo.getDownload() / countAllVo
                    .getExhibition()
                    * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //下载单价
        countAllVo.setDownloadUnitPrice(new BigDecimal(0));
        if (countAllVo.getDownload() > 0) {
            countAllVo.setDownloadUnitPrice(
                countAllVo.getRealCostMoney().divide(new BigDecimal(countAllVo.getDownload()),
                    BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //注册率
        countAllVo.setRegProbability(new BigDecimal(0));
        if (countAllVo.getDownload() > 0) {
            countAllVo.setRegProbability(new BigDecimal(
                countAllVo.getCountUser() / countAllVo.getDownload()
                    * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //注册单价
        countAllVo.setRegUnitPrice(new BigDecimal(0));
        if (countAllVo.getCountUser() > 0) {
            countAllVo.setRegUnitPrice(countAllVo.getRealCostMoney()
                .divide(new BigDecimal(countAllVo.getCountUser()),
                    BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //新增付费单价
        countAllVo.setCostUnitPrice(new BigDecimal(0));
        if (countAllVo.getFirstUser() > 0) {
            countAllVo.setCostUnitPrice(
                countAllVo.getRealCostMoney().divide(new BigDecimal(countAllVo.getFirstUser()),
                    BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //新增付费率
        countAllVo.setAddCostProbability(new BigDecimal(0));
        if (countAllVo.getCountUser() > 0) {
            countAllVo.setAddCostProbability(new BigDecimal(
                countAllVo.getFirstUser() / countAllVo
                    .getCountUser()
                    * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //新增ARPU
        countAllVo.setFirstArpu(new BigDecimal(0));
        if (countAllVo.getCountUser() > 0) {
            countAllVo.setFirstArpu(new BigDecimal(
                countAllVo.getFirstMoney() / countAllVo
                    .getCountUser()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //首日ROI
        countAllVo.setFirstROI(new BigDecimal(0));
        if (countAllVo.getRealCostMoney().compareTo(new BigDecimal(0)) > 0) {
            countAllVo.setFirstROI(countAllVo.getProfit()
                .divide(countAllVo.getRealCostMoney(),
                    BigDecimal.ROUND_CEILING)
                .multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
        //投产比
        countAllVo.setProductionRatio(new BigDecimal(0));
        if (countAllVo.getRealCostMoney().compareTo(new BigDecimal(0)) > 0) {
            countAllVo.setProductionRatio(countAllVo.getAliveProfit()
                .divide(countAllVo.getRealCostMoney(),
                    BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100))
                .setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }

        //总结--------------------------------------
        DayReportResultModal dayReportResultModal = new DayReportResultModal();
        dayReportResultModal.setDataList(dataList);
        dayReportResultModal.setCountDataList(countDataList);
        List<DayReportVo> countAllVoList = new ArrayList<>();
        countAllVoList.add(countAllVo);
        dayReportResultModal.setCountAllVo(countAllVoList);

        return dayReportResultModal;
    }

    @Override
    public List<FinanceUserVo> queryFinanceUserList(OverViewDto overViewDto) {
        QueryWrapper<OverViewDto> where = new QueryWrapper<>();
        QueryWrapper<RoiDto> costWhere = new QueryWrapper<>();

        String gameName = "全部游戏";
        String channelName = "全部渠道";
        String dealName = "全部广告";
        String name = "";
        if (null != overViewDto.getGameId() && !overViewDto.getGameId().isEmpty()) {
            where.in("a.game_id", overViewDto.getGameId());
            costWhere.in("a.game_id", overViewDto.getGameId());
            gameName = "多款游戏";
            for (Integer id : overViewDto.getGameId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.GAME);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
                if (overViewDto.getGameId().size() == 1) {
                    gameName = name;
                }
            }
        }
        if (null != overViewDto.getSubGameId() && !overViewDto.getSubGameId().isEmpty()) {
            where.in("a.sub_game_id", overViewDto.getSubGameId());
            costWhere.in("a.sub_game_id", overViewDto.getSubGameId());
            for (Integer id : overViewDto.getSubGameId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.SUB_GAME);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
            }
            if (overViewDto.getSubGameId().size() == 1) {
                gameName += "(" + name + ")";
            } else {
                gameName += "(多款子游戏)";
            }
        } else {
            if (null == overViewDto.getSubGameId() || overViewDto.getSubGameId().isEmpty()) {
                gameName += "(全部子游戏)";
            }
        }
        if (null != overViewDto.getPkgId() && !overViewDto.getPkgId().isEmpty()) {
            where.in("a.pkg_id", overViewDto.getPkgId());
            costWhere.in("a.pkg_id", overViewDto.getPkgId());
            for (Integer id : overViewDto.getPkgId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.PKG);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
            }
            if (overViewDto.getPkgId().size() == 1) {
                gameName += "(" + name + ")";
            } else {
                gameName += "(多款游戏包)";
            }
        } else {
            if (null == overViewDto.getPkgId() || overViewDto.getPkgId().isEmpty()) {
                gameName += "(全部游戏包)";
            }
        }
        if (null != overViewDto.getChannelTypeId() && !overViewDto.getChannelTypeId().isEmpty()) {
            where.in("a.channel_type_id", overViewDto.getChannelTypeId());
            costWhere.in("a.channel_type_id", overViewDto.getChannelTypeId());
            for (Integer id : overViewDto.getChannelTypeId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.CHANNEL_TYPE);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
            }
        }
        if (null != overViewDto.getChannelId() && !overViewDto.getChannelId().isEmpty()) {
            where.in("a.channel_id", overViewDto.getChannelId());
            costWhere.in("a.channel_id", overViewDto.getChannelId());
            channelName = "多款渠道";
            for (Integer id : overViewDto.getChannelId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.CHANNEL);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
            }
            if (overViewDto.getChannelId().size() == 1) {
                channelName = name;
            }
        }
        if (null != overViewDto.getChannelSubAccountId() && !overViewDto.getChannelSubAccountId()
            .isEmpty()) {
            where.in("a.channel_sub_account_id", overViewDto.getChannelSubAccountId());
            costWhere.in("a.channel_sub_account_id", overViewDto.getChannelSubAccountId());
            for (Integer id : overViewDto.getChannelSubAccountId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.SUB_CHANNEL);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
            }
            if (overViewDto.getChannelSubAccountId().size() == 1) {
                channelName += "(" + name + ")";
            } else {
                channelName += "(多款子渠道)";
            }
        } else {
            if (null == overViewDto.getChannelSubAccountId() || overViewDto.getChannelSubAccountId()
                .isEmpty()) {
                channelName += "(全部子渠道)";
            }
        }
        if (null != overViewDto.getDealId() && !overViewDto.getDealId().isEmpty()) {
            where.in("a.deal_id", overViewDto.getDealId());
            costWhere.in("a.deal_id", overViewDto.getDealId());
            dealName = "多款广告";
            for (Integer id : overViewDto.getDealId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.DEAL);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
            }
            if (overViewDto.getDealId().size() == 1) {
                dealName = name;
            }
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != overViewDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(overViewDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
                costWhere.ge("a.cost_day", startDate);
            }
            if (null != overViewDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(overViewDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
                costWhere.le("a.cost_day", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, BigDecimal> costMap = new LinkedHashMap<>();
        //查询投入成本
        List<CostDataVo> costData = ctDailyMapper.getCostData(costWhere);
        if (null != costData && !costData.isEmpty()) {
            for (CostDataVo costDataVo : costData) {
                costMap.put(costDataVo.getCostDay(), costDataVo.getCostMoney());
            }
        }
        List<OverViewBo> list = ctDailyMapper.getOverViewData(where);
        //新增用户留存数
        QueryWrapper<RoiDto> userWhere = new QueryWrapper<>();
        QueryWrapper<RoiDto> userWhere2 = new QueryWrapper<>();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                DateFormatUtils.format(calendar, "yyyy-MM-dd 00:00:00"));
            endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
            userWhere.ge("a.update_time", startDate);
            userWhere.le("a.update_time", endDate);
            userWhere2.ge("a.update_time", startDate);
            userWhere2.le("a.update_time", endDate);
            userWhere2.apply("a.pay_time is not null");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userWhere.groupBy("registerTime");
        List<DauDataVo> userData = ctDailyMapper.getUserCostData(userWhere);
        Map<String, Integer> userMap = new HashMap<>();
        if (null != userData && !userData.isEmpty()) {
            for (DauDataVo dauDataVo : userData) {
                userMap.put(dauDataVo.getRegisterTime(), dauDataVo.getCounts());
            }
        }
        userWhere2.groupBy("registerTime");
        List<DauDataVo> userData2 = ctDailyMapper.getUserCostData(userWhere2);
        Map<String, Integer> userMap2 = new HashMap<>();
        if (null != userData2 && !userData2.isEmpty()) {
            for (DauDataVo dauDataVo : userData2) {
                userMap2.put(dauDataVo.getRegisterTime(), dauDataVo.getCounts());
            }
        }
        List<FinanceUserVo> resList = new ArrayList<>();
        FinanceUserVo financeUserSummary = new FinanceUserVo();
        BigDecimal allPromotionCost = new BigDecimal(0);
        Integer allRegCount = 0;
        BigDecimal allRegUnitPrice = new BigDecimal(0);
        Integer allUserPayCount = 0;
        BigDecimal allCostUnitPrice = new BigDecimal(0);
        Integer allTotalRetention = 0;
        Integer allPayRetention = 0;
        BigDecimal allProfit = new BigDecimal(0);
        if (null != list && !list.isEmpty()) {
            for (OverViewBo overViewBo : list) {
                FinanceUserVo financeUserVo = new FinanceUserVo();
                financeUserVo.setRoiDate(overViewBo.getTimeDaily());
                financeUserVo.setGameName(gameName);
                financeUserVo.setChannelName(channelName);
                financeUserVo.setDealName(dealName);
                //推广费用
                financeUserVo.setPromotionCost(new BigDecimal(0));
                if (null != costMap.get(overViewBo.getTimeDaily())
                    && costMap.get(overViewBo.getTimeDaily()).compareTo(new BigDecimal(0)) > 0) {
                    financeUserVo.setPromotionCost(costMap.get(overViewBo.getTimeDaily()));
                }
                //利润
                financeUserVo.setProfit(
                    overViewBo.getTotalMoney().subtract(financeUserVo.getPromotionCost()));
                //总留存
                financeUserVo.setTotalRetention(0);
                if (null != userMap.get(financeUserVo.getRoiDate())
                    && userMap.get(financeUserVo.getRoiDate()) > 0) {
                    financeUserVo.setTotalRetention(userMap.get(financeUserVo.getRoiDate()));
                }
                //付费留存
                financeUserVo.setPayRetention(0);
                if (null != userMap2.get(financeUserVo.getRoiDate())
                    && userMap2.get(financeUserVo.getRoiDate()) > 0) {
                    financeUserVo.setPayRetention(userMap2.get(financeUserVo.getRoiDate()));
                }
                //注册单价
                financeUserVo.setRegUnitPrice(new BigDecimal(0));
                if (null != financeUserVo.getPromotionCost()
                    && financeUserVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                    && null != overViewBo.getCountUser()
                    && overViewBo.getCountUser() > 0) {
                    financeUserVo.setRegUnitPrice(financeUserVo.getPromotionCost()
                        .divide(new BigDecimal(overViewBo.getCountUser()),
                            BigDecimal.ROUND_CEILING)
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //付费单价
                financeUserVo.setCostUnitPrice(new BigDecimal(0));
                if (null != financeUserVo.getPromotionCost()
                    && financeUserVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                    && null != overViewBo.getTotalPayuser()
                    && overViewBo.getTotalPayuser() > 0) {
                    financeUserVo.setCostUnitPrice(financeUserVo.getPromotionCost()
                        .divide(new BigDecimal(overViewBo.getTotalPayuser()),
                            BigDecimal.ROUND_CEILING)
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //注册数
                if (null != overViewBo.getCountUser()) {
                    financeUserVo.setRegCount(overViewBo.getCountUser());
                }
                //付费用户数
                if (null != overViewBo.getTotalPayuser()) {
                    financeUserVo.setUserPayCount(overViewBo.getTotalPayuser());
                }
                resList.add(financeUserVo);
                //合计------------------------------------------------
                //推广费用
                allPromotionCost = allPromotionCost.add(financeUserVo.getPromotionCost());
                //注册数
                allRegCount += financeUserVo.getRegCount();
                //付费用户数
                allUserPayCount += financeUserVo.getUserPayCount();
                //总留存
                allTotalRetention += financeUserVo.getTotalRetention();
                //付费留存
                allPayRetention += financeUserVo.getPayRetention();
                //利润
                allProfit = allProfit.add(financeUserVo.getProfit());
            }
            financeUserSummary.setRoiDate("合计");
            financeUserSummary.setGameName("--");
            financeUserSummary.setChannelName("--");
            financeUserSummary.setDealName("--");
            financeUserSummary.setPromotionCost(allPromotionCost);
            financeUserSummary.setRegCount(allRegCount);
            financeUserSummary.setRegUnitPrice(allRegUnitPrice);
            if (financeUserSummary.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                && financeUserSummary.getRegCount() > 0) {
                financeUserSummary.setRegUnitPrice(financeUserSummary.getPromotionCost()
                    .divide(new BigDecimal(financeUserSummary.getRegCount()),
                        BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            financeUserSummary.setUserPayCount(allUserPayCount);
            financeUserSummary.setCostUnitPrice(allCostUnitPrice);
            if (financeUserSummary.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                && financeUserSummary.getUserPayCount() > 0) {
                financeUserSummary.setCostUnitPrice(financeUserSummary.getPromotionCost()
                    .divide(new BigDecimal(financeUserSummary.getUserPayCount()),
                        BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            financeUserSummary.setTotalRetention(allTotalRetention);
            financeUserSummary.setPayRetention(allPayRetention);
            financeUserSummary.setProfit(allProfit);
            resList.add(financeUserSummary);
        }
        return resList;
    }

    @Override
    public List<RecoveryVo> queryRecoveryList(OverViewDto overViewDto) {
        QueryWrapper<OverViewDto> where = new QueryWrapper<>();
        QueryWrapper<RoiDto> costWhere = new QueryWrapper<>();

        String gameName = "全部游戏";
        String channelName = "全部渠道";
        String gameNameText = "全部游戏";
        String subGameNameText = "全部子游戏";
        String pkgNameText = "全部游戏包";
        String channelTypeNameText = "全部渠道类型";
        String channelNameText = "全部渠道";
        String subChannelNameText = "全部子渠道";
        List<String> nameList = new ArrayList<>();
        String name = "";
        if (null != overViewDto.getGameId() && !overViewDto.getGameId().isEmpty()) {
            where.in("a.game_id", overViewDto.getGameId());
            costWhere.in("a.game_id", overViewDto.getGameId());
            gameName = "多款游戏";
            nameList = new ArrayList<>();
            for (Integer id : overViewDto.getGameId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.GAME);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
                nameList.add(name);
                if (overViewDto.getGameId().size() == 1) {
                    gameName = nameList.get(0);
                }
                gameNameText = String.join(",", nameList);
            }
        }
        if (null != overViewDto.getSubGameId() && !overViewDto.getSubGameId().isEmpty()) {
            where.in("a.sub_game_id", overViewDto.getSubGameId());
            costWhere.in("a.sub_game_id", overViewDto.getSubGameId());
            nameList = new ArrayList<>();
            for (Integer id : overViewDto.getSubGameId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.SUB_GAME);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
                nameList.add(name);
            }
            if (overViewDto.getSubGameId().size() == 1) {
                gameName += "(" + nameList.get(0) + ")";
            } else {
                gameName += "(多款子游戏)";
            }
            subGameNameText = String.join(",", nameList);
        } else {
            if (null == overViewDto.getSubGameId() || overViewDto.getSubGameId().isEmpty()) {
                gameName += "(全部子游戏)";
            }
        }
        if (null != overViewDto.getPkgId() && !overViewDto.getPkgId().isEmpty()) {
            where.in("a.pkg_id", overViewDto.getPkgId());
            costWhere.in("a.pkg_id", overViewDto.getPkgId());
            nameList = new ArrayList<>();
            for (Integer id : overViewDto.getPkgId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.PKG);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
                nameList.add(name);
            }
            if (overViewDto.getPkgId().size() == 1) {
                gameName += "(" + nameList.get(0) + ")";
            } else {
                gameName += "(多款游戏包)";
            }
            pkgNameText = String.join(",", nameList);
        } else {
            if (null == overViewDto.getPkgId() || overViewDto.getPkgId().isEmpty()) {
                gameName += "(全部游戏包)";
            }
        }
        if (null != overViewDto.getChannelTypeId() && !overViewDto.getChannelTypeId().isEmpty()) {
            where.in("a.channel_type_id", overViewDto.getChannelTypeId());
            costWhere.in("a.channel_type_id", overViewDto.getChannelTypeId());
            nameList = new ArrayList<>();
            for (Integer id : overViewDto.getChannelTypeId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.CHANNEL_TYPE);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
                nameList.add(name);
                channelTypeNameText = String.join(",", nameList);
            }
        }
        if (null != overViewDto.getChannelId() && !overViewDto.getChannelId().isEmpty()) {
            where.in("a.channel_id", overViewDto.getChannelId());
            costWhere.in("a.channel_id", overViewDto.getChannelId());
            channelName = "多款渠道";
            nameList = new ArrayList<>();
            for (Integer id : overViewDto.getChannelId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.CHANNEL);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
                nameList.add(name);
            }
            if (overViewDto.getChannelId().size() == 1) {
                channelName = nameList.get(0);
            }
            channelNameText = String.join(",", nameList);
        }
        if (null != overViewDto.getChannelSubAccountId() && !overViewDto.getChannelSubAccountId()
            .isEmpty()) {
            where.in("a.channel_sub_account_id", overViewDto.getChannelSubAccountId());
            costWhere.in("a.channel_sub_account_id", overViewDto.getChannelSubAccountId());
            nameList = new ArrayList<>();
            for (Integer id : overViewDto.getChannelSubAccountId()) {
                GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
                getNameByIdDto.setType(RuleTypeConstant.SUB_CHANNEL);
                getNameByIdDto.setId(id);
                name = ctUserService.getNameById(getNameByIdDto).getName();
                nameList.add(name);
            }
            if (overViewDto.getChannelSubAccountId().size() == 1) {
                channelName += "(" + nameList.get(0) + ")";
            } else {
                channelName += "(多款子渠道)";
            }
            subChannelNameText = String.join(",", nameList);
        } else {
            if (null == overViewDto.getChannelSubAccountId() || overViewDto.getChannelSubAccountId()
                .isEmpty()) {
                channelName += "(全部子渠道)";
            }
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != overViewDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(overViewDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
                costWhere.ge("a.cost_day", startDate);
            }
            if (null != overViewDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(overViewDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
                costWhere.le("a.cost_day", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, BigDecimal> costMap = new LinkedHashMap<>();
        //查询投入成本
        List<CostDataVo> costData = ctDailyMapper.getCostData(costWhere);
        if (null != costData && !costData.isEmpty()) {
            for (CostDataVo costDataVo : costData) {
                costMap.put(costDataVo.getCostDay(), costDataVo.getCostMoney());
            }
        }
        List<OverViewBo> list = ctDailyMapper.getOverViewData(where);
        List<RecoveryVo> resList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            BigDecimal aliveMoney = new BigDecimal(0);
            BigDecimal promotionCost = new BigDecimal(0);
            BigDecimal shareMoney = new BigDecimal(0);
            for (OverViewBo overViewBo : list) {
                //日期
                RecoveryVo recoveryVo = new RecoveryVo();
                recoveryVo.setRoiDate(overViewBo.getTimeDaily());
                recoveryVo.setGameName(gameName);
                recoveryVo.setChannelName(channelName);
                recoveryVo.setGameNameText(gameNameText);
                recoveryVo.setSubGameNameText(subGameNameText);
                recoveryVo.setPkgNameText(pkgNameText);
                recoveryVo.setChannelNameText(channelNameText);
                recoveryVo.setChannelTypeNameText(channelTypeNameText);
                recoveryVo.setSubChannelNameText(subChannelNameText);
                //流水
                aliveMoney = aliveMoney.add(overViewBo.getAliveMoney());
                recoveryVo.setAliveMoney(aliveMoney);
                //推广费用
                if (null != costMap.get(overViewBo.getTimeDaily())
                    && costMap.get(overViewBo.getTimeDaily()).compareTo(new BigDecimal(0)) > 0) {
                    promotionCost = promotionCost.add(costMap.get(overViewBo.getTimeDaily()));
                }
                recoveryVo.setPromotionCost(promotionCost);
                //分成金额
                if (null != overViewBo.getAliveMoney()
                    && overViewBo.getAliveMoney().compareTo(new BigDecimal(0)) > 0
                    && null != overViewBo.getAliveMoneyIos()
                    && overViewBo.getAliveMoneyIos().compareTo(new BigDecimal(0))
                    > 0) {
                    shareMoney = shareMoney.add(overViewBo.getAliveMoney().subtract(
                        overViewBo.getAliveMoneyIos()
                            .multiply(new BigDecimal(String.valueOf(0.29)))));
                }
                recoveryVo.setShareMoney(shareMoney);
                //回收金额
                recoveryVo.setRecoveryMoney(
                    recoveryVo.getShareMoney().subtract(recoveryVo.getPromotionCost()));
                resList.add(recoveryVo);
            }
        }
        return resList;
    }

    @Override
    public List<OverViewVo> queryOverViewList(OverViewDto overViewDto) {
        QueryWrapper<OverViewDto> where = new QueryWrapper<>();
        QueryWrapper<RoiDto> costWhere = new QueryWrapper<>();

        if (null != overViewDto.getGameId() && !overViewDto.getGameId().isEmpty()) {
            where.in("a.game_id", overViewDto.getGameId());
            costWhere.in("a.game_id", overViewDto.getGameId());
        }
        if (null != overViewDto.getSubGameId() && !overViewDto.getSubGameId().isEmpty()) {
            where.in("a.sub_game_id", overViewDto.getSubGameId());
            costWhere.in("a.sub_game_id", overViewDto.getSubGameId());
        }
        if (null != overViewDto.getPkgId() && !overViewDto.getPkgId().isEmpty()) {
            where.in("a.pkg_id", overViewDto.getPkgId());
            costWhere.in("a.pkg_id", overViewDto.getPkgId());
        }
        if (null != overViewDto.getChannelTypeId() && !overViewDto.getChannelTypeId().isEmpty()) {
            where.in("a.channel_type_id", overViewDto.getChannelTypeId());
            costWhere.in("a.channel_type_id", overViewDto.getChannelTypeId());
        }
        if (null != overViewDto.getChannelId() && !overViewDto.getChannelId().isEmpty()) {
            where.in("a.channel_id", overViewDto.getChannelId());
            costWhere.in("a.channel_id", overViewDto.getChannelId());
        }
        if (null != overViewDto.getChannelSubAccountId() && !overViewDto.getChannelSubAccountId()
            .isEmpty()) {
            where.in("a.channel_sub_account_id", overViewDto.getChannelSubAccountId());
            costWhere.in("a.channel_sub_account_id", overViewDto.getChannelSubAccountId());
        }
        if (null != overViewDto.getDealId() && !overViewDto.getDealId().isEmpty()) {
            where.in("a.deal_id", overViewDto.getDealId());
            costWhere.in("a.deal_id", overViewDto.getDealId());
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != overViewDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(overViewDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
                costWhere.ge("a.cost_day", startDate);
            }
            if (null != overViewDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(overViewDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
                costWhere.le("a.cost_day", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String, BigDecimal> costMap = new LinkedHashMap<>();
        //查询投入成本
        List<CostDataVo> costData = ctDailyMapper.getCostData(costWhere);
        if (null != costData && !costData.isEmpty()) {
            for (CostDataVo costDataVo : costData) {
                costMap.put(costDataVo.getCostDay(), costDataVo.getCostMoney());
            }
        }
        List<OverViewBo> list = ctDailyMapper.getOverViewData(where);
        List<OverViewVo> resList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            for (OverViewBo overViewBo : list) {
                OverViewVo financeVo = new OverViewVo();
                //日期
                financeVo.setRoiDate(overViewBo.getTimeDaily());
                //新增付费金额
                financeVo.setAddCostPrice(new BigDecimal(0));
                if (null != overViewBo.getFirstMoney()
                    && overViewBo.getFirstMoney().compareTo(new BigDecimal(0)) > 0) {
                    financeVo.setAddCostPrice(overViewBo.getFirstMoney());
                }
                //活跃付费金额
                financeVo.setActiveCostPrice(new BigDecimal(0));
                if (null != overViewBo.getAliveMoney()
                    && overViewBo.getAliveMoney().compareTo(new BigDecimal(0)) > 0) {
                    financeVo.setActiveCostPrice(overViewBo.getAliveMoney());
                }
                //老用户付费金额
                if (null != overViewBo.getAliveMoney()
                    && overViewBo.getAliveMoney().compareTo(new BigDecimal(0)) > 0 &&
                    overViewBo.getFirstMoney().compareTo(new BigDecimal(0)) > 0) {
                    financeVo.setOldUserPay(
                        overViewBo.getAliveMoney().subtract(overViewBo.getFirstMoney()));
                } else {
                    financeVo.setOldUserPay(new BigDecimal(0));
                }
                //活跃费率
                if (null != overViewBo.getAlivePayuser() && overViewBo.getAlivePayuser() > 0
                    && null != overViewBo.getCountUser()
                    && overViewBo.getCountDau() > 0) {
                    financeVo.setActiveProbability(new BigDecimal(
                        overViewBo.getAlivePayuser() / overViewBo.getCountDau() * 100).setScale(1,
                        BigDecimal.ROUND_HALF_EVEN));
                } else {
                    financeVo.setActiveProbability(new BigDecimal(0));
                }
                //新增付费率
                if (null != overViewBo.getFirstPayuser() && overViewBo.getFirstPayuser() > 0
                    && null != overViewBo.getCountUser()
                    && overViewBo.getCountUser() > 0) {
                    financeVo.setAddCostProbability(new BigDecimal(
                        overViewBo.getFirstPayuser() / overViewBo.getCountUser() * 100).setScale(1,
                        BigDecimal.ROUND_HALF_EVEN));
                } else {
                    financeVo.setAddCostProbability(new BigDecimal(0));
                }
                Integer oldPayUser = 0;
                if (null != overViewBo.getAlivePayuser() && null != overViewBo.getFirstPayuser()) {
                    oldPayUser = overViewBo.getAlivePayuser() - overViewBo.getFirstPayuser();
                }
                Integer oldDau = 0;
                if (null != overViewBo.getCountDau() && null != overViewBo.getCountUser()) {
                    oldDau = overViewBo.getCountDau() - overViewBo.getCountUser();
                }
                BigDecimal oldPay = new BigDecimal(0);
                if (null != overViewBo.getAliveMoney()
                    && overViewBo.getAliveMoney().compareTo(new BigDecimal(0)) > 0
                    && null != overViewBo.getFirstMoney()
                    && overViewBo.getFirstMoney().compareTo(new BigDecimal(0)) > 0) {
                    oldPay = overViewBo.getAliveMoney().subtract(overViewBo.getFirstMoney());
                }
                //老用户付费率
                if (oldPayUser > 0 && oldDau > 0) {
                    financeVo.setOldUserPayProbability(new BigDecimal(
                        oldPayUser / oldDau * 100).setScale(1,
                        BigDecimal.ROUND_HALF_EVEN));
                } else {
                    financeVo.setOldUserPayProbability(new BigDecimal(0));
                }
                //ARPPU
                if (null != overViewBo.getAliveMoney()
                    && overViewBo.getAliveMoney().compareTo(new BigDecimal(0)) > 0
                    && null != overViewBo.getAlivePayuser()
                    && overViewBo.getAlivePayuser() > 0) {
                    financeVo.setArppu(overViewBo.getAliveMoney()
                        .divide(new BigDecimal(overViewBo.getAlivePayuser()),
                            BigDecimal.ROUND_CEILING)
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                } else {
                    financeVo.setArppu(new BigDecimal(0));
                }
                //新增ARPPU
                if (null != overViewBo.getFirstMoney()
                    && overViewBo.getFirstMoney().compareTo(new BigDecimal(0)) > 0
                    && null != overViewBo.getFirstPayuser()
                    && overViewBo.getFirstPayuser() > 0) {
                    financeVo.setAddArppu(overViewBo.getFirstMoney()
                        .divide(new BigDecimal(overViewBo.getFirstPayuser()),
                            BigDecimal.ROUND_CEILING)
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                } else {
                    financeVo.setAddArppu(new BigDecimal(0));
                }
                //老用户ARPPU
                if (oldPay.compareTo(new BigDecimal(0)) > 0 && oldPayUser > 0) {
                    financeVo.setOldUserPayArppu(oldPay
                        .divide(new BigDecimal(oldPayUser),
                            BigDecimal.ROUND_CEILING)
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                } else {
                    financeVo.setOldUserPayArppu(new BigDecimal(0));
                }
                //DAU
                financeVo.setDau(0);
                if (null != overViewBo.getCountDau()) {
                    financeVo.setDau(overViewBo.getCountDau());
                }
                //注册数
                financeVo.setRegCount(0);
                if (null != overViewBo.getCountUser()) {
                    financeVo.setRegCount(overViewBo.getCountUser());
                }
                //活跃付费人数
                financeVo.setActiveCostCount(0);
                if (null != overViewBo.getAlivePayuser()) {
                    financeVo.setActiveCostCount(overViewBo.getAlivePayuser());
                }
                //新增付费人数
                financeVo.setAddCostCount(0);
                if (null != overViewBo.getFirstPayuser()) {
                    financeVo.setAddCostCount(overViewBo.getFirstPayuser());
                }
                resList.add(financeVo);
            }
        }
        return resList;
    }

    @Override
    public List<RoiVo> queryROIList(RoiDto roiDto) {
        QueryWrapper<RoiDto> where = new QueryWrapper<>();
        QueryWrapper<RoiDto> costWhere = new QueryWrapper<>();
        //剩余DAU
        QueryWrapper<RoiDto> dauWhere = new QueryWrapper<>();

        String gameName = "全部游戏";
        String channelName = "全部渠道";
        String dealName = "全部广告";
        if (null != roiDto.getGameId() && !roiDto.getGameId().isEmpty()) {
            where.in("a.game_id", roiDto.getGameId());
            costWhere.in("a.game_id", roiDto.getGameId());
            dauWhere.in("a.game_id", roiDto.getGameId());
            gameName = "多款游戏";
        }
        if (null != roiDto.getSubGameId() && !roiDto.getSubGameId().isEmpty()) {
            where.in("a.sub_game_id", roiDto.getSubGameId());
            costWhere.in("a.sub_game_id", roiDto.getSubGameId());
            dauWhere.in("a.sub_game_id", roiDto.getSubGameId());
            gameName += "(多款子游戏)";
        } else {
            if (null == roiDto.getGameId() || roiDto.getGameId().isEmpty()) {
                gameName += "(全部子游戏)";
            }
        }
        if (null != roiDto.getPkgId() && !roiDto.getPkgId().isEmpty()) {
            where.in("a.pkg_id", roiDto.getPkgId());
            costWhere.in("a.pkg_id", roiDto.getPkgId());
            dauWhere.in("a.pkg_id", roiDto.getPkgId());
            gameName += "(多款渠道游戏包)";
        } else {
            if (null == roiDto.getSubGameId() || roiDto.getSubGameId().isEmpty()) {
                gameName += "(全部渠道游戏包)";
            }
        }
        if (null != roiDto.getChannelTypeId() && !roiDto.getChannelTypeId().isEmpty()) {
            where.in("a.channel_type_id", roiDto.getChannelTypeId());
            costWhere.in("a.channel_type_id", roiDto.getChannelTypeId());
            dauWhere.in("a.channel_type_id", roiDto.getChannelTypeId());
        }
        if (null != roiDto.getChannelId() && !roiDto.getChannelId().isEmpty()) {
            where.in("a.channel_id", roiDto.getChannelId());
            costWhere.in("a.channel_id", roiDto.getChannelId());
            dauWhere.in("a.channel_id", roiDto.getChannelId());
            channelName = "多款渠道";
        }
        if (null != roiDto.getChannelSubAccountId() && !roiDto.getChannelSubAccountId().isEmpty()) {
            where.in("a.channel_sub_account_id", roiDto.getChannelSubAccountId());
            costWhere.in("a.channel_sub_account_id", roiDto.getChannelSubAccountId());
            dauWhere.in("a.channel_sub_account_id", roiDto.getChannelSubAccountId());
            channelName += "(多款子渠道)";
        } else {
            if (null == roiDto.getChannelId() || roiDto.getChannelId().isEmpty()) {
                channelName += "(全部子渠道)";
            }
        }
        if (null != roiDto.getDealId() && !roiDto.getDealId().isEmpty()) {
            where.in("a.deal_id", roiDto.getDealId());
            costWhere.in("a.deal_id", roiDto.getDealId());
            dauWhere.in("a.deal_id", roiDto.getDealId());
            dealName = "多款广告";
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != roiDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(roiDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
                costWhere.ge("a.cost_day", startDate);
                dauWhere.ge("a.register_time", startDate);
            }
            if (null != roiDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(roiDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
                costWhere.le("a.cost_day", endDate);
                dauWhere.le("a.register_time", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (null != roiDto.getCreateUser()) {
            where.eq("f.create_by", roiDto.getCreateUser());
            costWhere.eq("f.create_by", roiDto.getCreateUser());
            dauWhere.eq("f.create_by", roiDto.getCreateUser());
        }
        long time = (DateUtils.getMillis() / 1000 - 86400 * 2) * 1000;
        dauWhere.ge("a.update_time", DateUtils.format2Time(time));
        Map<String, BigDecimal> costMap = new LinkedHashMap<>();
        List<CostDataVo> costData = ctDailyMapper.getCostData(costWhere);
        if (null != costData && !costData.isEmpty()) {
            for (CostDataVo costDataVo : costData) {
                costMap.put(costDataVo.getCostDay(), costDataVo.getCostMoney());
            }
        }

        //剩余DAU和付费DAU
        List<DauDataVo> dauData = ctDailyMapper.getDauCostData(dauWhere);
        Map<String, Integer> regDauData = new HashMap<>();
        Map<String, Integer> payDauData = new HashMap<>();
        if (null != dauData && !dauData.isEmpty()) {
            for (DauDataVo dauDataVo : dauData) {
                if (regDauData.containsKey(dauDataVo.getRegisterTime())) {
                    regDauData.put(dauDataVo.getRegisterTime(),
                        dauDataVo.getCounts() + regDauData.get(dauDataVo.getRegisterTime()));
                } else {
                    regDauData.put(dauDataVo.getRegisterTime(), dauDataVo.getCounts());
                }
                if (null != dauDataVo.getPayTime() && !dauDataVo.getPayTime()
                    .equals("0000-00-00")) {
                    if (payDauData.containsKey(dauDataVo.getRegisterTime())) {
                        payDauData.put(dauDataVo.getRegisterTime(),
                            dauDataVo.getCounts() + payDauData.get(dauDataVo.getRegisterTime()));
                    } else {
                        payDauData.put(dauDataVo.getRegisterTime(), dauDataVo.getCounts());
                    }
                }
            }
        }
        String roiSql = "";
        for (int i = 1; i <= 150; i++) {
            roiSql += "SUM(b.day" + i + ") as day" + i + ",";
        }
        roiSql = roiSql.substring(0, roiSql.length() - 1);
        List<DailyPaybackVo> list = ctDailyMapper.getDailyPaybackData(roiSql, where);
        List<RoiVo> resList = new ArrayList<>();

        //合计对象
        RoiVo allRoiVo = new RoiVo();
        Map<String, BigDecimal> countRoiMap = new HashMap<>();
        Map<String, BigDecimal> allRemainROI = new HashMap<>();
        if (null != list && !list.isEmpty()) {
            for (DailyPaybackVo dailyPaybackVo : list) {
                RoiVo roiVo = new RoiVo();
                //日期
                roiVo.setRoiDate(dailyPaybackVo.getRegisterTime());
                //游戏
                roiVo.setGameName(gameName);
                //渠道
                roiVo.setChannelName(channelName);
                //广告
                roiVo.setDealName(dealName);
                //累计付费金额
                roiVo.setAllCostPrice(dailyPaybackVo.getTotalMoney());
                //新增付费金额
                roiVo.setAddCostPrice(dailyPaybackVo.getFirstMoney());
                //新增注册数
                roiVo.setRegCount(dailyPaybackVo.getCountUser());
                //新增付费人数
                roiVo.setCostCount(dailyPaybackVo.getFirstPayuser());
                //推广费用
                if (costMap.containsKey(roiVo.getRoiDate())) {
                    roiVo.setPromotionCost(costMap.get(roiVo.getRoiDate()));
                }
                //sdk分成所得
                roiVo.setSdkShare(
                    dailyPaybackVo.getRemainTotalMoney().setScale(2, BigDecimal.ROUND_HALF_EVEN)
                        .subtract(
                            dailyPaybackVo.getTotalMoneyIos().multiply(new BigDecimal("0.29"))));
                //真实ROI
                if (roiVo.getSdkShare().compareTo(new BigDecimal(0)) > 0 &&
                    roiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0) {
                    roiVo.setRealROI(roiVo.getSdkShare()
                        .divide(roiVo.getPromotionCost(),
                            BigDecimal.ROUND_CEILING)
                        .multiply(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_HALF_EVEN));
                }
                //累计ROI
                roiVo.setAllROI(roiVo.getRealROI());
                //利润
                roiVo.setProfit(roiVo.getSdkShare().subtract(roiVo.getPromotionCost())
                    .setScale(5, BigDecimal.ROUND_HALF_EVEN));
                //注册单价
                if (roiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                    && roiVo.getRegCount() > 0) {
                    roiVo.setRegUnitPrice(roiVo.getPromotionCost()
                        .divide(new BigDecimal(roiVo.getRegCount()),
                            BigDecimal.ROUND_CEILING)
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //付费率
                if (roiVo.getCostCount() > 0 && roiVo.getRegCount() > 0) {
                    roiVo.setCostProbability(new BigDecimal(
                        roiVo.getCostCount() / roiVo.getRegCount()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //付费单价
                if (roiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                    && roiVo.getCostCount() > 0) {
                    roiVo.setCostUnitPrice(roiVo.getPromotionCost().divide(
                            new BigDecimal(roiVo.getCostCount()),
                            BigDecimal.ROUND_CEILING)
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                //剩余DAU
                roiVo.setRemainDAU(regDauData.getOrDefault(roiVo.getRoiDate(), 0));
                //付费DAU
                if (payDauData.containsKey(roiVo.getRoiDate())) {
                    roiVo.setCostDAU(new BigDecimal(
                        String.valueOf(regDauData.get(roiVo.getRoiDate()))));
                }
                //首日ROI
                if (null != dailyPaybackVo.getDay1()
                    && dailyPaybackVo.getDay1().compareTo(new BigDecimal(0)) > 0
                    && roiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0) {
                    roiVo.setFirstROI(dailyPaybackVo.getDay1().divide(
                            new BigDecimal(String.valueOf(roiVo.getPromotionCost())),
                            BigDecimal.ROUND_CEILING)
                        .multiply(new BigDecimal(100))
                        .setScale(1, RoundingMode.HALF_EVEN));
                }
                Map<String, BigDecimal> roiMap = new HashMap<>();
                roiMap.put("day1", dailyPaybackVo.getDay1());
                //剩余ROI
                Map<String, BigDecimal> remainROI = new HashMap<>();
                Class<DailyPaybackVo> dailyPaybackVoClass = DailyPaybackVo.class;
                if (countRoiMap.containsKey("day1") && null != dailyPaybackVo.getDay1()) {
                    countRoiMap.put("day1", dailyPaybackVo.getDay1().add(countRoiMap.get("day1")));
                } else {
                    countRoiMap.put("day1", dailyPaybackVo.getDay1());
                    if (null == dailyPaybackVo.getDay1()) {
                        countRoiMap.put("day1", new BigDecimal(0));
                    }
                }
                for (int i = 2; i <= 150; i++) {
                    int j = i - 1;
                    Field declaredField = null;
                    try {
                        declaredField = dailyPaybackVoClass.getDeclaredField("day" + i);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    declaredField.setAccessible(true);
                    BigDecimal value = new BigDecimal(0);
                    try {
                        value = (BigDecimal) declaredField.get(dailyPaybackVo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    BigDecimal roi = null;
                    if (null != value && null != roiMap.get("day" + j)) {
                        roi = value.add(roiMap.get("day" + j));
                    } else {
                        roi = new BigDecimal(0);
                    }
                    roiMap.put("day" + i, roi);
                    if (null != roi && roi.compareTo(new BigDecimal(0)) > 0
                        && roiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0) {
                        remainROI.put("day" + i,
                            roi.divide(roiVo.getPromotionCost(), BigDecimal.ROUND_CEILING)
                                .multiply(new BigDecimal(100))
                                .setScale(1, BigDecimal.ROUND_HALF_EVEN));
                    } else {
                        remainROI.put("day" + i, new BigDecimal(0));
                    }
                    //累加ROI
                    if (countRoiMap.containsKey("day" + i) && null != value) {
                        countRoiMap.put("day" + i, value.add(countRoiMap.get("day" + i)));
                    } else {
                        countRoiMap.put("day" + i, new BigDecimal(0));
                    }
                }
                roiVo.setRemainROI(remainROI);
                resList.add(roiVo);
                //合计-----------------------------------------------------------------
                //推广费用
                allRoiVo.setPromotionCost(
                    allRoiVo.getPromotionCost().add(roiVo.getPromotionCost()));
                //新增付费金额
                allRoiVo.setAddCostPrice(allRoiVo.getAddCostPrice().add(roiVo.getAddCostPrice()));
                //累计付费金额
                allRoiVo.setAllCostPrice(allRoiVo.getAllCostPrice().add(roiVo.getAllCostPrice()));
                //sdk分成所得
                allRoiVo.setSdkShare(allRoiVo.getSdkShare().add(roiVo.getSdkShare()));
                //利润
                allRoiVo.setProfit(allRoiVo.getProfit().add(roiVo.getProfit()));
                //新增注册数
                allRoiVo.setRegCount(allRoiVo.getRegCount() + roiVo.getRegCount());
                //新增付费人数
                allRoiVo.setCostCount(allRoiVo.getCostCount() + roiVo.getCostCount());
                //剩余DAU
                allRoiVo.setRemainDAU(allRoiVo.getRemainDAU() + roiVo.getRemainDAU());
                //付费DAU
                allRoiVo.setCostDAU(allRoiVo.getCostDAU().add(roiVo.getCostDAU()));

                int day = DateUtils.dateToDiff('d', new Date(),
                    DateUtils.str2Date(roiVo.getRoiDate(), DateUtils.date_sdf.get()));

                for (int i = 1; i <= 149; i++) {
                    if (day < i) {
                        countRoiMap.put("day" + (i + 1), new BigDecimal(0));
                    }
                }
            }

            allRoiVo.setRoiDate("合计");
            allRoiVo.setGameName("--");
            allRoiVo.setChannelName("--");
            allRoiVo.setDealName("--");
            if (allRoiVo.getSdkShare().compareTo(new BigDecimal(0)) > 0
                && allRoiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0) {
                allRoiVo.setAllROI(allRoiVo.getSdkShare()
                    .divide(allRoiVo.getPromotionCost(), BigDecimal.ROUND_CEILING)
                    .multiply(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_HALF_EVEN));
            }
            //注册单价
            if (allRoiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                && allRoiVo.getRegCount() > 0) {
                allRoiVo.setRegUnitPrice(allRoiVo.getPromotionCost()
                    .divide(new BigDecimal(allRoiVo.getRegCount()),
                        BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //付费率
            if (allRoiVo.getCostCount() > 0 && allRoiVo.getRegCount() > 0) {
                allRoiVo.setCostProbability(new BigDecimal(
                    allRoiVo.getCostCount() / allRoiVo.getRegCount()
                        * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //付费单价
            if (allRoiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                && allRoiVo.getCostCount() > 0) {
                allRoiVo.setCostUnitPrice(allRoiVo.getPromotionCost().divide(
                        new BigDecimal(String.valueOf(allRoiVo.getCostCount())),
                        BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            //合计ROI处理
            //首日ROI
            if (countRoiMap.get("day1").compareTo(new BigDecimal(0)) > 0
                && allRoiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0) {
                allRoiVo.setFirstROI(countRoiMap.get("day1")
                    .divide(allRoiVo.getPromotionCost(), BigDecimal.ROUND_CEILING)
                    .multiply(new BigDecimal(100))
                    .setScale(1, BigDecimal.ROUND_HALF_EVEN));
            } else {
                allRoiVo.setFirstROI(new BigDecimal(0));
            }
            for (int i = 2; i <= 150; i++) {
                if (countRoiMap.get("day" + i).compareTo(new BigDecimal(0)) > 0
                    && allRoiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0) {
                    allRemainROI.put("day" + i, countRoiMap.get("day" + i)
                        .divide(allRoiVo.getPromotionCost(), BigDecimal.ROUND_CEILING)
                        .multiply(new BigDecimal(100))
                        .setScale(1, BigDecimal.ROUND_HALF_EVEN));
                } else {
                    allRemainROI.put("day" + i, new BigDecimal(0));
                }
            }
            allRoiVo.setRemainROI(allRemainROI);
            allRoiVo.setRealROI(allRoiVo.getAllROI());
            resList.add(allRoiVo);
        }

        return resList;
    }

    @Override
    public ModelAndView exportXls(RoiDto object, Class<RoiModal> clazz, String title) {
        List<RoiVo> list = queryROIList(object);
        List<RoiModal> exportList = new ArrayList<>();
        for (RoiVo roiVo : list) {
            RoiModal roiModal = new RoiModal();
            BeanUtil.copyProperties(roiVo, roiModal);
            AllRoiDailyModal bean = BeanUtil.mapToBean(roiVo.getRemainROI(), AllRoiDailyModal.class,
                false, new CopyOptions());
            BeanUtil.copyProperties(bean, roiModal);
            exportList.add(roiModal);
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams = new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(),
            title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        Map<String, Class<?>> newProperties = new HashMap<>();
        RoiVo roiVo = new RoiVo();

        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    @Override
    public ModelAndView recoveryExportXls(OverViewDto object, Class<RecoveryVo> clazz,
        String title) {
        List<RecoveryVo> exportList = queryRecoveryList(object);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams = new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(),
            title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        Map<String, Class<?>> newProperties = new HashMap<>();
        RoiVo roiVo = new RoiVo();

        mv.addObject(NormalExcelConstants.PARAMS, exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    @Override
    public void updateStartDaily(ParseStartDto parseStartDto, CtDevice ctDevice) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = simpleDateFormat.format(parseStartDto.getTime());
        String allDate = dateFormat.format(parseStartDto.getTime());

        Date day = DateUtils.str2Date(date, simpleDateFormat);
        Date allDay = DateUtils.str2Date(allDate, dateFormat);

        LambdaQueryWrapper<CtDaily> ctDailyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDailyLambdaQueryWrapper.eq(CtDaily::getDealId, parseStartDto.getDealId());
        ctDailyLambdaQueryWrapper.eq(CtDaily::getTimeDaily, day);
        CtDaily ctDaily = ctDailyMapper.selectOne(ctDailyLambdaQueryWrapper);

        if (ObjectUtils.isEmpty(ctDaily)) {
            CtDaily insertCtDaily = new CtDaily();
            insertCtDaily.setTimeDaily(day);
            insertCtDaily.setGameId(parseStartDto.getGameId());
            insertCtDaily.setPkgId(parseStartDto.getPkgId());
            insertCtDaily.setSubGameId(parseStartDto.getSubGameId());
            insertCtDaily.setChannelId(parseStartDto.getChannelId());
            insertCtDaily.setChannelTypeId(parseStartDto.getChannelTypeId());
            insertCtDaily.setChannelSubAccountId(parseStartDto.getChannelSubAccountId());
            insertCtDaily.setDealId(parseStartDto.getDealId());
            insertCtDaily.setUpdateTime(allDay);
            insertCtDaily.setCreateTime(allDay);

            if (ObjectUtils.isEmpty(ctDevice.getStartupTime())) {
                insertCtDaily.setCountActive(1);
                insertCtDaily.setCountActiveDev(1);
            } else {
                if (parseStartDto.getFirstActive() == 1) {
                    insertCtDaily.setCountActive(1);
                }
            }
            ctDailyMapper.insert(insertCtDaily);
        } else {
            if (ObjectUtils.isEmpty(ctDevice.getStartupTime())) {
                ctDaily.setCountActive(ctDaily.getCountActive() + 1);
                ctDaily.setCountActiveDev(ctDaily.getCountActiveDev() + 1);
            } else {
                if (parseStartDto.getFirstActive() == 1) {
                    ctDaily.setCountActive(ctDaily.getCountActive() + 1);
                }
            }
            ctDaily.setUpdateTime(day);
            ctDailyMapper.updateById(ctDaily);
        }
    }

    public void updateLoginDailyState(ParseLoginDto parseLoginDto, Integer countUser,
        Integer countUserDev, Integer countDau, Integer countDauDev) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = simpleDateFormat.format(parseLoginDto.getTime());
        String allDate = dateFormat.format(parseLoginDto.getTime());

        Date day = DateUtils.str2Date(date, simpleDateFormat);
        Date allDay = DateUtils.str2Date(allDate, dateFormat);

        LambdaQueryWrapper<CtDaily> ctDailyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDailyLambdaQueryWrapper.eq(CtDaily::getDealId, parseLoginDto.getDealId());
        ctDailyLambdaQueryWrapper.eq(CtDaily::getTimeDaily, day);
        CtDaily ctDaily = ctDailyMapper.selectOne(ctDailyLambdaQueryWrapper);

        if (ObjectUtils.isEmpty(ctDaily)) {
            CtDaily insertCtDaily = new CtDaily();
            insertCtDaily.setTimeDaily(day);
            insertCtDaily.setGameId(parseLoginDto.getGameId());
            insertCtDaily.setPkgId(parseLoginDto.getPkgId());
            insertCtDaily.setSubGameId(parseLoginDto.getSubGameId());
            insertCtDaily.setChannelId(parseLoginDto.getChannelId());
            insertCtDaily.setChannelTypeId(parseLoginDto.getChannelTypeId());
            insertCtDaily.setChannelSubAccountId(parseLoginDto.getChannelSubAccountId());
            insertCtDaily.setDealId(parseLoginDto.getDealId());
            insertCtDaily.setUpdateTime(allDay);
            insertCtDaily.setCreateTime(allDay);

            insertCtDaily.setCountUser(countUser);
            insertCtDaily.setCountUserDev(countUserDev);
            insertCtDaily.setCountDau(countDau);
            insertCtDaily.setCountDauDev(countDauDev);
            ctDailyMapper.insert(insertCtDaily);
        } else {
            ctDaily.setUpdateTime(day);
            ctDaily.setCountUser(ctDaily.getCountUser() + countUser);
            ctDaily.setCountUserDev(ctDaily.getCountUserDev() + countUserDev);
            ctDaily.setCountDau(ctDaily.getCountDau() + countDau);
            ctDaily.setCountDauDev(ctDaily.getCountDauDev() + countDauDev);
            ctDailyMapper.updateById(ctDaily);
        }
    }

    @Override
    public void updateLoginDaily(ParseLoginDto parseLoginDto, CtDevice ctDevice, CtUser ctUser) {

        Integer countDauDev = 0;
        Integer countUserDev = 0;
        Integer countDau = 0;
        Integer countUser = 0;
        String daysNum = "";
        String devDaysNum = "";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(parseLoginDto.getTime());
        Date timeDay = DateUtils.str2Date(date, simpleDateFormat);

        Calendar instanceTimeDay = Calendar.getInstance();
        instanceTimeDay.setTime(timeDay);

        if (ObjectUtils.isEmpty(ctDevice.getLoginTime())) {
            countDauDev = 1;
            countUserDev = 1;
        } else {

            int devDays = DateUtils.dateToDiff('d', timeDay, ctDevice.getCreateTime());
            int gapDevDays = DateUtils.dateToDiff('d', timeDay, ctDevice.getLoginTime());

            if (devDays > 0 && devDays < 150 && gapDevDays >= 1) {
                // TODO 更新设备留存表
                devDaysNum = String.valueOf(devDays + 1);
                countDauDev = 1;
                ctDailyLoyalDevService.updateLoginDailyDev(parseLoginDto, devDaysNum,
                    ctUser.getCreateTime());
            }
        }

        if (ObjectUtils.isEmpty(ctUser.getLoginTime())) {
            countDau = 1;
            countUser = 1;
        } else {

            int days = DateUtils.dateToDiff('d', timeDay, ctUser.getCreateTime());
            int gapDays = DateUtils.dateToDiff('d', timeDay, ctUser.getLoginTime());

            if (days > 0 && days < 150 && gapDays >= 1) {
                // TODO 更新用户留存表
                daysNum = String.valueOf(days + 1);
                countDau = 1;
                ctDailyLoyalService.updateLoginDaily(parseLoginDto, daysNum,
                    ctUser.getCreateTime());
            }
        }
        updateLoginDailyState(parseLoginDto, countUser, countUserDev, countDau, countDauDev);
        ctHourService.updateLoginHourState(parseLoginDto, countUser, countUserDev, countDau,
            countDauDev);
    }

    @Override
    public void updateAliveDaily(ParseAliveDto parseAliveDto, CtDevice ctDevice, CtUser ctUser) {

        Integer countValidDev = 0;
        Integer countValid = 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(parseAliveDto.getTime());

        Date day = DateUtils.str2Date(date, simpleDateFormat);

        int userSecond = DateUtils.dateToDiff('s', day, ctUser.getCreateTime());
        int deviceSecond = DateUtils.dateToDiff('s', day, ctDevice.getCreateTime());

        if (ObjectUtils.isEmpty(ctDevice.getPlayTime()) && deviceSecond > 595) {
            countValidDev = 1;
        }

        if (ObjectUtils.isEmpty(ctUser.getPlayTime()) && userSecond > 595) {
            countValid = 1;
        }

        updateAliveDailyState(parseAliveDto, countValid, countValidDev, ctUser.getCreateTime());
        ctHourService.updateAliveHourState(parseAliveDto, countValid, countValidDev,
            ctUser.getCreateTime());
    }

    public void updateAliveDailyState(ParseAliveDto parseAliveDto, Integer countValid,
        Integer countValidDev, Date userTime) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = simpleDateFormat.format(parseAliveDto.getTime());
        String time = simpleDateFormat.format(userTime);
        String allDate = dateFormat.format(parseAliveDto.getTime());

        Date day = DateUtils.str2Date(date, simpleDateFormat);
        Date timeDay = DateUtils.str2Date(time, simpleDateFormat);
        Date allDay = DateUtils.str2Date(allDate, dateFormat);

        LambdaQueryWrapper<CtDaily> ctDailyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDailyLambdaQueryWrapper.eq(CtDaily::getDealId, parseAliveDto.getDealId());
        ctDailyLambdaQueryWrapper.eq(CtDaily::getTimeDaily, timeDay);

        CtDaily ctDaily = ctDailyMapper.selectOne(ctDailyLambdaQueryWrapper);

        if (ObjectUtils.isEmpty(ctDaily)) {
            CtDaily insertCtDaily = new CtDaily();
            insertCtDaily.setTimeDaily(day);
            insertCtDaily.setGameId(parseAliveDto.getGameId());
            insertCtDaily.setPkgId(parseAliveDto.getPkgId());
            insertCtDaily.setSubGameId(parseAliveDto.getSubGameId());
            insertCtDaily.setChannelId(parseAliveDto.getChannelId());
            insertCtDaily.setChannelTypeId(parseAliveDto.getChannelTypeId());
            insertCtDaily.setChannelSubAccountId(parseAliveDto.getChannelSubAccountId());
            insertCtDaily.setDealId(parseAliveDto.getDealId());
            insertCtDaily.setUpdateTime(allDay);
            insertCtDaily.setCreateTime(allDay);

            insertCtDaily.setCountValidUser(countValid);
            insertCtDaily.setCountValidUserDev(countValidDev);
            ctDailyMapper.insert(insertCtDaily);
        } else {
            ctDaily.setUpdateTime(day);
            ctDaily.setCountValidUser(ctDaily.getCountValidUser() + countValid);
            ctDaily.setCountValidUserDev(ctDaily.getCountValidUserDev() + countValidDev);
            ctDailyMapper.updateById(ctDaily);
        }
    }

    @Override
    public void parsePayDaily(ParsePayDto parsePayDto, CtDevice ctDevice, CtUser ctUser) {
        // 日志时间 时间类型
        Date logDate = DateUtils.getDate(parsePayDto.getTime());
        // 初始化对象
        CtDaily ctDailyLog = initPayDaily(parsePayDto, logDate, logDate);
        CtDaily ctDailyUser = initPayDaily(parsePayDto, ctUser.getCreateTime(), logDate);
        CtDaily ctDailyDevice = initPayDaily(parsePayDto, ctDevice.getCreateTime(),
            logDate);
        CtHour ctHourLog = ctHourService.initPayHour(parsePayDto, logDate, logDate);
        CtHour ctHourUser = ctHourService.initPayHour(parsePayDto, ctUser.getCreateTime(),
            logDate);
        CtHour ctHourDevice = ctHourService.initPayHour(parsePayDto,
            ctDevice.getCreateTime(), logDate);
        // 支付过就算有效注册数
        if (ctUser.getPlayTime() == null) {
            ctDailyUser.setCountValidUser(CountUtil.increaseInt(ctDailyUser.getCountValidUser()));
            ctHourUser.setCountValidUser(CountUtil.increaseInt(ctHourUser.getCountValidUser()));
        }
        if (ctDevice.getPlayTime() == null) {
            ctDailyDevice.setCountValidUserDev(
                CountUtil.increaseInt(ctDailyDevice.getCountValidUserDev()));
            ctHourDevice.setCountValidUserDev(
                CountUtil.increaseInt(ctHourDevice.getCountValidUserDev()));
        }
        // 活跃付费
        // 活跃付费金额
        ctDailyLog.setAliveMoney(
            CountUtil.addDec(ctDailyLog.getAliveMoney(), parsePayDto.getMoney()));
        ctHourLog.setAliveMoney(
            CountUtil.addDec(ctHourLog.getAliveMoney(), parsePayDto.getMoney()));
        // 活跃付费订单数
        ctDailyLog.setAliveOrder(CountUtil.increaseInt(ctDailyLog.getAliveOrder()));
        ctHourLog.setAliveOrder(CountUtil.increaseInt(ctHourLog.getAliveOrder()));
        // 活跃付费优惠金额
        if (parsePayDto.getCouponPrice() != null) {
            ctDailyLog.setAliveMoneyDiscount(
                CountUtil.addDec(ctDailyLog.getAliveMoneyDiscount(), parsePayDto.getCouponPrice()));
            ctHourLog.setAliveMoneyDiscount(
                CountUtil.addDec(ctHourLog.getAliveMoneyDiscount(), parsePayDto.getCouponPrice()));
        }
        // 活跃付费人数、设备数
        if (DateUtils.isSameDay(logDate, ctDevice.getPayTime())) {
            ctDailyLog.setAlivePayuserDev(CountUtil.increaseInt(ctDailyLog.getAlivePayuserDev()));
        }
        if (DateUtils.isSameHour(logDate, ctDevice.getPayTime())) {
            ctHourLog.setAlivePayuserDev(CountUtil.increaseInt(ctHourLog.getAlivePayuserDev()));
        }
        if (DateUtils.isSameDay(logDate, ctUser.getPayTime())) {
            ctDailyLog.setAlivePayuser(CountUtil.increaseInt(ctDailyLog.getAlivePayuser()));
        }
        if (DateUtils.isSameHour(logDate, ctUser.getPayTime())) {
            ctHourLog.setAlivePayuser(CountUtil.increaseInt(ctHourLog.getAlivePayuser()));
        }
        // 活跃苹果支付金额
        if (PayTypeConstant.PAY_TYPE_APPLE.equals(parsePayDto.getPayType())) {
            ctDailyLog.setAliveMoneyIos(
                CountUtil.addDec(ctDailyLog.getAliveMoneyIos(), parsePayDto.getMoney()));
        }
        // 累计付费
        // 累计付费金额
        ctDailyUser.setTotalMoney(
            CountUtil.addDec(ctDailyUser.getTotalMoney(), parsePayDto.getMoney()));
        ctDailyDevice.setTotalMoney(
            CountUtil.addDec(ctDailyDevice.getTotalMoney(), parsePayDto.getMoney()));
        ctHourUser.setTotalMoney(
            CountUtil.addDec(ctHourUser.getTotalMoney(), parsePayDto.getMoney()));
        ctHourDevice.setTotalMoney(
            CountUtil.addDec(ctHourDevice.getTotalMoney(), parsePayDto.getMoney()));
        // 累计付费订单数
        ctDailyUser.setTotalOrder(CountUtil.increaseInt(ctDailyUser.getTotalOrder()));
        ctDailyDevice.setTotalOrder(CountUtil.increaseInt(ctDailyDevice.getTotalOrder()));
        ctHourUser.setTotalOrder(CountUtil.increaseInt(ctHourUser.getTotalOrder()));
        ctHourDevice.setTotalOrder(CountUtil.increaseInt(ctHourDevice.getTotalOrder()));
        // 累计优惠金额
        if (parsePayDto.getCouponPrice() != null) {
            ctDailyUser.setTotalMoneyDiscount(
                CountUtil.addDec(ctDailyUser.getTotalMoneyDiscount(),
                    parsePayDto.getCouponPrice()));
            ctDailyDevice.setTotalMoneyDiscount(
                CountUtil.addDec(ctDailyDevice.getTotalMoneyDiscount(),
                    parsePayDto.getCouponPrice()));
            ctHourUser.setTotalMoneyDiscount(
                CountUtil.addDec(ctHourUser.getTotalMoneyDiscount(), parsePayDto.getCouponPrice()));
            ctHourDevice.setTotalMoneyDiscount(
                CountUtil.addDec(ctHourDevice.getTotalMoneyDiscount(),
                    parsePayDto.getCouponPrice()));
        }
        // 累计付费人数
        if (ctUser.getPayTime() == null) {
            ctDailyUser.setTotalPayuser(CountUtil.increaseInt(ctDailyUser.getTotalPayuser()));
            ctHourUser.setTotalPayuser(CountUtil.increaseInt(ctHourUser.getTotalPayuser()));
        }
        if (ctDevice.getPayTime() == null) {
            ctDailyUser.setTotalPayuserDev(CountUtil.increaseInt(ctDailyUser.getTotalPayuserDev()));
            ctHourUser.setTotalPayuserDev(CountUtil.increaseInt(ctHourUser.getTotalPayuserDev()));
        }
        // 累计苹果支付金额
        if (PayTypeConstant.PAY_TYPE_APPLE.equals(parsePayDto.getPayType())) {
            ctDailyUser.setTotalMoneyIos(
                CountUtil.addDec(ctDailyUser.getTotalMoneyIos(), parsePayDto.getMoney()));
            ctDailyUser.setTotalMoneyIosDev(
                CountUtil.addDec(ctDailyUser.getTotalMoneyIosDev(), parsePayDto.getMoney()));
        }
        // 计算首日-按用户
        if (DateUtils.isSameDay(logDate, ctUser.getCreateTime())) {
            // 用户首日付费
            ctDailyUser.setFirstMoney(
                CountUtil.addDec(ctDailyUser.getFirstMoney(), parsePayDto.getMoney()));
            ctHourUser.setFirstMoney(
                CountUtil.addDec(ctHourUser.getFirstMoney(), parsePayDto.getMoney()));
            // 用户首日付费订单
            ctDailyUser.setFirstOrder(CountUtil.increaseInt(ctDailyUser.getFirstOrder()));
            ctHourUser.setFirstOrder(CountUtil.increaseInt(ctHourUser.getFirstOrder()));
            // 用户首日苹果支付金额
            if (PayTypeConstant.PAY_TYPE_APPLE.equals(parsePayDto.getPayType())) {
                ctDailyUser.setFirstMoneyIos(
                    CountUtil.addDec(ctDailyUser.getFirstMoneyIos(), parsePayDto.getMoney()));
            }
            // 用户首日付费优惠
            if (parsePayDto.getCouponPrice() != null) {
                ctDailyUser.setFirstMoneyDiscount(
                    CountUtil.addDec(ctDailyUser.getTotalMoneyDiscount(),
                        parsePayDto.getCouponPrice()));
                ctHourUser.setFirstMoneyDiscount(
                    CountUtil.addDec(ctHourUser.getTotalMoneyDiscount(),
                        parsePayDto.getCouponPrice()));
            }
            // 用户首日付费人数
            if (ctUser.getPayTime() == null) {
                ctDailyUser.setFirstPayuser(CountUtil.increaseInt(ctDailyUser.getFirstPayuser()));
                ctHourUser.setFirstPayuser(CountUtil.increaseInt(ctHourUser.getFirstPayuser()));
            }
        }
        // 计算首日-按设备
        if (DateUtils.isSameDay(logDate, ctDevice.getCreateTime())) {
            // 设备首日付费
            ctDailyDevice.setFirstMoney(
                CountUtil.addDec(ctDailyDevice.getFirstMoney(), parsePayDto.getMoney()));
            ctHourDevice.setFirstMoney(
                CountUtil.addDec(ctHourDevice.getFirstMoney(), parsePayDto.getMoney()));
            // 设备首日付费订单
            ctDailyDevice.setFirstOrder(CountUtil.increaseInt(ctDailyDevice.getFirstOrder()));
            ctHourDevice.setFirstOrder(CountUtil.increaseInt(ctHourDevice.getFirstOrder()));
            // 设备首日苹果支付金额
            if (PayTypeConstant.PAY_TYPE_APPLE.equals(parsePayDto.getPayType())) {
                ctDailyDevice.setFirstMoneyIos(
                    CountUtil.addDec(ctDailyDevice.getFirstMoneyIos(), parsePayDto.getMoney()));
            }
            // 设备首日付费优惠
            if (parsePayDto.getCouponPrice() != null) {
                ctDailyDevice.setFirstMoneyDiscount(
                    CountUtil.addDec(ctDailyDevice.getTotalMoneyDiscount(),
                        parsePayDto.getCouponPrice()));
                ctHourDevice.setFirstMoneyDiscount(
                    CountUtil.addDec(ctHourDevice.getTotalMoneyDiscount(),
                        parsePayDto.getCouponPrice()));
            }
            // 设备首日付费人数
            if (!DateUtils.isSameDay(ctUser.getPayTime(), logDate)) {
                ctDailyDevice.setFirstPayuser(
                    CountUtil.increaseInt(ctDailyDevice.getFirstPayuser()));
                ctHourDevice.setFirstPayuser(CountUtil.increaseInt(ctHourDevice.getFirstPayuser()));
            }
        }
        savePayDaily(ctDailyLog, ctDailyDevice, ctDailyUser);
        savePayHour(ctHourLog, ctHourDevice, ctHourUser);
        // 更新用户回本表
        ctDailyPaybackService.parsePayback(ctDailyUser.getId(), ctUser.getCreateTime(),
            parsePayDto.getMoney(), logDate);
        // 更新设备回本表
        ctDailyPaybackDevService.parsePaybackDev(ctDailyDevice.getId(), ctDevice.getCreateTime(),
            parsePayDto.getMoney(), logDate);
    }

    @Override
    public List<SummaryDailyBo> getSummaryDaily(SummaryDto summaryDto) {
        QueryWrapper<CtDaily> wrapper = new QueryWrapper();
        wrapper.in(CollectionUtil.isNotEmpty(summaryDto.getGameId()), "a.game_id",
            summaryDto.getGameId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryDto.getSubGameId()), "a.sub_game_id",
            summaryDto.getSubGameId());
        wrapper.eq(oConvertUtils.isNotEmpty(summaryDto.getCostPlatform()), "a.platform",
            summaryDto.getCostPlatform());
        wrapper.in(CollectionUtil.isNotEmpty(summaryDto.getPkgId()), "a.pkg_id",
            summaryDto.getPkgId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryDto.getAccountId()), "b.account_id",
            summaryDto.getAccountId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryDto.getDealId()), "a.deal_id",
            summaryDto.getDealId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryDto.getChannelTypeId()), "a.channel_type_id",
            summaryDto.getChannelTypeId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryDto.getChannelId()), "a.channel_id",
            summaryDto.getChannelId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryDto.getChannelSubAccountId()),
            "a.channel_sub_account_id",
            summaryDto.getChannelSubAccountId());
        wrapper.ge(oConvertUtils.isNotEmpty(summaryDto.getRegStartTime()), "a.time_daily",
            summaryDto.getRegStartTime());
        wrapper.le(oConvertUtils.isNotEmpty(summaryDto.getRegEndTime()), "a.time_daily",
            summaryDto.getRegEndTime());
        // 创建用户
        wrapper.eq(oConvertUtils.isNotEmpty(summaryDto.getCreateUser()), "b.create_by",
            summaryDto.getCreateUser());
        SummaryEnum summaryEnum = SummaryEnum.valueOfCode(summaryDto.getGroupType());
        wrapper.groupBy(summaryEnum.getGroupDaily());
        List<SummaryDailyBo> summaryDailyBoList = baseMapper.getSummaryDaily(
            summaryEnum.getGroupDaily(), summaryEnum.getGroupName(), wrapper);
        return summaryDailyBoList;
    }

    @Override
    public List<DetailDailyBo> getDetailDaily(DetailDto detailDto) {
        QueryWrapper<CtDaily> wrapper = new QueryWrapper();
        wrapper.in(CollectionUtil.isNotEmpty(detailDto.getGameId()), "a.game_id",
            detailDto.getGameId());
        wrapper.in(CollectionUtil.isNotEmpty(detailDto.getSubGameId()), "a.sub_game_id",
            detailDto.getSubGameId());
        wrapper.in(CollectionUtil.isNotEmpty(detailDto.getPkgId()), "a.pkg_id",
            detailDto.getPkgId());
        wrapper.in(CollectionUtil.isNotEmpty(detailDto.getDealId()), "a.deal_id",
            detailDto.getDealId());
        wrapper.in(CollectionUtil.isNotEmpty(detailDto.getChannelTypeId()), "a.channel_type_id",
            detailDto.getChannelTypeId());
        wrapper.in(CollectionUtil.isNotEmpty(detailDto.getChannelId()), "a.channel_id",
            detailDto.getChannelId());
        wrapper.in(CollectionUtil.isNotEmpty(detailDto.getChannelSubAccountId()),
            "a.channel_sub_account_id",
            detailDto.getChannelSubAccountId());
        wrapper.ge(oConvertUtils.isNotEmpty(detailDto.getRegStartTime()), "a.time_daily",
            detailDto.getRegStartTime());
        wrapper.le(oConvertUtils.isNotEmpty(detailDto.getRegEndTime()), "a.time_daily",
            detailDto.getRegEndTime());
        wrapper.groupBy("a.time_daily");
        wrapper.orderByDesc("a.time_daily");
        List<DetailDailyBo> detailDailyBoList = baseMapper.getDetailDaily(wrapper);
        return detailDailyBoList;
    }

    @Override
    public List<SummaryAdvertDailyBo> getSummaryAdvertDaily(SummaryAdvertDto summaryAdvertDto) {
        QueryWrapper<CtDaily> wrapper = new QueryWrapper();
        wrapper.in(CollectionUtil.isNotEmpty(summaryAdvertDto.getGameId()), "a.game_id",
            summaryAdvertDto.getGameId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryAdvertDto.getSubGameId()), "a.sub_game_id",
            summaryAdvertDto.getSubGameId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryAdvertDto.getPkgId()), "a.pkg_id",
            summaryAdvertDto.getPkgId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryAdvertDto.getDealId()), "a.deal_id",
            summaryAdvertDto.getDealId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryAdvertDto.getChannelTypeId()),
            "a.channel_type_id",
            summaryAdvertDto.getChannelTypeId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryAdvertDto.getChannelId()), "a.channel_id",
            summaryAdvertDto.getChannelId());
        wrapper.in(CollectionUtil.isNotEmpty(summaryAdvertDto.getChannelSubAccountId()),
            "a.channel_sub_account_id",
            summaryAdvertDto.getChannelSubAccountId());
        wrapper.ge(oConvertUtils.isNotEmpty(summaryAdvertDto.getRegStartTime()), "a.time_daily",
            summaryAdvertDto.getRegStartTime());
        wrapper.le(oConvertUtils.isNotEmpty(summaryAdvertDto.getRegEndTime()), "a.time_daily",
            summaryAdvertDto.getRegEndTime());
        String day = "a.time_daily";
        String dealId = "a.deal_id";
        String pkgName = "c.pkg_name";
        String dealName = "b.deal_name";
        String channelName = "f.channel_name";
        if (SummaryAdvertType.ALL.equals(summaryAdvertDto.getGroupType())) {
            wrapper.groupBy(day);
            dealId = "'全部'";
            pkgName = "'全部渠道游戏包'";
            dealName = "'全部广告'";
            channelName = "'全部渠道'";
        } else {
            wrapper.groupBy(day, dealId, pkgName, dealName, channelName);
        }
        wrapper.orderByDesc("a.time_daily");
        List<SummaryAdvertDailyBo> summaryAdvertDailyBoList = baseMapper.getSummaryAdvertDaily(day,
            dealId, pkgName, dealName, channelName, wrapper);
        return summaryAdvertDailyBoList;
    }

    @Override
    public List<UserPayRateDailyBo> getUserPayRateDaily(UserPayRateDto userPayRateDto) {
        String startTime = userPayRateDto.getRegStartTime() + " 00:00:00";
        String endTime = userPayRateDto.getRegEndTime() + " 23:59:59";
        QueryWrapper<CtDaily> wrapper = new QueryWrapper();
        wrapper.in(CollectionUtil.isNotEmpty(userPayRateDto.getGameId()), "a.game_id",
            userPayRateDto.getGameId());
        wrapper.in(CollectionUtil.isNotEmpty(userPayRateDto.getSubGameId()), "a.sub_game_id",
            userPayRateDto.getSubGameId());
        wrapper.in(CollectionUtil.isNotEmpty(userPayRateDto.getPkgId()), "a.pkg_id",
            userPayRateDto.getPkgId());
        wrapper.in(CollectionUtil.isNotEmpty(userPayRateDto.getDealId()), "a.deal_id",
            userPayRateDto.getDealId());
        wrapper.in(CollectionUtil.isNotEmpty(userPayRateDto.getChannelTypeId()),
            "a.channel_type_id", userPayRateDto.getChannelTypeId());
        wrapper.in(CollectionUtil.isNotEmpty(userPayRateDto.getChannelId()), "a.channel_id",
            userPayRateDto.getChannelId());
        wrapper.in(CollectionUtil.isNotEmpty(userPayRateDto.getChannelSubAccountId()),
            "a.channel_sub_account_id",
            userPayRateDto.getChannelSubAccountId());
        wrapper.ge("a.time_daily", startTime);
        wrapper.le("a.time_daily", endTime);
        String day = "DATE(a.time_daily)";
        String pkgName = "c.pkg_name";
        String dealName = "b.deal_name";
        String channelName = "f.channel_name";
        List<String> groupByArr = new ArrayList<>();
        groupByArr.add("a.time_daily");
        if (CollectionUtil.isNotEmpty(userPayRateDto.getPkgId())
            && userPayRateDto.getPkgId().size() == 1) {
            groupByArr.add(pkgName);
        } else {
            pkgName = "'全部游戏包'";
        }
        if (CollectionUtil.isNotEmpty(userPayRateDto.getDealId())
            && userPayRateDto.getDealId().size() == 1) {
            groupByArr.add(dealName);
        } else {
            dealName = "'全部广告'";
        }
        if (CollectionUtil.isNotEmpty(userPayRateDto.getChannelId())
            && userPayRateDto.getChannelId().size() == 1) {
            groupByArr.add(channelName);
        } else {
            channelName = "'全部渠道'";
        }
        wrapper.groupBy(groupByArr);
        wrapper.orderByAsc("a.time_daily");
        List<UserPayRateDailyBo> userPayRateDailyBoList = baseMapper.getUserPayRateDaily(day,
            pkgName, dealName, channelName, wrapper);
        return userPayRateDailyBoList;
    }

    @Override
    public List<WeekReportDailyBo> getWeekReportDaily(GetWeekReportDailyBo getWeekReportDailyBo) {
        QueryWrapper<CtDaily> wrapper = new QueryWrapper();
        wrapper.in(CollectionUtil.isNotEmpty(getWeekReportDailyBo.getGameId()), "a.game_id",
            getWeekReportDailyBo.getGameId());
        wrapper.in(CollectionUtil.isNotEmpty(getWeekReportDailyBo.getSubGameId()), "a.sub_game_id",
            getWeekReportDailyBo.getSubGameId());
        wrapper.in(CollectionUtil.isNotEmpty(getWeekReportDailyBo.getPkgId()), "a.pkg_id",
            getWeekReportDailyBo.getPkgId());
        wrapper.in(CollectionUtil.isNotEmpty(getWeekReportDailyBo.getChannelTypeId()),
            "a.channel_type_id", getWeekReportDailyBo.getChannelTypeId());
        wrapper.in(CollectionUtil.isNotEmpty(getWeekReportDailyBo.getChannelId()), "a.channel_id",
            getWeekReportDailyBo.getChannelId());
        wrapper.in(CollectionUtil.isNotEmpty(getWeekReportDailyBo.getChannelSubAccountId()),
            "a.channel_sub_account_id",
            getWeekReportDailyBo.getChannelSubAccountId());
        wrapper.ge(StringUtils.isNotEmpty(getWeekReportDailyBo.getRegStartTime()), "a.time_daily",
            getWeekReportDailyBo.getRegStartTime());
        wrapper.le(StringUtils.isNotEmpty(getWeekReportDailyBo.getRegEndTime()), "a.time_daily",
            getWeekReportDailyBo.getRegEndTime());
        wrapper.groupBy(
            "a.game_id,a.sub_game_id,a.pkg_id,a.channel_type_id,a.channel_id,a.channel_sub_account_id,a.time_daily");
        return baseMapper.getWeekReportDaily(wrapper);
    }

    /**
     * @param ctDailyLog
     * @param ctDailyDevice
     * @param ctDailyUser
     * @author chenyw
     * @description 支付-更新日汇总表
     * @date 11:00 2023/4/23/023
     **/
    private void savePayDaily(CtDaily ctDailyLog, CtDaily ctDailyDevice, CtDaily ctDailyUser) {
        if (ctDailyLog.getTimeDaily().equals(ctDailyDevice.getTimeDaily())) {
            BeanUtils.copyProperties(ctDailyDevice, ctDailyLog);
            if (ctDailyLog.getTimeDaily().equals(ctDailyUser.getTimeDaily())) {
                BeanUtils.copyProperties(ctDailyUser, ctDailyLog);
                saveOrUpdate(ctDailyLog);
                // 和ctDailyLog为同一个对象，返回id
                ctDailyUser.setId(ctDailyLog.getId());
            } else {
                saveOrUpdate(ctDailyLog);
                saveOrUpdate(ctDailyUser);
            }
            // 和ctDailyLog为同一个对象，返回id
            ctDailyDevice.setId(ctDailyLog.getId());
        } else {
            if (ctDailyLog.getTimeDaily().equals(ctDailyUser.getTimeDaily())) {
                BeanUtils.copyProperties(ctDailyLog, ctDailyUser);
                saveOrUpdate(ctDailyLog);
                // 和ctDailyUser为同一个对象，返回id
                ctDailyUser.setId(ctDailyLog.getId());
                saveOrUpdate(ctDailyDevice);
            } else {
                saveOrUpdate(ctDailyLog);
                saveOrUpdate(ctDailyDevice);
                saveOrUpdate(ctDailyUser);
            }
        }
    }

    /**
     * @param ctHourLog
     * @param ctHourDevice
     * @param ctHourUser
     * @author chenyw
     * @description 支付-更新小时汇总表
     * @date 11:10 2023/4/23/023
     **/
    private void savePayHour(CtHour ctHourLog, CtHour ctHourDevice, CtHour ctHourUser) {
        if (ctHourLog.getTimeHour().equals(ctHourLog.getTimeHour())) {
            BeanUtils.copyProperties(ctHourDevice, ctHourLog);
            if (ctHourLog.getTimeHour().equals(ctHourUser.getTimeHour())) {
                BeanUtils.copyProperties(ctHourUser, ctHourLog);
                ctHourService.saveOrUpdate(ctHourLog);
            } else {
                ctHourService.saveOrUpdate(ctHourLog);
                ctHourService.saveOrUpdate(ctHourUser);
            }
        } else {
            if (ctHourLog.getTimeHour().equals(ctHourUser.getTimeHour())) {
                BeanUtils.copyProperties(ctHourLog, ctHourUser);
                ctHourService.saveOrUpdate(ctHourLog);
                ctHourService.saveOrUpdate(ctHourDevice);
            } else {
                ctHourService.saveOrUpdate(ctHourLog);
                ctHourService.saveOrUpdate(ctHourDevice);
                ctHourService.saveOrUpdate(ctHourUser);
            }
        }
    }


    /**
     * @param parsePayDto 解析支付内容
     * @param dailyDate   统计时间
     * @param logTime     日志时间
     * @return org.jeecg.modules.count.entity.CtDaily
     * @author chenyw
     * @description 初始化daily
     * @date 16:46 2023/4/20/020
     **/
    private CtDaily initPayDaily(ParsePayDto parsePayDto, Date dailyDate, Date logTime) {
        Date timeDaily = DateUtils.str2Date(
            DateUtils.date_sdf.get().format(dailyDate),
            DateUtils.date_sdf.get());
        CtDaily ctDaily = getOne(new LambdaQueryWrapper<CtDaily>()
            .eq(CtDaily::getDealId, parsePayDto.getDealId())
            .eq(CtDaily::getTimeDaily, timeDaily));
        if (ctDaily == null) {
            ctDaily = new CtDaily();
            ctDaily.setGameId(parsePayDto.getGameId());
            ctDaily.setSubGameId(parsePayDto.getSubGameId());
            ctDaily.setChannelId(parsePayDto.getChannelId());
            ctDaily.setChannelTypeId(parsePayDto.getChannelTypeId());
            ctDaily.setChannelSubAccountId(parsePayDto.getChannelSubAccountId());
            ctDaily.setDealId(parsePayDto.getDealId());
            ctDaily.setPkgId(parsePayDto.getPkgId());
            ctDaily.setTimeDaily(timeDaily);
            ctDaily.setCreateTime(logTime);
            ctDaily.setUpdateTime(logTime);
        }
        return ctDaily;
    }

}
