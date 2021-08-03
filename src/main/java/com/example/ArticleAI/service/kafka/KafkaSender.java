package com.example.ArticleAI.service.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;

public interface KafkaSender {
    RecordMetadata send(String message);
}
