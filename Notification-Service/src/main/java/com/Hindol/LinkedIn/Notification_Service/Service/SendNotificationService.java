package com.Hindol.LinkedIn.Notification_Service.Service;

import com.Hindol.LinkedIn.Notification_Service.Entity.Notification;
import com.Hindol.LinkedIn.Notification_Service.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendNotificationService {
    private final NotificationRepository notificationRepository;
    public void send(Long userId, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);
        notificationRepository.save(notification);
        log.info("Successfully saved notification for User - {}", userId);
    }
}
