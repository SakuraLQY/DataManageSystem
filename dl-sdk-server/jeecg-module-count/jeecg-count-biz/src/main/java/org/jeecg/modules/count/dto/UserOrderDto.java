package org.jeecg.modules.count.dto;

import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.dto
 * @className: UserOrderDto
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/5 10:30
 */
@Data
public class UserOrderDto {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户ID
     */
    @Size(max = 10)
    private Integer userId;
}
