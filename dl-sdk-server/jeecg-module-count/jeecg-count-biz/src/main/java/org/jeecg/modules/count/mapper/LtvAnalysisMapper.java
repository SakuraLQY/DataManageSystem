package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.LtvAnalysisBo;
import org.jeecg.modules.count.entity.LtvAnalysis;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: ltv_analysis
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
public interface LtvAnalysisMapper extends BaseMapper<LtvAnalysis> {

    /**@param where
     * @author chenglin
     * @description 查询Ltv对应的数据信息
     * @date 9:57 2023/5/25
     **/
    List<LtvAnalysisBo> queryLtvAllList(@Param(Constants.WRAPPER)QueryWrapper where);

    /**@param where
     * @author chenglin
     * @description description
     * @date 9:57 2023/5/25
     **/
    List<LtvAnalysisBo> queryLtvByTypeList(@Param("type")String type,@Param(Constants.WRAPPER) QueryWrapper where);

    /**@param id
     * @author chenglin
     * @description 查询广告名字
     * @date 9:58 2023/5/25
     **/
    String selectByDealId(Integer id);
    /**@param id
     * @author chenglin
     * @description 查询渠道名字
     * @date 9:58 2023/5/25
     **/
    String selectByChannelId(Integer id);
    /**@param id
     * @author chenglin
     * @description 查询游戏名字
     * @date 9:58 2023/5/25
     **/
    String selectByGameId(Integer id);
    /**@param id
     * @author chenglin
     * @description 查询游戏包名字
     * @date 9:58 2023/5/25
     **/
    String selectBySubGameId(Integer id);

    /**
     * @param id:
     * @return String
     * @author Fkh
     * @description 查询渠道游戏包名字
     * @date 2023/6/21 13:57
     */
    String selectByPkgId(Integer id);
}
