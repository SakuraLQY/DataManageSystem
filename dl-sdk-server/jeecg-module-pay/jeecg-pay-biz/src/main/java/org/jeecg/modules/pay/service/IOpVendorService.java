package org.jeecg.modules.pay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.modules.pay.entity.OpVendor;
import org.jeecg.modules.pay.vo.OpVendorVo;

/**
 * @Description: op_vendor
 * @Author: jeecg-boot
 * @Date: 2022-12-10
 * @Version: V1.0
 */
public interface IOpVendorService extends IService<OpVendor> {

    /**
     * 分页查询
     *
     * @param page: 分页
     * @param wrapper: 条件
     * @return: IPage<OpVendorVo>
     * @description: TODO
     * @author: xmh
     * @date: 2022/12/12 10:12
     */
    IPage<OpVendorVo> getOpVendorByPage(Page<OpVendor> page, QueryWrapper<OpVendor> wrapper);

    /**
     * @param id: 厂商ID
     * @return OpVendorVo
     * @author xmh
     * @description 根据id获取厂商及渠道信息
     * @date 2022/12/19 9:13
     */
    OpVendorVo getOpVendorVoById(Integer id);

    /**
     * @author xmh
     * @description 删除厂商信息
     * @date 2023/3/13 14:48
     * @param id: ID
     */
    void deleteId(String id);

    /**
     * @author xmh
     * @description 删除厂商信息
     * @date 2023/3/13 14:48
     * @param ids: ID集合
     */
    void deleteIds(List<String> ids);

    /**
     * @author xmh
     * @description 添加
     * @date 2023/6/12 13:45
     * @param opVendor: 支付厂商
     */
    void insert(OpVendor opVendor);

    /**
     * @author xmh
     * @description 更新
     * @date 2023/6/12 15:01
     * @param opVendor: 支付厂商
     */
    void update(OpVendor opVendor);
}
