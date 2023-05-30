package org.jeecg.modules.game.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.Md5Util;
import org.jeecg.modules.game.dto.GameConfModel;
import org.jeecg.modules.game.entity.OpGame;
import org.jeecg.modules.game.entity.OpPackConfig;
import org.jeecg.modules.game.entity.OpPkg;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.mapper.OpGameMapper;
import org.jeecg.modules.game.mapper.OpPackConfigMapper;
import org.jeecg.modules.game.mapper.OpPkgMapper;
import org.jeecg.modules.game.mapper.OpSubGameMapper;
import org.jeecg.modules.game.service.IOpSubGameService;
import org.jeecg.modules.game.vo.GameObjVo;
import org.jeecg.modules.game.vo.OpPkgVo;
import org.jeecg.modules.game.vo.OpSubGameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.security.*;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.convert.Convert.toHex;

/**
 * @Description: op_son_game
 * @Author: jeecg-boot
 * @Date: 2022-12-09
 * @Version: V1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
@DS("open_gateway")
public class OpSubGameServiceImpl extends ServiceImpl<OpSubGameMapper, OpSubGame> implements
    IOpSubGameService {

    @Autowired
    private OpSubGameMapper opSubGameMapper;

    @Autowired
    private OpGameMapper opGameMapper;

    @Autowired
    private OpPkgMapper opPkgMapper;

    @Autowired
    private OpPackConfigMapper opPackConfigMapper;

    @Override
    public IPage<OpSubGameVo> getSubGameList(Page<T> page, OpSubGame opSubGame) {
        QueryWrapper<OpSubGameVo> q = new QueryWrapper<>();
        if (null != opSubGame.getId()) {
            q.eq("a.id", opSubGame.getId());
        }
        if (null != opSubGame.getGameId()) {
            q.eq("a.game_id", opSubGame.getGameId());
        }
        if (null != opSubGame.getStatus()) {
            q.eq("a.status", opSubGame.getStatus());
        }
        IPage<OpSubGameVo> pageList = baseMapper.getSubGameList(page, q);
        return pageList;
    }

    @Override
    public Map<Integer, GameObjVo> getOptionList(OpSubGame opSubGame) {
        QueryWrapper<OpSubGameVo> q = new QueryWrapper<>();
        if (null != opSubGame.getStatus()) {
            q.eq("a.status", opSubGame.getStatus());
        }
        //子游戏
        List<OpSubGameVo> opSubGameVoList = baseMapper.getSubGameList(q);
        //游戏
        List<OpGame> opGameList = opGameMapper.selectList(null);

        Map<Integer, List<OpSubGameVo>> opSubGameCollect = new HashMap<>();
        Map<Integer, GameObjVo> gameVoCollect = new HashMap<>();
        if (null != opSubGameVoList && !opSubGameVoList.isEmpty()) {
            opSubGameCollect = opSubGameVoList.stream()
                .collect(Collectors.groupingBy(OpSubGameVo::getGameId));
            for (Integer key : opSubGameCollect.keySet()) {
                List<GameObjVo> gameVoList = new ArrayList<>();
                GameObjVo gameObjVo = new GameObjVo();
                gameObjVo.setId(key);
                gameObjVo.setGameName(opSubGameCollect.get(key).get(0).getFaGameName());
                for (OpSubGameVo osg : opSubGameCollect.get(key)) {
                    GameObjVo gv = new GameObjVo();
                    gv.setId(osg.getId());
                    gv.setGameName(osg.getGameName());
                    gameVoList.add(gv);
                }
                gameObjVo.setList(gameVoList);
                gameVoCollect.put(key, gameObjVo);
            }
            for (OpGame opGame : opGameList) {
                if (!opSubGameCollect.containsKey(opGame.getId())) {
                    List<GameObjVo> gameVoList = new ArrayList<>();
                    GameObjVo gameObjVo = new GameObjVo();
                    gameObjVo.setId(opGame.getId());
                    gameObjVo.setGameName(opGame.getGameName());
                    gameObjVo.setList(gameVoList);
                    gameVoCollect.put(opGame.getId(), gameObjVo);
                }
            }
        }
        return gameVoCollect;
    }

    @Override
    public Map<Integer, Map<Integer, GameObjVo>> getGameAndSubGameAndPkgList() {
        Map<Integer, Map<Integer, GameObjVo>> map = new LinkedHashMap<>();
        OpSubGame opSubGame = new OpSubGame();
        opSubGame.setStatus(0);
        //游戏+子游戏
        map.put(1, getOptionList(opSubGame));
        //子游戏+渠道游戏包
        map.put(2, getSubGameAndPkg());
        return map;
    }

    private Map<Integer, GameObjVo> getSubGameAndPkg() {
        QueryWrapper<OpSubGame> q = new QueryWrapper<>();
        q.eq("status", 0);
        //子游戏
        List<OpSubGame> opSubGameList = opSubGameMapper.selectList(q);
        Map<Integer, String> opSubGameMap = new HashMap<>();
        if (null == opSubGameList || opSubGameList.isEmpty()) {
            return null;
        }
        for (OpSubGame opSubGame : opSubGameList) {
            opSubGameMap.put(opSubGame.getId(), opSubGame.getGameName());
        }
        //游戏
        List<OpPkg> opPkgList = opPkgMapper.selectList(null);
        Map<Integer, GameObjVo> resMap = new HashMap<>();
        Map<Integer, List<OpPkg>> opPkgCollect = new HashMap<>();
        if (null != opPkgList && !opPkgList.isEmpty()) {
            opPkgCollect = opPkgList.stream()
                .collect(Collectors.groupingBy(OpPkg::getSubGameId));
            for (Integer key2 : opSubGameMap.keySet()) {
                GameObjVo gameObjVo = new GameObjVo();
                gameObjVo.setId(key2);
                gameObjVo.setGameName(opSubGameMap.get(key2));
                if (opPkgCollect.containsKey(key2)) {
                    List<GameObjVo> list = new ArrayList<>();
                    for (OpPkg opPkg : opPkgCollect.get(key2)) {
                        GameObjVo g2 = new GameObjVo();
                        g2.setId(opPkg.getId());
                        g2.setGameName(opPkg.getPkgName());
                        list.add(g2);
                    }
                    gameObjVo.setList(list);
                }
                resMap.put(key2, gameObjVo);
            }

        }
        return resMap;
    }



    @Override
    public List<OpSubGame> queryAll() {
        LambdaQueryWrapper<OpSubGame> q = new LambdaQueryWrapper<>();
        q.eq(OpSubGame::getStatus, 0);
        return opSubGameMapper.selectList(q);
    }

    @Override
    public String splicingConf(OpSubGame opSubGame) {
        String gameName = opSubGame.getGameName();
        Integer appId = opSubGame.getId();
        String appKey = opSubGame.getGameKey();
        String payKey = opSubGame.getPayPubkey();
        String deliverUrl = opSubGame.getDeliverUrl();
        String conf =
            "game_name        " + gameName + "\r\napp_id      " + appId + "\napp_key      " + appKey
                + "\npay_key      \r\n" + payKey + "\ndeliver_url      \r\n" + deliverUrl;
        return conf;
    }

    @Override
    public Integer delete(String id) {
        return opSubGameMapper.delete(id);
    }

    @Override
    public Result<String> add(OpSubGame opSubGame) {
        List<OpSubGame> list = selectByGameName(opSubGame);
        if (null != list && !list.isEmpty()) {
            if (list.get(0).getStatus() == 0) {
                return Result.error("子游戏名已存在");
            } else {
                return Result.error("子游戏名已存在删除列表中，如需添加，请恢复");
            }
        }
        opSubGame.setStatus(0);
        GameConfModel gameConfModel = generaConf();
        opSubGame.setGameKey(gameConfModel.getGameKey());
        opSubGame.setPayPrikey(gameConfModel.getPayPrikey());
        opSubGame.setPayPubkey(gameConfModel.getPayPubkey());
        save(opSubGame);
        return Result.OK("添加成功！");
    }

    @Override
    public Result<String> update(OpSubGame opSubGame) {
        List<OpSubGame> list = selectByGameName(opSubGame);
        if (null != list && !list.isEmpty()) {
            if (!Objects.equals(list.get(0).getId(), opSubGame.getId())) {
                if (list.get(0).getStatus() == 0) {
                    return Result.error("子游戏名已存在");
                } else {
                    return Result.error("子游戏名已存在删除列表中，如需添加，请恢复");
                }
            }
        }
        updateById(opSubGame);
        return Result.OK("编辑成功!");
    }

    @Override
    public List<OpSubGame> selectByGameName(OpSubGame opSubGame) {
        LambdaQueryWrapper<OpSubGame> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpSubGame::getGameName, opSubGame.getGameName());
        wrapper.eq(OpSubGame::getGameId, opSubGame.getGameId());
        return opSubGameMapper.selectList(wrapper);
    }

    @Override
    public GameConfModel generaConf() {
        GameConfModel gameConfModel = new GameConfModel();
        String gameKey = "";
        String payPrikey = "";
        String payPubkey = "";
        try {
            //1.获取算法MD5实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            Long time = new Date().getTime() * 1000;
            String msg = time + "460ede31ed26deb466517b003c87ba20";
            //2.MD5加密
            byte[] buff = md.digest(msg.getBytes());
            //3.将128位的二进制编码转为32位的16进制编码
            gameKey = Md5Util.byteArrayToHexString(buff);
            //生成密钥对
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.generateKeyPair();
            //得到私钥
            payPrikey = Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded());
            //得到公钥
            payPubkey = Base64.getEncoder().encodeToString(kp.getPublic().getEncoded());
            gameConfModel.setGameKey(gameKey);
            gameConfModel.setPayPrikey(payPrikey);
            gameConfModel.setPayPubkey(payPubkey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return gameConfModel;
    }

    @Override
    public OpSubGame getSubGameById(Integer id) {
        return getById(id);
    }

}
