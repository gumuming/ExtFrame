package com.example.mybatis.ioc;


import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface ExtService {

    String value() default "";
}
