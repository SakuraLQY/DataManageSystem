package org.jeecg.modules.count.vo;

import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: UserTwoPayVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/10 15:19
 */
@Data
public class UserTwoPayVo {

    /**
     * 首日
     */
    private String payUserOne;
    private String twoPayUserOne;

    /**
     * 次日
     */
    private String payUserTwo;
    private String twoPayUserTwo;

    /**
     * 三日
     */
    private String payUserThird;
    private String twoPayUserThird;

    /**
     * 四日
     */
    private String payUserFour;
    private String twoPayUserFour;

    /**
     * 五日
     */
    private String payUserFive;
    private String twoPayUserFive;

    /**
     * 六日
     */
    private String payUserSix;
    private String twoPayUserSix;

    /**
     * 七日
     */
    private String payUserSeven;
    private String twoPayUserSeven;
}
