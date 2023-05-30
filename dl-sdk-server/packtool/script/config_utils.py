# encoding:utf-8
# Author:xiaohei
# CreateTime:2014-10-25
#
# The config operations
#
#
import sys
import os
import os.path
import file_utils
import log_utils
from xml.etree import ElementTree as ET
from xml.etree.ElementTree import SubElement
from xml.etree.ElementTree import Element
from xml.etree.ElementTree import ElementTree


def getLocalConfig():
    return None


def getToolVersion():
    config = getLocalConfig()
    if config and "tool_versionName" in config:
        return config['tool_versionName']

    return "unkown"


def getJDKHeapSize():
    config = getLocalConfig()
    if config and "jdk_heap_size" in config:
        return config['jdk_heap_size']

    return 512


def get_py_version():
    version = sys.version_info
    major = version.major
    minor = version.minor
    micro = version.micro

    currVersion = str(major) + "." + str(minor) + "." + str(micro)

    return currVersion


def is_py_env_2():
    version = sys.version_info
    major = version.major
    return major == 2


def getAllGames():
    """
        get all games
    """
    configFile = file_utils.getFullPath("games/games.xml")
    try:
        tree = ET.parse(configFile)
        root = tree.getroot()
    except Exception as e:
        print(e)
        log_utils.error("can not parse games.xml.path:%s", configFile)
        return None

    gamesNode = root.find('games')
    if gamesNode == None:
        return None

    games = gamesNode.findall('game')

    if games == None or len(games) <= 0:
        return None

    lstGames = []
    for cNode in games:
        game = {}
        params = cNode.findall('param')
        if params != None and len(params) > 0:
            for cParam in params:
                key = cParam.get("name")
                val = cParam.get("value")
                game[key] = val

        logNode = cNode.find('log')
        if logNode != None:
            game['log'] = dict()
            logParams = logNode.findall('param')
            if logParams != None and len(logParams) > 0:
                for lParam in logParams:
                    key = lParam.get("name")
                    val = lParam.get('value')
                    game['log'][key] = val

        lstGames.append(game)

    return lstGames


def getTestKeyStore():
    keystore = {}
    keystore['keystore'] = "config/keystore/xiaohei.keystore"
    keystore['password'] = "xiaohei"
    keystore['aliaskey'] = "xiaohei"
    keystore['aliaspwd'] = "xiaohei"

    return keystore


def getKeystore(appName, channelId):
    lstKeystores = getAllKeystores(appName)
    if lstKeystores != None and len(lstKeystores) > 0:
        for keystore in lstKeystores:
            if keystore['channelId'] == channelId:
                return keystore

    return getDefaultKeystore(appName)


def getDefaultKeystore(appName):
    fileName = "games/" + appName + "/keystore.xml"
    configFile = file_utils.getFullPath(fileName)
    try:
        tree = ET.parse(configFile)
        root = tree.getroot()
    except Exception as e:
        log_utils.error("can not parse keystore.xml.path:%s", configFile)
        return None

    params = root.find("default").findall("param")
    channel = {}
    for cParam in params:
        key = cParam.get('name')
        val = cParam.get('value')
        channel[key] = val

    return channel


def getSubDefaultKeystore():
    fileName = "subKeyStore/keystore.xml"
    configFile = file_utils.getFullPath(fileName)
    try:
        tree = ET.parse(configFile)
        root = tree.getroot()
    except Exception as e:
        log_utils.error("can not parse keystore.xml.path:%s", configFile)
        return None

    params = root.find("default").findall("param")
    channel = {}
    for cParam in params:
        key = cParam.get('name')
        val = cParam.get('value')
        channel[key] = val

    return channel


def getAllKeystores(appName):
    fileName = "games/" + appName + "/keystore.xml"

    configFile = file_utils.getFullPath(fileName)

    try:
        tree = ET.parse(configFile)
        root = tree.getroot()
    except Exception as e:
        log_utils.error("can not parse keystore.xml.path:%s", configFile)
        return None

    channels = root.find("keystores").findall("channel")
    lstKeystores = []

    for cNode in channels:
        channel = {}
        params = cNode.findall("param")
        for cParam in params:
            key = cParam.get('name')
            val = cParam.get('value')
            channel[key] = val

        lstKeystores.append(channel)

    return lstKeystores


def getAppID():
    configFile = file_utils.getFullPath("config/config.xml")

    try:
        tree = ET.parse(configFile)
        root = tree.getroot()
    except Exception as e:
        log_utils.error("can not parse config.xml.path:%s", configFile)
        return None

    gameNode = root.find("game")

    if gameNode == None:
        return None

    appID = gameNode.get('appID')

    return appID


def getAppKey():
    configFile = file_utils.getFullPath("config/config.xml")

    try:
        tree = ET.parse(configFile)
        root = tree.getroot()
    except Exception as e:
        log_utils.error("can not parse config.xml.path:%s", configFile)
        return None

    gameNode = root.find("game")

    if gameNode == None:
        return None

    appID = gameNode.get('appKey')

    return appID


def getAllChannels(appName):
    fileName = "games/" + appName + "/config.xml"

    configFile = file_utils.getFullPath(fileName)

    if not os.path.exists(configFile):
        log_utils.error("%s is not exists", configFile)
        return

    try:
        tree = ET.parse(configFile)
        root = tree.getroot()
    except Exception as e:
        log_utils.error("can not parse config.xml.path:%s", configFile)
        return None

    lstGPlugins = []
    globalPluginsNode = root.find("global-plugins")
    if globalPluginsNode is not None:
        globalPlugins = globalPluginsNode.findall("plugin")
        if globalPlugins is not None and len(globalPlugins) > 0:
            for pluginNode in globalPlugins:
                plugin = {}
                plugin['name'] = pluginNode.get("name")
                plugin['desc'] = pluginNode.get("desc")
                plugin['class'] = pluginNode.get("class")
                plugin['type'] = pluginNode.get("type")

                lstGPlugins.append(plugin)
                print(lstGPlugins)

    channels = root.find("channels").findall("channel")
    lstChannels = []
    for cNode in channels:
        channel = {}
        params = cNode.findall("param")
        for cParam in params:
            key = cParam.get('name')
            val = cParam.get('value')
            channel[key] = val

        sdkVersionNode = cNode.find('sdk-version')
        if sdkVersionNode != None and len(sdkVersionNode) > 0:
            versionCodeNode = sdkVersionNode.find('versionCode')
            versionNameNode = sdkVersionNode.find('versionName')
            if versionCodeNode != None and versionNameNode != None:
                channel['sdkLogicVersionCode'] = versionCodeNode.text
                channel['sdkLogicVersionName'] = versionNameNode.text

        sdkParams = cNode.find("sdk-params")
        tblSDKParams = {}

        if sdkParams != None:
            sdkParamNodes = sdkParams.findall('param')
            if sdkParamNodes != None and len(sdkParamNodes) > 0:
                for cParam in sdkParamNodes:
                    key = cParam.get('name')
                    val = cParam.get('value')
                    tblSDKParams[key] = val

        channel['sdkParams'] = tblSDKParams

        localGPlugins = list()
        if len(lstGPlugins) > 0:
            for p in lstGPlugins:
                localP = {}
                localP['name'] = p['name']
                localP['desc'] = p['desc']
                localP['class'] = p['class']
                localP['type'] = p['type']
                loadThirdPluginUserConfig(appName, channel, localP, localP['name'])
                localGPlugins.append(localP)

        ret = loadChannelUserConfig(appName, channel)
        if ret:
            lstPlugins = [] + localGPlugins
            pluginsNode = cNode.find("plugins")

            if pluginsNode != None:
                pluginNodeLst = pluginsNode.findall("plugin")
                if pluginNodeLst != None and len(pluginNodeLst) > 0:

                    for cPlugin in pluginNodeLst:
                        plugin = {}
                        plugin['name'] = cPlugin.get('name')

                        exists = False
                        for p in lstPlugins:
                            if p['name'] == plugin['name']:
                                exists = True
                                break

                        if not exists:
                            plugin['desc'] = cPlugin.get('desc')
                            plugin['class'] = cPlugin.get('class')
                            plugin['type'] = cPlugin.get('type')
                            loadThirdPluginUserConfig(appName, channel, plugin, plugin['name'])
                            lstPlugins.append(plugin)

            channel['third-plugins'] = lstPlugins
            lstChannels.append(channel)

    return lstChannels


def handlePluginConfig(channel, decompileDir):
    pass


def loadThirdPluginUserConfig(appName, channel, plugin, pluginName):
    # configFile = file_utils.getFullPath("config/plugin/" + pluginName + "/config.xml")
    configFile = file_utils.getFullPath(
        "games/" + appName + "/channels/" + channel['id'] + "/plugin/" + pluginName + "/config.xml")

    if not os.path.exists(configFile):
        configFile = file_utils.getFullPath(
            "games/" + appName + "/plugin/" + pluginName + "/config.xml")
        if not os.path.exists(configFile):
            log_utils.error("the plugin %s config.xml file is not exists.path:%s", pluginName,
                            configFile)
            return 0

    try:
        tree = ET.parse(configFile)
        root = tree.getroot()
    except:
        log_utils.error("can not parse config.xml.path:%s", configFile)
        return 0

    configNode = root

    subpluginNodes = configNode.find("subplugins")

    if subpluginNodes != None and len(subpluginNodes) > 0:
        plugin['subplugins'] = []
        for subNode in subpluginNodes:
            subplugin = {}
            subplugin['name'] = subNode.get('name')
            subplugin['desc'] = subNode.get('desc')
            subParamNodes = subNode.findall('param')
            subplugin['params'] = []
            if subParamNodes != None and len(subParamNodes) > 0:
                for subParamNode in subParamNodes:
                    param = {}
                    param['name'] = subParamNode.get('name')
                    param['value'] = subParamNode.get('value')
                    # log_utils.debug("name:"+param['name']+";val:"+param['value'])
                    param['required'] = subParamNode.get('required')
                    param['showName'] = subParamNode.get('showName')
                    param['bWriteInManifest'] = subParamNode.get('bWriteInManifest')
                    param['bWriteInClient'] = subParamNode.get('bWriteInClient')
                    subplugin['params'].append(param)

            plugin['subplugins'].append(subplugin)

    paramNodes = configNode.find("params")
    plugin['params'] = []
    if paramNodes != None and len(paramNodes) > 0:

        for paramNode in paramNodes:
            param = {}
            param['name'] = paramNode.get('name')
            param['value'] = paramNode.get('value')
            param['required'] = paramNode.get('required')
            param['showName'] = paramNode.get('showName')
            param['bWriteInManifest'] = paramNode.get('bWriteInManifest')
            param['bWriteInClient'] = paramNode.get('bWriteInClient')
            plugin['params'].append(param)

    operationNodes = configNode.find("operations")
    plugin['operations'] = []
    if operationNodes != None and len(operationNodes) > 0:

        for opNode in operationNodes:
            op = {}
            op['type'] = opNode.get('type')
            op['from'] = opNode.get('from')
            op['to'] = opNode.get('to')
            plugin['operations'].append(op)

    pluginNodes = configNode.find("plugins")
    if pluginNodes != None and len(pluginNodes) > 0:
        plugin['plugins'] = []
        for pNode in pluginNodes:
            p = {}
            p['name'] = pNode.get('name')
            p['type'] = pNode.get('type')
            plugin['plugins'].append(p)

    return 1


def loadChannelUserConfig(appName, channel):
    configFile = file_utils.getFullPath("config/sdk/" + channel['sdk'] + "/config.xml")

    if not os.path.exists(configFile):
        log_utils.error("the config.xml is not exists of sdk %s.path:%s", channel['sdk'],
                        configFile)
        return 0

    try:
        tree = ET.parse(configFile)
        root = tree.getroot()
    except:
        log_utils.error("can not parse config.xml.path:%s", configFile)
        return 0

    configNode = root

    paramNodes = configNode.find("params")
    channel['params'] = []
    if paramNodes != None and len(paramNodes) > 0:

        for paramNode in paramNodes:
            param = {}
            param['name'] = paramNode.get('name')
            param['required'] = paramNode.get('required')

            if param['required'] == '1':

                key = param['name']
                if key in channel['sdkParams'] and channel['sdkParams'][key] != None:
                    param['value'] = channel['sdkParams'][key]
                else:
                    log_utils.error("the sdk %s 'sdkParam's is not all configed in the config.xml.path:%s",
                                    channel['name'], configFile)
                    return 0
            else:  # 添加对required=0 的判断，如果games/appName/config.xml 有配置该参数则写入，否则用默认值
                key = param['name']
            if key in channel['sdkParams'] and channel['sdkParams'][key] is not None:
                param['value'] = channel['sdkParams'][key]
            else:
                param['value'] = paramNode.get('value')

            param['showName'] = paramNode.get('showName')
            param['bWriteInManifest'] = paramNode.get('bWriteInManifest')
            param['bWriteInClient'] = paramNode.get('bWriteInClient')
            channel['params'].append(param)


    # begin
    if channel['sdkParams'] is not None:

        for key in channel['sdkParams']:
            extraKey = True
            if channel['params'] is not None and len(channel['params']) > 0:
                for p in channel['params']:
                    if p['name'] == key:
                        extraKey = False
                        break

            if extraKey:
                param = {}
                param['name'] = key
                param['value'] = channel['sdkParams'][key]
                param['required'] = "1"
                param['showName'] = key
                param['bWriteInManifest'] = "0"
                param['bWriteInClient'] = "1"
                channel['params'].append(param)
    # end


    operationNodes = configNode.find("operations")
    channel['operations'] = []
    if operationNodes != None and len(operationNodes) > 0:

        for opNode in operationNodes:
            op = {}
            op['type'] = opNode.get('type')
            op['from'] = opNode.get('from')
            op['to'] = opNode.get('to')
            channel['operations'].append(op)

    pluginNodes = configNode.find("plugins")
    if pluginNodes != None and len(pluginNodes) > 0:
        channel['plugins'] = []
        for pNode in pluginNodes:
            p = {}
            p['name'] = pNode.get('name')
            p['type'] = pNode.get('type')
            channel['plugins'].append(p)

    versionNode = configNode.find("version")
    if versionNode != None and len(versionNode) > 0:
        versionCodeNode = versionNode.find("versionCode")
        versionNameNode = versionNode.find("versionName")
        # the sdk version code is used to check version update for the sdk.
        if versionCodeNode != None and versionNameNode != None:
            channel['sdkVersionCode'] = versionCodeNode.text
            channel['sdkVersionName'] = versionNameNode.text

    return 1


def writeDeveloperProperties(game, channel, targetFilePath):
    print("+++++++++++++++++++++++++++++++++++")
    print(channel['sdk'])

    targetFilePath = file_utils.getFullPath(targetFilePath)

    if not os.path.exists(targetFilePath):
        log_utils.debug("channel.properties is not exist")
        return
    file_utils.changeChannelProperties(targetFilePath,"channel_id=" ,channel['channelId'])
    file_utils.changeChannelProperties(targetFilePath, "isDebug=", game['debug'])




def writePluginConfigs(channel, targetFilePath):
    file_key = "plugins="
    thirdPlugins = channel.get('third-plugins')
    if thirdPlugins != None and len(thirdPlugins) > 0:
        for cPlugin in thirdPlugins:
            pluginClassName = cPlugin["class"]+";"
            if os.path.exists(targetFilePath):
                # 将文件读取到内存中
                with open(targetFilePath, "r", encoding="gbk") as f:
                    lines = f.readlines()
                    # 写的方式打开文件
                with open(targetFilePath, "w", encoding="gbk") as f_w:
                    for line in lines:
                        if file_key in line:
                            # 替换
                            lines += pluginClassName
                            print(line)
                        f_w.write(line)




