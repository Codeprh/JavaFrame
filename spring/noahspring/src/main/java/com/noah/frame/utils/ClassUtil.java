package com.noah.frame.utils;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.Set;

/**
 * class定义工具类
 *
 * @author noah
 */
@Slf4j
public class ClassUtil {

    public static final String FILE = "file";
    public static final String CLASS = ".class";

    public static void main(String[] args) {

        Set<Class<?>> packageClass = ClassUtil.extractPackageClass("com.noah.spring1");
        log.info("data:{}", packageClass);
    }

    /**
     * 根据包路径提取class
     *
     * @param packageName xxx.xx.x
     * @return 返回所有的set集合
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {

        Set<Class<?>> resut = Sets.newHashSet();
        if (StringUtils.isEmpty(packageName)) {
            return resut;
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(packageName.replace(".", "/"));

        if (Objects.isNull(url)) {
            log.warn("package not find anything , package:{}", packageName);
            return resut;
        }

        //1.根据不同类型的资源，采用不同的方式获取资源的集合
        if (url.getProtocol().equalsIgnoreCase(FILE)) {

            File fileOrDirectory = new File(url.getPath());
            doExtract(fileOrDirectory, resut, packageName);

        }

        return resut;
    }

    /**
     * 递归提取class文件（包和子包下面）
     *
     * @param fileOrDirectory
     */
    private static void doExtract(File fileOrDirectory, Set<Class<?>> resut, String packageName) {

        if (fileOrDirectory.isFile()) {
            return;
        }

        File[] listFiles = fileOrDirectory.listFiles((file) -> {

            //递归处理
            if (file.isDirectory()) {
                return true;
            }

            //判断是否是class文件
            String absolutePath = file.getAbsolutePath();
            if (absolutePath.endsWith(CLASS)) {
                addToClassSet(packageName, absolutePath, resut);
            }

            return false;
        });

        if (Objects.nonNull(listFiles) && listFiles.length > 0) {
            //递归处理
            for (File f : listFiles) {
                doExtract(f, resut, packageName);
            }
        }

    }

    /**
     * 把class添加到classSet集合中
     *
     * @param packageName
     * @param absolutePath
     */
    private static void addToClassSet(String packageName, String absolutePath, Set<Class<?>> resut) {

        absolutePath = absolutePath.replace(File.separator, ".");
        String className = absolutePath.substring(absolutePath.indexOf(packageName));

        className = className.substring(0, className.lastIndexOf("."));

        try {

            Class<?> targetClass = Class.forName(className);
            resut.add(targetClass);
        } catch (ClassNotFoundException e) {
            log.error("reflection get class error:", e);
            throw new RuntimeException(e);
        }


    }
}
