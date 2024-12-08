package com.Hindol.LinkedIn.Post_Service.Configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic postCreatedTopic() {
        return new NewTopic("post-created-topic", 3, (short)1);
    }

    @Bean
    public NewTopic postLikedTopic() {
        return new NewTopic("post-liked-topic", 3, (short)1);
    }
}