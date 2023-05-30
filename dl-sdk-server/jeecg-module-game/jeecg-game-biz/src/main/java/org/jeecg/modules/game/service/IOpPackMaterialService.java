package org.jeecg.modules.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import jdk.internal.dynalink.linker.LinkerServices;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.game.dto.OpPackMaterialDto;
import org.jeecg.modules.game.entity.OpPackMaterial;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.vo.OpPackMaterialVo;
import org.jeecg.modules.game.vo.OpSubGameVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: op_pack_material
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
public interface IOpPackMaterialService extends IService<OpPackMaterial> {

    /**
     * @param page
     * @param opPackMaterial
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.game.dto.OpPackMaterialDto>
     * @Author lili
     * @Description 查询列表
     * @Date 15:49 2023/1/10
     **/
    IPage<OpPackMaterial> getPackMaterialList(Page<T> page, OpPackMaterialDto opPackMaterial);

    /**
     * @param opPackMaterialDto
     * @Author lili
     * @Description 上传素材，编辑
     * @Date 10:24 2023/1/10
     **/
    void save(OpPackMaterialDto opPackMaterialDto) throws IOException;

}
