package org.jeecg.modules.advert.api.jrtt.base.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 头条接口请求
 * @Author: chenyw
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JrttBaseRequest<T> {

    /**
     * 授权access-token
     **/
    private String accessToken;

    /**
     * 请求接口地址
     **/
    private String url;

    /**
     * 请求数据
     **/
    private T data;

}
