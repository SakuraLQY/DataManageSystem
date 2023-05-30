package org.jeecg.modules.advert.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.advert.entity.OpJrttAssets;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: op_jrtt_assets
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
public interface OpJrttAssetsMapper extends BaseMapper<OpJrttAssets> {

    @Select("select asset_id from open_gateway.op_jrtt_assets where account_id = #{accountId}")
    List<Long> getAssetsIdList(@Param("accountId") Integer accountId);

}
