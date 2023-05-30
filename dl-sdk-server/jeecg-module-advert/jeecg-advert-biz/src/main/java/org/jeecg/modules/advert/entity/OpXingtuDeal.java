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
 * @Description: op_xingtu_deal
 * @Author: jeecg-boot
 * @Date:   2023-03-02
 * @Version: V1.0
 */
@Data
@TableName("op_xingtu_deal")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_xingtu_deal对象", description="op_xingtu_deal")
public class OpXingtuDeal implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private java.lang.Integer id;
	/**广告ID*/
	@Excel(name = "广告ID", width = 15)
    @ApiModelProperty(value = "广告ID")
    private java.lang.Integer dealId;
	/**主播ID*/
	@Excel(name = "主播ID", width = 15)
    @ApiModelProperty(value = "主播ID")
    private java.lang.Integer anchorPlanId;
	/**安卓下载链接*/
	@Excel(name = "安卓下载链接", width = 15)
    @ApiModelProperty(value = "安卓下载链接")
    private java.lang.String androidUrl;
	/**IOS下载链接*/
	@Excel(name = "IOS下载链接", width = 15)
    @ApiModelProperty(value = "IOS下载链接")
    private java.lang.String iosUrl;
	/**站点ID*/
	@Excel(name = "站点ID", width = 15)
    @ApiModelProperty(value = "站点ID")
    private java.lang.Integer siteId;
	/**头条站点ID*/
	@Excel(name = "头条站点ID", width = 15)
    @ApiModelProperty(value = "头条站点ID")
    private java.lang.String jrttSiteId;
}
