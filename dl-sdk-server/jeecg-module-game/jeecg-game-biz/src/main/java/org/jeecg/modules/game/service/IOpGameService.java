package org.jeecg.modules.game.service;

import org.jeecg.modules.game.common.ResultGameAndSubGameList;
import org.jeecg.modules.game.entity.OpGame;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: op_game
 * @Author: jeecg-boot
 * @Date: 2022-12-08
 * @Version: V1.0
 */
public interface IOpGameService extends IService<OpGame> {

  /**
   * @return org.jeecg.modules.game.common.ResultGameAndSubGameList<org.jeecg.modules.game.entity.OpGame>
   * @Author lili
   * @Description 得到游戏和子游戏列表
   * @Date 15:38 2022/12/21
   **/
  ResultGameAndSubGameList<OpGame> getGameAndSubGameList();



}
