package com.ozius.internship.project.springcontext;

import com.ozius.internship.project.entity.DomainEventPublisher;
import com.ozius.internship.project.entity.seller.ReviewAddedEvent;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashSet;
import java.util.Set;

public class SpringDevDomainEventPublisher implements DomainEventPublisher {

    public final ApplicationEventPublisher applicationEventPublisher;

    private final Set<Object> events;

    public SpringDevDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.events = new HashSet<>();
    }

    @Override
    public <T> void publishEvent(T event) {
        events.add(event);
    }

    @Transactional
    public void raiseAllEvents(){
        events.forEach(applicationEventPublisher::publishEvent);
    }

}
