package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.advert.entity.AtUnique;
import org.jeecg.modules.advert.vo.LogDeviceCallbackDataVo;
import org.springframework.stereotype.Repository;

/**
 * @Description: at_unique
 * @Author: jeecg-boot
 * @Date:   2023-04-24
 * @Version: V1.0
 */
@Repository
public interface AtUniqueMapper extends BaseMapper<AtUnique> {

    List<LogDeviceCallbackDataVo> getDeviceCallbackData(@Param("tableName")Integer tableName, Page page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
