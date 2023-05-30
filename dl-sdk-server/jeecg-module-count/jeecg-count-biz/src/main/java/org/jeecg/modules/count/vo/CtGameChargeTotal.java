package org.jeecg.modules.count.vo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: ct_gameChargeTotal
 * @Author: jeecg-boot
 * @Date: 2023-05-09
 * @Version: V1.0
 */
@Data
@TableName("ct_daily")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ct_game_charge对象", description = "作为sql查询的数据")
public class CtGameChargeTotal implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**
     * 游戏
     */
    @ApiModelProperty(value = "游戏")
    private Integer gameId;
    /**
     * 子游戏
     */
    @Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    private Integer subGameId;
    /**
     * 注册用户数
     */
    @Excel(name = "注册用户数", width = 15)
    @ApiModelProperty(value = "注册用户数")
    private Integer countUser;
    /**
     * 充值总额
     */
    @Excel(name = "充值总额", width = 15)
    @ApiModelProperty(value = "充值总额")
    private Integer totalMoney;
}
