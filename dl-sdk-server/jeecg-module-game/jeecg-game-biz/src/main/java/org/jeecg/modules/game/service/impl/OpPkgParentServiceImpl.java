package org.jeecg.modules.game.service.impl;


import cn.hutool.core.io.FileUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.io.File;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.modules.game.entity.OpFileChunk;
import org.jeecg.modules.game.entity.OpPackConfig;
import org.jeecg.modules.game.entity.OpPkgParent;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.mapper.OpFileChunkMapper;
import org.jeecg.modules.game.mapper.OpPackConfigMapper;
import org.jeecg.modules.game.mapper.OpPkgParentMapper;
import org.jeecg.modules.game.service.IOpPkgParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_pkg_parent
 * @Author: jeecg-boot
 * @Date: 2023-01-06
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_gateway")
public class OpPkgParentServiceImpl extends ServiceImpl<OpPkgParentMapper, OpPkgParent> implements
    IOpPkgParentService {

    @Autowired
    private JeecgBaseConfig jeecgBaseConfig;

    @Autowired
    private OpFileChunkMapper fileChunkMapper;

    @Autowired
    private OpPackConfigMapper opPackConfigMapper;

    /**
     * @param id: 母包id
     * @return Result<String>
     * @author
     * @description 删除母包
     * @date 2023/1/7 14:45
     */
    @Override
    public Result<String> deletePkgInfo(String id) {
        OpPkgParent pkgParent = getById(id);
        if (null == pkgParent) {
            return Result.error("母包信息不存在");
        }
        fileChunkMapper.delete(
            new LambdaQueryWrapper<OpFileChunk>().eq(OpFileChunk::getGameId, pkgParent.getGameId())
                .eq(OpFileChunk::getSubGameId, pkgParent.getSubGameId()));
        String realPath =
            jeecgBaseConfig.getPath().parentPkgFolderPath() + File.separator + pkgParent.getGameId()
                + File.separator + pkgParent.getSubGameId();
        if (!FileUtil.del(realPath)) {
            log.error("删除母包失败，请检查路径是否正确，路径 ： {}", realPath);
            return Result.error("删除失败，请联系管理员");
        }
        removeById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result<String> updatePkgInfo(Integer gameId, Integer subGameId, String fileName) {
        OpPkgParent pkgParent = getOne(
            new LambdaQueryWrapper<OpPkgParent>().eq(OpPkgParent::getGameId, gameId)
                .eq(OpPkgParent::getSubGameId, subGameId));
        String realPath = jeecgBaseConfig.getPath().parentPkgFolderPath() + File.separator + gameId
            + File.separator + subGameId + File.separator + fileName;
        try {
            ApkFile apkFile = new ApkFile(realPath);
            ApkMeta apkMeta = apkFile.getApkMeta();
            pkgParent.setApkName(fileName);
            pkgParent.setGameName(apkMeta.getLabel());
            pkgParent.setPackageName(apkMeta.getPackageName());
            pkgParent.setVersion(apkMeta.getVersionName());
            pkgParent.setPkgUpdateTime(new Date());
            if (pkgParent.getState() == 0) {
                pkgParent.setState(1);
            }
            updateById(pkgParent);
            apkFile.close();
        } catch (Exception e) {
            throw new JeecgBootException("更新母包信息失败");
        }
        return Result.ok();
    }

    @Override
    public void add(OpPkgParent opPkgParent) {
        save(opPkgParent);
        //添加打包配置
        OpPackConfig opPackConfig = new OpPackConfig();
        opPackConfig.setSubGameId(opPkgParent.getSubGameId());
        opPackConfig.setPackConfig(
            "{\"screen_type\":\"\",\"screen_width\":\"\",\"screen_height\":\"\",\"screen_path\":\"\",\"loading_type\":\"\",\"loading_width\":\"\",\"loading_height\":\"\",\"loading_path\":\"\",\"login_type\":\"\",\"login_width\":\"\",\"login_height\":\"\",\"login_path\":\"\",\"logo_type\":\"\",\"logo_width\":\"\",\"logo_height\":\"\",\"logo_path\":\"\"}");
        opPackConfigMapper.insert(opPackConfig);
    }

}
