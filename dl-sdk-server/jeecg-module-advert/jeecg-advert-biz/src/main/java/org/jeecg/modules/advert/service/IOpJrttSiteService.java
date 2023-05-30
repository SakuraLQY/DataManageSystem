package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.advert.entity.OpJrttSite;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.vo.JrttSiteListVo;
import org.jeecg.modules.advert.vo.JrttSiteVo;

/**
 * @Description: op_jrtt_site
 * @Author: jeecg-boot
 * @Date: 2023-02-16
 * @Version: V1.0
 */
public interface IOpJrttSiteService extends IService<OpJrttSite> {

    /**
     * @param dealId:    广告ID
     * @param sieId:     站点ID
     * @param channelId: 渠道ID
     * @return JrttSiteVo
     * @author xmh
     * @description 创建并发布头条站点
     * @date 2023/2/22 16:00
     */
    JrttSiteVo post_site(Integer dealId, Integer sieId, Integer channelId);
    /**
     * @param opJrttSite: 保存落地页
     * @return void
     * @author
     * @description
     * @date 2023/3/1 13:34
     */
    void saveSite(OpJrttSite opJrttSite);
    /**
     * @param page:
     * @return IPage<JrttSiteListVo>
     * @author
     * @description 落地页分页查询
     * @date 2023/3/2 15:18
     */
    IPage<JrttSiteListVo> getPage(Page<JrttSiteListVo> page, OpJrttSite opJrttSite);
}
