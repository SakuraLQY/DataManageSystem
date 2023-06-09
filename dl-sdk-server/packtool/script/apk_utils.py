# -*- coding: utf-8 -*-

import file_utils

import os.path
import config_utils
from xml.etree import ElementTree as ET
from xml.etree.ElementTree import SubElement

import os
import os.path

import sys
import shutil
import time
from PIL import Image
import image_utils
import log_utils
import smali_utils2

androidNS = 'http://schemas.android.com/apk/res/android'


def copyLibs(game, srcDir, dstDir):
    """
        copy shared libraries
    """

    if not os.path.exists(srcDir):
        return

    if not os.path.exists(dstDir):
        os.makedirs(dstDir)

    for f in os.listdir(srcDir):
        sourcefile = os.path.join(srcDir, f)
        targetfile = os.path.join(dstDir, f)

        if (sourcefile.endswith(".jar")):
            continue

        if os.path.isfile(sourcefile):
            if not os.path.exists(targetfile) or os.path.getsize(targetfile) != os.path.getsize(
                    sourcefile):
                destfilestream = open(targetfile, 'wb')
                sourcefilestream = open(sourcefile, 'rb')
                destfilestream.write(sourcefilestream.read())
                destfilestream.close()
                sourcefilestream.close()

        if os.path.isdir(sourcefile):
            copyLibs(game, sourcefile, targetfile)


def jar2dex(srcDir, dstDir, dextool="baksmali.jar"):
    """
        compile jar files to dex.
    """

    dexToolPath = file_utils.getFullToolPath("/lib/dx.jar")
    heapSize = config_utils.getJDKHeapSize()
    cmd = file_utils.getJavaCMD() + ' -jar -Xms%sm -Xmx%sm "%s" --dex --multi-dex --output="%s" ' % (
        heapSize, heapSize, dexToolPath, dstDir)

    for f in os.listdir(srcDir):
        if f.endswith(".jar"):
            cmd = cmd + " " + os.path.join(srcDir, f)

    libsPath = os.path.join(srcDir, "libs")
    if os.path.exists(libsPath):

        for f in os.listdir(libsPath):
            if f.endswith(".jar"):
                cmd = cmd + " " + os.path.join(srcDir, "libs", f)

    ret = file_utils.execFormatCmd(cmd)
    return ret


def dexes2smali(dexDir, targetdir, dextool="baksmali.jar"):
    """
        Transfer all dex in dexDir to smali
    """

    if not os.path.exists(dexDir):
        log_utils.error("the dexDir is not exists:" + dexDir)
        return 1

    files = file_utils.list_files(dexDir, [], [])
    for f in files:
        if not f.endswith(".dex"):
            continue

        dex2smali(f, targetdir, dextool)


def dex2smali(dexFile, targetdir, dextool="baksmali.jar"):
    """
        Transfer the dex to smali.
    """
    dexFile = dexFile.replace("\\","/")
    if not os.path.exists(dexFile):
        log_utils.error("the dexfile is not exists. path:%s", dexFile)
        return 1

    if not os.path.exists(targetdir):
        os.makedirs(targetdir)

    dexFile = file_utils.getFullPath(dexFile)
    smaliTool = file_utils.getFullToolPath(dextool)
    targetdir = file_utils.getFullPath(targetdir)

    cmd = '"%s" -jar "%s" -o "%s" "%s"' % (file_utils.getJavaCMD(), smaliTool, targetdir, dexFile)

    ret = file_utils.execFormatCmd(cmd)

    return ret


def decompileApk(source, targetdir, apktool="apktool2.jar"):
    """
        Decompile apk
    """
    apkfile = file_utils.getFullPath(source)
    targetdir = file_utils.getFullPath(targetdir)
    apktool = file_utils.getFullToolPath(apktool)
    if os.path.exists(targetdir):
        file_utils.del_file_folder(targetdir)
    if not os.path.exists(targetdir):
        os.makedirs(targetdir)

    heapSize = config_utils.getJDKHeapSize()
    cmd = '"%s" -jar -Xms%sm -Xmx%sm "%s" -v d -b -f "%s" -o "%s"' % (
        file_utils.getJavaCMD(), heapSize, heapSize, apktool, apkfile, targetdir)
    # cmd = '"%s" -q d -d -f "%s" "%s"' % (apktool, apkfile, targetdir)
    # print("decompile cmd : "+ cmd)

    ret = file_utils.execFormatCmd(cmd)
    return ret


def recompileApk(sourcefolder, apkfile, apktool="apktool2.jar"):
    """
        Recompile apk
    """
    os.chdir(file_utils.curDir)
    sourcefolder = file_utils.getFullPath(sourcefolder)
    apkfile = file_utils.getFullPath(apkfile)
    apktool = file_utils.getFullToolPath(apktool)

    ret = 1
    if os.path.exists(sourcefolder):
        heapSize = config_utils.getJDKHeapSize()
        cmd = '"%s" -jar -Xms%sm -Xmx%sm "%s" -v b -f "%s" -o "%s"' % (
            file_utils.getJavaCMD(), heapSize, heapSize, apktool, sourcefolder, apkfile)
        # cmd = '"%s" -q b -f "%s" "%s"' % (apktool, sourcefolder, apkfile)
        ret = file_utils.execFormatCmd(cmd)

    return ret


def generateKeystore(workDir, game, channel):
    """
        auto generate keystore file
        if user want to use auto generated keystore file, you can use this method
    """
    keytoolPath = file_utils.getJavaBinDir() + "keytool"

    keystorePath = os.path.join(workDir, 'temp_keystore')
    if not os.path.exists(keystorePath):
        os.makedirs(keystorePath)

    keystore = dict()
    keystore['keystore'] = os.path.join(keystorePath, "temp.keystore")
    keystore['password'] = channel['name'] + game['appName']
    keystore['aliaskey'] = game["appName"] + channel["name"] + time.strftime('%Y%m%d%H%M%S')
    keystore['aliaspwd'] = channel['name'] + game['appName']
    keystore['sigalg'] = "SHA1withRSA"

    dname = "CN=mqttserver.ibm.com, OU=ID, O=IBM, L=Hursley, S=Hants, C=GB"

    cmd = '"%s" -genkeypair -dname "%s" -alias "%s" -keyalg "RSA" -sigalg "%s" -validity 20000 -keystore "%s" -storepass "%s" -keypass "%s" ' % (
        keytoolPath, dname, keystore['aliaskey'], keystore['sigalg'], keystore['keystore'],
        keystore['password'], keystore['aliaspwd'])

    ret = file_utils.execFormatCmd(cmd)

    if ret:
        return None

    return keystore


def signApk(apkfile):
    """
        Sign apk
    """
    signApkInternal(apkfile, "config/keystore/game.keystore", "game888", "game.keystore",
                    "game888", "MD5withRSA")


def signApkInternal(apkfile, keystore, password, alias, aliaspwd, sigalg):
    apkfile = file_utils.getFullPath(apkfile)
    keystore = file_utils.getFullPath(keystore)
    aapt = file_utils.getFullToolPath("aapt")

    if not os.path.exists(keystore):
        log_utils.error("the keystore file is not exists. %s", keystore)
        return 1

    listcmd = '%s list %s' % (aapt, apkfile)

    output = os.popen(listcmd).read()
    for filename in output.split('\n'):
        if filename.find('META_INF') == 0:
            rmcmd = '"%s" remove "%s" "%s"' % (aapt, apkfile, filename)
            file_utils.execFormatCmd(rmcmd)

    if sigalg is None:
        sigalg = "SHA1withRSA"

    signcmd = '"%sjarsigner" -digestalg SHA1 -sigalg %s -keystore "%s" -storepass "%s" -keypass "%s" "%s" "%s" ' % (
        file_utils.getJavaBinDir(), sigalg,
        keystore, password, aliaspwd, apkfile, alias)

    ret = file_utils.execFormatCmd(signcmd)

    return ret


def copyRootResFiles(apkfile, decompileDir):
    apkfile = file_utils.getFullPath(apkfile)
    aapt = file_utils.getFullToolPath("aapt")
    decompileDir = file_utils.getFullPath(decompileDir)

    igoreFiles = ['AndroidManifest.xml', 'apktool.yml', 'smali', 'res', 'original', 'lib', 'build',
                  'assets', 'unknown', 'kotlin']

    for k in range(2, 20):
        igoreFiles.append("smali_classes" + str(k))

    igoreFileFullPaths = []

    for ifile in igoreFiles:
        fullpath = os.path.join(decompileDir, ifile)
        igoreFileFullPaths.append(fullpath)

    addFiles = []

    addFiles = file_utils.list_files(decompileDir, addFiles, igoreFileFullPaths)

    if len(addFiles) <= 0:
        return

    addCmd = '"%s" add "%s"'
    for f in addFiles:
        fname = f[(len(decompileDir) + 1):]
        addCmd = addCmd + ' ' + fname

    addCmd = addCmd % (aapt, apkfile)

    currPath = os.getcwd()
    print("-----addCmd---" + addCmd)
    os.chdir(decompileDir)
    file_utils.execFormatCmd(addCmd)
    os.chdir(currPath)


def alignApk(apkfile, targetapkfile):
    """
        zip align the apk file
    """

    align = file_utils.getFullToolPath('zipalign')
    aligncmd = '"%s" -f 4 "%s" "%s"' % (align, apkfile, targetapkfile)

    ret = file_utils.execFormatCmd(aligncmd)

    return ret


def getPackageName(decompileDir):
    """
        Get The package attrib of application node in AndroidManifest.xml
    """

    manifestFile = decompileDir + "/AndroidManifest.xml"
    manifestFile = file_utils.getFullPath(manifestFile)
    ET.register_namespace('android', androidNS)
    tree = ET.parse(manifestFile)
    root = tree.getroot()
    package = root.attrib.get('package')

    return package


def renamePackageName(decompileDir, newPackageName, isPublic=True):
    """
        Rename package name to the new name configed in the channel
    """
    manifestFile = decompileDir + "/AndroidManifest.xml"
    manifestFile = file_utils.getFullPath(manifestFile)
    ET.register_namespace('android', androidNS)
    tree = ET.parse(manifestFile)
    root = tree.getroot()
    package = root.attrib.get('package')

    oldPackageName = package
    tempPackageName = newPackageName.strip()

    if tempPackageName != None and len(tempPackageName) > 0:

        if tempPackageName[0:1] == '.':
            if not isPublic:
                newPackageName = oldPackageName + ".debug" + tempPackageName
            else:
                newPackageName = oldPackageName + tempPackageName
        else:
            newPackageName = tempPackageName

    if newPackageName == None or len(newPackageName) <= 0:
        newPackageName = oldPackageName

    log_utils.info("the new package name is %s", newPackageName)
    # now to check activity or service
    appNode = root.find('application')
    if appNode != None:

        activityLst = appNode.findall('activity')
        key = '{' + androidNS + '}name'
        if activityLst != None and len(activityLst) > 0:
            for aNode in activityLst:
                activityName = aNode.attrib[key]
                if activityName[0:1] == '.':
                    activityName = oldPackageName + activityName
                elif activityName.find('.') == -1:
                    activityName = oldPackageName + '.' + activityName
                aNode.attrib[key] = activityName

        serviceLst = appNode.findall('service')
        # key = '{'+androidNS+'}name'
        if serviceLst != None and len(serviceLst) > 0:
            for sNode in serviceLst:
                serviceName = sNode.attrib[key]
                if serviceName[0:1] == '.':
                    serviceName = oldPackageName + serviceName
                elif serviceName.find('.') == -1:
                    serviceName = oldPackageName + '.' + serviceName
                sNode.attrib[key] = serviceName

        receiverLst = appNode.findall('receiver')
        # key = '{'+androidNS+'}name'
        if receiverLst != None and len(receiverLst) > 0:
            for sNode in receiverLst:
                receiverName = sNode.attrib[key]
                if receiverName[0:1] == '.':
                    receiverName = oldPackageName + receiverName
                elif receiverName.find('.') == -1:
                    receiverName = oldPackageName + '.' + receiverName
                sNode.attrib[key] = receiverName

        providerLst = appNode.findall('provider')
        # key = '{'+androidNS+'}name'
        if providerLst != None and len(providerLst) > 0:
            for sNode in providerLst:
                providerName = sNode.attrib[key]
                if providerName[0:1] == '.':
                    providerName = oldPackageName + providerName
                elif providerName.find('.') == -1:
                    providerName = oldPackageName + '.' + providerName
                sNode.attrib[key] = providerName

        #替换旧的包名配置
        providerLst = appNode.findall('provider')
        key = '{' + androidNS + '}authorities'
        if providerLst != None and len(providerLst) > 0:
            for sNode in providerLst:
                providerName = sNode.attrib[key]
                sNode.attrib[key] = providerName.replace(oldPackageName, newPackageName)

    # 删除影响参数
    try:
        del root.attrib['{http://schemas.android.com/apk/res/android}compileSdkVersion']
    except (IndexError, BaseException):
        mycz = ''

    try:
        del root.attrib['{http://schemas.android.com/apk/res/android}compileSdkVersionCodename']
    except (IndexError, BaseException):
        mycz = ''

    try:
        del root.attrib['android:compileSdkVersion']
    except (IndexError, BaseException):
        mycz = ''

    try:
        del root.attrib['android:compileSdkVersionCodename']
    except (IndexError, BaseException):
        mycz = ''

    root.attrib['package'] = newPackageName
    tree.write(manifestFile, 'UTF-8')

    package = newPackageName
    return package


def copyResource(game, channel, packageName, sdkDir, decompileDir, operations, name,
                 pluginInfo=None):
    """
        Copy sdk resources to the apk decompile dir

        Merge manifest.xml
        Merge all res xml if the xml already exists in target apk.
        copy all others resources
    """

    if operations != None:
        for child in operations:
            if child['type'] == 'mergeManifest':
                manifestFrom = file_utils.getFullPath(os.path.join(sdkDir, child['from']))
                manifestFromTemp = manifestFrom
                manifestTo = file_utils.getFullPath(os.path.join(decompileDir, child['to']))

                if 'orientation' in game:
                    if game['orientation'] == 'portrait':
                        manifestFrom = manifestFrom[:-4] + "_portrait.xml"
                    else:
                        manifestFrom = manifestFrom[:-4] + "_landscape.xml"

                    if not os.path.exists(manifestFrom):
                        manifestFrom = manifestFromTemp

                log_utils.info("The sdk manifest file is %s", manifestFrom)

                # merge into xml
                bRet = mergeManifest(channel, manifestTo, manifestFrom)
                if bRet:
                    log_utils.info("merge manifest file success.")
                else:
                    log_utils.error("merge manifest file failed.")
                    return 1

            elif child['type'] == 'copyRes':

                if child['from'] == None or child['to'] == None:
                    log_utils.error(
                        "the sdk config file error. 'copyRes' need 'from' and 'to'.sdk name:%s",
                        name)
                    return 1

                copyFrom = file_utils.getFullPath(os.path.join(sdkDir, child['from']))
                copyTo = file_utils.getFullPath(os.path.join(decompileDir, child['to']))

                if child['to'] == 'lib':
                    copyLibs(game, copyFrom, copyTo)
                else:
                    copyResToApk(copyFrom, copyTo)

            elif child['type'] == 'script' and pluginInfo != None:
                # now only third-plugin support script
                if child['from'] == None:
                    log_utils.error(
                        "the sdk config file is error. 'script' need 'from' attrib to specify script.py")
                    return 1

                scriptName = child['from']
                log_utils.info("now to execute plugin script. name:%s", scriptName)
                doScript(channel, pluginInfo, decompileDir, packageName, sdkDir, scriptName)

    return 0


def copyChannelResources(game, channel, decompileDir):
    """
        Copy channel resources to decompile folder. for example icon resources, assets and so on.
    """

    resPath = "games/" + game['appName'] + "/channels/" + channel['id']
    resPath = file_utils.getFullPath(resPath)
    if not os.path.exists(resPath):
        log_utils.warning("the channel %s special res path is not exists. %s", channel['id'],
                          resPath)
        return 0

    targetResPath = file_utils.getFullPath(decompileDir)

    assetsPath = os.path.join(resPath, 'assets')
    libsPath = os.path.join(resPath, 'libs')
    resourcePath = os.path.join(resPath, 'res')

    targetAssetsPath = os.path.join(decompileDir, 'assets')
    targetLibsPath = os.path.join(decompileDir, 'lib')
    targetResourcePath = os.path.join(decompileDir, 'res')

    copyResToApk(assetsPath, targetAssetsPath)
    copyResToApk(libsPath, targetLibsPath)
    copyResToApk(resourcePath, targetResourcePath)

    log_utils.info("copy channel %s special res to apk success.", channel['name'])

    return 0


def copySubChannelResources(game, channelID, decompileDir):
    """
        Copy channel resources to decompile folder. for example icon resources, assets and so on.
    """

    resPath = "games/" + game['appName'] + "/channels/" + channelID
    resPath = file_utils.getFullPath(resPath)
    if not os.path.exists(resPath):
        log_utils.warning("the channel %s special res path is not exists. %s", channelID, resPath)
        return 0

    targetResPath = file_utils.getFullPath(decompileDir)

    assetsPath = os.path.join(resPath, 'assets')
    libsPath = os.path.join(resPath, 'libs')
    resourcePath = os.path.join(resPath, 'res')

    targetAssetsPath = os.path.join(decompileDir, 'assets')
    targetLibsPath = os.path.join(decompileDir, 'lib')
    targetResourcePath = os.path.join(decompileDir, 'res')

    copyResToApk(assetsPath, targetAssetsPath)
    copyResToApk(libsPath, targetLibsPath)
    copyResToApk(resourcePath, targetResourcePath)

    log_utils.info("copy channel %s special res to apk success.", channelID)

    return 0


def copyAppResources(game, decompileDir):
    """
        Copy game res files to apk.
    """
    resPath = "games/" + game['appName'] + "/res"
    resPath = file_utils.getFullPath(resPath)
    if not os.path.exists(resPath):
        log_utils.warning("the game %s has no extra res folder", game['appName'])
        return

    assetsPath = os.path.join(resPath, 'assets')
    libsPath = os.path.join(resPath, 'libs')
    resourcePath = os.path.join(resPath, 'res')

    targetAssetsPath = os.path.join(decompileDir, 'assets')
    targetLibsPath = os.path.join(decompileDir, 'lib')
    targetResourcePath = os.path.join(decompileDir, 'res')

    copyResToApk(assetsPath, targetAssetsPath)
    copyResToApk(libsPath, targetLibsPath)
    copyResToApk(resourcePath, targetResourcePath)


def copyAppRootResources(game, decompileDir):
    """
        Copy game root files to apk. the files will be in the root path of apk
    """
    resPath = "games/" + game['appName'] + "/root"
    resPath = file_utils.getFullPath(resPath)

    if not os.path.exists(resPath):
        log_utils.info("the game %s has no root folder", game['appName'])
        return

    targetResPath = file_utils.getFullPath(decompileDir)
    copyResToApk(resPath, targetResPath)

    return


def mergeManifest(channel, targetManifest, sdkManifest):
    """
        Merge sdk SdkManifest.xml to the apk AndroidManifest.xml
    """

    if not os.path.exists(targetManifest) or not os.path.exists(sdkManifest):
        log_utils.error("the manifest file is not exists.targetManifest:%s;sdkManifest:%s",
                        targetManifest, sdkManifest)
        return False

    ET.register_namespace('android', androidNS)
    targetTree = ET.parse(targetManifest)
    targetRoot = targetTree.getroot()

    ET.register_namespace('android', androidNS)
    sdkTree = ET.parse(sdkManifest)
    sdkRoot = sdkTree.getroot()

    f = open(targetManifest)
    targetContent = f.read()
    f.close()

    permissionConfigNode = sdkRoot.find('permissionConfig')
    if permissionConfigNode != None and len(permissionConfigNode) > 0:
        for child in list(permissionConfigNode):
            key = '{' + androidNS + '}name'
            val = child.get(key)
            if val != None and len(val) > 0:
                attrIndex = targetContent.find(val)
                if -1 == attrIndex:
                    targetRoot.append(child)

    appConfigNode = sdkRoot.find('applicationConfig')
    appNode = targetRoot.find('application')

    if appConfigNode != None:

        proxyApplicationName = appConfigNode.get('proxyApplication')




        # exists = appKeyWord != None and len(appKeyWord.strip()) > 0 and targetContent.find(appKeyWord) != -1

        # if not exists:
        # remove keyword check...
        for child in list(appConfigNode):
            targetRoot.find('application').append(child)

    targetTree.write(targetManifest, 'UTF-8')

    return True


def copyResToApk(copyFrom, copyTo):
    """
        Copy two resource folders
    """

    if not os.path.exists(copyFrom):
        log_utils.error("the copyFrom %s is not exists.", copyFrom)
        return

    if not os.path.exists(copyTo):
        os.makedirs(copyTo)

    if os.path.isfile(copyFrom) and not mergeResXml(copyFrom, copyTo):
        file_utils.copy_file(copyFrom, copyTo)
        return

    for f in os.listdir(copyFrom):
        sourcefile = os.path.join(copyFrom, f)
        targetfile = os.path.join(copyTo, f)

        if os.path.isfile(sourcefile):
            if not os.path.exists(copyTo):
                os.makedirs(copyTo)

            if mergeResXml(sourcefile, targetfile):
                continue

            if os.path.exists(targetfile):
                file_utils.del_file_folder(targetfile)
                log_utils.warning("%s is exists. but note that this file be pasted by %s",
                                  targetfile, sourcefile)
            destfilestream = open(targetfile, 'wb')
            sourcefilestream = open(sourcefile, 'rb')
            destfilestream.write(sourcefilestream.read())
            destfilestream.close()
            sourcefilestream.close()

        if os.path.isdir(sourcefile):
            copyResToApk(sourcefile, targetfile)


def mergeResXml(copyFrom, copyTo):
    """
        Merge all android res xml
    """

    if not os.path.exists(copyTo):
        return False

    aryXml = ['strings.xml', 'styles.xml', 'colors.xml', 'dimens.xml', 'ids.xml', 'attrs.xml',
              'integers.xml', 'arrays.xml', 'bools.xml', 'drawables.xml', 'values.xml']
    basename = os.path.basename(copyFrom)

    if basename in aryXml:
        if config_utils.is_py_env_2():
            f = open(copyTo)
        else:
            f = open(copyTo, 'r', encoding='utf-8')
        targetContent = f.read()
        f.close()
        print("-------------->", copyFrom)
        fromTree = ET.parse(copyFrom)
        fromRoot = fromTree.getroot()
        toTree = ET.parse(copyTo)
        toRoot = toTree.getroot()
        for node in list(fromRoot):
            val = node.get('name')
            if val != None and len(val) > 0:
                valMatched = '"' + val + '"'
                attrIndex = targetContent.find(valMatched)
                if -1 == attrIndex:
                    toRoot.append(node)
                else:
                    log_utils.warning("The node %s is already exists in %s", val, basename)

        toTree.write(copyTo, 'UTF-8')
        return True
    return False





def addSplashScreen(workDir, channel, decompileDir):

    splashPath = file_utils.getSplashPath(channel["sdk"])
    targetFile = decompileDir + "/res/drawable-xxhdpi"
    file_utils.copy_files(splashPath,targetFile)


def replaceResources(resPath, projectPath):
    file_utils.copy_files(resPath,projectPath)

def renameSplashImage(copyFrom):
    print("######## " + copyFrom + " ####")
    if not os.path.exists(copyFrom):
        log_utils.error("the copyFrom %s is not exists.", copyFrom)
        return
    for f in os.listdir(copyFrom):
        ffile = os.path.join(copyFrom, f)
        if not os.path.exists(ffile):
            log_utils.error("the dir %s is not exists.", ffile)
            continue
        if not os.path.isdir(ffile):
            log_utils.error("it is not a dir " + ffile)
            continue
        for ff in os.listdir(ffile):
            sourcefile = os.path.join(ffile, ff)
            print("### " + sourcefile)
            if os.path.isfile(sourcefile):
                dirname = os.path.dirname(sourcefile)
                basename = os.path.basename(sourcefile)
                print("### " + dirname)
                print("### " + basename)
                if basename.endswith('.jpg'):
                    os.rename(sourcefile, dirname + "/channel_splash.jpg")
                else:
                    os.rename(sourcefile, dirname + "/channel_splash.png")


def removeStartActivity(decompileDir):
    manifestFile = decompileDir + "/AndroidManifest.xml"
    manifestFile = file_utils.getFullPath(manifestFile)
    ET.register_namespace('android', androidNS)
    key = '{' + androidNS + '}name'

    tree = ET.parse(manifestFile)
    root = tree.getroot()

    applicationNode = root.find('application')
    if applicationNode is None:
        return

    activityNodeLst = applicationNode.findall('activity')
    if activityNodeLst is None:
        return

    activityName = ''

    for activityNode in activityNodeLst:
        bMain = False
        intentNodeLst = activityNode.findall('intent-filter')
        if intentNodeLst is None:
            continue

        for intentNode in intentNodeLst:
            bFindAction = False
            bFindCategory = False

            actionNodeLst = intentNode.findall('action')
            if actionNodeLst is None:
                continue
            for actionNode in actionNodeLst:
                if actionNode.attrib[key] == 'android.intent.action.MAIN':
                    bFindAction = True
                    break

            categoryNodeLst = intentNode.findall('category')
            if categoryNodeLst is None:
                continue
            for categoryNode in categoryNodeLst:
                if categoryNode.attrib[key] == 'android.intent.category.LAUNCHER':
                    bFindCategory = True
                    break

            if bFindAction and bFindCategory:
                bMain = True
                intentNode.remove(actionNode)
                intentNode.remove(categoryNode)
                break

        if bMain:
            activityName = activityNode.attrib[key]
            break

    tree.write(manifestFile, 'UTF-8')
    return activityName


def appendSplashActivity(decompileDir, splashType):
    manifestFile = decompileDir + "/AndroidManifest.xml"
    manifestFile = file_utils.getFullPath(manifestFile)
    ET.register_namespace('android', androidNS)
    key = '{' + androidNS + '}name'
    screenkey = '{' + androidNS + '}screenOrientation'
    theme = '{' + androidNS + '}theme'
    tree = ET.parse(manifestFile)
    root = tree.getroot()

    applicationNode = root.find('application')
    if applicationNode is None:
        return






def handleThirdPlugins(workDir, decompileDir, game, channel, packageName):
    pluginsFolder = file_utils.getFullPath('config/plugin')
    gamePluginFolder = file_utils.getFullPath('games/' + game['appName'] + '/plugin')
    plugins = channel.get('third-plugins')

    if plugins == None or len(plugins) <= 0:
        log_utils.info("the channel %s has no supported plugins.", channel['sdk'])
        return 0

    # copy all resources to temp folder.
    for plugin in plugins:
        pluginName = plugin['name']
        pluginSourceFolder = os.path.join(pluginsFolder, pluginName).replace("\\","/")
        if not os.path.exists(pluginSourceFolder):
            log_utils.warning("the plugin %s config folder is not exists", pluginName)
            continue

        pluginTargetFolder = workDir + "/plugins/" + pluginName
        file_utils.copy_files(pluginSourceFolder, pluginTargetFolder)

        gamePluginSourceFolder = os.path.join(gamePluginFolder, pluginName).replace("\\","/")
        if not os.path.exists(gamePluginSourceFolder):
            log_utils.warning("the plugin %s is not configed in the game %s", pluginName,
                              game['appName'])
            continue

        file_utils.copy_files(gamePluginSourceFolder, pluginTargetFolder)

        if not os.path.exists(pluginSourceFolder + "/classes.dex"):
            jar2dex(pluginSourceFolder, pluginTargetFolder)

    # handle plugins
    smaliDir = os.path.join(decompileDir, "smali").replace("\\","/")
    pluginNum = 0
    for plugin in plugins:
        pluginName = plugin['name']
        pluginFolder = workDir + "/plugins/" + pluginName

        if not os.path.exists(pluginFolder):
            log_utils.warning("the plugin %s temp folder is not exists", pluginName)
            continue

        pluginDexFile = os.path.join(pluginFolder, "classes.dex").replace("\\","/")
        ret = dex2smali(pluginDexFile, smaliDir, "baksmali.jar")
        if ret:
            return 1

        ret = copyResource(game, channel, packageName, pluginFolder, decompileDir,
                           plugin['operations'], pluginName, plugin)
        if ret:
            return 1

        pluginNum += 1

    log_utils.info("Total plugin num:%s;success handle num:%s", str(len(plugins)), str(pluginNum))


def generateNewRFile(newPackageName, decompileDir, channel):
    """
        Use all new resources to generate the new R.java, and compile it ,then copy it to the target smali dir
    """

    ret = checkValueResources(decompileDir)

    if ret:
        return 1

    # ret = generateSdkRFile(decompileDir, channel)
    # if ret:
    #     return 1

    decompileDir = file_utils.getFullPath(decompileDir)
    tempPath = os.path.dirname(decompileDir)
    tempPath = tempPath + "/temp"
    log_utils.debug("generate R:the temp path is %s", tempPath)
    if os.path.exists(tempPath):
        file_utils.del_file_folder(tempPath)
    if not os.path.exists(tempPath):
        os.makedirs(tempPath)

    resPath = os.path.join(decompileDir, "res")
    targetResPath = os.path.join(tempPath, "res")
    file_utils.copy_files(resPath, targetResPath)

    genPath = os.path.join(tempPath, "gen")
    if not os.path.exists(genPath):
        os.makedirs(genPath)

    manifestPath = os.path.join(decompileDir, "AndroidManifest.xml")
    targetDexPath = os.path.join(tempPath, "classes.dex")

    ret = doGenerateR(decompileDir, targetResPath, manifestPath, genPath, targetDexPath,
                      newPackageName)
    if ret:
        return 1

    return copyExtraR(decompileDir, channel, newPackageName)


def copyExtraR(decompileDir, channel, newPackageName):
    """
        copy the new generated R.java to sdk extra package

        first:add a new param in sdk channel config <param name="extraR" value="the package need to generate R. em. com.facebook" />

        for those sdk which used R.*.* directly in code.


    """

    if "extraR" not in channel:
        log_utils.debug("the sdk %s has no extraR config. don't need to generate extra R.java",
                        channel['sdk'])
        return 0

    log_utils.debug("sdk %s need to generate extra R.java. package names:%s", channel['sdk'],
                    channel['extraR'])

    newPackageNames = channel['extraR'].split(",")

    ret = 0

    decompileDir = file_utils.getFullPath(decompileDir)
    sdkPath = os.path.dirname(decompileDir) + "/sdk"
    resPath = os.path.join(sdkPath, channel['sdk'] + "/res")

    tempPath = os.path.dirname(decompileDir)
    tempPath = tempPath + "/temp"
    genPath = os.path.join(tempPath, "gen")

    rPath = newPackageName.replace('.', '/')
    rPath = os.path.join(genPath, rPath)
    rPath = os.path.join(rPath, "R.java")

    if not os.path.exists(rPath):
        log_utils.error("copy extra R failed. the R.java is not exists:%s", rPath)
        return 1

    for k in range(len(newPackageNames)):

        packageName = newPackageNames[k]

        tempPath = os.path.join(sdkPath, 'extraTemp' + str(k))
        log_utils.debug("generate sdk R: the temp path is %s", tempPath)
        if os.path.exists(tempPath):
            file_utils.del_file_folder(tempPath)

        if not os.path.exists(tempPath):
            os.makedirs(tempPath)

        targetResPath = os.path.join(tempPath, "res")
        file_utils.copy_files(resPath, targetResPath)

        genPath = os.path.join(tempPath, "gen")
        if not os.path.exists(genPath):
            os.makedirs(genPath)

        trPath = packageName.replace('.', '/')
        trPath = os.path.join(genPath, trPath)
        if not os.path.exists(trPath):
            os.makedirs(trPath)

        targetRPath = os.path.join(trPath, "R.java")

        file_utils.copy_file(rPath, targetRPath)

        file_utils.modifyFileContent(targetRPath, newPackageName, packageName)

        cmd = '"%sjavac" -source 1.7 -target 1.7 -encoding UTF-8 "%s"' % (
            file_utils.getJavaBinDir(), targetRPath)
        ret = file_utils.execFormatCmd(cmd)
        if ret:
            return 1

        dexToolPath = file_utils.getFullToolPath("/lib/dx.jar")

        heapSize = config_utils.getJDKHeapSize()
        targetDexPath = os.path.join(tempPath, "classes.dex")
        cmd = file_utils.getJavaCMD() + ' -jar -Xmx%sm -Xms%sm "%s" --dex --output="%s" "%s"' % (
            heapSize, heapSize, dexToolPath, targetDexPath, genPath)

        ret = file_utils.execFormatCmd(cmd)
        if ret:
            return 1

        smaliPath = os.path.join(decompileDir, "smali")
        ret = dex2smali(targetDexPath, smaliPath, "baksmali.jar")

    return 0


def generateSdkRFile(decompileDir, channel):
    """
        generate sdk extra R.java

        1、add a new param in sdk channel config <param name="extraR" value="com.facebook" />

        NOT USED

    """

    if "extraR" not in channel:
        log_utils.debug("the sdk %s has no extraR config. don't need to generate extra R.java",
                        channel['sdk'])
        return 0

    log_utils.debug("sdk %s need to generate extra R.java. package names:%s", channel['sdk'],
                    channel['extraR'])

    newPackageNames = channel['extraR'].split(",")

    ret = 0

    decompileDir = file_utils.getFullPath(decompileDir)
    sdkPath = os.path.dirname(decompileDir) + "/sdk"
    resPath = os.path.join(sdkPath, channel['sdk'] + "/res")

    manifestPath = os.path.join(sdkPath, channel['sdk'] + '/AndroidManifest.xml')

    if not os.path.exists(manifestPath):
        log_utils.error(
            "the AndroidManifest.xml is not exists in sdk %s folder, generate a temp AndroidManifest.xml",
            channel['sdk'])
        fp = open(manifestPath, "w")
        fp.write(
            '<?xml version="1.0" encoding="utf-8"?>\n<manifest xmlns:android="http://schemas.android.com/apk/res/android"\n\tpackage="com.tt19.fuse.sdk" >\n</manifest>')
        fp.close()

    for k in range(len(newPackageNames)):

        newPackageName = newPackageNames[k]

        tempPath = os.path.join(sdkPath, 'extraTemp' + str(k))
        log_utils.debug("generate sdk R: the temp path is %s", tempPath)
        if os.path.exists(tempPath):
            file_utils.del_file_folder(tempPath)

        if not os.path.exists(tempPath):
            os.makedirs(tempPath)

        targetResPath = os.path.join(tempPath, "res")
        file_utils.copy_files(resPath, targetResPath)

        genPath = os.path.join(tempPath, "gen")
        if not os.path.exists(genPath):
            os.makedirs(genPath)

        ET.register_namespace('android', androidNS)
        tree = ET.parse(manifestPath)
        root = tree.getroot()
        root.attrib['package'] = newPackageName
        tree.write(manifestPath, 'UTF-8')

        targetDexPath = os.path.join(tempPath, "classes.dex")

        ret = doGenerateR(decompileDir, targetResPath, manifestPath, genPath, targetDexPath,
                          newPackageName)

        if ret:
            return 1

    return 0


def doGenerateR(decompileDir, resPath, manifestPath, genPath, targetDexPath, newPackageName):
    """
        generate R.java for the newPackageName
    """

    if not os.path.exists(resPath):
        log_utils.debug("%s not exists ", resPath)
        return 1

    aaptPath = file_utils.getFullToolPath("aapt")

    androidPath = file_utils.getFullToolPath("android.jar")
    cmd = '"%s" p -f -m -J "%s" -S "%s" -I "%s" -M "%s"' % (
        aaptPath, genPath, resPath, androidPath, manifestPath)
    ret = file_utils.execFormatCmd(cmd)
    if ret:
        return 1

    rPath = newPackageName.replace('.', '/')
    rPath = os.path.join(genPath, rPath)
    rPath = os.path.join(rPath, "R.java")

    cmd = '"%sjavac" -source 1.7 -target 1.7 -encoding UTF-8 "%s"' % (
        file_utils.getJavaBinDir(), rPath)
    ret = file_utils.execFormatCmd(cmd)
    if ret:
        return 1

    dexToolPath = file_utils.getFullToolPath("/lib/dx.jar")

    heapSize = config_utils.getJDKHeapSize()
    cmd = file_utils.getJavaCMD() + ' -jar -Xmx%sm -Xms%sm "%s" --dex --output="%s" "%s"' % (
        heapSize, heapSize, dexToolPath, targetDexPath, genPath)

    ret = file_utils.execFormatCmd(cmd)
    if ret:
        return 1

    smaliPath = os.path.join(decompileDir, "smali")
    ret = dex2smali(targetDexPath, smaliPath, "baksmali.jar")

    return ret


def writeDevelopInfo(game, channel, decompileDir):
    developConfigFile = os.path.join(decompileDir, "assets")
    developConfigFile = developConfigFile.replace("\\", "/")
    if not os.path.exists(developConfigFile):
        os.makedirs(developConfigFile)

    channelConfigFile = os.path.join(developConfigFile, "channel.properties").replace("\\", "/")
    config_utils.writeDeveloperProperties(game, channel, channelConfigFile)





def writePluginInfo(channel, decompileDir):
    developConfigFile = os.path.join(decompileDir, "assets")
    if not os.path.exists(developConfigFile):
        os.makedirs(developConfigFile)

    developConfigFile = os.path.join(developConfigFile, "plugin.properties").replace("\\","/")
    config_utils.writePluginConfigs(channel, developConfigFile)


def writeManifestMetaInfo(channel, decompileDir):
    manifestFile = decompileDir + "/AndroidManifest.xml"
    manifestFile = file_utils.getFullPath(manifestFile)
    ET.register_namespace('android', androidNS)
    tree = ET.parse(manifestFile)
    root = tree.getroot()

    key = '{' + androidNS + '}name'
    val = '{' + androidNS + '}value'

    appNode = root.find('application')
    if appNode is None:
        return

    metaDataList = appNode.findall('meta-data')

    if metaDataList != None:
        for metaDataNode in metaDataList:
            keyName = metaDataNode.attrib[key]
            for child in channel['params']:
                if keyName == child['name'] and child['bWriteInManifest'] == '1':
                    log_utils.warning("the meta-data node %s repeated. remove it .", keyName)
                    appNode.remove(metaDataNode)

            if 'third-plugins' in channel and channel['third-plugins'] != None and len(
                    channel['third-plugins']) > 0:

                for cPlugin in channel['third-plugins']:
                    if 'params' in cPlugin and cPlugin['params'] != None and len(
                            cPlugin['params']) > 0:
                        for child in cPlugin['params']:
                            if keyName == child['name'] and child['bWriteInManifest'] == '1':
                                log_utils.warning("the meta-data node %s repeated. remove it .",
                                                  keyName)
                                appNode.remove(metaDataNode)

    existKeys = dict()
    for child in channel['params']:
        if child['bWriteInManifest'] != None and child['bWriteInManifest'] == '1':

            keyName = child['name']
            keyVal = child['value']
            if keyName in existKeys:
                log_utils.warning("the meta-data node %s repeated. exists value:%s; new value:%s",
                                  keyName, existKeys[keyName], keyVal)
                continue

            metaNode = SubElement(appNode, 'meta-data')
            metaNode.set(key, keyName)
            metaNode.set(val, keyVal)
            existKeys[keyName] = keyVal

    if 'third-plugins' in channel and channel['third-plugins'] != None and len(
            channel['third-plugins']) > 0:

        for cPlugin in channel['third-plugins']:
            if 'params' in cPlugin and cPlugin['params'] != None and len(cPlugin['params']) > 0:
                for child in cPlugin['params']:
                    if child['bWriteInManifest'] != None and child['bWriteInManifest'] == '1':

                        keyName = child['name']
                        keyVal = child['value']

                        if keyName in existKeys:
                            log_utils.warning(
                                "the meta-data node %s repeated. exists value:%s; new value:%s",
                                keyName, existKeys[keyName], keyVal)
                            continue

                        metaNode = SubElement(appNode, 'meta-data')
                        metaNode.set(key, child['name'])
                        metaNode.set(val, child['value'])
                        existKeys[keyName] = keyVal


        # log_utils.info(ET.tostring(root,encoding="us-ascii", method="text"))

    tree.write(manifestFile, 'UTF-8')

    log_utils.info("The manifestFile meta-data write successfully")


def doScript(channel, pluginInfo, decompileDir, packageName, sdkTempDir, scriptName):
    if scriptName != 'script.py':
        log_utils.error("the script file name must be script.py")
        return 1

    sdkScript = os.path.join(sdkTempDir, scriptName)

    if not os.path.exists(sdkScript):
        return 0

    sys.path.append(sdkTempDir)

    import script
    ret = script.execute(channel, pluginInfo, decompileDir, packageName)
    del sys.modules['script']
    sys.path.remove(sdkTempDir)

    return ret


def doSDKScript(channel, decompileDir, packageName, sdkTempDir):
    sdkScript = os.path.join(sdkTempDir, "sdk_script.py").replace("\\","/")

    if not os.path.exists(sdkScript):
        return 0

    sys.path.append(sdkTempDir)

    import sdk_script
    ret = sdk_script.execute(channel, decompileDir, packageName)
    del sys.modules['sdk_script']
    sys.path.remove(sdkTempDir)

    return ret


def doGamePostScript(game, channel, decompileDir, packageName):
    scriptDir = file_utils.getFullPath("games/" + game['appName'] + "/scripts")

    if not os.path.exists(scriptDir):
        log_utils.info(
            "the game post script is not exists. if you have some specail logic, you can do it in games/[yourgame]/scripts/post_script.py")
        return 0

    sdkScript = os.path.join(scriptDir, "post_script.py")

    if not os.path.exists(sdkScript):
        log_utils.info(
            "the game post script is not exists. if you have some specail logic, you can do it in games/[yourgame]/scripts/post_script.py")
        return 0

    sys.path.append(scriptDir)

    import post_script

    log_utils.info("now to execute post_script.py of game %s ", game['appName'])
    ret = post_script.execute(game, channel, decompileDir, packageName)
    del sys.modules['post_script']
    sys.path.remove(scriptDir)

    return ret


def checkValueResources(decompileDir):
    resPath = decompileDir + '/res'
    if not os.path.exists(resPath):
        return 0

    removeDuplcatedValResources(resPath, "values")

    removeDuplcatedValResources(resPath, "values-large")

    for k in range(9, 30):
        removeDuplcatedValResources(resPath, "values-v" + str(k))

    # for k in range(24, 30):
    #     valDir = os.path.join(resPath, "values-v"+str(k))
    #     if os.path.exists(valDir):
    #         file_utils.del_file_folder(valDir)

    # check drawable resource
    ldpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-ldpi')
    mdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-mdpi')
    hdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-hdpi')
    xhdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-xhdpi')
    xxhdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-xxhdpi')
    xxxhdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-xxxhdpi')

    removeDuplicateDrawableRes(ldpiPath, ldpiPath + "-v4")
    removeDuplicateDrawableRes(mdpiPath, mdpiPath + "-v4")
    removeDuplicateDrawableRes(hdpiPath, hdpiPath + "-v4")
    removeDuplicateDrawableRes(xhdpiPath, xhdpiPath + "-v4")
    removeDuplicateDrawableRes(xxhdpiPath, xxhdpiPath + "-v4")
    removeDuplicateDrawableRes(xxxhdpiPath, xxxhdpiPath + "-v4")

    return 0


def removeDuplcatedValResources(resDir, valFolder):
    valDir = os.path.join(resDir, valFolder)
    valDirV4 = os.path.join(resDir, valFolder + "-v4")
    if os.path.exists(valDirV4):
        tempFiles = []
        file_utils.list_files(valDirV4, tempFiles, [])
        for f in tempFiles:
            if mergeResXml(f, os.path.join(valDir, os.path.basename(f))):
                file_utils.del_file_folder(f)

    # end check -v4

    valFiles = []

    if os.path.exists(valDir):
        file_utils.list_files(valDir, valFiles, [])

    if not valFiles or len(valFiles) <= 0:
        # log_utils.debug("there no files in %s", resDir+"/"+valFolder)
        return 0

    names = ['string', 'style', 'color', 'dimen']
    targetFiles = {}
    existRes = {}

    for name in names:
        if name not in existRes:
            existRes[name] = {}

        for f in valFiles:

            # log_utils.debug("now to handle %s", f)

            if not isTargetResFile(f, name):
                # log_utils.debug("the %s is not contain %s", f, name)
                continue

            # log_utils.debug("now to handle %s", f)
            if f in targetFiles:
                tree = targetFiles[f]
            else:
                tree = ET.parse(f)
                targetFiles[f] = tree

            root = tree.getroot()
            for node in list(root):
                item = {}
                attribName = node.attrib.get('name')
                if attribName is None:
                    continue

                tag = node.tag
                nodeName = tag + "_" + attribName
                val = node.text
                existItem = existRes[name].get(nodeName)

                if existItem is not None:
                    resVal = existItem.get('value')
                    log_utils.warning("node %s duplicated!!! the val is %s;the newVal is %s",
                                      attribName, val, resVal)
                    root.remove(node)

                item['file'] = f
                item['name'] = nodeName
                item['value'] = val
                existRes[name][nodeName] = item

                # targetFiles[f] = tree

    for f in targetFiles.keys():
        # log_utils.debug("write val file:%s", f)
        targetFiles[f].write(f, "UTF-8")

    return 0


def changeAppNameInResString(decompileDir, appName):
    """
        修改strings.xml中的appName
    """

    stringFile = decompileDir + "/res/values/strings.xml"
    if not os.path.exists(stringFile):
        return False

    tree = ET.parse(stringFile)
    root = tree.getroot()

    for node in list(root):
        name = node.attrib.get('name')
        if name == 'app_name':
            node.text = appName
            tree.write(stringFile, "UTF-8")
            return True

    return False


def isTargetResFile(resFile, tagName):
    """
        resFile contain tagName?
        use this to check the resFile whether or not strings.xml, colors.xml, dimens.xml...
    """

    if os.path.splitext(resFile)[1] != '.xml':
        return False

    if not os.path.exists(resFile):
        return False

    # ET.register_namespace('ns1', "http://schemas.android.com/tools")
    resTree = ET.parse(resFile)
    root = resTree.getroot()

    # log_utils.debug("root.tag:"+root.tag+" to compare "+tagName)

    if root.tag != 'resources':
        return False

    # log_utils.debug("root len:"+str(len(root)))
    for node in list(root):
        # log_utils.debug("root.node:"+node.tag)
        if node.tag == tagName:
            return True

    return False


def checkValueResourcesDeplecated(decompileDir):
    valXmls = ['strings.xml', 'styles.xml', 'colors.xml', 'dimens.xml', 'ids.xml', 'attrs.xml',
               'integers.xml', 'arrays.xml', 'bools.xml', 'drawables.xml', 'public.xml']

    resDir = decompileDir + '/res/values'
    existsStrs = {}
    stringsXml = resDir + '/strings.xml'
    if os.path.exists(stringsXml):
        stringTree = ET.parse(stringsXml)
        root = stringTree.getroot()
        for node in list(root):
            stringItem = {}
            name = node.attrib.get('name')
            val = node.text
            stringItem['file'] = stringsXml
            stringItem['name'] = name
            stringItem['value'] = val
            existsStrs[name] = stringItem

    existsColors = {}
    colorsXml = resDir + 'colors.xml'
    if os.path.exists(colorsXml):
        colorTree = ET.parse(colorsXml)
        root = colorTree.getroot()
        for node in list(root):
            colorItem = {}
            name = node.attrib.get('name')
            val = node.text.lower()
            colorItem['file'] = colorsXml
            colorItem['name'] = name
            colorItem['value'] = val
            existsColors[name] = colorItem

    valueFiles = {}
    for filename in os.listdir(resDir):
        if filename in valXmls:
            continue

        srcFile = os.path.join(resDir, filename)
        if os.path.splitext(srcFile)[1] != '.xml':
            continue
        tree = ET.parse(srcFile)
        root = tree.getroot()
        if root.tag != 'resources':
            continue

        for node in list(root):
            dictRes = None
            if node.tag == 'string':
                dictRes = existsStrs
            elif node.tag == 'color':
                dictRes = existsColors
            else:
                continue

            name = node.attrib.get('name')
            val = node.text

            if name is None:
                continue

            resItem = dictRes.get(name)
            if resItem is not None:
                resVal = resItem.get('value')
                log_utils.warning("node %s duplicated!!! the val is %s;the newVal is %s", name, val,
                                  resVal)
                root.remove(node)

            else:
                valItem = {}
                valItem['file'] = srcFile
                valItem['name'] = name
                valItem['value'] = val
                dictRes[name] = valItem

        valueFiles[srcFile] = tree

    for valFile in valueFiles.keys():
        log_utils.debug("save:" + valFile)
        valueFiles[valFile].write(valFile, 'UTF-8')

    # check drawable resource
    nodpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-nodpi')
    ldpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-ldpi')
    mdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-mdpi')
    hdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-hdpi')
    xhdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-xhdpi')
    xxhdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-xxhdpi')
    xxxhdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-xxxhdpi')

    removeDuplicateDrawableRes(nodpiPath, nodpiPath + "-v4")
    removeDuplicateDrawableRes(ldpiPath, ldpiPath + "-v4")
    removeDuplicateDrawableRes(mdpiPath, mdpiPath + "-v4")
    removeDuplicateDrawableRes(hdpiPath, hdpiPath + "-v4")
    removeDuplicateDrawableRes(xhdpiPath, xhdpiPath + "-v4")
    removeDuplicateDrawableRes(xxhdpiPath, xxhdpiPath + "-v4")
    removeDuplicateDrawableRes(xxxhdpiPath, xxxhdpiPath + "-v4")

    return 0


def removeDuplicateDrawableRes(path1, path2):
    if not os.path.exists(path1) or not os.path.exists(path2):
        return

    duplicatedFiles = []

    for f1 in os.listdir(path1):
        for f2 in os.listdir(path2):
            if f1 == f2:
                log_utils.warning("%s duplicated", os.path.join(path2, f2))
                duplicatedFiles.append(os.path.join(path2, f2))
                break

    for d in duplicatedFiles:
        os.remove(d)


def getAppIconName(decompileDir):
    """
        从AndroidManifest.xml中获取游戏图标的名称
    """

    manifestFile = decompileDir + "/AndroidManifest.xml"
    manifestFile = file_utils.getFullPath(manifestFile)
    ET.register_namespace('android', androidNS)
    tree = ET.parse(manifestFile)
    root = tree.getroot()

    applicationNode = root.find('application')
    if applicationNode is None:
        return "ic_launcher"

    key = '{' + androidNS + '}icon'
    iconName = applicationNode.get(key)
    newIconName = "tt_app_icon"
    if iconName is None:
        return newIconName

    applicationNode.attrib[key] = "@drawable/"+newIconName
    tree.write(manifestFile, 'UTF-8')
    return newIconName


def appendChannelIconMark(gameIconPath, decompileDir):
    """
        自动给游戏图标加上渠道SDK的角标
        没有角标，生成没有角标的ICON
    """
    # gameIconPath = file_utils.getFullPath(gameIconPath)


    rlImg = Image.open(gameIconPath)


    xxhdpiSize = (512, 512)



    xxhdpiIcon = rlImg.resize(xxhdpiSize, Image.ANTIALIAS)



    xxhdpiPath = file_utils.getFullPath(decompileDir + '/res/drawable-xxhdpi')



    if not os.path.exists(xxhdpiPath):
        os.makedirs(xxhdpiPath)


    gameIconName = getAppIconName(decompileDir) + '.png'


    xxhdpiIcon.save(os.path.join(xxhdpiPath, gameIconName), 'PNG')
    if os.path.exists(xxhdpiPath + "-v4"):
        xxhdpiIcon.save(os.path.join(xxhdpiPath + "-v4", gameIconName), 'PNG')


    return 0


def checkCpuSupport(game, decompileDir):
    print("now to check cpu support...")

    isfilter = ("cpuSupport" in game) and len(game["cpuSupport"]) > 0

    filters = None
    if isfilter:
        filters = game["cpuSupport"].split('|')
        print(filters)

        for f in os.listdir(os.path.join(decompileDir, 'lib')):
            if f not in filters:
                file_utils.del_file_folder(os.path.join(decompileDir, 'lib/' + f))

    # make sure so in armeabi and armeabi-v7a is same
    armeabiPath = os.path.join(decompileDir, 'lib/armeabi')
    armeabiv7aPath = os.path.join(decompileDir, 'lib/armeabi-v7a')

    if os.path.exists(armeabiPath) and os.path.exists(armeabiv7aPath):

        for f in os.listdir(armeabiPath):
            fv7 = os.path.join(armeabiv7aPath, f)
            if not os.path.exists(fv7):
                shutil.copy2(os.path.join(armeabiPath, f), fv7)

        for fv7 in os.listdir(armeabiv7aPath):
            f = os.path.join(armeabiPath, fv7)
            if not os.path.exists(f):
                shutil.copy2(os.path.join(armeabiv7aPath, fv7), f)


def modifyGameName(gameName, decompileDir):
    """
        修改当前渠道的游戏名称,如果某个渠道的游戏名称特殊，可以配置gameName来指定。默认就是母包中游戏的名称
    """

    manifestFile = decompileDir + "/AndroidManifest.xml"
    manifestFile = file_utils.getFullPath(manifestFile)
    ET.register_namespace('android', androidNS)
    tree = ET.parse(manifestFile)
    root = tree.getroot()

    labelKey = '{' + androidNS + '}label'
    applicationNode = root.find('application')

    labelName = applicationNode.get(labelKey)

    log_utils.debug("the lable name is :%s", labelName)

    if labelName is not None and labelName == "@string/app_name" and changeAppNameInResString(
            decompileDir, gameName):
        log_utils.debug("app_name exists and handle successfully")
        pass
    else:
        log_utils.debug("app_name not exists. replace appName directly")
        applicationNode.set(labelKey, gameName)

    log_utils.info("the new game name is " + gameName)
    tree.write(manifestFile, 'UTF-8')





def checkApkForTTSDK(workDir, decompileDir):

    ret = 0
    log_utils.info("now to check the base.apk is correct?")

    manifestFile = decompileDir + "/AndroidManifest.xml"
    manifestFile = file_utils.getFullPath(manifestFile)
    ET.register_namespace('android', androidNS)
    tree = ET.parse(manifestFile)
    root = tree.getroot()

    key = '{' + androidNS + '}name'
    applicationNode = root.find('application')

    name = applicationNode.get(key)
    if not name or name == "":
        log_utils.error(
            "the android:name in application element must be 'com.tt19.fuse.TTApplication'. now change it to com.tt19.fuse.TTApplication, but maybe something will be wrong .")
        applicationNode.set(key, 'com.tt19.fuse.TTApplication')
        tree.write(manifestFile, 'UTF-8')

    smaliName = file_utils.getFullPath(decompileDir + "/smali/com/tt19/fuse/TTSdk.smali")




    return ret


def checkMultiDexJar(workDir, decompileDir):
    log_utils.debug("now to check multidex jar ... ")

    smaliPath = decompileDir + "/smali"

    multidexFilePath = file_utils.getFullPath(
        smaliPath + "/android/support/multidex/MultiDex.smali")



def splitDex(workDir, decompileDir):
    """
        如果函数上限超过限制，自动拆分smali，以便生成多个dex文件
    """

    log_utils.debug("now to check split dex ... ")

    smaliPath = decompileDir + "/smali"

    ##check multidex in mother apk

    for k in range(2, 20):
        tempPath = decompileDir + "/smali_classes" + str(k)
        if os.path.exists(tempPath):
            file_utils.copy_files(tempPath, smaliPath)
            file_utils.del_file_folder(tempPath)

    multidexFilePath = file_utils.getFullPath(
        smaliPath + "/android/support/multidex/MultiDex.smali")


    allFiles = []
    allFiles = file_utils.list_files(decompileDir, allFiles, [])

    maxFuncNum = 65535
    currFucNum = 0
    totalFucNum = 0

    currDexIndex = 1

    allRefs = dict()

    # 保证TTApplication等类在第一个classex.dex文件中
    for f in allFiles:
        f = f.replace("\\", "/")
        if "/com/tt19/fuse" in f or "/android/support/multidex" in f:
            currFucNum = currFucNum + smali_utils2.get_smali_method_count(f, allRefs)

    totalFucNum = currFucNum
    for f in allFiles:

        f = f.replace("\\", "/")
        if not f.endswith(".smali"):
            continue

        if "/com/tt19/fuse" in f or "/android/support/multidex" in f:
            continue

        thisFucNum = smali_utils2.get_smali_method_count(f, allRefs)
        totalFucNum = totalFucNum + thisFucNum
        if currFucNum + thisFucNum >= maxFuncNum:
            currFucNum = thisFucNum
            currDexIndex = currDexIndex + 1
            newDexPath = os.path.join(decompileDir, "smali_classes" + str(currDexIndex))
            if os.path.exists(newDexPath):
                file_utils.del_file_folder(newDexPath)

            os.makedirs(newDexPath)

        else:
            currFucNum = currFucNum + thisFucNum

        if currDexIndex > 1:
            targetPath = f[0:len(decompileDir)] + "/smali_classes" + str(currDexIndex) + f[len(
                smaliPath):]
            file_utils.copy_file(f, targetPath)
            file_utils.del_file_folder(f)

    log_utils.info("the total func num:" + str(totalFucNum))
    log_utils.info("split dex success. the classes.dex num:" + str(currDexIndex))


def writeLogConfig(game, decompileDir):
    """
        将日志参数写入到meta-data中
    """

    if 'log' not in game:
        log_utils.warning("the log config is not in games.xml of game: %s", game['appName'])
        return

    manifestFile = decompileDir + "/AndroidManifest.xml"
    manifestFile = file_utils.getFullPath(manifestFile)
    ET.register_namespace('android', androidNS)
    tree = ET.parse(manifestFile)
    root = tree.getroot()

    key = '{' + androidNS + '}name'
    val = '{' + androidNS + '}value'
    appNode = root.find('application')

    metaDataList = appNode.findall('meta-data')

    if metaDataList and len(metaDataList) > 0:

        for metaDataNode in metaDataList:
            keyName = metaDataNode.attrib[key]
            for lKey in game['log']:
                if keyName == lKey:
                    log_utils.warning("the meta-data node %s repeated. remove it .", keyName)
                    appNode.remove(metaDataNode)

    log_utils.info("-----------log config--------------")

    for lKey in game['log']:
        metaNode = SubElement(appNode, 'meta-data')
        metaNode.set(key, lKey)
        metaNode.set(val, game['log'][lKey])
        log_utils.info("%s = %s", lKey, game['log'][lKey])

    tree.write(manifestFile, 'UTF-8')

    log_utils.info("-----------log config--------------\n")


def getCompressRegx(game):
    """
        如果游戏有部分文件或者后缀名文件，在apktool重新打包的时候，没有被压缩，
        可以配置在打包工具/games/当前游戏/compress.txt文件中，加上需要压缩的文件或者文件后缀

    """

    result = list()
    compressFilePath = file_utils.getFullPath("games/" + game['appName'] + "/compress.txt")
    if not os.path.exists(compressFilePath):
        log_utils.debug("no need handle special compress. the compress.txt file is not exists:%s",
                        compressFilePath)
        return result

    f = open(compressFilePath, 'r')
    lines = f.readlines()
    f.close()

    for line in lines:

        if line.startswith('-'):
            line = line[1:].strip()

        if line.startswith("."):
            result.append(line[1:].strip())
        else:
            if line is not '' and line is not None:
                result.append(line.strip())

    return result


def modifyYml(versionName, versionCode, decompileDir):
    """
        修改apktool.yml 文件中的versionName,versionCode
    """
    ymlPath = file_utils.getFullPath(decompileDir + "/apktool.yml")
    if not os.path.exists(ymlPath):
        log_utils.warning("the apktool.yml is not exists in " + decompileDir)
        return

    ymlFile = open(ymlPath, 'r')
    lines = ymlFile.readlines()
    ymlFile.close()

    newLines = []
    for line in lines:
            if 'versionCode' in line and versionCode is not None:
                newLines.append("  versionCode: '" + versionCode + "'\n")
            elif 'versionName' in line and versionName is not None:
                newLines.append("  versionName: " + versionName + "\n")
            else:
                # 不去掉资源加载
                # if 'assets' not in line:
                    newLines.append(line)

    content = ''
    for line in newLines:
        content = content + line

    ymlFile = open(ymlPath, 'w')
    ymlFile.write(content)
    ymlFile.close()


def getOutputApkName(game, channel, packageName, decompileDir):
    """
        获取输出的最终apk包名
    """

    channelName = channel['sdk']
    channelName = channelName.replace(' ', '')
    if "outputApkName" not in game:
        apkName = channelName + '-' + time.strftime('%Y%m%d%H')
        if 'signApk' in channel and channel['signApk'] == '0':
            apkName = apkName + '_unsigned'

        return apkName + '.apk'

    formatStr = game['outputApkName']
    log_utils.debug("the output apk format string is " + formatStr)

    formatStr = formatStr.replace('{bundleID}', packageName)
    formatStr = formatStr.replace('{time}', time.strftime('%Y%m%d%H%M%S'))
    formatStr = formatStr.replace('{channelID}', channel['id'])
    formatStr = formatStr.replace('{channelName}', channelName)
    formatStr = formatStr.replace('{appName}', game['appName'])
    formatStr = formatStr.replace('{appID}', game['appID'])

    versionCode = getVersionCode(game, decompileDir)
    versionName = getVersionName(game, decompileDir)
    log_utils.debug("the versionCode is " + versionCode + ";versionName is " + versionName)

    formatStr = formatStr.replace('{versionCode}', versionCode)
    formatStr = formatStr.replace('{versionName}', versionName)

    apkName = formatStr
    if 'signApk' in channel and channel['signApk'] == '0':
        fname, fext = os.path.splitext(formatStr)
        apkName = fname + '_unsigned' + fext

    return apkName


def getVersionCode(game, decompileDir):
    """
        获取AndroidManifest.xml中设置的versionCode
    """

    if "versionCode" in game:
        return game['versionCode']

    ymlPath = file_utils.getFullPath(decompileDir + "/apktool.yml")
    if not os.path.exists(ymlPath):
        log_utils.warning("the apktool.yml is not exists in " + decompileDir)
        return "0"

    ymlFile = open(ymlPath, 'r')
    lines = ymlFile.readlines()
    ymlFile.close()

    for line in lines:
        if 'versionCode' in line:
            # versionCode: '15'
            return line.replace('versionCode:', '').strip().replace("'", "")

    return "0"


def getVersionName(game, decompileDir):
    """
        获取AndroidManifest.xml中设置的versionName
    """

    if "versionName" in game:
        return game['versionName']

    ymlPath = file_utils.getFullPath(decompileDir + "/apktool.yml")
    if not os.path.exists(ymlPath):
        log_utils.warning("the apktool.yml is not exists in " + decompileDir)
        return "0"

    ymlFile = open(ymlPath, 'r')
    lines = ymlFile.readlines()
    ymlFile.close()

    for line in lines:
        if 'versionName' in line:
            # versionCode: '15'
            return line.replace('versionName:', '').strip().replace("'", "")

    return "0"
