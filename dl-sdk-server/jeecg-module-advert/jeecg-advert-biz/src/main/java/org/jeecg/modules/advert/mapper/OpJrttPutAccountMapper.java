package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccountBo;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttToRefreshAccountBo;
import org.jeecg.modules.advert.entity.OpJrttPutAccount;
import org.jeecg.modules.advert.vo.OpJrttPutAccountVo;

/**
 * @Description: op_put_account
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */

public interface OpJrttPutAccountMapper extends BaseMapper<OpJrttPutAccount> {

    @Select("SELECT b.account_id,b.access_token,b.access_token_time,b.refresh_token,b.refresh_token_time,c.app_id,c.app_secret FROM `op_put_account` a LEFT JOIN `op_jrtt_put_account` b ON(a.id=b.account_id) LEFT JOIN `op_jrtt_put_account_app` c ON(a.app_id=c.id) WHERE a.level_id=1 and a.state = 1 and b.access_token_time < #{accessTokenTime} and b.refresh_token_time > #{refreshTokenTime} and b.refresh_token <> '' and c.app_id <> '' and c.app_secret <> ''")
    List<JrttToRefreshAccountBo> getRefreshAccount(
        @Param(value = "accessTokenTime") LocalDateTime accessTokenTime,
        @Param(value = "refreshTokenTime") LocalDateTime refreshTokenTime);

    @Update("update op_jrtt_put_account SET `access_token`= #{accessToken}, `access_token_time`= #{accessTokenTime},`refresh_token`= #{refreshToken}, `refresh_token_time`= #{refreshTokenTime} WHERE account_id = #{accountId}")
    int updateToken(@Param(value = "accountId") Integer accountId,
        @Param(value = "accessToken") String accessToken,
        @Param(value = "accessTokenTime") LocalDateTime accessTokenTime,
        @Param(value = "refreshToken") String refreshToken,
        @Param(value = "refreshTokenTime") LocalDateTime refreshTokenTime);

    @InterceptorIgnore(tenantLine = "true")
    IPage<OpJrttPutAccountVo> getOpJrttPutAccountList(Page<OpJrttPutAccountVo> page,
        @Param("gameId") Integer gameId, @Param("subGameId") Integer subGameId,
        @Param("levelId") Integer levelId, @Param("state") Integer state,
        @Param("nickName") String nickName,@Param("account") String account);
    
    /** 
     * @return java.util.List<org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccountBo>
     * @author chenyw
     * @description 获取所有的一级账号
     * @date 15:34 2023/4/27/027
     **/
    @Select("select a.account_id,a.advertiser_id,a.access_token from op_jrtt_put_account a left join op_put_account b on a.account_id = b.id where b.level_id = 1 and b.state = 1")
    List<JrttAccountBo> getFirstLevelAccount();

    /**
     * @param pid
     * @return java.util.List<org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccountBo>
     * @author chenyw
     * @description 获取一级账号下所有的二级账号
     * @date 15:34 2023/4/27/027
     **/
    @Select("select b.account_id,b.advertiser_id,b.access_token from op_put_account a left join op_jrtt_put_account b on a.id = b.account_id where a.pid = #{pid} and a.level_id = 2 and a.state = 1")
    List<JrttAccountBo> getListByPid(@Param("pid")Integer pid);

}
