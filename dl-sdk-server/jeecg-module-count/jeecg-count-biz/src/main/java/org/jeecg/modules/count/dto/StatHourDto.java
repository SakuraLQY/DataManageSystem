package org.jeecg.modules.count.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 合作商数据-渠道前端查询数据
 * @author: chenglin
 * @date: 2023年05月29日 10:51
 */
@Data
public class StatHourDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    private List<Integer> channelTypeId;
    /**渠道id*/
    @ApiModelProperty(value = "渠道id")
    private List<Integer> channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private List<Integer> channelSubAccountId;
    /**广告ID*/
    @ApiModelProperty(value = "广告ID")
    private List<Integer> dealId;
    /**注册开始日期*/
    @Excel(name = "结束时间", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始日期")
    private Date startTime;
    /**注册结束日期*/
    @Excel(name = "结束时间", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private Date endTime;
    /**归类方式*/
    @ApiModelProperty(value = "展示方式")
    private String type;
}
