package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.dto.LtvDto;
import org.jeecg.modules.count.entity.LtvAnalyze;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.count.vo.LtvPaybackVo;

/**
 * @Description: LTV分析
 * @Author: jeecg-boot
 * @Date:   2023-05-13
 * @Version: V1.0
 */
public interface LtvAnalyzeMapper extends BaseMapper<LtvAnalyze> {
    /**@param ltvSql
     * @author chenglin
     * @description 查询对应的ltv1-150的数据信息
     * @date  18:29 2023/5/15
     **/
    List<LtvPaybackVo> getPaybackLtvData(@Param(Constants.WRAPPER)QueryWrapper<LtvDto> where, @Param("ltvSql")String ltvSql) ;


}
