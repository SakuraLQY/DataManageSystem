package org.jeecg.modules.advert.api.xingtu.plan.api;

import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.xingtu.plan.bo.CreatePlanRsp;
import org.jeecg.modules.advert.api.xingtu.plan.bo.EditPlanRsp;
import org.jeecg.modules.advert.api.xingtu.plan.bo.XingCreatePlanParams;
import org.jeecg.modules.advert.api.xingtu.plan.bo.XingtuEditPlanParams;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.xingtu.plan.api
 * @className: XingtuPlanApi
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/10 20:12
 * @version: 1.0
 */
public class XingtuPlanApi extends JrttBaseApi {

    public static final String createPlanUrl = "https://ad.oceanengine.com/open_api/2/ad/create/";

    public static final String editPlanUrl = "https://ad.oceanengine.com/open_api/2/ad/update/";

    public static JrttBaseResponse<CreatePlanRsp> createPlan(String accessToken, XingCreatePlanParams xingCreatePlanParams){
        JrttBaseRequest<XingCreatePlanParams> request = new JrttBaseRequest<>(accessToken,createPlanUrl, xingCreatePlanParams);
        return post(request,CreatePlanRsp.class);
    }

    public static JrttBaseResponse<EditPlanRsp> editPlan(String accessToken, XingtuEditPlanParams editPlanParams){
        JrttBaseRequest<XingtuEditPlanParams> request = new JrttBaseRequest<>(accessToken, editPlanUrl, editPlanParams);
        return post(request, EditPlanRsp.class);
    }
}
