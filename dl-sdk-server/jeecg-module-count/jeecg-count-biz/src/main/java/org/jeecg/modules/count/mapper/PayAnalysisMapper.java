package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.PayAnalysisBo;
import org.jeecg.modules.count.bo.PayAnalysisTempBo;
import org.jeecg.modules.count.entity.PayAnalysis;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: pay_analysis
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
public interface PayAnalysisMapper extends BaseMapper<PayAnalysis> {

    /**@param where2
     * @author chenglin
     * @description 用来全部的数据信息
     * @date 15:52 2023/5/24
     **/
    List<PayAnalysisBo> selectByDaily(@Param(Constants.WRAPPER) QueryWrapper where2);

    /**@param where2
     * @author chenglin
     * @description 查询对应ct_daily表中的信息
     * @date 17:56 2023/5/24
     **/
    List<PayAnalysisBo> selectByTypeDaily(@Param("type") String type, @Param(Constants.WRAPPER)QueryWrapper where2);

    /**@param where3
     * @author chenglin
     * @description 查询ct_order表中的信息
     * @date 17:16 2023/5/24
     **/
    List<PayAnalysisTempBo> selectByOrder(@Param(Constants.WRAPPER) QueryWrapper where3);
    /**@param where3
     * @author chenglin
     * @description 查询ct_order表中的信息
     * @date 17:16 2023/5/24
     **/
    List<PayAnalysisTempBo> selectByTypeOrder(@Param("type") String type, @Param(Constants.WRAPPER)QueryWrapper where3);

    /**@param id
     * @author chenglin
     * @description 查询对应的姓名
     * @date 17:18 2023/5/24
     **/
    String selectByDealId(Integer id);
    /**@param id
     * @author chenglin
     * @description 查询对应的姓名
     * @date 17:18 2023/5/24
     **/
    String selectByChannelId(Integer id);
    /**@param id
     * @author chenglin
     * @description 查询对应的姓名
     * @date 17:18 2023/5/24
     **/
    String selectByGameId(Integer id);
    /**@param id
     * @author chenglin
     * @description 查询对应的姓名
     * @date 17:18 2023/5/24
     **/
    String selectBySubGameId(Integer id);

    /**
     * @param id: 渠道游戏包id
     * @return String 渠道游戏包名
     * @author Fkh
     * @description 查询渠道游戏包名
     * @date 2023/6/21 14:26
     */
    String selectByPkgId(Integer id);
}
