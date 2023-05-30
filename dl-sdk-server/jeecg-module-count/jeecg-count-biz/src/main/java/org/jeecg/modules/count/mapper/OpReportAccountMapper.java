package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.ReportAccountBillBo;
import org.jeecg.modules.count.bo.ReportAccountBo;
import org.jeecg.modules.count.bo.ReportAccountCostBo;
import org.jeecg.modules.count.bo.ReportBillCostBo;
import org.jeecg.modules.count.entity.OpReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 数据报表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
public interface OpReportAccountMapper extends BaseMapper<OpReport> {

    /**@Params where
     * @author chenglin
     * @description 查询对应的报表信息
     * @date 20:07 2023/5/22
     **/
    List<ReportAccountBo> queryAccountList(@Param(Constants.WRAPPER)QueryWrapper where);

    /**@param where2
     * @author chenglin
     * @description 查詢賬戶餘額
     * @date 9:49 2023/5/23
     **/
    List<ReportAccountCostBo> queryAccountCost(@Param(Constants.WRAPPER)QueryWrapper where2);

    /**@param id
     * @author chenglin
     * @description 根據账户id查询对应的账单信息
     * @date 9:49 2023/5/23
     **/
    ReportAccountBillBo queryBillByAccountId(@Param("id") Integer id);

    /**@param id
     * @author chenglin
     * @description 根據账户id查询对应的成本信息
     * @date 9:49 2023/5/23
     **/
    List<ReportBillCostBo> queryBillCostById(@Param("id") Integer id);

    /**@param id
     * @author chenglin
     * @description
     * @date 9:43 2023/5/26
     **/
    String getNameByGameId(Integer id);

    /**@param id
     * @author chenglin
     * @description
     * @date 9:43 2023/5/26
     **/
    String getNameBySubGameId(Integer id);
}
