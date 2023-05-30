package org.jeecg.modules.advert.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.audience.api.JrttAudienceApi;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttAddAudienceRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttAddAudienceResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAudiencePackagesAudienceResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAudiencePackagesResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAudienceRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAudienceResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeAuthorInfoRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeAuthorInfoResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanAccountsRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanAccountsResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanCategoriesRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanCategoriesResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSearchInfoRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSearchInfoResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSimilarAccountsRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSimilarAccountsResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCountryInfoRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCountryInfoResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCustomAudienceRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCustomAudienceResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetLiveAuthorizeRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetLiveAuthorizeResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttUpdateAudienceRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttUpdateAudienceResponse;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponseList;
import org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestId2WordOrSuggestRsp;
import org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestId2WordReq;
import org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestSuggestReq;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionCategoryReq;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionInterestCategory;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionKeywordsReq;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionKeywordsRsp;
import org.jeecg.modules.advert.api.jrtt.audience.constant.JrttDealInterestActionConstant;
import org.jeecg.modules.advert.bo.opJrttAudience.AwemeFanConfConfig;
import org.jeecg.modules.advert.bo.opJrttAudience.DistrictConfConfig;
import org.jeecg.modules.advert.bo.opJrttAudience.InterestActionConfConfig;
import org.jeecg.modules.advert.bo.opJrttAudience.RetargetingTagsConfConfig;
import org.jeecg.modules.advert.bo.opJrttAudience.SuperiorPopularityTypeConfConfig;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttOtherConstant;
import org.jeecg.modules.advert.dto.OpJrttAudienceDto;
import org.jeecg.modules.advert.entity.OpJrttAudience;
import org.jeecg.modules.advert.mapper.OpJrttAudienceMapper;
import org.jeecg.modules.advert.service.IOpJrttAudienceService;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.util.RedisUtils;
import org.jeecg.modules.advert.vo.AudienceVo;
import org.jeecg.modules.advert.vo.OpJrttAudienceVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.CollectionUtils;

/**
 * @Description: op_jrtt_audience
 * @Author: jeecg-boot
 * @Date: 2023-02-20
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
@Slf4j
public class OpJrttAudienceServiceImpl extends
    ServiceImpl<OpJrttAudienceMapper, OpJrttAudience> implements IOpJrttAudienceService {

    @Autowired
    private IOpJrttPutAccountService opJrttPutAccountService;
    @Autowired
    private OpJrttAudienceMapper opJrttAudienceMapper;
    @Autowired
    private IOpJrttPutAccountService accountService;

    @Override
    public IPage<OpJrttAudienceVo> selectList(Page<OpJrttAudienceVo> page,
        OpJrttAudienceDto opJrttAudienceDto) {
        LambdaQueryWrapper<OpJrttAudience> wrapper = new LambdaQueryWrapper<>();
        if (null != opJrttAudienceDto.getName()) {
            wrapper.like(OpJrttAudience::getName, opJrttAudienceDto.getName());
        }
        if (null != opJrttAudienceDto.getAccountId()) {
            wrapper.eq(OpJrttAudience::getAccountId, opJrttAudienceDto.getAccountId());
        }
        if (null != opJrttAudienceDto.getLandingType()) {
            wrapper.eq(OpJrttAudience::getLandingType, opJrttAudienceDto.getLandingType());
        }
        if (null != opJrttAudienceDto.getCreateBy()) {
            wrapper.eq(OpJrttAudience::getCreateBy, opJrttAudienceDto.getCreateBy());
        }
        if (null != opJrttAudienceDto.getStartTime()) {
            wrapper.ge(OpJrttAudience::getCreateTime, opJrttAudienceDto.getStartTime());
        }
        if (null != opJrttAudienceDto.getEndTime()) {
            wrapper.le(OpJrttAudience::getCreateTime, opJrttAudienceDto.getEndTime());
        }
        List<OpJrttAudience> opJrttAudienceList = opJrttAudienceMapper.selectList(wrapper);
        List<OpJrttAudienceVo> list = new ArrayList<>();
        for (OpJrttAudience opJrttAudience : opJrttAudienceList) {
            OpJrttAudienceVo opJrttAudienceVo = new OpJrttAudienceVo();
            opJrttAudienceVo.setId(opJrttAudience.getId());
            opJrttAudienceVo.setAudiencePackageId(opJrttAudience.getAudiencePackageId());
            opJrttAudienceVo.setName(opJrttAudience.getName());
            opJrttAudienceVo.setDescription(opJrttAudience.getDescription());
            opJrttAudienceVo.setLandingType(opJrttAudience.getLandingType());
            opJrttAudienceVo.setAccountId(opJrttAudience.getAccountId());
            opJrttAudienceVo.setDeliveryRange(opJrttAudience.getDeliveryRange());
            opJrttAudienceVo.setDistrict(opJrttAudience.getDistrict());
            opJrttAudienceVo.setGender(opJrttAudience.getGender());
            if (opJrttAudience.getAge() != null && !opJrttAudience.getAge().isEmpty()) {
                opJrttAudienceVo.setAge(
                    Arrays.asList(opJrttAudience.getAge().replaceAll(" ", "").split(",")));
            }
            if (oConvertUtils.isNotEmpty(opJrttAudience.getRetargetingTagsConf())) {
                JSONObject jsonObject = JSONObject.parseObject(
                    opJrttAudience.getRetargetingTagsConf());
                opJrttAudienceVo.setRetargetingTagsConf(jsonObject);
            }
            opJrttAudienceVo.setSuperiorPopularityType(opJrttAudience.getSuperiorPopularityType());
            opJrttAudienceVo.setInterestActionMode(opJrttAudience.getInterestActionMode());
            if (opJrttAudience.getAwemeFanConf() != null) {
                JSONObject jsonObject = JSONObject.parseObject(opJrttAudience.getAwemeFanConf());
                opJrttAudienceVo.setAwemeFanConf(jsonObject);
            }
            opJrttAudienceVo.setFilterAwemeAbnormalActive(
                opJrttAudience.getFilterAwemeAbnormalActive());
            opJrttAudienceVo.setFilterOwnAwemeFans(opJrttAudience.getFilterOwnAwemeFans());
            opJrttAudienceVo.setFilterAwemeFansCount(opJrttAudience.getFilterAwemeFansCount());
            if (opJrttAudience.getPlatform() != null && !opJrttAudience.getPlatform().isEmpty()) {
                opJrttAudienceVo.setPlatform(
                    Arrays.asList(opJrttAudience.getPlatform().replaceAll(" ", "").split(",")));
            }
            if (opJrttAudience.getDeviceType() != null && !opJrttAudience.getDeviceType()
                .isEmpty()) {
                opJrttAudienceVo.setDeviceType(
                    Arrays.asList(opJrttAudience.getDeviceType().replaceAll(" ", "").split(",")));
            }
            if (opJrttAudience.getAc() != null && !opJrttAudience.getAc().isEmpty()) {
                opJrttAudienceVo.setAc(
                    Arrays.asList(opJrttAudience.getAc().replaceAll(" ", "").split(",")));
            }
            opJrttAudienceVo.setHideIfExists(opJrttAudience.getHideIfExists());
            if (opJrttAudience.getDeviceBrand() != null && !opJrttAudience.getDeviceBrand()
                .isEmpty()) {
                opJrttAudienceVo.setDeviceBrand(
                    Arrays.asList(opJrttAudience.getDeviceBrand().replaceAll(" ", "").split(",")));
            }
            if (opJrttAudience.getLaunchPrice() != null && !opJrttAudience.getLaunchPrice()
                .isEmpty()) {
                opJrttAudienceVo.setLaunchPrice(
                    Arrays.asList(opJrttAudience.getLaunchPrice().replaceAll(" ", "").split(",")));
            }
            if (opJrttAudience.getAutoExtendTargets() != null
                && !opJrttAudience.getAutoExtendTargets().isEmpty()) {
                opJrttAudienceVo.setAutoExtendTargets(Arrays.asList(
                    opJrttAudience.getAutoExtendTargets().replaceAll(" ", "").split(",")));
            }
            opJrttAudienceVo.setIosOsv(opJrttAudience.getIosOsv());
            if (opJrttAudience.getCarrier() != null && !opJrttAudience.getCarrier().isEmpty()) {
                opJrttAudienceVo.setCarrier(
                    Arrays.asList(opJrttAudience.getCarrier().replaceAll(" ", "").split(",")));
            }
            opJrttAudienceVo.setAndroidOsv(opJrttAudience.getAndroidOsv());
            if (opJrttAudience.getArticleCategory() != null && !opJrttAudience.getArticleCategory()
                .isEmpty()) {
                opJrttAudienceVo.setArticleCategory(Arrays.asList(
                    opJrttAudience.getArticleCategory().replaceAll(" ", "").split(",")));
            }
            if (opJrttAudience.getInterestActionConf() != null) {
                JSONObject jsonObject = JSONObject.parseObject(
                    opJrttAudience.getInterestActionConf());
                opJrttAudienceVo.setInterestActionConf(jsonObject);
            }
            if (opJrttAudience.getDistrictConf() != null) {
                JSONObject jsonObject = JSONObject.parseObject(opJrttAudience.getDistrictConf());
                opJrttAudienceVo.setDistrictConf(jsonObject);
            }
            if (opJrttAudience.getSuperiorPopularityTypeConf() != null) {
                JSONObject jsonObject = JSONObject.parseObject(
                    opJrttAudience.getSuperiorPopularityTypeConf());
                opJrttAudienceVo.setSuperiorPopularityTypeConf(jsonObject);
            }
            opJrttAudienceVo.setHideIfConverted(opJrttAudience.getHideIfConverted());
            opJrttAudienceVo.setConvertedTimeDuration(opJrttAudience.getConvertedTimeDuration());
            opJrttAudienceVo.setGeolocation(opJrttAudience.getGeolocation());
            if (opJrttAudience.getAwemeFansNumbers() != null
                && !opJrttAudience.getAwemeFansNumbers().isEmpty()) {
                if (opJrttAudience.getAwemeFansNumbers().equals("[]")) {
                    opJrttAudienceVo.setAwemeFansNumbers(new ArrayList<>());
                } else {
                    opJrttAudienceVo.setAwemeFansNumbers(Arrays.asList(
                        opJrttAudience.getAwemeFansNumbers().replaceAll(" ", "").split(",")));
                }
            }
            opJrttAudienceVo.setCreateBy(opJrttAudience.getCreateBy());
            opJrttAudienceVo.setCreateTime(opJrttAudience.getCreateTime());
            list.add(opJrttAudienceVo);
        }
        Collections.reverse(list);
        IPage<OpJrttAudienceVo> pageList = new Page<>(page.getCurrent(), page.getSize());
        pageList.setRecords(list);
        pageList.setTotal(list.size());
        return pageList;
    }

    @Override
    public JrttBaseResponseList<JrttDealActionInterestCategory> getActionOrInterestCategories(
        Integer type,
        Integer accountId,
        List<String> actionScene, Integer actionDays) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        List<String> actionScene1 = spliceStr(actionScene);
        //构造请求对象
        JrttDealActionCategoryReq jrttDealActionCategoryReq =
            type.equals(JrttDealInterestActionConstant.TYPE_ACTION)
                ? new JrttDealActionCategoryReq(accountToken.getAdvertiserId(), actionScene1,
                actionDays)
                : new JrttDealActionCategoryReq(accountToken.getAdvertiserId());
        //发送请求
        return JrttAudienceApi
            .getActionOrInterestCategories(type, accountToken.getAccessToken(),
                jrttDealActionCategoryReq);
    }

    private List<String> spliceStr(List<String> actionScene) {
        LinkedList<String> actionScene1 = new LinkedList<>();
        int size = actionScene.size();
        for (int i = 0; i < size; i++) {
            String s = actionScene.get(i);
            String str = s.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\\"", "");
            actionScene1.add(str);
        }
        return actionScene1;
    }

    @Override
    public JrttBaseResponse<JrttDealActionKeywordsRsp> getActionOrInterestKeywords(Integer type,
        Integer accountId, String queryWords,
        List<String> actionScene, Integer actionDays) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        List<String> actionScene1 = spliceStr(actionScene);
        JrttDealActionKeywordsReq req =
            type.equals(JrttDealInterestActionConstant.TYPE_ACTION)
                ? new JrttDealActionKeywordsReq(accountToken.getAdvertiserId(), actionScene1,
                actionDays, queryWords)
                : new JrttDealActionKeywordsReq(accountToken.getAdvertiserId(), queryWords);

        return JrttAudienceApi
            .getActionOrInterestKeywords(type, accountToken.getAccessToken(), req);
    }

    @Override
    public JrttBaseResponse<ActionInterestId2WordOrSuggestRsp> getInterestActionInfoByIs(
        Integer accountId, List<String> ids, String tagType, String targetingType,
        List<String> actionScene, Integer actionDays) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        List<String> actionScene1 = spliceStr(actionScene);
        List<Integer> ids1 = spliceStr(ids).stream().mapToInt(Integer::parseInt).boxed()
            .collect(Collectors.toList());
        ActionInterestId2WordReq req = new ActionInterestId2WordReq(accountToken.getAdvertiserId(),
            tagType, targetingType, actionScene1, actionDays, ids1);
        return JrttAudienceApi.getInterestActionInfoByIs(accountToken.getAccessToken(), req);
    }

    @Override
    public JrttBaseResponse<ActionInterestId2WordOrSuggestRsp> getActionInterestKeywordSuggest(
        Integer accountId, Integer id, String tagType, String targetingType,
        List<String> actionScene, Integer actionDays) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        List<String> actionScene1 = spliceStr(actionScene);
        actionScene1.remove("SEARCH");
        ActionInterestSuggestReq req = new ActionInterestSuggestReq(
            accountToken.getAdvertiserId(), tagType, targetingType, actionScene1, actionDays, id);
        return JrttAudienceApi.getActionInterestKeywordSuggest(accountToken.getAccessToken(), req);
    }

    @Override
    public JrttBaseResponse<JrttGetCustomAudienceResponse> getCustomAudienceList(
        Integer selectType, Integer accountId, String invokeSource) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        JrttGetCustomAudienceRequest jrttGetCustomAudienceRequest = new JrttGetCustomAudienceRequest();
        jrttGetCustomAudienceRequest.setAdvertiserId(accountToken.getAdvertiserId());
        jrttGetCustomAudienceRequest.setSelectType(selectType);
        return JrttAudienceApi.getCustomAudience(accountToken.getAccessToken(),
            jrttGetCustomAudienceRequest);
    }

    @Override
    @Cacheable(cacheNames = RedisUtils.GET_COUNTRY_INFO_LIST, key = "'CN_info'")
    public JrttBaseResponse<JrttGetCountryInfoResponse> getCountryInfoList(
        Integer accountId) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        JrttGetCountryInfoRequest jrttGetCountryInfoRequest = new JrttGetCountryInfoRequest();
        jrttGetCountryInfoRequest.setAdvertiserId(accountToken.getAdvertiserId());
        List<String> codes = new ArrayList<>();
        codes.add("CN");
        jrttGetCountryInfoRequest.setCodes(codes);
        jrttGetCountryInfoRequest.setLanguage("ZH_CN");
        jrttGetCountryInfoRequest.setSubDistrict("FOUR_LEVEL");
        return JrttAudienceApi.getCountryInfo(accountToken.getAccessToken(),
            jrttGetCountryInfoRequest);
    }

    @Override
    public JrttBaseResponse<JrttGetAwemeFanCategoriesResponse> getAwemeFanCategories(
        Integer accountId,
        String behaviors) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        JrttGetAwemeFanCategoriesRequest jrttGetAwemeFanCategoriesRequest = new JrttGetAwemeFanCategoriesRequest();
        jrttGetAwemeFanCategoriesRequest.setAdvertiserId(accountToken.getAdvertiserId());
        jrttGetAwemeFanCategoriesRequest.setBehaviors(Arrays.asList(
            behaviors.substring(1, behaviors.length() - 1).replaceAll("\"", "").split(",")));
        return JrttAudienceApi.getAwemeFanCategories(accountToken.getAccessToken(),
            jrttGetAwemeFanCategoriesRequest);
    }

    @Override
    public JrttBaseResponse<JrttGetAwemeAuthorInfoResponse> getAwemeAuthorInfo(Integer accountId,
        String labelIds, String behaviors) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        JrttGetAwemeAuthorInfoRequest jrttGetAwemeAuthorInfoRequest = new JrttGetAwemeAuthorInfoRequest();
        jrttGetAwemeAuthorInfoRequest.setAdvertiserId(accountToken.getAdvertiserId());
        List<String> str = Arrays.asList(labelIds.substring(1, labelIds.length() - 1).split(","));
        List<Long> ids = str.stream().map(Long::parseLong).collect(Collectors.toList());
        jrttGetAwemeAuthorInfoRequest.setLabelIds(ids);
        jrttGetAwemeAuthorInfoRequest.setBehaviors(Arrays.asList(
            behaviors.substring(1, behaviors.length() - 1).replaceAll("\"", "").split(",")));
        return JrttAudienceApi.getAwemeAuthorInfo(accountToken.getAccessToken(),
            jrttGetAwemeAuthorInfoRequest);
    }

    @Override
    public JrttBaseResponse<JrttGetAwemeSearchInfoResponse> getAwemeSearchInfo(Integer accountId,
        String queryWord, String behaviors) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        JrttGetAwemeSearchInfoRequest jrttGetAwemeSearchInfoRequest = new JrttGetAwemeSearchInfoRequest();
        jrttGetAwemeSearchInfoRequest.setAdvertiserId(accountToken.getAdvertiserId());
        jrttGetAwemeSearchInfoRequest.setQueryWord(queryWord);
        jrttGetAwemeSearchInfoRequest.setBehaviors(Arrays.asList(
            behaviors.substring(1, behaviors.length() - 1).replaceAll("\"", "").split(",")));
        return JrttAudienceApi.getAwemeSearchInfo(accountToken.getAccessToken(),
            jrttGetAwemeSearchInfoRequest);
    }

    @Override
    public JrttBaseResponse<JrttGetAwemeSimilarAccountsResponse> getAwemeSimilarAccounts(
        Integer accountId, String awemeId, String behaviors) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        JrttGetAwemeSimilarAccountsRequest jrttGetAwemeSimilarAccountsRequest = new JrttGetAwemeSimilarAccountsRequest();
        jrttGetAwemeSimilarAccountsRequest.setAdvertiserId(accountToken.getAdvertiserId());
        jrttGetAwemeSimilarAccountsRequest.setAwemeId(awemeId);
        jrttGetAwemeSimilarAccountsRequest.setBehaviors(Arrays.asList(
            behaviors.substring(1, behaviors.length() - 1).replaceAll("\"", "").split(",")));
        return JrttAudienceApi.getAwemeSimilarAccounts(accountToken.getAccessToken(),
            jrttGetAwemeSimilarAccountsRequest);
    }

    @Override
    public JrttBaseResponse<JrttGetAwemeFanAccountsResponse> getAwemeFanAccounts(
        Integer accountId, Integer categoryId, String behaviors) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        JrttGetAwemeFanAccountsRequest jrttGetAwemeFanAccountsRequest = new JrttGetAwemeFanAccountsRequest();
        jrttGetAwemeFanAccountsRequest.setAdvertiserId(accountToken.getAdvertiserId());
        jrttGetAwemeFanAccountsRequest.setCategoryId(categoryId);
        jrttGetAwemeFanAccountsRequest.setBehaviors(Arrays.asList(
            behaviors.substring(1, behaviors.length() - 1).replaceAll("\"", "").split(",")));
        return JrttAudienceApi.getAwemeFanAccounts(accountToken.getAccessToken(),
            jrttGetAwemeFanAccountsRequest);
    }

    @Override
    public JrttBaseResponse<JrttGetLiveAuthorizeResponse> getLiveAuthorize(Integer accountId,
        String status, Integer page, Integer pageSize) {
        JrttTokenBo accountToken = accountService.getAccountToken(accountId);
        JrttGetLiveAuthorizeRequest jrttGetLiveAuthorizeRequest = new JrttGetLiveAuthorizeRequest();
        jrttGetLiveAuthorizeRequest.setAdvertiserId(accountToken.getAdvertiserId());
        jrttGetLiveAuthorizeRequest.setPage(page);
        jrttGetLiveAuthorizeRequest.setPageSize(50);
        jrttGetLiveAuthorizeRequest.setStatus(Arrays.asList(
            status.substring(1, status.length() - 1).replaceAll("\"", "").split(",")));
        return JrttAudienceApi.getLiveAuthorize(accountToken.getAccessToken(),
            jrttGetLiveAuthorizeRequest);
    }

    @Override
    public void save(OpJrttAudienceDto opJrttAudience) {
        LambdaQueryWrapper<OpJrttAudience> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpJrttAudience::getName, opJrttAudience.getName());
        if (opJrttAudience.getId() != null) {
            //判断重名
            if (!opJrttAudienceMapper.selectById(opJrttAudience.getId()).getName()
                .equals(opJrttAudience.getName())) {
                if (oConvertUtils.isNotEmpty(opJrttAudienceMapper.selectOne(wrapper))) {
                    throw new JeecgBootException("定向包名称重复");
                }
            }
        } else {
            //判断重名
            if (oConvertUtils.isNotEmpty(opJrttAudienceMapper.selectOne(wrapper))) {
                throw new JeecgBootException("定向包名称重复");
            }
        }
        JrttTokenBo accountToken = accountService.getAccountToken(opJrttAudience.getAccountId());
        OpJrttAudience addOpJrttAudience = new OpJrttAudience();
        JrttAddAudienceRequest jrttAddAudienceRequest = new JrttAddAudienceRequest();
        jrttAddAudienceRequest.setAdvertiserId(accountToken.getAdvertiserId());
        jrttAddAudienceRequest.setName(opJrttAudience.getName());
        jrttAddAudienceRequest.setDescription(opJrttAudience.getDescription());
        jrttAddAudienceRequest.setLandingType(opJrttAudience.getLandingType());
        jrttAddAudienceRequest.setDeliveryRange(opJrttAudience.getDeliveryRange());
        jrttAddAudienceRequest.setRetargetingTags(new ArrayList<>());
        if (opJrttAudience.getRetargetingTagsConf().getRetargetingTags() != null
            && !opJrttAudience.getRetargetingTagsConf().getRetargetingTags().isEmpty()) {
            jrttAddAudienceRequest.setRetargetingTags(
                opJrttAudience.getRetargetingTagsConf().getRetargetingTags());
        }
        jrttAddAudienceRequest.setRetargetingTagsExclude(new ArrayList<>());
        if (opJrttAudience.getRetargetingTagsConf().getRetargetingTagsExclude() != null
            && !opJrttAudience.getRetargetingTagsConf().getRetargetingTagsExclude().isEmpty()) {
            jrttAddAudienceRequest.setRetargetingTagsExclude(
                opJrttAudience.getRetargetingTagsConf().getRetargetingTagsExclude());
        }

        jrttAddAudienceRequest.setGender(opJrttAudience.getGender());
        addOpJrttAudience.setGender(opJrttAudience.getGender());

        jrttAddAudienceRequest.setAge(new ArrayList<>());
        addOpJrttAudience.setAge(null);
        if (opJrttAudience.getAge() != null && !opJrttAudience.getAge().isEmpty()) {
            jrttAddAudienceRequest.setAge(opJrttAudience.getAge());
            addOpJrttAudience.setAge(opJrttAudience.getAge().toString()
                .substring(1, opJrttAudience.getAge().toString().length() - 1));
        }
        addOpJrttAudience.setAndroidOsv(null);
        if (!opJrttAudience.getAndroidOsv().equals("0.0")) {
            addOpJrttAudience.setAndroidOsv(opJrttAudience.getAndroidOsv());
        }
        jrttAddAudienceRequest.setAndroidOsv(opJrttAudience.getAndroidOsv());

        addOpJrttAudience.setIosOsv(null);
        if (!opJrttAudience.getIosOsv().equals("0.0")) {
            addOpJrttAudience.setIosOsv(opJrttAudience.getIosOsv());
        }
        jrttAddAudienceRequest.setIosOsv(opJrttAudience.getIosOsv());

        jrttAddAudienceRequest.setCarrier(null);
        addOpJrttAudience.setCarrier(null);
        if (opJrttAudience.getCarrier() != null && !opJrttAudience.getCarrier().isEmpty()) {
            jrttAddAudienceRequest.setCarrier(opJrttAudience.getCarrier());
            addOpJrttAudience.setCarrier(opJrttAudience.getCarrier().toString()
                .substring(1, opJrttAudience.getCarrier().toString().length() - 1));
        }
        jrttAddAudienceRequest.setAc(new ArrayList<>());
        addOpJrttAudience.setAc(null);
        if (opJrttAudience.getAc() != null && !opJrttAudience.getAc().isEmpty()) {
            jrttAddAudienceRequest.setAc(opJrttAudience.getAc());
            addOpJrttAudience.setAc(opJrttAudience.getAc().toString()
                .substring(1, opJrttAudience.getAc().toString().length() - 1));
        }
        jrttAddAudienceRequest.setDeviceBrand(new ArrayList<>());
        addOpJrttAudience.setDeviceBrand(null);
        if (opJrttAudience.getDeviceBrand() != null && !opJrttAudience.getDeviceBrand().isEmpty()) {
            jrttAddAudienceRequest.setDeviceBrand(opJrttAudience.getDeviceBrand());
            addOpJrttAudience.setDeviceBrand(opJrttAudience.getDeviceBrand().toString()
                .substring(1, opJrttAudience.getDeviceBrand().toString().length() - 1));
        }
        jrttAddAudienceRequest.setArticleCategory(new ArrayList<>());
        addOpJrttAudience.setArticleCategory(null);
        if (opJrttAudience.getArticleCategory() != null && !opJrttAudience.getArticleCategory()
            .isEmpty()) {
            jrttAddAudienceRequest.setArticleCategory(opJrttAudience.getArticleCategory());
            addOpJrttAudience.setArticleCategory(opJrttAudience.getArticleCategory().toString()
                .substring(1, opJrttAudience.getArticleCategory().toString().length() - 1));
        }
        jrttAddAudienceRequest.setPlatform(opJrttAudience.getPlatform());
        addOpJrttAudience.setPlatform(null);
        if (opJrttAudience.getPlatform() != null && !opJrttAudience.getPlatform().isEmpty()) {
            addOpJrttAudience.setPlatform(opJrttAudience.getPlatform().toString()
                .substring(1, opJrttAudience.getPlatform().toString().length() - 1));
        }
        jrttAddAudienceRequest.setAutoExtendTargets(new ArrayList<>());
        addOpJrttAudience.setAutoExtendTargets(null);
        if (opJrttAudience.getAutoExtendTargets() != null && !opJrttAudience.getAutoExtendTargets()
            .isEmpty()) {
            jrttAddAudienceRequest.setAutoExtendTargets(opJrttAudience.getAutoExtendTargets());
            addOpJrttAudience.setAutoExtendTargets(opJrttAudience.getAutoExtendTargets().toString()
                .substring(1, opJrttAudience.getAutoExtendTargets().toString().length() - 1));
        }
        jrttAddAudienceRequest.setLaunchPrice(new ArrayList<>());
        addOpJrttAudience.setLaunchPrice(null);
        if (opJrttAudience.getLaunchPrice() != null && !opJrttAudience.getLaunchPrice().isEmpty()) {
            jrttAddAudienceRequest.setLaunchPrice(opJrttAudience.getLaunchPrice());
            addOpJrttAudience.setLaunchPrice(opJrttAudience.getLaunchPrice().toString()
                .substring(1, opJrttAudience.getLaunchPrice().toString().length() - 1));
        }
        jrttAddAudienceRequest.setActionScene(new ArrayList<>());
        if (opJrttAudience.getInterestActionConf().getActionScene() != null
            && !opJrttAudience.getInterestActionConf().getActionScene().isEmpty()) {
            jrttAddAudienceRequest.setActionScene(
                opJrttAudience.getInterestActionConf().getActionScene());
        }
        jrttAddAudienceRequest.setActionDays(
            opJrttAudience.getInterestActionConf().getActionDays());
        jrttAddAudienceRequest.setActionCategories(new ArrayList<>());
        if (opJrttAudience.getInterestActionConf().getActionCategories() != null
            && !opJrttAudience.getInterestActionConf().getActionCategories().isEmpty()) {
            jrttAddAudienceRequest.setActionCategories(
                opJrttAudience.getInterestActionConf().getActionCategories());
        }
        jrttAddAudienceRequest.setActionWords(new ArrayList<>());
        if (opJrttAudience.getInterestActionConf().getActionWords() != null
            && !opJrttAudience.getInterestActionConf().getActionWords().isEmpty()) {
            jrttAddAudienceRequest.setActionWords(
                opJrttAudience.getInterestActionConf().getActionWords());
        }
        jrttAddAudienceRequest.setInterestCategories(new ArrayList<>());
        if (opJrttAudience.getInterestActionConf().getInterestCategories() != null
            && !opJrttAudience.getInterestActionConf().getInterestCategories().isEmpty()) {
            jrttAddAudienceRequest.setInterestCategories(
                opJrttAudience.getInterestActionConf().getInterestCategories());
        }
        jrttAddAudienceRequest.setInterestWords(new ArrayList<>());
        if (opJrttAudience.getInterestActionConf().getInterestWords() != null
            && !opJrttAudience.getInterestActionConf().getInterestWords().isEmpty()) {
            jrttAddAudienceRequest.setInterestWords(
                opJrttAudience.getInterestActionConf().getInterestWords());
        }

        jrttAddAudienceRequest.setInterestActionMode(opJrttAudience.getInterestActionMode());
        jrttAddAudienceRequest.setDistrict(opJrttAudience.getDistrict());
        jrttAddAudienceRequest.setRegionVersion(
            opJrttAudience.getDistrictConf().getRegionVersion());
        jrttAddAudienceRequest.setCity(opJrttAudience.getDistrictConf().getCity());
        jrttAddAudienceRequest.setLocationType(opJrttAudience.getDistrictConf().getLocationType());
        jrttAddAudienceRequest.setFlowPackage(
            opJrttAudience.getSuperiorPopularityTypeConf().getFlowPackage());
        jrttAddAudienceRequest.setExcludeFlowPackage(
            opJrttAudience.getSuperiorPopularityTypeConf().getExcludeFlowPackage());
        jrttAddAudienceRequest.setHideIfConverted(opJrttAudience.getHideIfConverted());
        if (oConvertUtils.isNotEmpty(opJrttAudience.getConvertedTimeDuration())) {
            jrttAddAudienceRequest.setConvertedTimeDuration(
                opJrttAudience.getConvertedTimeDuration());
            addOpJrttAudience.setConvertedTimeDuration(opJrttAudience.getConvertedTimeDuration());
        }
        jrttAddAudienceRequest.setDeviceType(new ArrayList<>());
        addOpJrttAudience.setDeviceType(null);
        if (opJrttAudience.getDeviceType() != null && !opJrttAudience.getDeviceType().isEmpty()) {
            jrttAddAudienceRequest.setDeviceType(opJrttAudience.getDeviceType());
            addOpJrttAudience.setDeviceType(opJrttAudience.getDeviceType().toString()
                .substring(1, opJrttAudience.getDeviceType().toString().length() - 1));
        }
        jrttAddAudienceRequest.setAwemeFansNumbers(new ArrayList<>());
        addOpJrttAudience.setAwemeFansNumbers(null);
        if (opJrttAudience.getAwemeFansNumbers() != null && !opJrttAudience.getAwemeFansNumbers()
            .isEmpty()) {
            jrttAddAudienceRequest.setAwemeFansNumbers(opJrttAudience.getAwemeFansNumbers());
            addOpJrttAudience.setAwemeFansNumbers(opJrttAudience.getAwemeFansNumbers().toString()
                .substring(1, opJrttAudience.getAwemeFansNumbers().toString().length() - 1));
        }
        if (opJrttAudience.getFilterAwemeAbnormalActive() != 0) {
            jrttAddAudienceRequest.setFilterAwemeAbnormalActive(
                opJrttAudience.getFilterAwemeAbnormalActive());
            addOpJrttAudience.setFilterAwemeAbnormalActive(
                opJrttAudience.getFilterAwemeAbnormalActive());
        }
        if (opJrttAudience.getFilterAwemeFansCount() != 0) {
            jrttAddAudienceRequest.setFilterAwemeFansCount(
                opJrttAudience.getFilterAwemeFansCount());
            addOpJrttAudience.setFilterAwemeFansCount(opJrttAudience.getFilterAwemeFansCount());
        }
        if (opJrttAudience.getFilterOwnAwemeFans() != 0) {
            jrttAddAudienceRequest.setFilterOwnAwemeFans(opJrttAudience.getFilterOwnAwemeFans());
            addOpJrttAudience.setFilterOwnAwemeFans(opJrttAudience.getFilterOwnAwemeFans());
        }
        jrttAddAudienceRequest.setAwemeFanBehaviors(new ArrayList<>());
        if (opJrttAudience.getAwemeFanConf().getAwemeFanBehaviors() != null
            && !opJrttAudience.getAwemeFanConf().getAwemeFanBehaviors().isEmpty()) {
            jrttAddAudienceRequest.setAwemeFanBehaviors(
                opJrttAudience.getAwemeFanConf().getAwemeFanBehaviors());
        }
        jrttAddAudienceRequest.setAwemeFanAccounts(new ArrayList<>());
        if (opJrttAudience.getAwemeFanConf().getAwemeFanAccounts() != null
            && !opJrttAudience.getAwemeFanConf().getAwemeFanAccounts().isEmpty()) {
            jrttAddAudienceRequest.setAwemeFanAccounts(
                opJrttAudience.getAwemeFanConf().getAwemeFanAccounts());
        }
        jrttAddAudienceRequest.setAwemeFanCategories(new ArrayList<>());
        if (opJrttAudience.getAwemeFanConf().getAwemeFanCategories() != null
            && !opJrttAudience.getAwemeFanConf().getAwemeFanCategories().isEmpty()) {
            jrttAddAudienceRequest.setAwemeFanCategories(
                opJrttAudience.getAwemeFanConf().getAwemeFanCategories());
        }
        jrttAddAudienceRequest.setAwemeFanTimeScope(
            opJrttAudience.getAwemeFanConf().getAwemeFanTimeScope());
        jrttAddAudienceRequest.setHideIfExists(opJrttAudience.getHideIfExists());
        addOpJrttAudience.setName(opJrttAudience.getName());
        addOpJrttAudience.setDescription(opJrttAudience.getDescription());
        addOpJrttAudience.setLandingType(opJrttAudience.getLandingType());
        addOpJrttAudience.setAccountId(opJrttAudience.getAccountId());
        addOpJrttAudience.setDeliveryRange(opJrttAudience.getDeliveryRange());
        addOpJrttAudience.setDistrict(opJrttAudience.getDistrict());
        addOpJrttAudience.setRetargetingTagsConf(
            JSONObject.toJSONString(opJrttAudience.getRetargetingTagsConf()));
        addOpJrttAudience.setSuperiorPopularityType(opJrttAudience.getSuperiorPopularityType());
        addOpJrttAudience.setInterestActionMode(opJrttAudience.getInterestActionMode());
        addOpJrttAudience.setAwemeFanConf(
            JSONObject.toJSONString(opJrttAudience.getAwemeFanConf()));
        addOpJrttAudience.setHideIfExists(opJrttAudience.getHideIfExists());
        addOpJrttAudience.setInterestActionConf(
            JSONObject.toJSONString(opJrttAudience.getInterestActionConf()));
        addOpJrttAudience.setDistrictConf(
            JSONObject.toJSONString(opJrttAudience.getDistrictConf()));
        addOpJrttAudience.setSuperiorPopularityTypeConf(
            JSONObject.toJSONString(opJrttAudience.getSuperiorPopularityTypeConf()));
        addOpJrttAudience.setHideIfConverted(opJrttAudience.getHideIfConverted());
        addOpJrttAudience.setGeolocation(opJrttAudience.getGeolocation());
        addOpJrttAudience.setCreateBy(opJrttAudience.getCreateBy());
        addOpJrttAudience.setCreateTime(opJrttAudience.getCreateTime());
        JrttBaseResponse<JrttUpdateAudienceResponse> jrttBaseResponseUpdate = new JrttBaseResponse<>();
        JrttBaseResponse<JrttAddAudienceResponse> jrttBaseResponse = new JrttBaseResponse<>();
        if (opJrttAudience.getId() != null) {
            JrttUpdateAudienceRequest jrttUpdateAudienceRequest = new JrttUpdateAudienceRequest();
            BeanUtils.copyProperties(jrttAddAudienceRequest, jrttUpdateAudienceRequest);
            jrttUpdateAudienceRequest.setAudiencePackageId(opJrttAudience.getAudiencePackageId());
            jrttBaseResponseUpdate = JrttAudienceApi.updateAudience(
                accountToken.getAccessToken(), jrttUpdateAudienceRequest);
            if (!jrttBaseResponseUpdate.getCode().equals(JrttCodeConstant.OK)) {
                throw new JeecgBootException(jrttBaseResponseUpdate.getMessage());
            }
            OpJrttAudience updateOpJrttAudience = new OpJrttAudience();
            BeanUtils.copyProperties(addOpJrttAudience, updateOpJrttAudience);
            updateOpJrttAudience.setId(opJrttAudience.getId());
            updateOpJrttAudience.setAudiencePackageId(
                jrttBaseResponseUpdate.getData().getAudiencePackageId());
            opJrttAudienceMapper.updateById(updateOpJrttAudience);
        } else {
            jrttBaseResponse = JrttAudienceApi.addAudience(accountToken.getAccessToken(),
                jrttAddAudienceRequest);
            if (!jrttBaseResponse.getCode().equals(JrttCodeConstant.OK)) {
                throw new JeecgBootException(jrttBaseResponse.getMessage());
            }
            addOpJrttAudience.setAudiencePackageId(
                jrttBaseResponse.getData().getAudiencePackageId());
            opJrttAudienceMapper.insert(addOpJrttAudience);
        }
    }

    @Override
    public void shareAudiencePackage(OpJrttAudienceDto opJrttAudience) {
        List<String> list = Arrays.asList(opJrttAudience.getAccountIdList().split(","));
        List<Integer> newList = list.stream().map(Integer::parseInt).collect(Collectors.toList());
        newList.removeIf(opJrttAudience.getAccountId()::equals);
        if (!newList.isEmpty()) {
            for (Integer accountId : newList) {
                JrttTokenBo accountToken = accountService.getAccountToken(accountId);
                OpJrttAudience addOpJrttAudience = new OpJrttAudience();
                JrttAddAudienceRequest jrttAddAudienceRequest = new JrttAddAudienceRequest();
                jrttAddAudienceRequest.setAdvertiserId(accountToken.getAdvertiserId());
                jrttAddAudienceRequest.setName(opJrttAudience.getName());
                jrttAddAudienceRequest.setName("共享-" + opJrttAudience.getName());
                jrttAddAudienceRequest.setDescription(opJrttAudience.getDescription());
                jrttAddAudienceRequest.setLandingType(opJrttAudience.getLandingType());
                jrttAddAudienceRequest.setDeliveryRange(opJrttAudience.getDeliveryRange());
                jrttAddAudienceRequest.setRetargetingTags(
                    new ArrayList<>());
                jrttAddAudienceRequest.setRetargetingTagsExclude(
                    new ArrayList<>());
                if (opJrttAudience.getGender() != null && !opJrttAudience.getGender().isEmpty()) {
                    jrttAddAudienceRequest.setGender(opJrttAudience.getGender());
                    addOpJrttAudience.setGender(opJrttAudience.getGender());
                }
                if (opJrttAudience.getAge() != null && !opJrttAudience.getAge().isEmpty()) {
                    jrttAddAudienceRequest.setAge(opJrttAudience.getAge());
                    addOpJrttAudience.setAge(opJrttAudience.getAge().toString()
                        .substring(1, opJrttAudience.getAge().toString().length() - 1));
                }
                if (oConvertUtils.isNotEmpty(opJrttAudience.getAndroidOsv())) {
                    jrttAddAudienceRequest.setAndroidOsv(opJrttAudience.getAndroidOsv());
                    addOpJrttAudience.setAndroidOsv(opJrttAudience.getAndroidOsv());
                }
                if (oConvertUtils.isNotEmpty(opJrttAudience.getIosOsv())) {
                    jrttAddAudienceRequest.setIosOsv(opJrttAudience.getIosOsv());
                    addOpJrttAudience.setIosOsv(opJrttAudience.getIosOsv());
                }
                if (opJrttAudience.getCarrier() != null && !opJrttAudience.getCarrier().isEmpty()) {
                    jrttAddAudienceRequest.setCarrier(opJrttAudience.getCarrier());
                    addOpJrttAudience.setCarrier(opJrttAudience.getCarrier().toString()
                        .substring(1, opJrttAudience.getCarrier().toString().length() - 1));
                }
                if (opJrttAudience.getAc() != null && !opJrttAudience.getAc().isEmpty()) {
                    jrttAddAudienceRequest.setAc(opJrttAudience.getAc());
                    addOpJrttAudience.setAc(opJrttAudience.getAc().toString()
                        .substring(1, opJrttAudience.getAc().toString().length() - 1));
                }
                if (opJrttAudience.getDeviceBrand() != null && !opJrttAudience.getDeviceBrand()
                    .isEmpty()) {
                    jrttAddAudienceRequest.setDeviceBrand(opJrttAudience.getDeviceBrand());
                    addOpJrttAudience.setDeviceBrand(opJrttAudience.getDeviceBrand().toString()
                        .substring(1, opJrttAudience.getDeviceBrand().toString().length() - 1));
                }
                if (opJrttAudience.getArticleCategory() != null && !opJrttAudience
                    .getArticleCategory()
                    .isEmpty()) {
                    jrttAddAudienceRequest.setArticleCategory(opJrttAudience.getArticleCategory());
                    addOpJrttAudience
                        .setArticleCategory(opJrttAudience.getArticleCategory().toString()
                            .substring(1,
                                opJrttAudience.getArticleCategory().toString().length() - 1));
                }
                if (opJrttAudience.getPlatform() != null && !opJrttAudience.getPlatform()
                    .isEmpty()) {
                    jrttAddAudienceRequest.setPlatform(opJrttAudience.getPlatform());
                    addOpJrttAudience.setPlatform(opJrttAudience.getPlatform().toString()
                        .substring(1, opJrttAudience.getPlatform().toString().length() - 1));
                }
                if (opJrttAudience.getAutoExtendTargets() != null
                    && !opJrttAudience.getAutoExtendTargets()
                    .isEmpty()) {
                    jrttAddAudienceRequest
                        .setAutoExtendTargets(opJrttAudience.getAutoExtendTargets());
                    addOpJrttAudience.setAutoExtendTargets(
                        opJrttAudience.getAutoExtendTargets().toString()
                            .substring(1,
                                opJrttAudience.getAutoExtendTargets().toString().length() - 1));
                }
                if (opJrttAudience.getLaunchPrice() != null && !opJrttAudience.getLaunchPrice()
                    .isEmpty()) {
                    jrttAddAudienceRequest.setLaunchPrice(opJrttAudience.getLaunchPrice());
                    addOpJrttAudience.setLaunchPrice(opJrttAudience.getLaunchPrice().toString()
                        .substring(1, opJrttAudience.getLaunchPrice().toString().length() - 1));
                }
                jrttAddAudienceRequest.setActionScene(
                    opJrttAudience.getInterestActionConf().getActionScene());
                jrttAddAudienceRequest.setActionDays(
                    opJrttAudience.getInterestActionConf().getActionDays());
                jrttAddAudienceRequest.setActionCategories(
                    opJrttAudience.getInterestActionConf().getActionCategories());
                jrttAddAudienceRequest.setActionWords(
                    opJrttAudience.getInterestActionConf().getActionWords());
                jrttAddAudienceRequest.setInterestCategories(
                    opJrttAudience.getInterestActionConf().getInterestCategories());
                jrttAddAudienceRequest.setInterestWords(
                    opJrttAudience.getInterestActionConf().getInterestWords());
                jrttAddAudienceRequest
                    .setInterestActionMode(opJrttAudience.getInterestActionMode());
                jrttAddAudienceRequest.setDistrict(opJrttAudience.getDistrict());
                jrttAddAudienceRequest.setRegionVersion(
                    opJrttAudience.getDistrictConf().getRegionVersion());
                jrttAddAudienceRequest.setCity(opJrttAudience.getDistrictConf().getCity());
                jrttAddAudienceRequest.setLocationType(
                    opJrttAudience.getDistrictConf().getLocationType());
                jrttAddAudienceRequest.setFlowPackage(
                    opJrttAudience.getSuperiorPopularityTypeConf().getFlowPackage());
                jrttAddAudienceRequest.setExcludeFlowPackage(
                    opJrttAudience.getSuperiorPopularityTypeConf().getExcludeFlowPackage());
                jrttAddAudienceRequest.setHideIfConverted(opJrttAudience.getHideIfConverted());
                if (oConvertUtils.isNotEmpty(opJrttAudience.getConvertedTimeDuration())) {
                    jrttAddAudienceRequest.setConvertedTimeDuration(
                        opJrttAudience.getConvertedTimeDuration());
                    addOpJrttAudience.setConvertedTimeDuration(
                        opJrttAudience.getConvertedTimeDuration());
                }
                if (opJrttAudience.getDeviceType() != null && !opJrttAudience.getDeviceType()
                    .isEmpty()) {
                    jrttAddAudienceRequest.setDeviceType(opJrttAudience.getDeviceType());
                    addOpJrttAudience.setDeviceType(opJrttAudience.getDeviceType().toString()
                        .substring(1, opJrttAudience.getDeviceType().toString().length() - 1));
                }
                if (opJrttAudience.getAwemeFansNumbers() != null
                    && !opJrttAudience.getAwemeFansNumbers()
                    .isEmpty()) {
                    jrttAddAudienceRequest
                        .setAwemeFansNumbers(opJrttAudience.getAwemeFansNumbers());
                    addOpJrttAudience.setAwemeFansNumbers(
                        opJrttAudience.getAwemeFansNumbers().toString()
                            .substring(1,
                                opJrttAudience.getAwemeFansNumbers().toString().length() - 1));
                }
                if (oConvertUtils.isNotEmpty(opJrttAudience.getFilterAwemeAbnormalActive())) {
                    jrttAddAudienceRequest.setFilterAwemeAbnormalActive(
                        opJrttAudience.getFilterAwemeAbnormalActive());
                    addOpJrttAudience.setFilterAwemeAbnormalActive(
                        opJrttAudience.getFilterAwemeAbnormalActive());
                }
                if (oConvertUtils.isNotEmpty(opJrttAudience.getFilterAwemeFansCount())) {
                    jrttAddAudienceRequest.setFilterAwemeFansCount(
                        opJrttAudience.getFilterAwemeFansCount());
                    addOpJrttAudience
                        .setFilterAwemeFansCount(opJrttAudience.getFilterAwemeFansCount());
                }
                if (oConvertUtils.isNotEmpty(opJrttAudience.getFilterOwnAwemeFans())) {
                    jrttAddAudienceRequest.setFilterOwnAwemeFans(
                        opJrttAudience.getFilterOwnAwemeFans());
                    addOpJrttAudience.setFilterOwnAwemeFans(opJrttAudience.getFilterOwnAwemeFans());
                }
                jrttAddAudienceRequest.setAwemeFanBehaviors(
                    opJrttAudience.getAwemeFanConf().getAwemeFanBehaviors());
                jrttAddAudienceRequest.setAwemeFanAccounts(
                    opJrttAudience.getAwemeFanConf().getAwemeFanAccounts());
                jrttAddAudienceRequest.setAwemeFanCategories(
                    opJrttAudience.getAwemeFanConf().getAwemeFanCategories());
                jrttAddAudienceRequest.setAwemeFanTimeScope(
                    opJrttAudience.getAwemeFanConf().getAwemeFanTimeScope());
                jrttAddAudienceRequest.setHideIfExists(opJrttAudience.getHideIfExists());
                addOpJrttAudience.setName(jrttAddAudienceRequest.getName());
                addOpJrttAudience.setDescription(opJrttAudience.getDescription());
                addOpJrttAudience.setLandingType(opJrttAudience.getLandingType());
                addOpJrttAudience.setAccountId(accountId);
                addOpJrttAudience.setDeliveryRange(opJrttAudience.getDeliveryRange());
                addOpJrttAudience.setDistrict(opJrttAudience.getDistrict());
                addOpJrttAudience.setRetargetingTagsConf(
                    JSONObject.toJSONString(new Object()));
                addOpJrttAudience
                    .setSuperiorPopularityType(opJrttAudience.getSuperiorPopularityType());
                addOpJrttAudience.setInterestActionMode(opJrttAudience.getInterestActionMode());
                addOpJrttAudience.setAwemeFanConf(
                    JSONObject.toJSONString(opJrttAudience.getAwemeFanConf()));
                addOpJrttAudience.setHideIfExists(opJrttAudience.getHideIfExists());
                addOpJrttAudience.setInterestActionConf(
                    JSONObject.toJSONString(opJrttAudience.getInterestActionConf()));
                addOpJrttAudience.setDistrictConf(
                    JSONObject.toJSONString(opJrttAudience.getDistrictConf()));
                addOpJrttAudience.setSuperiorPopularityTypeConf(
                    JSONObject.toJSONString(opJrttAudience.getSuperiorPopularityTypeConf()));
                addOpJrttAudience.setHideIfConverted(opJrttAudience.getHideIfConverted());
                addOpJrttAudience.setGeolocation(opJrttAudience.getGeolocation());
                LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
                addOpJrttAudience.setCreateBy(sysUser.getUsername());
                addOpJrttAudience.setCreateTime(new Date());
                JrttBaseResponse<JrttAddAudienceResponse> jrttBaseResponse = JrttAudienceApi
                    .addAudience(
                        accountToken.getAccessToken(),
                        jrttAddAudienceRequest);
                if (!jrttBaseResponse.getCode().equals(JrttCodeConstant.OK)
                    && !jrttBaseResponse.getMessage()
                    .equals(JrttOtherConstant.JRTT_AUDIENCE_PACKAGE_RENAME)) {
                    throw new JeecgBootException(jrttBaseResponse.getMessage());
                } else if (jrttBaseResponse.getCode().equals(JrttCodeConstant.OK)) {
                    addOpJrttAudience.setAudiencePackageId(
                        jrttBaseResponse.getData().getAudiencePackageId());
                    save(addOpJrttAudience);
                }

            }
        }

    }

    @Override
    public void syncAudience(OpJrttAudienceDto opJrttAudience) {
        List<String> list = Arrays.asList(opJrttAudience.getAccountIdList().split(","));
        List<Integer> newList = list.stream().map(Integer::parseInt).collect(Collectors.toList());
        for (Integer accountId : newList) {
            JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(
                accountId);
            JrttGetAudienceRequest jrttGetAudienceRequest = new JrttGetAudienceRequest();
            jrttGetAudienceRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
            jrttGetAudienceRequest.setPage(1);
            jrttGetAudienceRequest.setPageSize(100);
            JrttBaseResponse<JrttGetAudienceResponse> jrttBaseResponse = JrttAudienceApi
                .getAudience(
                    jrttTokenBo.getAccessToken(), jrttGetAudienceRequest);
            //查询该广告主已存在的定向包
            List<Long> currentAudienceList = opJrttAudienceMapper.getAudienceIdList(
                accountId);
            List<Long> audiencePackageidList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(jrttBaseResponse.getData().getAudiencePackages())) {
                List<JrttGetAudiencePackagesResponse> getAudienceList = new ArrayList<>(
                    jrttBaseResponse.getData().getAudiencePackages());
                Integer page = 1;
                while (jrttBaseResponse.getData().getAudiencePackages().size() == 100) {
                    page++;
                    jrttGetAudienceRequest.setPage(page);
                    jrttBaseResponse = JrttAudienceApi.getAudience(jrttTokenBo.getAccessToken(),
                        jrttGetAudienceRequest);
                    if (!CollectionUtils.isEmpty(
                        jrttBaseResponse.getData().getAudiencePackages())) {
                        getAudienceList.addAll(jrttBaseResponse.getData().getAudiencePackages());
                    }
                }

                for (JrttGetAudiencePackagesResponse jrttGetAudiencePackagesResponse : getAudienceList) {

                    OpJrttAudience addOpJrttAudience = new OpJrttAudience();
                    addOpJrttAudience.setAudiencePackageId(
                        jrttGetAudiencePackagesResponse.getAudiencePackageId());
                    addOpJrttAudience.setName(jrttGetAudiencePackagesResponse.getName());
                    addOpJrttAudience.setDescription(
                        jrttGetAudiencePackagesResponse.getDescription());
                    addOpJrttAudience.setLandingType(
                        jrttGetAudiencePackagesResponse.getLandingType());
                    addOpJrttAudience.setAccountId(accountId);
                    addOpJrttAudience.setDeliveryRange(
                        jrttGetAudiencePackagesResponse.getDeliveryRange());
                    addOpJrttAudience.setDistrict(
                        jrttGetAudiencePackagesResponse.getAudience().getDistrict());
                    addOpJrttAudience.setGender(
                        jrttGetAudiencePackagesResponse.getAudience().getGender());
                    if (jrttGetAudiencePackagesResponse.getAudience().getAge() != null) {
                        addOpJrttAudience.setAge(
                            jrttGetAudiencePackagesResponse.getAudience().getAge().toString()
                                .substring(1, jrttGetAudiencePackagesResponse.getAudience().getAge()
                                    .toString().length() - 1));
                    }

                    RetargetingTagsConfConfig retargetingTagsConfConfig = new RetargetingTagsConfConfig();
                    retargetingTagsConfConfig.setRetargetingTags(
                        jrttGetAudiencePackagesResponse.getAudience().getRetargetingTags());
                    retargetingTagsConfConfig.setRetargetingTagsExclude(
                        jrttGetAudiencePackagesResponse.getAudience().getRetargetingTagsExclude());
                    addOpJrttAudience.setRetargetingTagsConf(
                        JSON.toJSONString(retargetingTagsConfConfig));

                    addOpJrttAudience.setSuperiorPopularityType(
                        jrttGetAudiencePackagesResponse.getAudience().getSuperiorPopularityType());
                    addOpJrttAudience.setInterestActionMode(
                        jrttGetAudiencePackagesResponse.getAudience().getInterestActionMode());

                    AwemeFanConfConfig awemeFanConfConfig = new AwemeFanConfConfig();
                    awemeFanConfConfig.setAwemeFanBehaviors(
                        jrttGetAudiencePackagesResponse.getAudience().getAwemeFanBehaviors());
                    awemeFanConfConfig.setAwemeFanAccounts(
                        jrttGetAudiencePackagesResponse.getAudience().getAwemeFanAccounts());
                    awemeFanConfConfig.setAwemeFanCategories(
                        jrttGetAudiencePackagesResponse.getAudience().getAwemeFanCategories());
                    awemeFanConfConfig.setAwemeFanTimeScope(
                        jrttGetAudiencePackagesResponse.getAudience().getAwemeFanTimeScope());
                    addOpJrttAudience.setAwemeFanConf(JSON.toJSONString(awemeFanConfConfig));

                    addOpJrttAudience.setFilterAwemeAbnormalActive(
                        jrttGetAudiencePackagesResponse.getAudience()
                            .getFilterAwemeAbnormalActive());
                    addOpJrttAudience.setFilterOwnAwemeFans(
                        jrttGetAudiencePackagesResponse.getAudience().getFilterOwnAwemeFans());
                    addOpJrttAudience.setFilterAwemeFansCount(
                        jrttGetAudiencePackagesResponse.getAudience().getFilterAwemeFansCount());
                    if (jrttGetAudiencePackagesResponse.getAudience().getPlatform() != null
                        && !jrttGetAudiencePackagesResponse.getAudience().getPlatform().isEmpty()) {
                        addOpJrttAudience.setPlatform(
                            jrttGetAudiencePackagesResponse.getAudience().getPlatform().toString()
                                .substring(1,
                                    jrttGetAudiencePackagesResponse.getAudience().getPlatform()
                                        .toString().length() - 1));
                    }
                    if (jrttGetAudiencePackagesResponse.getAudience().getDeviceType() != null
                        && !jrttGetAudiencePackagesResponse.getAudience().getDeviceType()
                        .isEmpty()) {
                        addOpJrttAudience.setDeviceType(
                            jrttGetAudiencePackagesResponse.getAudience().getDeviceType().toString()
                                .substring(1,
                                    jrttGetAudiencePackagesResponse.getAudience().getDeviceType()
                                        .toString().length() - 1));
                    }
                    if (jrttGetAudiencePackagesResponse.getAudience().getAc() != null
                        && !jrttGetAudiencePackagesResponse.getAudience().getAc().isEmpty()) {
                        addOpJrttAudience.setAc(
                            jrttGetAudiencePackagesResponse.getAudience().getAc().toString()
                                .substring(1,
                                    jrttGetAudiencePackagesResponse.getAudience().getAc().toString()
                                        .length() - 1));
                    }
                    addOpJrttAudience.setHideIfExists(
                        jrttGetAudiencePackagesResponse.getHideIfExists());
                    if (jrttGetAudiencePackagesResponse.getAudience().getDeviceBrand() != null
                        && !jrttGetAudiencePackagesResponse.getAudience().getDeviceBrand()
                        .isEmpty()) {
                        addOpJrttAudience.setDeviceBrand(
                            jrttGetAudiencePackagesResponse.getAudience().getDeviceBrand()
                                .toString().substring(1,
                                jrttGetAudiencePackagesResponse.getAudience().getDeviceBrand()
                                    .toString().length() - 1));
                    }
                    if (jrttGetAudiencePackagesResponse.getAudience().getLaunchPrice() != null
                        && !jrttGetAudiencePackagesResponse.getAudience().getLaunchPrice()
                        .isEmpty()) {
                        addOpJrttAudience.setLaunchPrice(
                            jrttGetAudiencePackagesResponse.getAudience().getLaunchPrice()
                                .toString().substring(1,
                                jrttGetAudiencePackagesResponse.getAudience().getLaunchPrice()
                                    .toString().length() - 1));
                    }
                    if (jrttGetAudiencePackagesResponse.getAudience().getAutoExtendTargets() != null
                        && !jrttGetAudiencePackagesResponse.getAudience().getAutoExtendTargets()
                        .isEmpty()) {
                        addOpJrttAudience.setAutoExtendTargets(
                            jrttGetAudiencePackagesResponse.getAudience().getAutoExtendTargets()
                                .toString().substring(1,
                                jrttGetAudiencePackagesResponse.getAudience().getAutoExtendTargets()
                                    .toString().length() - 1));
                    }
                    addOpJrttAudience.setIosOsv(
                        jrttGetAudiencePackagesResponse.getAudience().getIosOsv());
                    if (jrttGetAudiencePackagesResponse.getAudience().getCarrier() != null
                        && !jrttGetAudiencePackagesResponse.getAudience().getCarrier().isEmpty()) {
                        addOpJrttAudience.setCarrier(
                            jrttGetAudiencePackagesResponse.getAudience().getCarrier().toString()
                                .substring(1,
                                    jrttGetAudiencePackagesResponse.getAudience().getCarrier()
                                        .toString().length() - 1));
                    }
                    addOpJrttAudience.setAndroidOsv(
                        jrttGetAudiencePackagesResponse.getAudience().getAndroidOsv());
                    if (jrttGetAudiencePackagesResponse.getAudience().getArticleCategory() != null
                        && !jrttGetAudiencePackagesResponse.getAudience().getArticleCategory()
                        .isEmpty()) {
                        addOpJrttAudience.setArticleCategory(
                            jrttGetAudiencePackagesResponse.getAudience().getArticleCategory()
                                .toString().substring(1,
                                jrttGetAudiencePackagesResponse.getAudience().getArticleCategory()
                                    .toString().length() - 1));
                    }

                    InterestActionConfConfig interestActionConfConfig = new InterestActionConfConfig();
                    if (oConvertUtils.isNotEmpty(
                        jrttGetAudiencePackagesResponse.getAudience().getAction())) {
                        //查询jrttGetAudiencePackagesResponse.getAudience().getAction().中所含属性
                        Field[] fields = jrttGetAudiencePackagesResponse.getAudience().getAction()
                            .getClass().getDeclaredFields();
                        List<String> nameList = new ArrayList<>();
                        for (int i = 0; i < fields.length; i++) {
                            nameList.add(fields[i].getName());
                        }
                        if (nameList.contains("actionCategories")) {
                            interestActionConfConfig.setActionCategories(
                                jrttGetAudiencePackagesResponse.getAudience().getAction()
                                    .getActionCategories());
                        }
                        if (nameList.contains("actionDays")) {
                            interestActionConfConfig.setActionDays(
                                jrttGetAudiencePackagesResponse.getAudience().getAction()
                                    .getActionDays());
                        }
                        if (nameList.contains("actionWords")) {
                            interestActionConfConfig.setActionWords(
                                jrttGetAudiencePackagesResponse.getAudience().getAction()
                                    .getActionWords());
                        }
                        if (nameList.contains("actionScene")) {
                            interestActionConfConfig.setActionScene(
                                jrttGetAudiencePackagesResponse.getAudience().getAction()
                                    .getActionScene());
                        }
                    }
                    interestActionConfConfig.setInterestCategories(
                        jrttGetAudiencePackagesResponse.getAudience().getInterestCategories());
                    interestActionConfConfig.setInterestWords(
                        jrttGetAudiencePackagesResponse.getAudience().getInterestWords());
                    addOpJrttAudience.setInterestActionConf(
                        JSON.toJSONString(interestActionConfConfig));

                    DistrictConfConfig districtConfConfig = new DistrictConfConfig();
                    districtConfConfig.setRegionVersion(
                        jrttGetAudiencePackagesResponse.getAudience().getRegionVersion());
                    districtConfConfig.setCity(
                        jrttGetAudiencePackagesResponse.getAudience().getCity());
                    districtConfConfig.setLocationType(
                        jrttGetAudiencePackagesResponse.getAudience().getLocationType());
                    addOpJrttAudience.setDistrictConf(JSON.toJSONString(districtConfConfig));

                    SuperiorPopularityTypeConfConfig superiorPopularityTypeConfConfig = new SuperiorPopularityTypeConfConfig();
                    superiorPopularityTypeConfConfig.setFlowPackage(
                        jrttGetAudiencePackagesResponse.getAudience().getFlowPackage());
                    superiorPopularityTypeConfConfig.setExcludeFlowPackage(
                        jrttGetAudiencePackagesResponse.getAudience().getExcludeFlowPackage());
                    addOpJrttAudience.setSuperiorPopularityTypeConf(
                        JSON.toJSONString(superiorPopularityTypeConfConfig));

                    addOpJrttAudience.setHideIfConverted(
                        jrttGetAudiencePackagesResponse.getHideIfConverted());
                    addOpJrttAudience.setConvertedTimeDuration(
                        jrttGetAudiencePackagesResponse.getConvertedTimeDuration());
                    addOpJrttAudience.setGeolocation(null);
                    if (jrttGetAudiencePackagesResponse.getAudience().getAwemeFansNumbers() != null
                        && !jrttGetAudiencePackagesResponse.getAudience().getAwemeFansNumbers()
                        .isEmpty()) {
                        addOpJrttAudience.setAwemeFansNumbers(
                            jrttGetAudiencePackagesResponse.getAudience().getAwemeFansNumbers()
                                .toString().substring(1,
                                jrttGetAudiencePackagesResponse.getAudience().getAwemeFansNumbers()
                                    .toString().length() - 1));
                    }
                    LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
                    addOpJrttAudience.setCreateBy(sysUser.getUsername());
                    addOpJrttAudience.setCreateTime(new Date());

                    //数据库中包含的数据不包含远程数据
                    if (!currentAudienceList.contains(
                        jrttGetAudiencePackagesResponse.getAudiencePackageId())) {
                        addOpJrttAudience.setId(null);
                        opJrttAudienceMapper.insert(addOpJrttAudience);
                        audiencePackageidList.add(addOpJrttAudience.getAudiencePackageId());
                    } else {
                        OpJrttAudience updateOpJrttAudience = new OpJrttAudience();
                        BeanUtils.copyProperties(addOpJrttAudience, updateOpJrttAudience);
                        LambdaQueryWrapper<OpJrttAudience> wrapper = new LambdaQueryWrapper<>();
                        wrapper.eq(OpJrttAudience::getAudiencePackageId,
                            jrttGetAudiencePackagesResponse.getAudiencePackageId());
                        updateOpJrttAudience.setId(opJrttAudienceMapper.selectOne(wrapper).getId());
                        opJrttAudienceMapper.updateById(updateOpJrttAudience);
                        audiencePackageidList.add(updateOpJrttAudience.getAudiencePackageId());
                    }


                }
            }
            //删除本地有远程没有的数据
            for (Long audiencePackageid : currentAudienceList) {
                if (!audiencePackageidList.contains(audiencePackageid)) {
                    LambdaQueryWrapper<OpJrttAudience> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(OpJrttAudience::getAudiencePackageId, audiencePackageid);
                    opJrttAudienceMapper.deleteById(
                        opJrttAudienceMapper.selectOne(wrapper).getId());
                }
            }
        }
    }

    @Override
    public Result<List<AudienceVo>> getLocalAudienceList(Integer accountId) {
        LambdaQueryWrapper<OpJrttAudience> queryWrapper = new LambdaQueryWrapper<OpJrttAudience>()
            .select(OpJrttAudience::getAudiencePackageId, OpJrttAudience::getName,
                OpJrttAudience::getDescription)
            .eq(OpJrttAudience::getAccountId, accountId);
        List<OpJrttAudience> list = list(queryWrapper);
        List<AudienceVo> result = new LinkedList<>();
        for (OpJrttAudience item : list) {
            AudienceVo audienceVo = new AudienceVo();
            BeanUtils.copyProperties(item, audienceVo);
            result.add(audienceVo);
        }
        return Result.ok(result);
    }

}
