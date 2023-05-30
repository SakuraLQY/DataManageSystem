package org.jeecg.modules.users.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.users.entity.OpPayBlack;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.users.vo.OpPayBlackVo;

/**
 * @Description: op_pay_black
 * @Author: jeecg-boot
 * @Date: 2022-12-19
 * @Version: V1.0
 */
public interface OpPayBlackMapper extends BaseMapper<OpPayBlack> {

    /**
     * @param page    分页
     * @param wrapper 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.users.vo.OpPayBlackVo>
     * @Author lili
     * @Description 分页查询
     * @Date 19:05 2022/12/21
     **/
    IPage<OpPayBlackVo> getPayBlackList(Page<T> page,
        @Param(Constants.WRAPPER) QueryWrapper<OpPayBlackVo> wrapper);

    /**
     * @return java.util.List<org.jeecg.modules.users.vo.OpPayBlackVo>
     * @Author lili
     * @Description 查询游戏黑名单
     * @Date 20:04 2022/12/21
     **/
    @Select("SELECT a.*, b.game_name as gameName FROM op_pay_black a LEFT JOIN op_game b ON a.rule_id = b.id WHERE a.rule_type = 0  ")
    List<OpPayBlackVo> getGameOptionList();

    /**
     * @return java.util.List<org.jeecg.modules.users.vo.OpPayBlackVo>
     * @Author lili
     * @Description 查询子游戏黑名单
     * @Date 20:04 2022/12/21
     **/
    @Select("SELECT a.*, b.game_name as gameName FROM op_pay_black a LEFT JOIN op_sub_game b ON a.rule_id = b.id WHERE a.rule_type = 1  ")
    List<OpPayBlackVo> getSubGameOptionList();

    /**
     * @return java.lang.Integer
     * @Author lili
     * @Description 判断规则是否重复
     * @Date 15:23 2022/12/19
     **/
    @Select("select count(*) from op_pay_black where rule_type = #{ruleType} and rule_id = #{ruleId}")
    Integer checkRuleIdByRuleType(@Param("ruleId") Integer ruleId,
        @Param("ruleType") Integer ruleType);

}
