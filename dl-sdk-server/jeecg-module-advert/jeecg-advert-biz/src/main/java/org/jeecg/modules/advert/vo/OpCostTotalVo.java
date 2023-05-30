package org.jeecg.modules.advert.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author chenyw
 * @version V1.0
 * @description:
 * @date: 2023/3/7 14:51
 **/
@Data
@AllArgsConstructor
@ApiModel(value = "投放成本合计", description = "返回前端对象")
public class OpCostTotalVo {

    private static final long serialVersionUID = 1L;

    /**成本*/
    @ApiModelProperty(value = "成本")
    private BigDecimal totalCostMoney;

    /**展示*/
    @ApiModelProperty(value = "展示")
    private BigDecimal totalExhibition;

    /**点击*/
    @ApiModelProperty(value = "点击")
    private BigDecimal totalClick;

    /**下载*/
    @ApiModelProperty(value = "下载")
    private BigDecimal totalDownload;

}
