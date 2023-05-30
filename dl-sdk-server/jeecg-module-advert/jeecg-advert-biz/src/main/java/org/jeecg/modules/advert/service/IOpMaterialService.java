package org.jeecg.modules.advert.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.ks.account.bo.KsTokenBo;
import org.jeecg.modules.advert.dto.OpMaterialDto;
import org.jeecg.modules.advert.dto.SetAccountDto;
import org.jeecg.modules.advert.entity.OpMaterial;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.vo.AccountVo;
import org.jeecg.modules.advert.vo.OpMaterialVo;
import org.jeecg.modules.advert.vo.QuerySiteMaterialVo;
import org.jeecg.modules.advert.vo.ResponseSiteMaterialVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: op_material
 * @Author: jeecg-boot
 * @Date: 2023-01-12
 * @Version: V1.0
 */
public interface IOpMaterialService extends IService<OpMaterial> {

    /**
     * @param page
     * @param opMaterial
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.advert.vo.OpMaterialVo>
     * @Author lili
     * @Description 获得列表
     * @Date 15:47 2023/1/13
     **/
    IPage<OpMaterialVo> getMaterialList(Page<T> page, OpMaterialDto opMaterial);

    /**
     * @param opMaterialDto
     * @Author lili
     * @Description 上传素材
     * @Date 16:18 2023/1/12
     **/
    void add(OpMaterialDto opMaterialDto);

    /**
     * @param file
     * @return java.lang.Boolean
     * @Author lili
     * @Description 上传到本地
     * @Date 16:38 2023/1/13
     **/
    String upload(MultipartFile file) throws IOException;

    /**
     * @param jrttTokenBo
     * @param opMaterialDto
     * @Author lili
     * @Description 上传素材到头条
     * @Date 15:48 2023/3/10
     **/
    void pushJrttMaterial(JrttTokenBo jrttTokenBo, OpMaterialDto opMaterialDto);

    /**
     * @param ksTokenBo
     * @param opMaterialDto
     * @Author lili
     * @Description 上传素材到快手
     * @Date 15:50 2023/3/10
     **/
    void pushKsMaterial(KsTokenBo ksTokenBo, OpMaterialDto opMaterialDto);

    /**
     * @param setAccountDto
     * @Author lili
     * @Description 更新账号
     * @Date 14:44 2023/1/30
     **/
    void setAccount(SetAccountDto setAccountDto);

    /**
     * @param setAccountDto
     * @return java.lang.String
     * @Author lili
     * @Description 推送素材
     * @Date 15:48 2023/1/30
     **/
    void pushMaterial(SetAccountDto setAccountDto);

    /**
     * @param page:
     * @param queryParam:
     * @return IPage<ResponseSiteMaterialVo>
     * @author
     * @description 获取落地页素材
     * @date 2023/3/2 10:05
     */
    IPage<ResponseSiteMaterialVo> getSiteMaterialPage(Page<ResponseSiteMaterialVo> page,
        QuerySiteMaterialVo queryParam);

    /**
     * @param ids
     * @return java.util.List<org.jeecg.modules.advert.entity.OpMaterial>
     * @author chenyw
     * @description 根据id获取op_material对象
     * @date 14:07 2023/2/22/022
     **/
    List<OpMaterial> queryByIdList(String ids);

}
