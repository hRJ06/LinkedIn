package com.Hindol.LinkedIn.Notification_Service.Consumer;

import com.Hindol.LinkedIn.Notification_Service.Client.ConnectionClient;
import com.Hindol.LinkedIn.Notification_Service.DTO.PersonDTO;
import com.Hindol.LinkedIn.Notification_Service.Service.SendNotificationService;
import com.Hindol.LinkedIn.Post_Service.Event.PostCreatedEvent;
import com.Hindol.LinkedIn.Post_Service.Event.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceConsumer {
    private final ConnectionClient connectionClient;
    private final SendNotificationService sendNotificationService;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent) {
        log.info("Sending notification - handlePostCreated : {}", postCreatedEvent);
        List<PersonDTO> connections = connectionClient.getFirstConnections(postCreatedEvent.getCreatorId());
        log.info("Connections - {}", connections);
        /* TODO - Send notification to connection */
        for(PersonDTO connection : connections) {
            sendNotificationService.send(connection.getUserId(), String.format("Your connection %d has created a post. Check it out.", postCreatedEvent.getCreatorId()));
        }

    }

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent) {
        log.info("Sending notification - handlePostLiked : {}", postLikedEvent);
        /* TODO - Send notification to connection */
        String message = String.format("Your post %d has been liked by %d", postLikedEvent.getPostId(), postLikedEvent.getLikedByUserId());
        sendNotificationService.send(postLikedEvent.getCreatorId(), message);
    }
}
