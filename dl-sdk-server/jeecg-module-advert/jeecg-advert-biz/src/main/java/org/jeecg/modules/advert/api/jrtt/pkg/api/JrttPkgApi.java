package org.jeecg.modules.advert.api.jrtt.pkg.api;

import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageListV2Request;
import org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageListV2Response;
import org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageV2Request;
import org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageV2Response;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;

/**
 * @Description: 今日头条 应用管理api
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public class JrttPkgApi extends JrttBaseApi {

    /**
     * @param jrttBaseRequest
     * @return org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageV2Response
     * @author chenyw
     * @description 创建应用分包
     * @date 13:46 2023/2/17/017
     **/
    public static JrttBaseResponse<JrttExtendPackageV2Response> extendPackage(
        JrttBaseRequest<JrttExtendPackageV2Request> jrttBaseRequest) {
        JrttBaseResponse<JrttExtendPackageV2Response> jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, JrttExtendPackageV2Response.class);
        return jrttBaseResponse;
    }

    /**
     * @param jrttExtendPackageListV2Request
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.pkg.bo.JrttExtendPackageListV2Response
     * @author chenyw
     * @description 查询应用分包列表
     * @date 13:46 2023/2/17/017
     **/
    public static JrttExtendPackageListV2Response extendPackageList(
        JrttExtendPackageListV2Request jrttExtendPackageListV2Request, String accessToken) {
        JrttBaseRequest<JrttExtendPackageListV2Request> jrttBaseRequest = new JrttBaseRequest(
            accessToken,
            JrttUrlConstant.EXTEND_PACKAGE_LIST_V2, jrttExtendPackageListV2Request);
        JrttBaseResponse<JrttExtendPackageListV2Response> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttExtendPackageListV2Response.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse.getData();
    }

}
