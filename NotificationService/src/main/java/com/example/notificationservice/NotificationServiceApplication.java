package com.example.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Slf4j
@Component
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
    @KafkaListener(topics = "Not",groupId = "NotificationId")
    public void handelNotificatio(OrderPlacedEvents orderPlacedEvents){
        log.info("Recived Notificatio From Order-{}",orderPlacedEvents.getOrderNumber());
    }

}
