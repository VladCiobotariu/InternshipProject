package com.ozius.internship.project.springcontext;

import com.ozius.internship.project.entity.DomainEventPublisher;
import com.ozius.internship.project.infra.SpringDomainEventPublisher;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Primary
public class DeferredSpringEventPublisher implements DomainEventPublisher {

    public final SpringDomainEventPublisher delegate;
    private boolean deferEvents;
    private final Set<Object> events;

    public DeferredSpringEventPublisher(SpringDomainEventPublisher delegate) {
        this.delegate = delegate;
        this.events = new HashSet<>();
        this.deferEvents = true;
    }

    @Override
    public <T> void publishEvent(T event) {
        if(deferEvents){
            events.add(event);
        } else {
            delegate.publishEvent(event);
        }
    }

    public void startDeferEvents() {
        this.deferEvents = true;
    }

    @Transactional
    public void stopDeferEvents() {
        this.deferEvents = false;
        events.forEach(delegate::publishEvent);
        events.clear();
    }
}
