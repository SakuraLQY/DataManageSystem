package org.jeecg.modules.pay.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.pay.entity.OpVendor;
import org.jeecg.modules.pay.mapper.OpPayVendorMapper;
import org.jeecg.modules.pay.service.IOpPayVendorService;
import org.jeecg.modules.pay.service.IOpVendorService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_pay_vendor
 * @Author: jeecg-boot
 * @Date:   2022-12-10
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpPayVendorServiceImpl extends ServiceImpl<OpPayVendorMapper, OpPayVendor> implements IOpPayVendorService {

    @Resource
    private IOpVendorService opVendorService;

    @Override
    public void deleteId(String id) {
        List<String> list = new ArrayList<>();
        list.add(id);
        checkIsBind(list, "该渠道已被绑定, 请解绑后再删除");
        if(!removeById(id)){
            throw new JeecgBootException("删除失败");
        }
    }

    @Override
    public void deleteIds(List<String> ids) {
        checkIsBind(ids, "选择的渠道存在被绑定的渠道, 请解绑后再删除");
        if(!removeByIds(ids)) {
            throw new JeecgBootException("删除失败");
        }
    }

    @Override
    public void insert(OpPayVendor opPayVendor) {
        LambdaQueryWrapper<OpPayVendor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpPayVendor::getPayVendorName, opPayVendor.getPayVendorName());
        wrapper.eq(OpPayVendor::getPayType, opPayVendor.getPayType());
        OpPayVendor notNull = getOne(wrapper);
        if(oConvertUtils.isNotEmpty(notNull)) {
            throw new JeecgBootException("已存在相同名称的支付渠道");
        }
        save(opPayVendor);
    }

    /**
     * @author xmh
     * @description 判断渠道是否被绑定
     * @date 2023/3/13 15:05
     * @param list: 渠道ID
     * @param errorMsg: 错误信息
     */
    private void checkIsBind(List<String> list, String errorMsg) {
        LambdaQueryWrapper<OpVendor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(OpVendor::getAliPayVendor, list).or();
        queryWrapper.in(OpVendor::getHeePayVendor, list).or();
        queryWrapper.in(OpVendor::getYeePayVendor, list).or();
        queryWrapper.in(OpVendor::getIappPayVendor, list).or();
        queryWrapper.in(OpVendor::getIpaynowPayVendor, list).or();
        queryWrapper.in(OpVendor::getIpaynowappPayVendor, list).or();
        queryWrapper.in(OpVendor::getWxPayVendor, list);
        OpVendor vendor = opVendorService.getOne(queryWrapper, false);
        if(oConvertUtils.isNotEmpty(vendor)) {
            throw new JeecgBootException(errorMsg);
        }
    }
}
