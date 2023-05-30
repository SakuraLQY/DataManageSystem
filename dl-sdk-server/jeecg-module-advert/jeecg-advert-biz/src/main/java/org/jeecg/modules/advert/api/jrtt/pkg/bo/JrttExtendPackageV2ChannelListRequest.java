package org.jeecg.modules.advert.api.jrtt.pkg.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.jeecg.modules.advert.constant.jrtt.JrttExtendPackageMode;

/**
 * @Description: 创建应用分包请求参数 自定义渠道号信息
 * @Author: chenyw
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttExtendPackageV2ChannelListRequest {

    /**
     * 渠道号，渠道号ID支持英文，数字，下划线和连字符-，不超过50个字符，超出部分会被截断
     **/
    @JSONField(name = "channel_id")
    private String channelId;

    /**
     * 备注，渠道包备注信息，至多20个字符，超出部分会被截断处理
     **/
    @JSONField(name = "remark")
    private String remark;

}
