package ru.danilenkoya.linkShortener.beanpostprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.danilenkoya.linkShortener.annotation.LogExecutionTime;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "link-shortener.logging", value = "enabled-log-execution-time", havingValue = "true")
public class LogExecutionTimeBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, BeanMethodsData> beanMethodsDataByBeanName = new HashMap<>();
    /**
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();

        for (Method method : methods) {
            boolean isAnnotated = method.isAnnotationPresent(LogExecutionTime.class);

            if (isAnnotated) {
                beanMethodsDataByBeanName.putIfAbsent(beanName, new BeanMethodsData(bean.getClass(), new ArrayList<>()));
                beanMethodsDataByBeanName.get(beanName).annotatedMethods().add(method);
            }
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    /**
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        BeanMethodsData beanMethodsData = beanMethodsDataByBeanName.get(beanName);

        if (beanMethodsData == null) {
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
        Class<?> beanClass = beanMethodsData.clazz();
        List<Method> annotatedMethods = beanMethodsData.annotatedMethods();

        return Proxy.newProxyInstance(beanClass.getClassLoader(), bean.getClass().getInterfaces(), (proxy, method, args) -> {

            boolean isAnnotated = annotatedMethods.stream().anyMatch(pojoMethod -> methodEquals(pojoMethod, method));

            if (isAnnotated) {
                long start = System.currentTimeMillis();
                try {
                    return method.invoke(bean, args);
                } catch (Exception e) {
                    throw e.getCause();
                } finally {
                    log.info("Время выполнения метода {} : {}", method.getName(), System.currentTimeMillis() - start);
                }
            }

            return method.invoke(bean, args);
        });
    }

    public boolean methodEquals(Method method1, Method method2) {
            if (method1.getName().equals(method2.getName())) {
                return equalParamTypes(method1.getParameterTypes(), method2.getParameterTypes());
        }
        return false;
    }

    private boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        /* Avoid unnecessary cloning */
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i])
                    return false;
            }
            return true;
        }
        return false;
    }

    private record BeanMethodsData(Class<?> clazz, List<Method> annotatedMethods) {}
}
