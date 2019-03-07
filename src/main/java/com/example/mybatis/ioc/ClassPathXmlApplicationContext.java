package com.example.mybatis.ioc;

import com.example.mybatis.utils.ClassUtil;
import com.example.mybatis.utils.OptionalExt;
import lombok.val;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.mybatis.utils.ClassUtil.getClasses;


public class ClassPathXmlApplicationContext {

    //扫包范围
    private String packageName;

    ConcurrentMap<String, Object> initBean = null;

    public ClassPathXmlApplicationContext(String packageName) {
        this.packageName = packageName;
    }

    // 使用beanID查找对象
    public Object getBean(String beanId) throws Exception {

        // 1.使用反射机制获取该包下所有的类已经存在bean的注解类
        List<Class<?>> classExistService = findClassExistService();
        // 2.使用Java反射机制初始化对象
        initBean = initBean(classExistService);
        OptionalExt.ofNullable(initBean).orElseThrow(() -> new RuntimeException("初始化bean为空!"));
        // 3.使用beanID查找查找对应bean对象
        Object object = initBean.get(beanId);
        // 4.使用反射读取类的属性,赋值信息
        attrAssign(object);
        return object;
    }

    // 使用反射读取类的属性,赋值信息
    public void attrAssign(Object object) throws IllegalArgumentException, IllegalAccessException {
        // 1.获取类的属性是否存在 获取bean注解
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            //属性名称
            val fieldName = field.getName();
            Object bean = initBean.get(fieldName);
            if (Objects.nonNull(bean)) {
                // 2.使用属性名称查找bean容器赋值
                field.setAccessible(true);
                field.set(object, bean);
                continue;
            }
        }
    }

    // 使用反射机制获取该包下所有的类已经存在bean的注解类
    public List<Class<?>> findClassExistService() throws Exception {
        // 1.使用反射机制获取该包下所有的类
        Optional.ofNullable(packageName).orElseThrow(() -> new RuntimeException("扫包范围不能为空"));
        List<Class<?>> packageNameClasses = getClasses(packageName);
        // 2.使用反射技术获取当前包下所有的类
        // 3.存放类上有bean注入注解
        List<Class<?>> existClassesAnnotation = packageNameClasses.stream().filter(this::getAnnotation).collect(Collectors.toList());
        //List<Class> annotation = getAnnotation(packageNameClasses, (object) -> Objects.nonNull(object.getClass().getDeclaredAnnotation(ExtService.class)));
        // 4.判断该类上属否存在注解
        return existClassesAnnotation;
    }

    // 初始化bean对象
    public ConcurrentHashMap<String, Object> initBean(List<Class<?>> listClassesAnnotation) throws Exception {
        ConcurrentHashMap<String, Object> concurrentMap = new ConcurrentHashMap<>();
        for (Class classInfo : listClassesAnnotation) {
            Object newInstance = classInfo.newInstance();
            String beanId = ClassUtil.toLowerCaseFirstOne(classInfo.getSimpleName());
            concurrentMap.put(beanId, newInstance);
        }
        return concurrentMap;
    }





    public boolean getAnnotation(Class c) {
        if (Objects.nonNull(c.getDeclaredAnnotation(ExtService.class)))
            return true;
        else
            return false;
    }

    public List<Class> getAnnotation(List<Class> classes, Predicate<ExtService> predicate) {
        List<Class> result = Collections.emptyList();
        for (Class c : classes) {
            if (predicate.test((ExtService) c.getDeclaredAnnotation(ExtService.class)))
                result.add(c);
        }
        return result;
    }
}
