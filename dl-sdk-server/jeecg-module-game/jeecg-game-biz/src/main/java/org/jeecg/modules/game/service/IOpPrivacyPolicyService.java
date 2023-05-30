package org.jeecg.modules.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import jdk.internal.dynalink.linker.LinkerServices;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.game.dto.OpPrivacyPolicyDto;
import org.jeecg.modules.game.entity.OpPrivacyPolicy;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.vo.OpSubGameVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: op_privacy_policy
 * @Author: jeecg-boot
 * @Date: 2022-12-28
 * @Version: V1.0
 */
public interface IOpPrivacyPolicyService extends IService<OpPrivacyPolicy> {

    /**
     * @param page
     * @param opPrivacyPolicy
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.game.vo.OpPrivacyPolicyVo>
     * @Author lili
     * @Description 连表查询
     * @Date 10:04 2023/1/5
     **/
    IPage<OpPrivacyPolicy> getPrivacyPolicyList(Page<T> page, OpPrivacyPolicyDto opPrivacyPolicy);

    /**
     * @param opPrivacyPolicy
     * @Author lili
     * @Description 新增
     * @Date 17:54 2022/12/30
     **/
    void add(OpPrivacyPolicy opPrivacyPolicy);

//    /**
//     * @param pkgIds
//     * @return java.util.List<org.jeecg.modules.game.entity.OpPrivacyPolicy>
//     * @Author lili
//     * @Description 根据多个一级游戏包id得到列表
//     * @Date 11:26 2023/2/10
//     **/
//    List<OpPrivacyPolicy> getListByPkgIds(List<String> pkgIds, List<OpPrivacyPolicy> list);
    /**
     * @param opPrivacyPolicy
     * @Author lili
     * @Description 修改
     * @Date 11:43 2023/1/5
     **/
    void update(OpPrivacyPolicy opPrivacyPolicy);

    /**
     * @param file         文件
     * @param fileDir      远程目录
     * @param customBucket bucketName没有就用它
     * @param forbidType   过滤文本
     * @return java.lang.String
     * @Author lili
     * @Description 上传至OSS
     * @Date 17:54 2022/12/30
     **/
    String uploadToOSS(MultipartFile file, String fileDir, String customBucket, String[] forbidType);

    /**
     * @param objectPath
     * @return java.lang.String
     * @Author lili
     * @Description 刷新cdn
     * @Date 17:18 2023/1/4
     **/
    String refreshObjectCaches(String objectPath);

    /**
     * @param objectPath
     * @return java.lang.String
     * @Author lili
     * @Description 预热cdn
     * @Date 17:20 2023/1/4
     **/
    String pushObjectCache(String objectPath);

}
