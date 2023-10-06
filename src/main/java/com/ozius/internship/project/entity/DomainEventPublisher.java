package com.ozius.internship.project.entity;

import org.springframework.context.ApplicationEvent;

public interface DomainEventPublisher {
    <T> void publishEvent(T event);
}
