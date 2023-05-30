package org.jeecg.modules.advert.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.swing.text.html.Option;
import jdk.nashorn.internal.ir.ReturnNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.SubGameTypeConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.site.api.JrttSiteApi;
import org.jeecg.modules.advert.api.jrtt.site.bo.JrttModifySiteRequest;
import org.jeecg.modules.advert.api.jrtt.site.bo.JrttSiteRequest;
import org.jeecg.modules.advert.api.jrtt.site.bo.JrttSiteResponse;
import org.jeecg.modules.advert.api.jrtt.site.bo.JrttUpdateSiteRequest;
import org.jeecg.modules.advert.api.jrtt.site.bo.JrttUpdateSiteResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttSiteStatusConstant;
import org.jeecg.modules.advert.constant.jrtt.SiteUnitType;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpJrttProject;
import org.jeecg.modules.advert.entity.OpJrttSite;
import org.jeecg.modules.advert.entity.OpXingtuDeal;
import org.jeecg.modules.advert.entity.OpMaterial;
import org.jeecg.modules.advert.mapper.OpMaterialMapper;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.modules.advert.service.IOpJrttProjectService;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.mapper.OpJrttSiteMapper;
import org.jeecg.modules.advert.service.IOpJrttSiteService;
import org.jeecg.modules.advert.service.IOpXingtuDealService;
import org.jeecg.modules.advert.service.IOpMaterialService;
import org.jeecg.modules.advert.vo.JrttSiteListVo;
import org.jeecg.modules.advert.vo.JrttSiteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_jrtt_site
 * @Author: jeecg-boot
 * @Date: 2023-02-16
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_gateway")
public class OpJrttSiteServiceImpl extends ServiceImpl<OpJrttSiteMapper, OpJrttSite> implements
    IOpJrttSiteService {

    @Resource
    private IOpDealService opDealService;
    @Resource
    private IOpJrttProjectService opJrttProjectService;
    @Resource
    private IOpJrttPutAccountService opJrttPutAccountService;
    @Autowired
    private OpMaterialMapper materialMapper;
    @Resource
    private IOpXingtuDealService xingtuDealService;

    @Override
    public JrttSiteVo post_site(Integer dealId, Integer siteId, Integer channelId) {
        OpXingtuDeal xingtuDeal = null;
        OpDeal deal = opDealService.getById(dealId);
        if (oConvertUtils.isEmpty(deal)) {
            return new JrttSiteVo("缺失对应广告数据", 500);
        }

        OpJrttSite site = getById(siteId);
        if (oConvertUtils.isEmpty(site)) {
            return new JrttSiteVo("缺失对应站点数据", 500);
        }
        // 创建头条站点时
        if (channelId.equals(ChannelConstant.JRTT)) {
            if (oConvertUtils.isEmpty(deal.getPkgUrl())) {
                return new JrttSiteVo("此广告缺少下载链接", 500);
            }
            LambdaQueryWrapper<OpJrttProject> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OpJrttProject::getDealId, dealId);
            OpJrttProject opJrttProject = opJrttProjectService.getOne(queryWrapper);
            if (oConvertUtils.isEmpty(opJrttProject) || oConvertUtils.isEmpty(
                opJrttProject.getProjectId())) {
                return new JrttSiteVo("此广告没有对应的头条广告项目数据", 500);
            }
            if (oConvertUtils.isNotEmpty(opJrttProject.getSiteId()) && opJrttProject.getSiteId()
                .equals(siteId)) {
                return new JrttSiteVo("存在同样的站点记录", 500);
            }
        } else if (channelId.equals(ChannelConstant.XING_TU)) {
            LambdaQueryWrapper<OpXingtuDeal> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OpXingtuDeal::getDealId, dealId);
            xingtuDeal = xingtuDealService.getOne(wrapper);
            if (oConvertUtils.isEmpty(xingtuDeal)) {
                return new JrttSiteVo("此广告缺失星图广告数据", 500);
            }
        }

        String downloadUrl = deal.getPkgUrl();
        String siteContent = site.getSiteContent();
        // 替换下载类型为对应子游戏类型
        if (deal.getSubGameType().equals(SubGameTypeConstant.ANDROID)) {
            siteContent = siteContent.replace("download_link_type", "android_link");
        } else {
            siteContent = siteContent.replace("download_link_type", "ios_link");
        }
        // 替换下载链接
        siteContent = siteContent.replace("downloadUrl", downloadUrl);
        // 判断是否去除 image_url
        JSONArray array = JSONObject.parseArray(siteContent);
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            if (jsonObject.getString("name").equals("XrPicture")) {
                removeImageUrl(jsonObject);
            } else if (jsonObject.getString("name").equals("XrButton")) {
                jsonObject.remove("image_url");
                // 创建星图站点时
                if (channelId.equals(ChannelConstant.XING_TU)) {
                    String iosUrl = xingtuDeal.getIosUrl();
                    String androidUrl = xingtuDeal.getAndroidUrl();
                    JSONArray events = jsonObject.getJSONArray("events");
                    JSONObject behavior = events.getJSONObject(0).getJSONObject("behavior");

                    // 设置了IOS下载链接
                    if (oConvertUtils.isNotEmpty(iosUrl)) {
                        // 当是IOS类型时, 删除 ios_link 设为新的链接
                        if (!deal.getSubGameType().equals(SubGameTypeConstant.ANDROID)) {
                            behavior.remove("ios_link");
                        }
                        JSONObject linkType = new JSONObject();
                        linkType.put("link_type", "url");
                        linkType.put("url", iosUrl);
                        behavior.put("ios_link", linkType);
                    }
                    // 设置了Android下载链接
                    if (oConvertUtils.isNotEmpty(androidUrl)) {
                        // 当是Android类型时, 删除 android_link 设为新的链接
                        if (deal.getSubGameType().equals(SubGameTypeConstant.ANDROID)) {
                            behavior.remove("android_link");
                        }
                        JSONObject linkType = new JSONObject();
                        linkType.put("link_type", "url");
                        linkType.put("url", androidUrl);
                        behavior.put("android_link", linkType);
                    }
                }
            } else {
                JSONArray groupArray = jsonObject.getJSONArray("group_content");
                for (int j = 0; j < groupArray.size(); j++) {
                    removeImageUrl(groupArray.getJSONObject(j));
                }
            }
        }
        Integer account = deal.getAccountId();
        JrttTokenBo tokenBo = opJrttPutAccountService.getAccountToken(account);
        // 判断是否需要修改站点
        String jrttSiteId = xingtuDeal.getJrttSiteId();
        if (channelId.equals(ChannelConstant.XING_TU) && oConvertUtils.isNotEmpty(jrttSiteId)) {
            JrttModifySiteRequest jrttModifySiteRequest = new JrttModifySiteRequest();
            jrttModifySiteRequest.setAdvertiserId(tokenBo.getAdvertiserId());
            jrttModifySiteRequest.setSiteName(site.getSiteName());
            jrttModifySiteRequest.setSiteContent(array);
            jrttModifySiteRequest.setSiteId(xingtuDeal.getJrttSiteId());
            JrttBaseResponse<T> response = JrttSiteApi.modifySite(tokenBo.getAccessToken(),
                jrttModifySiteRequest);
            if (JrttCodeConstant.OK.equals(response.getCode())) {
                // 更新站点ID
                UpdateWrapper<OpXingtuDeal> xingtuUpdateWrapper = new UpdateWrapper<>();
                xingtuUpdateWrapper.set("site_id", siteId);
                xingtuUpdateWrapper.eq("deal_id", dealId);
                xingtuDealService.update(xingtuUpdateWrapper);

                return new JrttSiteVo(null, 200);
            } else {
                log.error("修改站点出错！广告ID:{},投放账号:{},站点ID:{},返回信息:{}", dealId, account, siteId,
                    response);
                return new JrttSiteVo("修改站点错误" + response.getMessage(), 500);
            }
        }
        // 构建创建站点请求数据
        JrttSiteRequest jrttSiteRequest = new JrttSiteRequest();
        jrttSiteRequest.setAdvertiserId(tokenBo.getAdvertiserId());
        jrttSiteRequest.setSiteName(site.getSiteName());
        jrttSiteRequest.setSiteContent(array);

        JrttBaseResponse<JrttSiteResponse> response = JrttSiteApi.createSite(
            tokenBo.getAccessToken(), jrttSiteRequest);
        if (JrttCodeConstant.OK.equals(response.getCode())) {
            JrttSiteResponse siteResponse = response.getData();
            // 构建更改站点状态请求数据
            JrttUpdateSiteRequest updateSiteRequest = new JrttUpdateSiteRequest();
            updateSiteRequest.setAdvertiserId(tokenBo.getAdvertiserId());
            updateSiteRequest.setStatus(JrttSiteStatusConstant.PUBLISHED);
            updateSiteRequest.setSiteIDs(new String[]{siteResponse.getSiteId()});

            JrttBaseResponse<JrttUpdateSiteResponse> updateSiteResponse = JrttSiteApi
                .updateSiteStatus(
                    tokenBo.getAccessToken(), updateSiteRequest);
            JrttUpdateSiteResponse jrttUpdateSiteResponse = updateSiteResponse.getData();
            // 更改成功
            if (jrttUpdateSiteResponse.getSuccessSiteIds().length >= 1) {
                if (channelId.equals(ChannelConstant.JRTT)) {
                    // 更新头条项目
                    UpdateWrapper<OpJrttProject> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.set("site_id", siteId);
                    updateWrapper.set("jrtt_site_id", siteResponse.getSiteId());
                    updateWrapper.eq("deal_id", dealId);
                    opJrttProjectService.update(updateWrapper);
                } else if (channelId.equals(ChannelConstant.XING_TU)) {
                    // 更新星图广告
                    UpdateWrapper<OpXingtuDeal> wrapper = new UpdateWrapper<>();
                    wrapper.set("site_id", siteId);
                    wrapper.set("jrtt_site_id", siteResponse.getSiteId());
                    wrapper.eq("deal_id", dealId);
                    xingtuDealService.update(wrapper);
                }

                return new JrttSiteVo(null, 200);
            } else {
                log.error("更改站点状态出错！广告ID:{},投放账号:{},站点ID:{},返回信息:{}", dealId, account, siteId,
                    updateSiteResponse);
                // 更改失败
                return new JrttSiteVo(jrttUpdateSiteResponse.getFailMessage(), 500);
            }
        } else {
            log.error("创建站点出错！广告ID:{},投放账号:{},站点ID:{},返回信息:{}", dealId, account, siteId, response);
            return new JrttSiteVo("创建站点错误" + response.getMessage(), 500);
        }
    }

    @Override
    public void saveSite(OpJrttSite opJrttSite) {
        JSONArray siteContent = JSON.parseArray(opJrttSite.getSiteContent());
        List<HashMap<String, Object>> list = new LinkedList<>();
        boolean existButton = false;
        for (int i = 0; i < siteContent.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            JSONObject jsonObject = siteContent.getJSONObject(i);
            String name = jsonObject.getString("name");
            map.put("name", name);
            map.put("width", 375);
            map.put("is_cover",1);
            map.put("float", jsonObject.getString("float"));
            //单图组件
            if (name.equals(SiteUnitType.NAME_SINGLE_IMAGE)) {
                //没选图片
                Integer materialId = Optional.ofNullable(jsonObject.getInteger("materialId"))
                    .orElseThrow(() -> new JeecgBootException("单图组件未选择图片"));
                OpMaterial material = materialMapper
                    .selectById(materialId);
                //材料不存在
                Optional.ofNullable(material)
                    .orElseThrow(() -> new JeecgBootException("材料不存在"));
                String[] specs = material.getSpecs().split("/*");
                map.put("height", Math.round(
                    (Integer.parseInt(specs[0]) * 1.0 / Integer.parseInt(specs[1])) * 375));
                map.put("image_url", jsonObject.getString("chooseImg"));
                map.put("ic_id", material.getJrttFileId());
            } else if (name.equals(SiteUnitType.NAME_GROUP_IMAGE)) { //组图组件
                //组图组件没有选图
                Integer[] materialIds = Optional.ofNullable(jsonObject.getObject("materialIds", Integer[].class)).
                    orElseThrow(() -> new JeecgBootException("组图组件未选择图片"));
                if(materialIds.length <= 0) {
                    throw new JeecgBootException("组图组件未选择图片");
                }
                List<Integer> materialIds1 = Arrays.asList(materialIds);
                List<OpMaterial> materials = materialMapper
                    .selectList(new LambdaQueryWrapper<OpMaterial>().in(OpMaterial::getId, materialIds1));
                //材料不存在
                Optional.ofNullable(materials)
                    .orElseThrow(() -> new JeecgBootException("素材不存在"));
                String[] specs = materials.get(0).getSpecs().split("/*");
                map.put("height", Math.round(
                    (Integer.parseInt(specs[0]) * 1.0 / Integer.parseInt(specs[1])) * 375));
                List<HashMap<String, String>> imgArr = new LinkedList<>();
                String[] chooseImgs = jsonObject.getObject("chooseImgs", String[].class);
                for (int j = 0; j < materials.size(); j++) {
                    HashMap<String, String> img = new HashMap<>();
                    img.put("image_url",chooseImgs[j]);
                    img.put("ic_id",materials.get(j).getJrttFileId());
                    imgArr.add(img);
                }
                map.put("group_content",imgArr);
            }else { //按钮组件
                existButton = true;
                //颜色
                String color = Optional.ofNullable(jsonObject.getString("color")).orElse("rgba(255,255,255,1)");
                //背景颜色
                String bgColor = Optional.ofNullable(jsonObject.getString("bg_color")).orElse("rgba(205,47,32,1)");
                map.put("bg_type","color");
                map.put("color",color);
                map.put("bg_color",bgColor);
                map.put("height",48);
                map.put("text", jsonObject.getString("text"));
                map.put("font_size",16);
                map.put("icon","download");
                List<HashMap<String, Object>> events = new LinkedList<>();
                HashMap<String, Object> event = new HashMap<>(2);

                HashMap<String, String> trigger = new HashMap<>(1);
                trigger.put("type","click");

                HashMap<String, Object> behavior = new HashMap<>(2);
                behavior.put("name","DownloadEvent");

                HashMap<String, String> downLoadLinkType = new HashMap<>(2);
                downLoadLinkType.put("link_type","url");
                downLoadLinkType.put("url","downloadUrl");

                behavior.put("download_link_type",downLoadLinkType);
                event.put("trigger",trigger);
                event.put("behavior",behavior);
                events.add(event);
                map.put("events",events);
            }
            list.add(map);
        }
        if (!existButton){ //不存在按钮组件
            throw new JeecgBootException("不存在按钮组件");
        }
        opJrttSite.setSiteContent(JSON.toJSONString(list));
        if (!save(opJrttSite)) {
            throw new JeecgBootException("保存落地页失败");
        }
    }

    @Override
    public IPage<JrttSiteListVo> getPage(Page<JrttSiteListVo> page,
        OpJrttSite opJrttSite) {
        QueryWrapper<OpJrttSite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(oConvertUtils.isNotEmpty(opJrttSite.getGameId()),"site.game_id",opJrttSite.getGameId());
        queryWrapper.eq(oConvertUtils.isNotEmpty(opJrttSite.getSubGameId()),"site.sub_game_id",opJrttSite.getSubGameId());
        queryWrapper.eq(oConvertUtils.isNotEmpty(opJrttSite.getCreateBy()),"site.create_by",opJrttSite.getCreateBy());
        return baseMapper.getPage(queryWrapper,page);
    }

    /**
     * @param jsonObject:
     * @author xmh
     * @description 如果存在 icid 则 删除 image_url
     * @date 2023/2/20 16:55
     */
    private void removeImageUrl(JSONObject jsonObject) {
        String icId = jsonObject.getString("ic_id");
        if (icId != null) {
            jsonObject.remove("image_url");
        }
    }
}
