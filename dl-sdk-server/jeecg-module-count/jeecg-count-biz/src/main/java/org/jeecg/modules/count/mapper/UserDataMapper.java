package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.entity.CtOrder;
import org.jeecg.modules.count.vo.PayUserStructDataVo;
import org.jeecg.modules.count.vo.UserAccumulateLevelVo;
import org.jeecg.modules.count.vo.UserOrderDataVo;
import org.springframework.stereotype.Repository;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.mapper
 * @className: UserDataMapper
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/6 15:44
 */
@Repository
public interface UserDataMapper extends BaseMapper<CtOrder> {

    /**
     * @param queryWrapper: 查询条件
     * @return List<UserOrderDataVo>
     * @author Fkh
     * @description 获取用户充值分布数据接口（累计付费和首日付费都可用）
     * @date 2023/5/13 15:13
     */
    List<UserOrderDataVo> userOrderDistribution(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return List<UserAccumulateLevelVo>
     * @author Fkh
     * @description 获取用户首充分布数据
     * @date 2023/5/13 15:14
     */
    List<UserAccumulateLevelVo> getUserAccumulateLevel(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的首日的付费人数
     * @date 2023/5/13 15:15
     */
    String selectPayUserOne(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的首日的二次付费人数
     * @date 2023/5/13 15:15
     */
    String selectTwoPayUserOne(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的次日的付费人数
     * @date 2023/5/13 15:15
     */
    String selectPayUserTwo(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的次日的二次付费人数
     * @date 2023/5/13 15:15
     */
    String selectTwoPayUserTwo(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的三日的付费人数
     * @date 2023/5/13 15:15
     */
    String selectPayUserThird(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的三日的二次付费人数
     * @date 2023/5/13 15:15
     */
    String selectTwoPayUserThird(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的四日的付费人数
     * @date 2023/5/13 15:15
     */
    String selectPayUserFour(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的四日的二次付费人数
     * @date 2023/5/13 15:15
     */
    String selectTwoPayUserFour(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的五日的付费人数
     * @date 2023/5/13 15:15
     */
    String selectPayUserFive(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的五日的二次付费人数
     * @date 2023/5/13 15:15
     */
    String selectTwoPayUserFive(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的六日的付费人数
     * @date 2023/5/13 15:15
     */
    String selectPayUserSix(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的六日的二次付费人数
     * @date 2023/5/13 15:15
     */
    String selectTwoPayUserSix(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的七日的付费人数
     * @date 2023/5/13 15:15
     */
    String selectPayUserSeven(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return String
     * @author Fkh
     * @description 获取二次付费数据中的七日的二次付费人数
     * @date 2023/5/13 15:15
     */
    String selectTwoPayUserSeven(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper:
     * @return List<PayUserStructDataVo>
     * @author Fkh
     * @description 获取统计订单表的用户创建时间以及该用户的以创建时间为组别的总金额数
     * @date 2023/5/13 15:19
     */
    List<PayUserStructDataVo> getPayUserStruct(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);
}
