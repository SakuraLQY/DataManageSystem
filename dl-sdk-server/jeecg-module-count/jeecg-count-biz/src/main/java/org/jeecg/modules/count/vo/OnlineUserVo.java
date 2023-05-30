
package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class OnlineUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
    /**名称*/
    @ApiModelProperty(value = "名称")
    private String name;
    /**在线人数*/
    @ApiModelProperty(value = "在线人数")
    private Integer countOnline;

}
