package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.vo.LogDeviceDataVo;
import org.springframework.stereotype.Repository;

@Repository
public interface LogManageMapper {

    List<LogDeviceDataVo> getDeviceData(Page page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);


}
