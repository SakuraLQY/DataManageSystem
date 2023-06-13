package org.jeecg.modules.pay.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.pay.entity.OpVendor;
import org.jeecg.modules.pay.mapper.OpPayVendorMapper;
import org.jeecg.modules.pay.mapper.OpVendorMapper;
import org.jeecg.modules.pay.service.IOpVendorService;
import org.jeecg.modules.pay.vo.OpVendorVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: op_vendor
 * @Author: jeecg-boot
 * @Date: 2022-12-10
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpVendorServiceImpl extends ServiceImpl<OpVendorMapper, OpVendor> implements
    IOpVendorService {

    @Resource
    private IGameApi gameApi;
    @Resource
    private OpPayVendorMapper opPayVendorMapper;

    @Override
    public IPage<OpVendorVo> getOpVendorByPage(Page<OpVendor> page,
        QueryWrapper<OpVendor> wrapper) {
        // 先查询厂商信息
        IPage<OpVendor> vendorPage = baseMapper.selectPage(page, wrapper);
        // 初始化Vo
        IPage<OpVendorVo> vendorVoPage = vendorPage.convert(OpVendorVo::new);
        if (vendorVoPage.getRecords().size() > 0) {
            addPayVendorData(vendorVoPage);
        }
        return vendorVoPage;
    }

    @Override
    public OpVendorVo getOpVendorVoById(Integer id) {
        LambdaQueryWrapper<OpVendor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpVendor::getId, id);
        OpVendor opVendor = baseMapper.selectOne(wrapper);
        // 转化为vo
        OpVendorVo opVendorVo = Optional.ofNullable(opVendor).map(OpVendorVo::new).orElse(null);
        Optional.ofNullable(opVendorVo).ifPresent(this::addPayVendorData);
        return opVendorVo;
    }

    @Override
    public void deleteId(String id) {
        List<String> list = new ArrayList<>();
        list.add(id);
        if(gameApi.checkVendorIsBind(list)){
            throw new JeecgBootException("该厂商已被绑定, 请解绑后再删除");
        }
        if(!removeById(id)) {
            throw new JeecgBootException("删除失败");
        }
    }

    @Override
    public void deleteIds(List<String> ids) {
        if(gameApi.checkVendorIsBind(ids)){
            throw new JeecgBootException("选择的厂商存在被绑定的厂商, 请解绑后再删除");
        }
        if(!removeByIds(ids)){
            throw new JeecgBootException("删除失败");
        }
    }

    @Override
    public void insert(OpVendor opVendor) {
        System.out.println(opVendor);
        LambdaQueryWrapper<OpVendor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpVendor::getVendorName, opVendor.getVendorName());
        OpVendor notNull = getOne(wrapper);
        if(oConvertUtils.isNotEmpty(notNull)){
            throw new JeecgBootException("已存在相同名称的支付厂商");
        }
        save(opVendor);
    }

    @Override
    public void update(OpVendor opVendor) {
        System.out.println(opVendor);
        LambdaUpdateWrapper<OpVendor> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OpVendor::getId, opVendor.getId());

        wrapper.set(OpVendor::getVendorName, opVendor.getVendorName());
        wrapper.set(OpVendor::getAliPayVendor, opVendor.getAliPayVendor());
        wrapper.set(OpVendor::getHeePayVendor, opVendor.getHeePayVendor());
        wrapper.set(OpVendor::getYeePayVendor, opVendor.getYeePayVendor());
        wrapper.set(OpVendor::getIappPayVendor, opVendor.getIappPayVendor());
        wrapper.set(OpVendor::getIpaynowPayVendor, opVendor.getIpaynowPayVendor());
        wrapper.set(OpVendor::getIpaynowappPayVendor, opVendor.getIpaynowappPayVendor());
        wrapper.set(OpVendor::getWxPayVendor, opVendor.getWxPayVendor());
        wrapper.set(OpVendor::getCreateBy, opVendor.getCreateBy());
        wrapper.set(OpVendor::getCreateTime, opVendor.getCreateTime());
        wrapper.set(OpVendor::getUpdateBy, opVendor.getUpdateBy());
        wrapper.set(OpVendor::getUpdateTime, opVendor.getUpdateTime());
        wrapper.set(OpVendor::getNote, opVendor.getNote());
        update(wrapper);
    }

    /**
     * @param opVendorVo: vo对象
     * @return: void
     * @description: 给 opVendor 注入 opPayVendor
     * @author: xmh
     * @date: 2022/12/12 14:36
     */
    private void addPayVendorData(OpVendorVo opVendorVo) {
        // 获取所有的支付渠道ID
        Set<Integer> vendorIds = getVendorPayIds(new HashSet<>(), opVendorVo);
        if (CollectionUtils.isNotEmpty(vendorIds)) {
            // 查找数据
            List<OpPayVendor> opPayVendorList = opPayVendorMapper.selectList(
                Wrappers.lambdaQuery(OpPayVendor.class).in(OpPayVendor::getId, vendorIds));
            // 属性注入
            for (OpPayVendor opPayVendor : opPayVendorList) {
                opVendorVo.addPayVendorData(opPayVendor);
            }
        }
    }

    /**
     * @param vendorVoPage: vo Page对象
     * @return: void
     * @description: 给 opVendor 注入 opPayVendor
     * @author: xmh
     * @date: 2022/12/12 14:36
     */
    private void addPayVendorData(IPage<OpVendorVo> vendorVoPage) {
        // 获取所有的支付渠道ID
        Set<Integer> payVendorIds = new HashSet<>();
        // 每个厂商建立映射关系
        Map<Integer, Set<Integer>> vendorIdsMap = new HashMap<>();
        for (OpVendorVo vendorVo : vendorVoPage.getRecords()) {
            Set<Integer> vendorIds = getVendorPayIds(payVendorIds, vendorVo);
            vendorIdsMap.put(vendorVo.getId(), vendorIds);
        }
        // 删除掉 null 防止有 null 的值
        payVendorIds.remove(null);
        if (CollectionUtils.isNotEmpty(payVendorIds)) {
            // 查找数据
            List<OpPayVendor> opPayVendorList = opPayVendorMapper.selectList(
                Wrappers.lambdaQuery(OpPayVendor.class).in(OpPayVendor::getId, payVendorIds));
            // 构造映射关系
            Map<Integer, OpPayVendor> payVendorMap = opPayVendorList.stream()
                .collect(Collectors.toMap(OpPayVendor::getId, opPayVendor -> opPayVendor));
            // 将支付渠道数据添加到vo中
            vendorVoPage.getRecords().forEach(e -> {
                // 遍历某个厂商绑定的支付渠道集合
                Set<Integer> ids = vendorIdsMap.get(e.getId());
                if (CollectionUtils.isNotEmpty(ids)) {
                    for (Integer id : ids) {
                        e.addPayVendorData(payVendorMap.get(id));
                    }
                }
            });
        }
    }

    /**
     * @param allIds: 所有的支付渠道ID set集合
     * @param vendor: 支付厂商对象
     * @return: Set<Integer>
     * @description: 获取支付厂商绑定的支付渠道ID
     * @author: xmh
     * @date: 2022/12/12 14:27
     */
    private Set<Integer> getVendorPayIds(Set<Integer> allIds, OpVendor vendor) {
        Integer ali = vendor.getAliPayVendor();
        Integer hee = vendor.getHeePayVendor();
        Integer yee = vendor.getYeePayVendor();
        Integer iapp = vendor.getIappPayVendor();
        Integer ipaynow = vendor.getIpaynowPayVendor();
        Integer ipaynowapp = vendor.getIpaynowappPayVendor();
        Integer wx = vendor.getWxPayVendor();

        Set<Integer> vendorIds = new HashSet<>();
        vendorIds.add(ali);
        vendorIds.add(hee);
        vendorIds.add(yee);
        vendorIds.add(iapp);
        vendorIds.add(ipaynow);
        vendorIds.add(ipaynowapp);
        vendorIds.add(wx);
        // 删除掉 null 防止有 null 的值
        vendorIds.remove(null);

        allIds.add(ali);
        allIds.add(hee);
        allIds.add(yee);
        allIds.add(iapp);
        allIds.add(ipaynow);
        allIds.add(ipaynowapp);
        allIds.add(wx);
        return vendorIds;
    }
}
