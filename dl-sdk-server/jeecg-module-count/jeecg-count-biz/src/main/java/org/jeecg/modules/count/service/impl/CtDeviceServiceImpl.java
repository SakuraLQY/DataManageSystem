package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.kafka.dto.ParseRegisterDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.mapper.CtDeviceMapper;
import org.jeecg.modules.count.service.ICtDeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: ct_device
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class CtDeviceServiceImpl extends ServiceImpl<CtDeviceMapper, CtDevice> implements ICtDeviceService {

    @Autowired
    private CtDeviceMapper ctDeviceMapper;

    @Override
    public CtDevice searchStartDevice(ParseStartDto parseStartDto){

        LambdaQueryWrapper<CtDevice> ctDeviceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDeviceLambdaQueryWrapper.eq(CtDevice::getDealId,parseStartDto.getDealId());
        ctDeviceLambdaQueryWrapper.eq(CtDevice::getUniqueId,parseStartDto.getUniqueId());
        CtDevice ctDevice = ctDeviceMapper.selectOne(ctDeviceLambdaQueryWrapper);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=simpleDateFormat.format(parseStartDto.getTime());
        Date day=DateUtils.str2Date(date,simpleDateFormat);

        CtDevice resultCtDevice = new CtDevice();

        if (ObjectUtils.isEmpty(ctDevice)){
            CtDevice insertCtDevice = new CtDevice();
            insertCtDevice.setGameId(parseStartDto.getGameId());
            insertCtDevice.setSubGameId(parseStartDto.getSubGameId());
            insertCtDevice.setChannelId(parseStartDto.getChannelId());
            insertCtDevice.setChannelTypeId(parseStartDto.getChannelTypeId());
            insertCtDevice.setChannelSubAccountId(parseStartDto.getChannelSubAccountId());
            insertCtDevice.setDealId(parseStartDto.getDealId());
            insertCtDevice.setUpdateTime(day);
            insertCtDevice.setCreateTime(day);
            insertCtDevice.setUniqueId(parseStartDto.getUniqueId());
            insertCtDevice.setPkgId(parseStartDto.getPkgId());

            //返回旧数据
            BeanUtils.copyProperties(insertCtDevice,resultCtDevice);

            insertCtDevice.setStartupTime(day);
            insertCtDevice.setClientIp(parseStartDto.getClientIp());
            insertCtDevice.setDeviceId(parseStartDto.getDeviceId());
            insertCtDevice.setSerialId(parseStartDto.getSerialId());
            insertCtDevice.setAndroidId(parseStartDto.getAndroidId());
            insertCtDevice.setDevOs(parseStartDto.getDevOs());
            insertCtDevice.setDevOsVer(parseStartDto.getDevOsVer());
            insertCtDevice.setDevModel(parseStartDto.getDevModel());
            insertCtDevice.setPkgName(parseStartDto.getPkgName());
            insertCtDevice.setPkgVersionCode(parseStartDto.getPkgVersionCode());
            insertCtDevice.setPkgVersionName(parseStartDto.getPkgVersionName());
            insertCtDevice.setSdkVersion(parseStartDto.getSdkVersion());

            ctDeviceMapper.insert(insertCtDevice);


        }else {

            if (ObjectUtils.isEmpty(ctDevice.getStartupTime())){
                ctDevice.setStartupTime(day);
            }
            BeanUtils.copyProperties(ctDevice,resultCtDevice);

            ctDevice.setClientIp(parseStartDto.getClientIp());
            ctDevice.setPkgName(parseStartDto.getPkgName());
            ctDevice.setPkgVersionCode(parseStartDto.getPkgVersionCode());
            ctDevice.setPkgVersionName(parseStartDto.getPkgVersionName());
            ctDevice.setSdkVersion(parseStartDto.getSdkVersion());
            ctDevice.setUpdateTime(day);

            ctDeviceMapper.updateById(ctDevice);
        }



        return resultCtDevice;
    }

    @Override
    public CtDevice searchLoginDevice(ParseLoginDto parseLoginDto){
        LambdaQueryWrapper<CtDevice> ctDeviceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDeviceLambdaQueryWrapper.eq(CtDevice::getDealId,parseLoginDto.getDealId());
        ctDeviceLambdaQueryWrapper.eq(CtDevice::getUniqueId,parseLoginDto.getUniqueId());
        CtDevice ctDevice = ctDeviceMapper.selectOne(ctDeviceLambdaQueryWrapper);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=simpleDateFormat.format(parseLoginDto.getTime());
        Date day=DateUtils.str2Date(date,simpleDateFormat);

        CtDevice resultCtDevice = new CtDevice();

        if (ObjectUtils.isEmpty(ctDevice)) {
            CtDevice insertCtDevice = new CtDevice();
            insertCtDevice.setGameId(parseLoginDto.getGameId());
            insertCtDevice.setSubGameId(parseLoginDto.getSubGameId());
            insertCtDevice.setChannelId(parseLoginDto.getChannelId());
            insertCtDevice.setChannelTypeId(parseLoginDto.getChannelTypeId());
            insertCtDevice.setChannelSubAccountId(parseLoginDto.getChannelSubAccountId());
            insertCtDevice.setDealId(parseLoginDto.getDealId());
            insertCtDevice.setUpdateTime(day);
            insertCtDevice.setCreateTime(day);
            insertCtDevice.setUniqueId(parseLoginDto.getUniqueId());
            insertCtDevice.setPkgId(parseLoginDto.getPkgId());

            //拷贝数据
            BeanUtils.copyProperties(insertCtDevice,resultCtDevice);
            insertCtDevice.setLoginTime(day);

            ctDeviceMapper.insert(insertCtDevice);

        }else {

            //拷贝旧数据
            BeanUtils.copyProperties(ctDevice,resultCtDevice);

            ctDevice.setLoginTime(day);
            ctDevice.setUpdateTime(day);
            ctDeviceMapper.updateById(ctDevice);
        }

        return resultCtDevice;
    }

    @Override
    public void searchRegisterDevice(ParseRegisterDto parseRegisterDto){
        LambdaQueryWrapper<CtDevice> ctDeviceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDeviceLambdaQueryWrapper.eq(CtDevice::getDealId,parseRegisterDto.getDealId());
        ctDeviceLambdaQueryWrapper.eq(CtDevice::getUniqueId,parseRegisterDto.getUniqueId());
        CtDevice ctDevice = ctDeviceMapper.selectOne(ctDeviceLambdaQueryWrapper);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=simpleDateFormat.format(parseRegisterDto.getTime());
        Date day=DateUtils.str2Date(date,simpleDateFormat);

        if (ObjectUtils.isEmpty(ctDevice)){
            CtDevice insertCtDevice = new CtDevice();
            insertCtDevice.setGameId(parseRegisterDto.getGameId());
            insertCtDevice.setSubGameId(parseRegisterDto.getSubGameId());
            insertCtDevice.setChannelId(parseRegisterDto.getChannelId());
            insertCtDevice.setChannelTypeId(parseRegisterDto.getChannelTypeId());
            insertCtDevice.setChannelSubAccountId(parseRegisterDto.getChannelSubAccountId());
            insertCtDevice.setDealId(parseRegisterDto.getDealId());
            insertCtDevice.setUpdateTime(day);
            insertCtDevice.setCreateTime(day);
            insertCtDevice.setUniqueId(parseRegisterDto.getUniqueId());
            insertCtDevice.setPkgId(parseRegisterDto.getPkgId());

            insertCtDevice.setRegisterTime(day);

            ctDeviceMapper.insert(insertCtDevice);


        }else {

            ctDevice.setUpdateTime(day);
            ctDevice.setRegisterTime(day);
            ctDeviceMapper.updateById(ctDevice);
        }

    }

    @Override
    public CtDevice searchAliveDevice(ParseAliveDto parseAliveDto){
        LambdaQueryWrapper<CtDevice> ctDeviceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDeviceLambdaQueryWrapper.eq(CtDevice::getDealId,parseAliveDto.getDealId());
        ctDeviceLambdaQueryWrapper.eq(CtDevice::getUniqueId,parseAliveDto.getUniqueId());
        CtDevice ctDevice = ctDeviceMapper.selectOne(ctDeviceLambdaQueryWrapper);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=simpleDateFormat.format(parseAliveDto.getTime());
        Date day=DateUtils.str2Date(date,simpleDateFormat);

        CtDevice resultCtDevice = new CtDevice();
        if (ObjectUtils.isEmpty(ctDevice)){
            CtDevice insertCtDevice = new CtDevice();
            insertCtDevice.setGameId(parseAliveDto.getGameId());
            insertCtDevice.setSubGameId(parseAliveDto.getSubGameId());
            insertCtDevice.setChannelId(parseAliveDto.getChannelId());
            insertCtDevice.setChannelTypeId(parseAliveDto.getChannelTypeId());
            insertCtDevice.setChannelSubAccountId(parseAliveDto.getChannelSubAccountId());
            insertCtDevice.setDealId(parseAliveDto.getDealId());
            insertCtDevice.setUpdateTime(day);
            insertCtDevice.setCreateTime(day);
            insertCtDevice.setUniqueId(parseAliveDto.getUniqueId());
            insertCtDevice.setPkgId(parseAliveDto.getPkgId());

            BeanUtils.copyProperties(insertCtDevice,resultCtDevice);

            ctDeviceMapper.insert(insertCtDevice);


        }else {
            int second = DateUtils.dateToDiff('s', day, ctDevice.getCreateTime());

            BeanUtils.copyProperties(ctDevice,resultCtDevice);
            ctDevice.setAliveTime(day);
            ctDevice.setUpdateTime(day);
            if (ObjectUtils.isEmpty(ctDevice.getPlayTime()) && second>595){
                ctDevice.setPlayTime(day);

                ctDeviceMapper.updateById(ctDevice);

                // TODO
            }

        }
        return resultCtDevice;
    }

    @Override
    public CtDevice parsePayDevice(ParsePayDto parsePayDto) {
        CtDevice result = new CtDevice();
        LambdaQueryWrapper<CtDevice> ctDeviceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDeviceLambdaQueryWrapper.eq(CtDevice::getDealId, parsePayDto.getDealId());
        ctDeviceLambdaQueryWrapper.eq(CtDevice::getUniqueId, parsePayDto.getUniqueId());
        CtDevice ctDevice = ctDeviceMapper.selectOne(ctDeviceLambdaQueryWrapper);
        // 日志时间
        Date logTime = DateUtils.getDate(parsePayDto.getTime());
        if (ctDevice == null) {
            ctDevice = new CtDevice();
            ctDevice.setGameId(parsePayDto.getGameId());
            ctDevice.setSubGameId(parsePayDto.getSubGameId());
            ctDevice.setChannelId(parsePayDto.getChannelId());
            ctDevice.setChannelTypeId(parsePayDto.getChannelTypeId());
            ctDevice.setChannelSubAccountId(parsePayDto.getChannelSubAccountId());
            ctDevice.setDealId(parsePayDto.getDealId());
            ctDevice.setUpdateTime(logTime);
            ctDevice.setCreateTime(logTime);
            ctDevice.setUniqueId(parsePayDto.getUniqueId());
            ctDevice.setPkgId(parsePayDto.getPkgId());
            // 返回旧数据
            BeanUtils.copyProperties(ctDevice, result);
            ctDevice.setPayTime(logTime);
            ctDevice.setPlayTime(logTime);
            save(ctDevice);
        } else {
            // 返回旧数据
            BeanUtils.copyProperties(ctDevice, result);
            ctDevice.setPayTime(logTime);
            ctDevice.setUpdateTime(logTime);
            ctDevice.setPlayTime(logTime);
            updateById(ctDevice);
        }
        return result;
    }

}
