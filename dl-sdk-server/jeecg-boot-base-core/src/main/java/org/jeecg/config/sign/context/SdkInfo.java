package org.jeecg.config.sign.context;


import lombok.Data;
import lombok.ToString;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;

@Data
@ToString
public class SdkInfo {

    /**
     * ip
     */
    private String ip;

    /**
     * 子游戏信息
     */
    private OpSubGameModel opSubGameModel;

    /**
     * 一级游戏包信息
     */
    private OpPkgModel opPkgModel;

}
