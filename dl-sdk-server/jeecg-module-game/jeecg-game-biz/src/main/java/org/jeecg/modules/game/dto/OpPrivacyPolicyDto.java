package org.jeecg.modules.game.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@ApiModel(value="前端传来对象", description="前端传来对象")
public class OpPrivacyPolicyDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;
    /**名称*/
    @ApiModelProperty(value = "名称")
    private String name;
//    @ApiModelProperty(value = "ID列表")
//    private List<String> pkgIds;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "cdn地址")
    private String url;
//    @ApiModelProperty(value = "备注")
//    private String descs;
    @ApiModelProperty(value = "创建用户")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
