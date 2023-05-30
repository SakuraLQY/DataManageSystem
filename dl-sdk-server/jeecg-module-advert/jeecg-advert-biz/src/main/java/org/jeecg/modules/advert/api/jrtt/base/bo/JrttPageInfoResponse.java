package org.jeecg.modules.advert.api.jrtt.base.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 今日头条分页信息响应
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttPageInfoResponse {

    /**
     * 页码
     **/
    @JSONField(name = "page")
    private String page;

    /**
     * 页面大小
     **/
    @JSONField(name = "page_size")
    private Integer pageSize;

    /**
     * 总数
     **/
    @JSONField(name = "total_number")
    private Integer totalNumber;

    /**
     * 总页数
     **/
    @JSONField(name = "total_page")
    private Integer totalPage;
}
