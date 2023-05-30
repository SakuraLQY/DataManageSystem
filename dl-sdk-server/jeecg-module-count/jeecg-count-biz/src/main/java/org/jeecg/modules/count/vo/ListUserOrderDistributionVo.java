package org.jeecg.modules.count.vo;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: ListUserOrderDistributionVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/6 16:23
 */
@Data
public class ListUserOrderDistributionVo {

    /**
     * 首日付费分布图表格数据
     */
    private List<Map<String,Object>> assignment;

    /**
     * 首日付费分布图饼状图数据
     */
    private List<Map<String,Object>>graphical;
}
