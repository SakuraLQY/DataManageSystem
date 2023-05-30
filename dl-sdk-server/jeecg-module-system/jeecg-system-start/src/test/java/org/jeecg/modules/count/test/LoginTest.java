package org.jeecg.modules.count.test;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.modules.count.entity.LgLogin;
import org.jeecg.modules.count.service.ILgLoginService;
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
public class LoginTest {

    @Autowired
    private ILgLoginService loginService;

    /**
     * @author chenyw
     * @description 测试添加归因表
     * @date 17:59 2023/3/31/031
     **/
    @Test
    public void testSave() {
        // 测试分表保存
        LgLogin lgLogin = new LgLogin();
        lgLogin.setDealId(0);
        lgLogin.setUserId(1);
        lgLogin.setGameId(3);
        lgLogin.setSubGameId(22);
        lgLogin.setPkgId(9);
        lgLogin.setChannelId(1);
        lgLogin.setChannelTypeId(2);
        lgLogin.setChannelSubAccountId(3);
        lgLogin.setUniqueId("1aasd");
        lgLogin.setLoginTime(new Date());
        lgLogin.setRegisterTime(new Date());
        lgLogin.setLoginIp("127.0.0.1");
        loginService.save(lgLogin);
    }

}
