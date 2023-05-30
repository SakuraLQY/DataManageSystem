package org.jeecg.modules.advert.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.PackStateConstant;
import org.jeecg.common.constant.SubGameTypeConstant;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.bo.ApkComment;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpPrivacyPolicyModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.util.PackUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.util.oss.OssBootUtil;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.modules.advert.api.ks.account.bo.KsTokenBo;
import org.jeecg.modules.advert.api.ks.app.api.KsAppApi;
import org.jeecg.modules.advert.api.ks.app.bo.KsAppQueryParamReponse;
import org.jeecg.modules.advert.api.ks.app.bo.KsAppQueryReponse;
import org.jeecg.modules.advert.api.ks.app.bo.KsAppQueryRequset;
import org.jeecg.modules.advert.api.ks.app.bo.KsAppRequest;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse;
import org.jeecg.modules.advert.api.ks.campaign.api.CampaignApi;
import org.jeecg.modules.advert.api.ks.campaign.bo.CreateCampaignRequest;
import org.jeecg.modules.advert.api.ks.campaign.bo.CreateCampaignResponse;
import org.jeecg.modules.advert.api.ks.campaign.bo.ModifyBudgetRequest;
import org.jeecg.modules.advert.constant.ks.CampaignConstant;
import org.jeecg.modules.advert.constant.ks.KsAppStateConstant;
import org.jeecg.modules.advert.constant.ks.KsCodeConstant;
import org.jeecg.modules.advert.constant.ks.KsSubGameTypeConstant;
import org.jeecg.modules.advert.constant.ks.KsUrlConstant;
import org.jeecg.modules.advert.dto.OpKuaishouDealPlanDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpKsPutAccount;
import org.jeecg.modules.advert.entity.OpKuaishouDealPlan;
import org.jeecg.modules.advert.mapper.OpKuaishouDealPlanMapper;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.modules.advert.service.IOpKsPutAccountService;
import org.jeecg.modules.advert.service.IOpKuaishouDealPlanService;
import org.jeecg.modules.advert.vo.MessageVo;
import org.jeecg.modules.advert.vo.OpKuaishouDealPlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: op_kuaishou_deal
 * @Author: jeecg-boot
 * @Date: 2023-03-09
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_gateway")
public class OpKuaishouDealPlanServiceImpl extends
    ServiceImpl<OpKuaishouDealPlanMapper, OpKuaishouDealPlan> implements
    IOpKuaishouDealPlanService {

    @Resource
    private IOpDealService opDealService;
    @Resource
    private IOpKsPutAccountService opKsPutAccountService;
    @Autowired
    private IGameApi iGameApi;
    @Autowired
    private JeecgBaseConfig jeecgBaseConfig;

    @Override
    public IPage<OpKuaishouDealPlanVo> getByPage(Page<OpKuaishouDealPlan> page, OpDeal deal,
        Integer campaignId, String startDate, String endDate) {
        QueryWrapper<OpDeal> wrapper = opDealService.baseQuery(deal, startDate, endDate);
        wrapper.eq(oConvertUtils.isNotEmpty(campaignId), "okdp.campaign_id", campaignId);
        return baseMapper.dealPlanInfoPage(page, wrapper);
    }

    @Override
    @Transactional
    public List<MessageVo> addDeal(OpKuaishouDealPlanDto opKuaishouDealDto) {
        // 返回数据
        List<MessageVo> list = new ArrayList<>();
        List<OpDeal> dealList = opDealService.addDeal(opKuaishouDealDto, ChannelConstant.KUAI_SHOU,
            KsUrlConstant.IOS_TRACK_URL, KsUrlConstant.ANDROID_TRACK_URL);
        for (OpDeal deal : dealList) {
            Integer dealId = deal.getId();
            OpKuaishouDealPlan kuaishouDeal = new OpKuaishouDealPlan();
            kuaishouDeal.setDealId(dealId);
            kuaishouDeal.setBudgetMode(opKuaishouDealDto.getBudgetMode());
            // 对分日预算进行处理
            if (CampaignConstant.WEEK.equals(opKuaishouDealDto.getBudgetMode())) {
                kuaishouDeal.setBudget(opKuaishouDealDto.getBudgetWeek());
            } else {
                kuaishouDeal.setBudget(opKuaishouDealDto.getBudget().toString());
            }
            // 插入快手广告计划
            if (!save(kuaishouDeal)) {
                // 手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                list.add(new MessageVo(null, "创建本地投放广告失败", 500));
            }
            try {
                MessageVo messageVo = createCampaign(dealId, deal.getDealName(),
                    deal.getAccountId(), opKuaishouDealDto);
                list.add(messageVo);
            } catch (Exception e) {
                list.add(new MessageVo(dealId, e.getMessage(), 500));
            }
        }
        return list;
    }

    @Override
    public void updateDeal(OpKuaishouDealPlanDto opKuaishouDealDto) {
        Integer dealId = opKuaishouDealDto.getId();
        String campaignName = opKuaishouDealDto.getDealName();
        Integer account = Integer.valueOf(opKuaishouDealDto.getAccountIds());
        OpDeal deal = opDealService.getById(dealId);
        OpKuaishouDealPlan opKuaishouDealPlan = getOne(
            new LambdaQueryWrapper<OpKuaishouDealPlan>().eq(OpKuaishouDealPlan::getDealId, dealId));

        if (oConvertUtils.isEmpty(deal)) {
            throw new JeecgBootException("该广告不存在");
        }
        if (oConvertUtils.isEmpty(opKuaishouDealPlan)) {
            throw new JeecgBootException("该广告计划不存在");
        }

        opDealService.updateDeal(opKuaishouDealDto);

        updateBudget(dealId, opKuaishouDealDto);

        // 如果没有快手广告计划ID或者更换了投放账号，需要创建快手广告计划
        if (oConvertUtils.isEmpty(opKuaishouDealPlan.getCampaignId()) || !deal.getAccountId()
            .equals(account)) {
            MessageVo messageVo = createCampaign(dealId, campaignName, account, opKuaishouDealDto);
            if (messageVo.getStatus() == 500) {
                throw new JeecgBootException(messageVo.getMessage());
            }
        }
    }

    @Override
    public void makeUpDealPlan(OpKuaishouDealPlanDto opKuaishouDealPlanDto) {
        Integer dealId = opKuaishouDealPlanDto.getId();
        OpDeal opDeal = opDealService.getOne(
            new LambdaQueryWrapper<OpDeal>().eq(OpDeal::getId, dealId));
        if (oConvertUtils.isEmpty(opDeal)) {
            throw new JeecgBootException("对应的广告不存在");
        }
        OpKuaishouDealPlan opKuaishouDealPlan = getOne(
            new LambdaQueryWrapper<OpKuaishouDealPlan>().eq(OpKuaishouDealPlan::getDealId, dealId));
        if (oConvertUtils.isEmpty(opKuaishouDealPlan)) {
            throw new JeecgBootException("选择的广告计划不存在");
        }

        updateBudget(dealId, opKuaishouDealPlanDto);

        MessageVo messageVo = createCampaign(dealId, opKuaishouDealPlanDto.getDealName(),
            opDeal.getAccountId(), opKuaishouDealPlanDto);
        if (messageVo.getStatus() == 500) {
            throw new JeecgBootException(messageVo.getMessage());
        }
    }

    @Override
    public void modifyBudget(OpKuaishouDealPlanDto opKuaishouDealPlanDto) {
        Integer dealId = opKuaishouDealPlanDto.getId();
        OpDeal deal = opDealService.getById(dealId);
        if (oConvertUtils.isEmpty(deal)) {
            throw new JeecgBootException("该广告不存在");
        }
        OpKuaishouDealPlan opKuaishouDealPlan = getOne(
            new LambdaQueryWrapper<OpKuaishouDealPlan>().eq(OpKuaishouDealPlan::getDealId, dealId));
        if (oConvertUtils.isEmpty(opKuaishouDealPlan)) {
            throw new JeecgBootException("该广告计划不存在");
        }
        if (oConvertUtils.isEmpty(opKuaishouDealPlan.getCampaignId())) {
            throw new JeecgBootException("该广告计划不存在快手计划ID");
        }

        updateBudget(dealId, opKuaishouDealPlanDto);

        KsTokenBo tokenBo = opKsPutAccountService.getAccountToken(deal.getAccountId());
        ModifyBudgetRequest request = new ModifyBudgetRequest();
        request.setAdvertiserId(tokenBo.getAdvertiserId());
        request.setCampaignId(Long.valueOf(opKuaishouDealPlan.getCampaignId()));
        if (CampaignConstant.DAY.equals(opKuaishouDealPlanDto.getBudgetMode())) {
            // 单位: 厘
            Long budget = opKuaishouDealPlanDto.getBudget() * 1000;
            request.setDayBudget(budget);
        } else if (CampaignConstant.WEEK.equals(opKuaishouDealPlanDto.getBudgetMode())) {
            String[] budgetWeek = opKuaishouDealPlanDto.getBudgetWeek().split(",");
            Long[] budgetWeekArr = new Long[budgetWeek.length];
            for (int i = 0; i < budgetWeek.length; i++) {
                budgetWeekArr[i] = Long.parseLong(budgetWeek[i]) * 1000;
            }
            request.setDayBudgetSchedule(budgetWeekArr);
        }
        KsBaseResponse<T> response = CampaignApi.modifyBudget(tokenBo.getAccessToken(), request);
        if (!KsCodeConstant.OK.equals(response.getCode())) {
            log.error("更新快手广告计划预算失败！广告ID:{},投放账号:{},请求参数:{},返回参数:{}", dealId, deal.getAccountId(),
                request, response);
        }
    }

    /**
     * @param dealId:                广告ID
     * @param opKuaishouDealPlanDto: 预算信息
     * @author xmh
     * @description 更新本地计划预算
     * @date 2023/3/16 15:17
     */
    private void updateBudget(Integer dealId, OpKuaishouDealPlanDto opKuaishouDealPlanDto) {
        // 更新本地计划预算
        LambdaUpdateWrapper<OpKuaishouDealPlan> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(OpKuaishouDealPlan::getBudgetMode, opKuaishouDealPlanDto.getBudgetMode());
        // 对分日预算进行处理
        if (CampaignConstant.WEEK.equals(opKuaishouDealPlanDto.getBudgetMode())) {
            wrapper.set(OpKuaishouDealPlan::getBudget, opKuaishouDealPlanDto.getBudgetWeek());
        } else {
            wrapper.set(OpKuaishouDealPlan::getBudget, opKuaishouDealPlanDto.getBudget());
        }
        wrapper.eq(OpKuaishouDealPlan::getDealId, dealId);
        update(wrapper);
    }

    @Override
    public void deleteDeal(List<String> list) {
        opDealService.removeByIds(list);
        LambdaQueryWrapper<OpKuaishouDealPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(OpKuaishouDealPlan::getDealId, list);
        remove(wrapper);
    }

    @Override
    public void deleteDeal(String id) {
        opDealService.removeById(id);
        LambdaQueryWrapper<OpKuaishouDealPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpKuaishouDealPlan::getDealId, id);
        remove(wrapper);
    }

    private MessageVo createCampaign(Integer dealId, String campaignName, Integer account,
        OpKuaishouDealPlanDto opKuaishouDealPlanDto) {
        KsTokenBo tokenBo = opKsPutAccountService.getAccountToken(account);
        CreateCampaignRequest request = new CreateCampaignRequest();
        request.setAdvertiserId(tokenBo.getAdvertiserId());
        request.setCampaignName(campaignName);
        request.setType(opKuaishouDealPlanDto.getType());
        request.setBidType(opKuaishouDealPlanDto.getBidType());
        if (CampaignConstant.DAY.equals(opKuaishouDealPlanDto.getBudgetMode())) {
            // 单位: 厘
            Long budget = opKuaishouDealPlanDto.getBudget() * 1000;
            request.setDayBudget(budget);
        } else if (CampaignConstant.WEEK.equals(opKuaishouDealPlanDto.getBudgetMode())) {
            String[] budgetWeek = opKuaishouDealPlanDto.getBudgetWeek().split(",");
            Long[] budgetWeekArr = new Long[budgetWeek.length];
            for (int i = 0; i < budgetWeek.length; i++) {
                budgetWeekArr[i] = Long.parseLong(budgetWeek[i]) * 1000;
            }
            request.setDayBudgetSchedule(budgetWeekArr);
        }
        KsBaseResponse<CreateCampaignResponse> response = CampaignApi.createCampaign(
            tokenBo.getAccessToken(), request);
        if (KsCodeConstant.OK.equals(response.getCode())) {
            CreateCampaignResponse campaignResponse = response.getData();
            // 更新广告计划ID
            LambdaUpdateWrapper<OpKuaishouDealPlan> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(OpKuaishouDealPlan::getCampaignId, campaignResponse.getCampaignId());
            updateWrapper.eq(OpKuaishouDealPlan::getDealId, dealId);
            update(updateWrapper);
            // 更新广告名
            LambdaUpdateWrapper<OpDeal> dealUpdateWrapper = new LambdaUpdateWrapper<>();
            dealUpdateWrapper.set(OpDeal::getDealName, campaignName);
            dealUpdateWrapper.eq(OpDeal::getId, dealId);
            opDealService.update(dealUpdateWrapper);
        } else {
            log.error("快手创建广告计划出错！广告ID:{},投放账号:{},请求数据:{},返回数据:{}", dealId, account, request,
                response);
            return new MessageVo(dealId, response.getMessage(), 500);
        }
        return new MessageVo(dealId, "创建成功", 200);
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void packAllDeal(){


        LambdaQueryWrapper<OpDeal> opdealLam = new LambdaQueryWrapper<>();
        opdealLam.eq(OpDeal::getPackState, PackStateConstant.INIT);
        List<OpDeal> opDeals = opDealService.list(opdealLam);

        for (OpDeal opDeal : opDeals) {

            Integer pkgId = opDeal.getPkgId();

            //查询advertiser_id
            LambdaQueryWrapper<OpKsPutAccount> opKsPutAccountLambdaQueryWrapper = new LambdaQueryWrapper<>();
            opKsPutAccountLambdaQueryWrapper.eq(OpKsPutAccount::getAccountId,opDeal.getAccountId());
            OpKsPutAccount opKsPutAccount = opKsPutAccountService.getOne(opKsPutAccountLambdaQueryWrapper);
            Long advertiserId = opKsPutAccount.getAdvertiserId();

            //app_version
            String appVersion = opDeal.getDealName();

            //查询app_name
            OpPkgModel opPkg = iGameApi.getOpPkgById(opDeal.getPkgId());
            String appName = opPkg.getPkgName();

            //查询package_name
            String packageName = opPkg.getPackageName();

            //查询platform
            OpSubGameModel opSubGame = iGameApi.getOpSubGame(opDeal.getSubGameId());
            Integer platform = opSubGame.getGameType();
            //数据库里的0,1对应需要上传字段的1,3
            if (platform== SubGameTypeConstant.ANDROID){
                platform= KsSubGameTypeConstant.ANDROID;
            }else if (platform==SubGameTypeConstant.IOS){
                platform=KsSubGameTypeConstant.IOS;
            }

            //查询app_privacy_url
//            OpPrivacyPolicyModel opPrivacy = iGameApi.getOpPrivacyId(opPkg.getPrivacyPolicyId());
            String appPrivacyUrl = opPkg.getPrivacyPolicyUrl();


            //查找token
            KsTokenBo ksTokenBo = opKsPutAccountService.getAccountToken(opDeal.getAccountId());
            String accessToken = ksTokenBo.getAccessToken();

            String file=jeecgBaseConfig.getPath().pkgFolderPath()+ File.separator+
                pkgId
                + ".apk";
            File pkgFile = new File(file);
            if (ObjectUtils.isNotEmpty(pkgFile)) {

                String newPathPkg =jeecgBaseConfig.getPath().getUpload()+ File.separator+
                    jeecgBaseConfig.getPath().getPkgDealFolder() + File.separator + opDeal.getId()
                    + ".apk";

                copyFile(file,newPathPkg);

                ApkComment apkComment = new ApkComment();
                apkComment.setPkgId(pkgId);
                apkComment.setDealId(opDeal.getId());
                boolean result = PackUtil
                    .setApkComment(newPathPkg, JSONObject.toJSONString(apkComment));
                if (!result){
                    log.error("打包失败,写入广告id错误，打包的文件为："+opDeal.getId()+".apk");
                    break;
                }

                //上传oss
                //修改打包状态--打包中
                opDeal.setPackState(PackStateConstant.PROCESSING);
                opDealService.updateById(opDeal);

                if (StringUtils.isEmpty(opDeal.getPkgUrl())){
                    try{
                        File uploadFile = new File(newPathPkg);
                        FileInputStream fileInputStream = new FileInputStream(uploadFile);
                        MultipartFile multipartFile = new MockMultipartFile(uploadFile.getName(),uploadFile.getName(),null,fileInputStream);
                        String uploadPath=
                            OssBootUtil.getPrivacyPolicyUrl()+ SymbolConstant.SINGLE_SLASH+jeecgBaseConfig.getPath().getPkgDealFolder();
                        String ossUrl = OssBootUtil.upload(multipartFile, uploadPath);

                        //失败修改打包字段--打包失败
                        if (ossUrl==null){
                            opDeal.setPackState(PackStateConstant.FAILURE);
                            opDealService.updateById(opDeal);
                            return;
                        }

                        opDeal.setPkgUrl(ossUrl);
                        opDealService.updateById(opDeal);
                    }catch (IOException  e){
                        log.error("文件读写异常");
                        e.printStackTrace();
                    } catch (Exception e) {
                        log.error("打包文件上传oss失败，打包文件为："+opDeal.getId()+".apk");
                        e.printStackTrace();
                    }
                }

                KsAppRequest ksAppRequest = new KsAppRequest();

                FileSystemResource fileSystemResource = new FileSystemResource(newPathPkg);

                ksAppRequest.setAdvertiserId(advertiserId);
                ksAppRequest.setFile(fileSystemResource);

                ksAppRequest.setAppVersion(appVersion);
                ksAppRequest.setAppName(appName);
                //TODO image_token字段未定义，无法进行表查询
                ksAppRequest.setImageToken("market4021ade73bfa4a089f0226c9a6678058.jpg");
                if (platform==1){
                    ksAppRequest.setPackageName(packageName);
                }
                ksAppRequest.setPlatform(platform);
                ksAppRequest.setAppPrivacyUrl(appPrivacyUrl);

                //对快手接口多次尝试
                Integer check=2;
                try{
                    while (check!=0){
                        KsBaseResponse<JSONObject> app = KsAppApi
                            .createApp(accessToken, ksAppRequest);

                        //请求失败
                        //失败修改打包字段--打包失败
                        if (!app.getMessage().equals("OK")){
                            opDeal.setPackState(PackStateConstant.FAILURE);
                            opDealService.updateById(opDeal);
                            check--;
                        }else {
                            break;
                        }
                    }
                }catch (Exception e){
                    opDeal.setPackState(PackStateConstant.FAILURE);
                    opDealService.updateById(opDeal);
                    e.printStackTrace();
                    continue;
                }

                if (check==0){
                    continue;
                }

                opDeal.setPackState(PackStateConstant.SUCCESS);
                opDealService.updateById(opDeal);


            }

        }
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void updateState(){

        //测试获取应用列表
        //查找token

        LambdaQueryWrapper<OpDeal> opdealLam = new LambdaQueryWrapper<>();
        opdealLam.eq(OpDeal::getPackState, PackStateConstant.SUCCESS);
        opdealLam.eq(OpDeal::getChannelId, ChannelConstant.KUAI_SHOU);
        List<OpDeal> opDeals = opDealService.list(opdealLam);

        for (OpDeal opDeal : opDeals) {
            KsTokenBo ksTokenBo = opKsPutAccountService.getAccountToken(opDeal.getAccountId());
            String accessToken = ksTokenBo.getAccessToken();

            LambdaQueryWrapper<OpKsPutAccount> opKsPutAccountLambdaQueryWrapper = new LambdaQueryWrapper<>();
            opKsPutAccountLambdaQueryWrapper.eq(OpKsPutAccount::getAccountId,opDeal.getAccountId());
            OpKsPutAccount opKsPutAccount = opKsPutAccountService.getOne(opKsPutAccountLambdaQueryWrapper);


            KsAppQueryRequset ksAppQueryRequset = new KsAppQueryRequset();
            ksAppQueryRequset.setAdvertiserId(opKsPutAccount.getAdvertiserId());
            ksAppQueryRequset.setPage(1);
            ksAppQueryRequset.setPageSize(100);

            System.out.println(System.currentTimeMillis());
            KsBaseResponse<KsAppQueryReponse> ksAppQueryReponseKsBaseResponse = KsAppApi
                .queryAppList(accessToken, ksAppQueryRequset);

            System.out.println(System.currentTimeMillis());

            ArrayList<KsAppQueryParamReponse> arrayList = new ArrayList<>();

            List<KsAppQueryParamReponse> ksAppQueryParamReponseList = ksAppQueryReponseKsBaseResponse
                .getData().getKsAppQueryParamReponseList();

            if (!CollectionUtils.isEmpty(ksAppQueryParamReponseList)){

                arrayList.addAll(ksAppQueryParamReponseList);

                Integer i=1;
                while (ksAppQueryParamReponseList.size()==100){

                    i++;
                    ksAppQueryRequset.setPageSize(i);

                    ksAppQueryReponseKsBaseResponse = KsAppApi
                        .queryAppList(accessToken, ksAppQueryRequset);

                    ksAppQueryParamReponseList = ksAppQueryReponseKsBaseResponse
                        .getData().getKsAppQueryParamReponseList();

                    arrayList.addAll(ksAppQueryParamReponseList);
                }
            }


            for (KsAppQueryParamReponse ks : arrayList) {
                String appVersion = opDeal.getDealName();
                if (ks.getAppVersion().equals(appVersion)){

                    if (ks.getScanStatus()== KsAppStateConstant.KS_APP_SATET_SUCCESS){
                        opDeal.setPackState(PackStateConstant.ANALYSIS_SUCCESS);
                        opDealService.updateById(opDeal);
                    }else if (ks.getScanStatus()== KsAppStateConstant.KS_APP_SATET_FAILURE){
                        opDeal.setPackState(PackStateConstant.ANALYSIS_FALURE);
                        opDealService.updateById(opDeal);
                    }
                    System.out.println(System.currentTimeMillis());
                    break;
                }
            }
        }
    }


    public void copyFile(String strOldpath, String strNewPath) {
        try {

            File fOldFile = new File(strOldpath);
            if (fOldFile.exists()) {
                int byteread = 0;
                InputStream inputStream = new FileInputStream(fOldFile);
                FileOutputStream fileOutputStream = new FileOutputStream(strNewPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, byteread);
                }
                inputStream.close();
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.error("复制文件出错,文件路径为："+strOldpath);
            e.printStackTrace();
        }
    }

    @Override
    public void packDeal(Integer id){

        LambdaQueryWrapper<OpDeal> opDealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        opDealLambdaQueryWrapper.eq(OpDeal::getId, id);
        OpDeal opDeal = opDealService.getOne(opDealLambdaQueryWrapper);

        opDeal.setPackState(PackStateConstant.INIT);
        opDealService.updateById(opDeal);

    }

}
