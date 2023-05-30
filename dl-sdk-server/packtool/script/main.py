# -*- coding: utf-8 -*-

import log_utils
import core
import sys
import importlib
importlib.reload(sys)

# 实现最简单的打包

if  __name__ == "__main__":
    log_utils.debug("SDKSdk开始打包")

    #示例脚本：python main2.py E:/apkcs/1.apk;;E:/apkcs/2.apk;;E:/apkcs/123.png;;浑元太极;;com.longteng.hytj;;cs123;;E:/apkcs/splash2.png::/res/drawable/splash2.png@@E:/apkcs/loading_bg.png::/assets/platform/android/loading_bg.png@@E:/apkcs/login_bg.png::/assets/platform/android/login_bg.png
    #原包地址
    #新包地址
    #icon地址
    #游戏名
    #游戏包名
    #打包文件名
    #替换资源路径
    #替换资源项目路径
    
    # gpus = "/data/apkcs/1.apk&&/data/apkcs/2.apk&&/data/apkcs/123.png&&山海之王&&com.longteng.shzw.gdt&&cs123&&"
    # param = gpus.split("&&", 7)
    #
    # usedApkPath = param[0]
    # newApkPath = param[1]
    # iconPath = param[2]
    # gameName = param[3]
    # gamePackage = param[4]
    # workspaceName = param[5]
    #
    #
    # if param[6]:
    #     resPathList = param[6].split("@@")
    # else:
    #     resPathList = []

    usedApkPath = sys.argv[1]
    newApkPath = sys.argv[2]
    iconPath = sys.argv[3]
    gameName = sys.argv[4]
    gamePackage = sys.argv[5]
    workspaceName = sys.argv[6]

    if sys.argv[7]:
        if sys.argv[7] != 'replaceresources':
            resPathList = sys.argv[7].split("@@")
        else:
            resPathList = []
    else:
        resPathList = []

    try:
        versionName = None if sys.argv[8] == 'versionName' else sys.argv[8]
    except (IndexError, BaseException):
        versionName = None

    try:
        versionCode = None if sys.argv[9] == 'versionCode' else sys.argv[9]
    except (IndexError, BaseException):
        versionCode = None

    core.pack(usedApkPath,newApkPath,iconPath,gameName,gamePackage,workspaceName,resPathList,versionName,versionCode)


# python main2.py E:/apkcs/1.apk;;E:/apkcs/2.apk;;E:/apkcs/123.png;;山海之王;;com.longteng.shzw.gdt;;cs123;;E:/apkcs/splash2.png::/res/drawable/splash2.png@@E:/apkcs/loading_bg.png::/assets/platform/android/loading_bg.png@@E:/apkcs/login_bg.png::/assets/platform/android/login_bg.png
