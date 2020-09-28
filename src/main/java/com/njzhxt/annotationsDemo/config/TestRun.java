package com.njzhxt.annotationsDemo.config;

import com.njzhxt.annotationsDemo.service.ParamProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: ybc
 * Date: 2020/9/28 18:37
 * Content:
 */
@Component
public class TestRun implements ApplicationRunner {


    @ParamProvider(value = "123")
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("==============");

        Class clazz = Class.forName("com.njzhxt.annotationsDemo.config.TestRun");

        //获取类注解信息
        ParamProvider paramProvider =(ParamProvider) clazz.getAnnotation(ParamProvider.class);
//        System.out.println( paramProvider.value()+"---");

        //获取所以方法注解信息 ps:这里需要使用 isAnnotationPresent 判断方法上是否使用了注解
        Method[] allMethods = clazz.getDeclaredMethods();
        for(int i=0;i<allMethods.length;i++){
            if(allMethods[i].isAnnotationPresent(ParamProvider.class)) {
                ParamProvider methodAnno = allMethods[i].getAnnotation(ParamProvider.class);
                System.out.println("遍历:当前方法名为："+allMethods[i].getName()+" 的注解信息：---"+methodAnno.value() + "---");
            }
        }

//        //获取指定方法注解信息
//        Method methodTest = clazz.getDeclaredMethod("run");
//        ParamProvider methodAnnotest =  methodTest.getAnnotation(ParamProvider.class);
//        System.out.println(methodAnnotest.value()+"---");
//
//
//        //获取属性注解信息
//        Field nameField =  clazz.getDeclaredField("name");
//        ParamProvider attrAnno = nameField.getAnnotation(ParamProvider.class);
//        System.out.println(attrAnno.value()+"---");
    }

}
