package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.modules.count.dto.UserLogDto;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.entity.LgLogin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: lg_login
 * @Author: jeecg-boot
 * @Date:   2023-04-19
 * @Version: V1.0
 */
public interface ILgLoginService extends IService<LgLogin> {

    void insertLoginLog(ParseLoginDto parseLoginDto, CtUser ctUser, CtDevice ctDevice);

    List<String> getUseLoginLog(UserLogDto userLogDto);
}
