package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.PublishConversionDto;
import org.jeecg.modules.count.entity.PublishConversion;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.vo.PublishConversionVo;

/**
 * @Description: publish_conversion
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
public interface IPublishConversionService extends IService<PublishConversion> {

    /**@param publishConversionDto
     * @author chenglin
     * @description 用来查询转换数据统计
     * @date 11:00 2023/5/24
     **/
    List<PublishConversionVo> queryConversionList(PublishConversionDto publishConversionDto);

}
