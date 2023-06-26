package org.jeecg.modules.count.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.count.dto.PayUserStructDto;
import org.jeecg.modules.count.dto.UserAccumulateLevelDto;
import org.jeecg.modules.count.dto.UserOrderDistributionDto;
import org.jeecg.modules.count.dto.UserTwoPayDto;
import org.jeecg.modules.count.entity.CtOrder;
import org.jeecg.modules.count.mapper.UserDataMapper;
import org.jeecg.modules.count.service.IUserOrderDistributionService;
import org.jeecg.modules.count.vo.ListUserOrderDistributionVo;
import org.jeecg.modules.count.vo.PayUserStructDataVo;
import org.jeecg.modules.count.vo.UserAccumulateLevelVo;
import org.jeecg.modules.count.vo.UserFirstPayVo;
import org.jeecg.modules.count.vo.UserOrderDataVo;
import org.jeecg.modules.count.vo.UserTwoPayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.service.impl
 * @className: UserOrderDistributionServiceImpl
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/6 15:42
 */
@Slf4j
@Service
@DS("open_countly")
public class UserOrderDistributionServiceImpl extends ServiceImpl<UserDataMapper, CtOrder> implements IUserOrderDistributionService {

    @Autowired
    private UserDataMapper userDataMapper;

    @Override
    public ListUserOrderDistributionVo userOrderDistribution(
        UserOrderDistributionDto userOrderDistributionDto,Boolean isOneDay){

        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(userOrderDistributionDto.getDealId())){
            queryWrapper.in("co.deal_id",userOrderDistributionDto.getDealId());
        }

        if (ObjectUtils.isNotEmpty(userOrderDistributionDto.getGameId())){
            queryWrapper.in("co.game_id",userOrderDistributionDto.getGameId());
        }

        if (ObjectUtils.isNotEmpty(userOrderDistributionDto.getSubGameId())){
            queryWrapper.in("co.sub_game_id",userOrderDistributionDto.getSubGameId());
        }

        if (ObjectUtils.isNotEmpty(userOrderDistributionDto.getPkgId())){
            queryWrapper.in("co.pkg_id",userOrderDistributionDto.getPkgId());
        }

        if (ObjectUtils.isNotEmpty(userOrderDistributionDto.getChannelTypeId())){
            queryWrapper.in("co.channel_type_id",userOrderDistributionDto.getChannelTypeId());
        }

        if (ObjectUtils.isNotEmpty(userOrderDistributionDto.getChannelId())){
            queryWrapper.in("co.channel_id",userOrderDistributionDto.getChannelId());
        }

        if (ObjectUtils.isNotEmpty(userOrderDistributionDto.getChannelSubAccountId())) {
            queryWrapper.in("co.channel_sub_account_id",
                userOrderDistributionDto.getChannelSubAccountId());
        }

        if (ObjectUtils.isNotEmpty(userOrderDistributionDto.getUserStartTime())){
            queryWrapper.ge("co.user_create_time",userOrderDistributionDto.getUserStartTime());
        }

        if (ObjectUtils.isNotEmpty(userOrderDistributionDto.getUserEndTime())) {
            queryWrapper.le("co.user_create_time", userOrderDistributionDto.getUserEndTime());
        }

        queryWrapper.groupBy("co.user_id","co.pkg_id");

        if (isOneDay){
            queryWrapper.apply("DATE_FORMAT(co.create_time,'%Y-%m-%d')=DATE_FORMAT(co.user_create_time,'%Y-%m-%d')");
        }
        List<UserOrderDataVo> userOrderDataVos = userDataMapper.userOrderDistribution(queryWrapper);

        List<Map<String, Object>> maps = initData();

        Integer totalNum=0;
        BigDecimal totalMoney = new BigDecimal(0);
        BigDecimal arpu = new BigDecimal(0);
        if (ObjectUtils.isNotEmpty(userOrderDataVos)){
            if ( ObjectUtil.equal(1,userOrderDistributionDto.getMode())){

                for (UserOrderDataVo userOrderDataVo : userOrderDataVos) {
                    BigDecimal money = userOrderDataVo.getMoney();
                    if (money.compareTo(new BigDecimal(2))>=0 && money.compareTo(new BigDecimal(10))<=0){
                        
                        maps.get(0).replace("num",Integer.parseInt(maps.get(0).get("num").toString())+1);
                        maps.get(0).replace("totalMoney",new BigDecimal(maps.get(0).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(11))>=0 && money.compareTo(new BigDecimal(29))<=0){

                        maps.get(1).replace("num",Integer.parseInt(maps.get(1).get("num").toString())+1);
                        maps.get(1).replace("totalMoney",new BigDecimal(maps.get(1).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(30))>=0 && money.compareTo(new BigDecimal(67))<=0){

                        maps.get(2).replace("num",Integer.parseInt(maps.get(2).get("num").toString())+1);
                        maps.get(2).replace("totalMoney",new BigDecimal(maps.get(2).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(68))>=0 && money.compareTo(new BigDecimal(97))<=0){

                        maps.get(3).replace("num",Integer.parseInt(maps.get(3).get("num").toString())+1);
                        maps.get(3).replace("totalMoney",new BigDecimal(maps.get(3).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(98))>=0 && money.compareTo(new BigDecimal(127))<=0){

                        maps.get(4).replace("num",Integer.parseInt(maps.get(4).get("num").toString())+1);
                        maps.get(4).replace("totalMoney",new BigDecimal(maps.get(4).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(128))>=0 && money.compareTo(new BigDecimal(327))<=0){

                        maps.get(5).replace("num",Integer.parseInt(maps.get(5).get("num").toString())+1);
                        maps.get(5).replace("totalMoney",new BigDecimal(maps.get(5).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(328))>=0 && money.compareTo(new BigDecimal(647))<=0){

                        maps.get(6).replace("num",Integer.parseInt(maps.get(6).get("num").toString())+1);
                        maps.get(6).replace("totalMoney",new BigDecimal(maps.get(6).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(648))>=0 && money.compareTo(new BigDecimal(999))<=0){

                        maps.get(7).replace("num",Integer.parseInt(maps.get(7).get("num").toString())+1);
                        maps.get(7).replace("totalMoney",new BigDecimal(maps.get(7).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(1000))>=0 && money.compareTo(new BigDecimal(1999))<=0){

                        maps.get(8).replace("num",Integer.parseInt(maps.get(8).get("num").toString())+1);
                        maps.get(8).replace("totalMoney",new BigDecimal(maps.get(8).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(2000))>=0 && money.compareTo(new BigDecimal(4999))<=0){

                        maps.get(9).replace("num",Integer.parseInt(maps.get(9).get("num").toString())+1);
                        maps.get(9).replace("totalMoney",new BigDecimal(maps.get(9).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(5000))>=0){
                        maps.get(10).replace("num",Integer.parseInt(maps.get(10).get("num").toString())+1);
                        maps.get(10).replace("totalMoney",new BigDecimal(maps.get(10).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);
                    }
                }
            }else if (true ||ObjectUtil.equal(2,userOrderDistributionDto.getMode())){
                for (UserOrderDataVo userOrderDataVo : userOrderDataVos) {
                    BigDecimal money = userOrderDataVo.getMoney();
                    if (money.compareTo(new BigDecimal(6))==0){

                        maps.get(0).replace("num",Integer.parseInt(maps.get(0).get("num").toString())+1);
                        maps.get(0).replace("totalMoney",new BigDecimal(maps.get(0).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(7))>=0 && money.compareTo(new BigDecimal(30))<=0){

                        maps.get(1).replace("num",Integer.parseInt(maps.get(1).get("num").toString())+1);
                        maps.get(1).replace("totalMoney",new BigDecimal(maps.get(1).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(31))>=0 && money.compareTo(new BigDecimal(68))<=0){

                        maps.get(2).replace("num",Integer.parseInt(maps.get(2).get("num").toString())+1);
                        maps.get(2).replace("totalMoney",new BigDecimal(maps.get(2).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(69))>=0 && money.compareTo(new BigDecimal(128))<=0){

                        maps.get(3).replace("num",Integer.parseInt(maps.get(3).get("num").toString())+1);
                        maps.get(3).replace("totalMoney",new BigDecimal(maps.get(3).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(129))>=0 && money.compareTo(new BigDecimal(198))<=0){

                        maps.get(4).replace("num",Integer.parseInt(maps.get(4).get("num").toString())+1);
                        maps.get(4).replace("totalMoney",new BigDecimal(maps.get(4).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(199))>=0 && money.compareTo(new BigDecimal(328))<=0){

                        maps.get(5).replace("num",Integer.parseInt(maps.get(5).get("num").toString())+1);
                        maps.get(5).replace("totalMoney",new BigDecimal(maps.get(5).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(328))>=0 && money.compareTo(new BigDecimal(648))<=0){

                        maps.get(6).replace("num",Integer.parseInt(maps.get(6).get("num").toString())+1);
                        maps.get(6).replace("totalMoney",new BigDecimal(maps.get(6).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(649))>=0 && money.compareTo(new BigDecimal(1000))<=0){

                        maps.get(7).replace("num",Integer.parseInt(maps.get(7).get("num").toString())+1);
                        maps.get(7).replace("totalMoney",new BigDecimal(maps.get(7).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(1001))>=0 && money.compareTo(new BigDecimal(2999))<=0){

                        maps.get(8).replace("num",Integer.parseInt(maps.get(8).get("num").toString())+1);
                        maps.get(8).replace("totalMoney",new BigDecimal(maps.get(8).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(3000))>=0 && money.compareTo(new BigDecimal(4999))<=0){

                        maps.get(9).replace("num",Integer.parseInt(maps.get(9).get("num").toString())+1);
                        maps.get(9).replace("totalMoney",new BigDecimal(maps.get(9).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);

                    }else if (money.compareTo(new BigDecimal(5000))>=0){
                        maps.get(10).replace("num",Integer.parseInt(maps.get(10).get("num").toString())+1);
                        maps.get(10).replace("totalMoney",new BigDecimal(maps.get(10).get("totalMoney").toString()).add(money));
                        totalNum++;
                        totalMoney=totalMoney.add(money);
                    }
                }
            }
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);

            for (Map<String, Object> map : maps) {
                if (Integer.parseInt(map.get("num").toString())>0){

                    Double num = Double.parseDouble(map.get("num").toString());
                    BigDecimal money = new BigDecimal(map.get("totalMoney").toString());

                    map.replace("ratio",nf.format(num/totalNum*100)+"%");
                    map.replace("payRatio",nf.format(money.divide(totalMoney,4,RoundingMode.HALF_UP).doubleValue()*100)+"%");
                    map.replace("avg",money.divide(new BigDecimal(num),2,RoundingMode.HALF_UP));
                }
            }
            if (totalNum!=0){
                arpu=totalMoney.divide(new BigDecimal(totalNum),2,RoundingMode.HALF_UP);
            }
        }

        ListUserOrderDistributionVo assignment = assignment(maps, arpu, userOrderDistributionDto.getMode());

        return assignment;
    }

    @Override
    public ListUserOrderDistributionVo userAccumulatePay(UserOrderDistributionDto userOrderDistributionDto){
        ListUserOrderDistributionVo listUserOrderDistributionVo = this
            .userOrderDistribution(userOrderDistributionDto, false);
        return listUserOrderDistributionVo;
    }

    public List<Map<String,Object>> initData(){
        ArrayList<Map<String,Object>> arrayList = new ArrayList<>();
        for (int i=0;i<11;i++){
            Map<String, Object> objectObjectMap = new LinkedHashMap<>();
            objectObjectMap.put("num",0);
            objectObjectMap.put("ratio","0.00%");
            objectObjectMap.put("totalMoney",0);
            objectObjectMap.put("avg",0);
            objectObjectMap.put("payRatio","0.00%");

            arrayList.add(objectObjectMap);
        }
        return arrayList;
    }

    public ListUserOrderDistributionVo assignment(List<Map<String,Object>> directionDatas,BigDecimal arpu,Integer mode){

        ArrayList<Map<String,Object>> transverseData = new ArrayList<>();
        ArrayList<Map<String,Object>> graphical = new ArrayList<>();
        ListUserOrderDistributionVo listUserOrderDistributionVo = new ListUserOrderDistributionVo();

        Integer flag=1;
        LinkedHashMap<String, Object> numMap = new LinkedHashMap<>();
        LinkedHashMap<String, Object> ratioMap = new LinkedHashMap<>();
        LinkedHashMap<String, Object> totalMoneyMap = new LinkedHashMap<>();
        LinkedHashMap<String, Object> avgMap = new LinkedHashMap<>();
        LinkedHashMap<String, Object> payRatioMap = new LinkedHashMap<>();
        for (Map<String, Object> directionData : directionDatas) {
            Object num = directionData.get("num");
            Object ratio = directionData.get("ratio");
            Object totalMoney = directionData.get("totalMoney");
            Object avg = directionData.get("avg");
            Object payRatio = directionData.get("payRatio");

            numMap.put("Interval"+flag,num);
            ratioMap.put("Interval"+flag,ratio);
            totalMoneyMap.put("Interval"+flag,totalMoney);
            avgMap.put("Interval"+flag,avg);
            payRatioMap.put("Interval"+flag,payRatio);

            flag++;
        }
        numMap.put("arpu",arpu);
        ratioMap.put("arpu",arpu);
        totalMoneyMap.put("arpu",arpu);
        avgMap.put("arpu",arpu);
        payRatioMap.put("arpu",arpu);

        transverseData.add(numMap);
        transverseData.add(ratioMap);
        transverseData.add(avgMap);
        transverseData.add(totalMoneyMap);
        transverseData.add(payRatioMap);

        if (mode==null){
            throw new RuntimeException("广告模式不可以为空");
        }

        if (mode==1){
            ArrayList<String> intervalName = new ArrayList<>();
            intervalName.add("2-10元");
            intervalName.add("11-29元");
            intervalName.add("30-67元");
            intervalName.add("68-97元");
            intervalName.add("98-127元");
            intervalName.add("128-327元");
            intervalName.add("328-647元");
            intervalName.add("648-999元");
            intervalName.add("1000-1999元");
            intervalName.add("2000-4999元");
            intervalName.add("5000+元");

            Integer interval=0;
            for (Object value:payRatioMap.values()){
                if (!ObjectUtil.equal("0.00%",String.valueOf(value)) && interval<=10){
                    HashMap<String, Object> intervalhashMap = new HashMap<>();
                    intervalhashMap.put("name",intervalName.get(interval));
                    intervalhashMap.put("value",value.toString().split("%"));
                    graphical.add(intervalhashMap);
                }
                interval++;
            }
        }else if (true || mode==2){
            ArrayList<String> intervalName = new ArrayList<>();
            intervalName.add("6元");
            intervalName.add("7-30元");
            intervalName.add("31-68元");
            intervalName.add("69-128元");
            intervalName.add("129-198元");
            intervalName.add("199-328元");
            intervalName.add("329-648元");
            intervalName.add("649-1000元");
            intervalName.add("1001-2999元");
            intervalName.add("3000-4999元");
            intervalName.add("5000+元");

            Integer interval=0;
            for (Object value:payRatioMap.values()){
                if (!ObjectUtil.equal("0.00%",String.valueOf(value)) && interval<=10){
                    HashMap<String, Object> intervalhashMap = new HashMap<>();
                    intervalhashMap.put("name",intervalName.get(interval));
                    intervalhashMap.put("value",value.toString().split("%"));
                    graphical.add(intervalhashMap);
                }
                interval++;
            }
        }
        listUserOrderDistributionVo.setAssignment(transverseData);
        listUserOrderDistributionVo.setGraphical(graphical);
        return listUserOrderDistributionVo;
    }


    @Override
    public UserFirstPayVo getUserAccumulateLevel(
        UserAccumulateLevelDto userAccumulateLevelDto){

        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(userAccumulateLevelDto.getDealId())){
            queryWrapper.in("co.deal_id",userAccumulateLevelDto.getDealId());
        }

        if (ObjectUtils.isNotEmpty(userAccumulateLevelDto.getGameId())){
            queryWrapper.in("co.game_id",userAccumulateLevelDto.getGameId());
        }

        if (ObjectUtils.isNotEmpty(userAccumulateLevelDto.getSubGameId())){
            queryWrapper.in("co.sub_game_id",userAccumulateLevelDto.getSubGameId());
        }

        if (ObjectUtils.isNotEmpty(userAccumulateLevelDto.getPkgId())){
            queryWrapper.in("co.pkg_id",userAccumulateLevelDto.getPkgId());
        }

        if (ObjectUtils.isNotEmpty(userAccumulateLevelDto.getChannelTypeId())){
            queryWrapper.in("co.channel_type_id",userAccumulateLevelDto.getChannelTypeId());
        }

        if (ObjectUtils.isNotEmpty(userAccumulateLevelDto.getChannelId())){
            queryWrapper.in("co.channel_id",userAccumulateLevelDto.getChannelId());
        }

        if (ObjectUtils.isNotEmpty(userAccumulateLevelDto.getChannelSubAccountId())) {
            queryWrapper.in("co.channel_sub_account_id",
                userAccumulateLevelDto.getChannelSubAccountId());
        }

        if (ObjectUtils.isNotEmpty(userAccumulateLevelDto.getUserStartTime())){
            queryWrapper.ge("co.user_create_time",userAccumulateLevelDto.getUserStartTime());
        }

        if (ObjectUtils.isNotEmpty(userAccumulateLevelDto.getUserEndTime())) {
            queryWrapper.le("co.user_create_time", userAccumulateLevelDto.getUserEndTime());
        }


        queryWrapper.groupBy("co.pkg_id","co.user_id");

        List<UserAccumulateLevelVo> userAccumulateLevelVos = userDataMapper
            .getUserAccumulateLevel(queryWrapper);

        TreeMap<String, Object> userMap = new TreeMap<>();
        TreeMap<String, Object> onlineTimeMap = new TreeMap<>();

        Integer count=0;
        UserFirstPayVo userFirstPayVo = new UserFirstPayVo();
        ArrayList<Map<String,Object>> levelMaps = new ArrayList<>();
        ArrayList<Map<String,Object>> ratioLevelMaps = new ArrayList<>();
        ArrayList<Map<String,Object>> timeMaps = new ArrayList<>();
        ArrayList<Map<String,Object>> ratioTimeMaps = new ArrayList<>();

        if (ObjectUtils.isNotEmpty(userAccumulateLevelVos)){

            for (UserAccumulateLevelVo userAccumulateLevelVo : userAccumulateLevelVos) {
                String roleLevel = userAccumulateLevelVo.getRoleLevel();
                String onlineTime = userAccumulateLevelVo.getOnlineTime();

                if (Integer.parseInt(roleLevel)>0){
                    if (userMap.containsKey(roleLevel)){
                        userMap.replace(roleLevel,Integer.parseInt(userMap.get(roleLevel).toString())+1);
                    }else{
                        userMap.put(roleLevel,1);
                    }

                    if (onlineTimeMap.containsKey(onlineTime)){
                        onlineTimeMap.replace(onlineTime,Integer.parseInt(onlineTimeMap.get(onlineTime).toString())+1);
                    }else {
                        onlineTimeMap.put(onlineTime,1);
                    }
                    count++;
                }

            }

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);

            for (Entry<String, Object> entry : userMap.entrySet()) {
                LinkedHashMap<String, Object> levelMap = new LinkedHashMap<>();
                LinkedHashMap<String, Object> ratioLevelMap = new LinkedHashMap<>();
                levelMap.put("level",entry.getKey()+"级");
                levelMap.put("num",entry.getValue());
                Double ratio = Double.valueOf(entry.getValue().toString())/count;
                levelMap.put("ratio",nf.format(ratio*100)+'%');
                levelMaps.add(levelMap);

                ratioLevelMap.put("name",entry.getKey()+"级");
                ratioLevelMap.put("value",nf.format(ratio*100));
                ratioLevelMaps.add(ratioLevelMap);
            }



            for (Entry<String, Object> entry : onlineTimeMap.entrySet()) {
                LinkedHashMap<String, Object> timeMap = new LinkedHashMap<>();
                LinkedHashMap<String, Object> ratioTimeMap = new LinkedHashMap<>();

                String time = entry.getKey();
                time=countTime(Integer.parseInt(time));



                if (!ObjectUtil.equal(null,time) && !ObjectUtil.equal("",time)){
                    timeMap.put("time",time);
                    timeMap.put("num",entry.getValue());
                    Double ratio = Double.valueOf(entry.getValue().toString())/count;
                    timeMap.put("ratio",nf.format(ratio*100)+'%');
                    timeMaps.add(timeMap);

                    ratioTimeMap.put("name",time);
                    ratioTimeMap.put("value",nf.format(ratio*100));
                    ratioTimeMaps.add(ratioTimeMap);
                }else {
                    timeMap.put("time","3分钟以内");
                    timeMap.put("num",entry.getValue());
                    Double ratio = Double.valueOf(entry.getValue().toString())/count;
                    timeMap.put("ratio",nf.format(ratio*100)+'%');
                    timeMaps.add(timeMap);

                    ratioTimeMap.put("name","3分钟以内");
                    ratioTimeMap.put("value",nf.format(ratio*100));
                    ratioTimeMaps.add(ratioTimeMap);
                }

            }
        }

        userFirstPayVo.setLevelMaps(levelMaps);
        userFirstPayVo.setRatioLevelMaps(ratioLevelMaps);
        userFirstPayVo.setTimeMaps(timeMaps);
        userFirstPayVo.setRatioTimeMaps(ratioTimeMaps);
        return userFirstPayVo;

    }

    public String countTime(Integer time){

        StringBuilder stringBuilder = new StringBuilder();
        if(time >= 31556926){
            stringBuilder.append(time/31556926+"年");
            time = (time%31556926);
        }
        if(time >= 86400){
            stringBuilder.append(time/86400+"天");
            time = (time%86400);
        }
        if(time >= 3600){
            stringBuilder.append(time/3600+"小时");
            time = (time%3600);
        }
        if(time >= 60){
            stringBuilder.append(time/60+"分钟");
        }

        return  stringBuilder.toString();
    }


    @Override
    public UserTwoPayVo getPayUserTwoPay(UserTwoPayDto userTwoPayDto){
        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(userTwoPayDto.getDealId())){
            queryWrapper.in("co.deal_id",userTwoPayDto.getDealId());
        }

        if (ObjectUtils.isNotEmpty(userTwoPayDto.getGameId())){
            queryWrapper.in("co.game_id",userTwoPayDto.getGameId());
        }

        if (ObjectUtils.isNotEmpty(userTwoPayDto.getSubGameId())){
            queryWrapper.in("co.sub_game_id",userTwoPayDto.getSubGameId());
        }

        if (ObjectUtils.isNotEmpty(userTwoPayDto.getPkgId())){
            queryWrapper.in("co.pkg_id",userTwoPayDto.getPkgId());
        }

        if (ObjectUtils.isNotEmpty(userTwoPayDto.getChannelTypeId())){
            queryWrapper.in("co.channel_type_id",userTwoPayDto.getChannelTypeId());
        }

        if (ObjectUtils.isNotEmpty(userTwoPayDto.getChannelId())){
            queryWrapper.in("co.channel_id",userTwoPayDto.getChannelId());
        }

        if (ObjectUtils.isNotEmpty(userTwoPayDto.getChannelSubAccountId())) {
            queryWrapper.in("co.channel_sub_account_id",
                userTwoPayDto.getChannelSubAccountId());
        }

        if (ObjectUtils.isNotEmpty(userTwoPayDto.getUserStartTime())){
            queryWrapper.ge("co.user_create_time",userTwoPayDto.getUserStartTime());
        }

        if (ObjectUtils.isNotEmpty(userTwoPayDto.getUserEndTime())) {
            queryWrapper.le("co.user_create_time", userTwoPayDto.getUserEndTime());
        }

        UserTwoPayVo userTwoPayVo = new UserTwoPayVo();
        userTwoPayVo.setPayUserOne(userDataMapper.selectPayUserOne(queryWrapper));
        userTwoPayVo.setTwoPayUserOne(userDataMapper.selectTwoPayUserOne(queryWrapper));

        userTwoPayVo.setPayUserTwo(userDataMapper.selectPayUserTwo(queryWrapper));
        userTwoPayVo.setTwoPayUserTwo(userDataMapper.selectTwoPayUserTwo(queryWrapper));

        userTwoPayVo.setPayUserThird(userDataMapper.selectPayUserThird(queryWrapper));
        userTwoPayVo.setTwoPayUserThird(userDataMapper.selectTwoPayUserThird(queryWrapper));

        userTwoPayVo.setPayUserFour(userDataMapper.selectPayUserFour(queryWrapper));
        userTwoPayVo.setTwoPayUserFour(userDataMapper.selectTwoPayUserFour(queryWrapper));

        userTwoPayVo.setPayUserFive(userDataMapper.selectPayUserFive(queryWrapper));
        userTwoPayVo.setTwoPayUserFive(userDataMapper.selectTwoPayUserFive(queryWrapper));

        userTwoPayVo.setPayUserSix(userDataMapper.selectPayUserSix(queryWrapper));
        userTwoPayVo.setTwoPayUserSix(userDataMapper.selectTwoPayUserSix(queryWrapper));

        userTwoPayVo.setPayUserSeven(userDataMapper.selectPayUserSeven(queryWrapper));
        userTwoPayVo.setTwoPayUserSeven(userDataMapper.selectTwoPayUserSeven(queryWrapper));

        return userTwoPayVo;
    }

    @Override
    public ArrayList<Map> getPayUserStruct(PayUserStructDto payUserStructDto){
        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(payUserStructDto.getDealId())){
            queryWrapper.in("co.deal_id",payUserStructDto.getDealId());
        }

        if (ObjectUtils.isNotEmpty(payUserStructDto.getGameId())){
            queryWrapper.in("co.game_id",payUserStructDto.getGameId());
        }

        if (ObjectUtils.isNotEmpty(payUserStructDto.getSubGameId())){
            queryWrapper.in("co.sub_game_id",payUserStructDto.getSubGameId());
        }

        if (ObjectUtils.isNotEmpty(payUserStructDto.getPkgId())){
            queryWrapper.in("co.pkg_id",payUserStructDto.getPkgId());
        }

        if (ObjectUtils.isNotEmpty(payUserStructDto.getChannelTypeId())){
            queryWrapper.in("co.channel_type_id",payUserStructDto.getChannelTypeId());
        }

        if (ObjectUtils.isNotEmpty(payUserStructDto.getChannelId())){
            queryWrapper.in("co.channel_id",payUserStructDto.getChannelId());
        }

        if (ObjectUtils.isNotEmpty(payUserStructDto.getChannelSubAccountId())) {
            queryWrapper.in("co.channel_sub_account_id",
                payUserStructDto.getChannelSubAccountId());
        }

        if (ObjectUtils.isNotEmpty(payUserStructDto.getUserStartTime())){
            queryWrapper.ge("co.user_create_time",payUserStructDto.getUserStartTime());
        }

        if (ObjectUtils.isNotEmpty(payUserStructDto.getUserEndTime())) {
            queryWrapper.le("co.user_create_time", payUserStructDto.getUserEndTime());
        }

        queryWrapper.apply("DATE_FORMAT(co.create_time,'%Y-%m-%d')=DATE_FORMAT(co.user_create_time,'%Y-%m-%d')");

        queryWrapper.groupBy("userCreateTime","co.user_id","co.pkg_id");
        queryWrapper.orderByDesc("userCreateTime","co.user_id","co.pkg_id");

        List<PayUserStructDataVo> payUserStructs = userDataMapper.getPayUserStruct(queryWrapper);


        HashMap<String, Map<String,Integer>> resultMap = new LinkedHashMap<>();


        for (PayUserStructDataVo payUserStruct : payUserStructs) {

            BigDecimal addNum = new BigDecimal(0);

            String userCreateTime = payUserStruct.getUserCreateTime();
            BigDecimal money = payUserStruct.getMoney();

            if (resultMap.get(userCreateTime)==null){
                HashMap<String, Integer> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("count",0);
                objectObjectHashMap.put("oneInterval",0);
                objectObjectHashMap.put("twoInterval",0);
                objectObjectHashMap.put("thirdInterval",0);
                objectObjectHashMap.put("fourInterval",0);
                objectObjectHashMap.put("fiveInterval",0);
                objectObjectHashMap.put("sixInterval",0);
                objectObjectHashMap.put("sevenInterval",0);
                objectObjectHashMap.put("eightInterval",0);
                objectObjectHashMap.put("nineInterval",0);
                objectObjectHashMap.put("tenInterval",0);
                objectObjectHashMap.put("elevenInterval",0);
                resultMap.put(userCreateTime,objectObjectHashMap);
            }

            if (ObjectUtil.equal("1",payUserStructDto.getDataType())){
                addNum=addNum.add(new BigDecimal(1));
            }else {
                addNum=addNum.add(money);
            }

            if (money.intValue()>=1){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("count")!=null ){
                    resultMap.get(userCreateTime).put("count",resultMap.get(userCreateTime).get("count")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("count",addNum.intValue());
                }

            }

            if (money.intValue()>=1 && money.intValue()<=5){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("firstInterval")!=null ){
                    resultMap.get(userCreateTime).put("firstInterval",resultMap.get(userCreateTime).get("firstInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("firstInterval",addNum.intValue());
                }


            }else if (money.intValue()>=6 && money.intValue()<=29){


                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("twoInterval")!=null ){
                    resultMap.get(userCreateTime).put("twoInterval",resultMap.get(userCreateTime).get("twoInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("twoInterval",addNum.intValue());
                }


            }else if (money.intValue()>=30 && money.intValue()<=67){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("thirdInterval")!=null ){
                    resultMap.get(userCreateTime).put("thirdInterval",resultMap.get(userCreateTime).get("thirdInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("thirdInterval",addNum.intValue());
                }


            }else if (money.intValue()>=68 && money.intValue()<=127){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("fourInterval")!=null ){
                    resultMap.get(userCreateTime).put("fourInterval",resultMap.get(userCreateTime).get("fourInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("fourInterval",addNum.intValue());
                }


            }else if (money.intValue()>=128 && money.intValue()<=197){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("fiveInterval")!=null ){
                    resultMap.get(userCreateTime).put("fiveInterval",resultMap.get(userCreateTime).get("fiveInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("fiveInterval",addNum.intValue());
                }


            }else if (money.intValue()>=198 && money.intValue()<=327){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("sixInterval")!=null ){
                    resultMap.get(userCreateTime).put("sixInterval",resultMap.get(userCreateTime).get("sixInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("sixInterval",addNum.intValue());
                }


            }else if (money.intValue()>=328 && money.intValue()<=647){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("sevenInterval")!=null ){
                    resultMap.get(userCreateTime).put("sevenInterval",resultMap.get(userCreateTime).get("sevenInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("sevenInterval",addNum.intValue());
                }


            }else if (money.intValue()>=648 && money.intValue()<=999){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("eightInterval")!=null ){
                    resultMap.get(userCreateTime).put("eightInterval",resultMap.get(userCreateTime).get("eightInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("eightInterval",addNum.intValue());
                }

            }else if (money.intValue()>=1000 && money.intValue()<=1999){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("nineInterval")!=null ){
                    resultMap.get(userCreateTime).put("nineInterval",resultMap.get(userCreateTime).get("nineInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("nineInterval",addNum.intValue());
                }

            }else if (money.intValue()>=2000 && money.intValue()<=4999){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("tenInterval")!=null ){
                    resultMap.get(userCreateTime).put("tenInterval",resultMap.get(userCreateTime).get("tenInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("tenInterval",addNum.intValue());
                }

            }else if (money.intValue()>=5000){

                if (resultMap.get(userCreateTime)!=null && resultMap.get(userCreateTime).get("elevenInterval")!=null ){
                    resultMap.get(userCreateTime).put("elevenInterval",resultMap.get(userCreateTime).get("elevenInterval")+addNum.intValue());
                }else {
                    resultMap.get(userCreateTime).put("elevenInterval",addNum.intValue());
                }
            }
        }

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        ArrayList<Map> result = new ArrayList<>();
        for (Entry<String, Map<String, Integer>> stringMapEntry : resultMap.entrySet()) {

            Map<String, Integer> value = stringMapEntry.getValue();
            Integer count = value.get("count");

            HashMap<String, Object> structMap = new HashMap<>();
            structMap.put("createTime",stringMapEntry.getKey());
            for (Entry<String, Integer> stringIntegerEntry : value.entrySet()) {

                if (!ObjectUtil.equal("count",stringIntegerEntry.getKey())){
                    Double interval = Double.valueOf(stringIntegerEntry.getValue());

                    structMap.put(stringIntegerEntry.getKey(),nf.format(interval/count*100)+"%");
                }
            }
            result.add(structMap);
        }


        return result;
    }


}
