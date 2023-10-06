package com.ozius.internship.project.infra;

import com.ozius.internship.project.entity.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


public class SpringProdDomainEventPublisher implements DomainEventPublisher {

    public final ApplicationEventPublisher applicationEventPublisher;

    public SpringProdDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public <T> void publishEvent(T event) {
        applicationEventPublisher.publishEvent(event);
    }
}
