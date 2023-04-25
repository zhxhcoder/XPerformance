package com.zhxh.performance.support;

/*
 * Created by zhxh on 2023/4/25
 */
public class StringUtil {
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    public static String replace(String regex, String replacement, CharSequence s) {
        if (isEmpty(regex)) {
            return String.valueOf(s);
        }
        if (isEmpty(s)) {
            return "";
        }
        return s.toString().replaceAll(regex, replacement);
    }
}
