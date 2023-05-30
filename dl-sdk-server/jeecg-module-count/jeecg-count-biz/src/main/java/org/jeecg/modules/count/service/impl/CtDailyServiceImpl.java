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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.PayTypeConstant;
import org.jeecg.modules.count.bo.SummaryAdvertDailyBo;
import org.jeecg.modules.count.constant.SummaryAdvertType;
import org.jeecg.modules.count.constant.enums.SummaryEnum;
import org.jeecg.modules.count.bo.DetailDailyBo;
import org.jeecg.modules.count.dto.DetailDto;
import org.jeecg.modules.count.dto.SummaryAdvertDto;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.CountUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.bo.summary.SummaryDailyBo;
import org.jeecg.modules.count.dto.RoiDto;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.entity.CtDailyPaybackDev;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtHour;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.mapper.CtDailyMapper;
import org.jeecg.modules.count.modal.AllRoiDailyModal;
import org.jeecg.modules.count.modal.CtRoleModal;
import org.jeecg.modules.count.modal.ROIListResult;
import org.jeecg.modules.count.modal.RoiModal;
import org.jeecg.modules.count.service.ICtDailyPaybackDevService;
import org.jeecg.modules.count.service.ICtDailyPaybackService;
import org.jeecg.modules.count.service.ICtDailyService;
import org.jeecg.modules.count.service.ICtHourService;
import org.jeecg.modules.count.vo.DailyPaybackVo;
import org.jeecg.modules.count.vo.DauDataVo;
import org.jeecg.modules.count.vo.RoiCostVo;
import org.jeecg.modules.count.vo.RoiVo;
import org.jeecg.modules.count.vo.CostDataVo;
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
@DS("open_countly")
public class CtDailyServiceImpl extends ServiceImpl<CtDailyMapper, CtDaily> implements
    ICtDailyService {

    @Autowired
    private CtDailyMapper ctDailyMapper;
    @Autowired
    private ICtHourService ctHourService;
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
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(roiDto.getStartTime());
                calendar.add(Calendar.DAY_OF_MONTH, -90);
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd 00:00:00"));
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
//        //组装出详细的成本列表
//        List<RoiCostVo> roiCostData = ctDailyMapper.getRoiCostData(costWhere);
        //剩余DAU和付费DAU
        List<DauDataVo> dauData = ctDailyMapper.getDauCostData(dauWhere);
        Map<String, Integer> regDauData = new HashMap<>();
        Map<String, Integer> payDauData = new HashMap<>();
        if (null != dauData && !dauData.isEmpty()) {
            for (DauDataVo dauDataVo : dauData) {
                regDauData.put(dauDataVo.getRegisterTime(), dauDataVo.getCounts());
                if (null != dauDataVo.getPayTime() && !dauDataVo.getPayTime()
                    .equals("0000-00-00")) {
                    payDauData.put(dauDataVo.getRegisterTime(), dauDataVo.getCounts());
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
        BigDecimal allPromotionCost = new BigDecimal(0);
        Integer allRegCount = 0;
        Integer allRemainDAU = 0;
        BigDecimal allCostDAU = new BigDecimal(0);
        BigDecimal allRegUnitPrice = new BigDecimal(0);
        Integer allCostCount = 0;
        BigDecimal allCostProbability = new BigDecimal(0);
        BigDecimal allCostUnitPrice = new BigDecimal(0);
        BigDecimal allAddCostPrice = new BigDecimal(0);
        BigDecimal allAllCostPrice = new BigDecimal(0);
        BigDecimal allSdkShare = new BigDecimal(0);
        BigDecimal allProfit = new BigDecimal(0);
//        BigDecimal allEstimatedROI = new BigDecimal(0);
        BigDecimal allAllROI = new BigDecimal(0);
        BigDecimal allFirstROI = new BigDecimal(0);
        Map<String, BigDecimal> allRemainROI = new HashMap<>();
        Map<String, BigDecimal> remainROISummary = new HashMap<>();
        BigDecimal allRealROI = new BigDecimal(0);
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
                //推广费用
                if (costMap.containsKey(dailyPaybackVo.getRegisterTime())) {
                    roiVo.setPromotionCost(costMap.get(dailyPaybackVo.getRegisterTime()));
                } else {
                    roiVo.setPromotionCost(new BigDecimal(0));
                }
                //sdk分成所得
                roiVo.setSdkShare(
                    dailyPaybackVo.getRemainTotalMoney().setScale(2, BigDecimal.ROUND_HALF_EVEN)
                        .subtract(
                            dailyPaybackVo.getTotalMoneyIos().multiply(new BigDecimal("0.29"))));
                //真实ROI
                if (null != roiVo.getSdkShare()
                    && roiVo.getSdkShare().compareTo(new BigDecimal(0)) > 0 &&
                    roiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0) {
                    roiVo.setRealROI(roiVo.getSdkShare()
                        .divide(roiVo.getPromotionCost(), BigDecimal.ROUND_CEILING)
                        .multiply(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_HALF_EVEN));
                } else {
                    roiVo.setRealROI(new BigDecimal(0));
                }
                //累计ROI
                roiVo.setAllROI(roiVo.getRealROI());
                //利润
                roiVo.setProfit(roiVo.getSdkShare().subtract(roiVo.getPromotionCost())
                    .setScale(5, BigDecimal.ROUND_HALF_EVEN));
                //注册单价
                if (null != roiVo.getPromotionCost()
                    && roiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                    && dailyPaybackVo.getCountUser() > 0) {
                    roiVo.setRegUnitPrice(roiVo.getPromotionCost()
                        .divide(new BigDecimal(dailyPaybackVo.getCountUser()),
                            BigDecimal.ROUND_CEILING)
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                } else {
                    roiVo.setRegUnitPrice(new BigDecimal(0));
                }
                //付费率
                if (null != dailyPaybackVo.getFirstPayuser() && dailyPaybackVo.getFirstPayuser() > 0
                    && dailyPaybackVo.getCountUser() > 0) {
                    roiVo.setCostProbability(new BigDecimal(
                        dailyPaybackVo.getFirstPayuser() / dailyPaybackVo.getCountUser()
                            * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                } else {
                    roiVo.setCostProbability(new BigDecimal(0));
                }
                //付费单价
                if (null != roiVo.getPromotionCost()
                    && roiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                    && dailyPaybackVo.getFirstPayuser() > 0) {
                    roiVo.setCostUnitPrice(roiVo.getPromotionCost().divide(
                            new BigDecimal(String.valueOf(dailyPaybackVo.getFirstPayuser())),
                            BigDecimal.ROUND_CEILING)
                        .setScale(2, BigDecimal.ROUND_HALF_EVEN));
                } else {
                    roiVo.setCostUnitPrice(new BigDecimal(0));
                }
                //剩余DAU
                roiVo.setRemainDAU(regDauData.getOrDefault(dailyPaybackVo.getRegisterTime(), 0));
                //付费DAU
                if (payDauData.containsKey(dailyPaybackVo.getRegisterTime())) {
                    roiVo.setCostDAU(new BigDecimal(
                        String.valueOf(regDauData.get(dailyPaybackVo.getRegisterTime()))));
                } else {
                    roiVo.setCostDAU(new BigDecimal(0));
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
                } else {
                    roiVo.setFirstROI(new BigDecimal(0));
                }
                Map<String, BigDecimal> roiMap = new HashMap<>();
                //剩余ROI
                Map<String, BigDecimal> remainROI = new HashMap<>();
                roiMap.put("day1", dailyPaybackVo.getDay1());
                Class<DailyPaybackVo> dailyPaybackVoClass = DailyPaybackVo.class;
                for (int i = 2; i <= 150; i++) {
                    int j = i - 1;
                    Field declaredField = null;
                    try {
                        declaredField = dailyPaybackVoClass.getDeclaredField("day" + i);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    declaredField.setAccessible(true);
                    BigDecimal value = null;
                    try {
                        value = (BigDecimal) declaredField.get(dailyPaybackVo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    BigDecimal roi = null;
                    if (value != null && null != roiMap.get("day" + j)) {
                        roi = value.add(roiMap.get("day" + j));
                        roiMap.put("day" + j, roi);
                    }
                    if (null != roi && roi.compareTo(new BigDecimal(0)) > 0
                        && roiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0) {
                        remainROI.put("day" + i,
                            roi.divide(roiVo.getPromotionCost(), BigDecimal.ROUND_CEILING)
                                .multiply(new BigDecimal(100))
                                .setScale(1, BigDecimal.ROUND_HALF_EVEN));
                    } else {
                        remainROI.put("day" + i, new BigDecimal(0));
                    }
                }
                roiVo.setRemainROI(remainROI);
                //累计付费金额
                roiVo.setAllCostPrice(dailyPaybackVo.getTotalMoney());
                //新增付费金额
                roiVo.setAddCostPrice(dailyPaybackVo.getFirstMoney());
                //新增注册数
                roiVo.setRegCount(dailyPaybackVo.getCountUser());
                //新增付费人数
                roiVo.setCostCount(dailyPaybackVo.getFirstPayuser());
                resList.add(roiVo);
                allPromotionCost = allPromotionCost.add(roiVo.getPromotionCost());
                allRegCount += roiVo.getRegCount();
                allRemainDAU += roiVo.getRemainDAU();
                allCostDAU = allCostDAU.add(roiVo.getCostDAU());
                allRegUnitPrice = allRegUnitPrice.add(roiVo.getRegUnitPrice());
                allCostCount += roiVo.getCostCount();
                allCostProbability = allCostProbability.add(roiVo.getCostProbability());
                allCostUnitPrice = allCostUnitPrice.add(roiVo.getCostUnitPrice());
                allAddCostPrice = allAddCostPrice.add(roiVo.getAddCostPrice());
                allAllCostPrice = allAllCostPrice.add(roiVo.getAllCostPrice());
                allSdkShare = allSdkShare.add(roiVo.getSdkShare());
                allProfit = allProfit.add(roiVo.getProfit());
//                allEstimatedROI = allEstimatedROI.add(roiVo.getEstimatedROI());
                //合计ROI叠加
                allFirstROI = allFirstROI.add(roiVo.getFirstROI());
                int day = DateUtils.dateToDiff('d', new Date(),
                    DateUtils.str2Date(roiVo.getRoiDate(), DateUtils.date_sdf.get()));

                for (int i = 1; i <= 149; i++) {
                    if (day >= i) {
                        if (!remainROISummary.containsKey("day" + (i + 1))) {
                            remainROISummary.put("day" + (i + 1), new BigDecimal(0));
                        }
                        remainROISummary.put("day" + (i + 1), remainROISummary.get("day" + (i + 1))
                            .add(remainROI.get("day" + (i + 1))));
                    }
                }
                allRealROI = allRealROI.add(roiVo.getRealROI());
            }
            RoiVo allRoiVo = new RoiVo();
            allRoiVo.setRoiDate("合计");
            allRoiVo.setGameName("--");
            allRoiVo.setChannelName("--");
            allRoiVo.setDealName("--");
            allRoiVo.setPromotionCost(allPromotionCost);
            allRoiVo.setRegCount(allRegCount);
            allRoiVo.setRemainDAU(allRemainDAU);
            allRoiVo.setCostDAU(allCostDAU);
            allRoiVo.setCostProbability(allCostProbability);
            allRoiVo.setCostUnitPrice(allCostUnitPrice);
            allRoiVo.setAddCostPrice(allAddCostPrice);
            allRoiVo.setAllCostPrice(allAllCostPrice);
            allRoiVo.setSdkShare(allSdkShare);
            allRoiVo.setProfit(allProfit);
            allRoiVo.setCostCount(allCostCount);
//            allRoiVo.setEstimatedROI(allEstimatedROI);
            if (null != allRoiVo.getSdkShare()
                && allRoiVo.getSdkShare().compareTo(new BigDecimal(0)) > 0
                && allRoiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0) {
                allRoiVo.setAllROI(allRoiVo.getSdkShare()
                    .divide(allRoiVo.getPromotionCost(), BigDecimal.ROUND_CEILING)
                    .multiply(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_HALF_EVEN));
            } else {
                allRoiVo.setAllROI(new BigDecimal(0));
            }
            if (null != allRoiVo.getPromotionCost()
                && allRoiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                && allRoiVo.getRegCount() > 0) {
                allRoiVo.setRegUnitPrice(allRoiVo.getPromotionCost()
                    .divide(new BigDecimal(allRoiVo.getRegCount()),
                        BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
            } else {
                allRoiVo.setRegUnitPrice(new BigDecimal(0));
            }
            if (allRoiVo.getCostCount() > 0 && allRoiVo.getRegCount() > 0) {
                allRoiVo.setCostProbability(new BigDecimal(
                    allRoiVo.getCostCount() / allRoiVo.getRegCount()
                        * 100).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            } else {
                allRoiVo.setCostProbability(new BigDecimal(0));
            }
            if (null != allRoiVo.getPromotionCost()
                && allRoiVo.getPromotionCost().compareTo(new BigDecimal(0)) > 0
                && allRoiVo.getCostCount() > 0) {
                allRoiVo.setCostUnitPrice(allRoiVo.getPromotionCost().divide(
                        new BigDecimal(String.valueOf(allRoiVo.getCostCount())),
                        BigDecimal.ROUND_CEILING)
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN));
            } else {
                allRoiVo.setCostUnitPrice(new BigDecimal(0));
            }
            //合计ROI处理
            if (null != allFirstROI && allFirstROI.compareTo(new BigDecimal(0)) > 0
                && allPromotionCost.compareTo(new BigDecimal(0)) > 0) {
                allRoiVo.setFirstROI(allFirstROI.divide(allPromotionCost, BigDecimal.ROUND_CEILING)
                        .multiply(new BigDecimal(100))
                        .setScale(1, BigDecimal.ROUND_HALF_EVEN));
            }else {
                allRoiVo.setFirstROI(new BigDecimal(0));
            }
            for (int i = 2; i <= 150; i++) {
                if(null != remainROISummary.get("day" + i) && remainROISummary.get("day" + i).compareTo(new BigDecimal(0)) > 0 && allPromotionCost.compareTo(new BigDecimal(0)) > 0) {
                    allRemainROI.put("day" + i, remainROISummary.get("day" + i).divide(allPromotionCost, BigDecimal.ROUND_CEILING)
                        .multiply(new BigDecimal(100))
                        .setScale(1, BigDecimal.ROUND_HALF_EVEN));
                }else {
                    allRemainROI.put("day" + i, new BigDecimal(0));
                }
            }
            allRoiVo.setRemainROI(allRemainROI);
            allRoiVo.setRealROI(allRealROI);
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

    public static void main(String[] args) {
        System.out.println(new BigDecimal(String.format("%.2f",
            new BigDecimal(180.000).divide(
                    new BigDecimal(String.valueOf(new BigDecimal(0.25))))
                .multiply(new BigDecimal(100))
                .setScale(1, BigDecimal.ROUND_HALF_EVEN))));
        ;
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
