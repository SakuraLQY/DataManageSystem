package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.DetailDailyBo;
import org.jeecg.modules.count.bo.SummaryAdvertDailyBo;
import org.jeecg.modules.count.bo.summary.SummaryDailyBo;
import org.jeecg.modules.count.dto.RoiDto;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.vo.CostDataVo;
import org.jeecg.modules.count.vo.DailyPaybackVo;
import org.jeecg.modules.count.vo.DauDataVo;
import org.jeecg.modules.count.vo.RoiCostVo;
import org.springframework.stereotype.Repository;

/**
 * @Description: ct_daily
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Repository
public interface CtDailyMapper extends BaseMapper<CtDaily> {

    /**
     * @param groupBy
     * @param groupName
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.summary.SummaryDailyBo>
     * @author chenyw
     * @description 数据汇总-数据总表查询
     * @date 18:29 2023/5/9/009
     **/
    List<SummaryDailyBo> getSummaryDaily(@Param("groupBy") String groupBy,
        @Param("groupName") String groupName,
        @Param(Constants.WRAPPER) Wrapper<CtDaily> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.vo.costDataVo>
     * @Author lili
     * @Description 查询投入成本
     * @Date 16:32 2023/5/10
     **/
    List<CostDataVo> getCostData(@Param(Constants.WRAPPER) Wrapper<RoiDto> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.vo.RoiCostVo>
     * @Author lili
     * @Description 获取出所有符合要求的成本
     * @Date 16:32 2023/5/10
     **/
    List<RoiCostVo> getRoiCostData(@Param(Constants.WRAPPER) Wrapper<RoiDto> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.vo.DauDataVo>
     * @Author lili
     * @Description 剩余DAU和付费DAU
     * @Date 17:05 2023/5/10
     **/
    List<DauDataVo> getDauCostData(@Param(Constants.WRAPPER) Wrapper<RoiDto> queryWrapper);

    /**
     * @param roiSql
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.vo.DailyPaybackVo>
     * @Author lili
     * @Description 查询回本数据
     * @Date 18:27 2023/5/10
     **/
    List<DailyPaybackVo> getDailyPaybackData(@Param("roiSql") String roiSql, @Param(Constants.WRAPPER) Wrapper<RoiDto> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.DetailDailyBo>
     * @author chenyw
     * @description 详细分析
     * @date 18:29 2023/5/9/009
     **/
    List<DetailDailyBo> getDetailDaily(@Param(Constants.WRAPPER) Wrapper<CtDaily> queryWrapper);

    /**
     * @param day
     * @param dealId
     * @param pkgName
     * @param dealName
     * @param channelName
     * @param queryWrapper 
     * @return java.util.List<org.jeecg.modules.count.bo.DetailDailyBo>
     * @author chenyw
     * @description 渠道数据明细
     * @date 11:22 2023/5/12/012
     **/
    List<SummaryAdvertDailyBo> getSummaryAdvertDaily(@Param("day") String day,
        @Param("dealId") String dealId, @Param("pkgName") String pkgName,
        @Param("dealName") String dealName, @Param("channelName") String channelName,
        @Param(Constants.WRAPPER) Wrapper<CtDaily> queryWrapper);

}
