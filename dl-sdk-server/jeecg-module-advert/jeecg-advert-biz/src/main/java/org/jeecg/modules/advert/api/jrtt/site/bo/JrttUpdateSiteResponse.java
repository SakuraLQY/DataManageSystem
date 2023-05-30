package org.jeecg.modules.advert.api.jrtt.site.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 更改站点状态返回数据
 * @date: 2023/2/20 17:47
 **/
@Data
public class JrttUpdateSiteResponse {

    /**
     * 更新失败的列表
     **/
    @JSONField(name = "fail")
    private List<FailMessage> failSiteIds;

    /**
     * 更新成功的列表
     **/
    @JSONField(name = "success")
    private String[] successSiteIds;

    /**
     * @return String
     * @author xmh
     * @description 获取失败信息
     * @date 2023/2/20 18:10
     */
    public String getFailMessage() {
        FailMessage failMessage = this.getFailSiteIds().get(0);
        return failMessage.getMessage();
    }
}

@Data
class FailMessage {

    @JSONField(name = "message")
    private String message;

    @JSONField(name = "site_id")
    private String siteId;
}
