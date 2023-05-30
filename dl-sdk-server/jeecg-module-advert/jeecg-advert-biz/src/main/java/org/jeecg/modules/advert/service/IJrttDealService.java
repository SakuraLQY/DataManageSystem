package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListListResponse;
import org.jeecg.modules.advert.dto.OpDealDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.vo.OpJrttDealVo;

/**
 * @Description: jrtt_deal
 * @Author: jeecg-boot
 * @Date: 2023-02-14
 * @Version: V1.0
 */
public interface IJrttDealService {

    /**
     * @param page:      分页信息
     * @param deal:      查询信息
     * @param startDate: 开始日期
     * @param endDate:   结束日期
     * @return IPage<OpJrttDealVo>
     * @author xmh
     * @description 分页获取头条广告信息
     * @date 2023/2/15 16:21
     */
    IPage<OpJrttDealVo> getByPage(Page<OpJrttDealVo> page, OpDeal deal, String startDate,
        String endDate);

    /**
     * @param opJrttDealDto: 广告基本信息
     * @author xmh
     * @description 添加头条广告
     * @date 2023/2/15 16:21
     */
    void addDeal(OpDealDto opJrttDealDto);

    /**
     * @param opJrttDealDto: 广告基本信息
     * @author xmh
     * @description 修改头条广告
     * @date 2023/2/15 19:34
     */
    void updateDeal(OpDealDto opJrttDealDto);

    /**
     * @param list: 广告ID集合
     * @author xmh
     * @description 删除多条广告
     * @date 2023/2/16 10:38
     */
    void deleteDeals(List<String> list);

    /**
     * @param id: 广告ID
     * @author xmh
     * @description 删除广告
     * @date 2023/2/16 10:38
     */
    void deleteDeal(String id);

    /**
     * @author xmh
     * @description 批量打包
     * @date 2023/3/6 16:06
     * @param opDealList: 要打包的广告
     */
    void extendPackage(List<OpDeal> opDealList);
    /**
     * @param id
     * @author chenyw
     * @description 重新打包
     * @date 10:05 2023/2/21/021
     **/
    void reExtendPackage(Integer id);

}
