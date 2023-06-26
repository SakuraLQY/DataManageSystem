package org.jeecg.modules.count.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.modules.count.dto.PayUserStructDto;
import org.jeecg.modules.count.dto.UserAccumulateLevelDto;
import org.jeecg.modules.count.dto.UserOrderDistributionDto;
import org.jeecg.modules.count.dto.UserTwoPayDto;
import org.jeecg.modules.count.service.IUserOrderDistributionService;
import org.jeecg.modules.count.vo.ListUserOrderDistributionVo;
import org.jeecg.modules.count.vo.UserFirstPayVo;
import org.jeecg.modules.count.vo.UserTwoPayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.controller
 * @className: UserOrderDataController
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/6 15:38
 */
@Api(tags="user_order_data")
@RestController
@RequestMapping("/user/order")
@Slf4j
public class UserOrderDataController {

    @Autowired
    private IUserOrderDistributionService iUserOrderDistributionService;
    @ApiOperation(value="首日付费")
    @GetMapping(value = "/userOrderDistribution")
    @UserPermissionData(alias = "co")
    public Result<ListUserOrderDistributionVo> userOrderDistribution(
        UserOrderDistributionDto userOrderDistributionDto,
        @RequestParam(name="isOneDay", defaultValue="true") Boolean isOneDay) {
        ListUserOrderDistributionVo listUserOrderDistributionVo = iUserOrderDistributionService
            .userOrderDistribution(userOrderDistributionDto,isOneDay);
        return Result.OK(listUserOrderDistributionVo);
    }


    @ApiOperation(value="累计付费")
    @GetMapping(value = "/userAccumulatePay")
    @UserPermissionData(alias = "co")
    public Result<ListUserOrderDistributionVo> userAccumulatePay(
        UserOrderDistributionDto userOrderDistributionDto) {
        ListUserOrderDistributionVo listUserOrderDistributionVo = iUserOrderDistributionService
            .userAccumulatePay(userOrderDistributionDto);
        return Result.OK(listUserOrderDistributionVo);
    }

    @ApiOperation(value="用户首充等级分布数据")
    @GetMapping(value = "/getUserAccumulateLevel")
    @UserPermissionData(alias = "co")
    public Result<UserFirstPayVo> getUserAccumulateLevel(
        UserAccumulateLevelDto userAccumulateLevelDto) {
        UserFirstPayVo data = iUserOrderDistributionService
            .getUserAccumulateLevel(userAccumulateLevelDto);
        return Result.OK(data);
    }

    @ApiOperation(value="用户二次付费数据")
    @GetMapping(value = "/getPayUserTwoPay")
    @UserPermissionData(alias = "co")
    public Result<ArrayList> getPayUserTwoPay(
        UserTwoPayDto userTwoPayDto) {
        UserTwoPayVo payUserTwoPay = iUserOrderDistributionService.getPayUserTwoPay(userTwoPayDto);
        ArrayList<UserTwoPayVo> arrayList = new ArrayList<>();
        arrayList.add(payUserTwoPay);
        return Result.OK(arrayList);
    }

    @ApiOperation(value="充值结构数据")
    @GetMapping(value = "/getPayUserStruct")
    @UserPermissionData(alias = "co")
    public Result<ArrayList<Map>> getPayUserStruct(
        PayUserStructDto payUserStructDto) {
        ArrayList<Map> payUserStruct = iUserOrderDistributionService
            .getPayUserStruct(payUserStructDto);
        return Result.OK(payUserStruct);
    }
}
