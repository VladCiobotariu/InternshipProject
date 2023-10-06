package com.ozius.internship.project.springcontext;

import com.ozius.internship.project.entity.DomainEventPublisher;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyEventListener {

    private final SpringDevDomainEventPublisher domainEventPublisher;

    public ApplicationReadyEventListener(SpringDevDomainEventPublisher domainEventPublisher) {
        this.domainEventPublisher = domainEventPublisher;
    }

    @EventListener
    public void handleApplicationReadyEvent(ApplicationReadyEvent applicationReadyEvent){
        domainEventPublisher.raiseAllEvents();
    }
}
