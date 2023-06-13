package org.jeecg.config.sign.interceptor;


import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.GameApi;
import org.jeecg.common.constant.PkgParentConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.util.IpUtils;
import org.jeecg.common.util.Md5Util;
import org.jeecg.config.SdkConfig;
import org.jeecg.config.sign.context.SdkContext;
import org.jeecg.config.sign.context.SdkInfo;
import org.jeecg.config.sign.util.BodyReaderHttpServletRequestWrapper;
import org.jeecg.config.sign.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.SortedMap;

/**
 * sdk 拦截器
 *
 * @author
 */
@Slf4j
public class SdkCheckInputInterceptor implements HandlerInterceptor {
    @Lazy
    @Autowired
    private GameApi gameApi;

    @Autowired
    private SdkConfig sdkConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        //获取全部参数(包括URL和body上的)
        SortedMap<String, String> allParams = HttpUtils.getAllParams(requestWrapper);
        String[] keys = allParams.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 获取游戏信息
        String subGameId = allParams.get("subGameId");
        String pkgId = allParams.get("pkgId");
        if(StringUtils.isBlank(subGameId)){
            throw new IdeaRunTimeException(ErrorCode.NO_FOUND_SUB_GAME);
        }

        OpSubGameModel opSubGameModel = gameApi.getOpSubGame(Integer.valueOf(subGameId));
        if(null == opSubGameModel){
            throw new IdeaRunTimeException(ErrorCode.NO_FOUND_SUB_GAME);
        }
        OpPkgModel opPkgModel = new OpPkgModel();
        if (!PkgParentConstant.DEFAULT_PKG_ID.toString().equals(pkgId)){
            opPkgModel = gameApi.getOpPkgById(Integer.valueOf(pkgId));
        }


        // 接口签名验证
        if(!sdkConfig.getIsDebug()){
            String str = "";
            for(Object key: keys){
                if("sign".equals(key) || "clientIp".equals(key)){
                    continue;
                }
                str += allParams.get(key);
            }
            String sign = Md5Util.md5Encode(str + opSubGameModel.getGameKey(), "utf-8");
            if(!Objects.equals(sign, allParams.get("sign"))){
                throw new IdeaRunTimeException(ErrorCode.SIGN_VERIFY_ERROR);
            }
        }
        SdkInfo sdkInfo = new SdkInfo();
        // 设置ip
        String clientIp = allParams.get("clientIp");
        if(StringUtils.isNotEmpty(allParams.get("clientIp"))){
            sdkInfo.setIp(clientIp);
        }else{
            sdkInfo.setIp(IpUtils.getIpAddr(request));
        }
        sdkInfo.setOpSubGameModel(opSubGameModel);
        sdkInfo.setOpPkgModel(opPkgModel);
        // 设置全局参数
        SdkContext.setSdkInfo(sdkInfo);
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SdkContext.remove();
    }
}
