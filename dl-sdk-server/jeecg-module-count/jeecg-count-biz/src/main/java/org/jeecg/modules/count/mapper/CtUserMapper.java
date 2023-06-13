package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.count.dto.CtUserDto;
import org.jeecg.modules.count.dto.OnlineUserDto;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.modal.CtUserModal;
import org.jeecg.modules.count.vo.CtUserVo;
import org.jeecg.modules.count.vo.OnlineUserVo;
import org.jeecg.modules.count.vo.PayUserVo;
import org.springframework.stereotype.Repository;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date: 2023-04-25
 * @Version: V1.0
 */
@Repository
public interface CtUserMapper extends BaseMapper<CtUser> {

    /**
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.count.vo.CtUserVo>
     * @Author lili
     * @Description 分页查询
     * @Date 15:14 2023/5/5
     **/
    IPage<CtUserVo> queryPageList(Page<T> page,
        @Param(Constants.WRAPPER) QueryWrapper<CtUserVo> wrapper);

    /**
     * @param wrapper
     * @return java.util.List<org.jeecg.modules.count.vo.CtUserVo>
     * @Author lili
     * @Description 查询全部（包含查询条件）
     * @Date 15:33 2023/5/6
     **/
    List<CtUserVo> queryAllList(@Param(Constants.WRAPPER) QueryWrapper<CtUserVo> wrapper);

    /**
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.count.vo.PayUserVo>
     * @Author lili
     * @Description 分页查询付费用户数据
     * @Date 16:25 2023/5/8
     **/
    IPage<PayUserVo> queryPagePayUserList(Page<T> page,
        @Param(Constants.WRAPPER) QueryWrapper<CtUserVo> wrapper);

    /**
     * @param wrapper
     * @return java.util.List<org.jeecg.modules.count.vo.PayUserVo>
     * @Author lili
     * @Description 付费用户数据查询全部（包含查询条件）
     * @Date 16:25 2023/5/8
     **/
    List<PayUserVo> getAllPayUserList(@Param(Constants.WRAPPER) QueryWrapper<CtUserVo> wrapper);

    /**
     * @param wrapper
     * @param typeName
     * @return java.util.List<org.jeecg.modules.count.vo.OnlineUserVo>
     * @Author lili
     * @Description 用户数据-在线统计
     * @Date 14:58 2023/5/16
     **/
    List<OnlineUserVo> getOnlineUserList(
        @Param(Constants.WRAPPER) QueryWrapper<OnlineUserDto> wrapper,
        @Param("typeName") String typeName);
}
