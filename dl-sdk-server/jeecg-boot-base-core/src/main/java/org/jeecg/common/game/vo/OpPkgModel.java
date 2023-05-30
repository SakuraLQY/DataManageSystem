package org.jeecg.common.game.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.game.bo.PkgChannelConfJrtt;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: op_pkg
 * @Author: jeecg-boot
 * @Date:   2023-01-12
 * @Version: V1.0
 */
@Data
@TableName("op_pkg")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_pkg对象", description="op_pkg")
public class OpPkgModel implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    private Integer id;
	/**游戏id*/
    private Integer gameId;
	/**子游戏id*/
    private Integer subGameId;
    /**厂商ID*/
    private java.lang.Integer vendorId;
	/**游戏名*/
    private String nickName;
    /**游戏包名*/
    private String packageName;

    /**
     * 渠道游戏包名字(用于区分展示)
     */
    private String pkgName;
	/**游戏版本*/
    private String versionName;
	/**游戏构建版本*/
    private String versionCode;
	/**下载链接*/
    private String downloadUrl;
	/**渠道id*/
    private Integer channelId;
	/**渠道配置*/
    private String channelConf;
	/**icon图打包素材路径*/
    private String iconMaterialPackPath;
	/**闪屏图打包素材路径*/
    private String screenMaterialPackPath;
	/**加载图打包素材路径*/
    private String loadingMaterialPackPath;
	/**登录图打包素材路径*/
    private String loginMaterialPackPath;
	/**logo图打包素材路径*/
    private String logoMaterialPackPath;
	/**打包状态，0为等待操作，1为待打包，2为打包中，3为打包成功，4为打包失败*/
    /**素材类型*/
    private Integer packState;
	/**备注*/
    private String descs;
    /**隐私政策地址*/
    private String privacyPolicyUrl;
    /**用户协议地址*/
    private String userAgreementUrl;
	/**创建人*/
    private String createBy;
	/**创建日期*/
    private Date createTime;
	/**修改人*/
    private String updateBy;
	/**修改日期*/
    private Date updateTime;
    /**今日头条配置*/
    private PkgChannelConfJrtt jrttConf;
    /**是否开启平台币*/
    private java.lang.Integer platformCurrencySwitch;
    /**折扣配置*/
    private java.lang.Integer platformCurrencyDiscount;
    /**包id配置*/
    private java.lang.String platformCurrencyPkgConfig;
    /**绑定手机开关*/
    private java.lang.Integer phoneSwitch;
    /**隐私政策开关*/
    private java.lang.Integer privacySwitch;
    /**实名验证窗口*/
    private java.lang.Integer idAuthSwitch;
    /**是否调用接口*/
    private java.lang.Integer idAuthApi;
    /**接入中宣部*/
    private java.lang.Integer officialAntiIndulgeSwitch;
    /**备案识别码*/
    private java.lang.String officialBizId;
    /**防沉迷开关*/
    private java.lang.Integer antiIndulgeSwitch;
}
