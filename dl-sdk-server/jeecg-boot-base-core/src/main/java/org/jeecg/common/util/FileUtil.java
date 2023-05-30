package org.jeecg.common.util;

import ch.qos.logback.core.util.TimeUtil;
import com.aliyun.oss.model.PutObjectResult;
import com.sun.org.apache.xpath.internal.operations.Bool;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.filter.FileTypeFilter;
import org.jeecg.common.util.filter.StrAttackFilter;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.util.oss.OssBootUtil;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author lili
 * @Description 文件
 * @Date $ 2022-12-30$
 **/
@Slf4j
public class FileUtil {

    /**
     * @param multiFile
     * @return java.io.File
     * @Author lili
     * @Description MultipartFile转换成File
     * @Date 10:17 2022/12/30
     **/
    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param file
     * @return java.lang.Boolean
     * @Author lili
     * @Description 过滤文件类型
     * @Date 10:24 2022/12/30
     **/
    public static Boolean checkFile(File file, Set<String> set) throws IOException {
        MultipartFile cMultiFile = new MockMultipartFile("file", file.getName(), null,
            new FileInputStream(file));
        // 获取文件原本的名字
        String originName = cMultiFile.getOriginalFilename();
        // 取出文件的后缀
        int count = 0;
        for (int i = 0; i < originName.length(); i++) {
            if (originName.charAt(i) == '.') {
                count = i;
                break;
            }
        }
        String endName = originName.substring(count); //取出文件类型
        if (!set.contains(endName)) {
//            return new String("上传的文件类型错误,只能上传pdf,doc,docx类型的文件");
            return false;
//            return  "上传的文件类型错误,只能上传html类型的文件";
        }
        return true;
    }

    /**
     * @param file
     * @return java.lang.String
     * @Author lili
     * @Description 得到html文件的源代码
     * @Date 10:21 2022/12/30
     **/
    public static String getHtmlFile(File file) {
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
        return htmlStr;
    }

    /**
     * @param content
     * @return java.io.File
     * @Author lili
     * @Description 将文本写入临时文件
     * @Date 17:13 2023/1/3
     **/
    public static File insertToFile(String content) throws IOException {
        File file = File.createTempFile("temp", ".html");
//        FileOutputStream fileOutputStream = new FileOutputStream("d://log.txt");
        FileWriter fw = new FileWriter(file);
        fw.write(content);
        fw.flush();
        fw.close();
        System.out.println("写入成功");
        return file;
    }

    /**
     * @param path 指定目录地址
     * @param file 文件
     * @return java.lang.String
     * @Author lili
     * @Description 上传到指定目录
     * @Date 10:32 2022/12/30
     **/
    public static Boolean upload(String path, MultipartFile file) {
        if (!path.endsWith(SymbolConstant.SINGLE_SLASH)) {
            path = path.concat(SymbolConstant.SINGLE_SLASH);
        }
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // 获取文件名(包括后缀)
        String fileName = file.getOriginalFilename();
        try (FileOutputStream fos = new FileOutputStream(path + fileName)) {
            fos.write(file.getBytes());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param path 指定目录地址
     * @param file 文件
     * @return java.lang.String
     * @Author lili
     * @Description 上传到指定目录
     * @Date 10:32 2022/12/30
     **/
    public static Boolean upload(String path, String gameId, String subGameId, MultipartFile file) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // 获取文件名(包括后缀)
        String fileName = gameId + "-" + subGameId + "-" + file.getOriginalFilename();
        try (FileOutputStream fos = new FileOutputStream(path + fileName)) {
            fos.write(file.getBytes());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param path: 文件路径
     * @return Boolean
     * @author czb
     * @description 删除文件
     * @date 2023/1/6 18:17
     */
    public static Boolean delFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    /**
     * @param file
     * @return java.lang.String
     * @Author lili
     * @Description 计算文件的md5值
     * @Date 14:08 2023/1/19
     **/
    public static String computeMD5(File file) {
        DigestInputStream din = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //第一个参数是一个输入流
            din = new DigestInputStream(new BufferedInputStream(new FileInputStream(file)), md5);

            byte[] b = new byte[1024];
            while (din.read(b) != -1);

            byte[] digest = md5.digest();

            StringBuilder result = new StringBuilder();
//            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest));
            return result.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (din != null) {
                    din.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
