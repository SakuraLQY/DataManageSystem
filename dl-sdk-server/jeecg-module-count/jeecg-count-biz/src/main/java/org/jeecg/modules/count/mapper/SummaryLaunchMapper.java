package org.jeecg.modules.count.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.LaunchTempBo;
import org.jeecg.modules.count.bo.LaunchTotalBo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.count.dto.SummaryLaunchDto;

/**
 * @Description: 数据投放
 * @Author: jeecg-boot
 * @Date: 2023-05-10
 * @Version: V1.0
 */
public interface SummaryLaunchMapper extends BaseMapper<SummaryLaunchDto> {

    List<LaunchTempBo> selectByAll(@Param(Constants.WRAPPER) QueryWrapper<SummaryLaunchDto> wrapper);

    List<LaunchTotalBo> selectByTypeOthers(@Param(Constants.WRAPPER) QueryWrapper<SummaryLaunchDto> q,
        @Param("params") String params);

    List<LaunchTempBo> selectByOthers(@Param(Constants.WRAPPER) QueryWrapper<SummaryLaunchDto> q,
        @Param("params") String params);

    String getNameByDeal(int id);

    String getNameByChannel(int id);

    String getNameByGame(int id);

    String getNameByPkg(int id);

    String getNameBySubGame(int id);

    String getNameBySubChannel(int id);

    String getNameByAccountId(int id);
}
