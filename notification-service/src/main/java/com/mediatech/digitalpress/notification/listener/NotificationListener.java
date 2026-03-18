package com.mediatech.digitalpress.notification.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @KafkaListener(topics = "article-published", groupId = "notification-group")
    public void listen(String message) {
        // Simulate sending notification
        System.out.println("Notification sent: " + message);
        // In real implementation, send email, push notification, etc.
    }
}
