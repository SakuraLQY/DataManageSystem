package org.jeecg.modules.game.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.PackUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PackageTest.class)
@Slf4j
public class PackageTest {

    /**
     * @author chenyw
     * @description 打包测试
     * @date 10:31 2023/1/19/019
     **/
    @Test
    public void testPythonPack() {
        String packToolPath = "D:\\workspace\\remote\\dl-sdk\\dl-sdk-server\\packtool\\script";
        String usedApkPath = "D:\\pack\\1.apk";
        String newApkPath = "D:\\pack\\2.apk";
        String iconPath = "D:\\pack\\icon.png";
        String gameName = "航海大王";
        String gamePackage = "com.hanghaidawang.test";
        String workspaceName = "test";
        String resPathList = "";
        String versionName = "1.0";
        String versionCode = "1";
        Boolean res = PackUtil.pythonPackage(packToolPath, usedApkPath, newApkPath, iconPath,
            gameName, gamePackage, workspaceName, resPathList, versionName, versionCode);
        log.info("python打包结果:{}", res);
    }

    @Test
    public void testComment() {
        boolean result = PackUtil.setApkComment("d:/pack/2.apk", "test");
        log.info("文件备注写入结果:{}", result);
    }

}
