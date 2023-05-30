package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.advert.entity.OpXingtuDropDeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.vo.OpXingtuDropDealVo;

/**
 * @Description: op_xingtu_drop_deal
 * @Author: jeecg-boot
 * @Date: 2023-03-07
 * @Version: V1.0
 */
public interface OpXingtuDropDealMapper extends BaseMapper<OpXingtuDropDeal> {

    /**
     * @param page:         分页信息
     * @param queryWrapper: 查询信息
     * @return IPage<OpXingtuDropDealVo>
     * @author xmh
     * @description 获取投放广告信息
     * @date 2023/3/8 16:31
     */
    IPage<OpXingtuDropDealVo> dropDealInfoPage(IPage<OpXingtuDropDealVo> page,
        @Param(Constants.WRAPPER) Wrapper<OpXingtuDropDeal> queryWrapper);
}
