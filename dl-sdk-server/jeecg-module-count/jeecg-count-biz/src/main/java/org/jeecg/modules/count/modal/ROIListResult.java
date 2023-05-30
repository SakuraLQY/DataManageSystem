package org.jeecg.modules.count.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import org.jeecg.modules.count.vo.RoiVo;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_role
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="列表+合计", description="列表+合计")
public class ROIListResult implements Serializable {
    private static final long serialVersionUID = 1L;

	/**list*/
    @ApiModelProperty(value = "list")
    private List<RoiVo> records;
    /**合计*/
    @ApiModelProperty(value = "合计")
    private RoiVo summaryROI;
    /**list数量*/
    @ApiModelProperty(value = "list数量")
    private Integer total;


}
