package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccountBo;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.entity.OpJrttPutAccount;
import org.jeecg.modules.advert.entity.OpPutAccount;
import org.jeecg.modules.advert.vo.OpJrttPutAccountVo;
import org.jeecg.modules.advert.vo.SYNCVO;

/**
 * @Description: op_put_account
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
public interface IOpJrttPutAccountService extends IService<OpJrttPutAccount> {

    IPage<OpJrttPutAccountVo> getPage(OpPutAccount opPutAccount, Integer pageNo, Integer pageSize, HttpServletRequest req);

    /**
     * @param accountId
     * @return org.jeecg.common.api.vo.Result<java.lang.String>
     * @author chenyw
     * @description 授权
     * @date 12:35 2023/2/13/013
     **/
    Result<String> auth(Integer accountId);

    /**
     * @param state
     * @param authCode
     * @return java.lang.String
     * @author chenyw
     * @description 头条回调
     * @date 12:35 2023/2/13/013
     **/
    String callBack(Integer state, String authCode);

    /**
     * @param accountId
     * @return org.jeecg.common.api.vo.Result<java.util.List<org.jeecg.modules.advert.vo.SYNCVO>>
     * @author chenyw
     * @description 同步账号
     * @date 12:35 2023/2/13/013
     **/
    Result<List<SYNCVO>> synAccount(Integer accountId);

    /**
     * @param accountId
     * @return org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo
     * @author chenyw
     * @description 获取access_token
     * @date 12:35 2023/2/13/013
     **/
    JrttTokenBo getAccountToken(Integer accountId);

    /**
     * @param accountId
     * @author chenyw
     * @description 根据id删除账号
     * @date 20:33 2023/2/16/016
     **/
    void deleteByAccountId(Integer accountId);


    /**
     * @param accountIds
     * @author chenyw
     * @description 根据id批量删除
     * @date 20:42 2023/2/16/016
     **/
    void batchDeleteByAccountId(String accountIds);

    /**
     * @return org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccountBo
     * @author chenyw
     * @description 获取所有的一级账号列表
     * @date 16:31 2023/4/27/027
     **/
    List<JrttAccountBo> getFirstLevelAccount();

    /**
     * @param pid
     * @return org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccountBo
     * @author chenyw
     * @description 根据pid获取所有的账号
     * @date 16:31 2023/4/27/027
     **/
    List<JrttAccountBo> getListByPid(Integer pid);

}
