package org.jeecg.modules.game.service;

import org.jeecg.modules.game.dto.SdkConfDto;
import org.jeecg.modules.game.dto.SdkIosConfDto;
import org.jeecg.modules.game.dto.SdkStartDataDto;
import org.jeecg.modules.game.vo.PrivacyVo;
import org.jeecg.modules.game.vo.SdkConfRes;
import org.jeecg.modules.game.vo.SdkIosConfRes;

/**
 * @Author lili
 * @Description sdk游戏
 * @Date 2023-03-29
 **/
public interface SdkGameService {

    /**
     * @return org.jeecg.modules.game.vo.SdkConfRes
     * @Author lili
     * @Description 安卓sdk初始化
     * @Date 13:32 2023/3/29
     **/
    SdkConfRes conf(SdkConfDto sdkConfDto);

    /**
     * @return org.jeecg.modules.game.vo.SdkConfRes
     * @Author lili
     * @Description iossdk初始化
     * @Date 13:32 2023/3/29
     **/
    SdkIosConfRes iosConf(SdkIosConfDto sdkIosConfDto);

    /**
     * @param sdkEventDto
     * @Author lili
     * @Description 激活
     * @Date 18:50 2023/3/29
     **/
    void start(SdkStartDataDto sdkEventDto);

    /**
     * @param pkgId
     * @return java.lang.String
     * @Author lili
     * @Description 获取隐私政策
     * @Date 11:12 2023/4/7
     **/
    PrivacyVo getPrivacy(Integer subGameId, Integer pkgId);

}
