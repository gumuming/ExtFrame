package com.example.mybatis.ibatis;

import java.lang.reflect.Proxy;

/**
 * 获取SqlSession对象
 */
public class SqlSession {

    // 获取getMapper
    public static <T> T getMapper(Class<T> clas)
            throws IllegalArgumentException, InstantiationException, IllegalAccessException {
        return (T) Proxy.newProxyInstance(clas.getClassLoader(), new Class[] { clas },
                new MyInvocationHandlerMybatis(clas));
    }
}
