package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpXingtuDeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.vo.OpXingtuDealVo;

/**
 * @Description: op_xingtu_deal
 * @Author: jeecg-boot
 * @Date:   2023-03-02
 * @Version: V1.0
 */
public interface OpXingtuDealMapper extends BaseMapper<OpXingtuDeal> {

    /**
     * @author xmh
     * @description 分页获取星图广告信息
     * @date 2023/3/2 17:31
     * @param page: 分页信息
     * @param queryWrapper: 查询条件
     * @return IPage<OpXingtuDealVo>
     */
    IPage<OpXingtuDealVo> dealInfoPage(IPage<OpXingtuDealVo> page, @Param(Constants.WRAPPER) Wrapper<OpDeal> queryWrapper);
}
