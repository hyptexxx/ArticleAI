package com.example.ArticleAI.service.kafka;

import com.example.ArticleAI.configurations.kafka.NlpKafkaProducerConfig;
import com.example.ArticleAI.configurations.kafka.Topic;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NlpKafkaSender implements KafkaSender {

    @SneakyThrows
    @Override
    public RecordMetadata send(String message) {
        return NlpKafkaProducerConfig
                .getProducer()
                .send(new ProducerRecord<>(Topic.NLP.getTopicName(), message))
                .get();
    }
}
