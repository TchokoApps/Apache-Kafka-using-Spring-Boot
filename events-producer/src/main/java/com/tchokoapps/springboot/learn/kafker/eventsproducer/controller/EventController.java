package com.tchokoapps.springboot.learn.kafker.eventsproducer.controller;

import com.tchokoapps.springboot.learn.kafker.eventsproducer.domain.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @PostMapping("/v1/event")
    public ResponseEntity<Event> postLibraryEvent(@RequestBody Event libraryEvent) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);

    }
}
