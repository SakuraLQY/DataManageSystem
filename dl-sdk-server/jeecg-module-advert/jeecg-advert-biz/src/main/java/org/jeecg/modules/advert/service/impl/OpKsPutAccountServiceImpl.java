package org.jeecg.modules.advert.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.account.api.JrttAccountApi;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccessTokenRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccessTokenResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectListResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttRefreshTokenRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttRefreshTokenResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttToRefreshAccountBo;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.ks.account.api.KsAccountApi;
import org.jeecg.modules.advert.api.ks.account.bo.KsAccessTokenRequest;
import org.jeecg.modules.advert.api.ks.account.bo.KsAccessTokenResponse;
import org.jeecg.modules.advert.api.ks.account.bo.KsRefreshTokenRequest;
import org.jeecg.modules.advert.api.ks.account.bo.KsRefreshTokenResponse;
import org.jeecg.modules.advert.api.ks.account.bo.KsToRefreshAccountBo;
import org.jeecg.modules.advert.api.ks.account.bo.KsTokenBo;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse;
import org.jeecg.modules.advert.constant.AccountLevelConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttGrantType;
import org.jeecg.modules.advert.constant.ks.KsCodeConstant;
import org.jeecg.modules.advert.dto.OpPutAccountDto;
import org.jeecg.modules.advert.entity.OpJrttPutAccount;
import org.jeecg.modules.advert.entity.OpJrttPutAccountApp;
import org.jeecg.modules.advert.entity.OpKsPutAccount;
import org.jeecg.modules.advert.entity.OpKsPutAccountApp;
import org.jeecg.modules.advert.entity.OpPutAccount;
import org.jeecg.modules.advert.mapper.OpJrttPutAccountMapper;
import org.jeecg.modules.advert.mapper.OpKsPutAccountMapper;
import org.jeecg.modules.advert.service.IOpJrttPutAccountAppService;
import org.jeecg.modules.advert.service.IOpKsPutAccountAppService;
import org.jeecg.modules.advert.service.IOpKsPutAccountService;
import org.jeecg.modules.advert.service.IOpPutAccountService;
import org.jeecg.modules.advert.vo.OpJrttPutAccountVo;
import org.jeecg.modules.advert.vo.OpKsPutAccountVo;
import org.jeecg.modules.advert.vo.SYNCVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @Description: op_ks_put_account
 * @Author: jeecg-boot
 * @Date:   2023-03-07
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
@Slf4j
public class OpKsPutAccountServiceImpl extends ServiceImpl<OpKsPutAccountMapper, OpKsPutAccount> implements IOpKsPutAccountService {

    @Autowired
    private IOpKsPutAccountAppService opKsPutAccountAppService;

    @Autowired
    private IOpPutAccountService opPutAccountService;

    @Autowired
    private OpKsPutAccountMapper opKsPutAccountMapper;

    @Override
    public IPage<OpKsPutAccountVo> getPage(OpPutAccount opPutAccount, Integer pageNo,
        Integer pageSize, HttpServletRequest req) {
        String subGameIds = opPutAccount.getSubGameIds();
        opPutAccount.setSubGameIds(null);
        opPutAccount.setChannelId(ChannelConstant.KUAI_SHOU);
        Integer gameId = null;
        Integer subGameId = null;
        if (subGameIds != null) {
            JSONObject jsonObject = JSONObject.parseObject(subGameIds);
            gameId = jsonObject.getObject("gameId", Integer.class);
            subGameId = jsonObject.getObject("subGameId", Integer.class);
        }
        Page<OpKsPutAccountVo> page = new Page<>(pageNo, pageSize);
        // 控制台报异常 关闭count优化
        page.setOptimizeCountSql(false);
        IPage<OpKsPutAccountVo> pageList = baseMapper.getOpKsPutAccountList(page, gameId,
            subGameId, opPutAccount.getAccount(), opPutAccount.getLevelId(), opPutAccount.getState(),opPutAccount.getNickName());
        return pageList;
    }

    @Override
    public Result<String> auth(Integer accountId) {
        OpPutAccount account = opPutAccountService.getById(accountId);
        OpKsPutAccountApp app = opKsPutAccountAppService.getById(account.getAppId());
        if (null == app) {
            throw new JeecgBootException("未绑定账号应用");
        }
        String authUrl = app.getAuthUrl().replace("{{accountId}}", accountId.toString());
        return Result.ok(authUrl);
    }

    @Override
    public void addSecondAccount(OpPutAccountDto opPutAccount) {
        OpPutAccount putAccount = new OpPutAccount();
        BeanUtils.copyProperties(opPutAccount, putAccount);
        opPutAccountService.save(putAccount);
        OpKsPutAccount opKsPutAccount = new OpKsPutAccount();
        opKsPutAccount.setAccountId(putAccount.getId());
        opKsPutAccount.setAdvertiserId(opPutAccount.getAdvertiserId());
        save(opKsPutAccount);

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
        OpKsPutAccountApp app = opKsPutAccountAppService.getById(opPutAccount.getAppId());
        KsAccessTokenRequest ksAccessTokenRequest = new KsAccessTokenRequest();
        ksAccessTokenRequest.setAppId(app.getAppId());
        ksAccessTokenRequest.setSecret(app.getAppSecret());
        ksAccessTokenRequest.setAuthCode(authCode);
        KsBaseResponse<KsAccessTokenResponse> ksBaseResponse = KsAccountApi.getAccessToken(
            ksAccessTokenRequest);
        if (!KsCodeConstant.OK.equals(ksBaseResponse.getCode())) {
            return ksBaseResponse.getMessage();
        }
        OpKsPutAccount opKsPutAccount = getOne(
            new LambdaQueryWrapper<OpKsPutAccount>().eq(OpKsPutAccount::getAccountId, state));
        if (null == opKsPutAccount) {
            opKsPutAccount = new OpKsPutAccount();
            opKsPutAccount.setAccountId(state);
        }
        KsAccessTokenResponse ksAccessTokenResponse = ksBaseResponse.getData();
        opKsPutAccount.setAccessToken(ksAccessTokenResponse.getAccessToken());
        opKsPutAccount.setRefreshToken(ksAccessTokenResponse.getRefreshToken());
        opKsPutAccount.setAdvertiserId(ksAccessTokenResponse.getAdvertiserId());
        List<Long> advertiserIds = ksAccessTokenResponse.getAdvertiserIds();
        if (!CollectionUtils.isEmpty(advertiserIds)) {
            opKsPutAccount.setAdvertiserIds(advertiserIds.toString());
        }
        opKsPutAccount.setAccessTokenTime(
            LocalDateTime.now().plusSeconds(ksAccessTokenResponse.getAccessTokenExpiresIn()));
        opKsPutAccount.setRefreshTokenTime(
            LocalDateTime.now().plusSeconds(ksAccessTokenResponse.getRefreshTokenExpiresIn()));
        opKsPutAccount.setAuthorizeTime(LocalDateTime.now());
        if (!updateById(opKsPutAccount) && !save(opKsPutAccount)) {
            return "本地账号ID：" + state + ",更新广告主：" + opKsPutAccount.getAccountId() + "授权失败";
        }
        return "本地账号ID：" + state + ",更新广告主：" + opKsPutAccount.getAccountId() + "授权成功";
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
        List<KsToRefreshAccountBo> refreshAccounts = baseMapper
            .getRefreshAccount(LocalDateTime.now().plusSeconds(120L),
                LocalDateTime.now().minusSeconds(120L));
        if (refreshAccounts.size() == 0) {
            return;
        }
        for (KsToRefreshAccountBo ksToRefreshAccountBo : refreshAccounts) {
            KsRefreshTokenRequest ksRefreshTokenRequest = new KsRefreshTokenRequest();
            ksRefreshTokenRequest.setAppId(ksToRefreshAccountBo.getAppId());
            ksRefreshTokenRequest.setSecret(ksToRefreshAccountBo.getAppSecret());
            ksRefreshTokenRequest.setRefreshToken(ksToRefreshAccountBo.getRefreshToken());
            KsBaseResponse<KsRefreshTokenResponse> ksBaseResponse = KsAccountApi.refreshToken(
                ksRefreshTokenRequest);
            if (!KsCodeConstant.OK.equals(ksBaseResponse.getCode())) {
                log.error("快手账号:{}同步失败", ksToRefreshAccountBo.getAccountId());
                continue;
            }
            baseMapper.updateToken(ksToRefreshAccountBo.getAccountId(),
                ksBaseResponse.getData().getAccessToken(),
                LocalDateTime.now().plusSeconds(ksBaseResponse.getData().getAccessTokenExpiresIn()),
                ksBaseResponse.getData().getRefreshToken(),
                LocalDateTime.now()
                    .plusSeconds(ksBaseResponse.getData().getRefreshTokenExpiresIn()));
            log.info("今日头条账号:{}同步成功", ksToRefreshAccountBo.getAccountId());
        }
    }

    @Override
    public KsTokenBo getAccountToken(Integer accountId) {
        OpPutAccount opPutAccount = opPutAccountService.getById(accountId);
        KsTokenBo ksTokenBo = new KsTokenBo();
        if (oConvertUtils.isEmpty(opPutAccount)) {
            throw new JeecgBootException("账号不存在");
        }
        OpKsPutAccount opKsPutAccount = getOne(
            new LambdaQueryWrapper<OpKsPutAccount>().eq(OpKsPutAccount::getAccountId, accountId));
        if (opKsPutAccount == null) {
            throw new JeecgBootException("广告主ID不存在");
        }
        ksTokenBo.setAdvertiserId(opKsPutAccount.getAdvertiserId());
        ksTokenBo.setAccessToken(opKsPutAccount.getAccessToken());
        if (AccountLevelConstant.LEVEL_2.equals(opPutAccount.getLevelId())) {
            // 如果是二级账号 token取一级账号的
            opKsPutAccount = getOne(
                new LambdaQueryWrapper<OpKsPutAccount>().eq(OpKsPutAccount::getAccountId, opPutAccount.getPid()));
            if (oConvertUtils.isEmpty(opKsPutAccount)) {
                throw new JeecgBootException("一级账号不存在");
            }
            ksTokenBo.setAccessToken(opKsPutAccount.getAccessToken());
        }
        if (opKsPutAccount.getAccessTokenTime().isBefore(LocalDateTime.now())) {
            throw new JeecgBootException("access_token过期");
        }
        return ksTokenBo;
    }

    @Override
    @Transactional
    public void deleteByAccountId(Integer accountId) {
        remove(new LambdaQueryWrapper<OpKsPutAccount>().eq(OpKsPutAccount::getAccountId,
            accountId));
        opPutAccountService.removeById(accountId);
    }

    @Override
    public void batchDeleteByAccountId(String accountIds) {
        List<Integer> accountIdList = Arrays.stream(accountIds.split(","))
            .map(a -> Integer.parseInt(a)).collect(Collectors.toList());
        opPutAccountService.removeByIds(accountIdList);
        remove(new LambdaQueryWrapper<OpKsPutAccount>().in(OpKsPutAccount::getAccountId,
            accountIdList));
    }
}
