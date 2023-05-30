package org.jeecg.modules.count.service.impl;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.asType;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.or;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.sort;

import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.jndi.ldap.Ber;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.advert.vo.OpChannelModel;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.constant.CloseTypeConstant;
import org.jeecg.common.constant.RuleTypeConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.function.bo.GetNameByIdDto;
import org.jeecg.common.function.vo.GetNameByIdVo;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpGameModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.IpUtils;
import org.jeecg.common.util.TimeUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.ISdkuserApi;
import org.jeecg.modules.count.constant.BlockadeType;
import org.jeecg.modules.count.constant.PayUserType;
import org.jeecg.modules.count.constant.enums.UserOnlineTypeEnum;
import org.jeecg.modules.count.dto.BlockadeDto;
import org.jeecg.modules.count.dto.CtUserDto;
import org.jeecg.modules.count.dto.OnlineUserDto;
import org.jeecg.modules.count.dto.PayUserDto;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.mapper.CtOrderMapper;
import org.jeecg.modules.count.mapper.CtUserMapper;
import org.jeecg.modules.count.modal.CtUserModal;
import org.jeecg.modules.count.modal.PayUserModal;
import org.jeecg.modules.count.service.ICtUserService;
import org.jeecg.modules.count.vo.CtRoleVo;
import org.jeecg.modules.count.vo.CtUserVo;
import org.jeecg.modules.count.vo.OnlineUserVo;
import org.jeecg.modules.count.vo.PayUserAndOrderVo;
import org.jeecg.modules.count.vo.PayUserVo;
import org.jeecg.modules.vo.OpRegisterLoginSwitchVo;
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
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date: 2023-04-25
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class CtUserServiceImpl extends ServiceImpl<CtUserMapper, CtUser> implements ICtUserService {

    @Autowired
    private CtUserMapper ctUserMapper;
    @Autowired
    private CtOrderMapper ctOrderMapper;
    @Autowired
    private ISdkuserApi sdkuserApi;
    @Autowired
    private IAdvertApi advertApi;
    @Autowired
    private IGameApi gameApi;
    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Override
    public List<OnlineUserVo> getOnlineUserList(OnlineUserDto onlineUserDto) {
        if (null == onlineUserDto.getType()) {
            throw new JeecgBootException("归类方式必传");
        }
        String typeName = UserOnlineTypeEnum.getTemplatePathByConfig(onlineUserDto.getType());
        QueryWrapper<OnlineUserDto> q = new QueryWrapper<>();
        if (null != onlineUserDto.getDealId()) {
            q.eq("deal_id", onlineUserDto.getDealId());
        }
        if (null != onlineUserDto.getGameId() && !onlineUserDto.getGameId().isEmpty()) {
            q.in("game_id", onlineUserDto.getGameId());
        }
        if (null != onlineUserDto.getSubGameId() && !onlineUserDto.getSubGameId().isEmpty()) {
            q.in("sub_game_id", onlineUserDto.getSubGameId());
        }
        if (null != onlineUserDto.getPkgId() && !onlineUserDto.getPkgId().isEmpty()) {
            q.in("pkg_id", onlineUserDto.getPkgId());
        }
        if (null != onlineUserDto.getChannelTypeId()) {
            q.eq("channel_type_id", onlineUserDto.getChannelTypeId());
        }
        if (null != onlineUserDto.getChannelId()) {
            q.eq("channel_id", onlineUserDto.getChannelId());
        }
        if (null != onlineUserDto.getChannelSubAccountId()) {
            q.eq("channel_sub_account_id", onlineUserDto.getChannelSubAccountId());
        }
        long time = (DateUtils.getMillis() / 1000 - 600) * 1000;
        q.ge("update_time", DateUtils.format2Time(time));
        List<OnlineUserVo> list = ctUserMapper.getOnlineUserList(q, typeName);
        if (null != list && !list.isEmpty()) {
            GetNameByIdDto getNameByIdDto = new GetNameByIdDto();
            getNameByIdDto.setType(onlineUserDto.getType());
            for (OnlineUserVo onlineUserVo : list) {
                getNameByIdDto.setId(onlineUserVo.getId());
                onlineUserVo.setName(getNameById(getNameByIdDto).getName());
            }
        }
        return list;
    }

    /**
     * @param getNameByIdDto
     * @return org.jeecg.common.function.vo.GetNameByIdVo
     * @Author lili
     * @Description 通过id得到名称（游戏，子游戏，渠道游戏包，广告）
     * @Date 16:17 2023/5/9
     **/
    private GetNameByIdVo getNameById(GetNameByIdDto getNameByIdDto) {
        if (null == getNameByIdDto.getId() || null == getNameByIdDto.getType()) {
            throw new JeecgBootException("参数不全");
        }
        GetNameByIdVo getNameByIdVo = new GetNameByIdVo();
        String name = "";
        if (Objects.equals(getNameByIdDto.getType(), RuleTypeConstant.GAME)) {
            OpGameModel opGame = gameApi.getOpGame(getNameByIdDto.getId());
            name = opGame.getGameName();
        }else if (Objects.equals(getNameByIdDto.getType(), RuleTypeConstant.SUB_GAME)) {
            OpSubGameModel opSubGame = gameApi.getOpSubGame(getNameByIdDto.getId());
            name = opSubGame.getGameName();
        }else if (Objects.equals(getNameByIdDto.getType(), RuleTypeConstant.DEAL)) {
            OpDealModel opDealModel = advertApi.getOpDeal(getNameByIdDto.getId());
            name = opDealModel.getDealName();
        }else {
            OpChannelModel opChannelModel = advertApi.getOpChannel(getNameByIdDto.getId());
            name = opChannelModel.getChannelName();
        }
        getNameByIdVo.setName(name);
        return getNameByIdVo;
    }

    @Override
    public IPage<CtUserVo> getPageList(Page<T> page, CtUserDto ctUserDto) {
        QueryWrapper<CtUserVo> q = new QueryWrapper<>();
        if (null != ctUserDto.getUserName() && ctUserDto.getUserName().trim().length() > 0) {
            q.like("b.user_name", ctUserDto.getUserName());
        }
        if (null != ctUserDto.getUserId()) {
            q.like("a.user_id", ctUserDto.getUserId());
        }
        if (null != ctUserDto.getDealId()) {
            q.eq("a.deal_id", ctUserDto.getDealId());
        }
        if (null != ctUserDto.getGameId() && !ctUserDto.getGameId().isEmpty()) {
            q.in("a.game_id", ctUserDto.getGameId());
        }
        if (null != ctUserDto.getSubGameId() && !ctUserDto.getSubGameId().isEmpty()) {
            q.in("a.sub_game_id", ctUserDto.getSubGameId());
        }
        if (null != ctUserDto.getPkgId() && !ctUserDto.getPkgId().isEmpty()) {
            q.in("a.pkg_id", ctUserDto.getPkgId());
        }
        if (null != ctUserDto.getChannelTypeId()) {
            q.eq("a.channel_type_id", ctUserDto.getChannelTypeId());
        }
        if (null != ctUserDto.getChannelId()) {
            q.eq("a.channel_id", ctUserDto.getChannelId());
        }
        if (null != ctUserDto.getChannelSubAccountId()) {
            q.eq("a.channel_sub_account_id", ctUserDto.getChannelSubAccountId());
        }
        try {
            if (null != ctUserDto.getRegStartTime()) {
                q.ge("a.register_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctUserDto.getRegStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != ctUserDto.getRegEndTime()) {
                q.le("a.register_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctUserDto.getRegEndTime(), "yyyy-MM-dd 23:59:59")));
            }
            if (null != ctUserDto.getLogStartTime()) {
                q.ge("a.login_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctUserDto.getLogStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != ctUserDto.getLogEndTime()) {
                q.le("a.login_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctUserDto.getLogEndTime(), "yyyy-MM-dd 23:59:59")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        IPage<CtUserVo> list = baseMapper.queryPageList(page, q);
        if (list.getTotal() > 0) {
            List<Integer> dealIdList = list.getRecords().stream().map(CtUserVo::getDealId)
                .collect(Collectors.toList());
            List<OpRegisterLoginSwitchVo> registerLoginSwitchVoList = sdkuserApi.getListByDealIdList(
                dealIdList);
            Map<Integer, OpRegisterLoginSwitchVo> map = new LinkedHashMap<>();
            if (null != registerLoginSwitchVoList && !registerLoginSwitchVoList.isEmpty()) {
                for (OpRegisterLoginSwitchVo opRegisterLoginSwitchVo : registerLoginSwitchVoList) {
                    map.put(opRegisterLoginSwitchVo.getRuleId(), opRegisterLoginSwitchVo);
                }
            }
            for (CtUserVo ctUserVo : list.getRecords()) {
                if (null != ctUserVo.getOnlineTime()) {
                    ctUserVo.setOnlineTimeStr(TimeUtil.getTimeStr(ctUserVo.getOnlineTime()));
                }
                ctUserVo.setUserState(0);
                ctUserVo.setIpState(0);
                ctUserVo.setIpPlace("未知");
                if (map.containsKey(ctUserVo.getDealId())) {
                    if (null != map.get(ctUserVo.getDealId()).getLoginLimitUserId()) {
                        String[] loginLimitUserArr = map.get(ctUserVo.getDealId())
                            .getLoginLimitUserId()
                            .split("\\r?\\n");
                        if (Arrays.asList(loginLimitUserArr)
                            .contains(String.valueOf(ctUserVo.getUserId()))) {
                            ctUserVo.setUserState(1);
                        }
                    }
                    if (null != map.get(ctUserVo.getDealId()).getLoginLimitIp()) {
                        String[] loginLimitIpArr = map.get(ctUserVo.getDealId()).getLoginLimitIp()
                            .split("\\r?\\n");
                        if (Arrays.asList(loginLimitIpArr).contains(ctUserVo.getClientIp())) {
                            ctUserVo.setIpState(1);
                        }
                    }
                }
                try {
                    if (oConvertUtils.isNotEmpty(ctUserVo.getClientIp())) {
                        if (oConvertUtils.isNotEmpty(
                            IpUtils.getPlaceInfo(ctUserVo.getClientIp()))) {
                            ctUserVo.setIpPlace(IpUtils.getPlaceInfo(ctUserVo.getClientIp()));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    @Override
    public List<CtUserModal> getAllList(CtUserDto ctUserDto) {
        QueryWrapper<CtUserVo> q = new QueryWrapper<>();
        if (null != ctUserDto.getUserName() && ctUserDto.getUserName().trim().length() > 0) {
            q.like("b.user_name", ctUserDto.getUserName());
        }
        if (null != ctUserDto.getUserId()) {
            q.like("a.user_id", ctUserDto.getUserId());
        }
        if (null != ctUserDto.getDealId()) {
            q.eq("a.deal_id", ctUserDto.getDealId());
        }
        if (null != ctUserDto.getGameId() && !ctUserDto.getGameId().isEmpty()) {
            q.in("a.game_id", ctUserDto.getGameId());
        }
        if (null != ctUserDto.getSubGameId() && !ctUserDto.getSubGameId().isEmpty()) {
            q.in("a.sub_game_id", ctUserDto.getSubGameId());
        }
        if (null != ctUserDto.getPkgId() && !ctUserDto.getPkgId().isEmpty()) {
            q.in("a.pkg_id", ctUserDto.getPkgId());
        }
        if (null != ctUserDto.getChannelTypeId()) {
            q.eq("a.channel_type_id", ctUserDto.getChannelTypeId());
        }
        if (null != ctUserDto.getChannelId()) {
            q.eq("a.channel_id", ctUserDto.getChannelId());
        }
        if (null != ctUserDto.getChannelSubAccountId()) {
            q.eq("a.channel_sub_account_id", ctUserDto.getChannelSubAccountId());
        }
        try {
            if (null != ctUserDto.getRegStartTime()) {
                q.ge("a.register_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctUserDto.getRegStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != ctUserDto.getRegEndTime()) {
                q.le("a.register_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctUserDto.getRegEndTime(), "yyyy-MM-dd 23:59:59")));
            }
            if (null != ctUserDto.getLogStartTime()) {
                q.ge("a.login_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctUserDto.getLogStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != ctUserDto.getLogEndTime()) {
                q.le("a.login_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctUserDto.getLogEndTime(), "yyyy-MM-dd 23:59:59")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<CtUserVo> list = baseMapper.queryAllList(q);
        List<CtUserModal> resList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            for (CtUserVo ctUserVo : list) {
                if (null != ctUserVo.getOnlineTime()) {
                    ctUserVo.setOnlineTimeStr(TimeUtil.getTimeStr(ctUserVo.getOnlineTime()));
                }
                ctUserVo.setIpPlace("未知");
                try {
                    if (oConvertUtils.isNotEmpty(ctUserVo.getClientIp())) {
                        if (oConvertUtils.isNotEmpty(
                            IpUtils.getPlaceInfo(ctUserVo.getClientIp()))) {
                            ctUserVo.setIpPlace(IpUtils.getPlaceInfo(ctUserVo.getClientIp()));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CtUserModal ctUserModal = new CtUserModal();
                BeanUtils.copyProperties(ctUserVo, ctUserModal);
                resList.add(ctUserModal);
            }
        }
        return resList;
    }

    @Override
    public ModelAndView exportXls(CtUserDto object,
        Class<CtUserModal> clazz, String title) {
        List<CtUserModal> exportList = getAllList(object);
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
    public IPage<PayUserVo> getPagePayUserList(Page<T> page, PayUserDto payUserDto) {
        QueryWrapper<CtUserVo> q = new QueryWrapper<>();
        QueryWrapper<CtUserVo> q2 = new QueryWrapper<>();
        if (null != payUserDto.getUserName() && payUserDto.getUserName().trim().length() > 0) {
            q.like("b.user_name", payUserDto.getUserName());
        }
        if (null != payUserDto.getUserId()) {
            q.like("a.user_id", payUserDto.getUserId());
        }
        if (null != payUserDto.getDealId()) {
            q.eq("a.deal_id", payUserDto.getDealId());
        }
        if (null != payUserDto.getGameId() && !payUserDto.getGameId().isEmpty()) {
            q.in("a.game_id", payUserDto.getGameId());
        }
        if (null != payUserDto.getSubGameId() && !payUserDto.getSubGameId().isEmpty()) {
            q.in("a.sub_game_id", payUserDto.getSubGameId());
        }
        if (null != payUserDto.getPkgId() && !payUserDto.getPkgId().isEmpty()) {
            q.in("a.pkg_id", payUserDto.getPkgId());
        }
        if (null != payUserDto.getChannelTypeId() && !payUserDto.getChannelTypeId().isEmpty()) {
            q.in("a.channel_type_id", payUserDto.getChannelTypeId());
        }
        if (null != payUserDto.getChannelId() && !payUserDto.getChannelId().isEmpty()) {
            q.in("a.channel_id", payUserDto.getChannelId());
        }
        if (null != payUserDto.getChannelSubAccountId() && !payUserDto.getChannelSubAccountId()
            .isEmpty()) {
            q.in("a.channel_sub_account_id", payUserDto.getChannelSubAccountId());
        }
        try {
            if (null != payUserDto.getRegStartTime()) {
                q.ge("a.create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getRegStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != payUserDto.getRegEndTime()) {
                q.le("a.create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getRegEndTime(), "yyyy-MM-dd 23:59:59")));
            }
            if (null != payUserDto.getLogStartTime()) {
                q.ge("a.login_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getLogStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != payUserDto.getLogEndTime()) {
                q.le("a.login_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getLogEndTime(), "yyyy-MM-dd 23:59:59")));
            }
            if (null != payUserDto.getPayStartTime()) {
                q2.ge("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getPayStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != payUserDto.getPayEndTime()) {
                q2.le("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getPayEndTime(), "yyyy-MM-dd 23:59:59")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        q.ne("a.pay_time", "0000-00-00 00:00:00");
        if (Objects.equals(payUserDto.getType(), PayUserType.ADD_PAY_USER)) {
            q.apply(
                "DATE_FORMAT(a.first_pay_time,'%m-%d-%Y') = DATE_FORMAT(a.register_time,'%m-%d-%Y')");
        }
        q.orderByDesc("a.id");
        IPage<PayUserVo> list = baseMapper.queryPagePayUserList(page, q);
        String minTime = DateUtils.now();
        if (list.getTotal() > 0) {
            for (PayUserVo payUserVo : list.getRecords()) {
                if (null != payUserVo.getOnlineTime()) {
                    payUserVo.setOnlineTimeStr(TimeUtil.getTimeStr(payUserVo.getOnlineTime()));
                }
                if (null != payUserVo.getRegisterTime() && (
                    DateUtils.getTimestamp(payUserVo.getRegisterTime()).getTime()
                        < DateUtils.str3Timestamp(minTime).getTime())) {
//                    SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    minTime = dateSdf.format(payUserVo.getRegisterTime());
                    minTime = DateUtils.date2Str(payUserVo.getRegisterTime(),
                        DateUtils.datetimeFormat.get());
                }
            }
            //拼接支付数据
            if (null == payUserDto.getPayStartTime()
                ||
                DateUtils.getTimestamp(Objects.requireNonNull(payUserDto.getPayStartTime()))
                    .getTime()
                    < DateUtils.str3Timestamp(minTime).getTime()) {
                q2.ge("create_time", minTime);
            }
            List<PayUserAndOrderVo> orderVoList = ctOrderMapper.selectOrderByDealIdAndUserId(q2);
            if (null != orderVoList && !orderVoList.isEmpty()) {
                Map<String, BigDecimal> moneyData = new LinkedHashMap<>();
                for (PayUserAndOrderVo payUserAndOrderVo : orderVoList) {
                    moneyData.put(
                        String.valueOf(payUserAndOrderVo.getDealId())
                            + payUserAndOrderVo.getUserId(),
                        payUserAndOrderVo.getTotalMoney());
                }
                for (PayUserVo payUserVo : list.getRecords()) {
                    if (moneyData.containsKey(
                        String.valueOf(payUserVo.getDealId()) + payUserVo.getUserId())) {
                        payUserVo.setTotalMoney(moneyData.get(
                            String.valueOf(payUserVo.getDealId()) + payUserVo.getUserId()));
                    }
                }
            }
        }

        return list;
    }

    @Override
    public List<PayUserModal> getAllPayUserList(PayUserDto payUserDto) {
        QueryWrapper<CtUserVo> q = new QueryWrapper<>();
        QueryWrapper<CtUserVo> q2 = new QueryWrapper<>();
        if (null != payUserDto.getUserName() && payUserDto.getUserName().trim().length() > 0) {
            q.like("b.user_name", payUserDto.getUserName());
        }
        if (null != payUserDto.getUserId()) {
            q.like("a.user_id", payUserDto.getUserId());
        }
        if (null != payUserDto.getDealId()) {
            q.eq("a.deal_id", payUserDto.getDealId());
        }
        if (null != payUserDto.getGameId() && !payUserDto.getGameId().isEmpty()) {
            q.in("a.game_id", payUserDto.getGameId());
        }
        if (null != payUserDto.getSubGameId() && !payUserDto.getSubGameId().isEmpty()) {
            q.in("a.sub_game_id", payUserDto.getSubGameId());
        }
        if (null != payUserDto.getPkgId() && !payUserDto.getPkgId().isEmpty()) {
            q.in("a.pkg_id", payUserDto.getPkgId());
        }
        if (null != payUserDto.getChannelTypeId() && !payUserDto.getChannelTypeId().isEmpty()) {
            q.in("a.channel_type_id", payUserDto.getChannelTypeId());
        }
        if (null != payUserDto.getChannelId() && !payUserDto.getChannelId().isEmpty()) {
            q.in("a.channel_id", payUserDto.getChannelId());
        }
        if (null != payUserDto.getChannelSubAccountId() && !payUserDto.getChannelSubAccountId()
            .isEmpty()) {
            q.in("a.channel_sub_account_id", payUserDto.getChannelSubAccountId());
        }
        try {
            if (null != payUserDto.getRegStartTime()) {
                q.ge("a.create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getRegStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != payUserDto.getRegEndTime()) {
                q.le("a.create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getRegEndTime(), "yyyy-MM-dd 23:59:59")));
            }
            if (null != payUserDto.getLogStartTime()) {
                q.ge("a.login_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getLogStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != payUserDto.getLogEndTime()) {
                q.le("a.login_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getLogEndTime(), "yyyy-MM-dd 23:59:59")));
            }
            if (null != payUserDto.getPayStartTime()) {
                q2.ge("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getPayStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != payUserDto.getPayEndTime()) {
                q2.le("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(payUserDto.getPayEndTime(), "yyyy-MM-dd 23:59:59")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        q.ne("a.pay_time", "0000-00-00 00:00:00");
        if (Objects.equals(payUserDto.getType(), PayUserType.ADD_PAY_USER)) {
            q.apply(
                "DATE_FORMAT(a.first_pay_time,'%m-%d-%Y') = DATE_FORMAT(a.register_time,'%m-%d-%Y')");
        }
        q.orderByDesc("a.id");
        List<PayUserVo> list = baseMapper.getAllPayUserList(q);
        String minTime = DateUtils.now();
        if (null == list || list.isEmpty()) {
            return null;
        }
        for (PayUserVo payUserVo : list) {
            if (null != payUserVo.getOnlineTime()) {
                payUserVo.setOnlineTimeStr(TimeUtil.getTimeStr(payUserVo.getOnlineTime()));
            }
            if (null != payUserVo.getRegisterTime() && (
                DateUtils.getTimestamp(payUserVo.getRegisterTime()).getTime()
                    < DateUtils.str3Timestamp(minTime).getTime())) {
                minTime = DateUtils.date2Str(payUserVo.getRegisterTime(),
                    DateUtils.datetimeFormat.get());
            }
        }
        //拼接支付数据
        if (null == payUserDto.getPayStartTime()
            ||
            DateUtils.getTimestamp(Objects.requireNonNull(payUserDto.getPayStartTime()))
                .getTime()
                < DateUtils.str3Timestamp(minTime).getTime()) {
            q2.ge("create_time", minTime);
        }
        List<PayUserModal> resList = new ArrayList<>();
        List<PayUserAndOrderVo> orderVoList = ctOrderMapper.selectOrderByDealIdAndUserId(q2);
        if (null != orderVoList && !orderVoList.isEmpty()) {
            Map<String, BigDecimal> moneyData = new LinkedHashMap<>();
            for (PayUserAndOrderVo payUserAndOrderVo : orderVoList) {
                moneyData.put(
                    String.valueOf(payUserAndOrderVo.getDealId())
                        + payUserAndOrderVo.getUserId(),
                    payUserAndOrderVo.getTotalMoney());
            }
            for (PayUserVo payUserVo : list) {
                if (moneyData.containsKey(
                    String.valueOf(payUserVo.getDealId()) + payUserVo.getUserId())) {
                    payUserVo.setTotalMoney(moneyData.get(
                        String.valueOf(payUserVo.getDealId()) + payUserVo.getUserId()));
                }
                PayUserModal payUserModal = new PayUserModal();
                BeanUtils.copyProperties(payUserVo, payUserModal);
                resList.add(payUserModal);
            }
        }
        return resList;
    }

    @Override
    public ModelAndView payUseExportXls(PayUserDto object, Class<PayUserModal> clazz,
        String title) {
        List<PayUserModal> exportList = getAllPayUserList(object);
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
    public void blockade(BlockadeDto ctUserDto) {
        if (ctUserDto.getType() == null || ctUserDto.getDealId() == null || (
            ctUserDto.getUserId() == null && oConvertUtils.isEmpty(ctUserDto.getClientIp()))) {
            throw new JeecgBootException("缺少参数");
        }
        List<Integer> dealIdList = new ArrayList<>();
        dealIdList.add(ctUserDto.getDealId());
        List<OpRegisterLoginSwitchVo> list = sdkuserApi.getListByDealIdList(dealIdList);
        //封号、封IP
        OpRegisterLoginSwitchVo opRegisterLoginSwitchVo = new OpRegisterLoginSwitchVo();
        if (null == list || list.isEmpty()) {
            if (Objects.equals(ctUserDto.getType(), BlockadeType.NOT_BLOCKADE_USER)
                || Objects.equals(
                ctUserDto.getType(), BlockadeType.NOT_BLOCKADE_IP)) {
                throw new JeecgBootException("不存在记录");
            }
            opRegisterLoginSwitchVo.setRuleType(RuleTypeConstant.DEAL);
            opRegisterLoginSwitchVo.setRuleId(ctUserDto.getDealId());
            opRegisterLoginSwitchVo.setLoginCloseType(CloseTypeConstant.CONDITION_CLOSE);
            if (Objects.equals(ctUserDto.getType(), BlockadeType.BLOCKADE_USER)) {
                opRegisterLoginSwitchVo.setLoginLimitUserId(String.valueOf(ctUserDto.getUserId()));
            } else if (Objects.equals(ctUserDto.getType(), BlockadeType.BLOCKADE_IP)) {
                opRegisterLoginSwitchVo.setLoginLimitIp(ctUserDto.getClientIp());
            }
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            opRegisterLoginSwitchVo.setCreateBy(sysUser.getUsername());
            opRegisterLoginSwitchVo.setCreateTime(new Date());
        } else {
            BeanUtils.copyProperties(list.get(0), opRegisterLoginSwitchVo);
            String[] loginLimitUserArr = opRegisterLoginSwitchVo.getLoginLimitUserId()
                .split("\\r?\\n");
            String[] loginLimitIpArr = opRegisterLoginSwitchVo.getLoginLimitIp()
                .split("\\r?\\n");
            List<String> arrList;
            if (Objects.equals(ctUserDto.getType(), BlockadeType.BLOCKADE_USER)) {
                if (!Arrays.asList(loginLimitUserArr)
                    .contains(String.valueOf(ctUserDto.getUserId()))) {
                    opRegisterLoginSwitchVo.setLoginLimitUserId(
                        opRegisterLoginSwitchVo.getLoginLimitUserId() + "\n"
                            + ctUserDto.getUserId());
                }
            } else if (Objects.equals(ctUserDto.getType(), BlockadeType.BLOCKADE_IP)) {
                if (!Arrays.asList(loginLimitIpArr)
                    .contains(String.valueOf(ctUserDto.getClientIp()))) {
                    opRegisterLoginSwitchVo.setLoginLimitIp(
                        opRegisterLoginSwitchVo.getLoginLimitIp() + "\n"
                            + ctUserDto.getClientIp());
                }
            } else if (Objects.equals(ctUserDto.getType(), BlockadeType.NOT_BLOCKADE_USER)) {
                arrList = Arrays.asList(loginLimitUserArr);
                List<String> userArrList = new ArrayList<String>(arrList);
                userArrList.remove(String.valueOf(ctUserDto.getUserId()));
                opRegisterLoginSwitchVo.setLoginLimitUserId(String.join("\n", userArrList));
            } else {
                arrList = Arrays.asList(loginLimitIpArr);
                List<String> ipArrList = new ArrayList<String>(arrList);
                ipArrList.remove(ctUserDto.getClientIp());
                opRegisterLoginSwitchVo.setLoginLimitIp(String.join("\n", ipArrList));
            }
        }
        if (!sdkuserApi.saveOpRegisterLoginSwitch(opRegisterLoginSwitchVo)) {
            throw new JeecgBootException("操作失败");
        }
    }

    @Override
    public CtUser searchLoginUser(ParseLoginDto parseLoginDto) {

        LambdaQueryWrapper<CtUser> ctUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctUserLambdaQueryWrapper.eq(CtUser::getDealId, parseLoginDto.getDealId());
        ctUserLambdaQueryWrapper.eq(CtUser::getUserId, parseLoginDto.getUserId());
        CtUser ctUser = ctUserMapper.selectOne(ctUserLambdaQueryWrapper);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String date = simpleDateFormat.format(parseLoginDto.getTime());
        String allDate = dateFormat.format(parseLoginDto.getTime());
        Date timeReg = DateUtils.str2Date(allDate, dateFormat);

        Date day = DateUtils.str2Date(date, simpleDateFormat);

        CtUser resultCtUser = new CtUser();

        if (ObjectUtils.isEmpty(ctUser)) {
            CtUser insertCtUser = new CtUser();
            insertCtUser.setGameId(parseLoginDto.getGameId());
            insertCtUser.setSubGameId(parseLoginDto.getSubGameId());
            insertCtUser.setChannelId(parseLoginDto.getChannelId());
            insertCtUser.setChannelTypeId(parseLoginDto.getChannelTypeId());
            insertCtUser.setChannelSubAccountId(parseLoginDto.getChannelSubAccountId());
            insertCtUser.setDealId(parseLoginDto.getDealId());
            insertCtUser.setUpdateTime(day);
            insertCtUser.setCreateTime(day);
            insertCtUser.setPkgId(parseLoginDto.getPkgId());
            insertCtUser.setUserId(parseLoginDto.getUserId());

            BeanUtils.copyProperties(insertCtUser, resultCtUser);

            insertCtUser.setRegisterTime(timeReg);
            insertCtUser.setLoginTime(day);
            insertCtUser.setUniqueId(parseLoginDto.getUniqueId());
            insertCtUser.setClientIp(parseLoginDto.getClientIp());
            insertCtUser.setLoginMask(1L);
            ctUserMapper.insert(insertCtUser);

        } else {
            BeanUtils.copyProperties(ctUser, resultCtUser);
        }

        return resultCtUser;
    }

    @Override
    public void updateMask(ParseLoginDto parseLoginDto, CtUser ctUser) {

        Long mask = ctUser.getLoginMask();
        Long payMask = ctUser.getPayUserLoginMask();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(parseLoginDto.getTime());
        Date timeDay = DateUtils.str2Date(date, simpleDateFormat);

        int days = DateUtils.dateToDiff('d', timeDay, ctUser.getRegisterTime());

        if (days >= 0 && days < 64) {
            if (0 == (mask & (1 << days))) {
                mask = (mask | (1 << days));
            }
        }

        if (ObjectUtils.isNotEmpty(ctUser.getFirstPayTime())) {

            int firstPayDays = DateUtils.dateToDiff('d', timeDay, ctUser.getFirstPayTime());

            Integer payLoyalLevel = 1;

            switch (firstPayDays) {
                case 0:
                    payLoyalLevel = 0;
                    break;
                case 1:
                    payLoyalLevel = 1;
                    break;
                case 2:
                    payLoyalLevel = 2;
                    break;
                case 6:
                    payLoyalLevel = 3;
                    break;
                case 14:
                    payLoyalLevel = 4;
                    break;
                case 29:
                    payLoyalLevel = 5;
                    break;
                case 44:
                    payLoyalLevel = 6;
                    break;
                case 59:
                    payLoyalLevel = 7;
                    break;
                case 89:
                    payLoyalLevel = 8;
                    break;
                default:
                    payLoyalLevel = 0;
                    break;
            }

            if (firstPayDays > 0 && firstPayDays <= 90) {
                payMask = (ctUser.getPayUserLoginMask() | (1 << payLoyalLevel));

            } else {
                payMask = 0L;
            }
        }
        updateExistUser(parseLoginDto, mask, payMask);
    }

    public void updateExistUser(ParseLoginDto parseLoginDto, Long mask, Long payMask) {
        LambdaQueryWrapper<CtUser> ctUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctUserLambdaQueryWrapper.eq(CtUser::getDealId, parseLoginDto.getDealId());
        ctUserLambdaQueryWrapper.eq(CtUser::getUserId, parseLoginDto.getUserId());

        CtUser ctUser = ctUserMapper.selectOne(ctUserLambdaQueryWrapper);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(parseLoginDto.getTime());
        Date time = DateUtils.str2Date(date, simpleDateFormat);

        ctUser.setClientIp(parseLoginDto.getClientIp());
        ctUser.setLoginTime(time);
        ctUser.setLoginMask(mask);
        ctUser.setPayUserLoginMask(payMask);
        ctUser.setUpdateTime(time);

        ctUserMapper.updateById(ctUser);
    }

    @Override
    public CtUser searchAliveUser(ParseAliveDto parseAliveDto) {

        LambdaQueryWrapper<CtUser> ctUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctUserLambdaQueryWrapper.eq(CtUser::getDealId, parseAliveDto.getDealId());
        ctUserLambdaQueryWrapper.eq(CtUser::getUserId, parseAliveDto.getUserId());
        CtUser ctUser = ctUserMapper.selectOne(ctUserLambdaQueryWrapper);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(parseAliveDto.getTime());
        Date day = DateUtils.str2Date(date, simpleDateFormat);

        CtUser resultCtUser = new CtUser();

        if (ObjectUtils.isEmpty(ctUser)) {
            CtUser insertCtUser = new CtUser();
            insertCtUser.setGameId(parseAliveDto.getGameId());
            insertCtUser.setSubGameId(parseAliveDto.getSubGameId());
            insertCtUser.setChannelId(parseAliveDto.getChannelId());
            insertCtUser.setChannelTypeId(parseAliveDto.getChannelTypeId());
            insertCtUser.setChannelSubAccountId(parseAliveDto.getChannelSubAccountId());
            insertCtUser.setDealId(parseAliveDto.getDealId());
            insertCtUser.setUpdateTime(day);
            insertCtUser.setCreateTime(day);
            insertCtUser.setPkgId(parseAliveDto.getPkgId());
            insertCtUser.setUserId(parseAliveDto.getUserId());

            BeanUtils.copyProperties(insertCtUser, resultCtUser);
            ctUserMapper.insert(insertCtUser);

        } else {
            BeanUtils.copyProperties(ctUser, resultCtUser);

            int second = DateUtils.dateToDiff('s', day, ctUser.getCreateTime());

            if (ObjectUtils.isEmpty(ctUser.getPlayTime()) && second > 595) {
                // TODO count_role
                ctUser.setPlayTime(day);
            }
            if (ctUser.getServerInit() != 0) {
                ctUser.setServerInit(parseAliveDto.getServerId());
            }
            if (ObjectUtils.isNotEmpty(ctUser.getAliveTime())) {
                if (DateUtils.dateToDiff('s', day, ctUser.getAliveTime()) > 295) {

                    ctUser.setOnlineTime(ctUser.getOnlineTime() + 300);

                    if (ObjectUtils.isEmpty(ctUser.getAliveTime()) || DateUtil.isSameDay(day,
                        ctUser.getAliveTime())) {
                        ctUser.setDayOnlineTime(ctUser.getDayOnlineTime() + 300);
                    } else {
                        ctUser.setDayOnlineTime(300);
                    }
                }
            }
            ctUser.setUpdateTime(day);
            ctUser.setServerLast(parseAliveDto.getServerId());
            ctUser.setAliveTime(day);

            ctUserMapper.updateById(ctUser);
        }

        return resultCtUser;
    }

    @Override
    public CtUser parsePayUser(ParsePayDto parsePayDto) {
        LambdaQueryWrapper<CtUser> ctUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctUserLambdaQueryWrapper.eq(CtUser::getDealId, parsePayDto.getDealId());
        ctUserLambdaQueryWrapper.eq(CtUser::getUserId, parsePayDto.getUserId());
        CtUser ctUser = getOne(ctUserLambdaQueryWrapper);
        CtUser result = new CtUser();
        Date logTime = DateUtils.getDate(parsePayDto.getTime());
        if (ctUser == null) {
            ctUser = new CtUser();
            ctUser.setGameId(parsePayDto.getGameId());
            ctUser.setSubGameId(parsePayDto.getSubGameId());
            ctUser.setChannelId(parsePayDto.getChannelId());
            ctUser.setChannelTypeId(parsePayDto.getChannelTypeId());
            ctUser.setChannelSubAccountId(parsePayDto.getChannelSubAccountId());
            ctUser.setDealId(parsePayDto.getDealId());
            ctUser.setUpdateTime(logTime);
            ctUser.setCreateTime(logTime);
            ctUser.setPkgId(parsePayDto.getPkgId());
            ctUser.setUserId(parsePayDto.getUserId());
            // 返回旧数据
            BeanUtils.copyProperties(ctUser, result);
            if (ctUser.getFirstPayTime() == null) {
                //首次充值，记录首充时间并登记付费用户登录情况
                ctUser.setFirstPayTime(logTime);
                // 支付用户登录标记
                ctUser.setPayUserLoginMask(1l);
            }
            ctUser.setPayTime(logTime);
            ctUser.setPlayTime(logTime);
            save(ctUser);
        } else {
            // 返回旧数据
            BeanUtils.copyProperties(ctUser, result);
            if (ctUser.getFirstPayTime() == null) {
                //首次充值，记录首充时间并登记付费用户登录情况
                ctUser.setFirstPayTime(logTime);
                // TODO 支付用户登录标记 是否可以改成数字？
                ctUser.setPayUserLoginMask(ctUser.getPayUserLoginMask() | 1);
            }
            ctUser.setPlayTime(logTime);
            ctUser.setPayTime(logTime);
            ctUser.setUpdateTime(logTime);
            updateById(ctUser);
        }
        return result;
    }
}
