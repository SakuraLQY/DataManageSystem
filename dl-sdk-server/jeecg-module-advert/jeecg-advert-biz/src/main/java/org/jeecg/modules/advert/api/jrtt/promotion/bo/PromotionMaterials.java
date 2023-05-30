package org.jeecg.modules.advert.api.jrtt.promotion.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 广告素材组合
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class PromotionMaterials {

    /**
     * 视频素材信息
     **/
    @JSONField(name = "video_material_list")
    List<VideoMaterial> videoMaterialList;

    /**
     * 标题素材，上限10个
     **/
    @JSONField(name = "title_material_list")
    List<TitleMaterial> titleMaterialList;

    /**
     * Android应用下载详情页 （橙子建站落地页）
     **/
    @JSONField(name = "web_url_material_list")
    List<String> webUrlMaterialList ;

    /**
     * 普通落地页链接素材
     **/
    @JSONField(name = "external_url_material_list")
    List<String> externalUrlMaterialList  ;

    /**
     * 产品信息
     **/
    @JSONField(name = "product_info")
    ProductInfo productInfo ;

    /**
     * 行动号召文案 行动号召文案，字数限制：[2-6]，数组上限为10
     **/
    @JSONField(name = "call_to_action_buttons")
    List<String> callToActionButtons;

}
