package com.ozius.internship.project.entity;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

public class EventPublisher implements ApplicationEventPublisherAware{

    private ApplicationEventPublisher applicationEventPublisher;

    private EventPublisher() {
    }

    public <T> void publishEvent(T event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private static final class EventPublisherHolder {
        private static final EventPublisher eventPublisher = new EventPublisher();
    }

    public static synchronized EventPublisher getInstance() {
        return EventPublisherHolder.eventPublisher;
    }
}
