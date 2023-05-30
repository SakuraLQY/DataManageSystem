package org.jeecg.modules.advert.dto;

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
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_jrtt_assets
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="前端传来对象", description="前端传来对象")
public class OpJrttAssetsDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "头条资产ID")
    private Long assetId;
    @ApiModelProperty(value = "资产名")
    private String assetName;
    @ApiModelProperty(value = "游戏")
    private Integer gameId;
    @ApiModelProperty(value = "子游戏")
    private Integer subGameId;
    @ApiModelProperty(value = "渠道游戏包")
    private List<String> pkgIdList;
    @ApiModelProperty(value = "投放账号")
    private Integer accountId;
    @ApiModelProperty(value = "多个投放账号")
    private String accountIdList;
    @ApiModelProperty(value = "渠道ID （4/5  头条/星图）")
    private Integer channelId;
    @ApiModelProperty(value = "创建用户")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "被分享账号")
    private String accountList;

}
