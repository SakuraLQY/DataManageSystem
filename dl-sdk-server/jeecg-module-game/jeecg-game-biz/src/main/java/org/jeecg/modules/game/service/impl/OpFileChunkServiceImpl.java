package org.jeecg.modules.game.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.modules.game.entity.OpFileChunk;
import org.jeecg.modules.game.entity.OpPkgParent;
import org.jeecg.modules.game.mapper.OpFileChunkMapper;
import org.jeecg.modules.game.mapper.OpPkgParentMapper;
import org.jeecg.modules.game.service.IOpFileChunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_file_chunk
 * @Author: jeecg-boot
 * @Date: 2023-01-18
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_gateway")
public class OpFileChunkServiceImpl extends ServiceImpl<OpFileChunkMapper, OpFileChunk> implements
    IOpFileChunkService {

    /**
     * 默认的分片大小：20MB
     */
    @Value("${parentPkgUpload.chunk-size}")
    public int DEFAULT_CHUNK_SIZE;

    @Autowired
    private JeecgBaseConfig jeecgBaseConfig;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private OpPkgParentMapper pkgParentMapper;

    @Override
    public Result<Map<String, Object>> checkUpload(String identifier, Integer gameId, Integer subGameId) {
        List<OpFileChunk> chunks = list(new LambdaQueryWrapper<OpFileChunk>()
            .eq(OpFileChunk::getIdentifier, identifier).eq(OpFileChunk::getGameId,gameId).eq(OpFileChunk::getSubGameId, subGameId));
        HashMap<String, Object> data = new HashMap<>();
        List<Integer> uploadedChunks = chunks.stream().map(OpFileChunk::getChunkNumber)
            .collect(Collectors.toList());
        data.put("uploadedChunks", uploadedChunks);
        return Result.OK(data);
    }

    @Override
    public Result<Map<String, Object>> uploadFile(OpFileChunk opFileChunk) {
        if (opFileChunk.getFile() == null) {
            throw new JeecgBootException("上传文件为空");
        }
        String realPath = jeecgBaseConfig.getPath().parentPkgFolderPath() + File.separator
            + opFileChunk.getGameId() + File.separator + opFileChunk.getSubGameId();
        File file = new File(realPath);
        HashMap<String, Object> data = new HashMap<>();
        data.put("chunkNumber", opFileChunk.getChunkNumber());
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new JeecgBootException("创建文件目录失败");
            }
        }
        String fileName = opFileChunk.getFileName();
        String fullFileName = file + File.separator + fileName;
        if (uploadFileByRandomAccessFile(fullFileName, opFileChunk)) {
            save(opFileChunk);
            String key =
                opFileChunk.getGameId() + "-" + opFileChunk.getSubGameId() + "-" + "successChunks";
            String s = redisTemplate.opsForValue().get(key);
            Long successChunks;
            if (s == null) {
                redisTemplate.opsForValue().set(key, String.valueOf(1), 10, TimeUnit.MINUTES);
                successChunks = 1L;
            } else {
                successChunks = redisTemplate.opsForValue().increment(key);
            }
            if (successChunks.intValue() >= opFileChunk.getTotalChunk()) {
                updatePkgInfo(fullFileName, opFileChunk);
                redisTemplate.delete(key);
            }
            data.put("currentChunkSize", opFileChunk.getCurrentChunkSize());
            return Result.OK(data);
        }

        return Result.error("上传分片失败", data);
    }

    private void updatePkgInfo(String fullFileName, OpFileChunk opFileChunk) {
        try {
            OpPkgParent pkgParent = pkgParentMapper.selectOne(new LambdaQueryWrapper<OpPkgParent>()
                .eq(OpPkgParent::getGameId, opFileChunk.getGameId())
                .eq(OpPkgParent::getSubGameId, opFileChunk.getSubGameId()));
            ApkFile apkFile = new ApkFile(fullFileName);
            ApkMeta apkMeta = apkFile.getApkMeta();
            pkgParent.setApkName(opFileChunk.getFileName());
            pkgParent.setGameName(apkMeta.getLabel());
            pkgParent.setPackageName(apkMeta.getPackageName());
            pkgParent.setVersion(apkMeta.getVersionName());
            pkgParent.setVersionCode(apkMeta.getVersionCode().toString());
            pkgParent.setPkgUpdateTime(new Date());
            if (pkgParent.getState() == 0) {
                pkgParent.setState(1);
            }
            pkgParentMapper.updateById(pkgParent);
            apkFile.close();
        } catch (Exception e) {
            throw new JeecgBootException("更新母包信息失败");
        }
    }

    private boolean uploadFileByRandomAccessFile(String fullFileName, OpFileChunk opFileChunk) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fullFileName, "rw")) {
            long chunkSize = opFileChunk.getChunkSize() == 0L ? DEFAULT_CHUNK_SIZE
                : opFileChunk.getChunkSize().longValue();
            long offset = chunkSize * (opFileChunk.getChunkNumber() - 1);
            randomAccessFile.seek(offset);
            randomAccessFile.write(opFileChunk.getFile().getBytes());
        } catch (IOException e) {
            log.error("文件上传失败:{}", e);
            return false;
        }
        return true;
    }

}
