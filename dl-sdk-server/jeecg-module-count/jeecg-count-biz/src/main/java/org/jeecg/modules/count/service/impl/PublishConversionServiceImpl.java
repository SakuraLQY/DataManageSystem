package org.jeecg.modules.count.service.impl;


import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jeecg.modules.count.bo.ConversionBo;
import org.jeecg.modules.count.dto.PublishConversionDto;
import org.jeecg.modules.count.entity.PublishConversion;
import org.jeecg.modules.count.mapper.PublishConversionMapper;
import org.jeecg.modules.count.service.IPublishConversionService;
import org.jeecg.modules.count.vo.PublishConversionVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: publish_conversion
 * @Author: jeecg-boot
 * @Date: 2023-05-24
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class PublishConversionServiceImpl extends
    ServiceImpl<PublishConversionMapper, PublishConversion> implements IPublishConversionService {

    @Resource
    private PublishConversionMapper publishConversionMapper;

    @Override
    public List<PublishConversionVo> queryConversionList(
        PublishConversionDto publishConversionDto) {
        QueryWrapper where = new QueryWrapper();
        if (ObjectUtils.isNotEmpty(publishConversionDto.getGameId())) {
            where.in("game_id", publishConversionDto.getGameId());
        }
        if (ObjectUtils.isNotEmpty(publishConversionDto.getSubGameId())) {
            where.in("sub_game_id", publishConversionDto.getSubGameId());
        }
        if (ObjectUtils.isNotEmpty(publishConversionDto.getPkgId())) {
            where.in("pkg_id", publishConversionDto.getPkgId());
        }
        Date startDate = null;
        Date endDate = null;
        try {
            if (null != publishConversionDto.getStartTime()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(publishConversionDto.getStartTime(),
                        "yyyy-MM-dd 00:00:00"));
                where.ge("time_daily", startDate);
            }
            if (null != publishConversionDto.getEndTime()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(publishConversionDto.getEndTime(),
                        "yyyy-MM-dd 23:59:59"));
                where.le("time_daily", endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //根据判断条件进行选择
        switch (publishConversionDto.getType()) {
            case " ":
                where.groupBy("time_daily");
                break;
            case "deal_id":
                where.groupBy("deal_id");
                break;
            case "channel_id":
                where.groupBy("channel_id");
                break;
            case "game_id":
                where.groupBy("game_id");
                break;
            case "sub_game_id":
                where.groupBy("sub_game_id");
                break;
            case "pkg_id":
                where.groupBy("pkg_id");
                break;
            default:
                where.groupBy("time_daily");
        }
        //定义接受的List
        List<PublishConversionVo> resList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 如果是查询全部，ID和名称展示的是日期，动态传参数
        List<ConversionBo> conversionBoList = null;
        if (publishConversionDto.getType().equals(" ")) {
            conversionBoList = publishConversionMapper.queryConversionByType( where);
        } else {
            conversionBoList = publishConversionMapper.queryConversionByTypeOther(publishConversionDto.getType(), where);
        }
        if (conversionBoList != null) {
            for (ConversionBo conversionBo : conversionBoList) {
                PublishConversionVo conversionVo = new PublishConversionVo();
                //ID的设置
                if (publishConversionDto.getType().equals(" ")) {
                    //拿到对应的日期
                        String dateTime = String.valueOf(DateUtil.format(conversionBo.getDateTime(),"yyyy-MM-dd"));
                        conversionVo.setTypeId(dateTime);
                        conversionVo.setName(dateTime);

                } else {
                    //根据对应的进行查询不同的名字
                    switch (publishConversionDto.getType()) {
                        case "deal_id":
                            String dealName =  publishConversionMapper.selectByDealId(conversionBo.getId());
                            conversionVo.setName(dealName);
                            conversionVo.setTypeId(String.valueOf(conversionBo.getId()));
                            break;
                        case "channel_id":
                            String channelName = publishConversionMapper.selectByChannelId(conversionBo.getId());
                            conversionVo.setName(channelName);
                            conversionVo.setTypeId(String.valueOf(conversionBo.getId()));
                            break;
                        case "game_id":
                            String gameName = publishConversionMapper.selectByGameId(conversionBo.getId());
                            conversionVo.setName(gameName);
                            conversionVo.setTypeId(String.valueOf(conversionBo.getId()));
                            break;
                        case "sub_game_id":
                            String subGameName = publishConversionMapper.selectBySubGameId(conversionBo.getId());
                            conversionVo.setName(subGameName);
                            conversionVo.setTypeId(String.valueOf(conversionBo.getId()));
                            break;
                        case "pkg_id":
                            String pkgName=publishConversionMapper.selectByPkgId(conversionBo.getId());
                            conversionVo.setName(pkgName);
                            conversionVo.setTypeId(String.valueOf(conversionBo.getId()));
                            break;
                        default:
                            try {
                                conversionVo.setName(DateUtil.format(sdf.parse(String.valueOf(conversionBo.getDateTime())), "yyyy-MM-dd"));
                                conversionVo.setTypeId(String.valueOf(conversionBo.getId()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                    }
                }
                //激活数
                conversionVo.setActive(conversionBo.getActive());
                //激活设备数
                conversionVo.setActiveDev(conversionBo.getActiveDev());
                //注册数
                conversionVo.setRegCount(conversionBo.getRegCount());
                //注册设备数
                conversionVo.setRegCountDev(conversionBo.getRegCountDev());
                //激活注册率
                if(ObjectUtils.isNotEmpty(conversionBo.getActiveDev()) && ObjectUtils.isNotEmpty(conversionBo.getRegCountDev()) && conversionBo.getActiveDev()>0){
                    BigDecimal activeRegRate = BigDecimal.valueOf(conversionBo.getRegCountDev())
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(conversionBo.getActiveDev()),2, RoundingMode.HALF_UP);
                    conversionVo.setActiveRegRate(activeRegRate+"%");
                }else{
                    conversionVo.setActiveRegRate("0");
                }
                conversionVo.setFirstPayUser(conversionBo.getFirstPayUser());
                conversionVo.setValidUser(conversionBo.getCountDau());
                resList.add(conversionVo);
            }
        }
        return resList;
    }
}
