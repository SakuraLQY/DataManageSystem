
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取行政信息  请求参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetCountryInfoRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId ;

    /**
     * 行政区域编码列表
     **/
    @JSONField(name = "codes")
    private List<String> codes ;

    /**
     * 语言类型
     **/
    @JSONField(name = "language")
    private String language ;

    /**
     * 行政区域层级
     **/
    @JSONField(name = "sub_district")
    private String subDistrict ;

}
