package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.count.bo.StatHourBo;
import org.jeecg.modules.count.dto.StatHourDto;
import org.jeecg.modules.count.entity.StatHour;
import org.jeecg.modules.count.mapper.StatHourMapper;
import org.jeecg.modules.count.modal.StatHourModal;
import org.jeecg.modules.count.service.IStatHourService;
import org.jeecg.modules.count.vo.StatHourVo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: cooperator_stat
 * @Author: jeecg-boot
 * @Date: 2023-05-29
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class StatHourServiceImpl extends ServiceImpl<StatHourMapper, StatHour> implements
    IStatHourService {

    @Resource
    private StatHourMapper statHourMapper;
    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Override
    public List<StatHourVo> queryList(StatHourDto statHourDto, String username) {
        QueryWrapper<StatHourDto> where = new QueryWrapper<>();
        QueryWrapper where3 = new QueryWrapper();
        String type = statHourDto.getType();
        //前端校验，如果是日期就日期否则显示广告名称
        if (ObjectUtil.isNotEmpty(statHourDto.getDealId())) {
            where3.in("a.deal_id", statHourDto.getDealId());
            where.in("a.deal_id", statHourDto.getDealId());
        }
        if (ObjectUtil.isNotEmpty(statHourDto.getChannelId())) {
            where.in("a.channel_id", statHourDto.getChannelId());
        }
        if (ObjectUtil.isNotEmpty(statHourDto.getChannelSubAccountId())) {
            where.in("a.channel_sub_account_id", statHourDto.getChannelSubAccountId());
        }
        if (ObjectUtil.isNotEmpty(statHourDto.getChannelTypeId())) {
            where.in("a.channel_type_id", statHourDto.getChannelTypeId());
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != statHourDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(statHourDto.getStartTime(),
                        "yyyy-MM-dd 00:00:00"));
                where.ge("a.time_daily", startDate);
            }
            if (null != statHourDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(statHourDto.getEndTime(),
                        "yyyy-MM-dd 23:59:59"));
                where.le("a.time_daily", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (type) {
            case "deal_id":
                where.groupBy("deal_id", "time_daily", "sub_game_id");
                break;
            case "time_daily":
                where.groupBy("time_daily", "sub_game_id", "deal_id");
                break;
            default:
                where.groupBy("time_daily", "sub_game_id", "deal_id");
        }
        String sql = "";
        for (int i = 1; i <= 90; i++) {
            sql += "IFNULL(SUM(c.day" + i + "),0) AS ltv" + i + ",";
        }
        List<StatHourBo> resultChannelList = statHourMapper.queryList(where, sql);
        //合计的参数
        Map<String, BigDecimal> stat = new HashMap<>();
        stat.put("ltv1", BigDecimal.ZERO);
        stat.put("ltv2", BigDecimal.ZERO);
        stat.put("ltv3", BigDecimal.ZERO);
        stat.put("ltv4", BigDecimal.ZERO);
        stat.put("ltv5", BigDecimal.ZERO);
        stat.put("ltv6", BigDecimal.ZERO);
        stat.put("ltv7", BigDecimal.ZERO);
        stat.put("ltv15", BigDecimal.ZERO);
        stat.put("ltv30", BigDecimal.ZERO);
        stat.put("ltv45", BigDecimal.ZERO);
        stat.put("ltv60", BigDecimal.ZERO);
        stat.put("ltv90", BigDecimal.ZERO);
        stat.put("loyal2", BigDecimal.ZERO);
        stat.put("loyal3", BigDecimal.ZERO);
        stat.put("loyal7", BigDecimal.ZERO);
        stat.put("loyal15", BigDecimal.ZERO);
        stat.put("loyal30", BigDecimal.ZERO);
        stat.put("countActive", BigDecimal.ZERO);
        stat.put("allCountActive", BigDecimal.ZERO);
        stat.put("countUserDev", BigDecimal.ZERO);
        stat.put("regCount", BigDecimal.ZERO);
        stat.put("allValidRegCount", BigDecimal.ZERO);
        stat.put("validReg", BigDecimal.ZERO);
        stat.put("firstPayUser", BigDecimal.ZERO);
        stat.put("firstPayMoney", BigDecimal.ZERO);
        stat.put("countDau", BigDecimal.ZERO);
        stat.put("alivePayUser", BigDecimal.ZERO);
        stat.put("allAlivePayUser", BigDecimal.ZERO);
        stat.put("totalMoney", BigDecimal.ZERO);
        stat.put("firstPayRate", BigDecimal.ZERO);
        stat.put("alivePayRate", BigDecimal.ZERO);
        stat.put("firstArpu", BigDecimal.ZERO);
        stat.put("totalArpu", BigDecimal.ZERO);
        List<StatHourVo> resList = new ArrayList<>();
        if (resultChannelList != null) {
            for (StatHourBo resultChannel : resultChannelList) {
                StatHourVo statHourVo = new StatHourVo();
                Integer gameId = resultChannel.getGameId();
                String gameName = statHourMapper.selectGameNameById(gameId);
                statHourVo.setGameName(gameName);
                Integer dealId = resultChannel.getDealId();
                //设置日期和广告名字
                switch (statHourDto.getType()) {
                    case "time_daily":
                        statHourVo.setDateTime(
                            DateUtil.format(resultChannel.getDateTime(), "yyyy-MM-dd"));
                        break;
                    case "deal_id":
                        statHourVo.setDealName(statHourMapper.selectDealNameById(dealId));
                        break;
                    default:
                        statHourVo.setDateTime(
                            DateUtil.format(resultChannel.getDateTime(), "yyyy-MM-dd"));
                }
                BigDecimal ltv1 = resultChannel.getLtv1();
                //需要反射拿到对象的值了
                Class<StatHourBo> statHourBoClass = StatHourBo.class;
                Map<String, BigDecimal> ltvMap = new HashMap<>();
                ltvMap.put("ltv1", ltv1);
                for (int i = 2; i <= 90; i++) {
                    Field declaredField = null;
                    Field declaredField1 = null;
                    try {
                        declaredField = statHourBoClass.getDeclaredField("ltv" + i);
                        declaredField1 = statHourBoClass.getDeclaredField("ltv" + (i - 1));
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    declaredField.setAccessible(true);
                    declaredField1.setAccessible(true);
                    BigDecimal value = null;
                    BigDecimal value1 = null;
                    try {
                        value = (BigDecimal) declaredField.get(resultChannel);
                        value1 = (BigDecimal) declaredField.get(resultChannel);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    ltvMap.put("ltv" + i, value.add(value1));
                }
                stat.put("ltv1", stat.get("ltv1").add(ltv1));
                stat.put("ltv2", stat.get("ltv2").add(ltvMap.get("ltv2")));
                stat.put("ltv3", stat.get("ltv3").add(ltvMap.get("ltv3")));
                stat.put("ltv4", stat.get("ltv4").add(ltvMap.get("ltv4")));
                stat.put("ltv5", stat.get("ltv5").add(ltvMap.get("ltv5")));
                stat.put("ltv6", stat.get("ltv6").add(ltvMap.get("ltv6")));
                stat.put("ltv7", stat.get("ltv7").add(ltvMap.get("ltv7")));
                stat.put("ltv15", stat.get("ltv15").add(ltvMap.get("ltv15")));
                stat.put("ltv30", stat.get("ltv30").add(ltvMap.get("ltv30")));
                stat.put("ltv45", stat.get("ltv45").add(ltvMap.get("ltv45")));
                stat.put("ltv60", stat.get("ltv60").add(ltvMap.get("ltv60")));
                stat.put("ltv90", stat.get("ltv90").add(ltvMap.get("ltv90")));
                //激活数
                statHourVo.setCountActive(resultChannel.getCountActive());
                stat.put("countActive", BigDecimal.valueOf(resultChannel.getCountActive())
                    .add(stat.getOrDefault("countActive", BigDecimal.ZERO)));
                //所有激活数
                statHourVo.setCountAllActive(resultChannel.getCountAllActive());
                stat.put("allCountActive", BigDecimal.valueOf(resultChannel.getCountAllActive())
                    .add(stat.getOrDefault("allCountActive", BigDecimal.ZERO)));
                //注册数
                statHourVo.setRegCount(resultChannel.getRegCount());
                stat.put("regCount", BigDecimal.valueOf(resultChannel.getRegCount())
                    .add(stat.getOrDefault("regCount", BigDecimal.ZERO)));
                //注册数按设备
                stat.put("countUserDev", BigDecimal.valueOf(resultChannel.getCountUserDev())
                    .add(stat.getOrDefault("countUserDev", BigDecimal.ZERO)));
                //所有有效注册数
                statHourVo.setAllValidReg(resultChannel.getAllValidReg());
                stat.put("allValidReg", BigDecimal.valueOf(resultChannel.getAllValidReg())
                    .add(stat.getOrDefault("allValidReg", BigDecimal.ZERO)));
                //有效注册数
                statHourVo.setValidReg(resultChannel.getValidReg());
                stat.put("validReg", BigDecimal.valueOf(resultChannel.getValidReg())
                    .add(stat.getOrDefault("validReg", BigDecimal.ZERO)));
                //首日付费人数
                statHourVo.setFirstPayUser(resultChannel.getFirstPayUser());
                stat.put("firstPayUser", BigDecimal.valueOf(resultChannel.getFirstPayUser())
                    .add(stat.getOrDefault("firstPayUser", BigDecimal.ZERO)));
                //首日付费金额
                statHourVo.setFirstPayMoney(resultChannel.getFirstPayMoney());
                stat.put("firstPayMoney", resultChannel.getFirstPayMoney()
                    .add(stat.getOrDefault("firstPayMoney", BigDecimal.ZERO)));
                //活跃用户
                statHourVo.setCountDau(resultChannel.getCountDau());
                stat.put("countDau", BigDecimal.valueOf(resultChannel.getCountDau())
                    .add(stat.getOrDefault("countDau", BigDecimal.ZERO)));
                //活跃付费人数
                statHourVo.setAlivePayUser(resultChannel.getAlivePayUser());
                stat.put("alivePayUser", BigDecimal.valueOf(resultChannel.getAlivePayUser())
                    .add(stat.getOrDefault("alivePayUser", BigDecimal.ZERO)));
                //总活跃付费人数
                statHourVo.setAllAlivePayUser(resultChannel.getAllAlivePayUser());
                stat.put("allAlivePayUser", BigDecimal.valueOf(resultChannel.getAllAlivePayUser())
                    .add(stat.getOrDefault("allAlivePayUser", BigDecimal.ZERO)));
                //总金额
                statHourVo.setTotalMoney(resultChannel.getTotalMoney());
                stat.put("totalMoney", resultChannel.getTotalMoney()
                    .add(stat.getOrDefault("totalMoney", BigDecimal.ZERO)));
                //留存2
                stat.put("loyal2",
                    resultChannel.getLoyal2().add(stat.getOrDefault("loyal2", BigDecimal.ZERO)));
                //留存3
                stat.put("loyal3",
                    resultChannel.getLoyal2().add(stat.getOrDefault("loyal3", BigDecimal.ZERO)));
                //留存7
                stat.put("loyal7",
                    resultChannel.getLoyal2().add(stat.getOrDefault("loyal7", BigDecimal.ZERO)));
                //留存15
                stat.put("loyal15",
                    resultChannel.getLoyal2().add(stat.getOrDefault("loyal15", BigDecimal.ZERO)));
                //留存30
                stat.put("loyal30",
                    resultChannel.getLoyal2().add(stat.getOrDefault("loyal30", BigDecimal.ZERO)));
                if (resultChannel.getLoyal2().compareTo(BigDecimal.ZERO) > 0
                    && resultChannel.getCountUserDev() > 0) {
                    BigDecimal loyal2 = resultChannel.getLoyal2().multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            RoundingMode.HALF_UP);
                    statHourVo.setLoyal2(loyal2 + "%");
                } else {
                    statHourVo.setLoyal2("0");
                }
                if (resultChannel.getLoyal3().compareTo(BigDecimal.ZERO) > 0
                    && resultChannel.getCountUserDev() > 0) {
                    BigDecimal loyal3 = resultChannel.getLoyal3().multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            RoundingMode.HALF_UP);
                    statHourVo.setLoyal3(loyal3 + "%");
                } else {
                    statHourVo.setLoyal3("0");
                }
                if (resultChannel.getLoyal7().compareTo(BigDecimal.ZERO) > 0
                    && resultChannel.getCountUserDev() > 0) {
                    BigDecimal loyal7 = resultChannel.getLoyal7().multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            RoundingMode.HALF_UP);
                    statHourVo.setLoyal7(loyal7 + "%");
                } else {
                    statHourVo.setLoyal7("0");
                }
                if (resultChannel.getLoyal15().compareTo(BigDecimal.ZERO) > 0
                    && resultChannel.getCountUserDev() > 0) {
                    BigDecimal loyal15 = resultChannel.getLoyal15()
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            RoundingMode.HALF_UP);
                    statHourVo.setLoyal15(loyal15 + "%");
                } else {
                    statHourVo.setLoyal15("0");
                }
                if (resultChannel.getLoyal30().compareTo(BigDecimal.ZERO) > 0
                    && resultChannel.getCountUserDev() > 0) {
                    BigDecimal loyal30 = resultChannel.getLoyal30()
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            RoundingMode.HALF_UP);
                    statHourVo.setLoyal30(loyal30 + "%");
                } else {
                    statHourVo.setLoyal30("0");
                }
                //计算比率
                if (resultChannel.getFirstPayUser() != null
                    && resultChannel.getCountUserDev() > 0) {
                    BigDecimal firstPayRate = BigDecimal.valueOf(resultChannel.getFirstPayUser())
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            RoundingMode.HALF_UP);
                    statHourVo.setFirstPayRate(firstPayRate + "%");
                } else {
                    statHourVo.setFirstPayRate("0");
                }
                if (resultChannel.getAlivePayUser() != null
                    && resultChannel.getCountUserDev() > 0) {
                    BigDecimal alivePayRate = BigDecimal.valueOf(resultChannel.getAlivePayUser())
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            RoundingMode.HALF_UP);
                    statHourVo.setAlivePayRate(alivePayRate + "%");
                } else {
                    statHourVo.setAlivePayRate("0");
                }
                //计算对应的arpu
                if (resultChannel.getCountUserDev() >0
                    && resultChannel.getFirstPayMoney().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal firstArpu = resultChannel.getFirstPayMoney()
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            RoundingMode.HALF_UP);
                    statHourVo.setFirstArpu(firstArpu);
                } else {
                    statHourVo.setFirstArpu(BigDecimal.ZERO);
                }
                if (resultChannel.getCountUserDev() >0
                    && resultChannel.getTotalMoney().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal totalArpu = resultChannel.getTotalMoney()
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            RoundingMode.HALF_UP);
                    statHourVo.setTotalArpu(totalArpu);
                } else {
                    statHourVo.setTotalArpu(BigDecimal.ZERO);
                }
                //计算对应的LTV
                if (resultChannel.getCountUserDev() > 0 && ltv1 != null) {
                    BigDecimal ltv = ltv1.divide(
                        BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                        BigDecimal.ROUND_CEILING);
                    statHourVo.setLtv1(ltv);
                } else {
                    statHourVo.setLtv1(BigDecimal.ZERO);
                }
                if (resultChannel.getCountUserDev() > 0 && ltvMap.containsKey("ltv3")) {
                    BigDecimal ltv3 = ltvMap.get("ltv3")
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            BigDecimal.ROUND_CEILING);
                    statHourVo.setLtv3(ltv3);
                } else {
                    statHourVo.setLtv3(BigDecimal.ZERO);
                }
                if (resultChannel.getCountUserDev() > 0 && ltvMap.containsKey("ltv7")) {
                    BigDecimal ltv7 = ltvMap.get("ltv7")
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            BigDecimal.ROUND_CEILING);
                    statHourVo.setLtv7(ltv7);
                } else {
                    statHourVo.setLtv7(BigDecimal.ZERO);
                }
                if (resultChannel.getCountUserDev() > 0 && ltvMap.containsKey("ltv30")) {
                    BigDecimal ltv30 = ltvMap.get("ltv30")
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            BigDecimal.ROUND_CEILING);
                    statHourVo.setLtv30(ltv30);
                } else {
                    statHourVo.setLtv30(BigDecimal.ZERO);
                }
                if (resultChannel.getCountUserDev() > 0 && ltvMap.containsKey("ltv60")) {
                    BigDecimal ltv60 = ltvMap.get("ltv60")
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            BigDecimal.ROUND_CEILING);
                    statHourVo.setLtv60(ltv60);
                } else {
                    statHourVo.setLtv60(BigDecimal.ZERO);
                }
                if (resultChannel.getCountUserDev() > 0 && ltvMap.containsKey("ltv90")) {
                    BigDecimal ltv90 = ltvMap.get("ltv90")
                        .divide(BigDecimal.valueOf(resultChannel.getCountUserDev()), 2,
                            BigDecimal.ROUND_CEILING);
                    statHourVo.setLtv90(ltv90);
                } else {
                    statHourVo.setLtv90(BigDecimal.ZERO);
                }
                resList.add(statHourVo);
            }
        }
        //计算合计栏
        StatHourVo allStatHourVo = new StatHourVo();
        //名称
        switch (statHourDto.getType()) {
            case "deal_id":
                allStatHourVo.setDealName("合计");
                break;
            default:
                allStatHourVo.setDateTime("合计");
        }
        //计算费率
        if (stat.containsKey("firstPayUser") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal firstPayRate = stat.get("firstPayUser")
                .multiply(BigDecimal.valueOf(100))
                .divide(stat.get("countUserDev"), 2, RoundingMode.HALF_UP);
            ;
            allStatHourVo.setFirstPayRate(firstPayRate + "%");
        } else {
            allStatHourVo.setFirstPayRate("0");
        }
        if (stat.containsKey("alivePayUser") && stat.containsKey("countDau")
            && stat.get("countDau").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal alivePayUser = stat.get("alivePayUser")
                .multiply(BigDecimal.valueOf(100))
                .divide(stat.get("countDau"), 2, RoundingMode.HALF_UP);
            allStatHourVo.setAlivePayRate(alivePayUser + "%");
        } else {
            allStatHourVo.setAlivePayRate("0");
        }
        //计算arpu
        if (stat.containsKey("countUserDev") && stat.containsKey("firstPayMoney")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal firstArpu = stat.get("firstPayMoney").divide(stat.get("countUserDev"), 2);
            allStatHourVo.setFirstArpu(firstArpu);
        } else {
            allStatHourVo.setFirstArpu(BigDecimal.ZERO);
        }
        if (stat.containsKey("countDau") && stat.containsKey("alivePayMoney")
            && stat.get("countDau").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal totalArpu = stat.get("alivePayMoney").divide(stat.get("countDau"), 2);
            allStatHourVo.setTotalArpu(totalArpu);
        } else {
            allStatHourVo.setTotalArpu(BigDecimal.ZERO);
        }
        //计算合计留存
        if (stat.containsKey("loyal2") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal loyal2 = stat.get("loyal2")
                .multiply(BigDecimal.valueOf(100))
                .divide(stat.get("countUserDev"), 2, RoundingMode.HALF_UP);

            allStatHourVo.setLoyal2(loyal2 + "%");
        } else {
            allStatHourVo.setLoyal2("0");
        }
        if (stat.containsKey("loyal3") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal loyal3 = stat.get("loyal3")
                .multiply(BigDecimal.valueOf(100))
                .divide(stat.get("countUserDev"), 2, RoundingMode.HALF_UP);
            allStatHourVo.setLoyal3(loyal3 + "%");
        } else {
            allStatHourVo.setLoyal3("0");
        }
        if (stat.containsKey("loyal7") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal loyal7 = stat.get("loyal7")
                .multiply(BigDecimal.valueOf(100))
                .divide(stat.get("countUserDev"), 2, RoundingMode.HALF_UP);
            allStatHourVo.setLoyal7(loyal7 + "%");
        } else {
            allStatHourVo.setLoyal7("0");
        }
        if (stat.containsKey("loyal15") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal loyal15 = stat.get("loyal15")
                .multiply(BigDecimal.valueOf(100))
                .divide(stat.get("countUserDev"), 2, RoundingMode.HALF_UP);
            allStatHourVo.setLoyal15(loyal15 + "%");
        } else {
            allStatHourVo.setLoyal15("0");
        }
        if (stat.containsKey("loyal30") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal loyal30 = stat.get("loyal30")
                .multiply(BigDecimal.valueOf(100))
                .divide(stat.get("countUserDev"), 2, RoundingMode.HALF_UP);
            allStatHourVo.setLoyal30(loyal30 + "%");
        } else {
            allStatHourVo.setLoyal30("0");
        }
        //计算LTV
        if (stat.containsKey("ltv1") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal ltv1 = stat.get("ltv1")
                .divide(stat.get("countUserDev"), 2, BigDecimal.ROUND_CEILING);
            allStatHourVo.setLtv1(ltv1);
        } else {
            allStatHourVo.setLtv1(BigDecimal.ZERO);
        }
        if (stat.containsKey("ltv3") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal ltv3 = stat.get("ltv3")
                .divide(stat.get("countUserDev"), 2, BigDecimal.ROUND_CEILING);
            allStatHourVo.setLtv3(ltv3);
        } else {
            allStatHourVo.setLtv3(BigDecimal.ZERO);
        }
        if (stat.containsKey("ltv7") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal ltv7 = stat.get("ltv7")
                .divide(stat.get("countUserDev"), 2, BigDecimal.ROUND_CEILING);
            allStatHourVo.setLtv7(ltv7);
        } else {
            allStatHourVo.setLtv7(BigDecimal.ZERO);
        }
        if (stat.containsKey("ltv30") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal ltv30 = stat.get("ltv30")
                .divide(stat.get("countUserDev"), 2, BigDecimal.ROUND_CEILING);
            allStatHourVo.setLtv30(ltv30);
        } else {
            allStatHourVo.setLtv30(BigDecimal.ZERO);
        }
        if (stat.containsKey("ltv60") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal ltv60 = stat.get("ltv60")
                .divide(stat.get("countUserDev"), 2, BigDecimal.ROUND_CEILING);
            allStatHourVo.setLtv60(ltv60);
        } else {
            allStatHourVo.setLtv60(BigDecimal.ZERO);
        }
        if (stat.containsKey("ltv90") && stat.containsKey("countUserDev")
            && stat.get("countUserDev").compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal ltv90 = stat.get("ltv90")
                .divide(stat.get("countUserDev"), 2, BigDecimal.ROUND_CEILING);
            allStatHourVo.setLtv90(ltv90);
        } else {
            allStatHourVo.setLtv90(BigDecimal.ZERO);
        }
        allStatHourVo.setGameName("全部游戏");
        resList.add(allStatHourVo);
        return resList;
    }

    @Override
    public ModelAndView exportExcel(HttpServletRequest request, StatHourDto statHourDto,
        Class<StatHourModal> statHourModalClass, String title) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String  username = JwtUtil.getUserNameByToken(request);

        List<StatHourVo>resList = this.queryList(statHourDto,username);
        List<StatHourModal>exportList = new ArrayList<>();
        for (StatHourVo statHourVo : resList) {
            StatHourModal statHourModal = new StatHourModal();
            BeanUtils.copyProperties(statHourVo,statHourModal);
            exportList.add(statHourModal);
        }
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, statHourModalClass);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;

    }
}
