package com.ozius.internship.project.infra.events;

public interface DomainEventPublisher {

    <T> void publishEvent(T event);

}
