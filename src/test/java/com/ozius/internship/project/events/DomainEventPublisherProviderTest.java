package com.ozius.internship.project.events;

import com.ozius.internship.project.ProjectApplicationTest;
import com.ozius.internship.project.infra.events.DomainEventPublisherProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ProjectApplicationTest.class)
public class DomainEventPublisherProviderTest {

    @Autowired
    private MyTestEventListener eventListener;

//    @Test
//    public void test_message_is_sent_and_received() {
//        MyTestEvent myTestEvent = new MyTestEvent("ala bala");
//        DomainEventPublisherProvider.getEventPublisher().publishEvent(myTestEvent);
//
//        assertThat(eventListener.getReceivedMessages()).containsExactly(myTestEvent);
//
//        // todo - not working
//    }
}
