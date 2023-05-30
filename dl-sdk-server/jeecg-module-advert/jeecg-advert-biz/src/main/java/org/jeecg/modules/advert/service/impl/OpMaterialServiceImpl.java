package org.jeecg.modules.advert.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.FileUtil;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.common.util.filter.FileTypeFilter;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.material.api.JrttMaterialApi;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttImageAdRequest;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttImageAdResponse;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttVideoAdRequest;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttVideoAdResponse;
import org.jeecg.modules.advert.api.ks.account.bo.KsTokenBo;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse;
import org.jeecg.modules.advert.api.ks.material.api.KsMaterialApi;
import org.jeecg.modules.advert.api.ks.material.bo.KsImageAdRequest;
import org.jeecg.modules.advert.api.ks.material.bo.KsImageAdResponse;
import org.jeecg.modules.advert.api.ks.material.bo.KsVideoAdRequest;
import org.jeecg.modules.advert.api.ks.material.bo.KsVideoAdResponse;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttImageUploadType;
import org.jeecg.modules.advert.constant.jrtt.MaterialType;
import org.jeecg.modules.advert.constant.ks.KsImageUploadType;
import org.jeecg.modules.advert.dto.OpMaterialDto;
import org.jeecg.modules.advert.dto.SetAccountDto;
import org.jeecg.modules.advert.entity.OpPutAccount;
import org.jeecg.modules.advert.mapper.OpMaterialMapper;
import org.jeecg.modules.advert.mapper.OpPutAccountMapper;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.service.IOpKsPutAccountService;
import org.jeecg.modules.advert.service.IOpMaterialService;
import org.jeecg.modules.advert.entity.OpMaterial;
import org.jeecg.modules.advert.vo.AccountVo;
import org.jeecg.modules.advert.vo.OpMaterialVo;
import org.jeecg.modules.advert.vo.QuerySiteMaterialVo;
import org.jeecg.modules.advert.vo.ResponseSiteMaterialVo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: op_material
 * @Author: jeecg-boot
 * @Date: 2023-01-12
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
@Slf4j
public class OpMaterialServiceImpl extends ServiceImpl<OpMaterialMapper, OpMaterial> implements
    IOpMaterialService {

    @Resource
    private JeecgBaseConfig jeecgBaseConfig;

    @Resource
    private OpMaterialMapper opMaterialMapper;

    @Resource
    private OpPutAccountMapper accountMapper;

    @Resource
    private IOpJrttPutAccountService opJrttPutAccountService;

    @Resource
    private IOpKsPutAccountService opKsPutAccountService;

    private static String[] imgType = {"jpeg,jpg,gif,png"};

    private static String[] videoType = {"mp4,mpeg,3gp,avi"};

    @Override
    public IPage<OpMaterialVo> getMaterialList(Page<T> page, OpMaterialDto opMaterial) {
        QueryWrapper<OpMaterialDto> q = new QueryWrapper<>();
        if (null != opMaterial.getGameId()) {
            q.eq("game_id", opMaterial.getGameId());
        }
        if (null != opMaterial.getSubGameId()) {
            q.eq("sub_game_id", opMaterial.getSubGameId());
        }
        if (null != opMaterial.getType1()) {
            q.eq("type1", opMaterial.getType1());
        }
        if (null != opMaterial.getType2()) {
            q.eq("type2", opMaterial.getType2());
        }
        if (null != opMaterial.getJrttAccountId()) {
            q.eq("jrtt_create_account_id", opMaterial.getJrttAccountId());
        }
        if (null != opMaterial.getJrttStatus()) {
            q.eq("jrtt_status", opMaterial.getJrttStatus());
        }
        if (null != opMaterial.getMaterialName()) {
            q.like("material_name", opMaterial.getMaterialName());
        }
        if (null != opMaterial.getCreateBy()) {
            q.eq("create_by", opMaterial.getCreateBy());
        }
        if (null != opMaterial.getStartTime()) {
            q.ge("create_time", opMaterial.getStartTime());
        }
        if (null != opMaterial.getEndTime()) {
            q.le("create_time", opMaterial.getEndTime());
        }
        return baseMapper.getMaterialList(page, q);
    }

    @Override
    public void add(OpMaterialDto opMaterialDto) {
        if (oConvertUtils.isEmpty(opMaterialDto.getFile())) {
            throw new JeecgBootException("素材不能为空");
        }
        String suffix = null;
        File file = new File(opMaterialDto.getFile());
        //保存数据
        OpMaterial opMaterial = new OpMaterial();
        MultipartFile cMultiFile = null;
        try {
            cMultiFile = new MockMultipartFile("file", file.getName(), null,
                new FileInputStream(file));
            try {
                suffix = FileTypeFilter.getFileType(cMultiFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = 0;
        int height = 0;
        //文件大小
        long size = file.length();
        if (opMaterialDto.getType1() == 1) {
            for (String type : imgType) {
                if (!type.contains(suffix)) {
                    throw new JeecgBootException("上传图片格式错误");
                }
            }
            if (opMaterialDto.getType2() == 101) {
                if (size > 2097152) {
                    throw new JeecgBootException("落地页大小不能超过2M");
                }
            } else {
                if (size > 512000) {
                    throw new JeecgBootException("ICON图片大小不能超过500K");
                }
            }
            // 图片对象
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(
                    cMultiFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 宽度
            width = bufferedImage.getWidth();
            // 高度
            height = bufferedImage.getHeight();
        } else {
            for (String type : videoType) {
                if (!type.contains(suffix)) {
                    throw new JeecgBootException("上传视频格式错误");
                }
            }
            if (size > 104857600) {
                throw new JeecgBootException("视频大小不能超过100M");
            }
            Encoder encoder = new Encoder();
            MultimediaInfo m = null;
            try {
                m = encoder.getInfo(file);
                // 视频帧宽高
                width = m.getVideo().getSize().getWidth();
                height = m.getVideo().getSize().getHeight();
            } catch (EncoderException e) {
                e.printStackTrace();
            }
        }
        //除去后缀的文件名
        String fileName = file.getName()
            .substring(0, file.getName().indexOf("."));
        //判断用文件名还是自定义名
        if (opMaterialDto.getMaterialName().isEmpty()) {
            opMaterialDto.setMaterialName(fileName);
        }
        //保存数据
        opMaterial.setGameId(opMaterialDto.getGameId());
        opMaterial.setSubGameId(opMaterialDto.getSubGameId());
        opMaterial.setMaterialName(opMaterialDto.getMaterialName());
        opMaterial.setType1(opMaterialDto.getType1());
        opMaterial.setType2(opMaterialDto.getType2());
        opMaterial.setFormat(suffix);
        opMaterial.setSpecs(String.valueOf(width) + '*' + height);
        opMaterial.setShowUrl(
            jeecgBaseConfig.getPath().getMaterialFolder() + File.separator + suffix + File.separator
                + file.getName());
        opMaterial.setCreateBy(opMaterialDto.getCreateBy());
        opMaterial.setCreateTime(opMaterialDto.getCreateTime());
        //上传头条
        JrttTokenBo jrttTokenBo = new JrttTokenBo();
        //上传快手
        KsTokenBo ksTokenBo = new KsTokenBo();
        //未上传
        opMaterial.setJrttStatus(3);
        opMaterial.setKsStatus(3);
        opMaterial.setGdtStatus(3);
        //上传
        if (opMaterialDto.getJrttAccountId() != null) {
            opMaterial.setJrttCreateAccountId(opMaterialDto.getJrttAccountId());
            if (opMaterialDto.getInUploadJrtt() == 1) {
                jrttTokenBo = opJrttPutAccountService.getAccountToken(opMaterialDto.getJrttAccountId());
                opMaterial.setJrttStatus(1);
            }
        }
        if (opMaterialDto.getKsAccountId() != null) {
            opMaterial.setKuaishouCreateAccountId(opMaterialDto.getKsAccountId());
            if (opMaterialDto.getInUploadJrtt() == 1) {
                ksTokenBo = opKsPutAccountService.getAccountToken(opMaterialDto.getKsAccountId());
                opMaterial.setKsStatus(1);
            }
        }
        save(opMaterial);
        opMaterialDto.setId(opMaterial.getId());
        //头条推送素材
        if (opMaterialDto.getInUploadJrtt() == 1 && opMaterialDto.getJrttAccountId() != null) {
            pushJrttMaterial(jrttTokenBo,opMaterialDto);
        }
        //快手推送素材
        if (opMaterialDto.getInUploadKs() == 1 && opMaterialDto.getKsAccountId() != null) {
            pushKsMaterial(ksTokenBo,opMaterialDto);
        }
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        //获得文件类型
        String suffix = null;
        Boolean flag = false;
        String url = "";
        try {
            suffix = FileTypeFilter.getFileType(file);
            String path = jeecgBaseConfig.getPath().materialFolderPath() + File.separator + suffix;
            //创建临时文件
            File tempFile = new File(file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
            //随机命名文件
            String fileName = UUIDGenerator.generate() + "_" + DateUtils.getMillis();
            //重命名
            tempFile = cn.hutool.core.io.FileUtil.rename(tempFile, fileName, true, true);
            InputStream inputStream = new FileInputStream(tempFile);
            //File转换成MultipartFile
            file = new MockMultipartFile(tempFile.getName(), tempFile.getName(), null, inputStream);
            //删除临时文件
            tempFile.delete();
            flag = FileUtil.upload(path, file);
            if (!flag) {
                throw new JeecgBootException("文件上传失败");
            }
            url = path + File.separator + file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public void pushJrttMaterial(JrttTokenBo jrttTokenBo, OpMaterialDto opMaterialDto) {
        String fileMd5 = FileUtil.computeMD5(new File(opMaterialDto.getFile()));
        OpMaterial opMaterial = opMaterialMapper.selectById(opMaterialDto.getId());
        //使用线程池获取线程
        ExecutorService es = Executors.newSingleThreadExecutor();
        // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
        FutureTask<String> future = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (opMaterialDto.getType1() == 1) {
                    JrttImageAdRequest jrttImageAdRequest = new JrttImageAdRequest();
                    jrttImageAdRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
                    jrttImageAdRequest.setUploadType(JrttImageUploadType.UPLOAD_BY_FILE);
                    jrttImageAdRequest.setFilename(opMaterialDto.getMaterialName());
                    jrttImageAdRequest.setImageSignature(fileMd5);
                    JrttBaseResponse<JrttImageAdResponse> jrttBaseResponse = null;
                    try {
                        jrttImageAdRequest.setImageFile(new FileSystemResource(opMaterialDto.getFile()));
                        jrttBaseResponse = JrttMaterialApi.imageAd(
                            jrttImageAdRequest, jrttTokenBo.getAccessToken());
                    } catch (Exception e) {
                        opMaterial.setJrttStatus(2);
                        log.error("头条报错--->"+e);
                    }
                    if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
                        //修改状态为上传失败
                        opMaterial.setJrttStatus(2);
                    } else {
                        //上传成功
                        opMaterial.setJrttMaterialId(
                            String.valueOf(jrttBaseResponse.getData().getMaterialId()));
                        opMaterial.setJrttFileId(jrttBaseResponse.getData().getId());
                        opMaterial.setJrttMaterialInfo(
                            JSONObject.toJSONString(jrttBaseResponse.getData()));
                        opMaterial.setJrttStatus(0);
                    }
                } else {
                    JrttVideoAdRequest jrttVideoAdRequest = new JrttVideoAdRequest();
                    jrttVideoAdRequest.setAdvertiserId(jrttTokenBo.getAdvertiserId());
                    jrttVideoAdRequest.setUploadType(JrttImageUploadType.UPLOAD_BY_FILE);
                    jrttVideoAdRequest.setFilename(opMaterialDto.getMaterialName());
                    jrttVideoAdRequest.setVideoSignature(fileMd5);
                    JrttBaseResponse<JrttVideoAdResponse> jrttBaseResponse = null;
                    try {
                        jrttVideoAdRequest.setVideoFile(new FileSystemResource(opMaterialDto.getFile()));
                        jrttBaseResponse = JrttMaterialApi.videoAd(
                            jrttVideoAdRequest, jrttTokenBo.getAccessToken());
                    } catch (Exception e) {
                        opMaterial.setJrttStatus(2);
                        log.error("头条报错--->"+e);
                    }
                    if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
                        //修改状态为上传失败
                        opMaterial.setJrttStatus(2);
                    } else {
                        //上传成功
                        opMaterial.setJrttMaterialId(
                            String.valueOf(jrttBaseResponse.getData().getMaterialId()));
                        opMaterial.setJrttFileId(jrttBaseResponse.getData().getVideoId());
                        opMaterial.setJrttMaterialInfo(
                            JSONObject.toJSONString(jrttBaseResponse.getData()));
                        opMaterial.setJrttStatus(0);
                    }
                }
                opMaterialMapper.updateJrttOpMaterial(opMaterial);
                return null;
            }
        });
        //执行任务
        es.execute(future);
    }

    @Override
    public void pushKsMaterial(KsTokenBo ksTokenBo, OpMaterialDto opMaterialDto) {
        String fileMd5 = FileUtil.computeMD5(new File(opMaterialDto.getFile()));
        OpMaterial opMaterial = opMaterialMapper.selectById(opMaterialDto.getId());
        //使用线程池获取线程
        ExecutorService es = Executors.newSingleThreadExecutor();
        // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
        FutureTask<String> future = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (opMaterialDto.getType1() == 1) {
                    KsImageAdRequest ksImageAdRequest = new KsImageAdRequest();
                    ksImageAdRequest.setAdvertiserId(ksTokenBo.getAdvertiserId());
                    ksImageAdRequest.setUploadType(KsImageUploadType.UPLOAD_BY_FILE);
                    ksImageAdRequest.setSignature(fileMd5);
                    KsBaseResponse<KsImageAdResponse> ksBaseResponse = null;
                    try {
                        ksImageAdRequest.setFile(new FileSystemResource(opMaterialDto.getFile()));
                        ksBaseResponse = KsMaterialApi.imageAd(
                            ksImageAdRequest, ksTokenBo.getAccessToken());
                    } catch (Exception e) {
                        //修改状态为上传失败
                        opMaterial.setKsStatus(2);
                        log.error("头条报错--->"+e);
                    }
                    if (!JrttCodeConstant.OK.equals(ksBaseResponse.getCode())) {
                        //修改状态为上传失败
                        opMaterial.setKsStatus(2);
                    } else {
                        //上传成功
                        opMaterial.setKuaishouMaterialId(ksBaseResponse.getData().getImageToken());
                        opMaterial.setKuaishouFileId(ksBaseResponse.getData().getPicId());
                        opMaterial.setKuaishouMaterialInfo(
                            JSONObject.toJSONString(ksBaseResponse.getData()));
                        opMaterial.setKsStatus(0);
                    }
                } else {
                    KsVideoAdRequest ksVideoAdRequest = new KsVideoAdRequest();
                    ksVideoAdRequest.setAdvertiserId(ksTokenBo.getAdvertiserId());
                    ksVideoAdRequest.setPhotoName(opMaterialDto.getMaterialName());
                    ksVideoAdRequest.setSignature(fileMd5);
                    KsBaseResponse<KsVideoAdResponse> ksBaseResponse = null;
                    try {
                        ksVideoAdRequest.setFile(new FileSystemResource(opMaterialDto.getFile()));
                        ksBaseResponse = KsMaterialApi.videoAd(
                            ksVideoAdRequest, ksTokenBo.getAccessToken());
                    } catch (Exception e) {
                        //修改状态为上传失败
                        opMaterial.setKsStatus(2);
                        log.error("头条报错--->"+e);
                    }
                    if (!JrttCodeConstant.OK.equals(ksBaseResponse.getCode())) {
                        //修改状态为上传失败
                        opMaterial.setKsStatus(2);
                    } else {
                        //上传成功
                        opMaterial.setKuaishouFileId(ksBaseResponse.getData().getPhotoId());
                        opMaterial.setKuaishouMaterialInfo(
                            JSONObject.toJSONString(ksBaseResponse.getData()));
                        opMaterial.setKsStatus(0);
                    }
                }
                opMaterialMapper.updateKsOpMaterial(opMaterial);
                return null;
            }
        });
        //执行任务
        es.execute(future);
    }

    @Override
    public void setAccount(SetAccountDto setAccountDto) {
        OpMaterial update = new OpMaterial();
        update.setId(setAccountDto.getId());
        if (setAccountDto.getType() == 1) {
            update.setJrttCreateAccountId(setAccountDto.getAccountId());
        } else if (setAccountDto.getType() == 2) {
            update.setGdtCreateAccountId(setAccountDto.getAccountId());
        } else {
            update.setKuaishouCreateAccountId(setAccountDto.getAccountId());
        }
        updateById(update);
    }

    @Override
    public void pushMaterial(SetAccountDto setAccountDto) {
        OpMaterial opMaterial = opMaterialMapper.selectById(setAccountDto.getId());
        String url = jeecgBaseConfig.getPath().getUpload() + File.separator
            + opMaterial.getShowUrl();
        OpMaterialDto opMaterialDto = new OpMaterialDto();
        if (setAccountDto.getType() == 1) {
            //头条
            JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(
                opMaterial.getJrttCreateAccountId());
            //修改状态为正在上传
            opMaterial.setJrttStatus(1);
            opMaterialMapper.updateById(opMaterial);
            opMaterialDto.setMaterialName(opMaterial.getMaterialName());
            opMaterialDto.setFile(url);
            opMaterialDto.setType1(opMaterial.getType1());
            opMaterialDto.setId(opMaterial.getId());
            pushJrttMaterial(jrttTokenBo,opMaterialDto);
        }else if (setAccountDto.getType() == 2) {

        }else {
            //快手
            KsTokenBo ksTokenBo = opKsPutAccountService.getAccountToken(
                opMaterial.getKuaishouCreateAccountId());
            //修改状态为正在上传
            opMaterial.setKsStatus(1);
            opMaterialMapper.updateById(opMaterial);
            opMaterialDto.setMaterialName(opMaterial.getMaterialName());
            opMaterialDto.setFile(url);
            opMaterialDto.setType1(opMaterial.getType1());
            opMaterialDto.setId(opMaterial.getId());
            pushKsMaterial(ksTokenBo,opMaterialDto);
        }
    }

    @Override
    public IPage<ResponseSiteMaterialVo> getSiteMaterialPage(Page<ResponseSiteMaterialVo> page,
        QuerySiteMaterialVo queryParam) {
        Date dateTime = queryParam.getCreateTime();
        queryParam.setCreateTime(null);
        QueryWrapper<QuerySiteMaterialVo> queryWrapper = QueryGenerator
            .initQueryWrapper(queryParam, null);
        if (dateTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);
            calendar.add(Calendar.HOUR, 23);//时
            calendar.add(Calendar.MINUTE, 59);//时
            calendar.add(Calendar.SECOND, 59);//秒
            queryWrapper.gt("create_time", dateTime).lt("create_time", calendar.getTime());
        }
        queryWrapper.eq("type2", MaterialType.TYPE_SITE);
        return baseMapper.getSiteMaterialPage(page, queryWrapper);
    }

    @Override
    public List<OpMaterial> queryByIdList(String ids) {
        List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(
            Collectors.toList());
        List<OpMaterial> result = baseMapper.selectBatchIds(idList);
        return result;
    }
}
