package com.tchokoapps.springboot.learn.kafker.eventsproducer.controller;

import com.tchokoapps.springboot.learn.kafker.eventsproducer.domain.Book;
import com.tchokoapps.springboot.learn.kafker.eventsproducer.domain.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaToJsonConverter {
    @GetMapping("to/json/book")
    public Book convertBookObjToJson() {
        return new Book();
    }

    @GetMapping("to/json/event")
    public Event convertLibraryEventObjToJson() {
        return new Event(null, new Book());
    }
}
