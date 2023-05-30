package org.jeecg.modules.game.service;

import java.util.List;
import org.jeecg.modules.game.bo.OpCommodityBo;
import org.jeecg.modules.game.dto.OpCommodityDto;
import org.jeecg.modules.game.entity.OpCommodity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.game.vo.OpCommodityVo;

/**
 * @Description: op_commodity
 * @Author: jeecg-boot
 * @Date:   2022-12-12
 * @Version: V1.0
 */
public interface IOpCommodityService extends IService<OpCommodity> {

    /**
     * @param opCommodity
     * @return org.jeecg.modules.game.vo.OpCommodityVo
     * @Author lili
     * @Description 根据游戏id得到商品
     * @Date 15:31 2023/3/14
     **/
    OpCommodityVo getOpCommodity(OpCommodity opCommodity);

    /**
     * @param opCommodity
     * @Author lili
     * @Description 更新
     * @Date 15:49 2023/3/14
     **/
    void save(OpCommodityDto opCommodity);

    /**
     * @param pkgId
     * @param subGameId
     * @return java.util.List<org.jeecg.modules.game.bo.OpCommodityBo>
     * @author chenyw
     * @description 根据包id或者子游戏id获取
     * @date 14:39 2023/4/11/011
     **/
    List<OpCommodityBo> getOpCommodityByPkgIdAndSubGameId(Integer pkgId, Integer subGameId);

}
