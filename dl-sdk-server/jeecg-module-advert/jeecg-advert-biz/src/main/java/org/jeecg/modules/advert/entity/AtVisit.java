package org.jeecg.modules.advert.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@TableName("at_visit")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value = "at_visit对象", description = "归因表")
public class AtVisit {

    /**
     * 主键id
     **/
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 一级游戏包id
     **/
    private Integer pkgId;

    /**
     * 渠道id
     **/
    private Integer channelId;

    /**
     * 唯一设备类型
     **/
    private String uniqueType;

    /**
     * 唯一设备标识
     **/
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String uniqueId;

    /**
     * 广告id
     **/
    private Integer dealId;

    /**
     * 回调参数
     **/
    private String visitData;

    /**
     * 创建时间
     **/
    private Date createTime;

    /**
     * 更新时间
     **/
    private Date updateTime;
}
