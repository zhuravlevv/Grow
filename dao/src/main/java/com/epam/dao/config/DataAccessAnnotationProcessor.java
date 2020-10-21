package com.epam.dao.config;

import com.epam.dao.annotations.InjectSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class DataAccessAnnotationProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessAnnotationProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        handleInjectSqlAnnotation(bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    private void handleInjectSqlAnnotation(Object bean){
        boolean beanContainsInjectSql = Arrays.stream(bean.getClass().getDeclaredFields()).anyMatch(field ->
                Arrays.stream(field.getAnnotations()).anyMatch(annotation ->
                        annotation.annotationType().equals(InjectSql.class))
        );
        if (beanContainsInjectSql) {
            Arrays.stream(bean.getClass().getDeclaredFields()).forEach(field -> {
                boolean fieldContainsInjectSql = Arrays.stream(field.getAnnotations()).anyMatch(annotation ->
                        annotation.annotationType().equals(InjectSql.class)
                );
                try {
                    if (fieldContainsInjectSql) {
                        InjectSql injectSql = field.getAnnotation(InjectSql.class);
                        ReflectionUtils.makeAccessible(field);
                        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                        InputStream inputStream = classloader.getResourceAsStream(injectSql.value());
                        if(inputStream == null)
                            throw new FileNotFoundException(injectSql.value());
                        String result = new BufferedReader(new InputStreamReader(inputStream))
                                .lines().collect(Collectors.joining(" "));
                        field.set(bean, result);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("File " + e.getMessage() + " doesn't exist");
                    LOGGER.error("File " + e.getMessage() + " doesn't exist");
                }
            });
        }
    }
}