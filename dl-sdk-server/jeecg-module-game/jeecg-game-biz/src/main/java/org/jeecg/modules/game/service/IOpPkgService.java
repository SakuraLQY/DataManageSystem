package org.jeecg.modules.game.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.modules.game.entity.OpPkg;
import org.jeecg.modules.game.vo.GameObjVo;
import org.jeecg.modules.game.vo.GameVo;
import org.jeecg.modules.game.vo.OpPkgVo;
import org.jeecg.modules.game.vo.SubAndOpPackGameVo;

/**
 * @Description: op_pkg
 * @Author: jeecg-boot
 * @Date: 2023-01-12
 * @Version: V1.0
 */
public interface IOpPkgService extends IService<OpPkg> {

    /**
     * @param gameId:    游戏ID
     * @param subGameId: 子游戏ID
     * @param channelId: 渠道ID
     * @return List<Map < String, Object>>
     * @author xmh
     * @description 获取头条的游戏包
     * @date 2023/2/15 16:20
     */
    List<Map<String, Object>> getJrttPkg(Integer gameId, Integer subGameId, Integer channelId);

    /**
     * @param type
     * @return java.util.List<org.jeecg.modules.game.vo.SubAndOpPackGameVo>
     * @Author lili
     * @Description 查询游戏+子游戏+一级游戏包的下拉框的值
     * @Date 14:37 2023/2/14
     **/
    List<SubAndOpPackGameVo> getOptionList(Integer type);

    /**
     * @return java.util.List<org.jeecg.modules.game.vo.SubAndOpPackGameVo>
     * @Author lili
     * @Description 三级联动
     * @Date 14:37 2023/2/14
     **/
    Map<Integer, GameVo> queryList();

    /**
     * @param id
     * @author chenyw
     * @description 更新打包状态为待打包
     * @date 14:13 2023/1/18/018
     **/
    void updatePackState(Integer id);

    /**
     * @param page 分页参数
     * @param queryWrapper 条件构造器
 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<org.jeecg.modules.game.entity.OpPkg>
     * @author chenyw
     * @description
     * @date 10:53 2023/1/31/031
     **/
    IPage<OpPkgVo> getPage(Page<OpPkg> page, QueryWrapper<OpPkg> queryWrapper);

    /**
     * @param ids
     * @return java.util.List<java.lang.Integer>
     * @Author lili
     * @Description 根据游戏id或子游戏id得到所有的一级游戏包ID
     * @Date 16:05 2023/2/10
     **/
    List<Integer> getIdsByGameIdOrSubGameId(List<String> ids);

    /**
     * @author chenyw
     * @description 新增记录
     * @date 13:37 2023/2/14/014
     **/
    void saveOpPkg(OpPkg opPkg);

    /**
     * @param id
     * @author chenyw
     * @description 删除
     * @date 14:10 2023/2/14/014
     **/
    void deleteOpPkgById(Integer id);


    /**
     * @param ids
     * @author chenyw
     * @description 批量删除
     * @date 14:10 2023/2/14/014
     **/
    void deleteOpPkgByIds(String ids);

    /**
     * @param packageName
     * @return org.jeecg.modules.game.entity.OpPkg
     * @Author lili
     * @Description 判断channelConf中的packageName是否存在该值
     * @Date 9:52 2023/2/15
     **/
    OpPkg getPkgByPackageName(String packageName);

}
