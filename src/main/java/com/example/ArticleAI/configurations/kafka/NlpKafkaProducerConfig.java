package com.example.ArticleAI.configurations.kafka;

import lombok.experimental.UtilityClass;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

@UtilityClass
public class NlpKafkaProducerConfig extends AbstractKafkaServerConfig {
    public Producer<Long, String> getProducer() {
        return new KafkaProducer<>(getConfig());
    }
}
