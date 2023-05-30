package org.jeecg.common.api;

import org.jeecg.common.function.bo.GetNameByIdDto;
import org.jeecg.common.function.vo.GetNameByIdVo;
import org.jeecg.common.game.vo.OpGameModel;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;

public interface GameApi {

    /**
     * @param subGameId
     * @return org.jeecg.common.game.vo.OpSubGameModel
     * @author chenyw
     * @description 根据子游戏id获得子游戏信息
     * @date 16:30 2022/12/19/019
     **/
    OpSubGameModel getOpSubGame(Integer subGameId);

    /**
     * @param id
     * @return org.jeecg.common.game.vo.OpGameModel
     * @Author lili
     * @Description 根据id得到游戏对象
     * @Date 16:41 2023/3/15
     **/
    OpGameModel getOpGame(Integer id);

    /**
     * @param id
     * @return org.jeecg.common.game.vo.OpPkgModel
     * @Author lili
     * @Description 根据ID得到对象
     * @Date 15:41 2023/2/13
     **/
    OpPkgModel getOpPkgById(Integer id);

}
