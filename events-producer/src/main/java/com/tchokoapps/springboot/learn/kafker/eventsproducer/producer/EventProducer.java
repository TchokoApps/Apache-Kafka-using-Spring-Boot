package com.tchokoapps.springboot.learn.kafker.eventsproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.NotNull;
import com.tchokoapps.springboot.learn.kafker.eventsproducer.domain.Event;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class EventProducer {

    public static final String TOPIC = "events";
    private KafkaTemplate<Integer, String> kafkaTemplate;
    private ObjectMapper objectMapper;

    public EventProducer(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendEvent(@NotNull Event event) throws JsonProcessingException {
        final Integer key = event.getId();
        final String value = objectMapper.writeValueAsString(event);
        ListenableFuture<SendResult<Integer, String>> sendResultListenableFuture = kafkaTemplate.sendDefault(key, value);
        sendResultListenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logFailure(key, value, throwable);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                logSuccess(key, value, result);
            }
        });
    }

    public void sendEventByTopic(@NotNull Event event) throws JsonProcessingException {

        ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>(TOPIC, event.getId(), objectMapper.writeValueAsString(event));
        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Failed to send producerRecord: {}, ex: {}", producerRecord, throwable);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("Send producerRecord succeded: {}", producerRecord);
            }
        });
    }

    public SendResult<Integer, String> sendEventSynchronous(@NotNull Event event) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        final Integer key = event.getId();
        final String value = objectMapper.writeValueAsString(event);
        return kafkaTemplate.sendDefault(key, value).get(1, TimeUnit.SECONDS);
    }

    private void logFailure(Integer key, String value, Throwable throwable) {
        log.error("Error happened when sending message with key = {}, value = {}, ex = {}", key, value, throwable);
    }

    private void logSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message sent successfully: key = {}, value = {}, result = {}", key, value, result);
    }
}
