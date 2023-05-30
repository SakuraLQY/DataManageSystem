package org.jeecg.modules.game.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.util.filter.FileTypeFilter;
import org.jeecg.common.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author lili
 * @Description 测试打包素材
 * @Date 2023-1-10
 **/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class PackMaterialTest {

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    /**
     * @Author lili
     * @Description 图片文件基础信息
     * @Date 15:06 2023/1/11
     **/
    @Test
    public void test1() throws IOException {
        String filePath = upLoadPath + SymbolConstant.SINGLE_SLASH + "temp/4365c0b5511ee97b657cd1e69bd7db81_1673317761643.png";
        File file = new File(filePath);
        Set<String> set = new HashSet<>();
        set.add(".png");
        Boolean flag = FileUtil.checkFile(file, set);
        System.out.println("filePath-->" + filePath);
        System.out.println(flag);
        System.out.println("size-->"+file.length());
        // 图片对象
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));
        // 宽度
        int width = bufferedImage.getWidth();
        // 高度
        int height = bufferedImage.getHeight();
        System.out.println("width-->"+width);
        System.out.println("height-->"+height);
        String name = file.getName().substring(0,file.getName().indexOf("."));
        System.out.println("fileName-->"+name);

    }


    @Test
    public void test2() throws Exception {
        File file = new File("D:\\opt\\upFiles\\temp\\4365c0b5511ee97b657cd1e69bd7db81_1673335369295.png");
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
        String suffix = FileTypeFilter.getFileType(multipartFile);
        System.out.println(suffix);
    }
}
