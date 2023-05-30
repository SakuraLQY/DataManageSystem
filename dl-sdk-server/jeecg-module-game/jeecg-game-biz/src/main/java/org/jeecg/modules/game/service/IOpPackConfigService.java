package org.jeecg.modules.game.service;

import java.util.List;
import java.util.Map;
import org.jeecg.modules.game.entity.OpPackConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.game.vo.OpPackConfigVo;

/**
 * @Description: op_pack_config
 * @Author: jeecg-boot
 * @Date:   2023-01-07
 * @Version: V1.0
 */
public interface IOpPackConfigService extends IService<OpPackConfig> {

    /**
     * @param opPackConfig
     * @return org.jeecg.modules.game.vo.OpPackConfigVo
     * @Author lili
     * @Description 查询对象
     * @Date 11:47 2023/1/9
     **/
    OpPackConfigVo getObjByGameId(OpPackConfig opPackConfig);

    /**
     * @param opPackConfigVo
     * @Author lili
     * @Description 编辑
     * @Date 13:51 2023/1/9
     **/
    void update(OpPackConfigVo opPackConfigVo);
}
