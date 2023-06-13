package org.jeecg.modules.count.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.function.bo.GetNameByIdDto;
import org.jeecg.common.function.vo.GetNameByIdVo;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.modules.count.dto.BlockadeDto;
import org.jeecg.modules.count.dto.CtUserDto;
import org.jeecg.modules.count.dto.OnlineUserDto;
import org.jeecg.modules.count.dto.PayUserDto;
import org.jeecg.modules.count.entity.CtUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.modal.CtUserModal;
import org.jeecg.modules.count.modal.PayUserModal;
import org.jeecg.modules.count.vo.CtUserVo;
import org.jeecg.modules.count.vo.OnlineUserVo;
import org.jeecg.modules.count.vo.PayUserVo;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-25
 * @Version: V1.0
 */
public interface ICtUserService extends IService<CtUser> {

    /**
     * @param onlineUserDto
     * @return java.util.List<org.jeecg.modules.count.vo.OnlineUserVo>
     * @Author lili
     * @Description 在线统计查询
     * @Date 13:54 2023/5/9
     **/
    List<OnlineUserVo> getOnlineUserList(OnlineUserDto onlineUserDto);

    /**
     * @param page
     * @param ctUserDto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.count.vo.CtUserVo>
     * @Author lili
     * @Description 连表分页查询
     * @Date 17:09 2023/4/25
     **/
    IPage<CtUserVo> getPageList(Page<T> page, CtUserDto ctUserDto);

    /**
     * @param ctUserDto
     * @return java.util.List<org.jeecg.modules.count.modal.CtUserModal>
     * @Author lili
     * @Description 查询全部（包含查询条件）
     * @Date 15:11 2023/5/5
     **/
    List<CtUserModal> getAllList(CtUserDto ctUserDto);

    /**
     * @param object
     * @param clazz
     * @param title
     * @return org.springframework.web.servlet.ModelAndView
     * @Author lili
     * @Description 导出报表
     * @Date 15:25 2023/5/5
     **/
    ModelAndView exportXls(CtUserDto object, Class<CtUserModal> clazz, String title);

    /**
     * @param page
     * @param payUserDto
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.count.vo.PayUserVo>
     * @Author lili
     * @Description 付费用户数据分页
     * @Date 16:22 2023/5/8
     **/
    IPage<PayUserVo> getPagePayUserList(Page<T> page, PayUserDto payUserDto);

    /**
     * @param getNameByIdDto
     * @return org.jeecg.common.function.vo.GetNameByIdVo
     * @Author lili
     * @Description 通过id得到名称（游戏，子游戏，渠道游戏包，广告）
     * @Date 20:03 2023/5/25
     **/
    GetNameByIdVo getNameById(GetNameByIdDto getNameByIdDto);

    /**
     * @param payUserDto
     * @return java.util.List<org.jeecg.modules.count.modal.PayUserModal>
     * @Author lili
     * @Description 付费用户数据查询全部（包含查询条件）
     * @Date 16:23 2023/5/8
     **/
    List<PayUserModal> getAllPayUserList(PayUserDto payUserDto);

    /**
     * @param object
     * @param clazz
     * @param title
     * @return org.springframework.web.servlet.ModelAndView
     * @Author lili
     * @Description 导出报表
     * @Date 16:37 2023/5/8
     **/
    ModelAndView payUseExportXls(PayUserDto object, Class<PayUserModal> clazz, String title);

    /**
     * @param ctUserDto
     * @Author lili
     * @Description 封号、封IP
     * @Date 11:49 2023/5/5
     **/
    void blockade(BlockadeDto ctUserDto);

    /**
     * @param parseLoginDto: login解析dto
     * @return CtUser
     * @author Fkh
     * @description login解析对CtUser表进行操作
     * @date 2023/4/23 14:32
     */
    CtUser searchLoginUser(ParseLoginDto parseLoginDto);

    /**
     * @param parseLoginDto: login解析dto
     * @param ctUser: 查询出来的CtUser数据
     * @return void
     * @author Fkh
     * @description login解析对CtUser表进行Mask字段的更新
     * @date 2023/4/23 14:33
     */
    void updateMask(ParseLoginDto parseLoginDto,CtUser ctUser);

    /**
     * @param parseAliveDto: alive解析dto
     * @return CtUser
     * @author Fkh
     * @description alive解析对CtUser表进行操作
     * @date 2023/4/23 14:34
     */
    CtUser searchAliveUser(ParseAliveDto parseAliveDto);

    /**
     * @param parsePayDto pay解析dto
     * @return org.jeecg.modules.count.entity.CtUser
     * @author chenyw
     * @description 获取用户信息
     * @date 20:14 2023/4/19/019
     **/
    CtUser parsePayUser(ParsePayDto parsePayDto);
}
