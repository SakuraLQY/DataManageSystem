package org.jeecg.modules.game.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.game.entity.OpPkg;
import org.jeecg.modules.game.entity.OpPrivacyPolicy;
import org.jeecg.modules.game.vo.OpPkgVo;

/**
 * @Description: op_pkg
 * @Author: jeecg-boot
 * @Date:   2023-01-12
 * @Version: V1.0
 */
public interface OpPkgMapper extends BaseMapper<OpPkg> {

    /**
     * @return java.util.List<org.jeecg.modules.game.vo.OpPkgVo>
     * @Author lili
     * @Description 查询只为头条的游戏包
     * @Date 14:36 2023/2/14
     **/
    @Select("SELECT a.*,b.game_name gameName,c.game_name subGameName FROM `open_gateway`.`op_pkg` a LEFT JOIN `open_gateway`.`op_game` b ON a.game_id = b.id LEFT JOIN `open_gateway`.`op_sub_game` c ON a.sub_game_id = c.id where a.channel_id = 4")
    List<OpPkgVo> getList();

    /**
     * @return java.util.List<org.jeecg.modules.game.vo.OpPkgVo>
     * @Author lili
     * @Description 查询所有的游戏包
     * @Date 14:36 2023/2/14
     **/
    @Select("SELECT a.*,b.game_name gameName,c.game_name subGameName FROM `open_gateway`.`op_pkg` a LEFT JOIN `open_gateway`.`op_game` b ON a.game_id = b.id LEFT JOIN `open_gateway`.`op_sub_game` c ON a.sub_game_id = c.id")
    List<OpPkgVo> getAll();

}
