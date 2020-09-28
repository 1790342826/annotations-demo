package com.njzhxt.annotationsDemo.config;

import com.njzhxt.annotationsDemo.service.ParamProviderService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: ybc
 * Date: 2020/9/28 18:16
 * Content:
 */
@Aspect
@Component
public class ParamProviderAspect {

    @Autowired
    private ParamProviderService paramProviderService;

    @Pointcut("@annotation(ParamProvider)")
    public void annotationPointcut() {
    }

    @Before("annotationPointcut()")
    public void beforeMethod(JoinPoint joinPoint) {
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ParamProvider annotation = method.getAnnotation(ParamProvider.class);
        String paramValueByKey = paramProviderService.getParamValueByKey(annotation.value());
    }
}
