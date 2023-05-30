package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.advert.entity.OpChannel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.vo.LogCallbackDataVo;
import org.springframework.stereotype.Repository;

/**
 * @Description: op_channel
 * @Author: jeecg-boot
 * @Date:   2023-01-05
 * @Version: V1.0
 */
@Repository
public interface OpChannelMapper extends BaseMapper<OpChannel> {

    List<LogCallbackDataVo> getCallbackData(@Param("tableName")Integer tableName, Page page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
