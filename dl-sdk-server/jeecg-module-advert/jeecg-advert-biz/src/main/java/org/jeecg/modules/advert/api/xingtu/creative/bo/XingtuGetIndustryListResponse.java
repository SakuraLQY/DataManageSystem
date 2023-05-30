package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttAddAssetsAppAssetRequest;

/**
 * @Description: 获取行业列表-list  响应参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuGetIndustryListResponse {

    /**
     * 行业ID
     **/
    @JSONField(name = "industry_id")
    private Integer industryId;

    /**
     * 行业名称
     **/
    @JSONField(name = "industry_name")
    private String industryName;

    /**
     * 所在级别
     **/
    @JSONField(name = "level")
    private Integer level;

    /**
     * 该行业的一级行业ID
     **/
    @JSONField(name = "first_industry_id")
    private Integer firstIndustryId;

    /**
     * 该行业的一级行业名称
     **/
    @JSONField(name = "first_industry_name")
    private String firstIndustryName;

    /**
     * 该行业的二级行业ID
     **/
    @JSONField(name = "second_industry_id")
    private Integer secondIndustryId;

    /**
     * 该行业的二级行业名称
     **/
    @JSONField(name = "second_industry_name")
    private String secondIndustryName;

    /**
     * 该行业的三级行业ID
     **/
    @JSONField(name = "third_industry_id")
    private Integer thirdIndustryId;

    /**
     * 该行业的三级行业名称
     **/
    @JSONField(name = "third_industry_name")
    private String thirdIndustryName;


}
