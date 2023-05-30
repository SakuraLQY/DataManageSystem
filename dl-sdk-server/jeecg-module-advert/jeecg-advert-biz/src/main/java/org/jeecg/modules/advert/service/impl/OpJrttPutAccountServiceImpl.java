package org.jeecg.modules.advert.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.account.api.JrttAccountApi;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccountBo;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttToRefreshAccountBo;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccessTokenRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccessTokenResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectListResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectResponse;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttRefreshTokenRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttRefreshTokenResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.constant.AccountLevelConstant;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttGrantType;
import org.jeecg.modules.advert.entity.OpJrttPutAccount;
import org.jeecg.modules.advert.entity.OpJrttPutAccountApp;
import org.jeecg.modules.advert.entity.OpPutAccount;
import org.jeecg.modules.advert.mapper.OpJrttPutAccountMapper;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.service.IOpJrttPutAccountAppService;
import org.jeecg.modules.advert.service.IOpPutAccountService;
import org.jeecg.modules.advert.vo.OpJrttPutAccountVo;
import org.jeecg.modules.advert.vo.SYNCVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @Description: op_put_account
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
@Slf4j
public class OpJrttPutAccountServiceImpl extends
    ServiceImpl<OpJrttPutAccountMapper, OpJrttPutAccount> implements
    IOpJrttPutAccountService {

    @Autowired
    private IOpJrttPutAccountAppService opPutAccountAppService;

    @Autowired
    private IOpPutAccountService opPutAccountService;

    @Override
    public IPage<OpJrttPutAccountVo> getPage(OpPutAccount opPutAccount, Integer pageNo,
        Integer pageSize, HttpServletRequest req) {
        String subGameIds = opPutAccount.getSubGameIds();
        opPutAccount.setSubGameIds(null);
        opPutAccount.setChannelId(ChannelConstant.JRTT);
        Integer gameId = null;
        Integer subGameId = null;
        if (subGameIds != null) {
            JSONObject jsonObject = JSONObject.parseObject(subGameIds);
            gameId = jsonObject.getObject("gameId", Integer.class);
            subGameId = jsonObject.getObject("subGameId", Integer.class);
        }
        Page<OpJrttPutAccountVo> page = new Page<>(pageNo, pageSize);
        // 控制台报异常 关闭count优化
        page.setOptimizeCountSql(false);
        IPage<OpJrttPutAccountVo> pageList = baseMapper.getOpJrttPutAccountList(page, gameId,
            subGameId, opPutAccount.getLevelId(), opPutAccount.getState(),
            opPutAccount.getNickName(), opPutAccount.getAccount());
        return pageList;
    }

    @Override
    public Result<String> auth(Integer accountId) {
        OpPutAccount account = opPutAccountService.getById(accountId);
        OpJrttPutAccountApp app = opPutAccountAppService.getById(account.getAppId());
        if (null == app) {
            throw new JeecgBootException("未绑定账号应用");
        }
        String authUrl = app.getAuthUrl().replace("{{accountId}}", accountId.toString());
        return Result.ok(authUrl);
    }

    @Override
    public String callBack(Integer state, String authCode) {
        if (state == null) {
            return "缺失参数state";
        }
        if (authCode == null) {
            return "缺失参数authCode";
        }
        OpPutAccount opPutAccount = opPutAccountService.getById(state);
        OpJrttPutAccountApp app = opPutAccountAppService.getById(opPutAccount.getAppId());
        JrttAccessTokenRequest jrttAccessTokenRequest = new JrttAccessTokenRequest();
        jrttAccessTokenRequest.setAppId(app.getAppId());
        jrttAccessTokenRequest.setSecret(app.getAppSecret());
        jrttAccessTokenRequest.setGrantType(JrttGrantType.AUTH_CODE);
        jrttAccessTokenRequest.setAuthCode(authCode);
        JrttBaseResponse<JrttAccessTokenResponse> jrttBaseResponse = JrttAccountApi.getAccessToken(
            jrttAccessTokenRequest);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            return jrttBaseResponse.getMessage();
        }
        boolean isUpdate = true;
        OpJrttPutAccount opJrttPutAccount = getOne(
            new LambdaQueryWrapper<OpJrttPutAccount>().eq(OpJrttPutAccount::getAccountId, state));
        if (null == opJrttPutAccount) {
            isUpdate = false;
            opJrttPutAccount = new OpJrttPutAccount();
            opJrttPutAccount.setAccountId(state);
        }
        JrttAccessTokenResponse jrttAccessTokenResponse = jrttBaseResponse.getData();
        opJrttPutAccount.setAccessToken(jrttAccessTokenResponse.getAccessToken());
        opJrttPutAccount.setRefreshToken(jrttAccessTokenResponse.getRefreshToken());
        List<String> advertiserIds = jrttAccessTokenResponse.getAdvertiserIds();
        if (!CollectionUtils.isEmpty(advertiserIds)) {
            opJrttPutAccount.setAdvertiserId(Long.parseLong(advertiserIds.get(0)));
            opJrttPutAccount.setAdvertiserIds(advertiserIds.toString());
        }
        opJrttPutAccount.setAccessTokenTime(
            LocalDateTime.now().plusSeconds(jrttAccessTokenResponse.getExpiresIn()));
        opJrttPutAccount.setRefreshTokenTime(
            LocalDateTime.now().plusSeconds(jrttAccessTokenResponse.getRefreshTokenExpiresIn()));
        opJrttPutAccount.setAuthorizeTime(LocalDateTime.now());
        if (isUpdate && !updateById(opJrttPutAccount)) {
            return "本地账号ID：" + state + ",更新广告主：" + opJrttPutAccount.getAccountId() + "授权失败";
        } else if (!isUpdate && !save(opJrttPutAccount)) {
            return "本地账号ID：" + state + ",更新广告主：" + opJrttPutAccount.getAccountId() + "授权失败";
        }
        return "本地账号ID：" + state + ",更新广告主：" + opJrttPutAccount.getAccountId() + "授权成功";
    }

    @Override
    public Result<List<SYNCVO>> synAccount(Integer accountId) {
        OpJrttPutAccount opJrttPutAccount = getOne(
            new LambdaQueryWrapper<OpJrttPutAccount>().eq(OpJrttPutAccount::getAccountId,
                accountId));
        OpPutAccount opPutAccount = opPutAccountService.getOne(
            new LambdaQueryWrapper<OpPutAccount>().eq(OpPutAccount::getId, accountId));
        String[] advertiserIds = opJrttPutAccount.getAdvertiserIds().replace("[", "")
            .replace("]", "")
            .split(",");
        LinkedList<SYNCVO> result = new LinkedList<>();
        for (String advertiserId : advertiserIds) {
            JrttAdvertiserSelectRequest jrttAdvertiserSelectRequest = new JrttAdvertiserSelectRequest();
            jrttAdvertiserSelectRequest.setAdvertiserId(advertiserId);
            JrttAdvertiserSelectResponse jrttAdvertiserSelectResponse = JrttAccountApi.advertiserSelect(
                opJrttPutAccount.getAccessToken(), jrttAdvertiserSelectRequest);
            for (JrttAdvertiserSelectListResponse jrttAdvertiserSelectListResponse : jrttAdvertiserSelectResponse.getList()) {
                SYNCVO syncvo = new SYNCVO();
                Long resAdvertiserId = jrttAdvertiserSelectListResponse.getAdvertiserId();
                syncvo.setAdvertiserId(resAdvertiserId);
                if (baseMapper.exists(new LambdaQueryWrapper<OpJrttPutAccount>()
                    .eq(OpJrttPutAccount::getAdvertiserId, resAdvertiserId))) {
                    syncvo.setFlag(SYNCVO.AUTHED);
                    result.add(syncvo);
                    continue;
                }
                syncvo.setFlag(SYNCVO.SUCCESS);
                // 插入同步的账号
                opPutAccount.setLevelId(AccountLevelConstant.LEVEL_2);
                opPutAccount.setId(null);
                opPutAccount.setPassword("");
                opPutAccount.setAccountDesc("");
                opPutAccount.setNickName(jrttAdvertiserSelectListResponse.getAdvertiserName());
                opPutAccount.setPid(accountId);
                opPutAccount.setCreateTime(LocalDateTime.now());
                if (opPutAccountService.save(opPutAccount)) {
                    opJrttPutAccount.setId(null);
                    opJrttPutAccount.setAccessToken("");
                    opJrttPutAccount.setAccessTokenTime(null);
                    opJrttPutAccount.setRefreshToken("");
                    opJrttPutAccount.setRefreshTokenTime(null);
                    opJrttPutAccount.setAdvertiserIds("");
                    opJrttPutAccount.setAuthorizeTime(null);
                    opJrttPutAccount.setAdvertiserId(resAdvertiserId);
                    opJrttPutAccount.setAccountId(opPutAccount.getId());
                    if (!save(opJrttPutAccount)) {
                        syncvo.setFlag(SYNCVO.FAIL);
                    }
                } else {
                    syncvo.setFlag(SYNCVO.FAIL);
                }
                result.add(syncvo);
            }
        }
        return Result.OK(result);
    }

    /**
     * @return void
     * @author
     * @description token刷新定时任务
     * @date 2023/1/13 10:07
     */
    @Scheduled(cron = "0/40 * * * * ?")
    public void refreshTokenTimer() {
        // 查询再过两分钟token就要过期的一级账号
        List<JrttToRefreshAccountBo> refreshAccounts = baseMapper
            .getRefreshAccount(LocalDateTime.now().plusSeconds(120L),
                LocalDateTime.now().minusSeconds(120L));
        if (refreshAccounts.size() == 0) {
            return;
        }
        for (JrttToRefreshAccountBo toRefreshAccount : refreshAccounts) {
            JrttRefreshTokenRequest jrttRefreshTokenRequest = new JrttRefreshTokenRequest();
            jrttRefreshTokenRequest.setAppId(toRefreshAccount.getAppId());
            jrttRefreshTokenRequest.setSecret(toRefreshAccount.getAppSecret());
            jrttRefreshTokenRequest.setGrantType(JrttGrantType.REFRESH_TOKEN);
            jrttRefreshTokenRequest.setRefreshToken(toRefreshAccount.getRefreshToken());
            JrttBaseResponse<JrttRefreshTokenResponse> jrttBaseResponse = JrttAccountApi.refreshToken(
                jrttRefreshTokenRequest);
            if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
                log.error("今日头条账号:{}同步失败", toRefreshAccount.getAccountId());
                continue;
            }
            baseMapper.updateToken(toRefreshAccount.getAccountId(),
                jrttBaseResponse.getData().getAccessToken(),
                LocalDateTime.now().plusSeconds(jrttBaseResponse.getData().getExpiresIn()),
                jrttBaseResponse.getData().getRefreshToken(),
                LocalDateTime.now()
                    .plusSeconds(jrttBaseResponse.getData().getRefreshTokenExpiresIn()));
            log.info("今日头条账号:{}同步成功", toRefreshAccount.getAccountId());
        }
    }

    @Override
    public JrttTokenBo getAccountToken(Integer accountId) {
        OpPutAccount opPutAccount = opPutAccountService.getById(accountId);
        JrttTokenBo jrttTokenBo = new JrttTokenBo();
        jrttTokenBo.setLevelId(opPutAccount.getLevelId());
        if (oConvertUtils.isEmpty(opPutAccount)) {
            throw new JeecgBootException("账号不存在");
        }
        OpJrttPutAccount opJrttPutAccount = getOne(
            new LambdaQueryWrapper<OpJrttPutAccount>().eq(OpJrttPutAccount::getAccountId,
                accountId));
        if (opJrttPutAccount == null) {
            throw new JeecgBootException("广告主ID不存在");
        }
        jrttTokenBo.setAdvertiserId(opJrttPutAccount.getAdvertiserId());
        jrttTokenBo.setAccessToken(opJrttPutAccount.getAccessToken());
        if (AccountLevelConstant.LEVEL_2.equals(opPutAccount.getLevelId())) {
            // 如果是二级账号 token取一级账号的
            opJrttPutAccount = getOne(
                new LambdaQueryWrapper<OpJrttPutAccount>().eq(OpJrttPutAccount::getAccountId,
                    opPutAccount.getPid()));
            if (oConvertUtils.isEmpty(opJrttPutAccount)) {
                throw new JeecgBootException("一级账号不存在");
            }
            jrttTokenBo.setAccessToken(opJrttPutAccount.getAccessToken());
        }
        if (opJrttPutAccount.getAccessTokenTime().isBefore(LocalDateTime.now())) {
            throw new JeecgBootException("access_token过期");
        }
        return jrttTokenBo;
    }

    @Override
    @Transactional
    public void deleteByAccountId(Integer accountId) {
        remove(new LambdaQueryWrapper<OpJrttPutAccount>().eq(OpJrttPutAccount::getAccountId,
            accountId));
        opPutAccountService.removeById(accountId);
    }

    @Override
    public void batchDeleteByAccountId(String accountIds) {
        List<Integer> accountIdList = Arrays.stream(accountIds.split(","))
            .map(a -> Integer.parseInt(a)).collect(Collectors.toList());
        opPutAccountService.removeByIds(accountIdList);
        remove(new LambdaQueryWrapper<OpJrttPutAccount>().in(OpJrttPutAccount::getAccountId,
            accountIdList));
    }

    @Override
    public List<JrttAccountBo> getFirstLevelAccount() {
        return baseMapper.getFirstLevelAccount();
    }

    @Override
    public List<JrttAccountBo> getListByPid(Integer pid) {
        return baseMapper.getListByPid(pid);
    }
}
