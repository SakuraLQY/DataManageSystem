package org.jeecg.modules.game.service.impl;

import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cdn.model.v20141111.PushObjectCacheRequest;
import com.aliyuncs.cdn.model.v20141111.PushObjectCacheResponse;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesRequest;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.filter.FileTypeFilter;
import org.jeecg.common.util.filter.StrAttackFilter;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.util.oss.OssBootUtil;
import org.jeecg.modules.game.dto.OpPrivacyPolicyDto;
import org.jeecg.modules.game.entity.OpPrivacyPolicy;
import org.jeecg.modules.game.mapper.OpPrivacyPolicyMapper;
import org.jeecg.modules.game.service.IOpPkgService;
import org.jeecg.modules.game.service.IOpPrivacyPolicyService;
import org.jeecg.common.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: op_privacy_policy
 * @Author: jeecg-boot
 * @Date: 2022-12-28
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
@Slf4j
public class OpPrivacyPolicyServiceImpl extends
    ServiceImpl<OpPrivacyPolicyMapper, OpPrivacyPolicy> implements IOpPrivacyPolicyService {

    private static String[] forbidType = {"html"};

    @Autowired
    private OpPrivacyPolicyMapper opPrivacyPolicyMapper;

    @Autowired
    private IOpPkgService opPkgService;

    @Override
    public IPage<OpPrivacyPolicy> getPrivacyPolicyList(Page<T> page,
        OpPrivacyPolicyDto opPrivacyPolicy) {
        QueryWrapper<OpPrivacyPolicy> q = new QueryWrapper<>();
        if (null != opPrivacyPolicy.getName()) {
            q.like("name", opPrivacyPolicy.getName());
        }
        if (null != opPrivacyPolicy.getCreateBy()) {
            q.eq("create_by", opPrivacyPolicy.getCreateBy());
        }
        if (null != opPrivacyPolicy.getStartTime()) {
            q.ge("create_time", opPrivacyPolicy.getStartTime());
        }
        if (null != opPrivacyPolicy.getEndTime()) {
            q.le("create_time", opPrivacyPolicy.getEndTime());
        }
        return baseMapper.getPrivacyPolicyList(page, q);
    }

    @Override
    public void add(OpPrivacyPolicy opPrivacyPolicy) {
        try {
            File file = FileUtil.insertToFile(opPrivacyPolicy.getContent());
            String path = OssBootUtil.getPrivacyPolicyUrl();
            InputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
            String url = uploadToOSS(multipartFile, path, null, forbidType);
            if (url.isEmpty()) {
                throw new JeecgBootException("文件上传失败");
            }
            url = url.replace(OssBootUtil.getBucketName() + "." + OssBootUtil.getEndPoint(),
                OssBootUtil.getCdnUrl());
            url = url.replace("https", "http");
            String res = refreshObjectCaches(url);
            if (res.isEmpty()) {
                throw new JeecgBootException("cdn刷新失败");
            }
            res = pushObjectCache(url);
            if (res.isEmpty()) {
                throw new JeecgBootException("cdn预热失败");
            }
            //删除临时文件
            file.delete();
            opPrivacyPolicy.setUrl(url);
            save(opPrivacyPolicy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(OpPrivacyPolicy opPrivacyPolicy) {
        try {
            String fileUrl = opPrivacyPolicy.getUrl();
            //在temp中创建临时文件
            String tempFile = fileUrl.substring(fileUrl.indexOf("temp"), fileUrl.length());
            String tempDir = System.getProperty("java.io.tmpdir");
            File file = new File(tempDir + tempFile);
            FileWriter fw = new FileWriter(file);
            fw.write(opPrivacyPolicy.getContent());
            fw.flush();
            fw.close();
            String path = OssBootUtil.getPrivacyPolicyUrl();
            InputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
            String url = uploadToOSS(multipartFile, path, null, forbidType);
            if (url.isEmpty()) {
                throw new JeecgBootException("文件上传失败");
            }
            url = url.replace(OssBootUtil.getBucketName() + "." + OssBootUtil.getEndPoint(),
                OssBootUtil.getCdnUrl());
            url = url.replace("https", "http");
            String res = refreshObjectCaches(url);
            if (res.isEmpty()) {
                throw new JeecgBootException("cdn刷新失败");
            }
            res = pushObjectCache(url);
            if (res.isEmpty()) {
                throw new JeecgBootException("cdn预热失败");
            }
            file.delete();
            opPrivacyPolicy.setUrl(url);
            updateById(opPrivacyPolicy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadToOSS(MultipartFile file, String fileDir, String customBucket,
        String[] forbidType) {
        String filePath = null;
        try {
            //获得文件类型
            String suffix = FileTypeFilter.getFileType(file);
            for (String type : forbidType) {
                if (!type.contains(suffix)) {
                    throw new Exception("上传失败，非法文件类型：" + suffix);
                }
            }
            OssBootUtil.initOss(OssBootUtil.getEndPoint(), OssBootUtil.getAccessKeyId(),
                OssBootUtil.getAccessKeySecret());
            StringBuilder fileUrl = new StringBuilder();
            String newBucket = OssBootUtil.getBucketName();
            if (oConvertUtils.isNotEmpty(customBucket)) {
                newBucket = customBucket;
            }
            //判断桶是否存在,不存在则创建桶
            if (!OssBootUtil.getOssClient().doesBucketExist(newBucket)) {
                OssBootUtil.getOssClient().createBucket(newBucket);
            }
            // 获取文件名
            String orgName = file.getOriginalFilename();
            if ("" == orgName) {
                orgName = file.getName();
            }
            orgName = CommonUtils.getFileName(orgName);
            String fileName = orgName.indexOf(".") == -1
                ? "pkg" + "_" + orgName
                : "pkg" + "_" + orgName.substring(0, orgName.lastIndexOf("."))
                    + orgName.substring(orgName.lastIndexOf("."));
            if (!fileDir.endsWith(SymbolConstant.SINGLE_SLASH)) {
                fileDir = fileDir.concat(SymbolConstant.SINGLE_SLASH);
            }
            //update-begin-author:wangshuai date:20201012 for: 过滤上传文件夹名特殊字符，防止攻击
            fileDir = StrAttackFilter.filter(fileDir);
            //update-end-author:wangshuai date:20201012 for: 过滤上传文件夹名特殊字符，防止攻击
            fileUrl = fileUrl.append(fileDir + fileName);

            if (oConvertUtils.isNotEmpty(OssBootUtil.getStaticDomain())
                && OssBootUtil.getStaticDomain().toLowerCase().startsWith(
                CommonConstant.STR_HTTP)) {
                filePath = OssBootUtil.getStaticDomain() + SymbolConstant.SINGLE_SLASH + fileUrl;
            } else {
                filePath = "https://" + newBucket + "." + OssBootUtil.getEndPoint()
                    + SymbolConstant.SINGLE_SLASH + fileUrl;
            }
            PutObjectResult result = OssBootUtil.getOssClient()
                .putObject(newBucket, fileUrl.toString(), file.getInputStream());
            // 设置权限(公开读)
            //            ossClient.setBucketAcl(newBucket, CannedAccessControlList.PublicRead);
            if (result != null) {
                log.info("------OSS文件上传成功------" + fileUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return filePath;
    }

    @Override
    public String pushObjectCache(String objectPath) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
            OssBootUtil.getAccessKeyId(), OssBootUtil.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        PushObjectCacheRequest request = new PushObjectCacheRequest();
        request.setObjectPath(objectPath);
        try {
            PushObjectCacheResponse response = client.getAcsResponse(request);
            return new Gson().toJson(response);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String refreshObjectCaches(String objectPath) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
            OssBootUtil.getAccessKeyId(), OssBootUtil.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        RefreshObjectCachesRequest request = new RefreshObjectCachesRequest();
        request.setObjectPath(objectPath);
        request.setObjectType("File");
        try {
            RefreshObjectCachesResponse response = client.getAcsResponse(request);
            return new Gson().toJson(response);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
