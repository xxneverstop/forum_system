package com.honortech.forum_system.utils;

import org.springframework.util.DigestUtils;

/**
 * MD5 加密工具
 */
public class MD5Util {
    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String md5(String str, String salt) {
        return md5(md5(str) + salt);
    }
}
