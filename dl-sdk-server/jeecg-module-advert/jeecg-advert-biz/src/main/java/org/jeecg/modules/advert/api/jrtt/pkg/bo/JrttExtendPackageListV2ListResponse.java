package org.jeecg.modules.advert.api.jrtt.pkg.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 查询应用分包列表响应参数 - 分包数据列表
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttExtendPackageListV2ListResponse {

    /**
     * 应用包ID
     **/
    @JSONField(name = "package_id")
    private String packageId;

    /**
     * 渠道号
     **/
    @JSONField(name = "channel_id")
    private String channelId;

    /**
     * 下载链接
     **/
    @JSONField(name = "download_url")
    private String downloadUrl;

    /**
     * 状态
     **/
    @JSONField(name = "status")
    private String status;

    /**
     * 版本号
     **/
    @JSONField(name = "version_name")
    private String versionName;

    /**
     * 更新时间
     **/
    @JSONField(name = "update_time")
    private String updateTime;

    /**
     * 分包失败原因
     **/
    @JSONField(name = "reason")
    private String reason;

    /**
     * 备注
     **/
    @JSONField(name = "remark")
    private String remark;

}
