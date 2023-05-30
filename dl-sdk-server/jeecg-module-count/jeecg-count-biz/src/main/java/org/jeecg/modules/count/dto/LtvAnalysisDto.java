package org.jeecg.modules.count.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: ltv分析统计接受前端参数
 * @author: chenglin
 * @date: 2023年05月24日 18:01
 */
@Data
public class LtvAnalysisDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**游戏项目*/
    @Excel(name = "游戏项目", width = 15)
    @ApiModelProperty(value = "游戏项目")
    private List<Integer> gameId;
    /**游戏名称*/
    @Excel(name = "游戏名称", width = 15)
    @ApiModelProperty(value = "游戏名称")
    private List<Integer> subGameId;
    /**子游戏包*/
    @Excel(name = "子游戏包", width = 15)
    @ApiModelProperty(value = "子游戏包")
    private List<Integer> pkgId;
    /**注册时间*/
    @Excel(name = "注册时间", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册时间")
    private Date startTime;
    /**结束时间*/
    @Excel(name = "结束时间", width = 15)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    /**归类方式**/
    private String type;
}
