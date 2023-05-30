# -*- coding: utf-8 -*-

import sys

import file_utils

import config_utils
import os
import os.path
import time
import log_utils
import core
import install_apk



def main(game):
    appName = game['appName']

    baseApkPath = file_utils.getFullPath('games/' + appName + '/base')
    baseApkPath = file_name(baseApkPath)
    log_utils.info("the base apk file is : %s", baseApkPath)

    if not os.path.exists(baseApkPath):
        log_utils.error('the base apk file is not exists')
        return

    channels = config_utils.getAllChannels(appName)

    if channels is None or len(channels) == 0:
        log_utils.info("没有任何可以打包的渠道")
        return
    channelName = None
    isPublic = True
    if channelName is not None:

        if channelName == '*':
            packChannels(game, channels, baseApkPath, isPublic)
            return

        selectChannel = getChannelByName(channelName, channels)
        if selectChannel is None:
            log_utils.info("指定的渠道名不存在")
            return

        ret = core.pack(game, selectChannel, baseApkPath, isPublic)
        if ret:
            log_utils.error("<< all done with error >>")

        else:
            log_utils.info("<< all nice done >>")

        return

    print(u"################################################################")
    print(u"\t%-15s%-20s%-20s\n" % (u"渠道名", u"渠道号", u"渠道"))

    for ch in channels:
        print(u"\t%-20s%-20s%-20s" % (ch['sdk'], ch['id'], ch['desc']))
    print("")
    selected = []
    while (True):
        target = input(u"请选择需要打包的渠道(渠道名),全部输入*,多个用逗号分割：")

        if target == '*':
            selected = channels
        else:
            for t in target.split(','):
                t = t.strip()
                matchChannels = [c for c in channels if c['sdk'].lower() == t.lower()]
                if len(matchChannels) > 0:
                    selected.append(matchChannels[0])

        if len(selected) == 0:
            print(u"\n无效的渠道名，请重新输入！！\n")
        else:
            break

    packChannels(game, selected, baseApkPath, isPublic)


def packChannels(game, channels, baseApkPath, isPublic):
    clen = len(channels)
    log_utils.info("now hava %s channels to package...", clen)

    sucNum = 0
    failNum = 0
    for channel in channels:
        ret = core.pack(game, channel, baseApkPath, isPublic)
        if ret:
            failNum = failNum + 1
        else:
            sucNum = sucNum + 1

    log_utils.info("<< success num:%s; failed num:%s >>", sucNum, failNum)
    if failNum > 0:
        log_utils.error("<< all done with error >>")
    else:
        log_utils.info("<< all nice done >>")
        if clen != 1:
            return
        name_ = game["appName"]
        path = file_utils.getFullOutputPath(name_, channels[0]["sdk"])
        print(path)

        # install_apk.install(path + "/" + game["outputApkName"])

def getChannelByName(channelName, channels):

    if channelName is None or channels is None or len(channels) == 0:
        return None

    for c in channels:
        if c['sdk'].lower() == channelName.lower():
            return c
    return None

def file_name(file_dir):
    apkName = ""
    for root, dirs, files in os.walk(file_dir):
        print(root) #当前目录路径
        print(dirs) #当前路径下所有子目录
        print(files) #
        apkName = root + "/"+files[0]
    return apkName
