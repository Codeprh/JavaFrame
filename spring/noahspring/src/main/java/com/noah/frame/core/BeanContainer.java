package com.noah.frame.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.noah.frame.core.annotations.Component;
import com.noah.frame.core.annotations.Service;
import com.noah.frame.utils.ClassUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 1、获取到类的加载器（获取项目发布的实际路径）
 * 2、通过类加载器获取到加载的资源信息
 * 3、依据不同的资源类型，采用不同的方式获取资源的集合
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    /**
     * 存放bean
     */
    private final Map<Class<?>, Object> beanMap = Maps.newConcurrentMap();

    /**
     * bean是否加载过
     */
    @Getter
    private volatile boolean load = false;

    /**
     * 表示bean的注解
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION = Lists.newArrayList(Component.class, Service.class);

    /**
     * 获取单例对象
     *
     * @return
     */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    /**
     * 单例BeanContainer
     */
    private enum ContainerHolder {

        HOLDER;
        private BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    /**
     * 加载bean
     *
     * @param packageName 包名
     */
    public void loadBean(String packageName) {

        if (load) {
            log.warn("bean already load!!!");
            return;
        }

        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);

        if (CollectionUtils.isNotEmpty(classSet)) {

            for (Class<?> clz : classSet) {

                try {

                    Annotation[] annotations = clz.getAnnotations();
                    boolean containsAny = CollectionUtils.containsAny(BEAN_ANNOTATION, annotations);

                    if (containsAny) {

                        Constructor<?> declaredConstructor = clz.getDeclaredConstructor();
                        declaredConstructor.setAccessible(true);

                        beanMap.put(clz, declaredConstructor.newInstance());
                    }

                } catch (Exception e) {
                    log.error("load bean error", e);
                    throw new RuntimeException(e);
                }
            }

        }

        //load bean初始化完成
        load = true;
    }

    /**
     * 获取bean容器的大小
     *
     * @return
     */
    public int size() {
        return beanMap.size();
    }


}
