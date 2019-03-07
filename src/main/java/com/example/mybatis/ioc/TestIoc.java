package com.example.mybatis.ioc;

import com.example.mybatis.mvc.ExtController;

public class TestIoc {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com.example.mybatis.ioc");
        Object apple = context.getBean("apple");
        System.out.println(apple);

    }
}
