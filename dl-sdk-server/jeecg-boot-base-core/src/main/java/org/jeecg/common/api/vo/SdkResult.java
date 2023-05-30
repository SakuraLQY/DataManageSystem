package org.jeecg.common.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.constant.CommonConstant;

import java.io.Serializable;

@Data
@ApiModel(value="SDK接口返回对象", description="SDK接口返回对象")
public class SdkResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    @ApiModelProperty(value = "成功标志")
    private Integer ret;

    /**
     * 返回说明
     */
    @ApiModelProperty(value = "返回说明")
    private String msg;

    /**
     * 返回内容
     */
    @ApiModelProperty(value = "返回内容")
    private T data;

    public static<T> SdkResult<T> success(T data) {
        SdkResult<T> r = new SdkResult<T>();
        r.setMsg("success");
        r.setRet(0);
        r.setData(data);
        return r;
    }

    public static<T> SdkResult<T> success(T data, String message) {
        SdkResult<T> r = new SdkResult<T>();
        r.setMsg(message);
        r.setRet(0);
        r.setData(data);
        return r;
    }

    public static SdkResult success() {
        SdkResult r = new SdkResult();
        r.setRet(0);
        r.setMsg("success");
        return r;
    }

    public static<T> SdkResult<T> error(String msg) {
        SdkResult<T> r = new SdkResult<T>();
        r.setRet(-999);
        r.setMsg(msg);
        return r;
    }

    public static<T> SdkResult<T> error(Integer ret, String msg) {
        SdkResult<T> r = new SdkResult<T>();
        r.setRet(ret);
        r.setMsg(msg);
        return r;
    }

    public static<T> SdkResult<T> error(T data, String msg) {
        SdkResult<T> r = new SdkResult<T>();
        r.setData(data);
        r.setRet(-999);
        r.setMsg(msg);
        return r;
    }

}
