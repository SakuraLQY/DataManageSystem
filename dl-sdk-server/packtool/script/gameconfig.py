# -*- coding: utf-8 -*-


import config_utils
import sys
import log_utils
import pack

def entryGame():
    games = config_utils.getAllGames()
    print(u"################################################################")
    print(u"\t%-20s%-20s%-20s\n" % ("AppId", u"AppName", u"描述"))
    if games != None and len(games) > 0:
        for ch in games:
            print(u"\t%-20s%-20s%-20s" % (ch['appId'], ch['appName'], ch['appDesc']))
    print("")
    selectedGameID = input(u"请选择一个游戏(输入appId)：")
    game = getGameByAppID(selectedGameID, games)
    log_utils.info("current selected game is %s(%s)", game['appName'], game['appDesc'])
    pack.main(game)

def getGameByAppID(appId, games):
    if games == None or len(games) <= 0:
        return None
    for game in games:
        if game['appId'] == appId:
            return game
    return None
