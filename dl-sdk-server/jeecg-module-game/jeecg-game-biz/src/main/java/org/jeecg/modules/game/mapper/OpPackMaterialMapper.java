package org.jeecg.modules.game.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.game.dto.OpPackMaterialDto;
import org.jeecg.modules.game.entity.OpPackMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.game.vo.OpPackMaterialVo;
import org.jeecg.modules.game.vo.OpSubGameVo;

/**
 * @Description: op_pack_material
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
public interface OpPackMaterialMapper extends BaseMapper<OpPackMaterial> {

    /**
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.game.vo.OpPackMaterialVo>
     * @Author lili
     * @Description 获得列表
     * @Date 16:07 2023/1/10
     **/
    IPage<OpPackMaterial> getPackMaterialList(Page<T> page,
        @Param(Constants.WRAPPER) QueryWrapper<OpPackMaterialDto> wrapper);

}
