package cn.zjc.utils;

import java.net.URL;

/**
 * @author zhangjinci
 * @version 2016/7/7 19:31
 */
public class ClassUtil {

    public static URL get(String path) {
        return get(path, true);
    }

    public static URL get(String path, boolean useClassLoader) {
        if(useClassLoader) {
            return ClassUtil.class.getClassLoader().getResource(path);
        } else {
            return ClassUtil.class.getResource(path);
        }
    }
}
