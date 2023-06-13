package org.jeecg.modules.count.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.modules.count.dto.ChannelDetailDto;
import org.jeecg.modules.count.entity.ChannelDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.modal.ChannelDetailModal;
import org.jeecg.modules.count.vo.ChannelDetailVo;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 渠道明细表数据
 * @Author: jeecg-boot
 * @Date:   2023-05-11
 * @Version: V1.0
 */
public interface IChannelDetailsService extends IService<ChannelDetails> {

    /**@param channelDetailDto
     * @author chenglin
     * @description 查询渠道信息
     * @date 19:25 2023/05/11
     **/
    List<ChannelDetailVo> queryList(ChannelDetailDto channelDetailDto);

    /**@param channelDetailDto
     * @author chenglin
     * @description 导出Excel的功能
     * @date 14:53 2023/6/13
     **/
    ModelAndView exportExcel(HttpServletRequest request, ChannelDetailDto channelDetailDto, Class<ChannelDetailModal> channelDetailModalClass, String title);
}
