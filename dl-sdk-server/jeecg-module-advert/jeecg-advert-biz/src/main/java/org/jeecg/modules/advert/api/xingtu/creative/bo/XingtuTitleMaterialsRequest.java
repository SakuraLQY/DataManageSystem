package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 创建程序化创意（营销链路）-title_materials  请求参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuTitleMaterialsRequest {

    /**
     * 创意标题
     **/
    @JSONField(name = "title")
    private String title ;

//    /**
//     * 广告计划ID
//     **/
//    @JSONField(name = "word_list")
//    private List<XingtuWordListRequest> wordList;


}
