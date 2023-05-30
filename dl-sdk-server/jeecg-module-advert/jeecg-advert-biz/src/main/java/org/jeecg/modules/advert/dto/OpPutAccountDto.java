
package org.jeecg.modules.advert.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: op_put_account
 * @Author: lili
 * @Date:   2023-01-09
 * @Version: V1.0
 */
@Data
@ApiModel(value="前端传来对象", description="前端传来对象")
public class OpPutAccountDto implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
    /**渠道id*/
    @Excel(name = "渠道id", width = 15, dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
    @ApiModelProperty(value = "渠道id")
    private Integer channelId;
	/**账号等级，1级为一级账号，2级为二级账号*/
    @ApiModelProperty(value = "账号等级，1级为一级账号，2级为二级账号")
    @Dict(dicCode = "account_level")
    private Integer levelId;
	/**父id*/
    @ApiModelProperty(value = "父id")
    private Integer pid;
	/**账号所属应用*/
    @ApiModelProperty(value = "账号所属应用")
    @Dict(dictTable = "open_gateway.op_jrtt_put_account_app", dicText = "app_name", dicCode = "id")
    private Integer appId;
	/**账号*/
    @ApiModelProperty(value = "账号")
    private String account;
	/**账号昵称*/
    @ApiModelProperty(value = "账号昵称")
    private String nickName;
	/**账号密码*/
    @ApiModelProperty(value = "账号密码")
    private String password;
	/**子游戏id*/
    @ApiModelProperty(value = "子游戏id")
    private String subGameIds;
	/**账号归属用户*/
    @ApiModelProperty(value = "账号归属用户")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "id")
    private String putUser;
	/**账号备注*/
    @ApiModelProperty(value = "账号备注")
    private String accountDesc;
	/**账号状态*/
    @ApiModelProperty(value = "账号状态")
    @Dict(dicCode = "account_state")
    private Integer state;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**广告主ID*/
    @ApiModelProperty(value = "广告主ID")
    private Long advertiserId;
    
}
