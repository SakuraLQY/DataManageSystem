package org.jeecg.modules.advert.api.jrtt.pkg.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;
import org.jeecg.modules.advert.constant.jrtt.JrttExtendPackageMode;

/**
 * @Description: 创建应用分包请求参数
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttExtendPackageV2Request {

    /**
     * 账户id，accout_type类型对应账户ID
     **/
    @JSONField(name = "account_id")
    private Long accountId;

    /**
     * 账户类型 允许值：BP 巨量纵横组织、 AD 广告主账号、STAR 星图
     **/
    @JSONField(name = "account_type")
    private String accountType;

    /**
     * 应用包ID
     **/
    @JSONField(name = "package_id")
    private String packageId;

    /**
     * 分包模式
     **/
    @JSONField(name = "mode")
    private String mode = JrttExtendPackageMode.MANUAL;

    /**
     * 自定义渠道号信息，数量限制：1~100 分包模式mode为Manual时可用且必填
     **/
    @JSONField(name = "channel_list")
    private List<JrttExtendPackageV2ChannelListRequest> channelList;

}
