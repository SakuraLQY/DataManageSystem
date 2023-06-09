package org.jeecg.modules.pay.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author lili
 * @Description //TODO $
 * @Date $ 2022-12-26$
 **/
public class RequestToMap {

    // 将request中的参数转换成Map
    public static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }

}
