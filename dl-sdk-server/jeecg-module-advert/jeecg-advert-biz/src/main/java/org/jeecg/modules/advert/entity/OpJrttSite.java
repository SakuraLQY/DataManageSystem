package org.jeecg.modules.advert.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: op_jrtt_site
 * @Author: jeecg-boot
 * @Date:   2023-02-16
 * @Version: V1.0
 */
@Data
@TableName("op_jrtt_site")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_jrtt_site对象", description="op_jrtt_site")
public class OpJrttSite implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**游戏-id*/
	@Excel(name = "游戏-id", width = 15)
    @ApiModelProperty(value = "游戏-id")
    private java.lang.Integer gameId;
	/**子游戏-id*/
	@Excel(name = "子游戏-id", width = 15)
    @ApiModelProperty(value = "子游戏-id")
    private java.lang.Integer subGameId;
	/**站点名*/
	@Excel(name = "站点名", width = 15)
    @ApiModelProperty(value = "站点名")
    private java.lang.String siteName;
	/**站点内容*/
	@Excel(name = "站点内容", width = 15)
    @ApiModelProperty(value = "站点内容")
    private java.lang.String siteContent;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
}
