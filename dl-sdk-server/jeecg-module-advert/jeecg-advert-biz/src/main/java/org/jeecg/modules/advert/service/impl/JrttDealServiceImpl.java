package org.jeecg.modules.advert.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.PackStateConstant;
import org.jeecg.common.constant.SubGameTypeConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.pkg.api.JrttPkgApi;
import org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageListV2ListResponse;
import org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageListV2Request;
import org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageListV2Response;
import org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageV2ChannelListRequest;
import org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageV2Request;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageV2Response;
import org.jeecg.modules.advert.constant.AccountLevelConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttAccountType;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttExtendPackageStatus;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;
import org.jeecg.modules.advert.dto.OpDealDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpJrttProject;
import org.jeecg.modules.advert.mapper.JrttDealMapper;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.modules.advert.service.IJrttDealService;
import org.jeecg.modules.advert.service.IOpJrttProjectService;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.vo.OpJrttDealVo;
import org.jeecgframework.codegenerate.generate.util.e;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Description: jrtt_deal
 * @Author: jeecg-boot
 * @Date: 2023-02-14
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_gateway")
public class JrttDealServiceImpl implements IJrttDealService {

    @Resource
    private IGameApi gameApi;
    @Resource
    private JrttDealMapper jrttDealMapper;
    @Resource
    private IOpDealService opDealService;
    @Resource
    private IOpJrttProjectService opJrttProjectService;
    @Resource
    private IOpJrttPutAccountService opJrttPutAccountService;
    // 同步打包时间状态 30分钟
    private long syncPackStatusTime = 30 * 60 * 1000;

    @Override
    public IPage<OpJrttDealVo> getByPage(Page<OpJrttDealVo> page,
        OpDeal deal, String startDate, String endDate) {
        QueryWrapper<OpDeal> wrapper = opDealService.baseQuery(deal, startDate, endDate);
        wrapper.eq("d.channel_id", ChannelConstant.JRTT);
        return jrttDealMapper.dealInfoPage(page, wrapper);
    }

    @Override
    public void addDeal(OpDealDto opJrttDealDto) {
        List<OpDeal> dealList = opDealService.addDeal(opJrttDealDto, ChannelConstant.JRTT, JrttUrlConstant.IOS_TRACK_URL, JrttUrlConstant.ANDROID_TRACK_URL);
        extendPackage(dealList);
    }

    @Override
    public void updateDeal(OpDealDto opDealDto) {
        opDealService.updateDeal(opDealDto);
    }

    @Override
    public void deleteDeals(List<String> list) {
        opDealService.removeByIds(list);
        LambdaQueryWrapper<OpJrttProject> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(OpJrttProject::getDealId, list);
        opJrttProjectService.remove(wrapper);
    }

    @Override
    public void deleteDeal(String id) {
        opDealService.removeById(id);
        LambdaQueryWrapper<OpJrttProject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpJrttProject::getDealId, id);
        opJrttProjectService.remove(wrapper);
    }

    @Override
    public void reExtendPackage(Integer id) {
        OpDeal opDeal = opDealService.getById(id);
        List<OpDeal> list = new ArrayList();
        list.add(opDeal);
        extendPackage(list);
    }

    /**
     * @param opDealList
     * @author chenyw
     * @description 批量创建分包
     * @date 15:20 2023/2/17/017
     **/
    @Override
    public void extendPackage(List<OpDeal> opDealList) {
        List<JrttBaseRequest<JrttExtendPackageV2Request>> jrttBaseRequests = new ArrayList();
        // 组装数据
        label:
        for (OpDeal opDeal : opDealList) {
            OpPkgModel opPkgModel = gameApi.getOpPkgById(opDeal.getPkgId());
            if (!(ChannelConstant.JRTT.equals(opPkgModel.getChannelId()) || ChannelConstant.XING_TU.equals(opPkgModel.getChannelId())) || StringUtils.isBlank(
                opPkgModel.getJrttConf().getPackageId())) {
                log.error("找不到广告id:" + opDeal.getId() + "对应的头条渠道信息");
                // 更新数据库应用分包状态为创建中
                opDeal.setPackState(PackStateConstant.PROCESSING);
                opDealService.updateById(opDeal);
                continue;
            }
            JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(
                opPkgModel.getJrttConf().getCreateAccountId());
            JrttExtendPackageV2ChannelListRequest jrttExtendPackageV2ChannelListRequest = new JrttExtendPackageV2ChannelListRequest();
            jrttExtendPackageV2ChannelListRequest.setChannelId(opDeal.getId().toString());
            jrttExtendPackageV2ChannelListRequest.setRemark(opDeal.getDealName());
            for (JrttBaseRequest<JrttExtendPackageV2Request> jrttBaseRequest : jrttBaseRequests) {
                JrttExtendPackageV2Request jrttExtendPackageV2Request = jrttBaseRequest.getData();
                if (Objects.equals(jrttExtendPackageV2Request.getPackageId(),
                    opPkgModel.getJrttConf().getPackageId()) &&
                    Objects.equals(jrttExtendPackageV2Request.getAccountId(),
                        jrttTokenBo.getAdvertiserId())) {
                    // 如果邨彩account_id和package_id一样的记录,放到渠道列表只要发送一次接口
                    jrttExtendPackageV2Request.getChannelList()
                        .add(jrttExtendPackageV2ChannelListRequest);
                    continue label;
                }
            }
            JrttBaseRequest<JrttExtendPackageV2Request> jrttBaseRequest = new JrttBaseRequest();
            JrttExtendPackageV2Request jrttExtendPackageV2Request = new JrttExtendPackageV2Request();
            jrttExtendPackageV2Request.setAccountId(jrttTokenBo.getAdvertiserId());
            jrttExtendPackageV2Request.setPackageId(opPkgModel.getJrttConf().getPackageId());
            if (AccountLevelConstant.LEVEL_1.equals(jrttTokenBo.getLevelId())) {
                jrttExtendPackageV2Request.setAccountType(JrttAccountType.BP);
            } else {
                jrttExtendPackageV2Request.setAccountType(JrttAccountType.AD);
            }
            jrttExtendPackageV2Request.setChannelList(new ArrayList<>());
            jrttExtendPackageV2Request.getChannelList().add(jrttExtendPackageV2ChannelListRequest);
            jrttBaseRequest.setAccessToken(jrttTokenBo.getAccessToken());
            jrttBaseRequest.setUrl(JrttUrlConstant.EXTEND_PACKAGE_V2);
            jrttBaseRequest.setData(jrttExtendPackageV2Request);
            jrttBaseRequests.add(jrttBaseRequest);
        }
        // 循环发送接口
        for (JrttBaseRequest<JrttExtendPackageV2Request> jrttBaseRequest : jrttBaseRequests) {
            JrttBaseResponse<JrttExtendPackageV2Response> jrttBaseResponse = JrttPkgApi.extendPackage(jrttBaseRequest);
            if(!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())){
                // 请求接口失败，设置打包状态为失败
                List<JrttExtendPackageV2ChannelListRequest> channelList = jrttBaseRequest.getData().getChannelList();
                List<OpDeal> dealList = channelList.stream().map(a->{
                    OpDeal opDeal = new OpDeal();
                    opDeal.setId(Integer.valueOf(a.getChannelId()));
                    opDeal.setPackState(PackStateConstant.FAILURE);
                    return opDeal;
                }).collect(Collectors.toList());
                opDealService.updateBatchById(dealList);
                continue;
            }
            for (JrttExtendPackageV2ChannelListRequest jrttExtendPackageV2ChannelListRequest : jrttBaseRequest.getData()
                .getChannelList()) {
                Integer dealId = Integer.valueOf(
                    jrttExtendPackageV2ChannelListRequest.getChannelId());
                log.info("应用分包创建成功，广告id:{}", dealId);
                OpDeal update = new OpDeal();
                update.setId(dealId);
                Date now = new Date();
                update.setPkgPublishTime(now);
                update.setPkgUpdateTime(now);
                // 更新数据库应用分包状态为创建中
                update.setPackState(PackStateConstant.PROCESSING);
                opDealService.updateById(update);
            }
        }
    }

    /**
     * @author chenyw
     * @description 批量查询头条分包状态并更新
     * @date 11:19 2023/2/20/020
     **/
    @Scheduled(cron = "0/5 * * * * ?")
    public void refreshPackState() {
        Date now = new Date();
        // 查找状态为打包中，并且是最近三十分钟内发布的安装包 (发布时间>当前时间-30分钟)
        Date pastTime = new Date(now.getTime() - syncPackStatusTime);
        LambdaQueryWrapper<OpDeal> lambdaQueryWrapper = new LambdaQueryWrapper<OpDeal>().eq(
                OpDeal::getChannelId, ChannelConstant.JRTT)
            .eq(OpDeal::getPackState, PackStateConstant.PROCESSING)
            .gt(OpDeal::getPkgPublishTime, pastTime);
        List<OpDeal> dealList = opDealService.list(lambdaQueryWrapper);
        if (CollectionUtil.isEmpty(dealList)) {
            // 没有需要查询分包的广告
            return;
        }
        log.info("查询广告分包状态,数量:{}", dealList.size());
        // 按照包id进行分组
        Map<Integer, List<OpDeal>> collect = dealList.stream()
            .collect(Collectors.groupingBy(OpDeal::getPkgId));
        collect.forEach((dealId, deals) -> {
            Integer accountId = deals.get(0).getAccountId();
            Integer pkgId = deals.get(0).getPkgId();
            OpPkgModel opPkgModel = gameApi.getOpPkgById(pkgId);
            if (!(ChannelConstant.JRTT.equals(opPkgModel.getChannelId()) || ChannelConstant.XING_TU.equals(opPkgModel.getChannelId())) || StringUtils.isBlank(
                opPkgModel.getJrttConf().getPackageId())) {
                log.info("找不到对应的头条渠道信息,包id:{}", pkgId);
                return;
            }
            JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(accountId);
            JrttExtendPackageListV2Request jrttExtendPackageListV2Request = new JrttExtendPackageListV2Request();
            Integer page = 1;
            Integer pageSize = 100;
            jrttExtendPackageListV2Request.setPage(page);
            jrttExtendPackageListV2Request.setPageSize(pageSize);
            jrttExtendPackageListV2Request.setPackageId(opPkgModel.getJrttConf().getPackageId());
            jrttExtendPackageListV2Request.setAccountId(jrttTokenBo.getAdvertiserId());
            if (AccountLevelConstant.LEVEL_1.equals(jrttTokenBo.getLevelId())) {
                jrttExtendPackageListV2Request.setAccountType(JrttAccountType.BP);
            } else {
                jrttExtendPackageListV2Request.setAccountType(JrttAccountType.AD);
            }
            JrttExtendPackageListV2Response jrttExtendPackageListV2Response = null;
            while (page == 1 || page < jrttExtendPackageListV2Response.getPageInfo()
                .getTotalPage()) {
                log.info("正在查询分包信息,渠道游戏包id:{},页数:{}", pkgId, page);
                jrttExtendPackageListV2Request.setPage(page);
                jrttExtendPackageListV2Response = JrttPkgApi.extendPackageList(
                    jrttExtendPackageListV2Request, jrttTokenBo.getAccessToken());
                if (CollectionUtil.isEmpty(jrttExtendPackageListV2Response.getList())) {
                    break;
                }
                for (JrttExtendPackageListV2ListResponse jrttExtendPackageListV2ListResponse : jrttExtendPackageListV2Response.getList()) {
                    if (CollectionUtil.isEmpty(deals)) {
                        return;
                    }
                    // 遍历列表查看是否已经创建成功
                    for (int i = deals.size() - 1; i >= 0; i--) {
                        Integer id = deals.get(i).getId();
                        if (Objects.equals(jrttExtendPackageListV2ListResponse.getChannelId(),
                            id.toString())) {
                            log.info("分包状态,分包id:{},状态:{}", id,
                                jrttExtendPackageListV2ListResponse.getStatus());
                            OpDeal update = new OpDeal();
                            update.setId(id);
                            update.setPkgUpdateTime(now);
                            if (Objects.equals(jrttExtendPackageListV2ListResponse.getStatus(),
                                JrttExtendPackageStatus.ENABLE)) {
                                update.setPackState(PackStateConstant.SUCCESS);
                            } else if (Objects.equals(
                                jrttExtendPackageListV2ListResponse.getStatus(),
                                JrttExtendPackageStatus.AUDIT_REJECTED)) {
                                update.setPackState(PackStateConstant.FAILURE);
                            } else {
                                update.setPackState(PackStateConstant.PROCESSING);
                            }
                            opDealService.updateById(update);
                            deals.remove(i);
                        }
                    }
                }
                if (CollectionUtil.isEmpty(deals)) {
                    return;
                }
                page = page + 1;
            }
        });
    }

}
