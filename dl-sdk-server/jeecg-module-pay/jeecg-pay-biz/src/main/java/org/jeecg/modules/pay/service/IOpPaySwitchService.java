package org.jeecg.modules.pay.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import java.util.List;
import org.jeecg.modules.pay.dto.IosPayListDto;
import org.jeecg.modules.pay.dto.OpPaySwitchDto;
import org.jeecg.modules.pay.entity.OpPaySwitch;
import org.jeecg.modules.pay.vo.IosCheckPayModelVo;


/**
 * @Description: op_pay_switch
 * @Author: jeecg-boot
 * @Date: 2022-12-12
 * @Version: V1.0
 */
public interface IOpPaySwitchService extends IService<OpPaySwitch> {

    /**
     * @param page
     * @param opPaySwitchDto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.pay.entity.OpPaySwitch>
     * @Author lili
     * @Description 查询列表
     * @Date 16:27 2023/4/10
     **/
    IPage<OpPaySwitch> selectList(Page<OpPaySwitch> page, OpPaySwitchDto opPaySwitchDto);

    /**
     * @param opPaySwitchDto
     * @Author lili
     * @Description 新增
     * @Date 16:28 2023/4/10
     **/
    void add(OpPaySwitchDto opPaySwitchDto);

    /**
     * @param subGameId:     子游戏id
     * @param userId:     用户id
     * @param version:    游戏版本
     * @param build:      游戏构建
     * @param orderMoney: 单笔金额
     * @return boolean
     * @author
     * @description
     * @date 2022/12/29 10:13
     */
    List<Integer> checkPayMode(Integer subGameId, Integer PkgId, String userId, String version, String build,
        BigDecimal orderMoney);

    /**
     * @param iosPayListDto
     * @return java.util.List<java.lang.Integer>
     * @author chenyw
     * @description 获取ios支付列表
     * @date 17:47 2023/4/7/007
     **/
    IosCheckPayModelVo checkPayModeIos(IosPayListDto iosPayListDto, String serverName, int port);

}
