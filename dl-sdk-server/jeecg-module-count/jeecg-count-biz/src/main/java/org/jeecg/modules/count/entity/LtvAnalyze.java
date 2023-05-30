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
 * @Description: LTV分析
 * @Author: jeecg-boot
 * @Date:   2023-05-13
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ltv_analyze对象", description="LTV分析")
public class LtvAnalyze implements Serializable {
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
	/**游戏包名*/
	@Excel(name = "游戏包名", width = 15)
    @ApiModelProperty(value = "游戏包名")
    private java.lang.Integer pkgId;
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private java.lang.Integer channelTypeId;
	/**渠道名称*/
	@Excel(name = "渠道名称", width = 15)
    @ApiModelProperty(value = "渠道名称")
    private java.lang.Integer channelId;
	/**二级渠道*/
	@Excel(name = "二级渠道", width = 15)
    @ApiModelProperty(value = "二级渠道")
    private java.lang.Integer channelSubAccountId;
	/**广告列表*/
	@Excel(name = "广告列表", width = 15)
    @ApiModelProperty(value = "广告列表")
    private java.lang.Integer dealId;
	/**起始日期*/
	@Excel(name = "起始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "起始日期")
    private java.util.Date startTime;
	/**结束日期*/
	@Excel(name = "结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private java.util.Date endTime;
	/**账号人员*/
	@Excel(name = "账号人员", width = 15)
    @ApiModelProperty(value = "账号人员")
    private java.lang.String userType;
	/**日期*/
	@Excel(name = "日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "日期")
    private java.util.Date dateTime;
	/**游戏名*/
	@Excel(name = "游戏名", width = 15)
    @ApiModelProperty(value = "游戏名")
    private java.lang.String gameName;
	/**渠道名*/
	@Excel(name = "渠道名", width = 15)
    @ApiModelProperty(value = "渠道名")
    private java.lang.String channelName;
	/**广告名*/
	@Excel(name = "广告名", width = 15)
    @ApiModelProperty(value = "广告名")
    private java.lang.String dealName;
	/**注册数*/
	@Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private java.lang.Integer countUser;
	/**LTV1*/
	@Excel(name = "LTV1", width = 15)
    @ApiModelProperty(value = "LTV1")
    private java.math.BigDecimal ltv1;
	/**LTV2*/
	@Excel(name = "LTV2", width = 15)
    @ApiModelProperty(value = "LTV2")
    private java.math.BigDecimal lyv2;
	/**LTV3*/
	@Excel(name = "LTV3", width = 15)
    @ApiModelProperty(value = "LTV3")
    private java.math.BigDecimal ltv3;
	/**LTV4*/
	@Excel(name = "LTV4", width = 15)
    @ApiModelProperty(value = "LTV4")
    private java.math.BigDecimal ltv4;
	/**LTV5*/
	@Excel(name = "LTV5", width = 15)
    @ApiModelProperty(value = "LTV5")
    private java.math.BigDecimal ltv5;
	/**LTV6*/
	@Excel(name = "LTV6", width = 15)
    @ApiModelProperty(value = "LTV6")
    private java.math.BigDecimal ltv6;
	/**LTV7*/
	@Excel(name = "LTV7", width = 15)
    @ApiModelProperty(value = "LTV7")
    private java.math.BigDecimal ltv7;
	/**LTV8*/
	@Excel(name = "LTV8", width = 15)
    @ApiModelProperty(value = "LTV8")
    private java.math.BigDecimal ltv8;
	/**LTV9*/
	@Excel(name = "LTV9", width = 15)
    @ApiModelProperty(value = "LTV9")
    private java.math.BigDecimal ltv9;
	/**LTV10*/
	@Excel(name = "LTV10", width = 15)
    @ApiModelProperty(value = "LTV10")
    private java.math.BigDecimal ltv10;
	/**LTV11*/
	@Excel(name = "LTV11", width = 15)
    @ApiModelProperty(value = "LTV11")
    private java.math.BigDecimal ltv11;
	/**LTV12*/
	@Excel(name = "LTV12", width = 15)
    @ApiModelProperty(value = "LTV12")
    private java.math.BigDecimal ltv12;
	/**LTV13*/
	@Excel(name = "LTV13", width = 15)
    @ApiModelProperty(value = "LTV13")
    private java.math.BigDecimal ltv13;
	/**LTV14*/
	@Excel(name = "LTV14", width = 15)
    @ApiModelProperty(value = "LTV14")
    private java.math.BigDecimal ltv14;
	/**LTV15*/
	@Excel(name = "LTV15", width = 15)
    @ApiModelProperty(value = "LTV15")
    private java.math.BigDecimal ltv15;
	/**LTV16*/
	@Excel(name = "LTV16", width = 15)
    @ApiModelProperty(value = "LTV16")
    private java.math.BigDecimal ltv16;
	/**LTV17*/
	@Excel(name = "LTV17", width = 15)
    @ApiModelProperty(value = "LTV17")
    private java.math.BigDecimal ltv17;
	/**LTV18*/
	@Excel(name = "LTV18", width = 15)
    @ApiModelProperty(value = "LTV18")
    private java.math.BigDecimal ltv18;
	/**LTV19*/
	@Excel(name = "LTV19", width = 15)
    @ApiModelProperty(value = "LTV19")
    private java.math.BigDecimal ltv19;
	/**LTV20*/
	@Excel(name = "LTV20", width = 15)
    @ApiModelProperty(value = "LTV20")
    private java.math.BigDecimal ltv20;
	/**LTV21*/
	@Excel(name = "LTV21", width = 15)
    @ApiModelProperty(value = "LTV21")
    private java.math.BigDecimal ltv21;
	/**LTV22*/
	@Excel(name = "LTV22", width = 15)
    @ApiModelProperty(value = "LTV22")
    private java.math.BigDecimal ltv22;
	/**LTV23*/
	@Excel(name = "LTV23", width = 15)
    @ApiModelProperty(value = "LTV23")
    private java.math.BigDecimal ltv23;
	/**LTV24*/
	@Excel(name = "LTV24", width = 15)
    @ApiModelProperty(value = "LTV24")
    private java.math.BigDecimal ltv24;
	/**LTV26*/
	@Excel(name = "LTV26", width = 15)
    @ApiModelProperty(value = "LTV26")
    private java.math.BigDecimal ltv26;
	/**LTV27*/
	@Excel(name = "LTV27", width = 15)
    @ApiModelProperty(value = "LTV27")
    private java.math.BigDecimal ltv27;
	/**LTV28*/
	@Excel(name = "LTV28", width = 15)
    @ApiModelProperty(value = "LTV28")
    private java.math.BigDecimal ltv28;
	/**LTV29*/
	@Excel(name = "LTV29", width = 15)
    @ApiModelProperty(value = "LTV29")
    private java.math.BigDecimal ltv29;
	/**LTV30*/
	@Excel(name = "LTV30", width = 15)
    @ApiModelProperty(value = "LTV30")
    private java.math.BigDecimal ltv30;
	/**LTV31*/
	@Excel(name = "LTV31", width = 15)
    @ApiModelProperty(value = "LTV31")
    private java.math.BigDecimal ltv31;
	/**LTV32*/
	@Excel(name = "LTV32", width = 15)
    @ApiModelProperty(value = "LTV32")
    private java.math.BigDecimal ltv32;
	/**LTV33*/
	@Excel(name = "LTV33", width = 15)
    @ApiModelProperty(value = "LTV33")
    private java.math.BigDecimal ltv33;
	/**LTV34*/
	@Excel(name = "LTV34", width = 15)
    @ApiModelProperty(value = "LTV34")
    private java.math.BigDecimal ltv34;
	/**LTV35*/
	@Excel(name = "LTV35", width = 15)
    @ApiModelProperty(value = "LTV35")
    private java.math.BigDecimal ltv35;
	/**LTV36*/
	@Excel(name = "LTV36", width = 15)
    @ApiModelProperty(value = "LTV36")
    private java.math.BigDecimal ltv36;
	/**LTV37*/
	@Excel(name = "LTV37", width = 15)
    @ApiModelProperty(value = "LTV37")
    private java.math.BigDecimal ltv37;
	/**LTV38*/
	@Excel(name = "LTV38", width = 15)
    @ApiModelProperty(value = "LTV38")
    private java.math.BigDecimal ltv38;
	/**LTV39*/
	@Excel(name = "LTV39", width = 15)
    @ApiModelProperty(value = "LTV39")
    private java.math.BigDecimal ltv39;
	/**LTV40*/
	@Excel(name = "LTV40", width = 15)
    @ApiModelProperty(value = "LTV40")
    private java.math.BigDecimal ltv40;
	/**LTV41*/
	@Excel(name = "LTV41", width = 15)
    @ApiModelProperty(value = "LTV41")
    private java.math.BigDecimal ltv41;
	/**LTV42*/
	@Excel(name = "LTV42", width = 15)
    @ApiModelProperty(value = "LTV42")
    private java.math.BigDecimal ltv42;
	/**LTV43*/
	@Excel(name = "LTV43", width = 15)
    @ApiModelProperty(value = "LTV43")
    private java.math.BigDecimal ltv43;
	/**LTV44*/
	@Excel(name = "LTV44", width = 15)
    @ApiModelProperty(value = "LTV44")
    private java.math.BigDecimal ltv44;
	/**LTV45*/
	@Excel(name = "LTV45", width = 15)
    @ApiModelProperty(value = "LTV45")
    private java.math.BigDecimal ltv45;
	/**LTV46*/
	@Excel(name = "LTV46", width = 15)
    @ApiModelProperty(value = "LTV46")
    private java.math.BigDecimal ltv46;
	/**LTV47*/
	@Excel(name = "LTV47", width = 15)
    @ApiModelProperty(value = "LTV47")
    private java.math.BigDecimal ltv47;
	/**LTV48*/
	@Excel(name = "LTV48", width = 15)
    @ApiModelProperty(value = "LTV48")
    private java.math.BigDecimal ltv48;
	/**LTV49*/
	@Excel(name = "LTV49", width = 15)
    @ApiModelProperty(value = "LTV49")
    private java.math.BigDecimal ltv49;
	/**LTV50*/
	@Excel(name = "LTV50", width = 15)
    @ApiModelProperty(value = "LTV50")
    private java.math.BigDecimal ltv50;
	/**LTV51*/
	@Excel(name = "LTV51", width = 15)
    @ApiModelProperty(value = "LTV51")
    private java.math.BigDecimal ltv51;
	/**LTV52*/
	@Excel(name = "LTV52", width = 15)
    @ApiModelProperty(value = "LTV52")
    private java.math.BigDecimal ltv52;
	/**LTV53*/
	@Excel(name = "LTV53", width = 15)
    @ApiModelProperty(value = "LTV53")
    private java.math.BigDecimal ltv53;
	/**LTV54*/
	@Excel(name = "LTV54", width = 15)
    @ApiModelProperty(value = "LTV54")
    private java.math.BigDecimal ltv54;
	/**LTV55*/
	@Excel(name = "LTV55", width = 15)
    @ApiModelProperty(value = "LTV55")
    private java.math.BigDecimal ltv55;
	/**LTV56*/
	@Excel(name = "LTV56", width = 15)
    @ApiModelProperty(value = "LTV56")
    private java.math.BigDecimal ltv56;
	/**LTV57*/
	@Excel(name = "LTV57", width = 15)
    @ApiModelProperty(value = "LTV57")
    private java.lang.String ltv57;
	/**LTV58*/
	@Excel(name = "LTV58", width = 15)
    @ApiModelProperty(value = "LTV58")
    private java.lang.String ltv58;
	/**LTV59*/
	@Excel(name = "LTV59", width = 15)
    @ApiModelProperty(value = "LTV59")
    private java.lang.String ltv59;
	/**LTV60*/
	@Excel(name = "LTV60", width = 15)
    @ApiModelProperty(value = "LTV60")
    private java.lang.String ltv60;
	/**LTV61*/
	@Excel(name = "LTV61", width = 15)
    @ApiModelProperty(value = "LTV61")
    private java.lang.String ltv61;
	/**LTV62*/
	@Excel(name = "LTV62", width = 15)
    @ApiModelProperty(value = "LTV62")
    private java.lang.String ltv62;
	/**LTV63*/
	@Excel(name = "LTV63", width = 15)
    @ApiModelProperty(value = "LTV63")
    private java.lang.String ltv63;
	/**LTV64*/
	@Excel(name = "LTV64", width = 15)
    @ApiModelProperty(value = "LTV64")
    private java.lang.String ltv64;
	/**LTV65*/
	@Excel(name = "LTV65", width = 15)
    @ApiModelProperty(value = "LTV65")
    private java.lang.String ltv65;
	/**LTV66*/
	@Excel(name = "LTV66", width = 15)
    @ApiModelProperty(value = "LTV66")
    private java.lang.String ltv66;
	/**LTV67*/
	@Excel(name = "LTV67", width = 15)
    @ApiModelProperty(value = "LTV67")
    private java.lang.String ltv67;
	/**LTV68*/
	@Excel(name = "LTV68", width = 15)
    @ApiModelProperty(value = "LTV68")
    private java.lang.String ltv68;
	/**LTV69*/
	@Excel(name = "LTV69", width = 15)
    @ApiModelProperty(value = "LTV69")
    private java.lang.String ltv69;
	/**LTV670*/
	@Excel(name = "LTV670", width = 15)
    @ApiModelProperty(value = "LTV670")
    private java.lang.String ltv70;
	/**LTV71*/
	@Excel(name = "LTV71", width = 15)
    @ApiModelProperty(value = "LTV71")
    private java.lang.String ltv71;
	/**LTV72*/
	@Excel(name = "LTV72", width = 15)
    @ApiModelProperty(value = "LTV72")
    private java.lang.String ltv72;
	/**LTV73*/
	@Excel(name = "LTV73", width = 15)
    @ApiModelProperty(value = "LTV73")
    private java.lang.String ltv73;
	/**LTV74*/
	@Excel(name = "LTV74", width = 15)
    @ApiModelProperty(value = "LTV74")
    private java.lang.String ltv74;
	/**LTV75*/
	@Excel(name = "LTV75", width = 15)
    @ApiModelProperty(value = "LTV75")
    private java.lang.String ltv75;
	/**LTV76*/
	@Excel(name = "LTV76", width = 15)
    @ApiModelProperty(value = "LTV76")
    private java.lang.String ltv76;
	/**LTV77*/
	@Excel(name = "LTV77", width = 15)
    @ApiModelProperty(value = "LTV77")
    private java.lang.String ltv77;
	/**LTV78*/
	@Excel(name = "LTV78", width = 15)
    @ApiModelProperty(value = "LTV78")
    private java.lang.String ltv78;
	/**LTV79*/
	@Excel(name = "LTV79", width = 15)
    @ApiModelProperty(value = "LTV79")
    private java.lang.String ltv79;
	/**LTV80*/
	@Excel(name = "LTV80", width = 15)
    @ApiModelProperty(value = "LTV80")
    private java.lang.String ltv80;
	/**LTV81*/
	@Excel(name = "LTV81", width = 15)
    @ApiModelProperty(value = "LTV81")
    private java.lang.String ltv81;
	/**LTV82*/
	@Excel(name = "LTV82", width = 15)
    @ApiModelProperty(value = "LTV82")
    private java.lang.String ltv82;
	/**LTV83*/
	@Excel(name = "LTV83", width = 15)
    @ApiModelProperty(value = "LTV83")
    private java.lang.String ltv83;
	/**LTV84*/
	@Excel(name = "LTV84", width = 15)
    @ApiModelProperty(value = "LTV84")
    private java.lang.String ltv84;
	/**LTV85*/
	@Excel(name = "LTV85", width = 15)
    @ApiModelProperty(value = "LTV85")
    private java.lang.String ltv85;
	/**LTV86*/
	@Excel(name = "LTV86", width = 15)
    @ApiModelProperty(value = "LTV86")
    private java.lang.String ltv86;
	/**LTV87*/
	@Excel(name = "LTV87", width = 15)
    @ApiModelProperty(value = "LTV87")
    private java.lang.String ltv87;
	/**LTV88*/
	@Excel(name = "LTV88", width = 15)
    @ApiModelProperty(value = "LTV88")
    private java.lang.String ltv88;
	/**LTV89*/
	@Excel(name = "LTV89", width = 15)
    @ApiModelProperty(value = "LTV89")
    private java.lang.String ltv89;
	/**LTV90*/
	@Excel(name = "LTV90", width = 15)
    @ApiModelProperty(value = "LTV90")
    private java.lang.String ltv90;
	/**LTV91*/
	@Excel(name = "LTV91", width = 15)
    @ApiModelProperty(value = "LTV91")
    private java.lang.String ltv91;
	/**LTV92*/
	@Excel(name = "LTV92", width = 15)
    @ApiModelProperty(value = "LTV92")
    private java.lang.String ltv92;
	/**LTV93*/
	@Excel(name = "LTV93", width = 15)
    @ApiModelProperty(value = "LTV93")
    private java.lang.String ltv93;
	/**LTV94*/
	@Excel(name = "LTV94", width = 15)
    @ApiModelProperty(value = "LTV94")
    private java.lang.String ltv94;
	/**LTV95*/
	@Excel(name = "LTV95", width = 15)
    @ApiModelProperty(value = "LTV95")
    private java.lang.String ltv95;
	/**LTV96*/
	@Excel(name = "LTV96", width = 15)
    @ApiModelProperty(value = "LTV96")
    private java.lang.String ltv96;
	/**LTV97*/
	@Excel(name = "LTV97", width = 15)
    @ApiModelProperty(value = "LTV97")
    private java.lang.String ltv97;
	/**LTV98*/
	@Excel(name = "LTV98", width = 15)
    @ApiModelProperty(value = "LTV98")
    private java.lang.String ltv98;
	/**LTV99*/
	@Excel(name = "LTV99", width = 15)
    @ApiModelProperty(value = "LTV99")
    private java.lang.String ltv99;
	/**LTV100*/
	@Excel(name = "LTV100", width = 15)
    @ApiModelProperty(value = "LTV100")
    private java.lang.String ltv100;
	/**LTV101*/
	@Excel(name = "LTV101", width = 15)
    @ApiModelProperty(value = "LTV101")
    private java.lang.String ltv101;
	/**LTV102*/
	@Excel(name = "LTV102", width = 15)
    @ApiModelProperty(value = "LTV102")
    private java.lang.String ltv102;
	/**LTV103*/
	@Excel(name = "LTV103", width = 15)
    @ApiModelProperty(value = "LTV103")
    private java.lang.String ltv103;
	/**LTV104*/
	@Excel(name = "LTV104", width = 15)
    @ApiModelProperty(value = "LTV104")
    private java.lang.String ltv104;
	/**LTV105*/
	@Excel(name = "LTV105", width = 15)
    @ApiModelProperty(value = "LTV105")
    private java.lang.String ltv105;
	/**LTV106*/
	@Excel(name = "LTV106", width = 15)
    @ApiModelProperty(value = "LTV106")
    private java.lang.String ltv106;
	/**LTV107*/
	@Excel(name = "LTV107", width = 15)
    @ApiModelProperty(value = "LTV107")
    private java.lang.String ltv107;
	/**LTV108*/
	@Excel(name = "LTV108", width = 15)
    @ApiModelProperty(value = "LTV108")
    private java.lang.String ltv108;
	/**LTV109*/
	@Excel(name = "LTV109", width = 15)
    @ApiModelProperty(value = "LTV109")
    private java.lang.String ltv109;
	/**LTV110*/
	@Excel(name = "LTV110", width = 15)
    @ApiModelProperty(value = "LTV110")
    private java.lang.String ltv110;
	/**LTV111*/
	@Excel(name = "LTV111", width = 15)
    @ApiModelProperty(value = "LTV111")
    private java.lang.String ltv111;
	/**LTV112*/
	@Excel(name = "LTV112", width = 15)
    @ApiModelProperty(value = "LTV112")
    private java.lang.String ltv112;
	/**LTV113*/
	@Excel(name = "LTV113", width = 15)
    @ApiModelProperty(value = "LTV113")
    private java.lang.String ltv113;
	/**LTV114*/
	@Excel(name = "LTV114", width = 15)
    @ApiModelProperty(value = "LTV114")
    private java.lang.String ltv114;
	/**LTV115*/
	@Excel(name = "LTV115", width = 15)
    @ApiModelProperty(value = "LTV115")
    private java.lang.String ltv115;
	/**LTV116*/
	@Excel(name = "LTV116", width = 15)
    @ApiModelProperty(value = "LTV116")
    private java.lang.String ltv116;
	/**LTV117*/
	@Excel(name = "LTV117", width = 15)
    @ApiModelProperty(value = "LTV117")
    private java.lang.String ltv117;
	/**LTV118*/
	@Excel(name = "LTV118", width = 15)
    @ApiModelProperty(value = "LTV118")
    private java.lang.String ltv118;
	/**LTV119*/
	@Excel(name = "LTV119", width = 15)
    @ApiModelProperty(value = "LTV119")
    private java.lang.String ltv119;
	/**LTV120*/
	@Excel(name = "LTV120", width = 15)
    @ApiModelProperty(value = "LTV120")
    private java.lang.String ltv120;
	/**LTV121*/
	@Excel(name = "LTV121", width = 15)
    @ApiModelProperty(value = "LTV121")
    private java.lang.String ltv121;
	/**LTV122*/
	@Excel(name = "LTV122", width = 15)
    @ApiModelProperty(value = "LTV122")
    private java.lang.String ltv122;
	/**LTV123*/
	@Excel(name = "LTV123", width = 15)
    @ApiModelProperty(value = "LTV123")
    private java.lang.String ltv123;
	/**LTV124*/
	@Excel(name = "LTV124", width = 15)
    @ApiModelProperty(value = "LTV124")
    private java.lang.String ltv124;
	/**LTV125*/
	@Excel(name = "LTV125", width = 15)
    @ApiModelProperty(value = "LTV125")
    private java.lang.String ltv125;
	/**LTV126*/
	@Excel(name = "LTV126", width = 15)
    @ApiModelProperty(value = "LTV126")
    private java.lang.String ltv126;
	/**LTV127*/
	@Excel(name = "LTV127", width = 15)
    @ApiModelProperty(value = "LTV127")
    private java.lang.String ltv127;
	/**LTV128*/
	@Excel(name = "LTV128", width = 15)
    @ApiModelProperty(value = "LTV128")
    private java.lang.String ltv128;
	/**LTV129*/
	@Excel(name = "LTV129", width = 15)
    @ApiModelProperty(value = "LTV129")
    private java.lang.String ltv129;
	/**LTV130*/
	@Excel(name = "LTV130", width = 15)
    @ApiModelProperty(value = "LTV130")
    private java.lang.String ltv130;
	/**LTV131*/
	@Excel(name = "LTV131", width = 15)
    @ApiModelProperty(value = "LTV131")
    private java.lang.String ltv131;
	/**LTV132*/
	@Excel(name = "LTV132", width = 15)
    @ApiModelProperty(value = "LTV132")
    private java.lang.String ltv132;
	/**LTV133*/
	@Excel(name = "LTV133", width = 15)
    @ApiModelProperty(value = "LTV133")
    private java.lang.String ltv133;
	/**LTV134*/
	@Excel(name = "LTV134", width = 15)
    @ApiModelProperty(value = "LTV134")
    private java.lang.String ltv134;
	/**LTV135*/
	@Excel(name = "LTV135", width = 15)
    @ApiModelProperty(value = "LTV135")
    private java.lang.String ltv135;
	/**LTV136*/
	@Excel(name = "LTV136", width = 15)
    @ApiModelProperty(value = "LTV136")
    private java.lang.String ltv136;
	/**LTV137*/
	@Excel(name = "LTV137", width = 15)
    @ApiModelProperty(value = "LTV137")
    private java.math.BigDecimal ltv137;
	/**LTV138*/
	@Excel(name = "LTV138", width = 15)
    @ApiModelProperty(value = "LTV138")
    private java.math.BigDecimal ltv138;
	/**LTV139*/
	@Excel(name = "LTV139", width = 15)
    @ApiModelProperty(value = "LTV139")
    private java.math.BigDecimal ltv139;
	/**LTV140*/
	@Excel(name = "LTV140", width = 15)
    @ApiModelProperty(value = "LTV140")
    private java.math.BigDecimal ltv140;
	/**LTV141*/
	@Excel(name = "LTV141", width = 15)
    @ApiModelProperty(value = "LTV141")
    private java.math.BigDecimal ltv141;
	/**LTV142*/
	@Excel(name = "LTV142", width = 15)
    @ApiModelProperty(value = "LTV142")
    private java.math.BigDecimal ltv142;
	/**LTV143*/
	@Excel(name = "LTV143", width = 15)
    @ApiModelProperty(value = "LTV143")
    private java.math.BigDecimal ltv143;
	/**LTV144*/
	@Excel(name = "LTV144", width = 15)
    @ApiModelProperty(value = "LTV144")
    private java.math.BigDecimal ltv144;
	/**LTV145*/
	@Excel(name = "LTV145", width = 15)
    @ApiModelProperty(value = "LTV145")
    private java.math.BigDecimal ltv145;
	/**LTV146*/
	@Excel(name = "LTV146", width = 15)
    @ApiModelProperty(value = "LTV146")
    private java.math.BigDecimal ltv146;
	/**LTV147*/
	@Excel(name = "LTV147", width = 15)
    @ApiModelProperty(value = "LTV147")
    private java.math.BigDecimal ltv147;
	/**LTV148*/
	@Excel(name = "LTV148", width = 15)
    @ApiModelProperty(value = "LTV148")
    private java.math.BigDecimal ltv148;
	/**LTV149*/
	@Excel(name = "LTV149", width = 15)
    @ApiModelProperty(value = "LTV149")
    private java.math.BigDecimal ltv149;
	/**TV150*/
	@Excel(name = "TV150", width = 15)
    @ApiModelProperty(value = "TV150")
    private java.math.BigDecimal ltv150;
}
