package org.jeecg.modules.advert.api.jrtt.tool.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;
import org.jeecg.modules.advert.constant.jrtt.JrttExtendPackageMode;

/**
 * @Description: 获取抖音授权关系请求参数
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttAwemeAuthListRequest {

    /**
     * 账户id，accout_type类型对应账户ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 筛选条件
     **/
    @JSONField(name = "filtering")
    private JrttAwemeAuthListFilteringRequest filtering;

    /**
     * 授权状态， 可选值:AUTHRIZED: 授权中、AUTHRIZING: 待授权确认、INVALID: 授权失效
     **/
    @JSONField(name = "auth_status")
    private List<String> authStatus;

    /**
     * 页码
     **/
    @JSONField(name = "page")
    private Integer page;

    /**
     * 页面大小
     **/
    @JSONField(name = "page_size")
    private Integer pageSize;

}
