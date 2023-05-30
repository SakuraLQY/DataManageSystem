package org.jeecg.common.function.bo;

import lombok.Data;

/**
 * @Description: 通过id得到名称（游戏，子游戏，渠道游戏包，广告）
 * @Author: lili
 * @Date: 2023-1-18
 * @Version: V1.0
 */
@Data
public class GetNameByIdDto {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 类型（游戏，子游戏，渠道游戏包，广告）
     */
    private Integer type;

}
