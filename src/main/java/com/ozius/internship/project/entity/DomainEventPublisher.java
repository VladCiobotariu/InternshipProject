package com.ozius.internship.project.entity;

public interface DomainEventPublisher {
    <T> void publishEvent(T event);
}
