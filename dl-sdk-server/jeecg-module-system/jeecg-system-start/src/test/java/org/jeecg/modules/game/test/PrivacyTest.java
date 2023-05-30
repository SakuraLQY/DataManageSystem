package org.jeecg.modules.game.test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cdn.model.v20141111.PushObjectCacheRequest;
import com.aliyuncs.cdn.model.v20141111.PushObjectCacheResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import io.swagger.models.auth.In;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.filter.FileTypeFilter;
import org.jeecg.common.util.oss.OssBootUtil;
import org.jeecg.modules.game.entity.OpPkg;
import org.jeecg.modules.game.mapper.OpPkgMapper;
import org.jeecg.modules.game.service.IOpPkgService;
import org.jeecg.modules.game.service.IOpPrivacyPolicyService;
import org.jeecg.common.util.FileUtil;
import org.jeecg.modules.game.service.impl.OpPkgServiceImpl;
import org.jeecg.modules.game.vo.OpPkgVo;
import org.jeecg.modules.game.vo.SubAndOpPackGameVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author lili
 * @Description 测试隐私政策管理
 * @Date $ 2022-12-29$
 **/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class PrivacyTest {

    @Resource
    private IOpPrivacyPolicyService opPrivacyPolicyService;

    @Autowired
    private OpPkgMapper opPkgMapper;

    @Autowired
    private IOpPkgService opPkgService;

    @Test
    public void test() {
        List<OpPkgVo> list = opPkgMapper.getList();
        Map<Integer, Map<Integer, List<OpPkgVo>>> map = list.stream()
            .collect(Collectors.groupingBy(OpPkgVo::getGameId, Collectors.groupingBy(OpPkgVo::getSubGameId)));
        List<SubAndOpPackGameVo> optionList = new ArrayList<>();
        for (Integer key : map.keySet()) {
            Map<Integer, List<OpPkgVo>> listOne = map.get(key);
            SubAndOpPackGameVo subAndOpPackGameVo = new SubAndOpPackGameVo();
            List<SubAndOpPackGameVo> subList = new ArrayList<>();
            for (Integer key2 : listOne.keySet()) {
                List<OpPkgVo> listTwo = listOne.get(key2);
                subAndOpPackGameVo.setTitle(listTwo.get(0).getGameName());
                subAndOpPackGameVo.setValue("game" + key);
                SubAndOpPackGameVo subAndOpPackGameVo2 = new SubAndOpPackGameVo();
                List<SubAndOpPackGameVo> pkgList = new ArrayList<>();
                for (OpPkgVo opPkgVo : listTwo) {
                    subAndOpPackGameVo2.setTitle(opPkgVo.getSubGameName());
                    subAndOpPackGameVo2.setValue("subGame" + key2);
                    SubAndOpPackGameVo subAndOpPackGameVo3 = new SubAndOpPackGameVo();
                    subAndOpPackGameVo3.setTitle(opPkgVo.getNickName());
                    subAndOpPackGameVo3.setValue(String.valueOf(opPkgVo.getId()));
                    subAndOpPackGameVo3.setChildren(new ArrayList<>());
                    pkgList.add(subAndOpPackGameVo3);
                }
                subAndOpPackGameVo2.setChildren(pkgList);
                subList.add(subAndOpPackGameVo2);
            }
            subAndOpPackGameVo.setChildren(subList);
            optionList.add(subAndOpPackGameVo);
        }
        System.out.println("optionList-->"+optionList);

    }

    /**
     * @Author lili
     * @Description 获得html文件文本
     * @Date 10:08 2023/1/5
     **/
    public void test0() {
        File file = new File("E:\\新建 XLS 工作表.xls");
        // 获取HTML文件流
        StringBuffer htmlSb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), "utf-8"));
            while (br.ready()) {
                htmlSb.append(br.readLine());
            }
            br.close();
            // 删除临时文件
            //file.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // HTML文件字符串
        String htmlStr = htmlSb.toString();
        System.out.println(htmlStr);
    }

    /**
     * @Author lili
     * @Description 下载文件到指定目录
     * @Date 15:23 2022/12/29
     **/
    @Test
    public void test1() throws IOException {

//        File file = new File("E:\\privacy2.html");
        File file = new File("E:\\新建 XLS 工作表.xls");
        MultipartFile cMultiFile = new MockMultipartFile("file", file.getName(), null,
            new FileInputStream(file));
        // 获取文件原本的名字
        String originName = cMultiFile.getOriginalFilename();
        // 判断文件是否是pdf文件
        Set<String> set = new HashSet<>();
        set.add(".html");
        // 取出文件的后缀
        int count = 0;
        for (int i = 0; i < originName.length(); i++) {
            if (originName.charAt(i) == '.') {
                count = i;
                break;
            }
        }
        String msg;
        String endName = originName.substring(count); //取出文件类型
        if (!set.contains(endName)) {
//            return new String("上传的文件类型错误,只能上传pdf,doc,docx类型的文件");
            msg = "上传的文件类型错误,只能上传html类型的文件";
            System.out.println(msg);
        }
        String path = "E:\\uploadDir\\";
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String msg2;
        // 获取文件名(包括后缀)
        String fileName = cMultiFile.getOriginalFilename();
        try (FileOutputStream fos = new FileOutputStream(path + fileName)) {
            fos.write(cMultiFile.getBytes());
            msg2 = "文件上传成功";
        } catch (Exception e) {
            log.error("file upload failed, fileName = {}", cMultiFile.getOriginalFilename(), e);
            msg2 = "文件上传失败";
        }
        System.out.println(msg2);

    }

    private static String[] forbidType = {"html"};

    /**
     * @Author lili
     * @Description 上传到oss
     * @Date 10:09 2023/1/5
     **/
    @Test
    public void test2() throws Exception {
        File file = new File("E:\\privacy2.html");
        MultipartFile cMultiFile = new MockMultipartFile("file", file.getName(), null,
            new FileInputStream(file));
        FileTypeFilter.fileTypeFilter(cMultiFile);
        String name = cMultiFile.getOriginalFilename();
        name = CommonUtils.getFileName(name);
        System.out.println(name);

        String url = opPrivacyPolicyService.uploadToOSS(cMultiFile, "subGame", null, forbidType);
        System.out.println(url);
    }

    /**
     * @Author lili
     * @Description 将文本内容写入文件
     * @Date 10:09 2023/1/5
     **/
    @Test
    public void test03() throws IOException {
        String c = "111222";
        File file = FileUtil.insertToFile(c);
        System.out.println(file.getPath());
    }

    /**
     * @Author lili
     * @Description 测试cdn刷新预热
     * @Date 10:10 2023/1/5
     **/
    @Test
    public void test4() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI7oSSKqvSOJxW", "7kS0bJQAq4Y8nzQtfQL09T3dNNRIOO");
        IAcsClient client = new DefaultAcsClient(profile);
//        RefreshObjectCachesRequest request = new RefreshObjectCachesRequest();
        PushObjectCacheRequest request = new PushObjectCacheRequest();
        request.setObjectPath("http://res.ttsdk.dragame.com/subGame/subGame24_privacy2_1672718242051.html");
//        request.setObjectType("File");
        try {
            PushObjectCacheResponse response = client.getAcsResponse(request);
//            RefreshObjectCachesResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        }  catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    /**
     * @Author lili
     * @Description 将oss的url转换成cdn的url
     * @Date 10:10 2023/1/5
     **/
    @Test
    public void test5 () {
        String url = "https://longyougame.oss-cn-hangzhou.aliyuncs.com/subGame/subGame24_privacy2_1672718242051.html";
        System.out.println("cdn-->" + OssBootUtil.getCdnUrl());
        System.out.println("cdn2-->" + OssBootUtil.getBucketName() + "." +OssBootUtil.getEndPoint());
        url = url.replace(OssBootUtil.getBucketName() + "." +OssBootUtil.getEndPoint(),OssBootUtil.getCdnUrl());
        url = url.replace("https","http");
        System.out.println(url);
    }

    @Test
    public void test6 () {
        String fileUrl = "http://res.ttsdk.dragame.com/subGame/subGame23_temp7622087567300407855.html";
        fileUrl = fileUrl.substring(fileUrl.indexOf(OssBootUtil.getPrivacyPolicyUrl() + SymbolConstant.SINGLE_SLASH), fileUrl.length());
        System.out.println(fileUrl);
    }

}
