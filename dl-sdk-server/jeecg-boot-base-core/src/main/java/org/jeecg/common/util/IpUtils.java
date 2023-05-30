package org.jeecg.common.util;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.jeecg.common.constant.CommonConstant;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * IP地址
 *
 * @Author scott
 * @email jeecgos@163.com
 * @Date 2019年01月14日
 */
public class IpUtils {
	private static Logger logger = LoggerFactory.getLogger(IpUtils.class);

	/**
	 * 获取IP地址
	 *
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 ||CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || CommonConstant.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
        	logger.error("IPUtils ERROR ", e);
        }

//        //使用代理，则获取第一个IP地址
//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
//			if(ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}

        return ip;
    }

    public static String getCityInfo(String ip) throws Exception {
        String path = IpUtils.class.getClassLoader().getResource("ip2region.xdb").getPath();
        Searcher searcher = Searcher.newWithFileOnly(path);
        // 2、查询
        long sTime = System.nanoTime();
        String region = searcher.search(ip);
        // 3、关闭资源
        searcher.close();
        return region.split("\\|")[2].replaceAll("省","");
    }

    /**
     * @param ip
     * @return java.lang.String
     * @Author lili
     * @Description 格式：国家-省份-市
     * @Date 11:14 2023/4/26
     **/
    public static String getPlaceInfo(String ip) throws Exception {
        String path = IpUtils.class.getClassLoader().getResource("ip2region.xdb").getPath();
        Searcher searcher = Searcher.newWithFileOnly(path);
        // 2、查询
        long sTime = System.nanoTime();
        String region = searcher.search(ip);
        List<String> list = Arrays.asList(region.replaceAll("省","").replaceAll("市","").split("\\|"));
        String res = list.get(0) + "-" + list.get(2) + "-" + list.get(3);
        // 3、关闭资源
        searcher.close();
        return res;
    }
}
