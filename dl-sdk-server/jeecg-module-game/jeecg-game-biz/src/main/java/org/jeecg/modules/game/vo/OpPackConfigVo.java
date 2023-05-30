package org.jeecg.modules.game.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_pack_config
 * @Author: jeecg-boot
 * @Date: 2023-01-07
 * @Version: V1.0
 */
@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class OpPackConfigVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;
    @ApiModelProperty(value = "打包配置")
    private Map<String, Object> packConfig;
    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

}
