package com.example.ArticleAI.configurations.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Topic {
    NLP("test.topic");

    private String topicName;
}
