package org.jeecg.modules.advert.api.jrtt.report.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttPageInfoResponse;

/**
 * @Description: 获取广告组数据 响应参数
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttReportCampaignResponse {

    /**
     * 分页信息
     **/
    @JSONField(name = "page_info")
    private JrttPageInfoResponse pageInfo;

    /**
     * 数据列
     **/
    @JSONField(name = "list")
    List<CampaignList> list;

}
