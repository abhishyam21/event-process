package com.exam.eventmetrics.consumer;

import com.exam.eventmetrics.engine.DateTimeProcessor;
import com.exam.eventmetrics.engine.EventProcessor;
import com.exam.eventmetrics.pojoentites.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Consumer {

    @Autowired
    private EventProcessor eventProcessor;

    @Autowired
    private DateTimeProcessor dateTimeProcessor;

    @KafkaListener(topics = "eventmetrics", groupId = "group_json",
            containerFactory = "userKafkaListenerFactory", autoStartup = "true")
    public void consumeEvents(Event event) {
        log.info("Consumed JSON Message: " + event.getId());
            eventProcessor.processMessage(event.getPayload());
            dateTimeProcessor.processDateTimeOfEvents(event);
    }

}
