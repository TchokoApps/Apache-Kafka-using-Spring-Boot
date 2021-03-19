package com.tchokoapps.springboot.learn.kafker.eventsproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tchokoapps.springboot.learn.kafker.eventsproducer.domain.Event;
import com.tchokoapps.springboot.learn.kafker.eventsproducer.producer.EventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
public class EventController {

    private EventProducer eventProducer;

    public EventController(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @PostMapping("/v1/event")
    public ResponseEntity<Event> sendEvent(@RequestBody Event event) throws JsonProcessingException {
        eventProducer.sendEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @PostMapping("/v1/event/sync")
    public ResponseEntity<Event> sendEventSynchronous(@RequestBody Event event) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        eventProducer.sendEventSynchronous(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @PostMapping("/v1/event/by/topic")
    public ResponseEntity<Event> sendEventByTopic(@RequestBody Event event) throws JsonProcessingException {
        eventProducer.sendEventByTopic(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
