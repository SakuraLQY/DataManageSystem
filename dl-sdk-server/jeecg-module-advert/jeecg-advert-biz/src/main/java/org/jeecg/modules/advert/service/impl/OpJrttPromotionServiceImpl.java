package org.jeecg.modules.advert.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.SubGameTypeConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.util.FileUtil;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.material.api.JrttMaterialApi;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttImageAdResponse;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttImageAdRequest;
import org.jeecg.modules.advert.api.jrtt.promotion.api.JrttPromotionApi;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionCreateRequest;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionCreateResponse;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionUpdateRequest;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionUpdateResponse;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.NativeSetting;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.ProductInfo;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.PromotionMaterials;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.TitleMaterial;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.VideoMaterial;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttDownloadType;
import org.jeecg.modules.advert.constant.jrtt.JrttImageUploadType;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttVideoImageModeConstant;
import org.jeecg.modules.advert.constant.jrtt.PromotionIdentityConstant;
import org.jeecg.modules.advert.dto.OpJrttPromotionDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpJrttProject;
import org.jeecg.modules.advert.entity.OpJrttPromotion;
import org.jeecg.modules.advert.entity.OpMaterial;
import org.jeecg.modules.advert.mapper.OpJrttPromotionMapper;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.modules.advert.service.IOpJrttProjectService;
import org.jeecg.modules.advert.service.IOpJrttPromotionService;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.service.IOpMaterialService;
import org.jeecg.modules.advert.util.ImageBase64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_jrtt_promotion
 * @Author: jeecg-boot
 * @Date: 2023-02-22
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpJrttPromotionServiceImpl extends
    ServiceImpl<OpJrttPromotionMapper, OpJrttPromotion> implements
    IOpJrttPromotionService {

    @Autowired
    private IOpJrttPutAccountService opJrttPutAccountService;
    @Autowired
    private IOpDealService opDealService;
    @Autowired
    private IOpJrttProjectService opJrttProjectService;
    @Autowired
    private IOpMaterialService opMaterialService;
    @Autowired
    private IGameApi gameApi;

    @Override
    public void saveOpJrttPromotion(OpJrttPromotionDto opJrttPromotionDto) {
        OpDeal opDeal = opDealService.getById(opJrttPromotionDto.getDealId());
        if (null == opDeal) {
            throw new JeecgBootException("广告不存在");
        }
        JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(opDeal.getAccountId());
        JrttPromotionCreateRequest jrttPromotionCreateRequest = new JrttPromotionCreateRequest();
        jrttPromotionCreateRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
        OpJrttProject opJrttProject = opJrttProjectService.getOne(
            new LambdaQueryWrapper<OpJrttProject>().eq(OpJrttProject::getDealId,
                opJrttPromotionDto.getDealId()));
        if (StringUtils.isBlank(opJrttProject.getJrttSiteId())) {
            throw new JeecgBootException("头条站点不存在");
        }
        jrttPromotionCreateRequest.setProjectId(opJrttProject.getProjectId());
        // 广告名称
        String promotionName = opJrttPromotionDto.getName();
        jrttPromotionCreateRequest.setName(promotionName);
        jrttPromotionCreateRequest.setOperation(opJrttPromotionDto.getOperation());
        PromotionMaterials promotionMaterials = new PromotionMaterials();
        // 视频素材信息
        VideoMaterial videoMaterial = new VideoMaterial();
        OpMaterial opMaterial = opMaterialService.getById(opJrttPromotionDto.getVideoMaterialId());
        if (opMaterial.getType2() == 201) {
            videoMaterial.setImageMode(JrttVideoImageModeConstant.ACROSS);
        } else {
            videoMaterial.setImageMode(JrttVideoImageModeConstant.VERTICAL);
        }
        videoMaterial.setVideoId(opMaterial.getJrttFileId());
        List<VideoMaterial> videoMaterialList = new ArrayList<>();
        // 获取素材信息
        JrttImageAdRequest jrttImageAdRequest = new JrttImageAdRequest();
        jrttImageAdRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
        jrttImageAdRequest.setFilename(opMaterial.getMaterialName() + "_screenshot");
        jrttImageAdRequest.setUploadType(JrttImageUploadType.UPLOAD_BY_FILE);
        File ImageFile = null;
        try {
            ImageFile = ImageBase64Util.base64ToFile(
                opJrttPromotionDto.getVideoMaterialScreenShot());
        } catch (Exception e) {
            log.error("base64转换异常", e);
            throw new JeecgBootException("base64转图片异常");
        }
        jrttImageAdRequest.setImageFile(new FileSystemResource(ImageFile.getPath()));
        jrttImageAdRequest.setImageSignature(FileUtil.computeMD5(ImageFile));
        JrttBaseResponse<JrttImageAdResponse> jrttImageAdResponse = JrttMaterialApi.imageAd(
            jrttImageAdRequest,
            jrttTokenBo.getAccessToken());
        if (!JrttCodeConstant.OK.equals(jrttImageAdResponse.getCode())) {
            throw new JeecgBootException(jrttImageAdResponse.getMessage());
        }
        // 删除临时的封面文件
        FileUtil.delFile(ImageFile.getPath());
        videoMaterial.setVideoCoverId(jrttImageAdResponse.getData().getId());
        videoMaterialList.add(videoMaterial);
        // 设置视频素材
        promotionMaterials.setVideoMaterialList(videoMaterialList);
        List<TitleMaterial> titleMaterialList = new ArrayList<>();
        for (String title : opJrttPromotionDto.getTitleMaterialList()) {
            TitleMaterial titleMaterial = new TitleMaterial();
            titleMaterial.setTitle(title);
            titleMaterialList.add(titleMaterial);
        }
        // 设置标题素材
        promotionMaterials.setTitleMaterialList(titleMaterialList);
        List<String> webUrlMaterialList = new ArrayList<>();
        webUrlMaterialList.add(JrttUrlConstant.CHENGZIJIANZHAN + opJrttProject.getJrttSiteId());
        // 设置落地页
        OpSubGameModel opSubGameModel = gameApi.getOpSubGame(opDeal.getSubGameId());
        if (SubGameTypeConstant.ANDROID.equals(opSubGameModel.getGameType())) {
            promotionMaterials.setWebUrlMaterialList(webUrlMaterialList);
        }
        if (Objects.equals(opJrttProject.getDownloadType(), JrttDownloadType.EXTERNAL_URL)) {
            // 落地页下载
            promotionMaterials.setExternalUrlMaterialList(webUrlMaterialList);
        }
        ProductInfo productInfo = new ProductInfo();
        List<String> titles = new ArrayList();
        titles.add(opJrttPromotionDto.getProductTitle());
        productInfo.setTitles(titles);
        List<String> imageIdList = new ArrayList();
        OpMaterial imgMaterial = opMaterialService.getById(opJrttPromotionDto.getProductImageId());
        imageIdList.add(imgMaterial.getJrttFileId());
        productInfo.setImageIds(imageIdList);
        productInfo.setSellingPoints(opJrttPromotionDto.getSellingPoints());
        // 设置产品信息
        promotionMaterials.setProductInfo(productInfo);
        // 设置行动号召文案
        promotionMaterials.setCallToActionButtons(opJrttPromotionDto.getCallToActionButtons());
        // 广告素材组合
        jrttPromotionCreateRequest.setPromotionMaterials(promotionMaterials);
        if (PromotionIdentityConstant.DOUYING.equals(opJrttPromotionDto.getPromotionIdentity())) {
            NativeSetting nativeSetting = new NativeSetting();
            nativeSetting.setAwemeId(opJrttPromotionDto.getAwemeId());
            nativeSetting.setIsFeedAndFavSee(opJrttPromotionDto.getIsFeedAndFavSee());
            nativeSetting.setAnchorRelatedType(opJrttPromotionDto.getAnchorRelatedType());
            // 原生广告设置
            jrttPromotionCreateRequest.setNativeSetting(nativeSetting);
        }
        jrttPromotionCreateRequest.setBudget(opJrttPromotionDto.getBudget());
        // TODO 预算与广告出价逻辑
        jrttPromotionCreateRequest.setBid(opJrttPromotionDto.getBid());
        jrttPromotionCreateRequest.setCpaBid(opJrttPromotionDto.getCpaBid());
        jrttPromotionCreateRequest.setDeepCpabid(opJrttPromotionDto.getDeepCpabid());
        jrttPromotionCreateRequest.setRoiGoal(opJrttPromotionDto.getRoiGoal());
        JrttPromotionCreateResponse jrttPromotionCreateResponse = JrttPromotionApi.promotionCreate(
            jrttPromotionCreateRequest, jrttTokenBo.getAccessToken());
        OpJrttPromotion opJrttPromotion = new OpJrttPromotion();
        // 入库
        opJrttPromotion.setId(opJrttPromotionDto.getId());
        opJrttPromotion.setDealId(opDeal.getId());
        opJrttPromotion.setAdvertiserId(jrttTokenBo.getAdvertiserId());
        opJrttPromotion.setProjectId(opJrttProject.getId());
        opJrttPromotion.setTitleMaterialList(
            StringUtils.join(opJrttPromotionDto.getTitleMaterialList(), ","));
        opJrttPromotion.setJrttPromotionId(jrttPromotionCreateResponse.getPromotionId());
        opJrttPromotion.setOperation(opJrttPromotionDto.getOperation());
        opJrttPromotion.setName(promotionName);
        opJrttPromotion.setVideoMaterialList(JSONObject.toJSONString(videoMaterialList));
        opJrttPromotion.setProductTitle(opJrttPromotionDto.getProductTitle());
        opJrttPromotion.setImageIds(opJrttPromotionDto.getProductImageId() + "");
        opJrttPromotion.setSellingPoints(
            StringUtils.join(opJrttPromotionDto.getSellingPoints(), ","));
        opJrttPromotion.setCallToActionButtons(
            StringUtils.join(opJrttPromotionDto.getCallToActionButtons(), ","));
        opJrttPromotion.setAwemeId(opJrttPromotionDto.getAwemeId());
        opJrttPromotion.setIsFeedAndFavSee(opJrttPromotionDto.getIsFeedAndFavSee());
        opJrttPromotion.setAnchorRelatedType(opJrttPromotionDto.getAnchorRelatedType());
        opJrttPromotion.setBudget(opJrttPromotionDto.getBudget());
        opJrttPromotion.setBid(opJrttPromotionDto.getBid());
        opJrttPromotion.setCpaBid(opJrttPromotionDto.getCpaBid());
        opJrttPromotion.setDeepCpabid(opJrttPromotionDto.getDeepCpabid());
        opJrttPromotion.setRoiGoal(opJrttPromotionDto.getRoiGoal());
        opJrttPromotion.setDeepCpabid(opJrttPromotionDto.getDeepCpabid());
        opJrttPromotion.setVideoMaterialId(opJrttPromotionDto.getVideoMaterialId());
        opJrttPromotion.setVideoMaterialFrameRate(opJrttPromotionDto.getVideoMaterialFrameRate());
        save(opJrttPromotion);
    }

    @Override
    public void updateOpJrttPromotion(OpJrttPromotionDto opJrttPromotionDto) {
        OpDeal opDeal = opDealService.getById(opJrttPromotionDto.getDealId());
        if (null == opDeal) {
            throw new JeecgBootException("广告不存在");
        }
        JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(opDeal.getAccountId());
        JrttPromotionUpdateRequest jrttPromotionUpdateRequest = new JrttPromotionUpdateRequest();
        jrttPromotionUpdateRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
        OpJrttProject opJrttProject = opJrttProjectService.getOne(
            new LambdaQueryWrapper<OpJrttProject>().eq(OpJrttProject::getDealId,
                opJrttPromotionDto.getDealId()));
        if (StringUtils.isBlank(opJrttProject.getJrttSiteId())) {
            throw new JeecgBootException("头条站点不存在");
        }
        jrttPromotionUpdateRequest.setPromotionId(opJrttPromotionDto.getJrttPromotionId());
        // 广告名称
        String promotionName = opJrttPromotionDto.getName();
        jrttPromotionUpdateRequest.setName(promotionName);
        jrttPromotionUpdateRequest.setOperation(opJrttPromotionDto.getOperation());
        PromotionMaterials promotionMaterials = new PromotionMaterials();
        // 视频素材信息
        VideoMaterial videoMaterial = new VideoMaterial();
        OpMaterial opMaterial = opMaterialService.getById(opJrttPromotionDto.getVideoMaterialId());
        if (opMaterial.getType2() == 201) {
            videoMaterial.setImageMode(JrttVideoImageModeConstant.ACROSS);
        } else {
            videoMaterial.setImageMode(JrttVideoImageModeConstant.VERTICAL);
        }
        videoMaterial.setVideoId(opMaterial.getJrttFileId());
        List<VideoMaterial> videoMaterialList = new ArrayList<>();
        // 获取素材信息
        JrttImageAdRequest jrttImageAdRequest = new JrttImageAdRequest();
        jrttImageAdRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
        jrttImageAdRequest.setFilename(opMaterial.getMaterialName() + "_screenshot");
        jrttImageAdRequest.setUploadType(JrttImageUploadType.UPLOAD_BY_FILE);
        File ImageFile = null;
        try {
            ImageFile = ImageBase64Util.base64ToFile(
                opJrttPromotionDto.getVideoMaterialScreenShot());
        } catch (Exception e) {
            log.error("base64转换异常", e);
            throw new JeecgBootException("base64转图片异常");
        }
        jrttImageAdRequest.setImageFile(new FileSystemResource(ImageFile.getPath()));
        jrttImageAdRequest.setImageSignature(FileUtil.computeMD5(ImageFile));
        JrttBaseResponse<JrttImageAdResponse> jrttImageAdResponse = JrttMaterialApi.imageAd(
            jrttImageAdRequest,
            jrttTokenBo.getAccessToken());
        if (!JrttCodeConstant.OK.equals(jrttImageAdResponse.getCode())) {
            throw new JeecgBootException(jrttImageAdResponse.getMessage());
        }
        // 删除临时的封面文件
        FileUtil.delFile(ImageFile.getPath());
        videoMaterial.setVideoCoverId(jrttImageAdResponse.getData().getId());
        videoMaterialList.add(videoMaterial);
        // 设置视频素材
        promotionMaterials.setVideoMaterialList(videoMaterialList);
        List<TitleMaterial> titleMaterialList = new ArrayList<>();
        for (String title : opJrttPromotionDto.getTitleMaterialList()) {
            TitleMaterial titleMaterial = new TitleMaterial();
            titleMaterial.setTitle(title);
            titleMaterialList.add(titleMaterial);
        }
        // 设置标题素材
        promotionMaterials.setTitleMaterialList(titleMaterialList);
        List<String> webUrlMaterialList = new ArrayList<>();
        webUrlMaterialList.add(JrttUrlConstant.CHENGZIJIANZHAN + opJrttProject.getJrttSiteId());
        // 设置落地页
        OpSubGameModel opSubGameModel = gameApi.getOpSubGame(opDeal.getSubGameId());
        if (SubGameTypeConstant.ANDROID.equals(opSubGameModel.getGameType())) {
            promotionMaterials.setWebUrlMaterialList(webUrlMaterialList);
        }
        if (Objects.equals(opJrttProject.getDownloadType(), JrttDownloadType.EXTERNAL_URL)) {
            // 落地页下载
            promotionMaterials.setExternalUrlMaterialList(webUrlMaterialList);
        }
        ProductInfo productInfo = new ProductInfo();
        List<String> titles = new ArrayList();
        titles.add(opJrttPromotionDto.getProductTitle());
        productInfo.setTitles(titles);
        List<String> imageIdList = new ArrayList();
        OpMaterial imgMaterial = opMaterialService.getById(opJrttPromotionDto.getProductImageId());
        imageIdList.add(imgMaterial.getJrttFileId());
        productInfo.setImageIds(imageIdList);
        productInfo.setSellingPoints(opJrttPromotionDto.getSellingPoints());
        // 设置产品信息
        promotionMaterials.setProductInfo(productInfo);
        // 设置行动号召文案
        promotionMaterials.setCallToActionButtons(opJrttPromotionDto.getCallToActionButtons());
        // 广告素材组合
        jrttPromotionUpdateRequest.setPromotionMaterials(promotionMaterials);
        if (PromotionIdentityConstant.DOUYING.equals(opJrttPromotionDto.getPromotionIdentity())) {
            NativeSetting nativeSetting = new NativeSetting();
            nativeSetting.setAwemeId(opJrttPromotionDto.getAwemeId());
            nativeSetting.setIsFeedAndFavSee(opJrttPromotionDto.getIsFeedAndFavSee());
            nativeSetting.setAnchorRelatedType(opJrttPromotionDto.getAnchorRelatedType());
            // 原生广告设置
            jrttPromotionUpdateRequest.setNativeSetting(nativeSetting);
        }
        jrttPromotionUpdateRequest.setBudget(opJrttPromotionDto.getBudget());
        jrttPromotionUpdateRequest.setBid(opJrttPromotionDto.getBid());
        jrttPromotionUpdateRequest.setCpaBid(opJrttPromotionDto.getCpaBid());
        jrttPromotionUpdateRequest.setDeepCpabid(opJrttPromotionDto.getDeepCpabid());
        jrttPromotionUpdateRequest.setRoiGoal(opJrttPromotionDto.getRoiGoal());
        JrttPromotionUpdateResponse jrttPromotionUpdateResponse = JrttPromotionApi.promotionUpdate(
            jrttPromotionUpdateRequest, jrttTokenBo.getAccessToken());
        OpJrttPromotion opJrttPromotion = new OpJrttPromotion();
        // 入库
        opJrttPromotion.setId(opJrttPromotionDto.getId());
        opJrttPromotion.setDealId(opDeal.getId());
        opJrttPromotion.setAdvertiserId(jrttTokenBo.getAdvertiserId());
        opJrttPromotion.setProjectId(opJrttProject.getId());
        opJrttPromotion.setTitleMaterialList(
            StringUtils.join(opJrttPromotionDto.getTitleMaterialList(), ","));
        opJrttPromotion.setJrttPromotionId(opJrttPromotionDto.getJrttPromotionId());
        opJrttPromotion.setOperation(opJrttPromotionDto.getOperation());
        opJrttPromotion.setName(promotionName);
        opJrttPromotion.setVideoMaterialList(JSONObject.toJSONString(videoMaterialList));
        opJrttPromotion.setProductTitle(opJrttPromotionDto.getProductTitle());
        opJrttPromotion.setImageIds(opJrttPromotionDto.getProductImageId() + "");
        opJrttPromotion.setSellingPoints(
            StringUtils.join(opJrttPromotionDto.getSellingPoints(), ","));
        opJrttPromotion.setCallToActionButtons(
            StringUtils.join(opJrttPromotionDto.getCallToActionButtons(), ","));
        opJrttPromotion.setAwemeId(opJrttPromotionDto.getAwemeId());
        opJrttPromotion.setIsFeedAndFavSee(opJrttPromotionDto.getIsFeedAndFavSee());
        opJrttPromotion.setAnchorRelatedType(opJrttPromotionDto.getAnchorRelatedType());
        opJrttPromotion.setBudget(opJrttPromotionDto.getBudget());
        opJrttPromotion.setBid(opJrttPromotionDto.getBid());
        opJrttPromotion.setCpaBid(opJrttPromotionDto.getCpaBid());
        opJrttPromotion.setDeepCpabid(opJrttPromotionDto.getDeepCpabid());
        opJrttPromotion.setRoiGoal(opJrttPromotionDto.getRoiGoal());
        opJrttPromotion.setDeepCpabid(opJrttPromotionDto.getDeepCpabid());
        opJrttPromotion.setVideoMaterialId(opJrttPromotionDto.getVideoMaterialId());
        opJrttPromotion.setVideoMaterialFrameRate(opJrttPromotionDto.getVideoMaterialFrameRate());
        updateById(opJrttPromotion);
    }
}
