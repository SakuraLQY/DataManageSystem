package org.jeecg.modules.advert.modal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import lombok.Data;
import org.jeecg.modules.advert.entity.OpDeal;

@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class DealOptionResult {

    @ApiModelProperty(value = "list")
    private IPage<OpDeal> list;

    @ApiModelProperty(value = "总数")
    private Integer total;

}
