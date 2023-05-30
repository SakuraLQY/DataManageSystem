package org.jeecg.modules.advert.api.jrtt.promotion.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @Description: 创建广告请求参数
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttPromotionCreateRequest {

    /**
     * 账户id，accout_type类型对应账户ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 项目ID
     **/
    @JSONField(name = "project_id")
    private Long projectId;

    /**
     * 广告名称，长度是1-50个字（两个英文字符占1个字）。名称不可重复，否则会报错
     **/
    @JSONField(name = "name")
    private String name;

    /**
     * 广告状态， 允许值: ENABLE开启(默认值）、DISABLE关闭
     **/
    @JSONField(name = "operation")
    private String operation;

    /**
     * 素材类型，直播场景必填
     **/
    @JSONField(name = "materials_type")
    private Long materialsType;

    /**
     * 广告素材组合
     **/
    @JSONField(name = "promotion_materials")
    private PromotionMaterials promotionMaterials;

    /**
     * 原生广告设置
     **/
    @JSONField(name = "native_setting")
    private NativeSetting nativeSetting;

    /**
     * 广告来源，字数限制：[2-10]（英文字符限制：[4-20]）
     **/
    @JSONField(name = "source")
    private String source;

    /**
     * 广告评论，ON为启用，OFF为不启用
     **/
    @JSONField(name = "is_comment_disable")
    private String isCommentDisable;

    /**
     * 客户端下载视频功能，ON为启用，OFF为不启用
     **/
    @JSONField(name = "ad_download_status")
    private String adDownloadStatus;

    /**
     * 预算
     **/
    @JSONField(name = "budget")
    private BigDecimal budget;

    /**
     * 点击出价/展示出价
     **/
    @JSONField(name = "bid")
    private BigDecimal bid;

    /**
     * 目标转化出价/预期成本
     **/
    @JSONField(name = "cpa_bid")
    private BigDecimal cpaBid;

    /**
     * 深度优化出价
     **/
    @JSONField(name = "deep_cpabid")
    private BigDecimal deepCpabid;

    /**
     * 深度转化ROI系数, 范围(0,5]，精度：保留小数点后四位
     **/
    @JSONField(name = "roi_goal")
    private BigDecimal roiGoal;

}
