package org.jeecg.modules.count.vo;

import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: UserAccumulateLeveVol
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/9 14:51
 */
@Data
public class UserAccumulateLevelVo {

    /**
     * 等级
     */
    private String roleLevel;

    /**
     * 在线时长
     */
    private String onlineTime;

}
