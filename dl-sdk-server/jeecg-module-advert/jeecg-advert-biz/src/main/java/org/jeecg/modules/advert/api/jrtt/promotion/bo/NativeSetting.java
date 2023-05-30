package org.jeecg.modules.advert.api.jrtt.promotion.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 原生广告设置
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class NativeSetting {

    /**
     * 授权抖音号id
     **/
    @JSONField(name = "aweme_id")
    private String awemeId;

    /**
     * 主页作品列表隐藏广告内容
     **/
    @JSONField(name = "is_feed_and_fav_see")
    private String isFeedAndFavSee;

    /**
     * 原生锚点启用开关
     **/
    @JSONField(name = "anchor_related_type")
    private String anchorRelatedType;

}
