package org.jeecg.modules.game.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class PackConfig {

    @JSONField(name = "screen_width")
    private String screenWidth;

    @JSONField(name = "screen_type")
    private String screenType;

    @JSONField(name = "loading_height")
    private String loadingHeight;

    @JSONField(name = "login_type")
    private String loginType;

    @JSONField(name = "logo_path")
    private String logoPath;

    @JSONField(name = "login_path")
    private String loginPath;

    @JSONField(name = "loading_path")
    private String loadingPath;

    @JSONField(name = "login_width")
    private String loginWidth;

    @JSONField(name = "logo_type")
    private String logoType;

    @JSONField(name = "screen_height")
    private String screenHeight;

    @JSONField(name = "screen_path")
    private String screenPath;

    @JSONField(name = "loading_width")
    private String loadingWidth;

    @JSONField(name = "loading_type")
    private String loadingType;

    @JSONField(name = "logo_height")
    private String logoHeight;

    @JSONField(name = "logo_width")
    private String logoWidth;

    @JSONField(name = "login_height")
    private String loginHeight;

}
