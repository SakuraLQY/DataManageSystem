package org.jeecg.modules.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.game.dto.GameConfModel;
import org.jeecg.modules.game.entity.OpSubGame;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.game.vo.GameObjVo;
import org.jeecg.modules.game.vo.OpSubGameVo;

import java.util.List;
import org.jeecg.modules.game.vo.SubAndOpPackGameVo;

/**
 * @Description: op_son_game
 * @Author: jeecg-boot
 * @Date: 2022-12-09
 * @Version: V1.0
 */
public interface IOpSubGameService extends IService<OpSubGame> {

    /**
     * @param page
     * @param opSubGame
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.demo.game.vo.OpSubGameVo>
     * @Author lili
     * @Description 连表查询列表
     * @Date 10:56 2022/12/16
     **/
    IPage<OpSubGameVo> getSubGameList(Page<T> page, OpSubGame opSubGame);

    /**
     * @param opSubGame
     * @return org.jeecg.modules.demo.game.common.ResultSubGameListPage<org.jeecg.modules.demo.game.vo.OpSubGameVo>
     * @Author lili
     * @Description 下拉框值
     * @Date 10:47 2022/12/16
     **/
    Map<Integer, GameObjVo> getOptionList(OpSubGame opSubGame);

    /**
     * @return java.util.Map<java.lang.Integer, java.util.Map < java.lang.Integer, org.jeecg.modules.game.vo.GameObjVo>>
     * @Author lili
     * @Description 分别得到游戏+子游戏列表以及子游戏+一级游戏包列表
     * @Date 17:57 2023/5/8
     **/
    Map<Integer, Map<Integer, GameObjVo>> getGameAndSubGameAndPkgList();

    /**
     * @return java.util.List<org.jeecg.modules.game.entity.OpSubGame>
     * @Author lili
     * @Description 不分页查询
     * @Date 10:14 2022/12/29
     **/
    List<OpSubGame> queryAll();

    /**
     * @param opSubGame
     * @return java.lang.String
     * @Author lili
     * @Description 拼接参数
     * @Date 13:56 2022/12/16
     **/
    String splicingConf(OpSubGame opSubGame);

    /**
     * @param id
     * @return java.lang.Integer
     * @Author lili
     * @Description 删除
     * @Date 13:56 2022/12/16
     **/
    Integer delete(String id);

    /**
     * @param opSubGame
     * @return org.jeecg.common.api.vo.Result<java.lang.String>
     * @Author lili
     * @Description 添加
     * @Date 13:41 2022/12/16
     **/
    Result<String> add(OpSubGame opSubGame);

    /**
     * @param opSubGame
     * @return org.jeecg.common.api.vo.Result<java.lang.String>
     * @Author lili
     * @Description 修改
     * @Date 13:45 2022/12/16
     **/
    Result<String> update(OpSubGame opSubGame);

    /**
     * @param opSubGame
     * @return java.util.List<org.jeecg.modules.demo.game.entity.OpSubGame>
     * @Author lili
     * @Description 判断是否重名
     * @Date 13:57 2022/12/16
     **/
    List<OpSubGame> selectByGameName(OpSubGame opSubGame);

    /**
     * @return org.jeecg.modules.demo.game.dto.GameConfModel
     * @Author lili
     * @Description 生成参数
     * @Date 13:57 2022/12/16
     **/
    GameConfModel generaConf();

    /**
     * @param id
     * @return org.jeecg.modules.game.entity.OpSubGame
     * @author chenyw
     * @description 根据id获取子游戏信息
     * @date 16:24 2022/12/19/019
     **/
    OpSubGame getSubGameById(Integer id);

}
