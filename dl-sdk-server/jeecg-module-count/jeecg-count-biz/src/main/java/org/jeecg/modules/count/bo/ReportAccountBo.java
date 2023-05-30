package org.jeecg.modules.count.bo;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @description: TODO
 * @author: chenglin
 * @date: 2023年05月22日 17:20
 */
@Data
public class ReportAccountBo implements Serializable {

    private Integer id;
    private Integer channelId;
    private Integer levelId;
    private Integer pid;
    private Integer appId;
    private String account;
    private String nickName;
    private String password;
    /**需要拼接**/
    private String subGameIds;
    /**账户归属**/
    private String putUser;
    /**账户备注**/
    private String accountDesc;
    /**账户状态**/
    private Integer state;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**广告名**/
    private String channelName;

    private String createUser;
}
