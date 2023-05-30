package org.jeecg.modules.advert.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import org.springframework.core.io.FileSystemResource;
import sun.misc.BASE64Decoder;

public class ImageBase64Util {

    /**
     * @param base64
     * @return java.io.File
     * @author chenyw
     * @description base64转file
     * @date 18:04 2023/2/27/027
     **/
    public static File base64ToFile(String base64) throws Exception {
        if(base64.contains("data:image")){
            base64 = base64.substring(base64.indexOf(",")+1);
        }
        base64 = base64.toString().replace("\r\n", "");
        //创建文件目录
        String prefix=".jpeg";
        File file = File.createTempFile(UUID.randomUUID().toString(), prefix);
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes =  decoder.decodeBuffer(base64);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);

        }finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

}
