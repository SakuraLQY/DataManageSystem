package org.jeecg.modules.advert.api.jrtt.promotion.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 标题素材
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class TitleMaterial {

    /**
     * 创意标题
     **/
    @JSONField(name = "title")
    private String title;

//    /**
//     * 动态词包ID
//     **/
//    @JSONField(name = " word_list")
//    private List<Integer> wordList;

}
