# coding=utf-8
import os
import log_utils


def install(path):
    print(path)
    if not os.path.exists(path):
        log_utils.error('the install apk file is not exists!!!!!!!!')
        return

    install_result = input(u"是否安装APK,1为安装，2为不安装:")

    if install_result.strip() and install_result == "1":
        print( u"安装中....")
        print(os.popen("adb install -r %s" % path).read())
        print(u"安装完成")
    else:
        return 0
