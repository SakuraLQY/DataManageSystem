package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.jeecg.modules.advert.service.IOpJrttAudienceService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.advert.vo.AudienceVo;
import org.jeecg.modules.advert.vo.OpJrttAudienceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_jrtt_audience
 * @Author: jeecg-boot
 * @Date: 2023-02-20
 * @Version: V1.0
 */
@Api(tags = "op_jrtt_audience")
@RestController
@RequestMapping("/advert/opJrttAudience")
@Slf4j
public class OpJrttAudienceController extends
    JeecgController<OpJrttAudience, IOpJrttAudienceService> {

    @Autowired
    private IOpJrttAudienceService opJrttAudienceService;

    /**
     * 分页列表查询
     *
     * @param opJrttAudience
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_jrtt_audience-分页列表查询")
    @ApiOperation(value = "op_jrtt_audience-分页列表查询", notes = "op_jrtt_audience-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpJrttAudienceVo>> queryPageList(OpJrttAudienceDto opJrttAudience,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        Page<OpJrttAudienceVo> page = new Page<OpJrttAudienceVo>(pageNo, pageSize);
        IPage<OpJrttAudienceVo> pageList = opJrttAudienceService.selectList(page, opJrttAudience);
        return Result.OK(pageList);
    }

    /**
     * 获取人群包列表
     *
     * @return
     */
    //@AutoLog(value = "op_jrtt_audience-分页列表查询")
    @ApiOperation(value = "op_jrtt_audience-获取人群包列表", notes = "op_jrtt_audience-获取人群包列表")
    @GetMapping(value = "/getCustomAudienceList")
    public JrttBaseResponse<JrttGetCustomAudienceResponse> getCustomAudienceList(
        @RequestParam(name = "select_type") Integer selectType,
        @RequestParam(name = "accountId") Integer accountId,
        @RequestParam(name = "invoke_source") String invokeSource) {
        return opJrttAudienceService.getCustomAudienceList(selectType, accountId, invokeSource);
    }
    @ApiOperation(value = "查询本地定向包")
    @GetMapping("/getLocalAudienceList")
    public Result<List<AudienceVo>> getLocalAudienceList(@RequestParam(name = "accountId")  Integer accountId){
        return opJrttAudienceService.getLocalAudienceList(accountId);
    }

    /**
     * @param accountId
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCountryInfoResponse>
     * @Author lili
     * @Description 获取行政信息
     * @Date 17:55 2023/2/25
     **/
    @ApiOperation(value = "op_jrtt_audience-获取行政信息", notes = "op_jrtt_audience-获取行政信息")
    @GetMapping(value = "/getCountryInfoList")
    public JrttBaseResponse<JrttGetCountryInfoResponse> getCountryInfoList(
        @RequestParam(name = "accountId") Integer accountId) {
        return opJrttAudienceService.getCountryInfoList(accountId);
    }

    /**
     * @param accountId
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCountryInfoResponse>
     * @Author lili
     * @Description 获取抖音类目列表
     * @Date 11:34 2023/2/27
     **/
    @ApiOperation(value = "op_jrtt_audience-获取抖音类目列表", notes = "op_jrtt_audience-获取抖音类目列表")
    @GetMapping(value = "/getAwemeFanCategories")
    public JrttBaseResponse<JrttGetAwemeFanCategoriesResponse> getAwemeFanCategories(
        Integer accountId, @RequestParam("behaviors") String behaviors) {
        return opJrttAudienceService.getAwemeFanCategories(accountId, behaviors);
    }

    /**
     * @param accountId
     * @param labelIds
     * @param behaviors
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeAuthorInfoResponse>
     * @Author lili
     * @Description 查询抖音号id对应的达人信息
     * @Date 13:59 2023/2/27
     **/
    @ApiOperation(value = "op_jrtt_audience-查询抖音号id对应的达人信息", notes = "op_jrtt_audience-查询抖音号id对应的达人信息")
    @GetMapping(value = "/getAwemeAuthorInfo")
    public JrttBaseResponse<JrttGetAwemeAuthorInfoResponse> getAwemeAuthorInfo(
        Integer accountId, @RequestParam("label_ids") String labelIds,
        @RequestParam("behaviors") String behaviors) {
        return opJrttAudienceService.getAwemeAuthorInfo(accountId, labelIds, behaviors);
    }

    /**
     * @param accountId
     * @param queryWord
     * @param behaviors
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSearchInfoResponse>
     * @Author lili
     * @Description 查询抖音帐号和类目信息
     * @Date 14:24 2023/2/27
     **/
    @ApiOperation(value = "op_jrtt_audience-查询抖音帐号和类目信息", notes = "op_jrtt_audience-查询抖音帐号和类目信息")
    @GetMapping(value = "/getAwemeSearchInfo")
    public JrttBaseResponse<JrttGetAwemeSearchInfoResponse> getAwemeSearchInfo(
        Integer accountId, @RequestParam("query_word") String queryWord,
        @RequestParam("behaviors") String behaviors) {
        return opJrttAudienceService.getAwemeSearchInfo(accountId, queryWord, behaviors);
    }

    /**
     * @param accountId
     * @param awemeId
     * @param behaviors
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSearchInfoResponse>
     * @Author lili
     * @Description 查询抖音类似帐号
     * @Date 14:33 2023/2/27
     **/
    @ApiOperation(value = "op_jrtt_audience-查询抖音类似帐号", notes = "op_jrtt_audience-查询抖音类似帐号")
    @GetMapping(value = "/getAwemeSimilarAccounts")
    public JrttBaseResponse<JrttGetAwemeSimilarAccountsResponse> getAwemeSimilarAccounts(
        Integer accountId, @RequestParam("aweme_id") String awemeId,
        @RequestParam("behaviors") String behaviors) {
        return opJrttAudienceService.getAwemeSimilarAccounts(accountId, awemeId, behaviors);
    }

    /**
     * @param accountId
     * @param categoryId
     * @param behaviors
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanAccountsResponse>
     * @Author lili
     * @Description 查询抖音类目下的推荐达人
     * @Date 14:42 2023/2/27
     **/
    @ApiOperation(value = "op_jrtt_audience-查询抖音类目下的推荐达人", notes = "op_jrtt_audience-查询抖音类目下的推荐达人")
    @GetMapping(value = "/getAwemeFanAccounts")
    public JrttBaseResponse<JrttGetAwemeFanAccountsResponse> getAwemeFanAccounts(
        Integer accountId, @RequestParam("category_id") Integer categoryId,
        @RequestParam("behaviors") String behaviors) {
        return opJrttAudienceService.getAwemeFanAccounts(accountId, categoryId, behaviors);
    }

    /**
     * @param accountId
     * @param status
     * @param page
     * @param pageSize
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetLiveAuthorizeResponse>
     * @Author lili
     * @Description 查询授权直播抖音达人列表
     * @Date 14:59 2023/2/27
     **/
    @ApiOperation(value = "op_jrtt_audience-查询授权直播抖音达人列表", notes = "op_jrtt_audience-查询授权直播抖音达人列表")
    @GetMapping(value = "/getLiveAuthorize")
    public JrttBaseResponse<JrttGetLiveAuthorizeResponse> getLiveAuthorize(
        Integer accountId, @RequestParam("status") String status,
        @RequestParam("page") Integer page, @RequestParam("page_size") Integer pageSize) {
        return opJrttAudienceService.getLiveAuthorize(accountId, status, page, pageSize);
    }

    /**
     * @param type
     * @param accountId
     * @param actionScene
     * @param actionDays
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponseList<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionInterestCategory>
     * @Author lili
     * @Description 行为类目查询, 兴趣类目查询
     * @Date 16:55 2023/2/27
     **/
    @GetMapping("/category")
    public JrttBaseResponseList<JrttDealActionInterestCategory> getActionCategories(
        @RequestParam(name = "type") Integer type,
        @RequestParam(name = "accountId") Integer accountId,
        @RequestParam(name = "action_scene") List<String> actionScene,
        @RequestParam(name = "action_days", required = false) Integer actionDays) {

        return opJrttAudienceService
            .getActionOrInterestCategories(type, accountId, actionScene, actionDays);
    }

    /**
     * @param accountId
     * @param type
     * @param queryWords
     * @param actionScene
     * @param actionDays
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionKeywordsRsp>
     * @Author lili
     * @Description 行为关键字查询, 兴趣关键字查询
     * @Date 16:56 2023/2/27
     **/
    @GetMapping("/keyword")
    public JrttBaseResponse<JrttDealActionKeywordsRsp> getActionOrInterestKeywords(
        @RequestParam(name = "accountId") Integer accountId,
        @RequestParam(name = "type") Integer type,
        @RequestParam(name = "query_words") String queryWords,
        @RequestParam(name = "action_scene") List<String> actionScene,
        @RequestParam(name = "action_days") Integer actionDays) {
        return opJrttAudienceService
            .getActionOrInterestKeywords(type, accountId, queryWords, actionScene, actionDays);
    }

    /**
     * @param accountId
     * @param ids
     * @param tagType
     * @param targetingType
     * @param actionScene
     * @param actionDays
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestId2WordOrSuggestRsp>
     * @Author lili
     * @Description 兴趣行为类目关键词id转词
     * @Date 16:57 2023/2/27
     **/
    @GetMapping("/interestActionInfoByIs")
    public JrttBaseResponse<ActionInterestId2WordOrSuggestRsp> getInterestActionInfoByIs(
        @RequestParam(name = "accountId") Integer accountId,
        @RequestParam(name = "ids") List<String> ids,
        @RequestParam(name = "tag_type") String tagType,
        @RequestParam(name = "targeting_type") String targetingType,
        @RequestParam(name = "action_scene") List<String> actionScene,
        @RequestParam(name = "action_days") Integer actionDays
    ) {
        return opJrttAudienceService.getInterestActionInfoByIs(accountId, ids, tagType,
            targetingType, actionScene, actionDays);
    }

    /**
     * @param accountId
     * @param id
     * @param tagType
     * @param targetingType
     * @param actionScene
     * @param actionDays
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestId2WordOrSuggestRsp>
     * @Author lili
     * @Description 获取行为兴趣推荐关键词
     * @Date 16:59 2023/2/27
     **/
    @GetMapping("/ActionInterestKeywordSuggest")
    public JrttBaseResponse<ActionInterestId2WordOrSuggestRsp> getActionInterestKeywordSuggest(
        @RequestParam(name = "accountId") Integer accountId,
        @RequestParam(name = "id") Integer id,
        @RequestParam(name = "tag_type") String tagType,
        @RequestParam(name = "targeting_type") String targetingType,
        @RequestParam(name = "action_scene") List<String> actionScene,
        @RequestParam(name = "action_days") Integer actionDays
    ) {
        return opJrttAudienceService.getActionInterestKeywordSuggest(accountId, id, tagType,
            targetingType, actionScene, actionDays);
    }

    /**
     * 添加、修改
     *
     * @param opJrttAudience
     * @return
     */
    @AutoLog(value = "op_jrtt_audience-添加、修改")
    @ApiOperation(value = "op_jrtt_audience-添加、修改", notes = "op_jrtt_audience-添加、修改")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody OpJrttAudienceDto opJrttAudience) {
        opJrttAudienceService.save(opJrttAudience);
        return Result.OK("保存成功！");
    }

    /**
     * 共享定向包
     *
     * @param opJrttAudience
     * @return
     */
    @AutoLog(value = "op_jrtt_audience-共享定向包")
    @ApiOperation(value = "op_jrtt_audience-共享定向包", notes = "op_jrtt_audience-共享定向包")
    @PostMapping(value = "/shareAudience")
    public Result<String> shareAudience(@RequestBody OpJrttAudienceDto opJrttAudience) {
        opJrttAudienceService.shareAudiencePackage(opJrttAudience);
        return Result.OK("共享成功！");
    }

    /**
     * 同步
     *
     * @param opJrttAudience
     * @return
     */
    @AutoLog(value = "op_jrtt_audience-同步")
    @ApiOperation(value = "op_jrtt_audience-同步", notes = "op_jrtt_audience-同步")
    @PostMapping(value = "/syncAudience")
    public Result<String> syncAudience(@RequestBody OpJrttAudienceDto opJrttAudience) {
        opJrttAudienceService.syncAudience(opJrttAudience);
        return Result.OK("同步成功！");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_jrtt_audience-通过id删除")
    @ApiOperation(value = "op_jrtt_audience-通过id删除", notes = "op_jrtt_audience-通过id删除")
    //@RequiresPermissions("advert:op_jrtt_audience:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opJrttAudienceService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_jrtt_audience-批量删除")
    @ApiOperation(value = "op_jrtt_audience-批量删除", notes = "op_jrtt_audience-批量删除")
    //@RequiresPermissions("advert:op_jrtt_audience:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opJrttAudienceService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_jrtt_audience-通过id查询")
    @ApiOperation(value = "op_jrtt_audience-通过id查询", notes = "op_jrtt_audience-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpJrttAudience> queryById(@RequestParam(name = "id", required = true) String id) {
        OpJrttAudience opJrttAudience = opJrttAudienceService.getById(id);
        if (opJrttAudience == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opJrttAudience);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opJrttAudience
     */
    //@RequiresPermissions("advert:op_jrtt_audience:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpJrttAudience opJrttAudience) {
        return super.exportXls(request, opJrttAudience, OpJrttAudience.class, "op_jrtt_audience");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("advert:op_jrtt_audience:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpJrttAudience.class);
    }

}
