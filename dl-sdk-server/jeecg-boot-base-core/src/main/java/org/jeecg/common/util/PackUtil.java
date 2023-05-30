package org.jeecg.common.util;

import cn.hutool.core.io.FileUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 打包工具
 * @Author: chenyw
 * @Date: 2022-12-28
 * @Version: V1.0
 */
@Slf4j
public class PackUtil {

    /**
     * @param packToolPath  打包工具路径
     * @param usedApkPath   原包地址
     * @param newApkPath    新包地址
     * @param iconPath      icon地址
     * @param gameName      游戏名
     * @param gamePackage   游戏包名
     * @param workspaceName 打包文件名
     * @param resPathList   替换资源路径
     * @param versionName
     * @param versionCode
     * @return boolean
     * @author chenyw
     * @description python打包
     * @date 17:37 2023/1/19/019
     **/
    public static boolean pythonPackage(String packToolPath, String usedApkPath, String newApkPath,
        String iconPath, String gameName, String gamePackage, String workspaceName,
        String resPathList, String versionName, String versionCode) {
        ProcessBuilder builder = new ProcessBuilder();
        if (StringUtils.isBlank(gamePackage)) {
            gamePackage = "gamepackage";
        }
        if (StringUtils.isBlank(resPathList)) {
            resPathList = "replaceresources";
        }
        if (StringUtils.isBlank(versionName)) {
            versionName = "versionName";
        }
        if (StringUtils.isBlank(versionCode)) {
            versionCode = "versionCode";
        }
        String pythonCommand = "python3";
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            // 如果是windows系统
            pythonCommand = "python";
        }
        builder.command(pythonCommand, "main.py", usedApkPath, newApkPath, iconPath, gameName,
            gamePackage, workspaceName, resPathList, versionName, versionCode);
        builder.directory(new File(packToolPath));
        log.info("正在执行python打包命令 dirRoot = {}, 执行命令 = {}", packToolPath, builder.command());
        try {
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            String allLine = "";
            String line;
            while ((line = reader.readLine()) != null) {
                allLine += line;
//                log.debug("line-{}:{}", gameName, line);
            }
            process.waitFor();
            int status = process.waitFor();
            if (status != 0 || allLine.contains("ERROR")) {
                log.error("游戏:{}打包失败  status={}", gameName, status);
            } else {
                log.info("游戏:{}打包成功", gameName);
                return true;
            }
        } catch (IOException | InterruptedException exception) {
            log.error("游戏:{}打包异常 {}", gameName, exception);
        }
        return false;
    }

    /**
     * @param filePath 目标文件地址
     * @param comment  备注
     * @return boolean
     * @author chenyw
     * @description 写入apk文件备注
     * @date 16:16 2023/1/30/030
     **/
    public static boolean setApkComment(String filePath, String comment) {
        File source = new File(filePath);
        String oldName = source.getName().substring(0, source.getName().lastIndexOf("."));
        String oldPath = source.getAbsolutePath()
            .substring(0, source.getAbsolutePath().lastIndexOf(File.separator));
        File tmpZip = new File(oldPath + File.separator + oldName + ".tmp");
        // 重命名文件
        FileUtil.move(source, tmpZip, true);
        byte[] buffer = new byte[1024];
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(tmpZip));
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(source))) {
            for (ZipEntry ze = zin.getNextEntry(); ze != null; ze = zin.getNextEntry()) {
                ZipEntry zipEntry = new ZipEntry(ze.getName());
                out.putNextEntry(zipEntry);
                for (int read = zin.read(buffer); read > -1; read = zin.read(buffer)) {
                    out.write(buffer, 0, read);
                }
                out.closeEntry();
            }
            out.setComment(comment);
            return true;
        } catch (Exception e) {
            log.error("更改apk备注失败", e);
            return false;
        }finally {
            FileUtil.del(tmpZip);
        }
    }

}
