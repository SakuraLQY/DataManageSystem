package org.jeecg.modules.game.test;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.util.oConvertUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author lili
 * @Description //
 * @Date $ 2023-1-9$
 **/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = JeecgSystemApplication.class)
public class PackConfigTest {

    /**
     * @Author lili
     * @Description 字符串和Map互转
     * @Date 13:54 2023/1/9
     **/
    @Test
    public void test1() {
        String str = "{\"screen_type\":\"\",\"screen_width\":\"\",\"screen_height\":\"\",\"screen_path\":\"\",\"loading_type\":\"\",\"loading_width\":\"\",\"loading_height\":\"\",\"loading_path\":\"\",\"login_type\":\"\",\"login_width\":\"\",\"login_height\":\"\",\"login_path\":\"\",\"logo_type\":\"\",\"logo_width\":\"\",\"logo_height\":\"\",\"logo_path\":\"\"}";
        Map<String, Object> map = JSONObject.parseObject(str,
            new TypeReference<Map<String, Object>>() {
            });
        String result = JSONUtils.toJSONString(map);
        System.out.println(oConvertUtils.isEmpty(map.get("screen_type")));
        System.out.println(result);
    }


}
