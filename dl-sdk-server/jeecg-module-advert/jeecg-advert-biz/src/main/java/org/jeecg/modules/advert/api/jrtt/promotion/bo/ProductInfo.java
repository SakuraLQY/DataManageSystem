package org.jeecg.modules.advert.api.jrtt.promotion.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 产品信息
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class ProductInfo {

    /**
     * 产品名称，字数限制：[1-20]，数组上限为1
     **/
    @JSONField(name = "titles")
    private List<String> titles;

    /**
     * 产品主图，尺寸要求108*108，上限10个
     **/
    @JSONField(name = "image_ids")
    private List<String> imageIds;

    /**
     * 产品卖点，字数限制：[6-9]，数组上限为10
     **/
    @JSONField(name = "selling_points")
    private List<String> sellingPoints;

}
