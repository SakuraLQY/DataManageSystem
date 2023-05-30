package org.jeecg.modules.count.vo;

import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: MonthPayReportController
 * @author: fkh
 * @description: TODO
 * @date: 2023/4/27 16:09
 */
@Data
public class MonthPayReportVo {

    /**
     * 日期
     */
    @Excel(name = "日期", width = 15)
    private String createTime;

    /**
     * 充值方式
     */
    @Excel(name = "充值方式", width = 15,dicCode = "pay_type")
    private Integer payType;

    /**
     * 游戏名
     */
    @Excel(name = "游戏名", width = 15)
    private String gameName;

    /**
     * 充值金额
     */
    @Excel(name = "充值金额", width = 15)
    private BigDecimal money;

    /**
     * 充值用户数
     */
    @Excel(name = "充值用户数", width = 15)
    private Integer countUser;

    /**
     * 充值次数
     */
    @Excel(name = "充值次数", width = 15)
    private Integer countNum;

    /**
     * ARPU值
     */
    @Excel(name = "ARPU值", width = 15)
    private Double arpuNum;
}
