package com.example.mybatis.mvc;



import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtController {

    String value() default "";
}
