package org.jeecg.modules.users.bo;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SessionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 子游戏id
     */
    private Integer subGameId;

    /**
     * 一级游戏包id
     */
    private Integer pkgId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 时间戳 精确到秒
     */
    private Long time;

    /**
     * 签名
     */
    private String sign;
}
