package org.jeecg.modules.count.entity;

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
 * @Description: 数据报表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_report对象", description="数据报表")
public class OpReport implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**游戏项目*/
	@Excel(name = "游戏项目", width = 15)
    @ApiModelProperty(value = "游戏项目")
    private java.lang.Integer gameId;
	/**游戏名称*/
	@Excel(name = "游戏名称", width = 15)
    @ApiModelProperty(value = "游戏名称")
    private java.lang.Integer subGameId;
	/**渠道游戏包*/
	@Excel(name = "渠道游戏包", width = 15)
    @ApiModelProperty(value = "渠道游戏包")
    private java.lang.Integer pkgId;
	/**渠道名称*/
	@Excel(name = "渠道名称", width = 15)
    @ApiModelProperty(value = "渠道名称")
    private java.lang.Integer channelId;
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private java.lang.Integer channelTypeId;
	/**二级渠道*/
	@Excel(name = "二级渠道", width = 15)
    @ApiModelProperty(value = "二级渠道")
    private java.lang.Integer channelSubAccountId;
	/**投放账号*/
	@Excel(name = "投放账号", width = 15)
    @ApiModelProperty(value = "投放账号")
    private java.lang.Integer accountId;
	/**账号人员*/
	@Excel(name = "账号人员", width = 15)
    @ApiModelProperty(value = "账号人员")
    private java.lang.String createUser;
	/**账号昵称*/
	@Excel(name = "账号昵称", width = 15)
    @ApiModelProperty(value = "账号昵称")
    private java.lang.String nickName;
	/**账号*/
	@Excel(name = "账号", width = 15)
    @ApiModelProperty(value = "账号")
    private java.lang.String account;
	/**游戏*/
	@Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private java.lang.String gameName;
	/**渠道*/
	@Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private java.lang.String channelName;
	/**投放消耗*/
	@Excel(name = "投放消耗", width = 15)
    @ApiModelProperty(value = "投放消耗")
    private java.math.BigDecimal outCostMoney;
	/**账号余额*/
	@Excel(name = "账号余额", width = 15)
    @ApiModelProperty(value = "账号余额")
    private java.math.BigDecimal surplusAmount;
	/**曝光*/
	@Excel(name = "曝光", width = 15)
    @ApiModelProperty(value = "曝光")
    private java.math.BigDecimal exhibition;
	/**下载*/
	@Excel(name = "下载", width = 15)
    @ApiModelProperty(value = "下载")
    private java.math.BigDecimal download;
	/**负责人*/
	@Excel(name = "负责人", width = 15)
    @ApiModelProperty(value = "负责人")
    private java.lang.String principalUser;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
	/**账单*/
	@Excel(name = "账单", width = 15)
    @ApiModelProperty(value = "账单")
    private java.lang.String bill;
}
