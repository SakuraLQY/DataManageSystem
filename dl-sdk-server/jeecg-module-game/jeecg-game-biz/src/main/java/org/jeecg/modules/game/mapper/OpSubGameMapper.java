package org.jeecg.modules.game.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.game.entity.OpSubGame;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.game.vo.OpSubGameVo;

import java.util.List;

/**
 * @Description: op_son_game
 * @Author: jeecg-boot
 * @Date: 2022-12-09
 * @Version: V1.0
 */
public interface OpSubGameMapper extends BaseMapper<OpSubGame> {

  /**
   * @param page
   * @param wrapper
   * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.demo.game.vo.OpSubGameVo>
   * @Author lili
   * @Description 获得列表
   * @Date 13:55 2022/12/16
   **/
  IPage<OpSubGameVo> getSubGameList(Page<T> page,
      @Param(Constants.WRAPPER) QueryWrapper<OpSubGameVo> wrapper);

  /**
   * @param wrapper
   * @return java.util.List<org.jeecg.modules.demo.game.vo.OpSubGameVo>
   * @Author lili
   * @Description 不分页列表
   * @Date 10:22 2022/12/19
   **/
  List<OpSubGameVo> getSubGameList(@Param(Constants.WRAPPER) QueryWrapper<OpSubGameVo> wrapper);

  /**
   * @param id
   * @return java.lang.Integer
   * @Author lili
   * @Description 删除
   * @Date 13:55 2022/12/16
   **/
  @Update("update op_sub_game set status = 1 where id = #{id}")
  Integer delete(String id);


}
