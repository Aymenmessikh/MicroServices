package com.microsevices.orderservice.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicBuilder {
    @Bean
    public NewTopic NotificationTopic(){
        return TopicBuilder.name("Not").build();
    }
}
