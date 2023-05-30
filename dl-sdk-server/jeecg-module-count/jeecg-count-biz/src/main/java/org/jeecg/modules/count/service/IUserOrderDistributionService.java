package org.jeecg.modules.count.service;

import java.util.ArrayList;
import java.util.Map;
import org.jeecg.modules.count.dto.PayUserStructDto;
import org.jeecg.modules.count.dto.UserAccumulateLevelDto;
import org.jeecg.modules.count.dto.UserOrderDistributionDto;
import org.jeecg.modules.count.dto.UserTwoPayDto;
import org.jeecg.modules.count.vo.ListUserOrderDistributionVo;
import org.jeecg.modules.count.vo.UserFirstPayVo;
import org.jeecg.modules.count.vo.UserTwoPayVo;

public interface IUserOrderDistributionService {

    /**
     * @param userOrderDistributionDto: 输入参数
     * @param isOneDay: 用于和累计付费区别是否为首日的条件
     * @return ListUserOrderDistributionVo
     * @author Fkh
     * @description 获取用户充值分布数据中的首日付费分布数据（包括表格数据以及饼状图数据）
     * @date 2023/5/13 15:04
     */
    ListUserOrderDistributionVo userOrderDistribution(UserOrderDistributionDto userOrderDistributionDto,Boolean isOneDay);

    /**
     * @param userOrderDistributionDto: 输入参数
     * @return ListUserOrderDistributionVo
     * @author Fkh
     * @description 获取用户充值分布数据中的累计付费分布数据（包括表格数据以及并饼状图数据）
     * @date 2023/5/13 15:06
     */
    ListUserOrderDistributionVo userAccumulatePay(UserOrderDistributionDto userOrderDistributionDto);

    /**
     * @param userAccumulateLevelDto: 输入参数
     * @return UserFirstPayVo
     * @author Fkh
     * @description 获取用户首充分布数据（包括了首充等级的表格和饼状图数据、首充时长的表格和饼状图数据）
     * @date 2023/5/13 15:09
     */
    UserFirstPayVo getUserAccumulateLevel(UserAccumulateLevelDto userAccumulateLevelDto);

    /**
     * @param userTwoPayDto: 输入参数
     * @return UserTwoPayVo
     * @author Fkh
     * @description 获取用户二次付费数据
     * @date 2023/5/13 15:11
     */
    UserTwoPayVo getPayUserTwoPay(UserTwoPayDto userTwoPayDto);

    /**
     * @param payUserStructDto: 输入参数
     * @return ArrayList<Map>
     * @author Fkh
     * @description 充值结构数据
     * @date 2023/5/13 15:11
     */
    ArrayList<Map> getPayUserStruct(PayUserStructDto payUserStructDto);
}
