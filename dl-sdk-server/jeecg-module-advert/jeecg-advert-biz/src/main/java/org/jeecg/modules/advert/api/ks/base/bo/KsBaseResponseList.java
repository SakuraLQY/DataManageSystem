package org.jeecg.modules.advert.api.ks.base.bo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 头条接口返回
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KsBaseResponseList<T> {

    /**
     * 返回码
     **/
    private Integer code;

    /**
     * 返回信息
     **/
    private String message;

    /**
     * 返回信息
     **/
    private List<T> data;

    /**
     * 请求日志id
     **/
    private String requestId;
}
