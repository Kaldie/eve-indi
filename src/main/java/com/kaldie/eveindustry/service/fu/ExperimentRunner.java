package com.kaldie.eveindustry.service.fu;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@Configuration
@RequiredArgsConstructor
public class ExperimentRunner implements ApplicationContextAware {

    // @Nonnull
    private ApplicationContext appContext;

    // @Override
    // public void setApplicationContext(ApplicationContext applicationContext)
    // throws BeansException {
    // // TODO Auto-generated method stub
    // this.applicationContext = applicationContext;

    // }
    @PostConstruct
    public void runAll() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(com.kaldie.eveindustry.eve_indi_annotations.Experiment));

        for (BeanDefinition beanDefinition : scanner.findCandidateComponents("com.kaldie.eveindustry")) {
            System.out.println(beanDefinition.getBeanClassName());
            System.out.println(appContext.getId());
            System.out.println(appContext.getDisplayName());
            try {

                Class<?> c = BeanGenerator.class.getClassLoader().loadClass(beanDefinition.getBeanClassName());
                // Constructor<?> constructor = c.getConstructor();
                // Object instance = constructor.newInstance();
                // Task task = (Task) instance;
                // appContext.getAutowireCapableBeanFactory().autowireBean(instance);
                Tasklala task = (Tasklala) appContext.getBean(beanDefinition.getBeanClassName());

                // task.setup();
                // task.run();
                // task.breakdown();

            } catch (SecurityException | IllegalArgumentException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

}
