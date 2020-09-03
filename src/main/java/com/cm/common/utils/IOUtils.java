package com.cm.common.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 处理io相关的工具类 或者适用apache common的相关库
 */
public class IOUtils {

    public static String streamToString(InputStream inputStream, int length) throws IOException {
        byte[] bytes = new byte[length];
        inputStream.read(bytes);
        return new String(bytes);
    }
}
