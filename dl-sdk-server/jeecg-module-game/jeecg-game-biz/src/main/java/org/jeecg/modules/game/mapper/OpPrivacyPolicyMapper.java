package org.jeecg.modules.game.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import jdk.internal.dynalink.linker.LinkerServices;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.game.entity.OpPrivacyPolicy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.game.vo.OpSubGameVo;

/**
 * @Description: op_privacy_policy
 * @Author: jeecg-boot
 * @Date:   2022-12-28
 * @Version: V1.0
 */

public interface OpPrivacyPolicyMapper extends BaseMapper<OpPrivacyPolicy> {

    /**
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.game.vo.OpPrivacyPolicyVo>
     * @Author lili
     * @Description 连表查询
     * @Date 10:06 2023/1/5
     **/
    IPage<OpPrivacyPolicy> getPrivacyPolicyList(Page<T> page,
        @Param(Constants.WRAPPER) QueryWrapper<OpPrivacyPolicy> wrapper);

    @Select("SELECT * from op_privacy_policy WHERE FIND_IN_SET(#{pkgId}, pkg_ids);")
    List<OpPrivacyPolicy> getListByPkgId(@Param(value = "pkgId") String pkgId);
}
