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
    private static String param;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(param);
    }

}
