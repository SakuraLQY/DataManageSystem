package org.jeecg.modules.users.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version V1.0
 * @description: 正则表达式工具类
 * @author: xmh
 * @date: 2022/12/15 12:00
 */
public class RegularUtils {

    /**
     * @param realName:
     * @return boolean
     * @author xmh
     * @description 正则表达式验证姓名
     * @date 2022/12/15 13:33
     */
    public static boolean checkRealName(String realName) {
        String regularExpression = "(^[\\u4e00-\\u9fa5.·]*$)";

        return realName.matches(regularExpression);
    }

    /**
     * checkPhone.
     *
     * @description 校验手机号码是否正确
     * @author chenyw
     * @date 2022/12/6/006 13:47
     * @param: phone
     * @return: boolean
     */
    public static boolean checkPhone(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    /**
     * checkUserName.
     *
     * @description  验证用户名
     * @author chenyw
     * @date 2022/12/6/006 14:01
     * @param: userName
     * @return: boolean
     */
    public static boolean checkUserName(String userName) {
        String regex = "^[a-zA-Z][0-9a-zA-Z_]{5,16}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userName);
        return m.matches();
    }

    /**
     * checkPassword.
     *
     * @description 验证密码
     * @author chenyw
     * @date 2022/12/6/006 14:01
     * @param: passwd
     * @return: boolean
     */
    public static boolean checkPassword(String passwd) {
        String regex = "^[A-Za-z0-9][A-Za-z0-9!@#$%^&*_.]{6,32}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(passwd);
        return m.matches();
    }
}
