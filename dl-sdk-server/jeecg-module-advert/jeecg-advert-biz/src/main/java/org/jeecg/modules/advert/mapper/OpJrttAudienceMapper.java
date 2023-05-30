package org.jeecg.modules.advert.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.advert.entity.OpJrttAudience;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: op_jrtt_audience
 * @Author: jeecg-boot
 * @Date:   2023-02-20
 * @Version: V1.0
 */
public interface OpJrttAudienceMapper extends BaseMapper<OpJrttAudience> {

    @Select("select audience_package_id audiencePackageId from open_gateway.op_jrtt_audience where account_id = #{accountId}")
    List<Long> getAudienceIdList(@Param("accountId") Integer accountId);
}
