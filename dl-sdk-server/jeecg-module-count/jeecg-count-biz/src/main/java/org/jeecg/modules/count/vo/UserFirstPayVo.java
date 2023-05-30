package org.jeecg.modules.count.vo;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: UserFirstPayVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/9 16:55
 */
@Data
public class UserFirstPayVo {

    /**
     * 等级人数表格数据
     */
    private List<Map<String,Object>> levelMaps;

    /**
     * 等级人数饼状图数据
     */
    private List<Map<String,Object>> ratioLevelMaps;

    /**
     *  在线时长人数表格数据
     */
    private List<Map<String,Object>> timeMaps;

    /**
     * 在线时长人数饼状图数据
     */
    private List<Map<String,Object>> ratioTimeMaps;
}
