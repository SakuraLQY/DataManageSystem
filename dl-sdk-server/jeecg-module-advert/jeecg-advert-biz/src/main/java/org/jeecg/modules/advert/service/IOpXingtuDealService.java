package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.jeecg.modules.advert.dto.OpXingtuDealDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpXingtuDeal;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.vo.OpXingtuDealVo;

/**
 * @Description: op_xingtu_deal
 * @Author: jeecg-boot
 * @Date: 2023-03-02
 * @Version: V1.0
 */
public interface IOpXingtuDealService extends IService<OpXingtuDeal> {

    /**
     * @param page:      分页信息
     * @param opDeal:    查询信息
     * @param anchorId:  主播
     * @param startDate: 开始日期
     * @param endDate:   结束日期
     * @return IPage<OpXingtuDealVo>
     * @author xmh
     * @description 分页获取星图广告信息
     * @date 2023/3/3 10:42
     */
    IPage<OpXingtuDealVo> getByPage(Page<OpXingtuDealVo> page, OpDeal opDeal, Integer anchorId,
        String startDate, String endDate);

    /**
     * @param opXingtuDealDto: 星图广告基本信息
     * @author xmh
     * @description 添加星图广告
     * @date 2023/3/2 18:28
     */
    void addDeal(OpXingtuDealDto opXingtuDealDto);

    /**
     * @param list: 广告ID集合
     * @author xmh
     * @description 删除多条广告
     * @date 2023/3/3 15:42
     */
    void deleteDeal(List<String> list);

    /**
     * @param id: 广告ID
     * @author xmh
     * @description 删除广告
     * @date 2023/3/3 15:42
     */
    void deleteDeal(String id);

    /**
     * @param opXingtuDealDto: 星图广告信息
     * @author xmh
     * @description 更新星图广告信息
     * @date 2023/3/6 16:09
     */
    void updateDeal(OpXingtuDealDto opXingtuDealDto);
}
