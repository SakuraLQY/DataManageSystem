package org.jeecg.modules.count.modal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class RoiModal implements Serializable {
    private static final long serialVersionUID = 1L;

	/**日期*/
    @Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private String roiDate;
	/**游戏*/
    @Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private String gameName;
	/**渠道*/
    @Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private String channelName;
	/**广告*/
    @Excel(name = "广告", width = 15)
    @ApiModelProperty(value = "广告")
    private String dealName;
    /**推广费用*/
    @Excel(name = "推广费用", width = 15)
    @ApiModelProperty(value = "推广费用")
    private BigDecimal promotionCost;
    /**新增注册数*/
    @Excel(name = "新增注册数", width = 15)
    @ApiModelProperty(value = "新增注册数")
    private Integer regCount;
    /**剩余DAU*/
    @Excel(name = "剩余DAU", width = 15)
    @ApiModelProperty(value = "剩余DAU")
    private Integer remainDAU;
    /**付费DAU*/
    @Excel(name = "付费DAU", width = 15)
    @ApiModelProperty(value = "付费DAU")
    private BigDecimal costDAU;
	/**注册单价*/
    @Excel(name = "注册单价", width = 15)
    @ApiModelProperty(value = "注册单价")
    private BigDecimal regUnitPrice;
    /**新增付费人数*/
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer costCount;
    /**付费率*/
    @Excel(name = "付费率", width = 15)
    @ApiModelProperty(value = "付费率")
    private BigDecimal costProbability;
    /**付费单价*/
    @Excel(name = "付费单价", width = 15)
    @ApiModelProperty(value = "付费单价")
    private BigDecimal costUnitPrice;
    /**新增付费金额*/
    @Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal addCostPrice;
    /**累计付费金额*/
    @Excel(name = "累计付费金额", width = 15)
    @ApiModelProperty(value = "累计付费金额")
    private BigDecimal allCostPrice;
    /**sdk分成所得*/
    @Excel(name = "sdk分成所得", width = 15)
    @ApiModelProperty(value = "sdk分成所得")
    private BigDecimal sdkShare;
    /**利润*/
    @Excel(name = "利润", width = 15)
    @ApiModelProperty(value = "利润")
    private BigDecimal profit;
    /**累计ROI*/
    @Excel(name = "累计ROI", width = 15)
    @ApiModelProperty(value = "累计ROI")
    private BigDecimal allROI;
    /**1日ROI(0)*/
    @Excel(name = "1日ROI(0)", width = 15)
    @ApiModelProperty(value = "1日ROI(0)")
    private BigDecimal firstROI;
    /**2日ROI(0)*/
    @Excel(name = "2日ROI(0)", width = 15)
    @ApiModelProperty(value = "2日ROI(0)")
    private java.math.BigDecimal day2=BigDecimal.ZERO;
    /**3日ROI(0)*/
    @Excel(name = "3日ROI(0)", width = 15)
    @ApiModelProperty(value = "3日ROI(0)")
    private java.math.BigDecimal day3=BigDecimal.ZERO;
    /**4日ROI(0)*/
    @Excel(name = "4日ROI(0)", width = 15)
    @ApiModelProperty(value = "4日ROI(0)")
    private java.math.BigDecimal day4=BigDecimal.ZERO;
    /**5日ROI(0)*/
    @Excel(name = "5日ROI(0)", width = 15)
    @ApiModelProperty(value = "5日ROI(0)")
    private java.math.BigDecimal day5=BigDecimal.ZERO;
    /**6日ROI(0)*/
    @Excel(name = "6日ROI(0)", width = 15)
    @ApiModelProperty(value = "6日ROI(0)")
    private java.math.BigDecimal day6=BigDecimal.ZERO;
    /**7日ROI(0)*/
    @Excel(name = "7日ROI(0)", width = 15)
    @ApiModelProperty(value = "7日ROI(0)")
    private java.math.BigDecimal day7= BigDecimal.ZERO;
    /**8日ROI(0)*/
    @Excel(name = "8日ROI(0)", width = 15)
    @ApiModelProperty(value = "8日ROI(0)")
    private java.math.BigDecimal day8=BigDecimal.ZERO;
    /**9日ROI(0)*/
    @Excel(name = "9日ROI(0)", width = 15)
    @ApiModelProperty(value = "9日ROI(0)")
    private java.math.BigDecimal day9=BigDecimal.ZERO;
    /**10日ROI(0)*/
    @Excel(name = "10日ROI(0)", width = 15)
    @ApiModelProperty(value = "10日ROI(0)")
    private java.math.BigDecimal day10=BigDecimal.ZERO;
    /**11日ROI(0)*/
    @Excel(name = "11日ROI(0)", width = 15)
    @ApiModelProperty(value = "11日ROI(0)")
    private java.math.BigDecimal day11=BigDecimal.ZERO;
    /**12日ROI(0)*/
    @Excel(name = "12日ROI(0)", width = 15)
    @ApiModelProperty(value = "12日ROI(0)")
    private java.math.BigDecimal day12=BigDecimal.ZERO;
    /**13日ROI(0)*/
    @Excel(name = "13日ROI(0)", width = 15)
    @ApiModelProperty(value = "13日ROI(0)")
    private java.math.BigDecimal day13=BigDecimal.ZERO;
    /**14日ROI(0)*/
    @Excel(name = "14日ROI(0)", width = 15)
    @ApiModelProperty(value = "14日ROI(0)")
    private java.math.BigDecimal day14=BigDecimal.ZERO;
    /**15日ROI(0)*/
    @Excel(name = "15日ROI(0)", width = 15)
    @ApiModelProperty(value = "15日ROI(0)")
    private java.math.BigDecimal day15=BigDecimal.ZERO;
    /**16日ROI(0)*/
    @Excel(name = "16日ROI(0)", width = 15)
    @ApiModelProperty(value = "16日ROI(0)")
    private java.math.BigDecimal day16=BigDecimal.ZERO;
    /**17日ROI(0)*/
    @Excel(name = "17日ROI(0)", width = 15)
    @ApiModelProperty(value = "17日ROI(0)")
    private java.math.BigDecimal day17=BigDecimal.ZERO;
    /**18日ROI(0)*/
    @Excel(name = "18日ROI(0)", width = 15)
    @ApiModelProperty(value = "18日ROI(0)")
    private java.math.BigDecimal day18=BigDecimal.ZERO;
    /**19日ROI(0)*/
    @Excel(name = "19日ROI(0)", width = 15)
    @ApiModelProperty(value = "19日ROI(0)")
    private java.math.BigDecimal day19=BigDecimal.ZERO;
    /**20日ROI(0)*/
    @Excel(name = "20日ROI(0)", width = 15)
    @ApiModelProperty(value = "20日ROI(0)")
    private java.math.BigDecimal day20=BigDecimal.ZERO;
    /**21日ROI(0)*/
    @Excel(name = "21日ROI(0)", width = 15)
    @ApiModelProperty(value = "21日ROI(0)")
    private java.math.BigDecimal day21=BigDecimal.ZERO;
    /**22日ROI(0)*/
    @Excel(name = "22日ROI(0)", width = 15)
    @ApiModelProperty(value = "22日ROI(0)")
    private java.math.BigDecimal day22=BigDecimal.ZERO;
    /**23日ROI(0)*/
    @Excel(name = "23日ROI(0)", width = 15)
    @ApiModelProperty(value = "23日ROI(0)")
    private java.math.BigDecimal day23=BigDecimal.ZERO;
    /**24日ROI(0)*/
    @Excel(name = "24日ROI(0)", width = 15)
    @ApiModelProperty(value = "24日ROI(0)")
    private java.math.BigDecimal day24=BigDecimal.ZERO;
    /**25日ROI(0)*/
    @Excel(name = "25日ROI(0)", width = 15)
    @ApiModelProperty(value = "25日ROI(0)")
    private java.math.BigDecimal day25=BigDecimal.ZERO;
    /**26日ROI(0)*/
    @Excel(name = "26日ROI(0)", width = 15)
    @ApiModelProperty(value = "26日ROI(0)")
    private java.math.BigDecimal day26=BigDecimal.ZERO;
    /**27日ROI(0)*/
    @Excel(name = "27日ROI(0)", width = 15)
    @ApiModelProperty(value = "27日ROI(0)")
    private java.math.BigDecimal day27=BigDecimal.ZERO;
    /**28日ROI(0)*/
    @Excel(name = "28日ROI(0)", width = 15)
    @ApiModelProperty(value = "28日ROI(0)")
    private java.math.BigDecimal day28=BigDecimal.ZERO;
    /**29日ROI(0)*/
    @Excel(name = "29日ROI(0)", width = 15)
    @ApiModelProperty(value = "29日ROI(0)")
    private java.math.BigDecimal day29=BigDecimal.ZERO;
    /**30日ROI(0)*/
    @Excel(name = "30日ROI(0)", width = 15)
    @ApiModelProperty(value = "30日ROI(0)")
    private java.math.BigDecimal day30=BigDecimal.ZERO;
    /**31日ROI(0)*/
    @Excel(name = "31日ROI(0)", width = 15)
    @ApiModelProperty(value = "31日ROI(0)")
    private java.math.BigDecimal day31=BigDecimal.ZERO;
    /**32日ROI(0)*/
    @Excel(name = "32日ROI(0)", width = 15)
    @ApiModelProperty(value = "32日ROI(0)")
    private java.math.BigDecimal day32=BigDecimal.ZERO;
    /**33日ROI(0)*/
    @Excel(name = "33日ROI(0)", width = 15)
    @ApiModelProperty(value = "33日ROI(0)")
    private java.math.BigDecimal day33=BigDecimal.ZERO;
    /**34日ROI(0)*/
    @Excel(name = "34日ROI(0)", width = 15)
    @ApiModelProperty(value = "34日ROI(0)")
    private java.math.BigDecimal day34=BigDecimal.ZERO;
    /**35日ROI(0)*/
    @Excel(name = "35日ROI(0)", width = 15)
    @ApiModelProperty(value = "35日ROI(0)")
    private java.math.BigDecimal day35=BigDecimal.ZERO;
    /**36日ROI(0)*/
    @Excel(name = "36日ROI(0)", width = 15)
    @ApiModelProperty(value = "36日ROI(0)")
    private java.math.BigDecimal day36=BigDecimal.ZERO;
    /**37日ROI(0)*/
    @Excel(name = "37日ROI(0)", width = 15)
    @ApiModelProperty(value = "37日ROI(0)")
    private java.math.BigDecimal day37=BigDecimal.ZERO;
    /**38日ROI(0)*/
    @Excel(name = "38日ROI(0)", width = 15)
    @ApiModelProperty(value = "38日ROI(0)")
    private java.math.BigDecimal day38=BigDecimal.ZERO;
    /**39日ROI(0)*/
    @Excel(name = "39日ROI(0)", width = 15)
    @ApiModelProperty(value = "39日ROI(0)")
    private java.math.BigDecimal day39=BigDecimal.ZERO;
    /**40日ROI(0)*/
    @Excel(name = "40日ROI(0)", width = 15)
    @ApiModelProperty(value = "40日ROI(0)")
    private java.math.BigDecimal day40=BigDecimal.ZERO;
    /**41日ROI(0)*/
    @Excel(name = "41日ROI(0)", width = 15)
    @ApiModelProperty(value = "41日ROI(0)")
    private java.math.BigDecimal day41=BigDecimal.ZERO;
    /**42日ROI(0)*/
    @Excel(name = "42日ROI(0)", width = 15)
    @ApiModelProperty(value = "42日ROI(0)")
    private java.math.BigDecimal day42=BigDecimal.ZERO;
    /**43日ROI(0)*/
    @Excel(name = "43日ROI(0)", width = 15)
    @ApiModelProperty(value = "43日ROI(0)")
    private java.math.BigDecimal day43=BigDecimal.ZERO;
    /**44日ROI(0)*/
    @Excel(name = "44日ROI(0)", width = 15)
    @ApiModelProperty(value = "44日ROI(0)")
    private java.math.BigDecimal day44=BigDecimal.ZERO;
    /**45日ROI(0)*/
    @Excel(name = "45日ROI(0)", width = 15)
    @ApiModelProperty(value = "45日ROI(0)")
    private java.math.BigDecimal day45=BigDecimal.ZERO;
    /**46日ROI(0)*/
    @Excel(name = "46日ROI(0)", width = 15)
    @ApiModelProperty(value = "46日ROI(0)")
    private java.math.BigDecimal day46=BigDecimal.ZERO;
    /**47日ROI(0)*/
    @Excel(name = "47日ROI(0)", width = 15)
    @ApiModelProperty(value = "47日ROI(0)")
    private java.math.BigDecimal day47=BigDecimal.ZERO;
    /**48日ROI(0)*/
    @Excel(name = "48日ROI(0)", width = 15)
    @ApiModelProperty(value = "48日ROI(0)")
    private java.math.BigDecimal day48=BigDecimal.ZERO;
    /**49日ROI(0)*/
    @Excel(name = "49日ROI(0)", width = 15)
    @ApiModelProperty(value = "49日ROI(0)")
    private java.math.BigDecimal day49=BigDecimal.ZERO;
    /**50日ROI(0)*/
    @Excel(name = "50日ROI(0)", width = 15)
    @ApiModelProperty(value = "50日ROI(0)")
    private java.math.BigDecimal day50=BigDecimal.ZERO;
    /**51日ROI(0)*/
    @Excel(name = "51日ROI(0)", width = 15)
    @ApiModelProperty(value = "51日ROI(0)")
    private java.math.BigDecimal day51=BigDecimal.ZERO;
    /**52日ROI(0)*/
    @Excel(name = "52日ROI(0)", width = 15)
    @ApiModelProperty(value = "52日ROI(0)")
    private java.math.BigDecimal day52=BigDecimal.ZERO;
    /**53日ROI(0)*/
    @Excel(name = "53日ROI(0)", width = 15)
    @ApiModelProperty(value = "53日ROI(0)")
    private java.math.BigDecimal day53=BigDecimal.ZERO;
    /**54日ROI(0)*/
    @Excel(name = "54日ROI(0)", width = 15)
    @ApiModelProperty(value = "54日ROI(0)")
    private java.math.BigDecimal day54=BigDecimal.ZERO;
    /**55日ROI(0)*/
    @Excel(name = "55日ROI(0)", width = 15)
    @ApiModelProperty(value = "55日ROI(0)")
    private java.math.BigDecimal day55=BigDecimal.ZERO;
    /**56日ROI(0)*/
    @Excel(name = "56日ROI(0)", width = 15)
    @ApiModelProperty(value = "56日ROI(0)")
    private java.math.BigDecimal day56=BigDecimal.ZERO;
    /**57日ROI(0)*/
    @Excel(name = "57日ROI(0)", width = 15)
    @ApiModelProperty(value = "57日ROI(0)")
    private java.math.BigDecimal day57=BigDecimal.ZERO;
    /**58日ROI(0)*/
    @Excel(name = "58日ROI(0)", width = 15)
    @ApiModelProperty(value = "58日ROI(0)")
    private java.math.BigDecimal day58=BigDecimal.ZERO;
    /**59日ROI(0)*/
    @Excel(name = "59日ROI(0)", width = 15)
    @ApiModelProperty(value = "59日ROI(0)")
    private java.math.BigDecimal day59=BigDecimal.ZERO;
    /**60日ROI(0)*/
    @Excel(name = "60日ROI(0)", width = 15)
    @ApiModelProperty(value = "60日ROI(0)")
    private java.math.BigDecimal day60=BigDecimal.ZERO;
    /**61日ROI(0)*/
    @Excel(name = "61日ROI(0)", width = 15)
    @ApiModelProperty(value = "61日ROI(0)")
    private java.math.BigDecimal day61=BigDecimal.ZERO;
    /**62日ROI(0)*/
    @Excel(name = "62日ROI(0)", width = 15)
    @ApiModelProperty(value = "62日ROI(0)")
    private java.math.BigDecimal day62=BigDecimal.ZERO;
    /**63日ROI(0)*/
    @Excel(name = "63日ROI(0)", width = 15)
    @ApiModelProperty(value = "63日ROI(0)")
    private java.math.BigDecimal day63=BigDecimal.ZERO;
    /**64日ROI(0)*/
    @Excel(name = "64日ROI(0)", width = 15)
    @ApiModelProperty(value = "64日ROI(0)")
    private java.math.BigDecimal day64=BigDecimal.ZERO;
    /**65日ROI(0)*/
    @Excel(name = "65日ROI(0)", width = 15)
    @ApiModelProperty(value = "65日ROI(0)")
    private java.math.BigDecimal day65=BigDecimal.ZERO;
    /**66日ROI(0)*/
    @Excel(name = "66日ROI(0)", width = 15)
    @ApiModelProperty(value = "66日ROI(0)")
    private java.math.BigDecimal day66=BigDecimal.ZERO;
    /**67日ROI(0)*/
    @Excel(name = "67日ROI(0)", width = 15)
    @ApiModelProperty(value = "67日ROI(0)")
    private java.math.BigDecimal day67=BigDecimal.ZERO;
    /**68日ROI(0)*/
    @Excel(name = "68日ROI(0)", width = 15)
    @ApiModelProperty(value = "68日ROI(0)")
    private java.math.BigDecimal day68=BigDecimal.ZERO;
    /**69日ROI(0)*/
    @Excel(name = "69日ROI(0)", width = 15)
    @ApiModelProperty(value = "69日ROI(0)")
    private java.math.BigDecimal day69=BigDecimal.ZERO;
    /**70日ROI(0)*/
    @Excel(name = "70日ROI(0)", width = 15)
    @ApiModelProperty(value = "70日ROI(0)")
    private java.math.BigDecimal day70=BigDecimal.ZERO;
    /**71日ROI(0)*/
    @Excel(name = "71日ROI(0)", width = 15)
    @ApiModelProperty(value = "71日ROI(0)")
    private java.math.BigDecimal day71=BigDecimal.ZERO;
    /**72日ROI(0)*/
    @Excel(name = "72日ROI(0)", width = 15)
    @ApiModelProperty(value = "72日ROI(0)")
    private java.math.BigDecimal day72=BigDecimal.ZERO;
    /**73日ROI(0)*/
    @Excel(name = "73日ROI(0)", width = 15)
    @ApiModelProperty(value = "73日ROI(0)")
    private java.math.BigDecimal day73=BigDecimal.ZERO;
    /**74日ROI(0)*/
    @Excel(name = "74日ROI(0)", width = 15)
    @ApiModelProperty(value = "74日ROI(0)")
    private java.math.BigDecimal day74=BigDecimal.ZERO;
    /**75日ROI(0)*/
    @Excel(name = "75日ROI(0)", width = 15)
    @ApiModelProperty(value = "75日ROI(0)")
    private java.math.BigDecimal day75=BigDecimal.ZERO;
    /**76日ROI(0)*/
    @Excel(name = "76日ROI(0)", width = 15)
    @ApiModelProperty(value = "76日ROI(0)")
    private java.math.BigDecimal day76=BigDecimal.ZERO;
    /**77日ROI(0)*/
    @Excel(name = "77日ROI(0)", width = 15)
    @ApiModelProperty(value = "77日ROI(0)")
    private java.math.BigDecimal day77=BigDecimal.ZERO;
    /**78日ROI(0)*/
    @Excel(name = "78日ROI(0)", width = 15)
    @ApiModelProperty(value = "78日ROI(0)")
    private java.math.BigDecimal day78=BigDecimal.ZERO;
    /**79日ROI(0)*/
    @Excel(name = "79日ROI(0)", width = 15)
    @ApiModelProperty(value = "79日ROI(0)")
    private java.math.BigDecimal day79=BigDecimal.ZERO;
    /**80日ROI(0)*/
    @Excel(name = "80日ROI(0)", width = 15)
    @ApiModelProperty(value = "80日ROI(0)")
    private java.math.BigDecimal day80=BigDecimal.ZERO;
    /**81日ROI(0)*/
    @Excel(name = "81日ROI(0)", width = 15)
    @ApiModelProperty(value = "81日ROI(0)")
    private java.math.BigDecimal day81=BigDecimal.ZERO;
    /**82日ROI(0)*/
    @Excel(name = "82日ROI(0)", width = 15)
    @ApiModelProperty(value = "82日ROI(0)")
    private java.math.BigDecimal day82=BigDecimal.ZERO;
    /**83日ROI(0)*/
    @Excel(name = "83日ROI(0)", width = 15)
    @ApiModelProperty(value = "83日ROI(0)")
    private java.math.BigDecimal day83=BigDecimal.ZERO;
    /**84日ROI(0)*/
    @Excel(name = "84日ROI(0)", width = 15)
    @ApiModelProperty(value = "84日ROI(0)")
    private java.math.BigDecimal day84=BigDecimal.ZERO;
    /**85日ROI(0)*/
    @Excel(name = "85日ROI(0)", width = 15)
    @ApiModelProperty(value = "85日ROI(0)")
    private java.math.BigDecimal day85=BigDecimal.ZERO;
    /**86日ROI(0)*/
    @Excel(name = "86日ROI(0)", width = 15)
    @ApiModelProperty(value = "86日ROI(0)")
    private java.math.BigDecimal day86=BigDecimal.ZERO;
    /**87日ROI(0)*/
    @Excel(name = "87日ROI(0)", width = 15)
    @ApiModelProperty(value = "87日ROI(0)")
    private java.math.BigDecimal day87=BigDecimal.ZERO;
    /**88日ROI(0)*/
    @Excel(name = "88日ROI(0)", width = 15)
    @ApiModelProperty(value = "88日ROI(0)")
    private java.math.BigDecimal day88=BigDecimal.ZERO;
    /**89日ROI(0)*/
    @Excel(name = "89日ROI(0)", width = 15)
    @ApiModelProperty(value = "89日ROI(0)")
    private java.math.BigDecimal day89=BigDecimal.ZERO;
    /**90日ROI(0)*/
    @Excel(name = "90日ROI(0)", width = 15)
    @ApiModelProperty(value = "90日ROI(0)")
    private java.math.BigDecimal day90=BigDecimal.ZERO;
    /**91日ROI(0)*/
    @Excel(name = "91日ROI(0)", width = 15)
    @ApiModelProperty(value = "91日ROI(0)")
    private java.math.BigDecimal day91=BigDecimal.ZERO;
    /**92日ROI(0)*/
    @Excel(name = "92日ROI(0)", width = 15)
    @ApiModelProperty(value = "92日ROI(0)")
    private java.math.BigDecimal day92=BigDecimal.ZERO;
    /**93日ROI(0)*/
    @Excel(name = "93日ROI(0)", width = 15)
    @ApiModelProperty(value = "93日ROI(0)")
    private java.math.BigDecimal day93=BigDecimal.ZERO;
    /**94日ROI(0)*/
    @Excel(name = "94日ROI(0)", width = 15)
    @ApiModelProperty(value = "94日ROI(0)")
    private java.math.BigDecimal day94=BigDecimal.ZERO;
    /**95日ROI(0)*/
    @Excel(name = "95日ROI(0)", width = 15)
    @ApiModelProperty(value = "95日ROI(0)")
    private java.math.BigDecimal day95=BigDecimal.ZERO;
    /**96日ROI(0)*/
    @Excel(name = "96日ROI(0)", width = 15)
    @ApiModelProperty(value = "96日ROI(0)")
    private java.math.BigDecimal day96=BigDecimal.ZERO;
    /**97日ROI(0)*/
    @Excel(name = "97日ROI(0)", width = 15)
    @ApiModelProperty(value = "97日ROI(0)")
    private java.math.BigDecimal day97=BigDecimal.ZERO;
    /**98日ROI(0)*/
    @Excel(name = "98日ROI(0)", width = 15)
    @ApiModelProperty(value = "98日ROI(0)")
    private java.math.BigDecimal day98=BigDecimal.ZERO;
    /**99日ROI(0)*/
    @Excel(name = "99日ROI(0)", width = 15)
    @ApiModelProperty(value = "99日ROI(0)")
    private java.math.BigDecimal day99=BigDecimal.ZERO;
    /**100日ROI(0)*/
    @Excel(name = "100日ROI(0)", width = 15)
    @ApiModelProperty(value = "100日ROI(0)")
    private java.math.BigDecimal day100=BigDecimal.ZERO;
    /**101日ROI(0)*/
    @Excel(name = "101日ROI(0)", width = 15)
    @ApiModelProperty(value = "101日ROI(0)")
    private java.math.BigDecimal day101=BigDecimal.ZERO;
    /**102日ROI(0)*/
    @Excel(name = "102日ROI(0)", width = 15)
    @ApiModelProperty(value = "102日ROI(0)")
    private java.math.BigDecimal day102=BigDecimal.ZERO;
    /**103日ROI(0)*/
    @Excel(name = "103日ROI(0)", width = 15)
    @ApiModelProperty(value = "103日ROI(0)")
    private java.math.BigDecimal day103=BigDecimal.ZERO;
    /**104日ROI(0)*/
    @Excel(name = "104日ROI(0)", width = 15)
    @ApiModelProperty(value = "104日ROI(0)")
    private java.math.BigDecimal day104=BigDecimal.ZERO;
    /**105日ROI(0)*/
    @Excel(name = "105日ROI(0)", width = 15)
    @ApiModelProperty(value = "105日ROI(0)")
    private java.math.BigDecimal day105=BigDecimal.ZERO;
    /**106日ROI(0)*/
    @Excel(name = "106日ROI(0)", width = 15)
    @ApiModelProperty(value = "106日ROI(0)")
    private java.math.BigDecimal day106=BigDecimal.ZERO;
    /**107日ROI(0)*/
    @Excel(name = "107日ROI(0)", width = 15)
    @ApiModelProperty(value = "107日ROI(0)")
    private java.math.BigDecimal day107=BigDecimal.ZERO;
    /**108日ROI(0)*/
    @Excel(name = "108日ROI(0)", width = 15)
    @ApiModelProperty(value = "108日ROI(0)")
    private java.math.BigDecimal day108=BigDecimal.ZERO;
    /**109日ROI(0)*/
    @Excel(name = "109日ROI(0)", width = 15)
    @ApiModelProperty(value = "109日ROI(0)")
    private java.math.BigDecimal day109=BigDecimal.ZERO;
    /**110日ROI(0)*/
    @Excel(name = "110日ROI(0)", width = 15)
    @ApiModelProperty(value = "110日ROI(0)")
    private java.math.BigDecimal day110=BigDecimal.ZERO;
    /**111日ROI(0)*/
    @Excel(name = "111日ROI(0)", width = 15)
    @ApiModelProperty(value = "111日ROI(0)")
    private java.math.BigDecimal day111=BigDecimal.ZERO;
    /**112日ROI(0)*/
    @Excel(name = "112日ROI(0)", width = 15)
    @ApiModelProperty(value = "112日ROI(0)")
    private java.math.BigDecimal day112=BigDecimal.ZERO;
    /**113日ROI(0)*/
    @Excel(name = "113日ROI(0)", width = 15)
    @ApiModelProperty(value = "113日ROI(0)")
    private java.math.BigDecimal day113=BigDecimal.ZERO;
    /**114日ROI(0)*/
    @Excel(name = "114日ROI(0)", width = 15)
    @ApiModelProperty(value = "114日ROI(0)")
    private java.math.BigDecimal day114=BigDecimal.ZERO;
    /**115日ROI(0)*/
    @Excel(name = "115日ROI(0)", width = 15)
    @ApiModelProperty(value = "115日ROI(0)")
    private java.math.BigDecimal day115=BigDecimal.ZERO;
    /**116日ROI(0)*/
    @Excel(name = "116日ROI(0)", width = 15)
    @ApiModelProperty(value = "116日ROI(0)")
    private java.math.BigDecimal day116=BigDecimal.ZERO;
    /**117日ROI(0)*/
    @Excel(name = "117日ROI(0)", width = 15)
    @ApiModelProperty(value = "117日ROI(0)")
    private java.math.BigDecimal day117=BigDecimal.ZERO;
    /**118日ROI(0)*/
    @Excel(name = "118日ROI(0)", width = 15)
    @ApiModelProperty(value = "118日ROI(0)")
    private java.math.BigDecimal day118=BigDecimal.ZERO;
    /**119日ROI(0)*/
    @Excel(name = "119日ROI(0)", width = 15)
    @ApiModelProperty(value = "119日ROI(0)")
    private java.math.BigDecimal day119=BigDecimal.ZERO;
    /**120日ROI(0)*/
    @Excel(name = "120日ROI(0)", width = 15)
    @ApiModelProperty(value = "120日ROI(0)")
    private java.math.BigDecimal day120=BigDecimal.ZERO;
    /**121日ROI(0)*/
    @Excel(name = "121日ROI(0)", width = 15)
    @ApiModelProperty(value = "121日ROI(0)")
    private java.math.BigDecimal day121=BigDecimal.ZERO;
    /**122日ROI(0)*/
    @Excel(name = "122日ROI(0)", width = 15)
    @ApiModelProperty(value = "122日ROI(0)")
    private java.math.BigDecimal day122=BigDecimal.ZERO;
    /**123日ROI(0)*/
    @Excel(name = "123日ROI(0)", width = 15)
    @ApiModelProperty(value = "123日ROI(0)")
    private java.math.BigDecimal day123=BigDecimal.ZERO;
    /**124日ROI(0)*/
    @Excel(name = "124日ROI(0)", width = 15)
    @ApiModelProperty(value = "124日ROI(0)")
    private java.math.BigDecimal day124=BigDecimal.ZERO;
    /**125日ROI(0)*/
    @Excel(name = "125日ROI(0)", width = 15)
    @ApiModelProperty(value = "125日ROI(0)")
    private java.math.BigDecimal day125=BigDecimal.ZERO;
    /**126日ROI(0)*/
    @Excel(name = "126日ROI(0)", width = 15)
    @ApiModelProperty(value = "126日ROI(0)")
    private java.math.BigDecimal day126=BigDecimal.ZERO;
    /**127日ROI(0)*/
    @Excel(name = "127日ROI(0)", width = 15)
    @ApiModelProperty(value = "127日ROI(0)")
    private java.math.BigDecimal day127=BigDecimal.ZERO;
    /**128日ROI(0)*/
    @Excel(name = "128日ROI(0)", width = 15)
    @ApiModelProperty(value = "128日ROI(0)")
    private java.math.BigDecimal day128=BigDecimal.ZERO;
    /**129日ROI(0)*/
    @Excel(name = "129日ROI(0)", width = 15)
    @ApiModelProperty(value = "129日ROI(0)")
    private java.math.BigDecimal day129=BigDecimal.ZERO;
    /**130日ROI(0)*/
    @Excel(name = "130日ROI(0)", width = 15)
    @ApiModelProperty(value = "130日ROI(0)")
    private java.math.BigDecimal day130=BigDecimal.ZERO;
    /**131日ROI(0)*/
    @Excel(name = "131日ROI(0)", width = 15)
    @ApiModelProperty(value = "131日ROI(0)")
    private java.math.BigDecimal day131=BigDecimal.ZERO;
    /**132日ROI(0)*/
    @Excel(name = "132日ROI(0)", width = 15)
    @ApiModelProperty(value = "132日ROI(0)")
    private java.math.BigDecimal day132=BigDecimal.ZERO;
    /**133日ROI(0)*/
    @Excel(name = "133日ROI(0)", width = 15)
    @ApiModelProperty(value = "133日ROI(0)")
    private java.math.BigDecimal day133=BigDecimal.ZERO;
    /**134日ROI(0)*/
    @Excel(name = "134日ROI(0)", width = 15)
    @ApiModelProperty(value = "134日ROI(0)")
    private java.math.BigDecimal day134=BigDecimal.ZERO;
    /**135日ROI(0)*/
    @Excel(name = "135日ROI(0)", width = 15)
    @ApiModelProperty(value = "135日ROI(0)")
    private java.math.BigDecimal day135=BigDecimal.ZERO;
    /**136日ROI(0)*/
    @Excel(name = "136日ROI(0)", width = 15)
    @ApiModelProperty(value = "136日ROI(0)")
    private java.math.BigDecimal day136=BigDecimal.ZERO;
    /**137日ROI(0)*/
    @Excel(name = "137日ROI(0)", width = 15)
    @ApiModelProperty(value = "137日ROI(0)")
    private java.math.BigDecimal day137=BigDecimal.ZERO;
    /**138日ROI(0)*/
    @Excel(name = "138日ROI(0)", width = 15)
    @ApiModelProperty(value = "138日ROI(0)")
    private java.math.BigDecimal day138=BigDecimal.ZERO;
    /**139日ROI(0)*/
    @Excel(name = "139日ROI(0)", width = 15)
    @ApiModelProperty(value = "139日ROI(0)")
    private java.math.BigDecimal day139=BigDecimal.ZERO;
    /**140日ROI(0)*/
    @Excel(name = "140日ROI(0)", width = 15)
    @ApiModelProperty(value = "140日ROI(0)")
    private java.math.BigDecimal day140=BigDecimal.ZERO;
    /**141日ROI(0)*/
    @Excel(name = "141日ROI(0)", width = 15)
    @ApiModelProperty(value = "141日ROI(0)")
    private java.math.BigDecimal day141=BigDecimal.ZERO;
    /**142日ROI(0)*/
    @Excel(name = "142日ROI(0)", width = 15)
    @ApiModelProperty(value = "142日ROI(0)")
    private java.math.BigDecimal day142=BigDecimal.ZERO;
    /**143日ROI(0)*/
    @Excel(name = "143日ROI(0)", width = 15)
    @ApiModelProperty(value = "143日ROI(0)")
    private java.math.BigDecimal day143=BigDecimal.ZERO;
    /**144日ROI(0)*/
    @Excel(name = "144日ROI(0)", width = 15)
    @ApiModelProperty(value = "144日ROI(0)")
    private java.math.BigDecimal day144=BigDecimal.ZERO;
    /**145日ROI(0)*/
    @Excel(name = "145日ROI(0)", width = 15)
    @ApiModelProperty(value = "145日ROI(0)")
    private java.math.BigDecimal day145=BigDecimal.ZERO;
    /**146日ROI(0)*/
    @Excel(name = "146日ROI(0)", width = 15)
    @ApiModelProperty(value = "146日ROI(0)")
    private java.math.BigDecimal day146=BigDecimal.ZERO;
    /**147日ROI(0)*/
    @Excel(name = "147日ROI(0)", width = 15)
    @ApiModelProperty(value = "147日ROI(0)")
    private java.math.BigDecimal day147=BigDecimal.ZERO;
    /**148日ROI(0)*/
    @Excel(name = "148日ROI(0)", width = 15)
    @ApiModelProperty(value = "148日ROI(0)")
    private java.math.BigDecimal day148=BigDecimal.ZERO;
    /**149日ROI(0)*/
    @Excel(name = "149日ROI(0)", width = 15)
    @ApiModelProperty(value = "149日ROI(0)")
    private java.math.BigDecimal day149=BigDecimal.ZERO;
    /**150日ROI(0)*/
    @Excel(name = "150日ROI(0)", width = 15)
    @ApiModelProperty(value = "150日ROI(0)")
    private java.math.BigDecimal day150= BigDecimal.ZERO;



}
