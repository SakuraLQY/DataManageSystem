package org.jeecg.modules.users.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.users.entity.OpRegisterLoginSwitch;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import org.jeecg.modules.users.vo.OpRegisterLoginSwitchVo;

/**
 * @Description: op_register_login_switch
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
public interface  IOpRegisterLoginSwitchService extends IService<OpRegisterLoginSwitch> {

  IPage<OpRegisterLoginSwitchVo> selectList(Page<OpRegisterLoginSwitch> page,OpRegisterLoginSwitch opRegisterLoginSwitch);

  /**
   * @param opRegisterLoginSwitch
   * @Author lili
   * @Description 检查规则是否重复
   * @Date 14:24 2022/12/15
   **/
  void checkRuleId(OpRegisterLoginSwitch opRegisterLoginSwitch);

  /**
   * @param opRegisterLoginSwitch
   * @Author lili
   * @Description 添加
   * @Date 16:11 2022/12/15
   **/
  void add(OpRegisterLoginSwitch opRegisterLoginSwitch);

  /**
   * @param opRegisterLoginSwitch
   * @Author lili
   * @Description 修改
   * @Date 16:12 2022/12/15
   **/
  void update(OpRegisterLoginSwitch opRegisterLoginSwitch);


  /**
   * 获取所有的开关
   * TODO 加缓存
   */
  List<OpRegisterLoginSwitch> getAllSwitchList();

}
