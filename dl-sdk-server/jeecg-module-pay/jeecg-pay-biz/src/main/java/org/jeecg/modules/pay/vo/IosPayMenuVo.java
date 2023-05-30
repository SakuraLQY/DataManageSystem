package org.jeecg.modules.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @author chenyw
 * @version V1.0
 * @description: ios支付列表
 * @date: 2022/1/14
 **/
@Data
@ApiModel(value="ios支付列表", description="ios支付列表")
public class IosPayMenuVo {

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title = "收银台";

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private String mny;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String desc;

    /**
     * 区服id
     */
    @ApiModelProperty(value = "区服id")
    private Integer serverId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 付款金额展示字段名
     */
    @ApiModelProperty(value = "付款金额")
    private String keyMny = "付款金额";

    /**
     * 商品展示字段名
     */
    @ApiModelProperty(value = "商品展示字段名")
    private String keyDesc = "商品";

    /**
     * 角色展示字段名
     */
    @ApiModelProperty(value = "角色展示字段名")
    private String keyRole = "角色";

    /**
     * 支付列表
     */
    @ApiModelProperty(value = "支付列表")
    List<IosPayMenuPayVo> ztList;

}
