package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.bo.StatDealBo;
import org.jeecg.modules.count.dto.StatDealDto;
import org.jeecg.modules.count.entity.StatDeal;
import org.jeecg.modules.count.mapper.StatDealMapper;
import org.jeecg.modules.count.service.IStatDealService;
import org.jeecg.modules.count.vo.StatDealVo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: stat_deal
 * @Author: jeecg-boot
 * @Date:   2023-05-30
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class StatDealServiceImpl extends ServiceImpl<StatDealMapper, StatDeal> implements IStatDealService {

    @Resource
    private StatDealMapper statDealMapper;
    @Value("${jeecg.path.upload}")
    private String upLoadPath;
    @Override
    public List<StatDealVo> queryList(StatDealDto statDealDto, String username) {
        QueryWrapper<StatDealDto> where = new QueryWrapper<>();
        String type = statDealDto.getType();
        //前端校验，如果是日期就日期否则显示广告名称
        if (ObjectUtil.isNotEmpty(statDealDto.getDealId())) {
            where.in("deal_id", statDealDto.getDealId());
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != statDealDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(statDealDto.getStartTime(),
                        "yyyy-MM-dd 00:00:00"));
                where.ge("time_daily", startDate);
            }
            if (null != statDealDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(statDealDto.getEndTime(),
                        "yyyy-MM-dd 23:59:59"));
                where.le("time_daily", endDate);
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
        Map<String, BigDecimal>stat = new HashMap<>();
        stat.put("countActive",BigDecimal.ZERO);
        stat.put("regCount",BigDecimal.ZERO);
        stat.put("validReg",BigDecimal.ZERO);
        stat.put("firstPayUser",BigDecimal.ZERO);
        stat.put("firstPayMoney",BigDecimal.ZERO);
        stat.put("countDau",BigDecimal.ZERO);
        stat.put("alivePayUser",BigDecimal.ZERO);
        stat.put("totalMoney",BigDecimal.ZERO);
        List<StatDealBo>resultList = statDealMapper.queryList(where);
        List<StatDealVo>resList = new ArrayList<>();
        for (StatDealBo statDealBo : resultList) {
            StatDealVo statDealVo = new StatDealVo();
            String gameName = statDealMapper.selectGameNameById(statDealBo.getGameId());
            statDealVo.setGameName(gameName);
            Integer dealId = statDealBo.getDealId();
            //设置日期和广告名字
            switch (statDealDto.getType()) {
                case "time_daily":
                    statDealVo.setDateTime(
                        DateUtil.format(statDealBo.getDateTime(), "yyyy-MM-dd"));
                    break;
                case "deal_id":
                    statDealVo.setDealName(statDealMapper.selectDealNameById(dealId));
                    break;
                default:
                    statDealVo.setDateTime(
                        DateUtil.format(statDealBo.getDateTime(), "yyyy-MM-dd"));
            }
            //激活数
            statDealVo.setCountActive(statDealBo.getCountActive());
            stat.put("countActive",stat.get("countActive").add(BigDecimal.valueOf(statDealBo.getCountActive())));
            // 活跃付费人数
            statDealVo.setAlivePayUser(statDealBo.getAlivePayUser());
            stat.put("alivePayUser",stat.get("alivePayUser").add(BigDecimal.valueOf(statDealBo.getAlivePayUser())));
            //注册数
            statDealVo.setRegCount(statDealBo.getRegCount());
            stat.put("regCount",stat.get("regCount").add(BigDecimal.valueOf(statDealBo.getRegCount())));
            //有效注册数
            statDealVo.setValidReg(statDealBo.getValidReg());
            stat.put("validReg",stat.get("validReg").add(BigDecimal.valueOf(statDealBo.getValidReg())));
            //新增付费数
            statDealVo.setFirstPayUser(statDealBo.getFirstPayUser());
            stat.put("firstPayUser",stat.get("firstPayUser").add(BigDecimal.valueOf(statDealBo.getFirstPayUser())));
            //新增付费金额
            statDealVo.setFirstPayMoney(statDealBo.getFirstPayMoney());
            stat.put("firstPayMoney",stat.get("firstPayMoney").add(statDealBo.getFirstPayMoney()));
            //活跃人数
            statDealVo.setCountDau(statDealBo.getCountDau());
            stat.put("countDau",stat.get("countDau").add(BigDecimal.valueOf(statDealBo.getCountDau())));
            //总金额
            statDealVo.setTotalMoney(statDealBo.getTotalMoney());
            stat.put("totalMoney",stat.get("totalMoney").add(statDealBo.getTotalMoney()));
            resList.add(statDealVo);
        }
        StatDealVo allStatDealVo = new StatDealVo();
        switch (statDealDto.getType()) {
            case "deal_id":
                allStatDealVo.setDealName("合计");
                break;
            default:
                allStatDealVo.setDateTime("合计");
        }
        allStatDealVo.setGameName("全部");
        allStatDealVo.setCountActive(stat.get("countActive").intValue());
        allStatDealVo.setAlivePayUser(stat.get("alivePayUser").intValue());
        allStatDealVo.setRegCount(stat.get("regCount").intValue());
        allStatDealVo.setValidReg(stat.get("validReg").intValue());
        allStatDealVo.setFirstPayUser(stat.get("firstPayUser").intValue());
        allStatDealVo.setFirstPayMoney(stat.get("firstPayMoney"));
        allStatDealVo.setCountDau(stat.get("countDau").intValue());
        allStatDealVo.setTotalMoney(stat.get("totalMoney"));
        resList.add(allStatDealVo);
        return resList;
    }

    @Override
    public ModelAndView exportExcel(HttpServletRequest request, StatDealVo statDealVo,StatDealDto statDealDto, Class name,
        String title) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String  username = JwtUtil.getUserNameByToken(request);
        // Step.2 获取导出数据
        List<StatDealVo> exportList = queryList(statDealDto,username);

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, name);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }


}
