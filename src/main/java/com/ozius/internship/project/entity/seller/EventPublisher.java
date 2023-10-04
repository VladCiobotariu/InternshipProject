package com.ozius.internship.project.entity.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    private static EventPublisher eventPublisher;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private EventPublisher() {
    }

    public static synchronized EventPublisher getInstance() {
        if(eventPublisher == null) {
            synchronized (EventPublisher.class) {
                if(eventPublisher == null) {
                    eventPublisher = new EventPublisher();
                }
            }
        }
        return eventPublisher;
    }

    public <T> void publishEvent(T event) {
        applicationEventPublisher.publishEvent(event);
    }

}
