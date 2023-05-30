package org.jeecg.modules.advert.test;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.util.IpUtils;
import org.jeecg.modules.game.service.IOpPkgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author lili
 * @Description 资产测试
 **/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = JeecgSystemApplication.class)
public class AssetsTest {
    @Resource
    private IGameApi gameApi;

    @Test
    public void test1() {
        OpPkgModel opPkgModel = gameApi.getOpPkgById(1);
        System.out.println(opPkgModel.getJrttConf().getAppId());
    }

}
