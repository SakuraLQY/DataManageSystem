package org.jeecg.modules.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.game.entity.OpFileChunk;

/**
 * @Description: op_file_chunk
 * @Author: jeecg-boot
 * @Date:   2023-01-18
 * @Version: V1.0
 */
public interface IOpFileChunkService extends IService<OpFileChunk> {

    Result<Map<String, Object>> checkUpload(String identifier, Integer gameId, Integer subGameId);

    Result<Map<String, Object>> uploadFile(OpFileChunk opFileChunk);
}
