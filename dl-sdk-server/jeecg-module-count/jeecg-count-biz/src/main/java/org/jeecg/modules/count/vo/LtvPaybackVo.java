package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
    /**
     * @description: 数据库查询出来的数据
     * @author: chenglin
     * @date: 2023年05月15日 16:49
     */
    @Data
    @ApiModel(value="传给前端对象", description="传给前端对象")
    public class LtvPaybackVo implements Serializable {
        private static final long serialVersionUID = 1L;

        /**日期*/
        @ApiModelProperty(value = "日期")
        private String ltvDate;
        /**游戏*/
        @ApiModelProperty(value = "游戏")
        private String gameName;
        /**渠道*/
        @ApiModelProperty(value = "渠道")
        private String channelName;
        /**广告*/
        @ApiModelProperty(value = "广告")
        private String dealName;
        /**新增注册数*/
        @Excel(name = "新增注册数", width = 15)
        @ApiModelProperty(value = "新增注册数")
        private Integer regCount;
        /**LTV1*/
        @Excel(name = "LTV1", width = 15)
        @ApiModelProperty(value = "LTV1")
        private BigDecimal ltv1;
        /**LTV2*/
        @Excel(name = "LTV2", width = 15)
        @ApiModelProperty(value = "LTV2")
        private BigDecimal ltv2;
        /**LTV3*/
        @Excel(name = "LTV3", width = 15)
        @ApiModelProperty(value = "LTV3")
        private BigDecimal ltv3;
        /**LTV4*/
        @Excel(name = "LTV4", width = 15)
        @ApiModelProperty(value = "LTV4")
        private BigDecimal ltv4;
        /**LTV5*/
        @Excel(name = "LTV5", width = 15)
        @ApiModelProperty(value = "LTV5")
        private BigDecimal ltv5;
        /**LTV6*/
        @Excel(name = "LTV6", width = 15)
        @ApiModelProperty(value = "LTV6")
        private BigDecimal ltv6;
        /**LTV7*/
        @Excel(name = "LTV7", width = 15)
        @ApiModelProperty(value = "LTV7")
        private BigDecimal ltv7;
        /**LTV8*/
        @Excel(name = "LTV8", width = 15)
        @ApiModelProperty(value = "LTV8")
        private BigDecimal ltv8;
        /**LTV9*/
        @Excel(name = "LTV9", width = 15)
        @ApiModelProperty(value = "LTV9")
        private BigDecimal ltv9;
        /**LTV10*/
        @Excel(name = "LTV10", width = 15)
        @ApiModelProperty(value = "LTV10")
        private BigDecimal ltv10;
        /**LTV11*/
        @Excel(name = "LTV11", width = 15)
        @ApiModelProperty(value = "LTV11")
        private BigDecimal ltv11;
        /**LTV12*/
        @Excel(name = "LTV12", width = 15)
        @ApiModelProperty(value = "LTV12")
        private BigDecimal ltv12;
        /**LTV13*/
        @Excel(name = "LTV13", width = 15)
        @ApiModelProperty(value = "LTV13")
        private BigDecimal ltv13;
        /**LTV14*/
        @Excel(name = "LTV14", width = 15)
        @ApiModelProperty(value = "LTV14")
        private BigDecimal ltv14;
        /**LTV15*/
        @Excel(name = "LTV15", width = 15)
        @ApiModelProperty(value = "LTV15")
        private BigDecimal ltv15;
        /**LTV16*/
        @Excel(name = "LTV16", width = 15)
        @ApiModelProperty(value = "LTV16")
        private BigDecimal ltv16;
        /**LTV17*/
        @Excel(name = "LTV17", width = 15)
        @ApiModelProperty(value = "LTV17")
        private BigDecimal ltv17;
        /**LTV18*/
        @Excel(name = "LTV18", width = 15)
        @ApiModelProperty(value = "LTV18")
        private BigDecimal ltv18;
        /**LTV19*/
        @Excel(name = "LTV19", width = 15)
        @ApiModelProperty(value = "LTV19")
        private BigDecimal ltv19;
        /**LTV20*/
        @Excel(name = "LTV20", width = 15)
        @ApiModelProperty(value = "LTV20")
        private BigDecimal ltv20;
        /**LTV21*/
        @Excel(name = "LTV21", width = 15)
        @ApiModelProperty(value = "LTV21")
        private BigDecimal ltv21;
        /**LTV22*/
        @Excel(name = "LTV22", width = 15)
        @ApiModelProperty(value = "LTV22")
        private BigDecimal ltv22;
        /**LTV23*/
        @Excel(name = "LTV23", width = 15)
        @ApiModelProperty(value = "LTV23")
        private BigDecimal ltv23;
        /**LTV24*/
        @Excel(name = "LTV24", width = 15)
        @ApiModelProperty(value = "LTV24")
        private BigDecimal ltv24;
        /**LTV26*/
        @Excel(name = "LTV25", width = 15)
        @ApiModelProperty(value = "LTV25")
        private BigDecimal ltv25;
        /**LTV26*/
        @Excel(name = "LTV26", width = 15)
        @ApiModelProperty(value = "LTV26")
        private BigDecimal ltv26;
        /**LTV27*/
        @Excel(name = "LTV27", width = 15)
        @ApiModelProperty(value = "LTV27")
        private BigDecimal ltv27;
        /**LTV28*/
        @Excel(name = "LTV28", width = 15)
        @ApiModelProperty(value = "LTV28")
        private BigDecimal ltv28;
        /**LTV29*/
        @Excel(name = "LTV29", width = 15)
        @ApiModelProperty(value = "LTV29")
        private BigDecimal ltv29;
        /**LTV30*/
        @Excel(name = "LTV30", width = 15)
        @ApiModelProperty(value = "LTV30")
        private BigDecimal ltv30;
        /**LTV31*/
        @Excel(name = "LTV31", width = 15)
        @ApiModelProperty(value = "LTV31")
        private BigDecimal ltv31;
        /**LTV32*/
        @Excel(name = "LTV32", width = 15)
        @ApiModelProperty(value = "LTV32")
        private BigDecimal ltv32;
        /**LTV33*/
        @Excel(name = "LTV33", width = 15)
        @ApiModelProperty(value = "LTV33")
        private BigDecimal ltv33;
        /**LTV34*/
        @Excel(name = "LTV34", width = 15)
        @ApiModelProperty(value = "LTV34")
        private BigDecimal ltv34;
        /**LTV35*/
        @Excel(name = "LTV35", width = 15)
        @ApiModelProperty(value = "LTV35")
        private BigDecimal ltv35;
        /**LTV36*/
        @Excel(name = "LTV36", width = 15)
        @ApiModelProperty(value = "LTV36")
        private BigDecimal ltv36;
        /**LTV37*/
        @Excel(name = "LTV37", width = 15)
        @ApiModelProperty(value = "LTV37")
        private BigDecimal ltv37;
        /**LTV38*/
        @Excel(name = "LTV38", width = 15)
        @ApiModelProperty(value = "LTV38")
        private BigDecimal ltv38;
        /**LTV39*/
        @Excel(name = "LTV39", width = 15)
        @ApiModelProperty(value = "LTV39")
        private BigDecimal ltv39;
        /**LTV40*/
        @Excel(name = "LTV40", width = 15)
        @ApiModelProperty(value = "LTV40")
        private BigDecimal ltv40;
        /**LTV41*/
        @Excel(name = "LTV41", width = 15)
        @ApiModelProperty(value = "LTV41")
        private BigDecimal ltv41;
        /**LTV42*/
        @Excel(name = "LTV42", width = 15)
        @ApiModelProperty(value = "LTV42")
        private BigDecimal ltv42;
        /**LTV43*/
        @Excel(name = "LTV43", width = 15)
        @ApiModelProperty(value = "LTV43")
        private BigDecimal ltv43;
        /**LTV44*/
        @Excel(name = "LTV44", width = 15)
        @ApiModelProperty(value = "LTV44")
        private BigDecimal ltv44;
        /**LTV45*/
        @Excel(name = "LTV45", width = 15)
        @ApiModelProperty(value = "LTV45")
        private BigDecimal ltv45;
        /**LTV46*/
        @Excel(name = "LTV46", width = 15)
        @ApiModelProperty(value = "LTV46")
        private BigDecimal ltv46;
        /**LTV47*/
        @Excel(name = "LTV47", width = 15)
        @ApiModelProperty(value = "LTV47")
        private BigDecimal ltv47;
        /**LTV48*/
        @Excel(name = "LTV48", width = 15)
        @ApiModelProperty(value = "LTV48")
        private BigDecimal ltv48;
        /**LTV49*/
        @Excel(name = "LTV49", width = 15)
        @ApiModelProperty(value = "LTV49")
        private BigDecimal ltv49;
        /**LTV50*/
        @Excel(name = "LTV50", width = 15)
        @ApiModelProperty(value = "LTV50")
        private BigDecimal ltv50;
        /**LTV51*/
        @Excel(name = "LTV51", width = 15)
        @ApiModelProperty(value = "LTV51")
        private BigDecimal ltv51;
        /**LTV52*/
        @Excel(name = "LTV52", width = 15)
        @ApiModelProperty(value = "LTV52")
        private BigDecimal ltv52;
        /**LTV53*/
        @Excel(name = "LTV53", width = 15)
        @ApiModelProperty(value = "LTV53")
        private BigDecimal ltv53;
        /**LTV54*/
        @Excel(name = "LTV54", width = 15)
        @ApiModelProperty(value = "LTV54")
        private BigDecimal ltv54;
        /**LTV55*/
        @Excel(name = "LTV55", width = 15)
        @ApiModelProperty(value = "LTV55")
        private BigDecimal ltv55;
        /**LTV56*/
        @Excel(name = "LTV56", width = 15)
        @ApiModelProperty(value = "LTV56")
        private BigDecimal ltv56;
        /**LTV57*/
        @Excel(name = "LTV57", width = 15)
        @ApiModelProperty(value = "LTV57")
        private BigDecimal ltv57;
        /**LTV58*/
        @Excel(name = "LTV58", width = 15)
        @ApiModelProperty(value = "LTV58")
        private BigDecimal ltv58;
        /**LTV59*/
        @Excel(name = "LTV59", width = 15)
        @ApiModelProperty(value = "LTV59")
        private BigDecimal ltv59;
        /**LTV60*/
        @Excel(name = "LTV60", width = 15)
        @ApiModelProperty(value = "LTV60")
        private BigDecimal ltv60;
        /**LTV61*/
        @Excel(name = "LTV61", width = 15)
        @ApiModelProperty(value = "LTV61")
        private BigDecimal ltv61;
        /**LTV62*/
        @Excel(name = "LTV62", width = 15)
        @ApiModelProperty(value = "LTV62")
        private BigDecimal ltv62;
        /**LTV63*/
        @Excel(name = "LTV63", width = 15)
        @ApiModelProperty(value = "LTV63")
        private BigDecimal ltv63;
        /**LTV64*/
        @Excel(name = "LTV64", width = 15)
        @ApiModelProperty(value = "LTV64")
        private BigDecimal ltv64;
        /**LTV65*/
        @Excel(name = "LTV65", width = 15)
        @ApiModelProperty(value = "LTV65")
        private BigDecimal ltv65;
        /**LTV66*/
        @Excel(name = "LTV66", width = 15)
        @ApiModelProperty(value = "LTV66")
        private BigDecimal ltv66;
        /**LTV67*/
        @Excel(name = "LTV67", width = 15)
        @ApiModelProperty(value = "LTV67")
        private BigDecimal ltv67;
        /**LTV68*/
        @Excel(name = "LTV68", width = 15)
        @ApiModelProperty(value = "LTV68")
        private BigDecimal ltv68;
        /**LTV69*/
        @Excel(name = "LTV69", width = 15)
        @ApiModelProperty(value = "LTV69")
        private BigDecimal ltv69;
        /**LTV70*/
        @Excel(name = "LTV70", width = 15)
        @ApiModelProperty(value = "LTV70")
        private BigDecimal ltv70;
        /**LTV71*/
        @Excel(name = "LTV71", width = 15)
        @ApiModelProperty(value = "LTV71")
        private BigDecimal ltv71;
        /**LTV72*/
        @Excel(name = "LTV72", width = 15)
        @ApiModelProperty(value = "LTV72")
        private BigDecimal ltv72;
        /**LTV73*/
        @Excel(name = "LTV73", width = 15)
        @ApiModelProperty(value = "LTV73")
        private BigDecimal ltv73;
        /**LTV74*/
        @Excel(name = "LTV74", width = 15)
        @ApiModelProperty(value = "LTV74")
        private BigDecimal ltv74;
        /**LTV75*/
        @Excel(name = "LTV75", width = 15)
        @ApiModelProperty(value = "LTV75")
        private BigDecimal ltv75;
        /**LTV76*/
        @Excel(name = "LTV76", width = 15)
        @ApiModelProperty(value = "LTV76")
        private BigDecimal ltv76;
        /**LTV77*/
        @Excel(name = "LTV77", width = 15)
        @ApiModelProperty(value = "LTV77")
        private BigDecimal ltv77;
        /**LTV78*/
        @Excel(name = "LTV78", width = 15)
        @ApiModelProperty(value = "LTV78")
        private BigDecimal ltv78;
        /**LTV79*/
        @Excel(name = "LTV79", width = 15)
        @ApiModelProperty(value = "LTV79")
        private BigDecimal ltv79;
        /**LTV80*/
        @Excel(name = "LTV80", width = 15)
        @ApiModelProperty(value = "LTV80")
        private BigDecimal ltv80;
        /**LTV81*/
        @Excel(name = "LTV81", width = 15)
        @ApiModelProperty(value = "LTV81")
        private BigDecimal ltv81;
        /**LTV82*/
        @Excel(name = "LTV82", width = 15)
        @ApiModelProperty(value = "LTV82")
        private BigDecimal ltv82;
        /**LTV83*/
        @Excel(name = "LTV83", width = 15)
        @ApiModelProperty(value = "LTV83")
        private BigDecimal ltv83;
        /**LTV84*/
        @Excel(name = "LTV84", width = 15)
        @ApiModelProperty(value = "LTV84")
        private BigDecimal ltv84;
        /**LTV85*/
        @Excel(name = "LTV85", width = 15)
        @ApiModelProperty(value = "LTV85")
        private BigDecimal ltv85;
        /**LTV86*/
        @Excel(name = "LTV86", width = 15)
        @ApiModelProperty(value = "LTV86")
        private BigDecimal ltv86;
        /**LTV87*/
        @Excel(name = "LTV87", width = 15)
        @ApiModelProperty(value = "LTV87")
        private BigDecimal ltv87;
        /**LTV88*/
        @Excel(name = "LTV88", width = 15)
        @ApiModelProperty(value = "LTV88")
        private BigDecimal ltv88;
        /**LTV89*/
        @Excel(name = "LTV89", width = 15)
        @ApiModelProperty(value = "LTV89")
        private BigDecimal ltv89;
        /**LTV90*/
        @Excel(name = "LTV90", width = 15)
        @ApiModelProperty(value = "LTV90")
        private BigDecimal ltv90;
        /**LTV91*/
        @Excel(name = "LTV91", width = 15)
        @ApiModelProperty(value = "LTV91")
        private BigDecimal ltv91;
        /**LTV92*/
        @Excel(name = "LTV92", width = 15)
        @ApiModelProperty(value = "LTV92")
        private BigDecimal ltv92;
        /**LTV93*/
        @Excel(name = "LTV93", width = 15)
        @ApiModelProperty(value = "LTV93")
        private BigDecimal ltv93;
        /**LTV94*/
        @Excel(name = "LTV94", width = 15)
        @ApiModelProperty(value = "LTV94")
        private BigDecimal ltv94;
        /**LTV95*/
        @Excel(name = "LTV95", width = 15)
        @ApiModelProperty(value = "LTV95")
        private BigDecimal ltv95;
        /**LTV96*/
        @Excel(name = "LTV96", width = 15)
        @ApiModelProperty(value = "LTV96")
        private BigDecimal ltv96;
        /**LTV97*/
        @Excel(name = "LTV97", width = 15)
        @ApiModelProperty(value = "LTV97")
        private BigDecimal ltv97;
        /**LTV98*/
        @Excel(name = "LTV98", width = 15)
        @ApiModelProperty(value = "LTV98")
        private BigDecimal ltv98;
        /**LTV99*/
        @Excel(name = "LTV99", width = 15)
        @ApiModelProperty(value = "LTV99")
        private BigDecimal ltv99;
        /**LTV100*/
        @Excel(name = "LTV100", width = 15)
        @ApiModelProperty(value = "LTV100")
        private BigDecimal ltv100;
        /**LTV101*/
        @Excel(name = "LTV101", width = 15)
        @ApiModelProperty(value = "LTV101")
        private BigDecimal ltv101;
        /**LTV102*/
        @Excel(name = "LTV102", width = 15)
        @ApiModelProperty(value = "LTV102")
        private BigDecimal ltv102;
        /**LTV103*/
        @Excel(name = "LTV103", width = 15)
        @ApiModelProperty(value = "LTV103")
        private BigDecimal ltv103;
        /**LTV104*/
        @Excel(name = "LTV104", width = 15)
        @ApiModelProperty(value = "LTV104")
        private BigDecimal ltv104;
        /**LTV105*/
        @Excel(name = "LTV105", width = 15)
        @ApiModelProperty(value = "LTV105")
        private BigDecimal ltv105;
        /**LTV106*/
        @Excel(name = "LTV106", width = 15)
        @ApiModelProperty(value = "LTV106")
        private BigDecimal ltv106;
        /**LTV107*/
        @Excel(name = "LTV107", width = 15)
        @ApiModelProperty(value = "LTV107")
        private BigDecimal ltv107;
        /**LTV108*/
        @Excel(name = "LTV108", width = 15)
        @ApiModelProperty(value = "LTV108")
        private BigDecimal ltv108;
        /**LTV109*/
        @Excel(name = "LTV109", width = 15)
        @ApiModelProperty(value = "LTV109")
        private BigDecimal ltv109;
        /**LTV110*/
        @Excel(name = "LTV110", width = 15)
        @ApiModelProperty(value = "LTV110")
        private BigDecimal ltv110;
        /**LTV111*/
        @Excel(name = "LTV111", width = 15)
        @ApiModelProperty(value = "LTV111")
        private BigDecimal ltv111;
        /**LTV112*/
        @Excel(name = "LTV112", width = 15)
        @ApiModelProperty(value = "LTV112")
        private BigDecimal ltv112;
        /**LTV113*/
        @Excel(name = "LTV113", width = 15)
        @ApiModelProperty(value = "LTV113")
        private BigDecimal ltv113;
        /**LTV114*/
        @Excel(name = "LTV114", width = 15)
        @ApiModelProperty(value = "LTV114")
        private BigDecimal ltv114;
        /**LTV115*/
        @Excel(name = "LTV115", width = 15)
        @ApiModelProperty(value = "LTV115")
        private BigDecimal ltv115;
        /**LTV116*/
        @Excel(name = "LTV116", width = 15)
        @ApiModelProperty(value = "LTV116")
        private BigDecimal ltv116;
        /**LTV117*/
        @Excel(name = "LTV117", width = 15)
        @ApiModelProperty(value = "LTV117")
        private BigDecimal ltv117;
        /**LTV118*/
        @Excel(name = "LTV118", width = 15)
        @ApiModelProperty(value = "LTV118")
        private BigDecimal ltv118;
        /**LTV119*/
        @Excel(name = "LTV119", width = 15)
        @ApiModelProperty(value = "LTV119")
        private BigDecimal ltv119;
        /**LTV120*/
        @Excel(name = "LTV120", width = 15)
        @ApiModelProperty(value = "LTV120")
        private BigDecimal ltv120;
        /**LTV121*/
        @Excel(name = "LTV121", width = 15)
        @ApiModelProperty(value = "LTV121")
        private BigDecimal ltv121;
        /**LTV122*/
        @Excel(name = "LTV122", width = 15)
        @ApiModelProperty(value = "LTV122")
        private BigDecimal ltv122;
        /**LTV123*/
        @Excel(name = "LTV123", width = 15)
        @ApiModelProperty(value = "LTV123")
        private BigDecimal ltv123;
        /**LTV124*/
        @Excel(name = "LTV124", width = 15)
        @ApiModelProperty(value = "LTV124")
        private BigDecimal ltv124;
        /**LTV125*/
        @Excel(name = "LTV125", width = 15)
        @ApiModelProperty(value = "LTV125")
        private BigDecimal ltv125;
        /**LTV126*/
        @Excel(name = "LTV126", width = 15)
        @ApiModelProperty(value = "LTV126")
        private BigDecimal ltv126;
        /**LTV127*/
        @Excel(name = "LTV127", width = 15)
        @ApiModelProperty(value = "LTV127")
        private BigDecimal ltv127;
        /**LTV128*/
        @Excel(name = "LTV128", width = 15)
        @ApiModelProperty(value = "LTV128")
        private BigDecimal ltv128;
        /**LTV129*/
        @Excel(name = "LTV129", width = 15)
        @ApiModelProperty(value = "LTV129")
        private BigDecimal ltv129;
        /**LTV130*/
        @Excel(name = "LTV130", width = 15)
        @ApiModelProperty(value = "LTV130")
        private BigDecimal ltv130;
        /**LTV131*/
        @Excel(name = "LTV131", width = 15)
        @ApiModelProperty(value = "LTV131")
        private BigDecimal ltv131;
        /**LTV132*/
        @Excel(name = "LTV132", width = 15)
        @ApiModelProperty(value = "LTV132")
        private BigDecimal ltv132;
        /**LTV133*/
        @Excel(name = "LTV133", width = 15)
        @ApiModelProperty(value = "LTV133")
        private BigDecimal ltv133;
        /**LTV134*/
        @Excel(name = "LTV134", width = 15)
        @ApiModelProperty(value = "LTV134")
        private BigDecimal ltv134;
        /**LTV135*/
        @Excel(name = "LTV135", width = 15)
        @ApiModelProperty(value = "LTV135")
        private BigDecimal ltv135;
        /**LTV136*/
        @Excel(name = "LTV136", width = 15)
        @ApiModelProperty(value = "LTV136")
        private BigDecimal ltv136;
        /**LTV137*/
        @Excel(name = "LTV137", width = 15)
        @ApiModelProperty(value = "LTV137")
        private BigDecimal ltv137;
        /**LTV138*/
        @Excel(name = "LTV138", width = 15)
        @ApiModelProperty(value = "LTV138")
        private BigDecimal ltv138;
        /**LTV139*/
        @Excel(name = "LTV139", width = 15)
        @ApiModelProperty(value = "LTV139")
        private BigDecimal ltv139;
        /**LTV140*/
        @Excel(name = "LTV140", width = 15)
        @ApiModelProperty(value = "LTV140")
        private BigDecimal ltv140;
        /**LTV141*/
        @Excel(name = "LTV141", width = 15)
        @ApiModelProperty(value = "LTV141")
        private BigDecimal ltv141;
        /**LTV142*/
        @Excel(name = "LTV142", width = 15)
        @ApiModelProperty(value = "LTV142")
        private BigDecimal ltv142;
        /**LTV143*/
        @Excel(name = "LTV143", width = 15)
        @ApiModelProperty(value = "LTV143")
        private BigDecimal ltv143;
        /**LTV144*/
        @Excel(name = "LTV144", width = 15)
        @ApiModelProperty(value = "LTV144")
        private BigDecimal ltv144;
        /**LTV145*/
        @Excel(name = "LTV145", width = 15)
        @ApiModelProperty(value = "LTV145")
        private BigDecimal ltv145;
        /**LTV146*/
        @Excel(name = "LTV146", width = 15)
        @ApiModelProperty(value = "LTV146")
        private BigDecimal ltv146;
        /**LTV147*/
        @Excel(name = "LTV147", width = 15)
        @ApiModelProperty(value = "LTV147")
        private BigDecimal ltv147;
        /**LTV148*/
        @Excel(name = "LTV148", width = 15)
        @ApiModelProperty(value = "LTV148")
        private BigDecimal ltv148;
        /**LTV149*/
        @Excel(name = "LTV149", width = 15)
        @ApiModelProperty(value = "LTV149")
        private BigDecimal ltv149;
        /**TV150*/
        @Excel(name = "TV150", width = 15)
        @ApiModelProperty(value = "TV150")
        private BigDecimal ltv150;
    }

