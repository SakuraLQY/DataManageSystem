package org.jeecg.modules.advert.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.JsonObject;
import io.swagger.util.Json;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.sound.midi.Track;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.RestUtil;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.project.bo.Audience;
import org.jeecg.modules.advert.api.jrtt.project.bo.CreateProjectRequest;
import org.jeecg.modules.advert.api.jrtt.project.bo.CreateProjectResponse;
import org.jeecg.modules.advert.api.jrtt.project.bo.DeliveryRange;
import org.jeecg.modules.advert.api.jrtt.project.bo.DeliverySetting;
import org.jeecg.modules.advert.api.jrtt.project.bo.Keyword;
import org.jeecg.modules.advert.api.jrtt.project.bo.OptimizeGoal;
import org.jeecg.modules.advert.api.jrtt.project.bo.TrackUrlSetting;
import org.jeecg.modules.advert.api.jrtt.project.bo.UpdateProjectRequest;
import org.jeecg.modules.advert.api.jrtt.project.bo.UpdateProjectResponse;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpJrttAssets;
import org.jeecg.modules.advert.entity.OpJrttProject;
import org.jeecg.modules.advert.entity.OpJrttPutAccount;
import org.jeecg.modules.advert.mapper.OpDealMapper;
import org.jeecg.modules.advert.mapper.OpJrttProjectMapper;
import org.jeecg.modules.advert.service.IOpJrttAssetsService;
import org.jeecg.modules.advert.service.IOpJrttEventsService;
import org.jeecg.modules.advert.service.IOpJrttProjectService;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.vo.CreateProjectRspVo;
import org.jeecg.modules.advert.vo.OpJrttProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_jrtt_project
 * @Author: jeecg-boot
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpJrttProjectServiceImpl extends
    ServiceImpl<OpJrttProjectMapper, OpJrttProject> implements
    IOpJrttProjectService {

    private static final String createProjectUrl = "https://api.oceanengine.com/open_api/v3.0/project/create/";

    public static final String updateProjectUrl = "https://api.oceanengine.com/open_api/v3.0/project/update/";

    @Autowired
    private IOpJrttPutAccountService opJrttPutAccountService;

    @Autowired
    private IOpJrttAssetsService assetsService;

    @Autowired
    private OpDealMapper dealMapper;

    @Override
    public Result<List<CreateProjectRspVo>> batchCreateProject(OpJrttProjectVo opJrttProjectVo) {
        List<Integer> dealIds = opJrttProjectVo.getDealIds();
        OpJrttProject opJrttProject = opJrttProjectVo.getOpJrttProject();
        List<CreateProjectRspVo> results = new LinkedList<>();
        List<OpDeal> opDeals = dealMapper.selectBatchIds(dealIds);
        checkCreateCondition(opDeals,results);
        for (OpDeal deal : opDeals) {
            JrttBaseRequest request = createParams(deal, opJrttProject);
            JrttBaseResponse response = JrttBaseApi.post(request, CreateProjectResponse.class);
            if(response.getCode() == 0){
                opJrttProject.setDealId(deal.getId());
                CreateProjectResponse data = (CreateProjectResponse)response.getData();
                opJrttProject.setProjectId(data.getProjectId());

                save(opJrttProject);
                //mybatis添加成功后回填ID，导致插入数据库，报重复主键插入失败，重新设null，自增
                opJrttProject.setId(null);
            }
            results.add(
                new CreateProjectRspVo(deal.getId(), response.getCode(), response.getMessage()));
        }
        return Result.ok(results);
    }

    @Override
    public Result<UpdateProjectResponse> updateProject(OpJrttProject opJrttProject) {
        OpDeal deal = dealMapper.selectById(opJrttProject.getDealId());
        JrttBaseRequest updateParams = createUpdateParams(opJrttProject, deal);
        JrttBaseResponse<UpdateProjectResponse> result = JrttBaseApi.post(updateParams, UpdateProjectResponse.class);
        if (result.getCode() == 0){
            updateById(opJrttProject);
            return Result.ok(result.getData());
        }else {
            return Result.error(result.getMessage());
        }
    }

    private JrttBaseRequest createUpdateParams(OpJrttProject opJrttProject, OpDeal deal) {
        JrttTokenBo accountToken = opJrttPutAccountService.getAccountToken(deal.getAccountId());
        UpdateProjectRequest updateProjectRequest = new UpdateProjectRequest();

        updateProjectRequest.setAdvertiserId(opJrttProject.getAdvertiserId());
        updateProjectRequest.setProjectId(opJrttProject.getProjectId());
        updateProjectRequest.setName(deal.getDealName() + "-" + opJrttProject.getName());
        updateProjectRequest.setAudienceExtend(opJrttProject.getAudienceExtend());
        updateProjectRequest.setKeywords(JSON.parseObject(opJrttProject.getKeywords(),Keyword[].class));
        updateProjectRequest.setDownloadMode(opJrttProject.getDownloadMode());
        updateProjectRequest.setAudience(JSON.parseObject(opJrttProject.getAudience(),Audience.class));

        DeliverySetting deliverySetting = JSON
            .parseObject(opJrttProject.getDeliverySetting(), DeliverySetting.class);
        String[] ignoreFields = new String[]{
            "startTime","deepBidType","bidType","bidSpeed","pricing","budgetOptimizeSwitch"
        };
        DeliverySetting targetDeliverySetting  = new DeliverySetting();
        BeanUtils.copyProperties(deliverySetting,targetDeliverySetting,ignoreFields);
        updateProjectRequest.setDeliverySetting(targetDeliverySetting);
        updateProjectRequest.setTrackUrlSetting(JSON.parseObject(opJrttProject.getTrackUrlSetting(),TrackUrlSetting.class));

        return new JrttBaseRequest(accountToken.getAccessToken(),updateProjectUrl,updateProjectRequest);
    }

    /**
     * @param opDeals: 广告
     * @param results: 返回结果
     * @return void
     * @author
     * @description
     * @date 2023/2/21 11:39
     */
    private void checkCreateCondition(List<OpDeal> opDeals,List<CreateProjectRspVo> results){
        Iterator<OpDeal> iterator = opDeals.iterator();
        while (iterator.hasNext()){
            OpDeal deal = iterator.next();
            OpJrttAssets asset = assetsService.getOne(new LambdaQueryWrapper<OpJrttAssets>()
                .eq(OpJrttAssets::getAccountId, deal.getAccountId())
                .eq(OpJrttAssets::getPkgId, deal.getPkgId()));
            OpJrttProject project = getOne(
                new LambdaQueryWrapper<OpJrttProject>().eq(OpJrttProject::getDealId, deal.getId()));
            if (project != null || asset == null) {
                if(project != null){
                    results.add(new CreateProjectRspVo(deal.getId(),-2, "此广告已创建项目"));
                }else {
                    results.add(new CreateProjectRspVo(deal.getId(), -1, "该账户没有没有资产，请先创建资产!"));
                }
                iterator.remove();
            }
        }
    }
    /**
     * @param deal: 广告
     * @param opJrttProject: 请求对象
     * @return JrttBaseRequest
     * @author
     * @description 构造请求对象
     * @date 2023/2/21 11:39
     */
    private JrttBaseRequest createParams(OpDeal deal, OpJrttProject opJrttProject) {
        Integer accountId = deal.getAccountId();

        //获取token
        JrttTokenBo accountToken = opJrttPutAccountService.getAccountToken(accountId);

        opJrttProject.setDownloadUrl(deal.getPkgUrl());
        opJrttProject.setAdvertiserId(accountToken.getAdvertiserId());
        opJrttProject.setName(deal.getDealName() + "-" + opJrttProject.getName());

        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        String[] ignoreFields = new String[]{"optimizeGoal", "keywords", "trackUrlSetting",
            "deliverySetting", "deliveryRange"};
        BeanUtils.copyProperties(opJrttProject, createProjectRequest, ignoreFields);
        //设置优化目标
        OptimizeGoal optimizeGoal = JSON
            .parseObject(opJrttProject.getOptimizeGoal(), OptimizeGoal.class);
        //设置资产ID
        OpJrttAssets asset = assetsService.getOne(new LambdaQueryWrapper<OpJrttAssets>()
            .eq(OpJrttAssets::getAccountId, deal.getAccountId())
            .eq(OpJrttAssets::getPkgId, deal.getPkgId()));
        LinkedList<Long> assets = new LinkedList<>();
        assets.add(asset.getAssetId());
        optimizeGoal.setAssetIds(assets);
        createProjectRequest.setOptimizeGoal(optimizeGoal);

        //设置关键字
        Keyword[] keywords = JSON.parseObject(opJrttProject.getKeywords(), Keyword[].class);
        createProjectRequest.setKeywords(keywords);
        //设置链接
        TrackUrlSetting trackUrlSetting = JSON
            .parseObject(opJrttProject.getTrackUrlSetting(), TrackUrlSetting.class);
        createProjectRequest.setTrackUrlSetting(trackUrlSetting);
        //设置排期、预算与出价
        DeliverySetting deliverySetting = JSON
            .parseObject(opJrttProject.getDeliverySetting(), DeliverySetting.class);
        createProjectRequest.setDeliverySetting(deliverySetting);
        //设置广告版位
        DeliveryRange deliveryRange = JSON
            .parseObject(opJrttProject.getDeliveryRange(), DeliveryRange.class);
        createProjectRequest.setDeliveryRange(deliveryRange);

        //设置定向包
        Audience audience = JSON.parseObject(opJrttProject.getAudience(), Audience.class);
        createProjectRequest.setAudience(audience);
        return new JrttBaseRequest(accountToken.getAccessToken(), createProjectUrl,
            createProjectRequest);
    }


}
