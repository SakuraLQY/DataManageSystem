package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.DayReportBo;
import org.jeecg.modules.count.bo.DetailDailyBo;
import org.jeecg.modules.count.bo.RetentionBo;
import org.jeecg.modules.count.bo.StatCustomBo;
import org.jeecg.modules.count.bo.SummaryAdvertDailyBo;
import org.jeecg.modules.count.bo.OverViewBo;
import org.jeecg.modules.count.bo.SummaryDailyBo;
import org.jeecg.modules.count.bo.UserPayRateDailyBo;
import org.jeecg.modules.count.bo.WeekReportDailyBo;
import org.jeecg.modules.count.dto.OverViewDto;
import org.jeecg.modules.count.dto.RetentionDto;
import org.jeecg.modules.count.dto.RoiDto;
import org.jeecg.modules.count.dto.XingtuDayReportDto;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.vo.CostDataVo;
import org.jeecg.modules.count.vo.DailyPaybackVo;
import org.jeecg.modules.count.vo.DauDataVo;
import org.jeecg.modules.count.vo.RetentionVo;
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
     * @return java.util.List<org.jeecg.modules.count.bo.SummaryDailyBo>
     * @author chenyw
     * @description 数据汇总-数据总表查询
     * @date 18:29 2023/5/9/009
     **/
    List<SummaryDailyBo> getSummaryDaily(@Param("groupBy") String groupBy,
        @Param("groupName") String groupName,
        @Param(Constants.WRAPPER) Wrapper<CtDaily> queryWrapper);

    /**
     * @param groupBy
     * @param groupName
     * @param paybackSql
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.StatCustomBo>
     * @Author lili
     * @Description 合作商数据统计 合作商数据【数据】
     * @Date 17:13 2023/5/26
     **/
    List<StatCustomBo> getStatCustom(@Param("groupBy") String groupBy,
        @Param("groupName") String groupName, @Param("paybackSql") String paybackSql,
        @Param(Constants.WRAPPER) Wrapper<OverViewDto> queryWrapper);

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
     * @return java.util.List<org.jeecg.modules.count.vo.CostDataVo>
     * @Author lili
     * @Description 查询投入成本
     * @Date 13:47 2023/5/23
     **/
    List<CostDataVo> getCostV2Data(@Param(Constants.WRAPPER) Wrapper<RoiDto> queryWrapper,
        @Param("sql") String sql);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.vo.CostDataVo>
     * @Author lili
     * @Description 展示计划列表
     * @Date 15:02 2023/5/30
     **/
    List<CostDataVo> getCostV3Data(
        @Param(Constants.WRAPPER) Wrapper<XingtuDayReportDto> queryWrapper);

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
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.vo.DauDataVo>
     * @Author lili
     * @Description
     * @Date 15:15 2023/5/17
     **/
    List<DauDataVo> getUserCostData(@Param(Constants.WRAPPER) Wrapper<RoiDto> queryWrapper);

    /**
     * @param roiSql
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.vo.DailyPaybackVo>
     * @Author lili
     * @Description 查询回本数据
     * @Date 18:27 2023/5/10
     **/
    List<DailyPaybackVo> getDailyPaybackData(@Param("roiSql") String roiSql,
        @Param(Constants.WRAPPER) Wrapper<RoiDto> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.DetailDailyBo>
     * @author chenyw
     * @description 详细分析
     * @date 18:29 2023/5/9/009
     **/
    List<DetailDailyBo> getDetailDaily(@Param(Constants.WRAPPER) Wrapper<CtDaily> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.entity.CtDaily>
     * @Author lili
     * @Description 查询整体概况、财务数据-回收数据
     * @Date 14:03 2023/5/15
     **/
    List<OverViewBo> getOverViewData(@Param(Constants.WRAPPER) Wrapper<OverViewDto> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.OverViewBo>
     * @Author lili
     * @Description 星图日报中查询ctDaily表
     * @Date 16:01 2023/5/30
     **/
    List<OverViewBo> getDailyData(@Param(Constants.WRAPPER) Wrapper<XingtuDayReportDto> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.DayReportBo>
     * @Author lili
     * @Description 数据日报查询
     * @Date 19:54 2023/5/22
     **/
    List<DayReportBo> getDayReportData(@Param(Constants.WRAPPER) Wrapper<OverViewDto> queryWrapper);

    /**
     * @param queryWrapper
     * @param sql
     * @return java.util.List<org.jeecg.modules.count.vo.RetentionVo>
     * @Author lili
     * @Description 发行合作商数据统计-留存数据统计
     * @Date 14:08 2023/5/25
     **/
    List<RetentionBo> getRetentionData(@Param(Constants.WRAPPER) Wrapper<RetentionDto> queryWrapper,
        @Param("sql") String sql);

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

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.WeekReportDailyBo>
     * @author chenyw
     * @description 获取数据周报数据
     * @date 19:49 2023/5/22/022
     **/
    List<WeekReportDailyBo> getWeekReportDaily(
        @Param(Constants.WRAPPER) Wrapper<CtDaily> queryWrapper);

    /**
     * @param day
     * @param pkgName
     * @param dealName
     * @param channelName
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.UserPayRateDailyBo>
     * @author chenyw
     * @description 用户付费率
     * @date 16:52 2023/5/17/017
     **/
    List<UserPayRateDailyBo> getUserPayRateDaily(@Param("day") String day,
        @Param("pkgName") String pkgName, @Param("dealName") String dealName,
        @Param("channelName") String channelName,
        @Param(Constants.WRAPPER) Wrapper<CtDaily> queryWrapper);


}
