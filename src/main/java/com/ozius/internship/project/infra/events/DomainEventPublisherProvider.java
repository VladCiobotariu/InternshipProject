package com.ozius.internship.project.infra.events;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DomainEventPublisherProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static DomainEventPublisher getEventPublisher() {
        return applicationContext.getBean(DomainEventPublisher.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(DomainEventPublisherProvider.applicationContext != null) {
            throw new IllegalStateException("Only one initialization expected at startup!");
        }
        DomainEventPublisherProvider.applicationContext = applicationContext;

    }
}
