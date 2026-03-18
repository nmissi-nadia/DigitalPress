package com.mediatech.digitalpress.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendArticlePublishedEvent(Long articleId, Long authorId) {
        String message = "Article " + articleId + " published by user " + authorId;
        kafkaTemplate.send("article-published", message);
    }
}
