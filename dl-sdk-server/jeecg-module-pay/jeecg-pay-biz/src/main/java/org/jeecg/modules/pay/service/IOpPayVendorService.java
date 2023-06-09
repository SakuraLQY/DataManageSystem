package org.jeecg.modules.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.modules.pay.entity.OpPayVendor;

/**
 * @Description: op_pay_vendor
 * @Author: jeecg-boot
 * @Date:   2022-12-10
 * @Version: V1.0
 */
public interface IOpPayVendorService extends IService<OpPayVendor> {

    /**
     * @author xmh
     * @description 删除渠道信息
     * @date 2023/3/13 14:48
     * @param id: ID
     */
    void deleteId(String id);

    /**
     * @author xmh
     * @description 删除渠道信息
     * @date 2023/3/13 14:48
     * @param ids: ID集合
     */
    void deleteIds(List<String> ids);

    /**
     * @author xmh
     * @description 添加
     * @date 2023/6/12 11:46
     * @param opPayVendor: 支付渠道
     */
    void insert(OpPayVendor opPayVendor);
}
