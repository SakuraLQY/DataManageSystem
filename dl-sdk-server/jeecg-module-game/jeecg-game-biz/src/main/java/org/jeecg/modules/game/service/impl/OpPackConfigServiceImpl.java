package org.jeecg.modules.game.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.game.entity.OpPackConfig;
import org.jeecg.modules.game.mapper.OpPackConfigMapper;
import org.jeecg.modules.game.service.IOpPackConfigService;
import org.jeecg.modules.game.vo.OpPackConfigVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_pack_config
 * @Author: jeecg-boot
 * @Date:   2023-01-07
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpPackConfigServiceImpl extends ServiceImpl<OpPackConfigMapper, OpPackConfig> implements
    IOpPackConfigService {

    @Resource
    private OpPackConfigMapper opPackConfigMapper;

    @Override
    public OpPackConfigVo getObjByGameId(OpPackConfig opPackConfig) {
        LambdaQueryWrapper<OpPackConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpPackConfig::getSubGameId,opPackConfig.getSubGameId());
        OpPackConfig res = opPackConfigMapper.selectOne(wrapper);
        if (oConvertUtils.isEmpty(res)) {
            return null;
        }
        OpPackConfigVo opPackConfigVo = new OpPackConfigVo();
        opPackConfigVo.setId(res.getId());
        opPackConfigVo.setSubGameId(res.getSubGameId());
        opPackConfigVo.setCreateBy(res.getCreateBy());
        opPackConfigVo.setCreateTime(res.getCreateTime());
        Map<String, Object> map = JSONObject.parseObject(res.getPackConfig(),
            new TypeReference<Map<String, Object>>() {
            });
        opPackConfigVo.setPackConfig(map);
        return opPackConfigVo;
    }

    @Override
    public void update(OpPackConfigVo opPackConfigVo) {
        OpPackConfig opPackConfig = new OpPackConfig();
        opPackConfig.setId(opPackConfigVo.getId());
        opPackConfig.setSubGameId(opPackConfigVo.getSubGameId());
        opPackConfig.setPackConfig(JSONUtils.toJSONString(opPackConfigVo.getPackConfig()));
        opPackConfig.setCreateBy(opPackConfigVo.getCreateBy());
        opPackConfig.setCreateTime(opPackConfigVo.getCreateTime());
        updateById(opPackConfig);
    }
}
