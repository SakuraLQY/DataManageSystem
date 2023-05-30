package org.jeecg.modules.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.game.entity.OpPkgParent;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: op_pkg_parent
 * @Author: jeecg-boot
 * @Date:   2023-01-06
 * @Version: V1.0
 */
public interface IOpPkgParentService extends IService<OpPkgParent> {



    Result<String> deletePkgInfo(String id);

    Result<String> updatePkgInfo(Integer gameId, Integer subGameId, String fileName);

    /**
     * @param opPkgParent
     * @Author lili
     * @Description 添加
     * @Date 10:00 2023/2/6
     **/
    void add(OpPkgParent opPkgParent);
}
