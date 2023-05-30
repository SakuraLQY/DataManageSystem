package org.jeecg.modules.advert.service;

import java.util.Map;
import org.jeecg.modules.advert.entity.OpChannelSubAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.modal.ChannelModal;
import org.jeecg.modules.advert.modal.ChannelObjModal;

/**
 * @Description: op_channel_sub_account
 * @Author: jeecg-boot
 * @Date:   2023-01-06
 * @Version: V1.0
 */
public interface IOpChannelSubAccountService extends IService<OpChannelSubAccount> {

    /**
     * @return java.util.Map<java.lang.Integer, org.jeecg.modules.advert.modal.ChannelModal>
     * @Author lili
     * @Description 三级联动下拉框(单选)
     * @Date 17:09 2023/4/28
     **/
    Map<Integer, ChannelModal> queryList();

    /**
     * @return java.util.Map<java.lang.Integer, java.util.Map < java.lang.Integer, org.jeecg.modules.advert.modal.ChannelObjModal>>
     * @Author lili
     * @Description 三级联动下拉框(多选)
     * @Date 20:03 2023/5/8
     **/
    Map<Integer, Map<Integer, ChannelObjModal>> getThirdOptionMuchList();


}
