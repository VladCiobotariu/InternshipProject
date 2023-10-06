package com.ozius.internship.project.springcontext;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyEventListener {

    private final DeferredSpringEventPublisher domainEventPublisher;

    public ApplicationReadyEventListener(DeferredSpringEventPublisher domainEventPublisher) {
        this.domainEventPublisher = domainEventPublisher;
    }

    @EventListener
    public void handleApplicationReadyEvent(ApplicationReadyEvent applicationReadyEvent){
        domainEventPublisher.stopDeferEvents();
    }
}
