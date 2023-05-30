package org.jeecg.modules.game.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.modules.game.common.ResultGameAndSubGameList;
import org.jeecg.modules.game.entity.OpGame;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.mapper.OpGameMapper;
import org.jeecg.modules.game.mapper.OpSubGameMapper;
import org.jeecg.modules.game.service.IOpGameService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: op_game
 * @Author: jeecg-boot
 * @Date: 2022-12-08
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpGameServiceImpl extends ServiceImpl<OpGameMapper, OpGame> implements IOpGameService {

  @Resource
  private OpGameMapper opGameMapper;

  @Resource
  private OpSubGameMapper opSubGameMapper;

  @Override
  public ResultGameAndSubGameList<OpGame> getGameAndSubGameList() {
    List<OpGame> gameList = opGameMapper.selectByMap(null);
    List<OpSubGame> subGameList = opSubGameMapper.selectByMap(null);
    return new ResultGameAndSubGameList<>(gameList,subGameList);
  }
}
