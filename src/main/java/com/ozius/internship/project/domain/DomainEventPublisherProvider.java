package com.ozius.internship.project.domain;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisherProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static DomainEventPublisher getEventPublisher() {
        return applicationContext.getBean(DomainEventPublisher.class);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        DomainEventPublisherProvider.applicationContext = applicationContext;
    }
}
