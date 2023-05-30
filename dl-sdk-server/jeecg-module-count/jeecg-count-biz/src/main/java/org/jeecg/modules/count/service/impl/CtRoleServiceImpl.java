package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.TimeUtil;
import org.jeecg.modules.api.ISdkuserApi;
import org.jeecg.modules.count.dto.CtRoleDto;
import org.jeecg.modules.count.entity.CtRole;
import org.jeecg.modules.count.mapper.CtRoleMapper;
import org.jeecg.modules.count.modal.CtRoleModal;
import org.jeecg.modules.count.modal.CtUserModal;
import org.jeecg.modules.count.service.ICtRoleService;
import org.jeecg.modules.count.vo.CtRoleVo;
import org.jeecg.modules.vo.OpRegisterLoginSwitchVo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: ct_role
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class CtRoleServiceImpl extends ServiceImpl<CtRoleMapper, CtRole> implements ICtRoleService {

    @Autowired
    private CtRoleMapper ctRoleMapper;
    @Autowired
    private ISdkuserApi sdkuserApi;
    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Override
    public IPage<CtRoleVo> getPageList(Page<T> page, CtRoleDto ctRoleDto) {
        QueryWrapper<CtRoleVo> q = new QueryWrapper<>();
        if (null != ctRoleDto.getUserName() && ctRoleDto.getUserName().trim().length() > 0) {
            q.like("b.user_name", ctRoleDto.getUserName());
        }
        if (null != ctRoleDto.getRoleName() && ctRoleDto.getRoleName().trim().length() > 0) {
            q.like("a.role_name", ctRoleDto.getRoleName());
        }
        if (null != ctRoleDto.getUserId()) {
            q.like("a.user_id", ctRoleDto.getUserId());
        }
        if (null != ctRoleDto.getDealId()) {
            q.eq("a.deal_id", ctRoleDto.getDealId());
        }
        if (null != ctRoleDto.getGameId() && !ctRoleDto.getGameId().isEmpty()) {
            q.in("a.game_id", ctRoleDto.getGameId());
        }
        if (null != ctRoleDto.getSubGameId() && !ctRoleDto.getSubGameId().isEmpty()) {
            q.in("a.sub_game_id", ctRoleDto.getSubGameId());
        }
        if (null != ctRoleDto.getPkgId() && !ctRoleDto.getPkgId().isEmpty()) {
            q.in("a.pkg_id", ctRoleDto.getPkgId());
        }
        if (null != ctRoleDto.getChannelTypeId()) {
            q.eq("a.channel_type_id", ctRoleDto.getChannelTypeId());
        }
        if (null != ctRoleDto.getChannelId()) {
            q.eq("a.channel_id", ctRoleDto.getChannelId());
        }
        if (null != ctRoleDto.getChannelSubAccountId()) {
            q.eq("a.channel_sub_account_id", ctRoleDto.getChannelSubAccountId());
        }
        try {
            if (null != ctRoleDto.getRegStartTime()) {
                q.ge("a.create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctRoleDto.getRegStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != ctRoleDto.getRegEndTime()) {
                q.le("a.create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctRoleDto.getRegEndTime(), "yyyy-MM-dd 23:59:59")));
            }
            if (null != ctRoleDto.getLogStartTime()) {
                q.ge("a.alive_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctRoleDto.getLogStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != ctRoleDto.getLogEndTime()) {
                q.le("a.alive_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctRoleDto.getLogEndTime(), "yyyy-MM-dd 23:59:59")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        IPage<CtRoleVo> list = baseMapper.queryPageList(page, q);
        if (list.getTotal() > 0) {
            List<Integer> dealIdList = list.getRecords().stream().map(CtRoleVo::getDealId)
                .collect(Collectors.toList());
            List<OpRegisterLoginSwitchVo> registerLoginSwitchVoList = sdkuserApi.getListByDealIdList(
                dealIdList);
            Map<Integer, OpRegisterLoginSwitchVo> map = new LinkedHashMap<>();
            if (null != registerLoginSwitchVoList && !registerLoginSwitchVoList.isEmpty()) {
                for (OpRegisterLoginSwitchVo opRegisterLoginSwitchVo : registerLoginSwitchVoList) {
                    map.put(opRegisterLoginSwitchVo.getRuleId(), opRegisterLoginSwitchVo);
                }
            }
            for (CtRoleVo ctRoleVo : list.getRecords()) {
                if (null != ctRoleVo.getOnlineTime()) {
                    ctRoleVo.setOnlineTimeStr(TimeUtil.getTimeStr(ctRoleVo.getOnlineTime()));
                }
                ctRoleVo.setUserState(0);
                if (map.containsKey(ctRoleVo.getDealId())) {
                    if (null != map.get(ctRoleVo.getDealId()).getLoginLimitUserId()) {
                        String[] loginLimitUserArr = map.get(ctRoleVo.getDealId()).getLoginLimitUserId()
                            .split("\\r?\\n");
                        if (Arrays.asList(loginLimitUserArr)
                            .contains(String.valueOf(ctRoleVo.getUserId()))) {
                            ctRoleVo.setUserState(1);
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<CtRoleModal> getAllList(CtRoleDto ctRoleDto) {
        QueryWrapper<CtRoleVo> q = new QueryWrapper<>();
        if (null != ctRoleDto.getUserName() && ctRoleDto.getUserName().trim().length() > 0) {
            q.like("b.user_name", ctRoleDto.getUserName());
        }
        if (null != ctRoleDto.getRoleName() && ctRoleDto.getRoleName().trim().length() > 0) {
            q.like("a.role_name", ctRoleDto.getRoleName());
        }
        if (null != ctRoleDto.getUserId()) {
            q.like("a.user_id", ctRoleDto.getUserId());
        }
        if (null != ctRoleDto.getDealId()) {
            q.eq("a.deal_id", ctRoleDto.getDealId());
        }
        if (null != ctRoleDto.getGameId() && !ctRoleDto.getGameId().isEmpty()) {
            q.in("a.game_id", ctRoleDto.getGameId());
        }
        if (null != ctRoleDto.getSubGameId() && !ctRoleDto.getSubGameId().isEmpty()) {
            q.in("a.sub_game_id", ctRoleDto.getSubGameId());
        }
        if (null != ctRoleDto.getPkgId() && !ctRoleDto.getPkgId().isEmpty()) {
            q.in("a.pkg_id", ctRoleDto.getPkgId());
        }
        if (null != ctRoleDto.getChannelTypeId()) {
            q.eq("a.channel_type_id", ctRoleDto.getChannelTypeId());
        }
        if (null != ctRoleDto.getChannelId()) {
            q.eq("a.channel_id", ctRoleDto.getChannelId());
        }
        if (null != ctRoleDto.getChannelSubAccountId()) {
            q.eq("a.channel_sub_account_id", ctRoleDto.getChannelSubAccountId());
        }
        try {
            if (null != ctRoleDto.getRegStartTime()) {
                q.ge("a.create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctRoleDto.getRegStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != ctRoleDto.getRegEndTime()) {
                q.le("a.create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctRoleDto.getRegEndTime(), "yyyy-MM-dd 23:59:59")));
            }
            if (null != ctRoleDto.getLogStartTime()) {
                q.ge("a.alive_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctRoleDto.getLogStartTime(), "yyyy-MM-dd 00:00:00")));
            }
            if (null != ctRoleDto.getLogEndTime()) {
                q.le("a.alive_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(ctRoleDto.getLogEndTime(), "yyyy-MM-dd 23:59:59")));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<CtRoleVo> list = baseMapper.queryAllList(q);
        List<CtRoleModal> resList = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            for (CtRoleVo ctRoleVo : list) {
                if (null != ctRoleVo.getOnlineTime()) {
                    ctRoleVo.setOnlineTimeStr(TimeUtil.getTimeStr(ctRoleVo.getOnlineTime()));
                }
                CtRoleModal ctRoleModal = new CtRoleModal();
                BeanUtils.copyProperties(ctRoleVo, ctRoleModal);
                resList.add(ctRoleModal);
            }
        }
        return resList;
    }

    @Override
    public ModelAndView exportXls(CtRoleDto object, Class<CtRoleModal> clazz, String title) {
        List<CtRoleModal> exportList = getAllList(object);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    @Override
    public void searchAliveRole(ParseAliveDto parseAliveDto){

        LambdaQueryWrapper<CtRole> ctRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctRoleLambdaQueryWrapper.eq(CtRole::getUserId,parseAliveDto.getUserId());
        ctRoleLambdaQueryWrapper.eq(CtRole::getServerId,parseAliveDto.getServerId());
        ctRoleLambdaQueryWrapper.eq(CtRole::getRoleId,parseAliveDto.getRoleId());
        CtRole ctRole = ctRoleMapper.selectOne(ctRoleLambdaQueryWrapper);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=simpleDateFormat.format(parseAliveDto.getTime());
        Date day= DateUtils.str2Date(date,simpleDateFormat);

        if (ObjectUtils.isEmpty(ctRole)){
            CtRole insertCtRole = new CtRole();

            insertCtRole.setGameId(parseAliveDto.getGameId());
            insertCtRole.setSubGameId(parseAliveDto.getSubGameId());
            insertCtRole.setChannelId(parseAliveDto.getChannelId());
            insertCtRole.setChannelTypeId(parseAliveDto.getChannelTypeId());
            insertCtRole.setChannelSubAccountId(parseAliveDto.getChannelSubAccountId());
            insertCtRole.setDealId(parseAliveDto.getDealId());
            insertCtRole.setUpdateTime(day);
            insertCtRole.setCreateTime(day);
            insertCtRole.setPkgId(parseAliveDto.getPkgId());

            insertCtRole.setRoleId(parseAliveDto.getRoleId());
            insertCtRole.setUserId(parseAliveDto.getUserId());
            insertCtRole.setServerId(parseAliveDto.getServerId());
            insertCtRole.setServerName(parseAliveDto.getServerName());

            insertCtRole.setAliveTime(day);
            insertCtRole.setRoleLevel(parseAliveDto.getRoleLevel());
            insertCtRole.setRoleName(parseAliveDto.getRoleName());
            insertCtRole.setOnlineTime(300);
            ctRoleMapper.insert(insertCtRole);
        }else {

            int second = DateUtils.dateToDiff('s', day, ctRole.getAliveTime());

            if (second>295){
                ctRole.setOnlineTime(ctRole.getOnlineTime()+300);
            }
            ctRole.setUpdateTime(day);
            ctRole.setRoleLevel(parseAliveDto.getRoleLevel());
            ctRole.setRoleName(parseAliveDto.getRoleName());
            ctRole.setAliveTime(day);
            ctRoleMapper.updateById(ctRole);
        }

    }

    @Override
    public CtRole parsePayRole(ParsePayDto parsePayDto) {
        CtRole result = new CtRole();
        LambdaQueryWrapper<CtRole> ctRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctRoleLambdaQueryWrapper.eq(CtRole::getDealId, parsePayDto.getDealId());
        ctRoleLambdaQueryWrapper.eq(CtRole::getUserId, parsePayDto.getUserId());
        ctRoleLambdaQueryWrapper.eq(CtRole::getServerId, parsePayDto.getServerId());
        ctRoleLambdaQueryWrapper.eq(CtRole::getRoleId, parsePayDto.getRoleId());
        CtRole ctRole = getOne(ctRoleLambdaQueryWrapper);
        // 日志时间
        Date logTime = DateUtils.getDate(parsePayDto.getTime());
        if (ctRole == null) {
            ctRole = new CtRole();
            ctRole.setGameId(parsePayDto.getGameId());
            ctRole.setSubGameId(parsePayDto.getSubGameId());
            ctRole.setChannelId(parsePayDto.getChannelId());
            ctRole.setChannelTypeId(parsePayDto.getChannelTypeId());
            ctRole.setChannelSubAccountId(parsePayDto.getChannelSubAccountId());
            ctRole.setDealId(parsePayDto.getDealId());
            ctRole.setUpdateTime(logTime);
            ctRole.setCreateTime(logTime);
            ctRole.setPkgId(parsePayDto.getPkgId());
            // 返回旧数据
            BeanUtils.copyProperties(ctRole, result);
            ctRole.setUserId(parsePayDto.getUserId());
            ctRole.setServerId(parsePayDto.getServerId());
            ctRole.setRoleId(parsePayDto.getRoleId());
            save(ctRole);
        } else {
            // 返回旧数据
            BeanUtils.copyProperties(ctRole, result);
            ctRole.setAliveTime(logTime);
            ctRole.setUpdateTime(logTime);
            updateById(ctRole);
        }
        return result;
    }

}
