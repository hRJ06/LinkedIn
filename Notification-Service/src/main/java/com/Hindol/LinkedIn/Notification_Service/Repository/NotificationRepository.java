package com.Hindol.LinkedIn.Notification_Service.Repository;

import com.Hindol.LinkedIn.Notification_Service.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
