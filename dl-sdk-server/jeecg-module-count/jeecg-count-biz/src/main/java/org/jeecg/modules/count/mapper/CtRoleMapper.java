package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.count.entity.CtRole;
import org.jeecg.modules.count.vo.CtRoleVo;
import org.jeecg.modules.count.vo.CtUserVo;
import org.springframework.stereotype.Repository;

/**
 * @Description: ct_role
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Repository
public interface CtRoleMapper extends BaseMapper<CtRole> {

    /**
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.count.vo.CtRoleVo>
     * @Author lili
     * @Description 分页查询
     * @Date 15:32 2023/5/6
     **/
    IPage<CtRoleVo> queryPageList(Page<T> page,
        @Param(Constants.WRAPPER) QueryWrapper<CtRoleVo> wrapper);

    /**
     * @param wrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.count.vo.CtRoleVo>
     * @Author lili
     * @Description 查询全部（包含查询条件）
     * @Date 15:32 2023/5/6
     **/
    List<CtRoleVo> queryAllList(@Param(Constants.WRAPPER) QueryWrapper<CtRoleVo> wrapper);
}
