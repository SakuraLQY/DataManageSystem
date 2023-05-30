package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.SubGameTypeConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.advert.api.jrtt.assets.api.JrttAssetApi;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttAddAssetsAppAssetRequest;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttAddAssetsRequest;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttAddAssetsResponse;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetAssetsAppResponse;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetAssetsRequest;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetAssetsResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttOtherConstant;
import org.jeecg.modules.advert.dto.OpJrttAssetsDto;
import org.jeecg.modules.advert.entity.OpJrttAssets;
import org.jeecg.modules.advert.entity.OpJrttAudience;
import org.jeecg.modules.advert.entity.OpJrttPutAccount;
import org.jeecg.modules.advert.mapper.OpJrttAssetsMapper;
import org.jeecg.modules.advert.service.IOpJrttAssetsService;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.vo.OpJrttAssetsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jeecg.common.game.api.IGameApi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.CollectionUtils;

/**
 * @Description: op_jrtt_assets
 * @Author: jeecg-boot
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpJrttAssetsServiceImpl extends
    ServiceImpl<OpJrttAssetsMapper, OpJrttAssets> implements
    IOpJrttAssetsService {

    @Autowired
    private IGameApi gameApi;
    @Autowired
    private OpJrttAssetsMapper opJrttAssetsMapper;
    @Autowired
    private IOpJrttPutAccountService opJrttPutAccountService;


    @Override
    public void add(OpJrttAssetsDto opJrttAssets) {
        List<String> pkgIdList = opJrttAssets.getPkgIdList();
        Integer pkgId = Integer.valueOf(pkgIdList.get(2));
        //判断数据库中是否存在记录
        LambdaQueryWrapper<OpJrttAssets> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpJrttAssets::getPkgId,pkgId);
        wrapper.eq(OpJrttAssets::getAccountId,opJrttAssets.getAccountId());
        wrapper.eq(OpJrttAssets::getChannelId,opJrttAssets.getChannelId());
        if (oConvertUtils.isNotEmpty(opJrttAssetsMapper.selectOne(wrapper))) {
            throw new JeecgBootException("包名已经存在，请不要重复创建同包名的资产");
        }
        Integer gameId = Integer.valueOf(pkgIdList.get(0).substring(4, pkgIdList.get(0).length()));
        Integer subGameId = Integer.valueOf(
            pkgIdList.get(1).substring(7, pkgIdList.get(1).length()));
        //根据accountId得到广告主ID和accessToken
        LambdaQueryWrapper<OpJrttPutAccount> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(OpJrttPutAccount::getAccountId, opJrttAssets.getAccountId());
        OpJrttPutAccount opJrttPutAccount = opJrttPutAccountService.getOne(wrapper2);
        String accessToken = opJrttPutAccountService.getAccountToken(opJrttAssets.getAccountId())
            .getAccessToken();
        Long advertiserId = opJrttPutAccount.getAdvertiserId();
        //查询一级游戏包昵称
        OpPkgModel opPkgModel = gameApi.getOpPkgById(pkgId);
        String name = opPkgModel.getNickName();
        if(oConvertUtils.isEmpty(opPkgModel.getJrttConf())) {
            throw new JeecgBootException("该渠道游戏包未配置头条参数");
        }
        String packageName = opPkgModel.getJrttConf().getPackageName();
        String downloadUrl = opPkgModel.getDownloadUrl();
        OpSubGameModel opSubGameModel = gameApi.getOpSubGame(subGameId);
        String appId;
        String packageId = "";
        String appType = "IOS";
        //添加资产
        JrttAddAssetsRequest jrttAddAssetsRequest = new JrttAddAssetsRequest();
        jrttAddAssetsRequest.setAdvertiserId(Long.valueOf(advertiserId));
        jrttAddAssetsRequest.setAssetType("APP");
        JrttAddAssetsAppAssetRequest jrttAddAssetsAppAssetRequest = new JrttAddAssetsAppAssetRequest();
        jrttAddAssetsAppAssetRequest.setName(name);
        jrttAddAssetsAppAssetRequest.setPackageName(packageName);
        jrttAddAssetsAppAssetRequest.setDownloadUrl(downloadUrl);
        //安卓
        if (SubGameTypeConstant.ANDROID.equals(opSubGameModel.getGameType())) {
            appId = opPkgModel.getJrttConf().getAppId();
            packageId = opPkgModel.getJrttConf().getPackageId();
            appType = "Android";
            jrttAddAssetsAppAssetRequest.setAppId(Integer.valueOf(appId));
            jrttAddAssetsAppAssetRequest.setPackageId(packageId);
        }
        jrttAddAssetsAppAssetRequest.setAppType(appType);
        jrttAddAssetsRequest.setAppAsset(jrttAddAssetsAppAssetRequest);
        JrttBaseResponse<JrttAddAssetsResponse> jrttBaseResponse = JrttAssetApi.addAssets(accessToken,
            jrttAddAssetsRequest);
        //code不为0
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            //线上存在已有的资产
            if (JrttOtherConstant.JRTT_SAME_ASSETS.equals(jrttBaseResponse.getMessage())) {
                OpJrttAssetsDto opJrttAssetsDto = new OpJrttAssetsDto();
                opJrttAssetsDto.setAccountId(opJrttAssets.getAccountId());
                syncAssets(opJrttAssetsDto);
                throw new JeecgBootException("线上同包名的资产已存在，请不要重复创建，现已同步，请刷新");
            }else {
                throw new JeecgBootException(jrttBaseResponse.getMessage());
            }
        }
        JrttAddAssetsResponse jrttAddAssetsResponse = jrttBaseResponse.getData();
        Long assetId = jrttAddAssetsResponse.getAssetId();
        OpJrttAssets newOpJrttAssets = new OpJrttAssets();
        newOpJrttAssets.setAssetId(assetId);
        newOpJrttAssets.setAssetName(name);
        newOpJrttAssets.setGameId(gameId);
        newOpJrttAssets.setSubGameId(subGameId);
        newOpJrttAssets.setPkgId(pkgId);
        newOpJrttAssets.setAccountId(opJrttAssets.getAccountId());
        newOpJrttAssets.setCreateBy(opJrttAssets.getCreateBy());
        newOpJrttAssets.setCreateTime(opJrttAssets.getCreateTime());
        newOpJrttAssets.setChannelId(opJrttAssets.getChannelId());
        opJrttAssetsMapper.insert(newOpJrttAssets);
    }

    @Override
    public IPage<OpJrttAssetsVo> selectList(Page<OpJrttAssetsVo> page,
        OpJrttAssetsDto opJrttAssetsDto) {
        LambdaQueryWrapper<OpJrttAssets> wrapper = new LambdaQueryWrapper<>();
        if (null != opJrttAssetsDto.getAssetName()) {
            wrapper.like(OpJrttAssets::getAssetName, opJrttAssetsDto.getAssetName());
        }
        if (null != opJrttAssetsDto.getPkgIdList() && !opJrttAssetsDto.getPkgIdList().isEmpty()) {
            List<Integer> gameIdList = new ArrayList<>();
            List<Integer> subGameIdList = new ArrayList<>();
            List<Integer> pkgIdList = new ArrayList<>();
            for (String id : opJrttAssetsDto.getPkgIdList()) {
                if (id.contains("game")) {
                    id = id.replace("game", "");
                    id = id.replaceAll(" ", "");
                    gameIdList.add(Integer.valueOf(id));
                } else if (id.contains("subGame")) {
                    id = id.replace("subGame", "");
                    id = id.replaceAll(" ", "");
                    subGameIdList.add(Integer.valueOf(id));
                } else {
                    pkgIdList.add(Integer.valueOf(id));
                }
            }
            if (!gameIdList.isEmpty()) {
                wrapper.or().in(OpJrttAssets::getGameId, gameIdList);
            }
            if (!subGameIdList.isEmpty()) {
                wrapper.or().in(OpJrttAssets::getSubGameId, subGameIdList);
            }
            if (!pkgIdList.isEmpty()) {
                wrapper.or().in(OpJrttAssets::getPkgId, pkgIdList);
            }
        }
        if (null != opJrttAssetsDto.getAccountId()) {
            wrapper.eq(OpJrttAssets::getAccountId, opJrttAssetsDto.getAccountId());
        }
        if (null != opJrttAssetsDto.getCreateBy()) {
            wrapper.eq(OpJrttAssets::getCreateBy, opJrttAssetsDto.getCreateBy());
        }
        if (null != opJrttAssetsDto.getStartTime()) {
            wrapper.ge(OpJrttAssets::getCreateTime, opJrttAssetsDto.getStartTime());
        }
        if (null != opJrttAssetsDto.getEndTime()) {
            wrapper.le(OpJrttAssets::getCreateTime, opJrttAssetsDto.getEndTime());
        }
        List<OpJrttAssets> list = opJrttAssetsMapper.selectList(wrapper);
        List<OpJrttAssetsVo> newList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (OpJrttAssets opJrttAssets : list) {
                OpJrttAssetsVo opJrttAssetsVo = new OpJrttAssetsVo();
                BeanUtils.copyProperties(opJrttAssets, opJrttAssetsVo);
                //通过子游戏id得到游戏类型
                Integer gameType = gameApi.getOpSubGame(opJrttAssets.getSubGameId()).getGameType();
                opJrttAssetsVo.setGameType(gameType);
                newList.add(opJrttAssetsVo);
            }
        }
        IPage<OpJrttAssetsVo> pageList = new Page<>(page.getCurrent(), page.getSize());
        pageList.setRecords(Lists.reverse(newList));
        assert list != null;
        pageList.setTotal(list.size());
        return pageList;
    }

    @Override
    public void syncAssets(OpJrttAssetsDto opJrttAssets) {
        List<String> list = Arrays.asList(opJrttAssets.getAccountIdList().split(","));
        List<Integer> newList = list.stream().map(Integer::parseInt).collect(Collectors.toList());
        for (Integer accountId : newList) {
            JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(accountId);
            JrttGetAssetsRequest jrttGetAssetsRequest = new JrttGetAssetsRequest();
            jrttGetAssetsRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
            jrttGetAssetsRequest.setAssetType("APP");
            jrttGetAssetsRequest.setPageSize(30);
            JrttGetAssetsResponse jrttGetAssetsResponse = JrttAssetApi.getAssets(jrttTokenBo.getAccessToken(),
                jrttGetAssetsRequest);
            //查询该广告主已存在的资产
            List<Long> currentAssetsList = opJrttAssetsMapper.getAssetsIdList(
                accountId);
            List<Long> assetsIdList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(jrttGetAssetsResponse.getApp())) {
                List<JrttGetAssetsAppResponse> getAssetsList = new ArrayList<>(
                    jrttGetAssetsResponse.getApp());
                Integer page = 1;
                while (jrttGetAssetsResponse.getApp().size() == 30) {
                    page++;
                    jrttGetAssetsRequest.setPage(page);
                    jrttGetAssetsRequest.setPageSize(30);
                    jrttGetAssetsResponse = JrttAssetApi.getAssets(jrttTokenBo.getAccessToken(),
                        jrttGetAssetsRequest);
                    if (!CollectionUtils.isEmpty(jrttGetAssetsResponse.getApp())) {
                        getAssetsList.addAll(jrttGetAssetsResponse.getApp());
                    }
                }

                for (JrttGetAssetsAppResponse jrttGetAssetsAppResponse : getAssetsList) {
                    //存在该一级游戏包，可以将该条数据存入数据库
                    OpPkgModel opPkgModel = gameApi.getPkgByPackageName(
                        jrttGetAssetsAppResponse.getPackageName());
                    if (null != opPkgModel) {
                        OpJrttAssets addOpJrttAssets = new OpJrttAssets();
                        addOpJrttAssets.setAssetId(jrttGetAssetsAppResponse.getAssetId());
                        addOpJrttAssets.setAssetName(
                            String.valueOf(jrttGetAssetsAppResponse.getAssetName()));
                        addOpJrttAssets.setGameId(opPkgModel.getGameId());
                        addOpJrttAssets.setSubGameId(opPkgModel.getSubGameId());
                        addOpJrttAssets.setPkgId(opPkgModel.getId());
                        addOpJrttAssets.setAccountId(accountId);
                        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
                        addOpJrttAssets.setCreateBy(sysUser.getUsername());
                        addOpJrttAssets.setCreateTime(new Date());
                        addOpJrttAssets.setChannelId(4);
                        //数据库中包含的数据不包含远程数据
                        if (!currentAssetsList.contains(jrttGetAssetsAppResponse.getAssetId())) {
                            assetsIdList.add(addOpJrttAssets.getAssetId());
                            opJrttAssetsMapper.insert(addOpJrttAssets);
                        }else {
                            assetsIdList.add(addOpJrttAssets.getAssetId());
                        }
                    }

                }

            }
            //删除本地有远程没有的数据
            for (Long assetsId : currentAssetsList) {
                if (!assetsIdList.contains(assetsId)) {
                    LambdaQueryWrapper<OpJrttAssets> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(OpJrttAssets::getAssetId, assetsId);
                    opJrttAssetsMapper.deleteById(
                        opJrttAssetsMapper.selectOne(wrapper).getId());
                }
            }


        }
    }
}
