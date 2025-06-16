package ru.aston.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, String>> sendMessage(String topic, String key, String message) {

        logger.info("sendMessage() topic = {}, key = {}, message = {}", topic, key, message);
        return this.kafkaTemplate.send(topic, key, message);
    }
}
