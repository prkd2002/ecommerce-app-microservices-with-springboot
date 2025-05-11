package com.thm.ecommerce.kafka.payment;

import com.thm.ecommerce.notification.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
