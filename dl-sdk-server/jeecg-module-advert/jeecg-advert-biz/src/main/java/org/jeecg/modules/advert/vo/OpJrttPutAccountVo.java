package org.jeecg.modules.advert.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
@ApiModel(value = "今日头条头投放账号前端返回对象", description = "今日头条头投放账号前端返回对象")
public class OpJrttPutAccountVo {
    private static final long serialVersionUID = 1L;

    /**id*/
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**渠道id*/
    @Excel(name = "渠道id", width = 15, dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
    @Dict(dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
    /**账号等级，1级为一级账号，2级为二级账号*/
    @Excel(name = "账号等级，1级为一级账号，2级为二级账号", width = 15)
    @ApiModelProperty(value = "账号等级，1级为一级账号，2级为二级账号")
    @Dict(dicCode = "account_level")
    private java.lang.Integer levelId;
    /**父id*/
    @Excel(name = "父id", width = 15)
    @ApiModelProperty(value = "父id")
    private java.lang.Integer pid;
    /**账号所属应用*/
    @Excel(name = "账号所属应用", width = 15)
    @ApiModelProperty(value = "账号所属应用")
    @Dict(dictTable = "open_gateway.op_jrtt_put_account_app", dicText = "app_name", dicCode = "id")
    private java.lang.Integer appId;
    /**账号*/
    @Excel(name = "账号", width = 15)
    @ApiModelProperty(value = "账号")
    private java.lang.String account;
    /**账号昵称*/
    @Excel(name = "账号昵称", width = 15)
    @ApiModelProperty(value = "账号昵称")
    private java.lang.String nickName;
    /**账号密码*/
    @Excel(name = "账号密码", width = 15)
    @ApiModelProperty(value = "账号密码")
    private java.lang.String password;
    /**子游戏id*/
    @Excel(name = "子游戏id", width = 15)
    @ApiModelProperty(value = "子游戏id")
    private java.lang.String subGameIds;
    /**账号归属用户*/
    @Excel(name = "账号归属用户", width = 15)
    @ApiModelProperty(value = "账号归属用户")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "id")
    private java.lang.String putUser;
    /**账号备注*/
    @Excel(name = "账号备注", width = 15)
    @ApiModelProperty(value = "账号备注")
    private java.lang.String accountDesc;
    /**账号状态*/
    @Excel(name = "账号状态", width = 15)
    @ApiModelProperty(value = "账号状态")
    @Dict(dicCode = "account_state")
    private java.lang.Integer state;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**授权时间*/
    @Excel(name = "授权时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "授权时间")
    private LocalDateTime authorizeTime;
}
