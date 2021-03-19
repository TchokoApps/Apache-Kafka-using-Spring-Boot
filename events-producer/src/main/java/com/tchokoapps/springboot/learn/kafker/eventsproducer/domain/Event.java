package com.tchokoapps.springboot.learn.kafker.eventsproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Event {
    private Integer id;
    private Book book;
}
