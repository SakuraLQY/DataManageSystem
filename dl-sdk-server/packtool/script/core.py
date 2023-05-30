# -*- coding: utf-8 -*-
# Author:xiaohei
# CreateTime:2014-10-25
#
# The main operation entry
#
# 1、decomplie apk to smali use apktool.jar
# 2、rename the apk package name to new name configed in channel
# 3、copy all sdk resources of the channel to the target decompile dir
#    3.1、copy all none xml files to the target dir
#	 3.2、merge the sdk AndroidManifest.xml to the target apk AndroidManifest.xml
#	 3.3、merge all the sdk res xml like strings.xml, drawables.xml,styles.xml...
# 4、copy all code and resources of supported third-plugins
# 5、generate plugin info and develop info in assets folder,and write meta-data to AndroidManifest.xml
# 6、regenerate the R file
# 7、recompile the apk file
# 8、sign the apk file
# 9、zip allign the apk file

import sys
import file_utils
import apk_utils
import config_utils
import log_utils
import os
import os.path
import time

# 打包方法
def pack(usedApkPath,newApkPath,iconPath,gameName,gamePackage,workspaceName,resPathList,versionName,versionCode):
    log_utils.info("%s now to package %s...", gameName, gamePackage)

    # 获得临时存放目录，并把apk拷贝过去
    workDir = 'workspace/'+workspaceName
    workDir = file_utils.getFullPath(workDir)

    file_utils.del_file_folder(workDir)
    tempApkSource = workDir + "/temp.apk"
    file_utils.copy_file(usedApkPath, tempApkSource)

    #反编译操作
    decompileDir = workDir + "/decompile"
    ret = apk_utils.decompileApk(tempApkSource, decompileDir)
    if ret:
        return 1

    # change xml config and so on（重命名包名）
    if gamePackage != 'gamepackage':
        newPackageName = apk_utils.renamePackageName(decompileDir, gamePackage)

    # auto handle icon（icon替换）
    # iconPath = 'games/icon.png'
    apk_utils.appendChannelIconMark(iconPath, decompileDir)


    # 替换资源
    if len(resPathList):
        for resPath in resPathList:
            newResPath = resPath.split("::", 1)
            print(newResPath[0] + "---" + decompileDir+newResPath[1])
            apk_utils.replaceResources(newResPath[0],decompileDir+newResPath[1])


    # modify game name if channel specified.(修改游戏名字)
    apk_utils.modifyGameName(gameName, decompileDir)

    # modify yml（修改版本等配置文件，暂时不用）
    apk_utils.modifyYml(versionName, versionCode, decompileDir)

    # 输出apk名
    ret = recompileApk(workDir, decompileDir, newApkPath)
    if ret:
        return 1

    # Delete temporary packaging directory
    file_utils.del_file_folder(workDir)

    log_utils.info("game_key %s package success.", workspaceName)
    return 0


def recompileApk(workDir, decompileDir, newApkPath):
    targetApk = workDir + "/output.apk"
    log_utils.debug("now to recompileApk for %s", newApkPath)
    ret = apk_utils.recompileApk(decompileDir, targetApk)
    if ret:
        return 1

    apk_utils.copyRootResFiles(targetApk, decompileDir)


    ret = apk_utils.signApk(targetApk)
    if ret:
        return 1

    # ret = apk_utils.alignApk(targetApk, newApkPath)
    # if ret:
    #     return 1

    file_utils.copy_files(targetApk, newApkPath)

    log_utils.info("package success to %s.",  newApkPath)
    return 0
