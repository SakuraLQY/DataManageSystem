package org.jeecg.common.game.api;

import java.math.BigDecimal;
import java.util.List;
import org.jeecg.common.api.GameApi;
import org.jeecg.common.function.bo.GetNameByIdDto;
import org.jeecg.common.function.vo.GetNameByIdVo;
import org.jeecg.common.game.vo.OpCommodityModel;
import org.jeecg.common.game.vo.OpGameModel;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpPrivacyPolicyModel;
import org.jeecg.common.game.vo.OpSubGameModel;


public interface IGameApi extends GameApi {

    /**
     * @param subGameId
     * @return org.jeecg.common.game.vo.OpSubGameModel
     * @author chenyw
     * @description 根据子游戏id获得子游戏信息
     * @date 16:30 2022/12/19/019
     **/
    OpSubGameModel getOpSubGame(Integer subGameId);

    /**
     * @param id
     * @return org.jeecg.common.game.vo.OpGameModel
     * @Author lili
     * @Description 根据id得到游戏对象
     * @Date 16:45 2023/3/15
     **/
    OpGameModel getOpGame(Integer id);

    /**
     * @param goodsId
     * @return org.jeecg.common.game.vo.OpCommodityModel
     * @author chenyw
     * @description 根据商品id获取内购商品对象
     * @date 20:47 2023/1/6/006
     **/
    OpCommodityModel getOpCommdityByGoodId(Integer subGameId, Integer pkgId, String goodsId);

    /**
     * @param subGameId
     * @param money
     * @return org.jeecg.common.game.vo.OpCommodityModel
     * @author chenyw
     * @description 根据金额获取商品
     * @date 20:34 2023/4/7/007
     **/
    OpCommodityModel getOpCommdityByMoney(Integer subGameId, Integer pkgId, BigDecimal money);

    /**
     * @param id
     * @return org.jeecg.common.game.vo.OpPkgModel
     * @Author lili
     * @Description 根据ID得到对象
     * @Date 15:41 2023/2/13
     **/
    OpPkgModel getOpPkgById(Integer id);

    /**
     * @param packageName
     * @return org.jeecg.common.game.vo.OpPkgModel
     * @Author lili
     * @Description 根据packageName得到对象
     * @Date 9:56 2023/2/15
     **/
    OpPkgModel getPkgByPackageName(String packageName);

    /**
     * @author xmh
     * @description 判断厂商ID是否被绑定
     * @date 2023/3/13 15:54
     * @param list: 厂商ID
     * @return boolean
     */
    boolean checkVendorIsBind(List<String> list);

    OpPrivacyPolicyModel getOpPrivacyId(Integer id);
}
