package org.jeecg.modules.advert.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.FileUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.material.api.JrttMaterialApi;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttImageAdRequest;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttImageAdResponse;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.VideoMaterial;
import org.jeecg.modules.advert.api.xingtu.creative.api.XingtuCreativeApi;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuAdDataRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuAddCustomCreativeRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuAddCustomCreativeResponse;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuAddProceduralCreativeRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuCreativeRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuCustomAdDataRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuCustomCreativeListRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuCustomVideoMaterialsRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuGetIndustryListResponse;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuGetIndustryResponse;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuImageInfoRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuTitleMaterialsRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuVideoInfoRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuVideoMaterialsRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuWordListRequest;
import org.jeecg.modules.advert.bo.opXingtuCreative.CreativeVideoMeterials;
import org.jeecg.modules.advert.bo.opXingtuCreative.TitleList;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttImageUploadType;
import org.jeecg.modules.advert.constant.jrtt.JrttVideoImageModeConstant;
import org.jeecg.modules.advert.constant.xingtu.XingtuCreativeWordId;
import org.jeecg.modules.advert.dto.OpXingtuCreativeDto;
import org.jeecg.modules.advert.entity.OpMaterial;
import org.jeecg.modules.advert.entity.OpXingtuCreative;
import org.jeecg.modules.advert.mapper.OpXingtuCreativeMapper;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.service.IOpMaterialService;
import org.jeecg.modules.advert.service.IOpXingtuCreativeService;
import org.jeecg.modules.advert.util.ImageBase64Util;
import org.jeecg.modules.advert.util.RedisUtils;
import org.jeecg.modules.advert.vo.CreativeIndustryVo;
import org.jeecg.modules.advert.vo.OpXingtuCreativeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_xingtu_creative
 * @Author: jeecg-boot
 * @Date: 2023-03-20
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
@Slf4j
public class OpXingtuCreativeServiceImpl extends
    ServiceImpl<OpXingtuCreativeMapper, OpXingtuCreative> implements IOpXingtuCreativeService {

    @Autowired
    private IOpJrttPutAccountService opJrttPutAccountService;
    @Autowired
    private OpXingtuCreativeMapper opXingtuCreativeMapper;
    @Autowired
    private IOpMaterialService opMaterialService;

    @Override
    @Cacheable(cacheNames = RedisUtils.GET_INDUSTRY_LIST, key = "'Industry_info'")
    public List<CreativeIndustryVo> getIndustryList() {
        JrttBaseResponse<XingtuGetIndustryResponse> jrttBaseResponse = XingtuCreativeApi.getIndustryList(
            opJrttPutAccountService.list().get(0).getAccessToken());
        Map<Integer, Map<Integer, List<XingtuGetIndustryListResponse>>> map = jrttBaseResponse.getData()
            .getList().stream()
            .collect(Collectors.groupingBy(XingtuGetIndustryListResponse::getFirstIndustryId,
                Collectors.groupingBy(XingtuGetIndustryListResponse::getSecondIndustryId)));
        List<CreativeIndustryVo> optionList = new ArrayList<>();
        for (Integer key : map.keySet()) {
            Map<Integer, List<XingtuGetIndustryListResponse>> listOne = map.get(key);
            CreativeIndustryVo creativeIndustryVo = new CreativeIndustryVo();
            List<CreativeIndustryVo> subList = new ArrayList<>();
            for (Integer key2 : listOne.keySet()) {
                if (key2 != 0) {
                    List<XingtuGetIndustryListResponse> listTwo = listOne.get(key2);
                    creativeIndustryVo.setLabel(listTwo.get(0).getFirstIndustryName());
                    creativeIndustryVo.setValue(key);
                    CreativeIndustryVo creativeIndustryVo2 = new CreativeIndustryVo();
                    List<CreativeIndustryVo> pkgList = new ArrayList<>();
                    for (XingtuGetIndustryListResponse xingtuGetIndustryListResponse : listTwo) {
                        creativeIndustryVo2.setLabel(
                            xingtuGetIndustryListResponse.getSecondIndustryName());
                        creativeIndustryVo2.setValue(key2);
                        if (xingtuGetIndustryListResponse.getThirdIndustryId() != 0) {
                            CreativeIndustryVo creativeIndustryVo3 = new CreativeIndustryVo();
                            creativeIndustryVo3.setLabel(
                                xingtuGetIndustryListResponse.getThirdIndustryName());
                            creativeIndustryVo3.setValue(
                                xingtuGetIndustryListResponse.getThirdIndustryId());
                            creativeIndustryVo3.setChildren(new ArrayList<>());
                            pkgList.add(creativeIndustryVo3);
                        }
                    }
                    creativeIndustryVo2.setChildren(pkgList);
                    subList.add(creativeIndustryVo2);
                }
            }
            creativeIndustryVo.setChildren(subList);
            optionList.add(creativeIndustryVo);
        }
        return optionList;
    }

    @Override
    public void save(OpXingtuCreativeDto opXingtuCreative) {
        JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(
            opXingtuCreative.getAccountId());
            //选择广告创意
            if (!opXingtuCreative.getImageMode().equals("CREATIVE_IMAGE_MODE_AWEME_LIVE")) {
                OpXingtuCreative res = new OpXingtuCreative();
                List<XingtuTitleMaterialsRequest> titleMaterials = new ArrayList<>();
                //创意标题素材
                for (TitleList titleList : opXingtuCreative.getTitleList()) {
                    XingtuTitleMaterialsRequest xingtuTitleMaterialsRequest = new XingtuTitleMaterialsRequest();
                    xingtuTitleMaterialsRequest.setTitle(titleList.getTitle());
                    titleMaterials.add(xingtuTitleMaterialsRequest);
                }
                    //程序化创意
                    if (opXingtuCreative.getCreativeMaterialMode().equals("STATIC_ASSEMBLE")) {
                            XingtuAddProceduralCreativeRequest xingtuAddProceduralCreativeRequest = new XingtuAddProceduralCreativeRequest();
                            xingtuAddProceduralCreativeRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
                            //广告计划数据
                            XingtuAdDataRequest xingtuAdDataRequest = new XingtuAdDataRequest();
                            BeanUtils.copyProperties(opXingtuCreative, xingtuAdDataRequest);
                            if (opXingtuCreative.getAdKeywords().isEmpty()) {
                                xingtuAdDataRequest.setAdKeywords(null);
                            }
                            xingtuAdDataRequest.setThirdIndustryId(
                                opXingtuCreative.getThirdIndustryId().get(2));
                            xingtuAdDataRequest.setIesCoreUserId(opXingtuCreative.getTrillId());
                            xingtuAddProceduralCreativeRequest.setAdData(xingtuAdDataRequest);
                            //程序化素材信息
                            XingtuCreativeRequest xingtuCreativeRequest = new XingtuCreativeRequest();

                            List<XingtuVideoMaterialsRequest> videoMaterials = new ArrayList<>();
                            xingtuCreativeRequest.setTitleMaterials(titleMaterials);
                            xingtuAddProceduralCreativeRequest.setAdId(opXingtuCreative.getAdIdLong());
                            //创意视频素材
                            XingtuVideoMaterialsRequest xingtuVideoMaterialsRequest = new XingtuVideoMaterialsRequest();
                            // 视频素材信息
                            OpMaterial opMaterial = opMaterialService.getById(
                                opXingtuCreative.getVideoMaterials().get(0).getVideoMaterialId());
                            if (opMaterial.getType2() == 201) {
                                xingtuVideoMaterialsRequest.setImageMode(JrttVideoImageModeConstant.ACROSS);
                            } else {
                                xingtuVideoMaterialsRequest.setImageMode(
                                    JrttVideoImageModeConstant.VERTICAL);
                            }
                            // 获取素材信息
                            JrttImageAdRequest jrttImageAdRequest = new JrttImageAdRequest();
                            jrttImageAdRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
                            jrttImageAdRequest.setFilename(opMaterial.getMaterialName() + "-横幅");
                            jrttImageAdRequest.setUploadType(JrttImageUploadType.UPLOAD_BY_FILE);
                            File ImageFile = null;
                            try {
                                ImageFile = ImageBase64Util.base64ToFile(
                                    opXingtuCreative.getVideoMaterials().get(0).getVideoMaterialScreenShot());
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
                            XingtuImageInfoRequest xingtuImageInfoRequest = new XingtuImageInfoRequest();
                            xingtuImageInfoRequest.setImageId(jrttImageAdResponse.getData().getId());
                            xingtuVideoMaterialsRequest.setImageInfo(xingtuImageInfoRequest);
                            XingtuVideoInfoRequest xingtuVideoInfoRequest = new XingtuVideoInfoRequest();
                            xingtuVideoInfoRequest.setVideoId(opMaterial.getJrttFileId());
                            xingtuVideoMaterialsRequest.setVideoInfo(xingtuVideoInfoRequest);
                            opXingtuCreative.getVideoMaterials().get(0).setVideoId(opMaterial.getJrttFileId());
                            opXingtuCreative.getVideoMaterials().get(0).setImageId(jrttImageAdResponse.getData().getId());
                            opXingtuCreative.getVideoMaterials().get(0).setVideoMaterialScreenShot(null);
                            videoMaterials.add(xingtuVideoMaterialsRequest);
                            xingtuCreativeRequest.setVideoMaterials(videoMaterials);
                            xingtuAddProceduralCreativeRequest.setCreative(xingtuCreativeRequest);
                            if (opXingtuCreative.getId() == null) {
                                XingtuCreativeApi.createProceduralCreative(
                                    xingtuAddProceduralCreativeRequest, jrttTokenBo.getAccessToken());
                            }else {
                                XingtuCreativeApi.updateProceduralCreative(
                                    xingtuAddProceduralCreativeRequest, jrttTokenBo.getAccessToken());
                            }
                            //保存到数据库
                            BeanUtils.copyProperties(opXingtuCreative, res);
                            res.setAdId(opXingtuCreative.getAdId());

                            res.setAdKeywords(opXingtuCreative.getAdKeywords().toString()
                                .substring(1, opXingtuCreative.getAdKeywords().toString().length() - 1));
                            res.setThirdIndustryId(opXingtuCreative.getThirdIndustryId().toString()
                                .substring(1, opXingtuCreative.getThirdIndustryId().toString().length() - 1));
                            res.setTitleList(JSONObject.toJSONString(opXingtuCreative.getTitleList()));
                            res.setVideoMaterials(
                                JSONObject.toJSONString(opXingtuCreative.getVideoMaterials()));
                    }else {
                            //自定义创意
                            XingtuAddCustomCreativeRequest xingtuAddCustomCreativeRequest = new XingtuAddCustomCreativeRequest();
                            xingtuAddCustomCreativeRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
                            xingtuAddCustomCreativeRequest.setAdId(opXingtuCreative.getAdIdLong());
                            //自定义创意素材信息
                            List<XingtuCustomCreativeListRequest> creativeList = new ArrayList<>();
                            XingtuCustomCreativeListRequest xingtuCustomCreativeListRequest = new XingtuCustomCreativeListRequest();
                            List<XingtuCustomVideoMaterialsRequest> videoMaterials = new ArrayList<>();
                            xingtuCustomCreativeListRequest.setTitleMaterials(titleMaterials.get(0));
                            //创意视频素材
                            XingtuCustomVideoMaterialsRequest xingtuCustomVideoMaterialsRequest = new XingtuCustomVideoMaterialsRequest();
                            // 视频素材信息
                            OpMaterial opMaterial = opMaterialService.getById(
                                opXingtuCreative.getVideoMaterials().get(0).getVideoMaterialId());
                            if (opMaterial.getType2() == 201) {
                                xingtuCustomCreativeListRequest.setImageMode(JrttVideoImageModeConstant.ACROSS);
                            } else {
                                xingtuCustomCreativeListRequest.setImageMode(JrttVideoImageModeConstant.VERTICAL);
                            }
                            // 获取素材信息
                            JrttImageAdRequest jrttImageAdRequest = new JrttImageAdRequest();
                            jrttImageAdRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
                            jrttImageAdRequest.setFilename(opMaterial.getMaterialName() + "-横幅");
                            jrttImageAdRequest.setUploadType(JrttImageUploadType.UPLOAD_BY_FILE);
                            File ImageFile = null;
                            try {
                                ImageFile = ImageBase64Util.base64ToFile(
                                    opXingtuCreative.getVideoMaterials().get(0).getVideoMaterialScreenShot());
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
                            XingtuImageInfoRequest xingtuImageInfoRequest = new XingtuImageInfoRequest();
                            xingtuImageInfoRequest.setImageId(jrttImageAdResponse.getData().getId());
                            xingtuCustomVideoMaterialsRequest.setImageInfo(xingtuImageInfoRequest);
                            XingtuVideoInfoRequest xingtuVideoInfoRequest = new XingtuVideoInfoRequest();
                            xingtuVideoInfoRequest.setVideoId(opMaterial.getJrttFileId());
                            xingtuCustomVideoMaterialsRequest.setVideoInfo(xingtuVideoInfoRequest);
                            opXingtuCreative.getVideoMaterials().get(0).setVideoId(opMaterial.getJrttFileId());
                            opXingtuCreative.getVideoMaterials().get(0).setImageId(jrttImageAdResponse.getData().getId());
                            opXingtuCreative.getVideoMaterials().get(0).setVideoMaterialScreenShot(null);
                            videoMaterials.add(xingtuCustomVideoMaterialsRequest);

                            xingtuCustomCreativeListRequest.setVideoMaterials(videoMaterials.get(0));
                            creativeList.add(xingtuCustomCreativeListRequest);
                            xingtuAddCustomCreativeRequest.setCreativeList(creativeList);
                            //广告计划数据
                            XingtuCustomAdDataRequest xingtuCustomAdDataRequest = new XingtuCustomAdDataRequest();
                            BeanUtils.copyProperties(opXingtuCreative, xingtuCustomAdDataRequest);
                            if (opXingtuCreative.getAdKeywords().isEmpty()) {
                                xingtuCustomAdDataRequest.setAdKeywords(null);
                            }
                            xingtuCustomAdDataRequest.setThirdIndustryId(
                                opXingtuCreative.getThirdIndustryId().get(2));
                            xingtuCustomAdDataRequest.setIesCoreUserId(opXingtuCreative.getTrillId());
                            xingtuAddCustomCreativeRequest.setAdData(xingtuCustomAdDataRequest);
                            JrttBaseResponse<XingtuAddCustomCreativeResponse> jrttBaseResponse = new JrttBaseResponse<>();
                            if (opXingtuCreative.getId() == null) {
                                jrttBaseResponse = XingtuCreativeApi.createCustomCreative(
                                    xingtuAddCustomCreativeRequest, jrttTokenBo.getAccessToken());
                            }else {
                                jrttBaseResponse = XingtuCreativeApi.updateCustomCreative(
                                    xingtuAddCustomCreativeRequest, jrttTokenBo.getAccessToken());
                            }
                            if (jrttBaseResponse.getData().getErrors() != null) {
                                throw new JeecgBootException(jrttBaseResponse.getData().getErrors().get(0).getMessage());
                            }
                            //保存到数据库
                            BeanUtils.copyProperties(opXingtuCreative, res);
                            res.setAdId(opXingtuCreative.getAdId());
                            res.setCreativeId(jrttBaseResponse.getData().getCreativeIds().get(0));
                            res.setAdKeywords(opXingtuCreative.getAdKeywords().toString()
                                .substring(1, opXingtuCreative.getAdKeywords().toString().length() - 1));
                            res.setThirdIndustryId(opXingtuCreative.getThirdIndustryId().toString()
                                .substring(1, opXingtuCreative.getThirdIndustryId().toString().length() - 1));
                            res.setTitleList(JSONObject.toJSONString(opXingtuCreative.getTitleList()));
                            res.setVideoMaterials(
                                JSONObject.toJSONString(opXingtuCreative.getVideoMaterials()));
                        }
                    if (opXingtuCreative.getId() == null) {
                        opXingtuCreativeMapper.insert(res);
                    }else {
                        opXingtuCreativeMapper.updateById(res);
                    }


            }

    }

    @Override
    public OpXingtuCreativeVo getCreative(OpXingtuCreative opXingtuCreative) {
        LambdaQueryWrapper<OpXingtuCreative> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpXingtuCreative::getAccountId, opXingtuCreative.getAccountId());
        wrapper.eq(OpXingtuCreative::getAdId, opXingtuCreative.getAdId());
        OpXingtuCreative res = opXingtuCreativeMapper.selectOne(wrapper);
        if (oConvertUtils.isEmpty(res)) {
            log.error("null---");
            return null;
        }
        OpXingtuCreativeVo opXingtuCreativeVo = new OpXingtuCreativeVo();
        BeanUtils.copyProperties(res, opXingtuCreativeVo);
        if (oConvertUtils.isNotEmpty(res.getAdKeywords())) {
            opXingtuCreativeVo.setAdKeywords(Arrays.asList(res.getAdKeywords().replaceAll(" ","").split(",")));
        }
        if (oConvertUtils.isNotEmpty(res.getThirdIndustryId())) {
            List<String> thirdIndustryId = Arrays.asList(res.getThirdIndustryId().replaceAll(" ","").split(","));
            opXingtuCreativeVo.setThirdIndustryId(
                thirdIndustryId.stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        if (oConvertUtils.isNotEmpty(res.getTitleList())) {
            opXingtuCreativeVo.setTitleList(JSONArray.parseArray(res.getTitleList(), TitleList.class));
        }
        if (oConvertUtils.isNotEmpty(res.getVideoMaterials())) {
            opXingtuCreativeVo.setVideoMaterials(
                JSONArray.parseArray(res.getVideoMaterials(), CreativeVideoMeterials.class));
        }
        return opXingtuCreativeVo;
    }
}
