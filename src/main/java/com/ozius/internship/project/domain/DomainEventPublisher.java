package com.ozius.internship.project.domain;

public interface DomainEventPublisher {
    <T> void publishEvent(T event);
}
