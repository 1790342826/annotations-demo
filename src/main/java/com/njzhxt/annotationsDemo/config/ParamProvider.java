package com.njzhxt.annotationsDemo.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: ybc
 * Date: 2020/9/28 18:08
 * Content:
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)// 注解级别 ： 当前 运行级别
@Documented
@Component
public @interface ParamProvider {

    String value() default "";

}
