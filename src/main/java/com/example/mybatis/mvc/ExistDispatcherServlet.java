package com.example.mybatis.mvc;

import com.example.mybatis.ioc.ExtService;
import com.example.mybatis.utils.ClassUtil;
import lombok.val;
import lombok.var;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class ExistDispatcherServlet extends HttpServlet {

    // mvc bean key=beanId ,value=对象
    ConcurrentHashMap<String, Object> beans = new ConcurrentHashMap<>();

    // mvc 请求方法 key=requestUrl,value=对象
    ConcurrentHashMap<String, Object> urls = new ConcurrentHashMap<>();

    // mvc 请求方法 key=requestUrl,value=方法
    ConcurrentHashMap<String, String> methods = new ConcurrentHashMap<>();


    //1 初始化自定义SpringMVC容器
    public void init() {

        // 1.获取当前包下所有的类
        List<Class<?>> classes = ClassUtil.getClasses("com.example.mybatis.mvc");

        // 2.初始化当前包下所有的类,使用Java反射机制初始化对象存放在SpringMVC容器中key(beanId)-value(当前实例对象)
        try {
            findClassMVCBeans(classes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3.初始化HandlerMapping方法,将url和方法对应上
    }

    // 2.初始化当前包下所有的类,使用Java反射机制初始化对象存放在SpringMVC容器中key(beanId)-value(当前实例对象)
    public void findClassMVCBeans(List<Class<?>> classes) throws IllegalAccessException, InstantiationException {
        beans = new ConcurrentHashMap<>();
        for (Class c : classes) {
            Annotation declaredAnnotation = c.getDeclaredAnnotation(ExtController.class);
            if (Objects.nonNull(declaredAnnotation)) {
                String beanId = ClassUtil.toLowerCaseFirstOne(c.getSimpleName());
                beans.put(beanId, c.newInstance());
            }
        }
    }

    // 3.初始化HandlerMapping方法,将url和方法对应上
    public void handlerMapping(ConcurrentHashMap<String, Object> mvcBeans) {
        //bean 对象
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object mvcObject = entry.getValue();

            // 判断类上是否有@ExtRequestMapping注解
            Class<?> objectClass = mvcObject.getClass();
            ExtRequestingMapping declaredAnnotation = mvcObject.getClass().getDeclaredAnnotation(ExtRequestingMapping.class);
            String classUrl = "";
            if (Objects.nonNull(declaredAnnotation))
                classUrl = declaredAnnotation.value();

            Method[] declaredMethods = objectClass.getDeclaredMethods();
            // 遍历当前类的所有方法,判断方法上是否有注解
            for (Method method : declaredMethods) {
                ExtRequestingMapping declaredAnnotation1 = method.getDeclaredAnnotation(ExtRequestingMapping.class);
                if (Objects.nonNull(declaredAnnotation1)) {
                    var methodUrl = declaredAnnotation1.value();
                    methods.put(classUrl + methodUrl, method.getName());
                    urls.put(classUrl + methodUrl, mvcObject);
                }
            }
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    public void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        // 1.获取请求url地址
        val requestURI = req.getRequestURI();
        // 2.使用请求url查找对应mvc 控制器bean
        Object object = urls.get(requestURI);
        if (null == object) {
            System.out.println("Not find method 404");
            resp.getWriter().println("Not find object 404");
            return;
        }
        // 3.获取对应的请求方法
        String methodName = methods.get(requestURI);
        if (null == methodName || "".equals(methodName)) {
            resp.getWriter().println("Not find method 404");
            return;
        }
        // 4.使用java反射技术执行方法 针对 String
        String o = (String)methodInvoke(object.getClass(), object, methodName);
        // 5.视图展示
        viewDisplay(o,req,resp);

    }

    // 执行方法
    public Object methodInvoke(Class<? extends Object> classInfo, Object object, String methodName) {
        Object result = null;
        try {
            Method method = classInfo.getMethod(methodName);
            result = method.invoke(object);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
            return result;
    }

    public void viewDisplay(String pageName, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // 获取后缀信息
        String suffix = ".jsp";
        // 页面目录地址
        String prefix = "/";
        req.getRequestDispatcher(prefix + pageName + suffix).forward(req, res);
    }
}
