package org.jeecg.modules.advert.service;

import java.util.List;
import org.jeecg.modules.advert.dto.OpXingtuCreativeDto;
import org.jeecg.modules.advert.entity.OpXingtuCreative;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.vo.CreativeIndustryVo;
import org.jeecg.modules.advert.vo.OpXingtuCreativeVo;

/**
 * @Description: op_xingtu_creative
 * @Author: jeecg-boot
 * @Date:   2023-03-20
 * @Version: V1.0
 */
public interface IOpXingtuCreativeService extends IService<OpXingtuCreative> {

    /**
     * @return java.util.List<org.jeecg.modules.advert.vo.CreativeIndustryVo>
     * @Author lili
     * @Description 获取行业列表
     * @Date 17:03 2023/3/22
     **/
    List<CreativeIndustryVo> getIndustryList();

    /**
     * @param opXingtuCreative
     * @Author lili
     * @Description 添加、编辑
     * @Date 17:04 2023/3/22
     **/
    void save(OpXingtuCreativeDto opXingtuCreative);

    /**
     * @param opXingtuCreative
     * @return org.jeecg.modules.advert.vo.OpXingtuCreativeVo
     * @Author lili
     * @Description 得到该计划的创意
     * @Date 13:49 2023/3/23
     **/
    OpXingtuCreativeVo getCreative(OpXingtuCreative opXingtuCreative);

}
