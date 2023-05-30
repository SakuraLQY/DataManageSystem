package org.jeecg.modules.advert.entity;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.PackStateConstant;
import org.jeecg.modules.advert.dto.OpDealDto;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ad_deal
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
@TableName("op_jrtt_action")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value="op_jrtt_action对象", description="op_deal")
public class OpJrttAction implements Serializable {
    private static final long serialVersionUID = 1L;

	/**广告ID*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;
	/**行为类型*/
	@Excel(name = "行为类型", width = 15)
    @ApiModelProperty(value = "行为类型")
    private Integer actionType;
	/**行为内容*/
	@Excel(name = "行为内容", width = 15)
    @ApiModelProperty(value = "行为内容")
    private String actionContent;
    /**行为内容*/
    @Excel(name = "创建时间", width = 15)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**行为内容*/
    @Excel(name = "创建用户", width = 15)
    @ApiModelProperty(value = "创建用户")
    private String createBy;

}
