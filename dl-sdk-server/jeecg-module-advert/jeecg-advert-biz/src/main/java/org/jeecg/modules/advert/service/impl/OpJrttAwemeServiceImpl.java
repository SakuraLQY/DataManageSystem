package org.jeecg.modules.advert.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.DateUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.tool.api.JrttToolApi;
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListFilteringRequest;
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListListResponse;
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListRequest;
import org.jeecg.modules.advert.constant.jrtt.JrttAwemeAuthConstant;
import org.jeecg.modules.advert.entity.OpJrttAweme;
import org.jeecg.modules.advert.mapper.OpJrttAwemeMapper;
import org.jeecg.modules.advert.service.IOpJrttAwemeService;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_jrtt_aweme
 * @Author: jeecg-boot
 * @Date: 2023-02-22
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_gateway")
public class OpJrttAwemeServiceImpl extends ServiceImpl<OpJrttAwemeMapper, OpJrttAweme> implements
    IOpJrttAwemeService {

    @Autowired
    private IOpJrttPutAccountService opJrttPutAccountService;

    @Override
    public List<OpJrttAweme> syncAwemeByAccountId(Integer accountId) {
        JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(accountId);
        JrttAwemeAuthListRequest jrttAwemeAuthListRequest = new JrttAwemeAuthListRequest();
        jrttAwemeAuthListRequest.setPage(1);
        jrttAwemeAuthListRequest.setPageSize(100);
        JrttAwemeAuthListFilteringRequest jrttAwemeAuthListFilteringRequest = new JrttAwemeAuthListFilteringRequest();
        jrttAwemeAuthListFilteringRequest.setAuthType(
            new ArrayList<String>() {{
                add(JrttAwemeAuthConstant.Type.AWEME_ACCOUNT);
            }});
        jrttAwemeAuthListRequest.setFiltering(jrttAwemeAuthListFilteringRequest);
        jrttAwemeAuthListRequest.setAuthStatus(
            new ArrayList<String>() {{
                add(JrttAwemeAuthConstant.Status.AUTHRIZED);
            }});
        jrttAwemeAuthListRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
        List<JrttAwemeAuthListListResponse> jrttAwemeAuthListListResponseList = JrttToolApi.awemeAuthList(
            jrttAwemeAuthListRequest, jrttTokenBo.getAccessToken());
        List<OpJrttAweme> opJrttAwemeList = new ArrayList();
        for (JrttAwemeAuthListListResponse jrttAwemeAuthListListResponse : jrttAwemeAuthListListResponseList) {
            OpJrttAweme opJrttAweme = new OpJrttAweme();
            opJrttAweme.setAccountId(accountId);
            opJrttAweme.setAwemeId(jrttAwemeAuthListListResponse.getAwemeId());
            opJrttAweme.setAwemeName(jrttAwemeAuthListListResponse.getAwemeName());
            opJrttAweme.setStartTime(
                DateUtils.str2Date(jrttAwemeAuthListListResponse.getStartTime(),
                    DateUtils.date_sdf.get()));
            opJrttAweme.setEndTime(DateUtils.str2Date(jrttAwemeAuthListListResponse.getEndTime(),
                DateUtils.date_sdf.get()));
            opJrttAwemeList.add(opJrttAweme);
        }
        if (CollectionUtil.isNotEmpty(opJrttAwemeList)) {
            // 先删除后添加
            remove(new LambdaQueryWrapper<OpJrttAweme>().eq(OpJrttAweme::getAccountId, accountId));
            saveBatch(opJrttAwemeList);
            log.info("同步成功,记录数:{}", opJrttAwemeList.size());
        } else {
            log.info("没有需要同步的数据");
        }
        return opJrttAwemeList;
    }

}
