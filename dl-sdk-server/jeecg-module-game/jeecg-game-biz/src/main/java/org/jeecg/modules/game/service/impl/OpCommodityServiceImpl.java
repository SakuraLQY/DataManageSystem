package org.jeecg.modules.game.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.game.bo.OpCommodityBo;
import org.jeecg.modules.game.bo.PackConfig;
import org.jeecg.modules.game.dto.OpCommodityDto;
import org.jeecg.modules.game.entity.OpCommodity;
import org.jeecg.modules.game.mapper.OpCommodityMapper;
import org.jeecg.modules.game.service.IOpCommodityService;
import org.jeecg.modules.game.vo.OpCommodityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_commodity
 * @Author: jeecg-boot
 * @Date: 2022-12-12
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpCommodityServiceImpl extends ServiceImpl<OpCommodityMapper, OpCommodity> implements
    IOpCommodityService {

    @Resource
    private OpCommodityMapper opCommodityMapper;

    @Override
    public OpCommodityVo getOpCommodity(OpCommodity opCommodity) {
        LambdaQueryWrapper<OpCommodity> wrapper = new LambdaQueryWrapper<>();
        if (opCommodity.getSubGameId() == null && opCommodity.getPkgId() == null) {
            throw new JeecgBootException("子游戏和一级游戏包必填一个");
        }
        if (opCommodity.getSubGameId() != null) {
            wrapper.eq(OpCommodity::getSubGameId, opCommodity.getSubGameId());
        }
        if (opCommodity.getPkgId() != null) {
            wrapper.eq(OpCommodity::getPkgId, opCommodity.getPkgId());
        }
        OpCommodity res = opCommodityMapper.selectOne(wrapper);
        if (res == null) {
            return null;
        }
        OpCommodityVo opCommodityVo = new OpCommodityVo();
        opCommodityVo.setId(res.getId());
        opCommodityVo.setSubGameId(res.getSubGameId());
        opCommodityVo.setPkgId(res.getPkgId());
        opCommodityVo.setGoodsConf(JSONArray.parseArray(res.getGoodsConf(), OpCommodityBo.class));
        return opCommodityVo;
    }

    @Override
    public void save(OpCommodityDto opCommodity) {
        OpCommodity res = new OpCommodity();
        BeanUtils.copyProperties(opCommodity, res);
        res.setGoodsConf(JSONObject.toJSONString(opCommodity.getGoodsConf()));
        if (opCommodity.getId() == null) {
            save(res);
        } else {
            updateById(res);
        }
    }

    @Override
    public List<OpCommodityBo> getOpCommodityByPkgIdAndSubGameId(Integer pkgId, Integer subGameId) {
        OpCommodity opCommodity = getOne(
            new LambdaQueryWrapper<OpCommodity>().eq(OpCommodity::getPkgId, pkgId));
        if (opCommodity == null) {
            // 如果渠道游戏包没有配置就去 游戏包里面找
            opCommodity = getOne(
                new LambdaQueryWrapper<OpCommodity>().eq(OpCommodity::getSubGameId, subGameId));
        }
        if (opCommodity != null && StringUtils.isNotEmpty(opCommodity.getGoodsConf())) {
            return JSONArray.parseArray(opCommodity.getGoodsConf(), OpCommodityBo.class);
        } else {
            return null;
        }
    }
}
