package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttToRefreshAccountBo;
import org.jeecg.modules.advert.api.ks.account.bo.KsToRefreshAccountBo;
import org.jeecg.modules.advert.entity.OpKsPutAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.vo.OpJrttPutAccountVo;
import org.jeecg.modules.advert.vo.OpKsPutAccountVo;

/**
 * @Description: op_ks_put_account
 * @Author: jeecg-boot
 * @Date: 2023-03-07
 * @Version: V1.0
 */
public interface OpKsPutAccountMapper extends BaseMapper<OpKsPutAccount> {

    @Select("SELECT b.account_id,b.access_token,b.access_token_time,b.refresh_token,b.refresh_token_time,c.app_id,c.app_secret FROM `op_put_account` a LEFT JOIN `op_ks_put_account` b ON(a.id=b.account_id) LEFT JOIN `op_ks_put_account_app` c ON(a.app_id=c.id) WHERE a.level_id=1 and a.state = 1 and b.access_token_time < #{accessTokenTime} and b.refresh_token_time > #{refreshTokenTime} and b.refresh_token <> '' and c.app_id <> '' and c.app_secret <> ''")
    List<KsToRefreshAccountBo> getRefreshAccount(
        @Param(value = "accessTokenTime") LocalDateTime accessTokenTime,
        @Param(value = "refreshTokenTime") LocalDateTime refreshTokenTime);

    @Update("update op_ks_put_account SET `access_token`= #{accessToken}, `access_token_time`= #{accessTokenTime},`refresh_token`= #{refreshToken}, `refresh_token_time`= #{refreshTokenTime} WHERE account_id = #{accountId}")
    int updateToken(@Param(value = "accountId") Integer accountId,
        @Param(value = "accessToken") String accessToken,
        @Param(value = "accessTokenTime") LocalDateTime accessTokenTime,
        @Param(value = "refreshToken") String refreshToken,
        @Param(value = "refreshTokenTime") LocalDateTime refreshTokenTime);

    @InterceptorIgnore(tenantLine = "true")
    IPage<OpKsPutAccountVo> getOpKsPutAccountList(Page<OpKsPutAccountVo> page,
        @Param("gameId") Integer gameId, @Param("subGameId") Integer subGameId,
        @Param("account") String account,
        @Param("levelId") Integer levelId, @Param("state") Integer state,
        @Param("nickName") String nickName);

}
