package org.jeecg.modules.users.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.poi.ss.formula.functions.T;
import org.apache.zookeeper.Op;
import org.jeecg.modules.users.entity.OpUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.users.vo.OpUserVo;

import java.util.List;
import java.util.Map;

/**
 * @Description: op_user
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
public interface OpUserMapper extends BaseMapper<OpUser> {


    /**
     * @param page
     * @param wrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.users.vo.OpUserVo>
     * @Author lili
     * @Description 分页列表查询
     * @Date 14:18 2022/12/15
     **/
    IPage<OpUserVo> getUserList(Page<T> page,
        @Param(Constants.WRAPPER) QueryWrapper<OpUserVo> wrapper);

}
