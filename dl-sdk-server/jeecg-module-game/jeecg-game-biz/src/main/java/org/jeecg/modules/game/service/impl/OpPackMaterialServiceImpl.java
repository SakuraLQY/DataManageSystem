package org.jeecg.modules.game.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.modules.game.dto.OpPackMaterialDto;
import org.jeecg.modules.game.entity.OpPackConfig;
import org.jeecg.modules.game.entity.OpPackMaterial;
import org.jeecg.modules.game.mapper.OpPackMaterialMapper;
import org.jeecg.modules.game.service.IOpPackConfigService;
import org.jeecg.modules.game.service.IOpPackMaterialService;
import org.jeecg.common.util.FileUtil;
import org.jeecg.modules.game.vo.OpPackConfigVo;
import org.jeecg.modules.game.vo.OpPackMaterialVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: op_pack_material
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
@Slf4j
public class OpPackMaterialServiceImpl extends
    ServiceImpl<OpPackMaterialMapper, OpPackMaterial> implements
    IOpPackMaterialService {

    @Autowired
    private JeecgBaseConfig jeecgBaseConfig;
    @Resource
    private IOpPackConfigService opPackConfigService;

    @Override
    public IPage<OpPackMaterial> getPackMaterialList(Page<T> page,
        OpPackMaterialDto opPackMaterial) {
        QueryWrapper<OpPackMaterialDto> q = new QueryWrapper<>();
        if (null != opPackMaterial.getGameId()) {
            q.eq("game_id", opPackMaterial.getGameId());
        }
        if (null != opPackMaterial.getSubGameId()) {
            q.eq("sub_game_id", opPackMaterial.getSubGameId());
        }
        if (null != opPackMaterial.getType()) {
            q.eq("type", opPackMaterial.getType());
        }
        if (null != opPackMaterial.getMaterialName()) {
            q.like("material_name", opPackMaterial.getMaterialName());
        }
        if (null != opPackMaterial.getCreateBy()) {
            q.eq("create_by", opPackMaterial.getCreateBy());
        }
        if (null != opPackMaterial.getStartTime()) {
            q.ge("create_time", opPackMaterial.getStartTime());
        }
        if (null != opPackMaterial.getEndTime()) {
            q.le("create_time", opPackMaterial.getEndTime());
        }

        IPage<OpPackMaterial> pageList = baseMapper.getPackMaterialList(page, q);
        return pageList;
    }

    @Override
    public void save(OpPackMaterialDto opPackMaterialDto) throws IOException {
        String filePath = jeecgBaseConfig.getPath().getUpload() + File.separator
            + opPackMaterialDto.getFile();
        File file = new File(filePath);
        //文件大小
        long size = file.length();
        if (size > 2097152) {
            throw new JeecgBootException("图片大小不能超过2M");
        }
        // 图片对象
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));
        // 宽度
        int width = bufferedImage.getWidth();
        // 高度
        int height = bufferedImage.getHeight();
        //文件类型
        Set<String> set = new HashSet<>();
        String fileType = "";
        if (opPackMaterialDto.getType() == 1) {
            set.add(".png");
            if (!FileUtil.checkFile(file, set)) {
                throw new JeecgBootException("上传图片格式错误");
            }
            if (width != 512 || height != 512) {
                throw new JeecgBootException("上传图片尺寸错误");
            }
            fileType = "png";
        } else {
            OpPackConfig opPackConfig = new OpPackConfig();
            opPackConfig.setSubGameId(opPackMaterialDto.getSubGameId());
            OpPackConfigVo opPackConfigVo = opPackConfigService.getObjByGameId(opPackConfig);
            if (oConvertUtils.isEmpty(opPackConfigVo)) {
                throw new JeecgBootException("游戏未配置打包参数");
            }
            if (opPackMaterialDto.getType() == 2) {
                if (oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("screen_type"))
                    || oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("screen_width"))
                    || oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("screen_height"))) {
                    throw new JeecgBootException("该游戏未配置游戏闪屏打包配置");
                }
                set = new HashSet<>();
                set.add("." + opPackConfigVo.getPackConfig().get("screen_type"));
                if (!FileUtil.checkFile(file, set)) {
                    throw new JeecgBootException("上传图片格式错误");
                }
                if (width != Integer.parseInt(
                    opPackConfigVo.getPackConfig().get("screen_width").toString())
                    || height != Integer.parseInt(
                    opPackConfigVo.getPackConfig().get("screen_height").toString())) {
                    throw new JeecgBootException("上传图片尺寸错误");
                }
                fileType = opPackConfigVo.getPackConfig().get("screen_type").toString();
            } else if (opPackMaterialDto.getType() == 3) {
                if (oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("loading_type"))
                    || oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("loading_width"))
                    || oConvertUtils.isEmpty(
                    opPackConfigVo.getPackConfig().get("loading_height"))) {
                    throw new JeecgBootException("该游戏未配置游戏加载图打包配置");
                }
                set = new HashSet<>();
                set.add("." + opPackConfigVo.getPackConfig().get("loading_type"));
                if (!FileUtil.checkFile(file, set)) {
                    throw new JeecgBootException("上传图片格式错误");
                }
                if (width != Integer.parseInt(
                    opPackConfigVo.getPackConfig().get("loading_width").toString())
                    || height != Integer.parseInt(
                    opPackConfigVo.getPackConfig().get("loading_height").toString())) {
                    throw new JeecgBootException("上传图片尺寸错误");
                }
                fileType = opPackConfigVo.getPackConfig().get("loading_type").toString();
            } else if (opPackMaterialDto.getType() == 4) {
                if (oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("login_type"))
                    || oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("login_width"))
                    || oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("login_height"))) {
                    throw new JeecgBootException("该游戏未配置游戏登陆图打包配置");
                }
                set = new HashSet<>();
                set.add("." + opPackConfigVo.getPackConfig().get("login_type"));
                if (!FileUtil.checkFile(file, set)) {
                    throw new JeecgBootException("上传图片格式错误");
                }
                if (width != Integer.parseInt(
                    opPackConfigVo.getPackConfig().get("login_width").toString())
                    || height != Integer.parseInt(
                    opPackConfigVo.getPackConfig().get("login_height").toString())) {
                    throw new JeecgBootException("上传图片尺寸错误");
                }
                fileType = opPackConfigVo.getPackConfig().get("login_type").toString();
            } else if (opPackMaterialDto.getType() == 5) {
                if (oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("logo_type"))
                    || oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("logo_width"))
                    || oConvertUtils.isEmpty(opPackConfigVo.getPackConfig().get("logo_height"))) {
                    throw new JeecgBootException("该游戏未配置游戏logo打包配置");
                }
                set = new HashSet<>();
                set.add("." + opPackConfigVo.getPackConfig().get("logo_type"));
                if (!FileUtil.checkFile(file, set)) {
                    throw new JeecgBootException("上传图片格式错误");
                }
                if (width != Integer.parseInt(
                    opPackConfigVo.getPackConfig().get("logo_width").toString())
                    || height != Integer.parseInt(
                    opPackConfigVo.getPackConfig().get("logo_height").toString())) {
                    throw new JeecgBootException("上传图片尺寸错误");
                }
                fileType = opPackConfigVo.getPackConfig().get("logo_type").toString();
            }
        }
        try {
            //除去后缀的文件名
            String fileName = file.getName().substring(0, file.getName().indexOf("."));
            //判断用文件名还是自定义名
            if (opPackMaterialDto.getMaterialName().isEmpty()) {
                opPackMaterialDto.setMaterialName(fileName);
            }
            OpPackMaterial opPackMaterial = new OpPackMaterial();
            opPackMaterial.setGameId(opPackMaterialDto.getGameId());
            opPackMaterial.setSubGameId(opPackMaterialDto.getSubGameId());
            opPackMaterial.setMaterialName(opPackMaterialDto.getMaterialName());
            opPackMaterial.setType(opPackMaterialDto.getType());
            opPackMaterial.setFormat(fileType);
            opPackMaterial.setSpecs(String.valueOf(width) + '*' + height);
            opPackMaterial.setShowUrl(opPackMaterialDto.getFile());
            opPackMaterial.setMaterialDesc(opPackMaterialDto.getMaterialDesc());
//            opPackMaterial.setCreateBy(opPackMaterialDto.getCreateBy());
//            opPackMaterial.setCreateTime(opPackMaterialDto.getCreateTime());
            if (opPackMaterialDto.getId() != null) {
                opPackMaterial.setId(opPackMaterialDto.getId());
                updateById(opPackMaterial);
            }else {
                save(opPackMaterial);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
