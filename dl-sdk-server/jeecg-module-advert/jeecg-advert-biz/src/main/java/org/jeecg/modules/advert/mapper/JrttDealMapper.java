package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.vo.OpJrttDealVo;

/**
 * @Description: jrtt_deal
 * @Author: jeecg-boot
 * @Date: 2023-02-14
 * @Version: V1.0
 */
public interface JrttDealMapper {

    /**
     * @param page:         分页信息
     * @param queryWrapper: 查询条件
     * @return IPage<OpJrttDealVo>
     * @author xmh
     * @description 分页获取头条广告信息
     * @date 2023/2/15 19:31
     */
    IPage<OpJrttDealVo> dealInfoPage(IPage<OpJrttDealVo> page,
        @Param(Constants.WRAPPER) Wrapper<OpDeal> queryWrapper);
}
