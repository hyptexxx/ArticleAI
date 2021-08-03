package com.example.ArticleAI.configurations.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NlpKafkaProducerConfig extends AbstractKafkaServerConfig {

    @Bean("producerKafkaConfig")
    public Producer<Long, String> getProducer() {
        return new KafkaProducer<>(getConfig());
    }
}
