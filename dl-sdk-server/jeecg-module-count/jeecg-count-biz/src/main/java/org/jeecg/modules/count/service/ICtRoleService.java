package org.jeecg.modules.count.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.modules.count.dto.CtRoleDto;
import org.jeecg.modules.count.dto.CtUserDto;
import org.jeecg.modules.count.entity.CtRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.modal.CtRoleModal;
import org.jeecg.modules.count.modal.CtUserModal;
import org.jeecg.modules.count.vo.CtRoleVo;
import org.jeecg.modules.count.vo.CtUserVo;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: ct_role
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
public interface ICtRoleService extends IService<CtRole> {

    /**
     * @param page
     * @param ctRoleDto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.count.vo.CtRoleVo>
     * @Author lili
     * @Description 连表分页查询
     * @Date 18:11 2023/5/5
     **/
    IPage<CtRoleVo> getPageList(Page<T> page, CtRoleDto ctRoleDto);

    /**
     * @param ctRoleDto
     * @return java.util.List<org.jeecg.modules.count.modal.CtRoleModal>
     * @Author lili
     * @Description 查询全部（包含查询条件）
     * @Date 15:29 2023/5/6
     **/
    List<CtRoleModal> getAllList(CtRoleDto ctRoleDto);

    /**
     * @param object
     * @param clazz
     * @param title
     * @return org.springframework.web.servlet.ModelAndView
     * @Author lili
     * @Description 导出报表
     * @Date 15:38 2023/5/6
     **/
    ModelAndView exportXls(CtRoleDto object, Class<CtRoleModal> clazz, String title);

    void searchAliveRole(ParseAliveDto parseAliveDto);

    /**
     * @param parsePayDto
     * @return org.jeecg.modules.count.entity.CtRole
     * @author chenyw
     * @description 解析支付角色对象
     * @date 20:34 2023/4/20/020
     **/
    CtRole parsePayRole(ParsePayDto parsePayDto);
}
