package org.jeecg.modules.advert.api.jrtt.site.api;

import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.site.bo.JrttModifySiteRequest;
import org.jeecg.modules.advert.api.jrtt.site.bo.JrttSiteRequest;
import org.jeecg.modules.advert.api.jrtt.site.bo.JrttSiteResponse;
import org.jeecg.modules.advert.api.jrtt.site.bo.JrttUpdateSiteRequest;
import org.jeecg.modules.advert.api.jrtt.site.bo.JrttUpdateSiteResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;

/**
 * @author xmh
 * @version V1.0
 * @description: 今日头条 站点管理
 * @date: 2023/2/22 11:51
 **/
public class JrttSiteApi extends JrttBaseApi {

    /**
     * @param accessToken:     token
     * @param jrttSiteRequest: 请求参数
     * @return JrttBaseResponse<JrttSiteResponse>
     * @author xmh
     * @description 创建站点
     * @date 2023/2/22 16:01
     */
    public static JrttBaseResponse<JrttSiteResponse> createSite(String accessToken,
        JrttSiteRequest jrttSiteRequest) {
        JrttBaseRequest<JrttSiteRequest> jrttBaseRequest = new JrttBaseRequest<>(accessToken,
            JrttUrlConstant.CREATE_SITE, jrttSiteRequest);
        return JrttBaseApi.post(jrttBaseRequest, JrttSiteResponse.class);
    }

    /**
     * @param accessToken:       token
     * @param updateSiteRequest: 请求参数
     * @return JrttBaseResponse<JrttUpdateSiteResponse>
     * @author xmh
     * @description 更改站点状态
     * @date 2023/2/22 16:02
     */
    public static JrttBaseResponse<JrttUpdateSiteResponse> updateSiteStatus(String accessToken,
        JrttUpdateSiteRequest updateSiteRequest) {
        JrttBaseRequest<JrttUpdateSiteRequest> jrttBaseRequest = new JrttBaseRequest<>(accessToken,
            JrttUrlConstant.POST_SITE, updateSiteRequest);
        return JrttBaseApi.post(jrttBaseRequest, JrttUpdateSiteResponse.class);
    }

    /**
     * @param accessToken:       token
     * @param modifySiteRequest: 请求参数
     * @return JrttBaseResponse<T>
     * @author xmh
     * @description 修改站点内容
     * @date 2023/3/6 15:58
     */
    public static JrttBaseResponse<T> modifySite(String accessToken,
        JrttModifySiteRequest modifySiteRequest) {
        JrttBaseRequest<JrttModifySiteRequest> jrttBaseRequest = new JrttBaseRequest<>(accessToken,
            JrttUrlConstant.MODIFY_SITE, modifySiteRequest);
        return JrttBaseApi.post(jrttBaseRequest, T.class);
    }
}
