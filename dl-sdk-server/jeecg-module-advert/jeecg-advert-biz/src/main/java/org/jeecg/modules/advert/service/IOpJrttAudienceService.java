package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeAuthorInfoResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanAccountsResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanCategoriesResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSearchInfoResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSimilarAccountsResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCountryInfoResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCustomAudienceResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetLiveAuthorizeResponse;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponseList;
import org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestId2WordOrSuggestRsp;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionInterestCategory;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionKeywordsRsp;
import org.jeecg.modules.advert.dto.OpJrttAudienceDto;
import org.jeecg.modules.advert.entity.OpJrttAudience;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.vo.AudienceVo;
import org.jeecg.modules.advert.vo.OpJrttAudienceVo;

/**
 * @Description: op_jrtt_audience
 * @Author: jeecg-boot
 * @Date: 2023-02-20
 * @Version: V1.0
 */
public interface IOpJrttAudienceService extends IService<OpJrttAudience> {

    /**
     * @param page
     * @param opJrttAudienceDto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.advert.entity.OpJrttAssets>
     * @Author lili
     * @Description 查询列表
     * @Date 13:57 2023/2/14
     **/
    IPage<OpJrttAudienceVo> selectList(Page<OpJrttAudienceVo> page,
        OpJrttAudienceDto opJrttAudienceDto);

    /**
     * @param type
     * @param accountId
     * @param actionScene
     * @param actionDays
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponseList<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionInterestCategory>
     * @Author lili
     * @Description 行为类目查询, 兴趣类目查询
     * @Date 14:34 2023/3/15
     **/
    JrttBaseResponseList<JrttDealActionInterestCategory> getActionOrInterestCategories(Integer type,
        Integer accountId, List<String> actionScene, Integer actionDays);

    /**
     * @param accountId:   账号ID
     * @param queryWords:
     * @param actionScene:
     * @param actionDays:
     * @return JrttBaseResponse
     * @author
     * @description
     * @date 2023/2/23 18:43
     */
    JrttBaseResponse<JrttDealActionKeywordsRsp> getActionOrInterestKeywords(Integer type,
        Integer accountId, String queryWords, List<String> actionScene, Integer actionDays);

    /**
     * @param accountId:                账号ID
     * @param ids                       类目或关键词id列表
     * @param tagType:查询类型：类目还是关键词
     * @param targetingType:查询目标：兴趣还是行为
     * @param actionScene:行为场景
     * @param actionDays:行为天数
     * @return JrttBaseResponseList<ActionInterestId2WordRsp>
     * @author
     * @description
     * @date 2023/2/24 11:08
     */
    JrttBaseResponse<ActionInterestId2WordOrSuggestRsp> getInterestActionInfoByIs(Integer accountId,
        List<String> ids, String tagType, String targetingType, List<String> actionScene,
        Integer actionDays);

    /**
     * @param accountId:                账号ID
     * @param id:                       类目或关键词id
     * @param tagType:查询类型：类目还是关键词
     * @param targetingType:查询目标：兴趣还是行为
     * @param actionScene:行为场景
     * @param actionDays:行为天数
     * @return JrttBaseResponse<ActionInterestId2WordOrSuggestRsp>
     * @author
     * @description
     * @date 2023/2/24 15:02
     */
    JrttBaseResponse<ActionInterestId2WordOrSuggestRsp> getActionInterestKeywordSuggest(
        Integer accountId, Integer id, String tagType, String targetingType,
        List<String> actionScene, Integer actionDays);

    /**
     * @Author lili
     * @Description 人群包列表数据
     * @Date 18:10 2023/2/22
     **/
    JrttBaseResponse<JrttGetCustomAudienceResponse> getCustomAudienceList(
        Integer selectType, Integer accountId, String invokeSource);

    /**
     * @param accountId
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCountryInfoResponse>
     * @Author lili
     * @Description 获取行政信息
     * @Date 17:47 2023/2/25
     **/
    JrttBaseResponse<JrttGetCountryInfoResponse> getCountryInfoList(
        Integer accountId);

    /**
     * @param accountId
     * @param behaviors 互动行为
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCountryInfoResponse>
     * @Author lili
     * @Description 获取抖音类目列表
     * @Date 11:31 2023/2/27
     **/
    JrttBaseResponse<JrttGetAwemeFanCategoriesResponse> getAwemeFanCategories(
        Integer accountId, String behaviors);

    /**
     * @param accountId
     * @param labelIds
     * @param behaviors 互动行为
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeAuthorInfoResponse>
     * @Author lili
     * @Description 查询抖音号id对应的达人信息
     * @Date 13:56 2023/2/27
     **/
    JrttBaseResponse<JrttGetAwemeAuthorInfoResponse> getAwemeAuthorInfo(
        Integer accountId, String labelIds, String behaviors);

    /**
     * @param accountId
     * @param queryWord
     * @param behaviors
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSearchInfoResponse>
     * @Author lili
     * @Description 查询抖音帐号和类目信息
     * @Date 14:22 2023/2/27
     **/
    JrttBaseResponse<JrttGetAwemeSearchInfoResponse> getAwemeSearchInfo(
        Integer accountId, String queryWord, String behaviors);

    /**
     * @param accountId
     * @param awemeId
     * @param behaviors
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSimilarAccountsResponse>
     * @Author lili
     * @Description 查询抖音类似帐号
     * @Date 14:30 2023/2/27
     **/
    JrttBaseResponse<JrttGetAwemeSimilarAccountsResponse> getAwemeSimilarAccounts(
        Integer accountId, String awemeId, String behaviors);

    /**
     * @param accountId
     * @param categoryId
     * @param behaviors
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanAccountsResponse>
     * @Author lili
     * @Description 查询抖音类目下的推荐达人
     * @Date 14:40 2023/2/27
     **/
    JrttBaseResponse<JrttGetAwemeFanAccountsResponse> getAwemeFanAccounts(
        Integer accountId, Integer categoryId, String behaviors);

    /**
     * @param accountId
     * @param status
     * @param page
     * @param pageSize
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetLiveAuthorizeResponse>
     * @Author lili
     * @Description 查询授权直播抖音达人列表
     * @Date 14:55 2023/2/27
     **/
    JrttBaseResponse<JrttGetLiveAuthorizeResponse> getLiveAuthorize(
        Integer accountId, String status, Integer page, Integer pageSize);

    /**
     * @param opJrttAudience
     * @Author lili
     * @Description 添加定向包
     * @Date 18:10 2023/2/22
     **/
    void save(OpJrttAudienceDto opJrttAudience);

    /**
     * @param opJrttAudience
     * @Author lili
     * @Description 批量分享定向包,不共享人群包
     * @Date 18:10 2023/2/22
     **/
    void shareAudiencePackage(OpJrttAudienceDto opJrttAudience);

    /**
     * @param opJrttAudience
     * @Author lili
     * @Description 同步定向包
     * @Date 18:10 2023/2/22
     **/
    void syncAudience(OpJrttAudienceDto opJrttAudience);

    /**
     * @param accountId: 账号ID
     * @return Result<List<AudienceVo>>
     * @author
     * @description
     * @date 2023/3/9 14:07
     */
    Result<List<AudienceVo>> getLocalAudienceList(Integer accountId);
}
