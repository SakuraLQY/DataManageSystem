package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.count.dto.GameChargeDto;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.vo.CtGameChargeTotal;
import org.jeecg.modules.count.mapper.CtGameChargeTotalMapper;
import org.jeecg.modules.count.service.ICtGameChargeTotalService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: ct_gameChargeTotal
 * @Author: jeecg-boot
 * @Date:   2023-05-09
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_countly")
public class CtGameChargeTotalServiceImpl extends ServiceImpl<CtGameChargeTotalMapper, CtGameChargeTotal> implements ICtGameChargeTotalService {

    @Resource
    private CtGameChargeTotalMapper gameChargeTotalMapper;
    @Override
    public List<GameChargeDto> queryList() {
        List<GameChargeDto>queryList = new ArrayList<>();
        //查出游戏id,子游戏id,注册数，总金额
        // TODO 权限失效
        QueryWrapper<CtDaily> queryWrapper = new QueryWrapper();
        List<CtGameChargeTotal> list = gameChargeTotalMapper.selectParams(queryWrapper);
        for (CtGameChargeTotal ctGameChargeTotal : list) {
            GameChargeDto data = new GameChargeDto();
            Integer gameId = ctGameChargeTotal.getGameId();
            Integer subGameId = ctGameChargeTotal.getSubGameId();
            //查询游戏名字
            String gameName = gameChargeTotalMapper.queryGameById(gameId);
            //查询子游戏名字
            String subGameName = gameChargeTotalMapper.querySubGameById(subGameId);
            //计算ARPU
            if(ctGameChargeTotal.getCountUser()!=0){
                data.setArpu(BigDecimal.valueOf(ctGameChargeTotal.getTotalMoney())
                        .divide(BigDecimal.valueOf(ctGameChargeTotal.getCountUser()),2, RoundingMode.HALF_UP));
            }else{
                data.setArpu(BigDecimal.ZERO);
            }
            data.setGameName(gameName);
            data.setSubGameName(subGameName);
            data.setTotalMoney(ctGameChargeTotal.getTotalMoney());
            data.setRegistryNumber(ctGameChargeTotal.getCountUser());
            queryList.add(data);
        }
        return queryList;
    }
}
