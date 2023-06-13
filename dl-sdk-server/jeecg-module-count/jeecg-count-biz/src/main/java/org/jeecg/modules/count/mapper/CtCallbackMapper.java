package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.entity.CtCallback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.count.vo.CtCallbackVo;

/**
 * @Description: ct_callback
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
public interface CtCallbackMapper extends BaseMapper<CtCallback> {

    /**
     * @param page
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.vo.CtCallbackVo>
     * @author chenyw
     * @description 分页查询CtCallback
     * @date 17:06 2023/5/15/015
     **/
    List<CtCallbackVo> getCtCallbackPage(Page page, @Param(Constants.WRAPPER)QueryWrapper queryWrapper);
}
