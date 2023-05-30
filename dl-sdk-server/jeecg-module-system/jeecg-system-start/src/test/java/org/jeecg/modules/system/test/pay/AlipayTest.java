package org.jeecg.modules.system.test.pay;

import org.jeecg.JeecgSystemApplication;
import org.jeecg.modules.pay.bo.AlipayConf;
import org.jeecg.modules.pay.service.IOpAlipayService;
import org.jeecg.modules.pay.service.IOpPayVendorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author lili
 * @Description 支付宝测试
 * @Date 2022-12-24
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class AlipayTest {

    @Autowired
    private IOpAlipayService opAlipayService;

    /**
     * @Author lili
     * @Description 测试调用支付宝接口
     * @Date 17:32 2022/12/24
     **/
//    @Test
//    public void test1() {
//        AlipayConf alipayConf = new AlipayConf();
//        alipayConf.setAppId("2018083161222069");
//        alipayConf.setSellerId("2088721696911804");
//        alipayConf.setSellerPrikey(
//            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDP9jjsE1C/6v7smon9JIRNs3vkBuM/rym0gAKlLOaOVVOUzjaGKh2nS3c/cLaSeTGVTNhmkiDy6j7y8BieoGAI4bccHbC7nTcBhuPtamiCUElTl5U20zng+Ou8+/hcOrmO9MsFVzzO26tRThzK/av7t6X0icRfwIhc+lF+AeVhxA+ft7i1T7WpJgHvFe9nbYPv/1AIshqnpCGN0HFVFDSaN9fvjeuGo4GgR34lrcbkmCZqcUanptmpaa8FI1tlbWQURfPjwp/fviktExJHlMOagkE10jNxbsFRjJ84FKYYufC2/5ayf4QrToXpArKgdZQNhXt8zPjZeQJ7FKP/B9ZVAgMBAAECggEADIV8w+od0JBrrP9ZE57hQHRwHziy8iCv2ChlAGEFuMg2ch1Ady+N/YaztWQp+gPGEoE//l0KEtY72BDQyqxJW/+LMOwwufsrVrIygxcHF+GRqLDV0SlCsOML/qMFPzhh99ORbc7k4Gellw2m+inv8wYJvgGVi1ZiX4bK5VVj5FD38KzXaHggh8T21Bk7Jvp7ewV+dY563M+yTh6k5AalrjCXX/jfu+H4dcGAgvkuIDBSflwTzBRJQU6eRpcKTP+Bg9cEG8/3Bda1BQwJD/nm7z8HCk8ypPKsf8Ryy/N889PWli9etFFD+/6hTtaQJ6bDeYc8zFOi6gZ8mgI3gFWT+QKBgQD1t91603ekx8NDKJtoqpnjCyBhuKScQ8xLegM+SEZ8TemAYYdrdU8N4paEoL82beT2l09arMAHUh0L13BuLInYmY0p/kwDjbSI6UNG8RLHbEUeu6D7wt9YKYCZyofR7Z5atKjB450vAXaEee+hFd718RUBeDU3M7Q3LaVDHCbERwKBgQDYqekHK/Zc6+2rogO48BovARfs8nDbNVsSjbTezDedxkFD7PzJt7DQJFPLr6PixGRiH8dYUANpAlH7+uFzH5mNmQycjyx333wC5GAzZeK4gWl9vFmjXcTngYOdmcbmHlc+r00KzhuIl9VTeK85nuZXgdvjyzbhlqBsSsl1OLRqgwKBgQCZuqDceXT/KOOnHr6sKyAABjQKL2T9RwWHqxxgWQOQPYZ7BYmd11TQURTugUtgGD0Jcfz5s96oNd1qTS1QIJlVZCwHO8v+5ScwCRBIUAb5sWlFIwpCuNbW55BHp4h19H4ALcrvEyKCCB5IH6mmVHKb2GrV8okIslZrtykP5YrzfQKBgQCH1f7DAEz09PWNJYYv7Fh+H7FgXJ9kz47Jkw5SoLY9tI42h930mbXAyQXALd4Rzg8hdcsFEyUQMOQ1lpTCGI2suMevYBQBE5wsNAIcN0RtaLU6n1uTkLZiblCV/lM08tC//CfRHmckvAyCyKcS+Qxw0HmL11wREt6BBmJHm8WBAQKBgCMqVSGFmMZN7/+a/AnyNQvAIZZxjCeccRLNDFyqiQ2Hho9frLBMN+BANtpTjf5LdjDf9/AxMhVQKzc+VSsLu+hKUK+z/HJgqGsAqtuU/QC+C7W3nnGE3/MhgG+XgLJVBZHxmvoU8EKeE1hsWRcEl+4JGN+2DbNrILDKStnKB1bs");
//        alipayConf.setAlipayPubkey(
//            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjXCDEUwadiopwD0+C81N0b7K7142ToGeC6a0/ovlTS4K0M2XkV0ZCt1X6dP23HgM4byBR8uwaJjl7HAdx2N72BGO0AsccWD0u+o+l0/edPXQssXsyZ+MO0QLJSlPKfX4BumbBPXpceuiDTDPyT4vl8W7qzVNaxH7hluE5V3/qi580L3A6Cg3eBxVP2u3psZ77OVbueMYrrgcAGWmRRApoM8Gw4VdOAJZceT7T2p2ksfFrF85nqIjhKWoWYrejubjPPP854ee3QyGYC/7Lkc288OM/t3BKSO1lQ1m8h6GrdwYs7bc6K87+jdVzvcMMDMoYjEC0gj2J4tmN0b4tcEV7wIDAQAB");
//        alipayConf.setOrderId("12141");
//        alipayConf.setMoney(0.01);
//        alipayConf.setCouponPrice(0.0);
//        alipayConf.setSubject("ces");
//        String form = opAlipayService.alipayOpen(alipayConf);
//        System.out.println(form);
//
//    }

}
