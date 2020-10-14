package com.njzhxt.annotationsDemo.config;

import com.njzhxt.annotationsDemo.service.ParamProviderService;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ParameterMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author ybc
 * @className ParamProviderConfig
 * @description TODO
 * @date 2020/10/14 14:26
 */
@Configuration
public class ParamProviderConfig
        // 尝试获取 使用自定义注解的类
        implements ApplicationListener<ContextRefreshedEvent>
{
    public static List<Invoker> invokerList = new ArrayList<>();

    // 获取自定义注解
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器
        if(event.getApplicationContext().getParent()==null){
            Map<String,Object> beans = event.getApplicationContext().getBeansWithAnnotation(ParamProvider.class);
            for(Object bean : beans.values()){
                ParamProvider ca = bean.getClass().getAnnotation(ParamProvider.class);
                System.out.println(bean.getClass().getName()+"==="+ca.value());
                Method[] methods = bean.getClass().getMethods();
                for (Method declaredMethod : methods) {
                    System.out.println(declaredMethod.getName());
                    ParamProvider ma = AnnotationUtils.findAnnotation(declaredMethod, ParamProvider.class);
                    if(ma != null){
                        invokerList.add(new Invoker(declaredMethod,ma.value(),bean));
                        System.out.println(bean.getClass().getName()+"==="+ca.value()+"==="+ma.value());
                    }
                }
                Method[] declaredMethods = bean.getClass().getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    System.out.println(declaredMethod.getName());
                    ParamProvider ma = declaredMethod.getAnnotation(ParamProvider.class);
                    if(ma != null){
                        System.out.println(bean.getClass().getName()+"==="+ca.value()+"==="+ma.value());
                    }
                }
            }
            System.err.println("=====onApplicationEvent====="+event.getSource().getClass().getName());
        }
        invoke();
    }

    private void invoke() {
        for (Invoker invoker : invokerList) {
            try {
                invoker.getMethod().invoke(invoker.getBean(),invoker.getTag());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Data
    class Invoker{
        private Method method;
        private String tag;
        private Object bean;

        public Invoker(Method method, String tag, Object bean) {
            this.method = method;
            this.tag = tag;
            this.bean = bean;
        }
    }

    List<Class> classList = new ArrayList<>();

    @PostConstruct
    public void init() {
        classList.add(TestRun.class);
        classList.forEach(e -> setName(e));
    }

    @Autowired
    private ParamProviderService paramProviderService;

    public void setName(Class clazz) {
//        //处理注解在类上
//        if (clazz.isAnnotationPresent(ParamProvider.class)) {
//            ParamProvider annotation = (ParamProvider) clazz.getAnnotation(ParamProvider.class);
//            try {
//                Field field = clazz.getDeclaredField("name");
//                field.setAccessible(true);
//                field.set(null, annotation.value());
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            return;
//        }
        //处理注解在属性上

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ParamProvider annotation = declaredField.getAnnotation(ParamProvider.class);
            if (null == annotation) {
                continue;
            }
            declaredField.setAccessible(true);
            try {
                String paramValueByKey = paramProviderService.getParamValueByKey(annotation.value());
                // 属性为静态变量 可以设置为 null
                // 尝试设置 不加static   给
                declaredField.set(null, paramValueByKey);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
