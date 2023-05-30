package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.advert.entity.OpJrttSite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.entity.OpJrttSite;
import org.jeecg.modules.advert.vo.JrttSiteListVo;

/**
 * @Description: op_jrtt_site
 * @Author: jeecg-boot
 * @Date:   2023-02-16
 * @Version: V1.0
 */
public interface OpJrttSiteMapper extends BaseMapper<OpJrttSite> {


    IPage<JrttSiteListVo> getPage(@Param(Constants.WRAPPER) Wrapper<OpJrttSite> queryWrapper,Page<JrttSiteListVo> page);
}
