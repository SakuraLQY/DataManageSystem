package org.jeecg.common.util;

/**
 * @Author lili
 * @Description //时间
 * @Date 2023-05-06
 **/
public class TimeUtil {

    /**
     * @param time
     * @return java.lang.String
     * @Author lili
     * @Description 转化成 天-时-分-秒
     * @Date 15:00 2023/5/6
     **/
    public static String getTimeStr(Integer time) {
        String timeStr = "";
        if(time >= 86400) {
            timeStr += time / 86400 + "天";
            time = time % 86400;
        }
        if(time >= 3600) {
            timeStr += time / 3600 + "时";
            time = time % 3600;
        }
        if(time >= 60) {
            timeStr += time / 60 + "分";
            time = time % 60;
        }
        if(time >0) {
            timeStr += time + "秒";
        }
        return timeStr;

    }

}
