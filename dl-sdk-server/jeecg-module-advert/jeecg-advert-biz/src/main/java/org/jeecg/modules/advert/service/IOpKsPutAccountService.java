package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.ks.account.bo.KsTokenBo;
import org.jeecg.modules.advert.dto.OpPutAccountDto;
import org.jeecg.modules.advert.entity.OpKsPutAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.entity.OpPutAccount;
import org.jeecg.modules.advert.vo.OpJrttPutAccountVo;
import org.jeecg.modules.advert.vo.OpKsPutAccountVo;
import org.jeecg.modules.advert.vo.SYNCVO;

/**
 * @Description: op_ks_put_account
 * @Author: jeecg-boot
 * @Date:   2023-03-07
 * @Version: V1.0
 */
public interface IOpKsPutAccountService extends IService<OpKsPutAccount> {

    /**
     * @param opPutAccount
     * @param pageNo
     * @param pageSize
     * @param req
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.advert.vo.OpKsPutAccountVo>
     * @Author lili
     * @Description 查询列表
     * @Date 16:46 2023/3/9
     **/
    IPage<OpKsPutAccountVo> getPage(OpPutAccount opPutAccount, Integer pageNo, Integer pageSize, HttpServletRequest req);

    /**
     * @param accountId
     * @return org.jeecg.common.api.vo.Result<java.lang.String>
     * @author lili
     * @description 授权
     * @date 12:35 2023/2/13/013
     **/
    Result<String> auth(Integer accountId);

    /**
     * @param opPutAccount
     * @Author lili
     * @Description 添加二级账号
     * @Date 15:11 2023/3/9
     **/
    void addSecondAccount(OpPutAccountDto opPutAccount);

    /**
     * @param state
     * @param authCode
     * @return java.lang.String
     * @Author lili
     * @Description 快手回调
     * @Date 19:20 2023/3/7
     **/
    String callBack(Integer state, String authCode);

    /**
     * @param accountId
     * @return org.jeecg.modules.advert.api.ks.account.bo.KsTokenBo
     * @Author lili
     * @Description 获取access_token
     * @Date 19:43 2023/3/7
     **/
    KsTokenBo getAccountToken(Integer accountId);

    /**
     * @param accountId
     * @Author lili
     * @Description 根据id删除账号
     * @Date 19:52 2023/3/7
     **/
    void deleteByAccountId(Integer accountId);


    /**
     * @param accountIds
     * @Author lili
     * @Description 根据id批量删除
     * @Date 19:52 2023/3/7
     **/
    void batchDeleteByAccountId(String accountIds);
}
