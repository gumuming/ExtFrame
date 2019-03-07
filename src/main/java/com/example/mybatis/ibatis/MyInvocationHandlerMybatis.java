package com.example.mybatis.ibatis;

import com.example.mybatis.utils.JDBCUtils;
import com.example.mybatis.utils.OptionalExt;
import com.example.mybatis.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能说明:mybatis框架注解版本 插入与查询注解
 * 1.使用动态代理技术,获取接口方法上的sql语句
 * 2.根据不同的SQL语句执行
 */
@Slf4j
public class MyInvocationHandlerMybatis implements InvocationHandler {

    //被代理对象
    private Object object;

    public MyInvocationHandlerMybatis(Object object) {
        this.object = object;
    }

    /**
     * 该方法负责集中处理动态代理类上的所有方法调用。 调用处理器根据这三个参数进行预处理或分派到委托类实例上反射执行
     *
     * @param proxy  代理类实例
     * @param method 被调用的方法对象
     * @param args   调用参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ExtInsert declaredAnnotation = method.getDeclaredAnnotation(ExtInsert.class);
        if (Objects.nonNull(declaredAnnotation))
            return insertSql(declaredAnnotation, method, args);
        ExtSelect declaredAnnotation1 = method.getDeclaredAnnotation(ExtSelect.class);
        if (Objects.nonNull(declaredAnnotation1))
            return selectMybatis(declaredAnnotation1, method, args);
        return null;
    }

    /**
     * 执行查询sql语句
     *
     * @param extSelect method name
     * @param method    method
     * @param args      object[]
     * @return object
     */
    public Object selectMybatis(ExtSelect extSelect, Method method, Object[] args) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        // 获取查询SQL语句
        val selectSql = extSelect.value();
        // 将方法上的参数存放在Map集合中
        Parameter[] parameters = method.getParameters();
        // 获取方法上参数集合
        ConcurrentHashMap<String, Object> paramMap = getExtParams(parameters, args);
        // 获取SQL传递参数
        List<String> sqlSelectParameter = SqlUtils.sqlSelectParameter(selectSql);
        // 排序参数
        List<Object> paramValues = Collections.emptyList();
        sqlSelectParameter.forEach(s -> paramValues.add(paramMap.get(s).toString()));
        // 变为?号
        String paramQuestion = SqlUtils.paramQuestion(selectSql, sqlSelectParameter);
        log.info("paramQuestion: {}", paramQuestion);
        // 调用JDBC代码查询
        ResultSet query = JDBCUtils.query(paramQuestion, paramValues);
        // 获取返回类型
        Class<?> returnType = method.getReturnType();
        if (!query.next())
            return null;//没有找到数据
        // 向上移动
        query.previous();
        // 实例化对象
        Object newInstance = returnType.newInstance();
        while (query.next()) {
            for (String paramName : sqlSelectParameter) {
                Object object = query.getObject(paramName);
                Field declaredField = returnType.getDeclaredField(paramName);
                declaredField.setAccessible(true);
                declaredField.set(newInstance, object);
            }
        }
        return newInstance;
    }

    /**
     * 执行插入sql语句
     *
     * @param extInsert string
     * @param method    method
     * @param args      object[]
     * @return int
     */
    public int insertSql(ExtInsert extInsert, Method method, Object[] args) {
        // 获取注解上的sql
        String insertSql = extInsert.value();
        log.info("insertSql: {}", insertSql);
        // 获取方法上的参数
        Parameter[] parameters = method.getParameters();
        // 将方法上的参数存放在Map集合中
        ConcurrentHashMap<String, Object> paramMap = getExtParams(parameters, args);
        // 获取SQL语句上需要传递的参数
        String[] sqlParameter = SqlUtils.sqlInsertParameter(insertSql);
        List<Object> paramValues = Collections.emptyList();
        for (String s : sqlParameter) {
            Object object = paramMap.get(s);
            paramValues.add(object);
        }
        // 将SQL语句替换为？号
        String paramQuestion = SqlUtils.paramQuestion(insertSql, sqlParameter);
        log.info("new sql: {}", paramQuestion);
        // 调用jdbc代码执行
        int insert = JDBCUtils.insert(paramQuestion, false, paramValues);
        return insert;
    }

    /**
     * @param parameters 注解参数
     * @param args       接口参数
     * @return map
     */
    private ConcurrentHashMap<String, Object> getExtParams(Parameter[] parameters, Object[] args) {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            ExtParam declaredAnnotation = parameters[i].getDeclaredAnnotation(ExtParam.class);
            map.put(declaredAnnotation.value(), args[i]);
        }
        return map;
    }
}