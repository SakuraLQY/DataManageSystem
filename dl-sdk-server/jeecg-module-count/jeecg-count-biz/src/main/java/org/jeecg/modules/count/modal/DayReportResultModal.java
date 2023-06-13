package org.jeecg.modules.count.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.jeecg.modules.count.vo.DayReportVo;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_role
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="导出表格对象", description="导出表格对象")
public class DayReportResultModal implements Serializable {
    private static final long serialVersionUID = 1L;

	/**列表数据*/
    @ApiModelProperty(value = "列表数据")
    private List<DayReportVo> dataList;
    /**合计汇总*/
    @ApiModelProperty(value = "合计汇总")
    private Map<Integer, DayReportVo> countDataList;
    /**大盘汇总*/
    @ApiModelProperty(value = "大盘汇总")
    private List<DayReportVo> countAllVo;

}
