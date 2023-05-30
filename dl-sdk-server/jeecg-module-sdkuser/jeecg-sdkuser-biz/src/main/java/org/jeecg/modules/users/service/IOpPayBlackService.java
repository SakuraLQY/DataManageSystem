package org.jeecg.modules.users.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.List;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.users.common.ResultPayBlackList;
import org.jeecg.modules.users.entity.OpPayBlack;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.users.entity.OpRegisterLoginSwitch;
import org.jeecg.modules.users.vo.OpPayBlackVo;

/**
 * @Description: op_pay_black
 * @Author: jeecg-boot
 * @Date: 2022-12-19
 * @Version: V1.0
 */
public interface IOpPayBlackService extends IService<OpPayBlack> {

    /**
     * @param page
     * @param opPayBlack
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.users.vo.OpPayBlackVo>
     * @Author lili
     * @Description 分页查询列表
     * @Date 18:58 2022/12/21
     **/
    IPage<OpPayBlackVo> getPayBlackList(Page<T> page, OpPayBlack opPayBlack);

    /**
     * @param opPayBlack
     * @return java.util.List<org.jeecg.modules.users.vo.OpPayBlackVo>
     * @Author lili
     * @Description 下拉框列表
     * @Date 19:30 2022/12/21
     **/
    List<OpPayBlackVo> getOptionList(OpPayBlack opPayBlack);

    /**
     * @param userId    用户ID
     * @param gameId    游戏ID
     * @param subGameId 子游戏ID
     * @param ip        IP
     * @param device    设备
     * @return java.lang.Boolean
     * @Author lili
     * @Description
     * @Date 13:54 2022/12/22
     **/
    Boolean checkUser(Integer userId, Integer gameId, Integer subGameId, String ip, String device);

    /**
     * @param opPayBlack 对象
     * @Author lili
     * @Description 检查规则是否重复
     * @Date 15:20 2022/12/19
     **/
    void checkRuleId(OpPayBlack opPayBlack);

    /**
     * @param opPayBlack 对象
     * @Author lili。
     * @Description 添加。
     * @Date 15:20 2022/12/19。
     **/
    void add(OpPayBlack opPayBlack);

    /**
     * @param opPayBlack
     * @Author lili
     * @Description 修改
     * @Date 15:20 2022/12/19
     **/
    void update(OpPayBlack opPayBlack);

}
