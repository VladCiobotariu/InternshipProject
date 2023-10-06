package com.ozius.internship.project.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MyTestEventListener {

    private Set<MyTestEvent> receivedMessages = new HashSet<>();

    @EventListener
    public void handleMyEvent(MyTestEvent event) {
        receivedMessages.add(event);
    }

    public Set<MyTestEvent> getReceivedMessages() {
        return receivedMessages;
    }
}
