package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.advert.dto.OpJrttAssetsDto;
import org.jeecg.modules.advert.entity.OpJrttAssets;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.vo.OpJrttAssetsVo;

/**
 * @Description: op_jrtt_assets
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
public interface IOpJrttAssetsService extends IService<OpJrttAssets> {

    /**
     * @param opJrttAssets
     * @Author lili
     * @Description 新增
     * @Date 11:45 2023/2/14
     **/
    void add(OpJrttAssetsDto opJrttAssets);

    /**
     * @param page
     * @param opJrttAssetsDto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.advert.entity.OpJrttAssets>
     * @Author lili
     * @Description 查询列表
     * @Date 13:57 2023/2/14
     **/
    IPage<OpJrttAssetsVo> selectList(Page<OpJrttAssetsVo> page,OpJrttAssetsDto opJrttAssetsDto);

    /**
     * @param opJrttAssets
     * @Author lili
     * @Description 同步资产
     * @Date 15:48 2023/2/14
     **/
    void syncAssets(OpJrttAssetsDto opJrttAssets);

}
