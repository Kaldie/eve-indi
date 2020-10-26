package com.kaldie.eveindustry.service.experiments;

import java.util.Map.Entry;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanExpressionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExperimentRunner implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(ExperimentRunner.class);
    
    @Nonnull
    private ApplicationContext appContext;

    public void runAll() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(com.kaldie.eveindustry.eve_indi_annotations.Experiment.class));

        for (Entry<String, Object> entry : appContext.getBeansWithAnnotation(com.kaldie.eveindustry.eve_indi_annotations.Experiment.class).entrySet()) {
            try {
                if (entry.getValue() instanceof Task)
                {
                    Task task = (Task) entry.getValue();
                    logger.debug("setup of {}", task);
                    task.setup();
                    logger.debug("run of {}", task);
                    task.run();
                    logger.debug("breakdown of {}", task);
                    task.breakdown();
                    logger.debug("Finished breakdown of {}", task);
                }
            } catch (BeanExpressionException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        appContext = applicationContext;
    }

}
