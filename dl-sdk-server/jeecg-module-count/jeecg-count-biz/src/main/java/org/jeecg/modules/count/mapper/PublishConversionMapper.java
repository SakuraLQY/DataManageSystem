package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.swagger.models.auth.In;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.count.bo.ConversionBo;
import org.jeecg.modules.count.entity.PublishConversion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: publish_conversion
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
public interface PublishConversionMapper extends BaseMapper<PublishConversion> {

    /**@param
     * @author chenglin
     * @description 查询转换数据的相关信息
     * @date  11:29 2023/5/24
     **/
    List<ConversionBo> queryConversionByType(@Param(Constants.WRAPPER) QueryWrapper where);

    String selectByDealId(Integer id);

    String selectByChannelId(Integer id);

    String selectByGameId(Integer id);

    String selectBySubGameId(Integer id);

    List<ConversionBo> queryConversionByTypeOther(@Param("type")String type,@Param(Constants.WRAPPER) QueryWrapper where);
}
