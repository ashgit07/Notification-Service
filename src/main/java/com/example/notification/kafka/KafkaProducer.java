package com.example.notification.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;


@Service
public class KafkaProducer {

    private static final Logger logger =  LoggerFactory.getLogger(KafkaProducer.class);
    private static final String TOPIC = "user1";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("Producing message -> %s", message));
        kafkaTemplate.send(TOPIC, message);
    }
}

