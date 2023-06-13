package org.jeecg.modules.game.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.bo.ApkComment;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.config.SdkConfig;
import org.jeecg.modules.game.bo.GameSelectModal;
import org.jeecg.modules.game.bo.PackConfig;
import org.jeecg.common.constant.PackStateConstant;
import org.jeecg.modules.game.entity.OpGame;
import org.jeecg.modules.game.entity.OpPackConfig;
import org.jeecg.modules.game.entity.OpPkg;
import org.jeecg.modules.game.entity.OpPkgParent;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.mapper.OpGameMapper;
import org.jeecg.modules.game.mapper.OpPkgMapper;
import org.jeecg.modules.game.mapper.OpSubGameMapper;
import org.jeecg.modules.game.service.IOpPackConfigService;
import org.jeecg.modules.game.service.IOpPkgParentService;
import org.jeecg.modules.game.service.IOpPkgService;
import org.jeecg.modules.game.service.IOpSubGameService;
import org.jeecg.common.util.PackUtil;
import org.jeecg.modules.game.vo.GameObjVo;
import org.jeecg.modules.game.vo.GameVo;
import org.jeecg.modules.game.vo.OpPkgVo;
import org.jeecg.modules.game.vo.OpSubGameVo;
import org.jeecg.modules.game.vo.SubAndOpPackGameVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_pkg
 * @Author: jeecg-boot
 * @Date: 2023-01-12
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_gateway")
public class OpPkgServiceImpl extends ServiceImpl<OpPkgMapper, OpPkg> implements IOpPkgService {

    @Autowired
    private SdkConfig sdkConfig;
    @Autowired
    private JeecgBaseConfig jeecgBaseConfig;
    @Autowired
    private IOpPkgParentService opPkgParentService;
    @Autowired
    private IOpPackConfigService opPackConfigService;
    @Autowired
    private IOpSubGameService opSubGameService;
    @Autowired
    private OpPkgMapper opPkgMapper;
    @Autowired
    private OpGameMapper opGameMapper;
    @Autowired
    private OpSubGameMapper opSubGameMapper;

    @Override
    public List<SubAndOpPackGameVo> getOptionList(GameSelectModal gameSelectModal) {
        QueryWrapper<OpPkg> wrapper = new QueryWrapper<>();
        if (null != gameSelectModal.getChannelId() && !gameSelectModal.getChannelId().isEmpty()) {
            wrapper.in("a.channel_id", gameSelectModal.getChannelId());
        }
        List<OpPkgVo> list = opPkgMapper.getList(wrapper);
        Map<Integer, Map<Integer, List<OpPkgVo>>> map = list.stream()
            .collect(Collectors.groupingBy(OpPkgVo::getGameId,
                Collectors.groupingBy(OpPkgVo::getSubGameId)));
        List<SubAndOpPackGameVo> optionList = new ArrayList<>();
        for (Integer key : map.keySet()) {
            Map<Integer, List<OpPkgVo>> listOne = map.get(key);
            SubAndOpPackGameVo subAndOpPackGameVo = new SubAndOpPackGameVo();
            List<SubAndOpPackGameVo> subList = new ArrayList<>();
            for (Integer key2 : listOne.keySet()) {
                List<OpPkgVo> listTwo = listOne.get(key2);
                if (gameSelectModal.getType() == 0) {
                    subAndOpPackGameVo.setLabel(listTwo.get(0).getGameName());
                } else {
                    subAndOpPackGameVo.setTitle(listTwo.get(0).getGameName());
                }
                subAndOpPackGameVo.setValue("game" + key);
                SubAndOpPackGameVo subAndOpPackGameVo2 = new SubAndOpPackGameVo();
                List<SubAndOpPackGameVo> pkgList = new ArrayList<>();
                for (OpPkgVo opPkgVo : listTwo) {
                    if (gameSelectModal.getType() == 0) {
                        subAndOpPackGameVo2.setLabel(opPkgVo.getSubGameName());
                    } else {
                        subAndOpPackGameVo2.setTitle(opPkgVo.getSubGameName());
                    }
                    subAndOpPackGameVo2.setValue("subGame" + key2);
                    SubAndOpPackGameVo subAndOpPackGameVo3 = new SubAndOpPackGameVo();
                    if (gameSelectModal.getType() == 0) {
                        subAndOpPackGameVo3.setLabel(opPkgVo.getPkgName());
                    } else {
                        subAndOpPackGameVo3.setTitle(opPkgVo.getPkgName());

                    }
                    subAndOpPackGameVo3.setValue(String.valueOf(opPkgVo.getId()));
                    subAndOpPackGameVo3.setChildren(new ArrayList<>());
                    pkgList.add(subAndOpPackGameVo3);
                }
                subAndOpPackGameVo2.setChildren(pkgList);
                subList.add(subAndOpPackGameVo2);
            }
            subAndOpPackGameVo.setChildren(subList);
            optionList.add(subAndOpPackGameVo);
        }
        return optionList;
    }

    @Override
    public Map<Integer, GameVo> queryList() {
        QueryWrapper<OpSubGameVo> q = new QueryWrapper<>();
        q.eq("a.status", 0);
        //子游戏
        List<OpSubGameVo> opSubGameVoList = opSubGameMapper.getSubGameList(q);
        //游戏
        List<OpGame> opGameList = opGameMapper.selectList(null);
        //渠道游戏包
        List<OpPkgVo> opPkgList = opPkgMapper.getAll();
        Map<Integer, Map<Integer, List<OpPkgVo>>> opPkgCollect = new HashMap<>();

        Map<Integer, GameVo> resCollect = new HashMap<>();
        if (null != opPkgList && !opPkgList.isEmpty()) {
            opPkgCollect = opPkgList.stream()
                .collect(Collectors.groupingBy(OpPkgVo::getGameId,
                    Collectors.groupingBy(OpPkgVo::getSubGameId)));
            for (OpGame opGame : opGameList) {
                GameVo gameVo = new GameVo();
                gameVo.setId(opGame.getId());
                gameVo.setGameName(opGame.getGameName());
                Map<Integer, GameObjVo> gameVoCollect = new HashMap<>();
                for (OpSubGameVo opSubGameVo : opSubGameVoList) {
                    if (Objects.equals(opSubGameVo.getGameId(), opGame.getId())) {
                        GameObjVo gameObjVo = new GameObjVo();
                        gameObjVo.setId(opSubGameVo.getId());
                        gameObjVo.setGameName(opSubGameVo.getGameName());
                        if (opPkgCollect.containsKey(opGame.getId())) {
                            if (opPkgCollect.get(opGame.getId()).containsKey(opSubGameVo.getId())) {
                                List<GameObjVo> gameVoList = new ArrayList<>();
                                for (OpPkgVo osg : opPkgCollect.get(opGame.getId()).get(opSubGameVo.getId())) {
                                    GameObjVo gv = new GameObjVo();
                                    gv.setId(osg.getId());
                                    gv.setGameName(osg.getPkgName());
                                    gameVoList.add(gv);
                                }
                                gameObjVo.setList(gameVoList);
                            }
                        }
                        gameVoCollect.put(opSubGameVo.getId(), gameObjVo);
                    }
                }
                gameVo.setMap(gameVoCollect);
                resCollect.put(opGame.getId(), gameVo);
            }
        }
        return resCollect;
    }

    @Override
    public void updatePackState(Integer id) {
        OpPkg opPkg = getById(id);
        if (PackStateConstant.PROCESSING.equals(opPkg.getPackState())
            || PackStateConstant.WAIT.equals(opPkg.getPackState())) {
            throw new JeecgBootException("打包任务正在执行中，请稍等");
        }
        OpPkg update = new OpPkg();
        update.setId(id);
        update.setPackState(PackStateConstant.WAIT);
        updateById(update);
    }

    @Override
    public List<Map<String, Object>> getJrttPkg(Integer gameId, Integer subGameId,
        Integer channelId) {
        LambdaQueryWrapper<OpPkg> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(OpPkg::getId, OpPkg::getNickName);
        if (channelId.equals(ChannelConstant.XING_TU)) {
            wrapper.eq(OpPkg::getChannelId, ChannelConstant.JRTT).or()
                .eq(OpPkg::getChannelId, channelId);
        } else {
            wrapper.eq(OpPkg::getChannelId, channelId);
        }
        wrapper.eq(OpPkg::getGameId, gameId);
        wrapper.eq(OpPkg::getSubGameId, subGameId);
        return opPkgMapper.selectMaps(wrapper);
    }

    @Override
    public IPage<OpPkgVo> getPage(Page<OpPkg> page, QueryWrapper<OpPkg> queryWrapper) {
        IPage<OpPkg> opPkgPage = baseMapper.selectPage(page, queryWrapper);
        // 初始化Vo
        IPage<OpPkgVo> pageList = opPkgPage.convert(opPkg -> {
            OpPkgVo opPkgVo = new OpPkgVo();
            BeanUtils.copyProperties(opPkg, opPkgVo);
            String fullPath =
                jeecgBaseConfig.getPath().pkgFolderPath() + File.separator + opPkg.getId()
                    + ".apk";
            File file = new File(fullPath);
            if (file.exists()) {
                // 如果一级游戏包存在的话，返回路径
                String path =
                    jeecgBaseConfig.getPath().getPkgFolder() + File.separator + opPkg.getId()
                        + ".apk";
                opPkgVo.setApkPath(path);
            }
            return opPkgVo;
        });
        return pageList;
    }

    @Override
    public List<Integer> getIdsByGameIdOrSubGameId(List<String> ids) {
        List<Integer> pkgIds = new ArrayList<>();
        for (String id : ids) {
            List<OpPkg> list = new ArrayList<>();
            LambdaQueryWrapper<OpPkg> queryWrapper = new LambdaQueryWrapper<>();
            if (id.contains("game")) {
                id = id.replace("game", "");
                queryWrapper.eq(OpPkg::getGameId, id);
                list = opPkgMapper.selectList(queryWrapper);
                for (OpPkg opPkg : list) {
                    pkgIds.add(opPkg.getId());
                }
            } else if (id.contains("subGame")) {
                id = id.replace("subGame", "");
                queryWrapper.eq(OpPkg::getSubGameId, id);
                list = opPkgMapper.selectList(queryWrapper);
                for (OpPkg opPkg : list) {
                    pkgIds.add(opPkg.getId());
                }
            } else {
                pkgIds.add(Integer.valueOf(id));
            }
        }
        return pkgIds;
    }

    @Override
    public OpPkg getPkgByPackageName(String packageName) {
        LambdaQueryWrapper<OpPkg> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpPkg::getPackageName, packageName);
        return opPkgMapper.selectOne(wrapper);
    }

    @Override
    public OpPkg getPkgById(Integer pkgId) {
        return getById(pkgId);
    }

    @Override
    public void saveOpPkg(OpPkg opPkg) {
        long count = count(
            new LambdaQueryWrapper<OpPkg>().eq(OpPkg::getSubGameId, opPkg.getSubGameId()));
        count++;
        OpSubGame opSubGame = opSubGameService.getById(opPkg.getSubGameId());
        // 渠道游戏包名在新增的时候自动生成
        opPkg.setPkgName(opSubGame.getGameName() + "-" + count + "号包:" + opPkg.getNickName());
        opPkg.setStatus(CommonConstant.DEL_FLAG_0);
        save(opPkg);
    }

    @Override
    public void deleteOpPkgById(Integer id) {
        OpPkg update = new OpPkg();
        update.setId(id);
        update.setStatus(CommonConstant.DEL_FLAG_1);
        updateById(update);
    }

    @Override
    public void deleteOpPkgByIds(String ids) {
        List<Integer> idList = Arrays.asList(ids.split(",")).stream().map(Integer::valueOf).collect(
            Collectors.toList());
        List<OpPkg> updateList = new ArrayList<>();
        for (Integer id : idList) {
            OpPkg update = new OpPkg();
            update.setId(id);
            update.setStatus(CommonConstant.DEL_FLAG_1);
            updateList.add(update);
        }
        updateBatchById(updateList);
    }

    /**
     * @author chenyw
     * @description 打包任务定时执行
     * @date 10:28 2023/1/19/019
     **/
    @Scheduled(cron = "0/5 * * * * ?")
    public void packTask() {
        QueryWrapper<OpPkg> queryWrapper = new QueryWrapper();
        List<OpPkg> opPkgList = list(
            queryWrapper.lambda().eq(OpPkg::getPackState, PackStateConstant.WAIT));
        if (CollectionUtil.isEmpty(opPkgList)) {
            return;
        }
        for (OpPkg opPkg : opPkgList) {
            log.info("正在执行打包任务,包id:{}", opPkg.getId());
            OpPkg update = new OpPkg();
            update.setId(opPkg.getId());
            // 获母包取包信息
            try {
                OpPkgParent pkgParent = opPkgParentService.getOne(
                    new LambdaQueryWrapper<OpPkgParent>().eq(OpPkgParent::getSubGameId,
                        opPkg.getSubGameId()));
                if (null == pkgParent) {
                    log.error("打包失败,没有配置游戏母包,包id:{}", opPkg.getId());
                    update.setPackState(PackStateConstant.FAILURE);
                    updateById(update);
                    break;
                }
                // 修改打包状态为进行中
                update.setPackState(PackStateConstant.PROCESSING);
                updateById(update);
                // 母包地址
                String usedApkPath =
                    jeecgBaseConfig.getPath().parentPkgFolderPath() + File.separator
                        + opPkg.getGameId()
                        + File.separator + opPkg.getSubGameId() + File.separator
                        + pkgParent.getApkName();
                String newApkPath = jeecgBaseConfig.getPath().pkgFolderPath() + File.separator
                    + opPkg.getId() + ".apk";
                // 资源列表
                OpPackConfig opPackConfig = opPackConfigService.getOne(
                    new LambdaQueryWrapper<OpPackConfig>().eq(OpPackConfig::getSubGameId,
                        opPkg.getSubGameId()));
                String resPathList = "";
                if (null != opPackConfig && StringUtils.isNotEmpty(opPackConfig.getPackConfig())) {
                    PackConfig packConfig = JSONObject.parseObject(opPackConfig.getPackConfig(),
                        PackConfig.class);
                    boolean hasBefore = false;
                    if (StringUtils.isNotEmpty(packConfig.getScreenPath())
                        && StringUtils.isNotEmpty(
                        opPkg.getScreenMaterialPackPath())) {
                        hasBefore = true;
                        resPathList +=
                            jeecgBaseConfig.getPath().getUpload() + SymbolConstant.SINGLE_SLASH
                                + opPkg.getScreenMaterialPackPath() + SymbolConstant.DOUBLE_COLON
                                + packConfig.getScreenPath();
                    }
                    if (StringUtils.isNotEmpty(packConfig.getLoadingPath())
                        && StringUtils.isNotEmpty(
                        opPkg.getLoadingMaterialPackPath())) {
                        if (hasBefore) {
                            resPathList += SymbolConstant.DOUBLE_AT;
                        }
                        hasBefore = true;
                        resPathList +=
                            jeecgBaseConfig.getPath().getUpload() + SymbolConstant.SINGLE_SLASH
                                + opPkg.getLoadingMaterialPackPath() + SymbolConstant.DOUBLE_COLON
                                + packConfig.getLoadingPath();
                    }

                    if (StringUtils.isNotEmpty(packConfig.getLoginPath()) && StringUtils.isNotEmpty(
                        opPkg.getLoginMaterialPackPath())) {
                        if (hasBefore) {
                            resPathList += SymbolConstant.DOUBLE_AT;
                        }
                        hasBefore = true;
                        resPathList +=
                            jeecgBaseConfig.getPath().getUpload() + SymbolConstant.SINGLE_SLASH
                                + opPkg.getLoginMaterialPackPath() + SymbolConstant.DOUBLE_COLON
                                + packConfig.getLoginPath();
                    }
                    if (StringUtils.isNotEmpty(packConfig.getLogoPath()) && StringUtils.isNotEmpty(
                        opPkg.getLogoMaterialPackPath())) {
                        if (hasBefore) {
                            resPathList += SymbolConstant.DOUBLE_AT;
                        }
                        resPathList +=
                            jeecgBaseConfig.getPath().getUpload() + SymbolConstant.SINGLE_SLASH
                                + opPkg.getLogoMaterialPackPath() + SymbolConstant.DOUBLE_COLON
                                + packConfig.getLogoPath();
                    }
                } else {
                    log.warn("游戏包id:{}找不到子游戏的打包配置", opPkg.getId());
                }
                String iconPath =
                    jeecgBaseConfig.getPath().getUpload() + SymbolConstant.SINGLE_SLASH
                        + opPkg.getIconMaterialPackPath();
                boolean result = PackUtil.pythonPackage(sdkConfig.getPackToolPath(), usedApkPath,
                    newApkPath,
                    iconPath, opPkg.getNickName(), opPkg.getPackageName(), opPkg.getId().toString(),
                    resPathList, opPkg.getVersionName(), opPkg.getVersionCode());
                if (!result) {
                    update.setPackState(PackStateConstant.FAILURE);
                    updateById(update);
                    log.error("打包失败,python打包命令执行失败,包id:{}", opPkg.getId());
                    break;
                }
                ApkComment apkComment = new ApkComment();
                apkComment.setPkgId(opPkg.getId());
                String comment = JSONObject.toJSONString(apkComment);
                result = PackUtil.setApkComment(newApkPath, comment);
                if (!result) {
                    update.setPackState(PackStateConstant.FAILURE);
                    updateById(update);
                    log.error("打包失败,写入apk备注失败,包id:{}", opPkg.getId());
                    break;
                }
                update.setPackState(PackStateConstant.SUCCESS);
                updateById(update);
                log.info("打包成功,包id:{}", opPkg.getId());
            } catch (Exception e) {
                log.error("打包失败,包id:{},exception: {}", opPkg.getId(), e);
                update.setPackState(PackStateConstant.FAILURE);
                updateById(update);
            }
        }
    }
}
