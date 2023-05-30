package org.jeecg.modules.advert.test;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.advert.dto.JrttVisitDto;
import org.jeecg.modules.advert.service.IJrttVisitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author lili
 * @Description 资产测试
 **/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class VisitTest {

    @Autowired
    private IJrttVisitService jrttVisitService;

    /**
     * @author chenyw
     * @description 测试添加归因表
     * @date 17:59 2023/3/31/031
     **/
    @Test
    public void test1() {
        JrttVisitDto jrttVisitDto = new JrttVisitDto();
        jrttVisitDto.setProjectId(123456l);
        jrttVisitDto.setPromotionId(1234567l);
        jrttVisitDto.setCampaignId(12345678l);
        jrttVisitDto.setAid(123456789l);
        jrttVisitDto.setCid(112321l);
        jrttVisitDto.setIdfa("idfaidfaidfa");
        jrttVisitDto.setAndroidId("androididandroidid");
        jrttVisitDto.setOaid("oaidoaidoaidoaid");
        jrttVisitDto.setImei("imeiimeiimeiimei");
        jrttVisitDto.setMac("macmacmacmac");
        jrttVisitDto.setOs(1);
        jrttVisitDto.setIp("127.0.0.1");
        jrttVisitDto.setTime(1680256731777l);
        jrttVisitDto.setDealId(123);
        jrttVisitService.click(jrttVisitDto);
    }

}
