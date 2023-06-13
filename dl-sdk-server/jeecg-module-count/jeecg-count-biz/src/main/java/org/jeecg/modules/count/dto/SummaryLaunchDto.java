package org.jeecg.modules.count.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.util.List;
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
 * @Description: 数据投放
 * @Author: jeecg-boot
 * @Date:   2023-05-10
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SummaryLaunchDto implements Serializable {
    private static final long serialVersionUID = 1L;
	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**游戏选择*/
	@Excel(name = "游戏选择", width = 15)
    @ApiModelProperty(value = "游戏选择")
    private Integer gameId;
	/**子游戏*/
	@Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    private Integer subGameId;
	/**游戏包*/
	@Excel(name = "游戏包", width = 15)
    @ApiModelProperty(value = "游戏包")
    private Integer pkgId;
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private Integer channelTypeId;
	/**渠道*/
	@Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private Integer channelId;
	/**渠道子账号*/
	@Excel(name = "渠道子账号", width = 15)
    @ApiModelProperty(value = "渠道子账号")
    private Integer channelSubAccountId;
	/**广告编号*/
	@Excel(name = "广告编号", width = 15)
    @ApiModelProperty(value = "广告编号")
    private List<Integer> dealId;
	/**投放账号*/
	@Excel(name = "投放账号", width = 15)
    @ApiModelProperty(value = "投放账号")
    private  List<Integer> accountId;
	/**注册时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册起始时间")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册结束时间")
    private Date endTime;
	/**归类方式*/
    @ApiModelProperty(value = "归类方式")
    private String type;

}
