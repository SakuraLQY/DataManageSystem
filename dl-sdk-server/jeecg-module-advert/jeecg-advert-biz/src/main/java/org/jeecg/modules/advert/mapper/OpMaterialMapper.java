package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.advert.dto.OpMaterialDto;
import org.jeecg.modules.advert.entity.OpMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.vo.OpMaterialVo;
import org.jeecg.modules.advert.vo.QuerySiteMaterialVo;
import org.jeecg.modules.advert.vo.ResponseSiteMaterialVo;

/**
 * @Description: op_material
 * @Author: jeecg-boot
 * @Date: 2023-01-12
 * @Version: V1.0
 */
public interface OpMaterialMapper extends BaseMapper<OpMaterial> {

    /**
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.advert.vo.OpMaterialVo>
     * @Author lili
     * @Description 获得列表
     * @Date 15:47 2023/1/13
     **/
    IPage<OpMaterialVo> getMaterialList(Page<T> page,
        @Param(Constants.WRAPPER) QueryWrapper<OpMaterialDto> wrapper);

    /**
     * @param opMaterial
     * @return int
     * @Author lili
     * @Description 修改头条相关参数
     * @Date 17:40 2023/1/31
     **/
    @Update("UPDATE `open_gateway`.`op_material` SET jrtt_status = #{jrttStatus},jrtt_file_id = #{jrttFileId},jrtt_material_id = #{jrttMaterialId},jrtt_material_info = #{jrttMaterialInfo},jrtt_create_account_id = #{jrttCreateAccountId} where id = #{id}")
    int updateJrttOpMaterial(OpMaterial opMaterial);

    /**
     * @param opMaterial
     * @return int
     * @Author lili
     * @Description 修改快手相关参数
     * @Date 17:53 2023/3/10
     **/
    @Update("UPDATE `open_gateway`.`op_material` SET ks_status = #{ksStatus},kuaishou_file_id = #{kuaishouFileId},kuaishou_material_id = #{kuaishouMaterialId},kuaishou_material_info = #{kuaishouMaterialInfo},kuaishou_create_account_id = #{kuaishouCreateAccountId} where id = #{id}")
    int updateKsOpMaterial(OpMaterial opMaterial);

    /**
     * @param page:
     * @param queryWrapper:
     * @return IPage<ResponseSiteMaterialVo>
     * @author
     * @description 查询落地页素材
     * @date 2023/3/2 10:41
     */
    IPage<ResponseSiteMaterialVo> getSiteMaterialPage(Page<ResponseSiteMaterialVo> page,
        @Param(Constants.WRAPPER) QueryWrapper<QuerySiteMaterialVo> queryWrapper);
}
