package com.michael.socialmedia.repository;

import com.michael.socialmedia.domain.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository  extends JpaRepository<Notifications,Long> {

}
