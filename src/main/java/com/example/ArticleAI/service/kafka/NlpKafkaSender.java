package com.example.ArticleAI.service.kafka;

import com.example.ArticleAI.configurations.kafka.Topic;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NlpKafkaSender implements KafkaSender {
    private final ApplicationContext applicationContext;


    @SneakyThrows
    @Override
    public RecordMetadata send(String message) {
        final Producer<Long, String> producer
                = (Producer<Long, String>) applicationContext.getBean("producerKafkaConfig");
        return  producer
                .send(new ProducerRecord<>(Topic.NLP.getTopicName(), message))
                .get();
    }
}
