package com.njzhxt.annotationsDemo.config;

import java.lang.annotation.*;

/**
 * @Author: ybc
 * Date: 2020/9/28 18:08
 * Content:
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)// 注解级别 ： 当前 运行级别
//@Target({ElementType.METHOD,ElementType.TYPE})
//@Retention(value = RetentionPolicy.RUNTIME)
//@Documented
public @interface ParamProvider {

    String value() default "";

}
