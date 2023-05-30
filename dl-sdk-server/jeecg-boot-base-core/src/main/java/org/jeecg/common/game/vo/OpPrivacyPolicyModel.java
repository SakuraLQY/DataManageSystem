package org.jeecg.common.game.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_privacy_policy
 * @Author: jeecg-boot
 * @Date:   2022-12-28
 * @Version: V1.0
 */
@Data
@TableName("op_privacy_policy")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_privacy_policy对象", description="op_privacy_policy")
public class OpPrivacyPolicyModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**名称*/
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
//	/**子游戏ID*/
//	@Excel(name = "多个一级游戏包ID")
//    @ApiModelProperty(value = "多个一级游戏包ID")
//    private java.lang.String pkgIds;
    /**内容*/
    @Excel(name = "内容", width = 15)
    @ApiModelProperty(value = "内容")
    private java.lang.String content;
    /**cdn地址*/
    @Excel(name = "cdn地址", width = 15)
    @ApiModelProperty(value = "cdn地址")
    private java.lang.String url;
    /**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
}
