
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 人群包列表  请求参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetCustomAudienceRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId ;

    /**
     * 查询类型
     **/
    @JSONField(name = "select_type")
    private Integer selectType ;

//    /**
//     * 偏移
//     **/
//    @JSONField(name = "offset")
//    private Integer offset ;
//
//    /**
//     * 返回数据量
//     **/
//    @JSONField(name = "limit")
//    private Integer limit ;



}
