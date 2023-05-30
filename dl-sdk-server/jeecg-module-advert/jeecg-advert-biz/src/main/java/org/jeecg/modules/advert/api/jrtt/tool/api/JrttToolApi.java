package org.jeecg.modules.advert.api.jrtt.tool.api;

import java.util.List;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListListResponse;
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListRequest;
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;

/**
 * @Description: 今日头条 工具api
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public class JrttToolApi extends JrttBaseApi {

    /**
     * @param jrttAwemeAuthListRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttAwemeAuthListListResponse
     * @author chenyw
     * @description 获取抖音授权关系
     * @date 13:46 2023/2/20/017
     **/
    public static List<JrttAwemeAuthListListResponse> awemeAuthList(
        JrttAwemeAuthListRequest jrttAwemeAuthListRequest, String accessToken) {
        JrttBaseRequest<JrttAwemeAuthListRequest> jrttBaseRequest = new JrttBaseRequest<>(
            accessToken,
            JrttUrlConstant.AWEME_AUTH_LIST, jrttAwemeAuthListRequest);
        JrttBaseResponse<JrttAwemeAuthListResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttAwemeAuthListResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse.getData().getList();
    }

}
