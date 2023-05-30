package org.jeecg.modules.users.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.users.dto.SdkIdAuthDto;
import org.jeecg.modules.users.entity.OpUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.users.vo.OpUserVo;
import org.jeecg.modules.users.vo.SdkUserIdAuthRes;

/**
 * @Description: op_user
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
public interface IOpUserService extends IService<OpUser> {

  /**
   * idAuth.
   *
   * @description
   * @author xmh
   * @date 2022/12/6 15:18
   * @param: SdkIdAuthDto
   * @return: org.jeecg.modules.users.vo.SdkUserIdAuthRes
   */
    SdkUserIdAuthRes idAuth(SdkIdAuthDto sdkIdAuthDto);

    /**
     * @param page
     * @param opUser
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.users.vo.OpUserVo>
     * @Author lili
     * @Description 连表分页查询
     * @Date 14:21 2022/12/15
     **/
    IPage<OpUserVo> SysUserPage(Page<T> page, OpUser opUser);

    /**
     * @param id
     * @param userPassword 密码
     * @Author lili
     * @Description 修改密码
     * @Date 14:21 2022/12/15
     **/
    void editPass(Integer id, String userPassword);

    /**
     * @param id
     * @param platformCurrency 余额
     * @Author lili
     * @Description 修改余额
     * @Date 14:21 2022/12/15
     **/
    void editPlatformCurrency(Integer id, String platformCurrency);

    /**
     * @param id
     * @Author lili
     * @Description 解绑手机
     * @Date 14:21 2022/12/15
     **/
    void removePhone(Integer id);

    /**
     * getOpUserById.
     *
     * @description 根据id获取用户
     * @author chenyw
     * @date 2022/12/9/009 11:17
     * @param: id
     * @return: org.jeecg.modules.users.entity.OpUser
     */
    OpUser getOpUserById(Integer id);

    /**
     * getOpUserByUserName.
     *
     * @description 根据用户名查询
     * @author chenyw
     * @date 2022/12/9/009 21:20
     * @param: userName 用户名
     * @return: org.jeecg.modules.users.entity.OpUser
     */
    OpUser getOpUserByUserName(String userName);

  /**
   * @param id
   * @author chenyw
   * @description 根据id更新用户信息
   * @date 19:40 2022/12/23/023
   **/
  void updateOpUserById(OpUser opUser);

    /**
     * getOpUserByUserPhone.
     *
     * @description 根据手机查询
     * @author chenyw
     * @date 2022/12/9/009 21:20
     * @param: userPhone 手机号
     * @return: org.jeecg.modules.users.entity.OpUser
     */
    OpUser getOpUserByUserPhone(String userPhone);
}
