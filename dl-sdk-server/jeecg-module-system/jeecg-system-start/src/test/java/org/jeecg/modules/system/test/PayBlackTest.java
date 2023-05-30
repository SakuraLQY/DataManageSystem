package org.jeecg.modules.system.test;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.modules.users.service.IOpPayBlackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @Author lili
 * @Description 恶意支付黑名单
 * @Date 10:58 2022/12/22
 **/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = JeecgSystemApplication.class)
public class PayBlackTest {

    @Resource
    private IOpPayBlackService opPayBlackService;

    @Test
    public void test1(){
        //测试子游戏中userId
        Boolean flag = opPayBlackService.checkUser(472,17,23,"111","222");
        System.out.println("11-->"+flag);
        //测试子游戏中ip
        Boolean flag2 = opPayBlackService.checkUser(47,17,23,"12.1.1.1","222");
        System.out.println("12-->"+flag2);
        //测试子游戏中device
        Boolean flag3 = opPayBlackService.checkUser(47,17,23,"12.1.1.00","12.1.1.0.112");
        System.out.println("13-->"+flag3);
        //测试游戏中userId
        Boolean flag4 = opPayBlackService.checkUser(47,17,23,"12.1.1.00","12.1.1.0.112");
        System.out.println("14-->"+flag4);
        //测试不存在
        Boolean flag5 = opPayBlackService.checkUser(47,17,23,"00","00");
        System.out.println("15-->"+flag5);


    }

}
