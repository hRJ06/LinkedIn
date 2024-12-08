package com.Hindol.LinkedIn.Notification_Service.Consumer;

import com.Hindol.LinkedIn.Connection_Service.Event.AcceptConnectionRequestEvent;
import com.Hindol.LinkedIn.Connection_Service.Event.SendConnectionRequestEvent;
import com.Hindol.LinkedIn.Notification_Service.Service.SendNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionServiceConsumer {
    private final SendNotificationService sendNotificationService;
    @KafkaListener(topics = "send-connection-request-topic")
    public void handleSendConnectionRequest(SendConnectionRequestEvent sendConnectionRequestEvent) {
        log.info("Sending notification - handleSendConnectionRequest : {}", sendConnectionRequestEvent);
        String message = String.format("You have received a connection request from User with ID : %d", sendConnectionRequestEvent.getSenderId());
        sendNotificationService.send(sendConnectionRequestEvent.getReceiverId(), message);
    }
    @KafkaListener(topics = "accept-connection-request-topic")
    public void handleAcceptConnectionRequest(AcceptConnectionRequestEvent acceptConnectionRequestEvent) {
        log.info("Sending notification - handleAcceptConnectionRequest : {}", acceptConnectionRequestEvent);
        String message = String.format("Your connection request have been accepted by User with ID : %d", acceptConnectionRequestEvent.getReceiverId());
        sendNotificationService.send(acceptConnectionRequestEvent.getSenderId(), message);
    }
}
